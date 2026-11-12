package swingy.gameplay.behaviors;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Vector;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Behavior;
import swingy.utils.TrueRandom;

public class JumpingPoint extends Behavior {
    private static final int MAX_MOVEMENT_COST = 50;
    int id = 3;

    // N Is total search radious (50 steps + 1 because of center, so 50 steps tothe left, to the top, to the bottom, to the right and the corners)
    static final int R = MAX_MOVEMENT_COST, W = 2 * R + 1, N = W * W;

    // use all of these with get index so it oesnt have to be 2d

    // explored nodes in this session
    private BitSet explored = new BitSet(N);

    // previous node from this one
    final short[] previous = new short[N];

    // for this node
    final short[] cost = new short[N];

    // stamp gow waht search id this node was
    final int[] stamp = new int[N];

    // nodes that found the objective
    final short[] found = new short[N];

    // nodes that found the objective
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
        Arrays.fill(found, (short)-1);
        Arrays.fill(cost, (short)-1);
        this.id = 3;
        this.cd = 500;
    }

    // This is probably gonna be the hardest algorithmi am going to implement in this project.
    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        byte jump = startJumping(info, map, where.getx(), where.gety());

        return ((byte)TrueRandom.getrandom(5));
    }

    private byte startJumping(GameData info, boolean [][] map, long x, long y) {

    }

    private byte jumpLeft() {
        
    }

    private byte jumpRight() {
        
    }

    private byte jumpDown() {
        
    }

    private byte jumpUp() {
        
    }

    private byte jumpLeftUp() {
        
    }

    private byte jumpRightUp() {
        
    }

    private byte jumpLeftBottom() {
        
    }

    private byte jumpRightBottom() {
        
    }
}
