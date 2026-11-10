package swingy.gameplay.behaviors;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Behavior;
import swingy.utils.TrueRandom;

public class JumpingPoint extends Behavior {
    private static final int MAX_MOVEMENT_COST = 500;

    private class Node {
        public int x;
        public int y;
        public byte previous;
        public byte cost;

        public Node(int x, int y, byte previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }
    }

    public JumpingPoint() {
        this.id = 3;
        this.cd = 500;
    }

    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        return ((byte)TrueRandom.getrandom(5));
    }



}
