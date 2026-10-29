package swingy.gameplay;
import java.util.Vector;
import swingy.gameplay.mapgenerator.GMap;;

public class GameData {
    protected GMap           map;
    protected Vector<Unit>  units;
    protected Unit          player;

    public GameData(GMap pmap, Unit pplayer) {
        map = new GMap(pmap);
        player = new Unit(pplayer);
    }

    public boolean isInside(Coordinates toCheck) {
        return (map.isInside(toCheck));
    }

    public boolean hasWall(Coordinates toCheck) {
        return (map.hasWall(toCheck));
    }

    public final Unit getPlayer() {
        return (new Unit(player));
    }

    public final GMap getMap() {
        return (new GMap(map));
    }
}
