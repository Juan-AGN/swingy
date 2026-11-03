package swingy.gameplay;

public abstract class Behavior {
    protected byte id;

    public abstract byte getDirection(GameData info, boolean [][] map);
}
