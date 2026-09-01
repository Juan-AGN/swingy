package swingy.gameplay.mapgenerator;

import java.util.Arrays;

public class Tile {
    private final int width;
    private final int height;

    private final boolean[][] cells;

    private final int connections;

    public Tile(boolean[][] cells, int connections) {
        this.height = cells.length;
        this.width = cells[0].length;
        this.cells = cells;
        this.connections = connections;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[][] getCells() {
        return cells;
    }

    public int getConnections() {
        return connections;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Tile other))
            return false;

        return connections == other.connections
                && Arrays.deepEquals(cells, other.cells);
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.deepHashCode(cells)
                + connections;
    }
}
