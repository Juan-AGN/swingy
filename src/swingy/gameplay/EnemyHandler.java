package swingy.gameplay;

import java.util.Vector;

import swingy.gameplay.behaviors.Jester;
import swingy.gameplay.roles.Assasin;
import swingy.gameplay.roles.Tank;
import swingy.gameplay.roles.Warrior;
import swingy.utils.NameGenerator;
import swingy.utils.TrueRandom;
import swingy.view.Sprite;
import swingy.view.Sprites;

import java.util.Set;
import java.util.HashSet;

public class EnemyHandler {
    protected Vector<Unit>  units = new Vector<Unit>();
    protected Set<Byte> ids = new HashSet<>();
    private byte maxEnemy = 100;
    private int spawnCooldown = 5000;
    private int cooldown = 5000;
    private Unit player;
    private boolean[][] tmap;

    public EnemyHandler(Unit pplayer, GameData game) {
        for (byte i = 1; i <= maxEnemy; i ++)
            ids.add(i);
        player = pplayer;
        units.add(game.player);
        tmap = game.map.getMap();
    }

    private Vector<Unit> getWholeEnemy(Coordinates where) {
        Vector<Unit> toret = new Vector<Unit>();

        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).where.compare(where))
                toret.add(new Unit(units.elementAt(i)));
        
        return (toret);
    }

    private Unit getWholeEnemy(byte pid) {
        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).getId() == pid)
                return (new Unit(units.elementAt(i)));
        
        return (null);
    }

    public Vector<Unit> getEnemy(Coordinates where) {
        Vector<Unit> toret = new Vector<Unit>();

        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).where.compare(where))
                toret.add(new Unit(units.elementAt(i)));
        
        return (toret);
    }

    public Unit getEnemy(byte pid) {
        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).getId() == pid)
                return (new Unit(units.elementAt(i)));
        
        return (null);
    }

    public boolean hasEnemy(Coordinates where) {
        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).where.compare(where))
                return (true);
        
        return (false);
    }

    public boolean hasEnemy(byte pid) {
        for (int i = 0; i < units.size(); i ++)
            if (units.elementAt(i).getId() == pid)
                return (true);
        
        return (false);
    }

    public void spawn(GameData game) {
        cooldown = spawnCooldown;
        if (ids.size() < 1)
            return ;
        Coordinates spawn = new Coordinates(game.player.where.getx() - (TrueRandom.getrandom(16) - 8), game.player.where.gety() - (TrueRandom.getrandom(16) - 8));
        Role setRole;
        Behavior setBehavior;
        if (!game.map.isInside(spawn) || game.map.hasWall(spawn))
            return ;
        int level = TrueRandom.getrandom((int)player.getLevel()) + 1;
        byte sid = ids.iterator().next();
        ids.remove(sid);

        switch(TrueRandom.getrandom(3)) {
            case 0:
                setRole = new Tank(level);
                break ;
            case 1:
                setRole = new Assasin(level);
                break ;
            case 2:
                setRole = new Warrior(level);
                break ;
            default:
                setRole = new Warrior(level);
        }

        switch(TrueRandom.getrandom(1)) {
            default:
                setBehavior = new Jester();
        }

        units.add(new Unit(sid, NameGenerator.generateName(), setRole, spawn, 300, false, setBehavior));
    }

    public void removeUnit(Unit who) {
        if (units.remove(who)) {
            ids.add(who.getId());
        }
    }

    public void frame(int ms, GameData game) {
        for (int i = 0; i < units.size(); i ++)
            if (!game.isInside(units.elementAt(i).where) && units.elementAt(i).getId() != 0)
                removeUnit(units.elementAt(i));
        cooldown -= ms;
        if (cooldown <= 0)
            spawn(game);

        for (int i = 0; i < units.size(); i ++)
            units.elementAt(i).activity(ms, game, tmap);
    }

    public Sprite getUnitSprite(Coordinates where) {
        Vector<Unit> who = this.getWholeEnemy(where);

        if (who.size() < 1)
            return (null);

        if (who.size() > 1)
            return (Sprites.getInstance().allSprites[Sprites.TREE]);

        if (who.elementAt(0).getId() == 0)
            return (Sprites.getInstance().allSprites[who.elementAt(0).unitrole.getSpriteId(true)]);
        return (Sprites.getInstance().allSprites[who.elementAt(0).unitrole.getSpriteId(false)]);
    }
}
