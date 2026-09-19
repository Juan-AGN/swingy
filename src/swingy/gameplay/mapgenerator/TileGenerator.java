package swingy.gameplay.mapgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import swingy.utils.TrueRandom;

public final class TileGenerator {
	public static Tile rotateClockwise(Tile tile) {

		int oldWidth = tile.getWidth();
		int oldHeight = tile.getHeight();

		boolean[][] oldCells = tile.getCells();

		boolean[][] newCells = new boolean[oldWidth][oldHeight];

		for (int y = 0; y < oldHeight; y++) {

			for (int x = 0; x < oldWidth; x++) {

				int newX = oldHeight - 1 - y;
				int newY = x;

				newCells[newY][newX] = oldCells[y][x];
			}
		}

		return new Tile(newCells, rotateConnections(tile.getConnections()));
	}

	private static int rotateConnections(int mask) {
		int result = 0;

		if ((mask & Direction.TOP.mask()) != 0)
			result |= Direction.RIGHT.mask();

		if ((mask & Direction.RIGHT.mask()) != 0)
			result |= Direction.BOTTOM.mask();

		if ((mask & Direction.BOTTOM.mask()) != 0)
			result |= Direction.LEFT.mask();

		if ((mask & Direction.LEFT.mask()) != 0)
			result |= Direction.TOP.mask();

		return result;
	}

	public static List<Tile> rotations(Tile tile) {

		List<Tile> result = new ArrayList<>();

		Tile current = tile;

		for (int i = 0; i < 4; i++) {

			if (!result.contains(current)) {
				result.add(current);
			}

			current = rotateClockwise(current);
		}

		return (result);
	}

	public static Tile mirror(Tile tile) {

		int width = tile.getWidth();
		int height = tile.getHeight();

		boolean[][] oldCells = tile.getCells();

		boolean[][] newCells = new boolean[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newCells[y][width - 1 - x] = oldCells[y][x];
			}
		}

		int oldMask = tile.getConnections();
		int newMask = 0;

		if ((oldMask & Direction.TOP.mask()) != 0)
			newMask |= Direction.TOP.mask();

		if ((oldMask & Direction.RIGHT.mask()) != 0)
			newMask |= Direction.LEFT.mask();

		if ((oldMask & Direction.BOTTOM.mask()) != 0)
			newMask |= Direction.BOTTOM.mask();

		if ((oldMask & Direction.LEFT.mask()) != 0)
			newMask |= Direction.RIGHT.mask();

		return new Tile(newCells, newMask);
	}

	public static Tile[] allVariants(Tile tile, boolean includeMirror) {
		Set<Tile> variants = new LinkedHashSet<>();

		variants.addAll(rotations(tile));

		if (includeMirror) {
			variants.addAll(rotations(mirror(tile)));
		}

		return (variants.toArray(Tile[]::new));
	}

    public static Tile getRandomMinConnections(int minConnections) {
        if (minConnections < 1 || minConnections > 4)
            return (Tiles.EMPTY);
        Tile[] validTiles = Arrays.stream(Tiles.ALL).filter(tile -> Integer.bitCount(tile.getConnections()) >= minConnections).toArray(Tile[]::new);

        return (validTiles[TrueRandom.getrandom(validTiles.length)]);
    }

    public static Tile getRandomMinLine() {
        Tile[] validTiles = Arrays.stream(Tiles.ALL).filter(tile -> (tile.getConnections() & 5) == 5 || (tile.getConnections() & 10) == 10).toArray(Tile[]::new);

        return (validTiles[TrueRandom.getrandom(validTiles.length)]);
    }

    public static Tile getRandomMinCurve() {
        Tile[] validTiles = Arrays.stream(Tiles.ALL).filter(tile -> (Integer.bitCount(tile.getConnections()) >= 2 && tile.getConnections() != 5 && tile.getConnections() != 10)).toArray(Tile[]::new);

        return (validTiles[TrueRandom.getrandom(validTiles.length)]);
    }

    /**
     * Gives back a random tile with the asked for connections.
     *
     * @param connections bitmap of connections (1 = TOP | 2 = RIGHT | 4 = BOTTOM | 8 = LEFT), you can also use the enums on tiles class, this bitmap normaly shouldnt include the prio.
     * @param prio same as connections, but prioritary, connection can have, prio must have.
     * @param nconnections num of connections (1 for one connection, 2 for two etc...).
     */

    public static Tile getRandomSpecConnect(int connections, int prio, int nconnections) {
        Tile tile;
        Tile[] tileList;

        if ((prio | connections) == 5 || (prio | connections) == 10)
            tile = getRandomMinLine();
        else if (nconnections >= 2)
            tile = getRandomMinCurve();
        else
            tile = getRandomMinConnections(nconnections);

        tileList = TileGenerator.allVariants(tile, true);
        tileList = Arrays.stream(tileList).filter(ttile -> ((ttile.getConnections() & prio) == prio && (ttile.getConnections() & connections) != 0) && Integer.bitCount(ttile.getConnections()) >= nconnections).toArray(Tile[]::new);
        
        if (tileList.length == 0)
            return getRandomMinConnections(4);

        return(tileList[TrueRandom.getrandom(tileList.length)]);
    }
}
