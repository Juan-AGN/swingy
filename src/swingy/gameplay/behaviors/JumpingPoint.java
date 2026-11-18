package swingy.gameplay.behaviors;

import java.util.Arrays;
import java.util.BitSet;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Behavior;
import swingy.utils.GetIndexBit;
import swingy.utils.TrueRandom;

public class JumpingPoint extends Behavior {
    static final byte UP = 1;
    static final byte LEFT = 2;
    static final byte BOTTOM = 3;
    static final byte RIGHT = 4;
    static final byte UPRIGHT = 5;
    static final byte BOTTOMRIGHT = 6;
    static final byte BOTTOMLEFT = 5;
    static final byte UPLEFT = 5;
    private static final int MAX_MOVEMENT_COST = 50;
    int id = 3;
    int priocount = 0;

    // N Is total search radious (50 steps + 1 because of center, so 50 steps tothe left, to the top, to the bottom, to the right and the corners)
    static final int R = MAX_MOVEMENT_COST, W = 2 * R + 1, N = W * W;

    // use all of these with get index so it oesnt have to be 2d

    // explored nodes in this session
    private BitSet explored = new BitSet(N);

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

    public JumpingPoint() {
        Arrays.fill(stamp, -1);
        Arrays.fill(cost, (short)-1);
        Arrays.fill(prio, (short)-1);
        this.id = 3;
        this.cd = 500;
        this.found = -1;
    }

    // This is probably gonna be the hardest algorithmi am going to implement in this project.
    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        byte jump = startJumping(info, map, where.getx(), where.gety());

