package swingy.gameplay.mapgenerator;

import swingy.gameplay.Coordinates;

public class GMap {
    // May need to make getter eventually, I wanted to make these with const getters, but apparently java doesnt have those :(
    protected boolean[][]   map;
    protected Coordinates   mapsize;
    protected Coordinates   center;
    byte                    pool;

    // mapsize isn't a param just in case i forget that i want to make it so the coordenates are from 0 to mapsize.
    public GMap(boolean[][] pmap, byte ppool, Coordinates pcenter) {
        this.map = new boolean[pmap.length][pmap[0].length];
    
        for (int y = 0; y < pmap.length; y ++)
            for (int x = 0; x < pmap[y].length; x ++)
                    this.map[y][x] = pmap[y][x];

        this.mapsize = new Coordinates(pmap[0].length, pmap.length);
        this.pool = ppool;
        this.center = pcenter;
    }

    public GMap(GMap toCopy) {
        this.map = toCopy.getMap();
        this.mapsize = new Coordinates(toCopy.mapsize);
        this.pool = toCopy.pool;
        this.center = toCopy.center;
    }

    public boolean isInside(Coordinates toCheck) {
        return (toCheck.getx() >= 0 && toCheck.gety() >= 0 && toCheck.getx() < mapsize.getx() && toCheck.gety() < mapsize.gety());
    }

    public boolean hasWall(Coordinates toCheck) {
        if (!isInside(toCheck))
            return (false);
        else if (map[(int)(toCheck.gety())][(int)toCheck.getx()])
            return (false);
        return (true);
    }

    public boolean[][] getMap() {
        boolean [][]toret = new boolean[map.length][map[0].length];
    
        for (int y = 0; y < map.length; y ++)
            for (int x = 0; x < map[y].length; x ++)
                    toret[y][x] = map[y][x];
        return (toret);
    }

    public final Coordinates getCenter() {
        return (new Coordinates(center));
    }
}
