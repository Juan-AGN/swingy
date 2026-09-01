package swingy.gameplay.mapgenerator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

		return result;
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

	public static List<Tile> allVariants(Tile tile, boolean includeMirror) {
		Set<Tile> variants = new LinkedHashSet<>();

		variants.addAll(rotations(tile));

		if (includeMirror) {
			variants.addAll(rotations(mirror(tile)));
		}

		return new ArrayList<>(variants);
	}
}
