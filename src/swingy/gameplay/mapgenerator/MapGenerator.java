package swingy.gameplay.mapgenerator;

public final class MapGenerator {
    int [][]map;

    public int[][] generateMap(int level) {
        int size = (level - 1) * 5 + 10 - (level % 2);

        if (level <= 0)
            return (new int[1][1]);
        this.map = new int[size][size];

        if (level > 2 && level % 2 == 1)
            return (map);
        else
            threebythree();

        return (map);
    }

    private void threebythree() {
        
    }
}
