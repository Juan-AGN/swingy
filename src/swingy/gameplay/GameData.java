package swingy.gameplay;

import java.util.Vector;

import swingy.gameplay.mapgenerator.GMap;
import swingy.view.Sprite;

public class GameData {
    protected GMap           map;
    protected Unit          player;
    protected EnemyHandler  enemies;

    public GameData(GMap pmap, Unit pplayer) {
        map = new GMap(pmap);
        player = new Unit(pplayer);
        enemies = new EnemyHandler(pplayer, this);
    }

    protected EnemyHandler getWholeEnemyHandler() {
        return (this.enemies);
    }

    public boolean isInside(Coordinates toCheck) {
        return (map.isInside(toCheck));
    }

    public boolean hasWall(Coordinates toCheck) {
        return (map.hasWall(toCheck));
    }

    public boolean hasEnemy(Coordinates where) {
        return (enemies.hasEnemy(where));
    }

    public Sprite getUnitSprite(Coordinates where) {
        return (enemies.getUnitSprite(where));
    }

    public Vector<Coordinates> getEnemyCoords() {
        return (enemies.getEnemyCoords());
    }

    public final Unit getPlayer() {
        return (new Unit(player));
    }

    public final GMap getMap() {
        return (new GMap(map));
    }
}
