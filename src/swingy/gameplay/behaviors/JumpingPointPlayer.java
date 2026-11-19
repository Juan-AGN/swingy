package swingy.gameplay.behaviors;

import java.util.Arrays;
import java.util.BitSet;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Behavior;
import swingy.utils.GetIndexBit;
import swingy.utils.TrueRandom;

public class JumpingPointPlayer extends Behavior {
    static final byte UP = 1;
    static final byte LEFT = 2;
    static final byte BOTTOM = 3;
    static final byte RIGHT = 4;
    static final byte UPRIGHT = 5;
    static final byte BOTTOMRIGHT = 6;
    static final byte BOTTOMLEFT = 7;
    static final byte UPLEFT = 8;
    private static final int MAX_MOVEMENT_COST = 50;
    int id = 4;

    // N Is total search radious (50 steps + 1 because of center, so 50 steps tothe left, to the top, to the bottom, to the right and the corners)
    static final int R = MAX_MOVEMENT_COST + 2, W = 2 * R + 1, N = W * W;

    // use all of these with get index so it oesnt have to be 2d

    // explored nodes in this session
    private BitSet explored = new BitSet(N);

    long wherex = 0;
    long wherey = 0;
    // previous node from this one
    final short[] previous = new short[N];

    final byte[] dir = new byte[N];

    // for this node
    final short[] cost = new short[N];

    // stamp is what search id this node was
    final int[] stamp = new int[N];

    // nodes that found the objective
    short found;

    // priority nodes
    final short[] prio = new short[N + 1];

    int searchId = 0;

    // Just in case i overstep the maximun ID (a little far from max int just in case), it should really ever come into effect, but just in case.
    void beginSearch() {
        searchId++;
        if (searchId > 2000000)
        {
            for (int i = 0; i < stamp.length; i++)
                stamp[i] = -2000000;
            searchId = -1999999;
        }
    }

    boolean visited(short cell) {
        return (stamp[cell] == searchId);
    }

    void visit(short cell) {
        stamp[cell] = searchId;
    }

    public JumpingPointPlayer() {
        Arrays.fill(stamp, -1);
        Arrays.fill(cost, (short)-1);
        Arrays.fill(prio, (short)-1);
        this.id = 4;
        this.cd = 500;
        this.found = -1;
    }

    // This is probably gonna be the hardest algorithmi am going to implement in this project.
    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        explored.clear();
        found = -1;
        beginSearch();
        byte jump = startJumping(info, map, where.getx(), where.gety());

