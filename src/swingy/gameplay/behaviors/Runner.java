package swingy.gameplay.behaviors;

import swingy.gameplay.Behavior;
import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.utils.TrueRandom;

public class Runner extends Behavior {
    public Runner() {
        this.id = 2;
        this.previous = (byte)(TrueRandom.getrandom(4) + 1);
        this.cd = 300;
    }

    private byte previous;

    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        switch (previous) {
            case 1:
                if (!info.hasWall(new Coordinates(where.getx(), where.gety() - 1)))
                    return (1);
                break ;
            case 2:
                if (!info.hasWall(new Coordinates(where.getx() - 1, where.gety())))
                    return (2);
                break ;
            case 3:
                if (!info.hasWall(new Coordinates(where.getx(), where.gety() + 1)))
                    return (3);
                break ;
            case 4:
                if (!info.hasWall(new Coordinates(where.getx() + 1, where.gety())))
                    return (4);
                break ;
            default:
                break ;
        }
        byte newDir = (byte)(TrueRandom.getrandom(4) + 1);
        this.previous = newDir;

        return (newDir);
    }
}
