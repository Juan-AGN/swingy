package swingy.gameplay.mapgenerator;

public enum Direction {
    TOP(1),
    RIGHT(2),
    BOTTOM(4),
    LEFT(8);

    private final int mask;

    Direction(int mask) {
        this.mask = mask;
    }

    public int mask() {
        return mask;
    }
}
