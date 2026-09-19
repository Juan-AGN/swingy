package swingy.gameplay.mapgenerator;

public final class MapGenerator {
    private int [][]map;
    private int scaledsize;
    private int pos;

    public int[][] generateMap(int level) {
        int size = (level - 1) * 5 + 10 - (level % 2);

        if (level <= 0)
            return (new int[1][1]);
        this.map = new int[size - 1][size - 1];

        if (level > 2 && level % 2 == 1)
            return (map);
        else
            threebythree(size);

        return (map);
    }

    private void threebythree(int size) {
        scaledsize = size / 3;
        pos = (size / 2) + 1;
        

    }
}
