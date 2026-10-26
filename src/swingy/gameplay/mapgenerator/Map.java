package swingy.gameplay.mapgenerator;

import swingy.gameplay.Coordinates;

public class Map {
    // May need to make getter eventually, I wanted to make these with const getters, but apparently java doesnt have those :(
    protected boolean[][]   map;
    protected Coordinates   mapsize;
    protected Coordinates   center;
    byte                    pool;

    // mapsize isn't a param just in case i forget that i want to make it so the coordenates are from 0 to mapsize.
    public Map(boolean[][] pmap, byte ppool, Coordinates pcenter) {
        this.map = pmap;
        this.mapsize = new Coordinates(pmap[0].length, pmap.length);
        this.pool = ppool;
        this.center = pcenter;
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

    public final boolean[][] getMap() {
        return (map);
    }

    public final Coordinates getCenter() {
        return (center);
    }
}
