package swingy.gameplay.mapgenerator;

import java.util.Arrays;

import swingy.utils.TrueRandom;

public final class MapGenerator {
    private boolean [][]map;
    private Tile [][]mapTiles;
    private byte [][]mainpath;
    private byte [][]avoidpath;
    private int scaledsize;
    private int size;
    private byte pool;

    public boolean[][] generateMap(int level) {
        size = (level - 1) * 5 + 10 - (level % 2);

        if (level <= 0)
            return (new boolean[1][1]);
        if (size % 3 == 0)
        {
            this.map = new boolean[size][size];
            this.mapTiles = new Tile[size / 3][size / 3];
            this.mainpath = new byte[size / 3][size / 3];
            this.avoidpath = new byte[size / 3][size / 3];
            this.pool = (byte)(TrueRandom.getrandom(Tiles.POOLS.length + 1) - 1);

            this.threebythree(size);
        }
        else
        {
            this.map = new boolean[size][size];
            this.mapTiles = new Tile[(size / 3) + 2][(size / 3) + 2];
            this.mainpath = new byte[(size / 3) + 2][(size / 3) + 2];
            this.avoidpath = new byte[(size / 3) + 2][(size / 3) + 2];
            this.pool = (byte)(TrueRandom.getrandom(Tiles.POOLS.length + 1) - 1);

            this.threebythreetwo(size);  
        }

        return(map);
    }

    private void threebythree(int size) {
        scaledsize = size / 3;
        int posx = (size / 3) / 2;
        int posy = posx;
        this.startMainthree(posy, posx);
        this.recursiveGenthree(posy, posx, -1);
        this.fillMaptree();
    }

    private void threebythreetwo(int size) {
        scaledsize = (size / 3) + 2;
        int posx = ((size / 3) + (size % 3) - 1) / 2;
        int posy = posx;
        this.startMainthree(posy, posx);
        this.recursiveGenthree(posy, posx, -1);
        this.fillMaptwo();
    }

    private void fillMaptree() {
        for (int posy = 0; posy < this.scaledsize; posy++)
        {
            for (int posx = 0; posx < this.scaledsize; posx++) {
                if (mapTiles[posy][posx] != null)
                {
                    for (int y = 0; y < 3; y++)
                        if (map.length >= y)
                            for (int x = 0; x < 3; x++)
                                if (map[y].length > x)
                                    map[y + (posy * 3)][x + (posx * 3)] = mapTiles[posy][posx].getCells()[y][x];
                }
                else
                {
                    for (int y = 0; y < 3; y++)
                        if (map.length > y)
                            for (int x = 0; x < 3; x++)
                                if (map[y].length > x)
                                        map[y + (posy * 3)][x + (posx * 3)] = false;
                }
            }
        }
    }

    private void fillMaptwo() {
        for (int posy = 0; posy < this.scaledsize; posy++)
        {
            for (int posx = 0; posx < this.scaledsize; posx++) {
                if (mapTiles[posy][posx] != null)
                {
                    for (int y = 0; y < 3; y++)
                    {
                        if (map.length > y + ((posy) * 3) - (size % 3) && y + ((posy) * 3) - (size % 3) >= 0)
                        {
                            for (int x = 0; x < 3; x++)
                            {
                                if (map[y].length > x + ((posx) * 3) - (size % 3) && x + ((posx) * 3) - (size % 3) >= 0)
                                     map[y + ((posy) * 3) - (size % 3)][x + ((posx) * 3) - (size % 3)] = mapTiles[posy][posx].getCells()[y][x];
                            }
                        }
                    }
                }
                else
                {
                    for (int y = 0; y < 3; y++)
                    {
                        if (map.length > y + ((posy) * 3) - (size % 3) && y - (size % 3) >= 0)
                        {
                            for (int x = 0; x < 3; x++)
                            {
                                if (map[y].length > x + ((posx) * 3) - (size % 3) && x - (size % 3) >= 0)
                                    map[y + ((posy) * 3) - (size % 3)][x + ((posx) * 3) - (size % 3)] = false;
                            }
                        }
                        
                    }
                }
            }
        }
    }

    private void startMainthree(int posy, int posx) {
        int []directions = {Tiles.TOP, Tiles.RIGHT, Tiles.BOTTOM, Tiles.LEFT};

        mainpath[posy][posx] = (byte)directions[TrueRandom.getrandom(4)];
        switch(mainpath[posy][posx]) {
            case Tiles.TOP:
                mainThree(posy - 1, posx, Tiles.BOTTOM);
                break ;
            case Tiles.RIGHT:
                mainThree(posy, posx + 1, Tiles.LEFT);
                break ;
            case Tiles.BOTTOM:
                mainThree(posy + 1, posx, Tiles.TOP);
                break ;
            case Tiles.LEFT:
                mainThree(posy, posx - 1, Tiles.RIGHT);
                break ;
        }
        mapTiles[posy][posx] = Tiles.EMPTY;
    }