        if (jump == -1)
            jump = (byte)TrueRandom.getrandom(5);
        return (jump);
    }

    private byte startJumping(GameData info, boolean [][] map, long x, long y) {
        if (info.hasPlayer(new Coordinates(x, y)))
            return (5);
        byte retval = -1;
        wherex = x;
        wherey = y;
        short pos = (short)idx(x, y);
        stamp[pos] = searchId;
        explored.set(pos);
        if (info.isInside(x + 1, y) && !info.hasWall(x + 1, y))
            jumpRight(info, map, x + 1, y, RIGHT, 0, pos);
        if (info.isInside(x + 1, y - 1) && ((info.hasWall(x, y - 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y - 1))))
            jumpRightUp(info, map, x + 1, y - 1, UPRIGHT, 0, pos);   
        if (info.isInside(x, y - 1) && !info.hasWall(x, y - 1)) 
            jumpUp(info, map, x, y - 1, UP, 0, pos);
        if (info.isInside(x - 1, y - 1) && !info.hasWall(x - 1, y - 1) && ((info.hasWall(x, y - 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y - 1))))
            jumpLeftUp(info, map, x - 1, y - 1, UPLEFT, 0, pos);
        if (info.isInside(x - 1, y) && !info.hasWall(x - 1, y))
            jumpLeft(info, map, x - 1, y, LEFT, 0, pos);
        if (info.isInside(x - 1, y + 1) && !info.hasWall(x - 1, y + 1) && ((info.hasWall(x, y + 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y + 1))))
            jumpLeftBottom(info, map, x - 1, y + 1, BOTTOMLEFT, 0, pos);
        if (info.isInside(x, y + 1) && !info.hasWall(x, y + 1))
            jumpDown(info, map, x, y + 1, BOTTOM, 0, pos);
        if (info.isInside(x + 1, y + 1) && !info.hasWall(x + 1, y + 1) && ((info.hasWall(x, y + 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y + 1))))
            jumpRightBottom(info, map, x + 1, y + 1, BOTTOMRIGHT, 0, pos);

        while (prio[0] != -1 && !checkWaste(cost[prio[0]]))
            jumpingPoint(info, prio[0]);

        if (prio[0] != -1)
            emptyPrio();

        if (found != -1)
        {
            retval = getfirstmove(pos);
            switch (retval) {
                case UPRIGHT:
                    if (!info.hasWall(x + 1, y))
                        retval = RIGHT;
                    else
                        retval = UP;
                    break;
                case UPLEFT:
                    if (!info.hasWall(x - 1, y))
                        retval = LEFT;
                    else
                        retval = UP;
                    break;
                case BOTTOMLEFT:
                    if (!info.hasWall(x - 1, y))
                        retval = LEFT;
                    else
                        retval = BOTTOM;
                    break;
                case BOTTOMRIGHT:
                    if (!info.hasWall(x + 1, y))
                        retval = RIGHT;
                    else
                        retval = BOTTOM;
                    break;
                default:
                    break;
            }
        }
        return (retval);
    }

    private boolean jumpLeft(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost ++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasPlayer(new Coordinates(x, y)))
        {
            this.addFound(x, y, pcost, LEFT, pprevious);
            return (true);
        }

        short pos = (short)idx(x, y);
        if (info.isInside(x - 1, y + 1))
            if (!info.hasWall(x - 1, y + 1) && ((info.hasWall(x, y + 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y + 1))) && !explored.get(idx(x - 1, y + 1)))
                jumper = setJumpingPoint(info, x - 1, y + 1, pcost + 2, BOTTOMLEFT, pos, false);
        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && ((info.hasWall(x, y - 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y - 1))) && !explored.get(idx(x - 1, y - 1)))
                jumper = setJumpingPoint(info, x - 1, y - 1, pcost + 2, UPLEFT, pos, false);
        
        if (jumper)
            setJumpingPoint(info, x, y, pcost, LEFT, pprevious, true);

        if (info.isInside(x - 1, y) && !info.hasWall(x - 1, y))
        {
            if (jumper == false)
                return (jumpLeft(info, map, x - 1, y, prevdir, pcost, pprevious));
            else
                jumpLeft(info, map, x - 1, y, prevdir, pcost, pprevious);
        }
        return (jumper);
    }

    private boolean jumpRight(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasPlayer(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, RIGHT, pprevious);
            return (true);
        }

        short pos = (short) idx(x, y);

        if (info.isInside(x + 1, y + 1))
            if (!info.hasWall(x + 1, y + 1) && ((info.hasWall(x, y + 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y + 1))) && !explored.get(idx(x + 1, y + 1)))
                jumper = setJumpingPoint(info, x + 1, y + 1, pcost + 2, BOTTOMRIGHT, pos, false);

        if (info.isInside(x + 1, y - 1))
            if (!info.hasWall(x + 1, y - 1) && ((info.hasWall(x, y - 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y - 1))) && !explored.get(idx(x + 1, y - 1)))
                jumper = setJumpingPoint(info, x + 1, y - 1, pcost + 2, UPRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x, y, pcost, RIGHT, pprevious, true);

        if (info.isInside(x + 1, y) && !info.hasWall(x + 1, y)) {
            if (jumper == false)
                return (jumpRight(info, map, x + 1, y, prevdir, pcost, pprevious));
            else
                jumpRight(info, map, x + 1, y, prevdir, pcost, pprevious);
        }

        return (jumper);
    }


    private boolean jumpDown(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasPlayer(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, BOTTOM, pprevious);
            return (true);
        }

        short pos = (short) idx(x, y);

        if (info.isInside(x - 1, y + 1))
            if (!info.hasWall(x - 1, y + 1) && ((info.hasWall(x - 1, y) && !info.hasWall(x, y + 1)) || (info.hasWall(x, y + 1) && !info.hasWall(x - 1, y))) && !explored.get(idx(x - 1, y + 1)))
                jumper = setJumpingPoint(info, x - 1, y + 1, pcost + 2, BOTTOMLEFT, pos, false);

        if (info.isInside(x + 1, y + 1))
            if (!info.hasWall(x + 1, y + 1) && ((info.hasWall(x + 1, y) && !info.hasWall(x, y + 1)) || (info.hasWall(x, y + 1) && !info.hasWall(x + 1, y))) && !explored.get(idx(x + 1, y + 1)))
                jumper = setJumpingPoint(info, x + 1, y + 1, pcost + 2, BOTTOMRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x, y, pcost, BOTTOM, pprevious, true);

        if (info.isInside(x, y + 1) && !info.hasWall(x, y + 1)) {
            if (jumper == false)
                return (jumpDown(info, map, x, y + 1, prevdir, pcost, pprevious));
            else
                jumpDown(info, map, x, y + 1, prevdir, pcost, pprevious);
        }

        return (jumper);
    }


    private boolean jumpUp(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasPlayer(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, UP, pprevious);
            return (true);
        }

        short pos = (short) idx(x, y);

        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && ((info.hasWall(x - 1, y) && !info.hasWall(x, y - 1)) || (info.hasWall(x, y - 1) && !info.hasWall(x - 1, y))) && !explored.get(idx(x - 1, y - 1)))
                jumper = setJumpingPoint(info, x - 1, y - 1, pcost + 2, UPLEFT, pos, false);

        if (info.isInside(x + 1, y - 1))
            if (!info.hasWall(x + 1, y - 1) && ((info.hasWall(x + 1, y) && !info.hasWall(x, y - 1)) || (info.hasWall(x, y - 1) && !info.hasWall(x + 1, y))) && !explored.get(idx(x + 1, y - 1)))
                jumper = setJumpingPoint(info, x + 1, y - 1, pcost + 2, UPRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x, y, pcost, UP, pprevious, true);

        if (info.isInside(x, y - 1) && !info.hasWall(x, y - 1)) {
            if (jumper == false)
                return (jumpUp(info, map, x, y - 1, prevdir, pcost, pprevious));
            else
                jumpUp(info, map, x, y - 1, prevdir, pcost, pprevious);
        }

        return (jumper);
    }

    private void jumpLeftUp(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        pcost += 2;
        boolean jumper = false;
        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return ;
        short me = (short) idx(x, y);

        jumper = jumpUp(info, map, x, y, prevdir, pcost - 1, me);
        jumper = (jumpLeft(info, map, x, y, prevdir, pcost - 1, me) || jumper) == true ? true : false;

        if (jumper == true)
            setForcedJumpingPoint(info, x, y, pcost, UPLEFT, pprevious);

        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && (!info.hasWall(x - 1, y) || !info.hasWall(x, y - 1)) && !explored.get(idx(x - 1, y - 1)))
                jumpLeftUp(info, map, x - 1, y - 1, UPLEFT, pcost, pprevious);
    }


    private void jumpRightUp(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        pcost += 2;
        boolean jumper = false;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return ;
        short me = (short) idx(x, y);

        jumper = jumpUp(info, map, x, y, prevdir, pcost - 1, me);
        jumper = (jumpRight(info, map, x, y, prevdir, pcost - 1, me) || jumper);

        if (jumper)
            setForcedJumpingPoint(info, x, y, pcost, UPRIGHT, pprevious);

        if (info.isInside(x + 1, y - 1))
            if (!info.hasWall(x + 1, y - 1) && (!info.hasWall(x + 1, y) || !info.hasWall(x, y - 1))  && !explored.get(idx(x + 1, y - 1)))
                jumpRightUp(info, map, x + 1, y - 1, UPRIGHT, pcost, pprevious);
    }

    private void jumpLeftBottom(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        pcost += 2;
        boolean jumper = false;
        short me = (short) idx(x, y);

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return ;

        jumper = jumpDown(info, map, x, y, prevdir, pcost - 1, me);
        jumper = jumpLeft(info, map, x, y, prevdir, pcost - 1, me) || jumper;

        if (jumper)
            setForcedJumpingPoint(info, x, y, pcost, BOTTOMLEFT, pprevious);

        if (info.isInside(x - 1, y + 1))
            if (!info.hasWall(x - 1, y + 1) && (!info.hasWall(x - 1, y) || !info.hasWall(x, y + 1)) && !explored.get(idx(x - 1, y + 1)))
                jumpLeftBottom(info, map, x - 1, y + 1, BOTTOMLEFT, pcost, pprevious);
    }

    private void jumpRightBottom(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        pcost += 2;
        boolean jumper = false;
        short me = (short) idx(x, y);

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return ;

        jumper = jumpDown(info, map, x, y, prevdir, pcost - 1, me);
        jumper = jumpRight(info, map, x, y, prevdir, pcost - 1, me) || jumper;

        if (jumper)
            setForcedJumpingPoint(info, x, y, pcost, BOTTOMRIGHT, pprevious);

        if (info.isInside(x + 1, y + 1))
            if (!info.hasWall(x + 1, y + 1) && (!info.hasWall(x + 1, y) || !info.hasWall(x, y + 1)) && !explored.get(idx(x + 1, y + 1)))
                jumpRightBottom(info, map, x + 1, y + 1, BOTTOMRIGHT, pcost, pprevious);
    }

    private void jumpingPoint(GameData info, short ppos) {
        Coordinates where = GetIndexBit.getCoordinates(ppos, W);
        where = new Coordinates((where.getx() - R) + wherex, (where.gety() - R) + wherey);
        explored.set(ppos);
        prioexecuted();

        switch (dir[ppos]) {
            case BOTTOMLEFT:
                jumpLeftBottom(info, null, where.getx(), where.gety(), BOTTOMLEFT, cost[ppos] - 2, ppos);
                break ;
            case BOTTOMRIGHT:
                jumpRightBottom(info, null, where.getx(), where.gety(), BOTTOMRIGHT, cost[ppos] - 2, ppos);
                break ;
            case UPRIGHT:
                jumpRightUp(info, null, where.getx(), where.gety(), UPRIGHT, cost[ppos] - 2, ppos);
                break ;
            case UPLEFT:
                jumpLeftUp(info, null, where.getx(), where.gety(), UPLEFT, cost[ppos] - 2, ppos);
                break ;
        }
    }

    private void setForcedJumpingPoint(GameData info, long x, long y, int pcost, byte pdir, short pprevious) {
        short pos = (short)idx(x, y);

        stamp[pos] = searchId;
        dir[pos] = pdir;
        cost[pos] = (short)pcost;
        explored.set(pos);
        if (pos != pprevious)
            previous[pos] = pprevious;
        return;
    }

    private boolean setJumpingPoint(GameData info, long x, long y, int pcost, byte pdir, short pprevious, boolean pexplored) {
        short pos = (short)idx(x, y);
        int i = 0;
        short temp;

        if (info.hasPlayer(new Coordinates(x, y)))
        {
            this.addFound(x, y, pcost, pdir, pprevious);
            return (true);
        }

        if ((stamp[pos] == searchId && cost[pos] < pcost) || explored.get(pos))
            return (true);

        stamp[pos] = searchId;
        previous[pos] = pprevious;
        dir[pos] = pdir;
        cost[pos] = (short)pcost;
        if (pexplored)
            explored.set(pos);

        if (prio[0] == -1)
        {
            prio[0] = pos;
            return (true);
        }
        for (; prio[i] != -1 && cost[prio[i]] < pcost; i ++) {};

        temp = prio[i];
        prio[i] = pos;
        for (int u = i + 1; u < prio.length && temp != -1; u ++)
        {
            short ttemp = prio[u];
            prio[u] = temp;
            temp = ttemp;
        }
        return (true);
    }

    private void addFound(long x, long y, int pcost, byte pdir, short pprevious) {
        short pos = (short)idx(x, y);

        if (found != -1)
            if (cost[found] <= pcost)
                return ;
        stamp[pos] = searchId;
        previous[pos] = pprevious;
        dir[pos] = pdir;
        cost[pos] = (short)pcost;
        this.found = pos;
    }

    private byte getfirstmove(short ppos) {
        short tpos = found;
        short toret = found;
        while (tpos != ppos)
        {
            toret = tpos;
            tpos = previous[tpos];
        }

        if (toret < 0)
            return (0);
        return (dir[toret]);
    }

    private boolean checkWaste(int pcost) {
        if (found != -1 && cost[found] <= pcost)
            return (true);
        return (false);
    }

    private void prioexecuted() {
        for (int u = 0; prio[u] != -1; u ++)
            prio[u] = prio[u + 1];
    }

    private void emptyPrio() {
        for (int u = 0; prio[u] != -1; u ++)
            prio[u] = -1;
    }

    private int idx(long x, long y) {
        return GetIndexBit.getIndex(x - wherex + R, y - wherey + R, W);
    }
}
