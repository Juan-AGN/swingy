package swingy.gameplay.behaviors;

import swingy.gameplay.Behavior;
import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.utils.TrueRandom;

public class Jester extends Behavior {
    public Jester() {
        this.id = 1;
    }

    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        return ((byte)TrueRandom.getrandom(5));
    }
}
