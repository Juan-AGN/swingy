package swingy.gameplay;
import java.util.Vector;
import swingy.gameplay.mapgenerator.Map;;

public class GameData {
    protected Map           map;
    protected Vector<Unit>  units;
    protected Unit          player;

    public GameData(Map pmap, Unit pplayer) {
        map = pmap;
        player = pplayer;
    }
}