    private int mainThree(int posy, int posx, int previous) {
        int []directions = {Tiles.TOP, Tiles.RIGHT, Tiles.BOTTOM, Tiles.LEFT};
        boolean flag = false;

        if (posy < 0 || posx < 0 || posy >= scaledsize || posx >= scaledsize)
            return (0);

        mainpath[posy][posx] += previous;
        if (mainpath[posy][posx] == 15)
        {
            mainpath[posy][posx] -= previous;
            return (-1);
        }
            
        directions = Arrays.stream(directions).filter(direction -> (mainpath[posy][posx] & direction) == 0 && (avoidpath[posy][posx] & direction) == 0).toArray();

        for (int i = directions.length; i > 0; i--) {
            int towhere = directions[TrueRandom.getrandom(directions.length)];

            switch(towhere) {
                case Tiles.TOP:
                    mainpath[posy][posx] += Tiles.TOP;
                    if (mainThree(posy - 1, posx, Tiles.BOTTOM) == -1)
                    {
                        mainpath[posy][posx] -= Tiles.TOP;
                        avoidpath[posy][posx] += Tiles.TOP;
                        break ;
                    }
                    flag = true;
                    break ;
                case Tiles.RIGHT:
                    mainpath[posy][posx] += Tiles.RIGHT;
                    if (mainThree(posy, posx + 1, Tiles.LEFT) == -1)
                    {
                        mainpath[posy][posx] -= Tiles.RIGHT;
                        avoidpath[posy][posx] += Tiles.RIGHT;
                        break ;
                    }
                    flag = true;
                    break ;
                case Tiles.BOTTOM:
                    mainpath[posy][posx] += Tiles.BOTTOM;
                    if (mainThree(posy + 1, posx, Tiles.TOP) == -1)
                    {
                        mainpath[posy][posx] -= Tiles.BOTTOM;
                        avoidpath[posy][posx] += Tiles.BOTTOM;
                        break ;
                    }
                    flag = true;
                    break ;
                case Tiles.LEFT:
                    mainpath[posy][posx] += Tiles.LEFT;
                    if (mainThree(posy, posx - 1, Tiles.RIGHT) == -1)
                    {
                        mainpath[posy][posx] -= Tiles.LEFT;
                        avoidpath[posy][posx] += Tiles.LEFT;
                        break ;
                    }
                    flag = true;
                    break ;
            }
            if (flag == true)
            {
                if ((posy < 1 || posx < 1 || posy >= scaledsize - 1 || posx >= scaledsize - 1) && size % 3 != 0)
                    mapTiles[posy][posx] = TileGenerator.getRandomSpecConnect(0, mainpath[posy][posx], Integer.bitCount(mainpath[posy][posx]), (byte)Tiles.SPECIAL_POOL_ID);
                else
                    mapTiles[posy][posx] = TileGenerator.getRandomSpecConnect(0, mainpath[posy][posx], Integer.bitCount(mainpath[posy][posx]), pool);
                return (0);
            }
            directions = Arrays.stream(directions).filter(direction -> (mainpath[posy][posx] & direction) == 0 && (avoidpath[posy][posx] & direction) == 0 && direction != towhere).toArray();
        }
        mainpath[posy][posx] -= previous;
        return (-1);
    }

    private void recursiveGenthree(int posy, int posx, int previous) {
        int []directions = {Tiles.TOP, Tiles.RIGHT, Tiles.BOTTOM, Tiles.LEFT};

        if (posy < 0 || posx < 0 || posy >= this.scaledsize - 1 || posx >= this.scaledsize - 1)
            return ;

        if (this.mainpath[posy][posx] == 0)
        {
            if (mapTiles[posy][posx] != null)
                return ;

            if ((posy < 1 || posx < 1 || posy >= scaledsize || posx >= scaledsize) && size % 3 == 1)
            {
                this.mapTiles[posy][posx] = TileGenerator.getRandomSpecConnect(0, previous, 1, (byte)Tiles.SPECIAL_POOL_ID);
                return ;
            }
    
            this.mapTiles[posy][posx] = TileGenerator.getRandomSpecConnect(0, previous, 1, this.pool);
        }

        mainpath[posy][posx] = 0;
        directions = Arrays.stream(directions).filter(direction -> (this.mapTiles[posy][posx].getConnections() & direction) != 0 && direction != previous).toArray();

        if (directions.length == 0)
            return ;

        for (int i = directions.length; i > 0; i--) {
            int towhere = directions[TrueRandom.getrandom(directions.length)];
            if (TrueRandom.getrandom(1000) != 0)
            {
                switch(towhere) {
                    case Tiles.TOP:
                        recursiveGenthree(posy - 1, posx, Tiles.BOTTOM);
                        break ;
                    case Tiles.RIGHT:
                        recursiveGenthree(posy, posx + 1, Tiles.LEFT);
                        break ;
                    case Tiles.BOTTOM:
                        recursiveGenthree(posy + 1, posx, Tiles.TOP);
                        break ;
                    case Tiles.LEFT:
                        recursiveGenthree(posy, posx - 1, Tiles.RIGHT);
                        break ;
                }
            }
            directions = Arrays.stream(directions).filter(direction -> (direction != towhere)).toArray();
        }
    }
    public static void main(String[] args) {
        MapGenerator generator = new MapGenerator();

        generator.generateMap(15);

        boolean[][] map = generator.map;
        System.out.print("o");
        for (int x = 0; x < map[0].length; x++) { 
            System.out.print("-");
        }
        System.out.print("o");
        System.out.println();
        for (int y = 0; y < map.length; y++) {
            System.out.print( "|");
            for (int x = 0; x < map[y].length; x++) {
                System.out.print(map[y][x] ? "░" : "█");
            }
            System.out.print( "|");
            System.out.println();
        }
        System.out.print("o");
        for (int x = 0; x < map[0].length; x++) { 
            System.out.print("-");
        }
        System.out.print("o");
        System.out.println();
    }
}