        return ((byte)TrueRandom.getrandom(5));
    }

    private byte startJumping(GameData info, boolean [][] map, long x, long y) {
        int retval = -1;
        short pos = (short)GetIndexBit.getIndex(x, y, W);
        stamp[pos] = id;
        explored.set(GetIndexBit.getIndex(x, y, W));
        if (info.isInside(x + 1, y) && !info.hasWall(x + 1, y))
            jumpRight(info, map, x + 1, y, RIGHT, 0, pos);
        if (info.isInside(x + 1, y - 1) && !info.hasWall(x + 1, y - 1))
            jumpRightUp(info, map, x + 1, y - 1, UPRIGHT, 0, pos);   
        if (info.isInside(x, y - 1) && !info.hasWall(x, y - 1)) 
            jumpUp(info, map, x, y - 1, UP, 0, pos);
        if (info.isInside(x - 1, y - 1) && !info.hasWall(x - 1, y - 1));
            jumpLeftUp(info, map, x - 1, y - 1, UPLEFT, 0, pos);
        if (info.isInside(x - 1, y) && !info.hasWall(x - 1, y))
            jumpLeft(info, map, x - 1, y, LEFT, 0, pos);
        if (info.isInside(x - 1, y + 1) && !info.hasWall(x - 1, y + 1))
            jumpLeftBottom(info, map, x - 1, y + 1, BOTTOMLEFT, 0, pos);
        if (info.isInside(x, y + 1) && !info.hasWall(x, y + 1))
            jumpDown(info, map, x, y + 1, BOTTOM, 0, pos);
        if (info.isInside(x + 1, y + 1) && !info.hasWall(x + 1, y + 1))
            jumpRightBottom(info, map, x + 1, y + 1, BOTTOMRIGHT, 0, pos);

        while (prio[0] != -1) {

        }
    }

    private boolean jumpLeft(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost ++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasEnemy(new Coordinates(x, y)))
        {
            this.addFound(x, y, pcost, (byte)2, pprevious);
            return (true);
        }

        short pos = (short)GetIndexBit.getIndex(x, y, W);
        if (info.isInside(x - 1, y + 1))
            if (!info.hasWall(x - 1, y + 1) && (info.hasWall(x, y + 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y + 1)) && !explored.get(GetIndexBit.getIndex(x - 1, y + 1, W)))
                jumper = setJumpingPoint(info, x - 1, y + 1, pcost + 2, BOTTOMLEFT, pos, false);
        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && (info.hasWall(x, y - 1) && !info.hasWall(x - 1, y)) || (info.hasWall(x - 1, y) && !info.hasWall(x, y - 1)) && !explored.get(GetIndexBit.getIndex(x - 1, y - 1, W)))
                jumper = setJumpingPoint(info, x - 1, y - 1, pcost + 2, UPLEFT, pos, false);
        
        if (jumper)
            setJumpingPoint(info, x - 1, y + 1, pcost, LEFT, pos, true);

        if (info.isInside(x - 1, y) && !info.hasWall(x - 1, y))
        {
            if (jumper == false)
                return (jumpLeft(info, map, x, y, prevdir, pcost, pprevious));
            else
                jumpLeft(info, map, x - 1, y, prevdir, pcost, pprevious);
        }
        return (true);
    }

    private boolean jumpRight(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasEnemy(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, (byte) 2, pprevious);
            return (true);
        }

        short pos = (short) GetIndexBit.getIndex(x, y, W);

        if (info.isInside(x + 1, y + 1))
            if (!info.hasWall(x + 1, y + 1) && ((info.hasWall(x, y + 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y + 1))) && !explored.get(GetIndexBit.getIndex(x + 1, y + 1, W)))
                jumper = setJumpingPoint(info, x + 1, y + 1, pcost + 2, BOTTOMRIGHT, pos, false);

        if (info.isInside(x + 1, y - 1))
            if (!info.hasWall(x + 1, y - 1) && ((info.hasWall(x, y - 1) && !info.hasWall(x + 1, y)) || (info.hasWall(x + 1, y) && !info.hasWall(x, y - 1))) && !explored.get(GetIndexBit.getIndex(x + 1, y - 1, W)))
                jumper = setJumpingPoint(info, x + 1, y - 1, pcost + 2, UPRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x + 1, y + 1, pcost, RIGHT, pos, true);

        if (info.isInside(x + 1, y) && !info.hasWall(x + 1, y)) {
            if (jumper == false)
                return (jumpRight(info, map, x + 1, y, prevdir, pcost, pprevious));
            else
                jumpRight(info, map, x + 1, y, prevdir, pcost, pprevious);
        }

        return (true);
    }


    private boolean jumpDown(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasEnemy(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, (byte) 2, pprevious);
            return (true);
        }

        short pos = (short) GetIndexBit.getIndex(x, y, W);

        if (info.isInside(x - 1, y + 1))
            if (!info.hasWall(x - 1, y + 1) && ((info.hasWall(x - 1, y) && !info.hasWall(x, y + 1)) || (info.hasWall(x, y + 1) && !info.hasWall(x - 1, y))) && !explored.get(GetIndexBit.getIndex(x - 1, y + 1, W)))
                jumper = setJumpingPoint(info, x - 1, y + 1, pcost + 2, BOTTOMLEFT, pos, false);

        if (info.isInside(x + 1, y + 1))
            if (!info.hasWall(x + 1, y + 1) && ((info.hasWall(x + 1, y) && !info.hasWall(x, y + 1)) || (info.hasWall(x, y + 1) && !info.hasWall(x + 1, y))) && !explored.get(GetIndexBit.getIndex(x + 1, y + 1, W)))
                jumper = setJumpingPoint(info, x + 1, y + 1, pcost + 2, BOTTOMRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x - 1, y + 1, pcost, BOTTOM, pos, true);

        if (info.isInside(x, y + 1) && !info.hasWall(x, y + 1)) {
            if (jumper == false)
                return (jumpDown(info, map, x, y + 1, prevdir, pcost, pprevious));
            else
                jumpDown(info, map, x, y + 1, prevdir, pcost, pprevious);
        }

        return (true);
    }


    private boolean jumpUp(GameData info, boolean[][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        boolean jumper = false;
        pcost++;

        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return false;

        if (info.hasEnemy(new Coordinates(x, y))) {
            this.addFound(x, y, pcost, (byte) 2, pprevious);
            return (true);
        }

        short pos = (short) GetIndexBit.getIndex(x, y, W);

        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && ((info.hasWall(x - 1, y) && !info.hasWall(x, y - 1)) || (info.hasWall(x, y - 1) && !info.hasWall(x - 1, y))) && !explored.get(GetIndexBit.getIndex(x - 1, y - 1, W)))
                jumper = setJumpingPoint(info, x - 1, y - 1, pcost + 2, UPLEFT, pos, false);

        if (info.isInside(x + 1, y - 1))
            if (!info.hasWall(x + 1, y - 1) && ((info.hasWall(x + 1, y) && !info.hasWall(x, y - 1)) || (info.hasWall(x, y - 1) && !info.hasWall(x + 1, y))) && !explored.get(GetIndexBit.getIndex(x + 1, y - 1, W)))
                jumper = setJumpingPoint(info, x + 1, y - 1, pcost + 2, UPRIGHT, pos, false);

        if (jumper)
            setJumpingPoint(info, x - 1, y - 1, pcost, UP, pos, true);

        if (info.isInside(x, y - 1) && !info.hasWall(x, y - 1)) {
            if (jumper == false)
                return (jumpUp(info, map, x, y - 1, prevdir, pcost, pprevious));
            else
                jumpUp(info, map, x, y - 1, prevdir, pcost, pprevious);
        }

        return (true);
    }

    private void jumpLeftUp(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        pcost += 2;
        boolean jumper = false;
        if (!info.isInside(x, y) || pcost > MAX_MOVEMENT_COST)
            return ;
        short pos = (short) GetIndexBit.getIndex(x, y, W);

        if (info.isInside(x - 1, y))
            if (!info.hasWall(x - 1, y) && !explored.get(GetIndexBit.getIndex(x -1, y, W)))
                jumper = jumpUp(info, map, x - 1, y, prevdir, pcost - 1, pprevious);

        if (info.isInside(x, y - 1))
            if (!info.hasWall(x, y - 1) && !explored.get(GetIndexBit.getIndex(x, y - 1, W)))
                jumper = jumpLeft(info, map, x, y - 1, prevdir, pcost - 1, pprevious) || jumper == true ? true : false;
        if (jumper == true)
            setForcedJumpingPoint(info, x, y, pcost, UPLEFT, pprevious);


        if (info.isInside(x - 1, y - 1))
            if (!info.hasWall(x - 1, y - 1) && ((info.hasWall(x - 1, y) && !info.hasWall(x, y - 1)) || (info.hasWall(x, y - 1) && !info.hasWall(x - 1, y))) && !explored.get(GetIndexBit.getIndex(x - 1, y - 1, W)))
                jumpLeftUp(info, map, x - 1, y - 1, UPLEFT, pcost, pprevious);
    }

    private boolean jumpRightUp(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        
    }

    private boolean jumpLeftBottom(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        
    }

    private boolean jumpRightBottom(GameData info, boolean [][] map, long x, long y, byte prevdir, int pcost, short pprevious) {
        
    }

    private void jumpingPoint() {

    }

    private void setForcedJumpingPoint(GameData info, long x, long y, int pcost, byte pdir, short pprevious) {
        short pos = (short)GetIndexBit.getIndex(x, y, W);

        stamp[pos] = id;
        previous[pos] = pprevious;
        dir[pos] = pdir;
        cost[pos] = (short)pcost;
        explored.set(pos);
    }

    private boolean setJumpingPoint(GameData info, long x, long y, int pcost, byte pdir, short pprevious, boolean pexplored) {
        short pos = (short)GetIndexBit.getIndex(x, y, W);
        int i = 0;
        short temp;

        if (info.hasEnemy(new Coordinates(x, y)))
        {
            this.addFound(x, y, pcost, pdir, pprevious);
            return (true);
        }

        if ((stamp[pos] == id && cost[pos] < pcost) || explored.get(pos))
            return (true);

        stamp[pos] = id;
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
        for (; prio[i] != -1 && cost[prio[i]] >= pcost; i ++) {};

        temp = prio[i];
        prio[i] = temp;
        for (int u = i + 1; temp != -1; u ++)
        {
            short ttemp = prio[u];
            prio[u] = temp;
            temp = ttemp;
        }
        return (true);
    }

    private void addFound(long x, long y, int pcost, byte pdir, short pprevious) {
        short pos = (short)GetIndexBit.getIndex(x, y, W);

        if (found != -1)
            if (cost[found] <= pcost)
                return ;
        stamp[pos] = id;
        previous[pos] = pprevious;
        dir[pos] = pdir;
        this.found = pos;
    }
}
