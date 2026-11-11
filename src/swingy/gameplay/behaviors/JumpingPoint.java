package swingy.gameplay.behaviors;

import java.util.Vector;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Behavior;
import swingy.utils.TrueRandom;

public class JumpingPoint extends Behavior {
    private static final int MAX_MOVEMENT_COST = 500;
    int id = 0;
    public Vector<MNode> nodes;

    private class MNode {
        public int id;
        public int x;
        public int y;
        public MNode previous;
        public Vector<MNode> next;
        public int cost;
        public byte mprev = -1;
        public boolean explored = false;
        public boolean found = false;

        public MNode(int px, int py, MNode pprevious, int pid, int pcost) {
            this.x = px;
            this.y = py;
            this.previous = pprevious;
            this.cost = pcost;
            this.id = pid;
        }

        public void changeOwner(MNode pprevious, int pcost) {
            this.previous = pprevious;
            this.changeCost(pcost);
        }

        public void changeCost(int pcost) {
            for (int i = 0; i < next.size(); i ++)
                next.get(i).reduceCost(cost - pcost);

            this.cost = pcost;
        }

        public void reduceCost(int pcost) {
            for (int i = 0; i < next.size(); i ++)
                next.get(i).reduceCost(pcost);

            this.cost -= pcost;
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
