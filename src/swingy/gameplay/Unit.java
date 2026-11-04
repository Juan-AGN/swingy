package swingy.gameplay;

import java.util.ArrayList;
import java.util.logging.Level;

public class Unit {
    protected Role unitrole;
    protected Coordinates where;
    protected double currenthp;
    protected double cooldown = 0;
    protected double basecooldown = 50;

    // My tame is Pteo Uoz, son of Ian Oxisa and distant relative of Eih Ajzeveñiat. (examples of random names, the name is generated totally randomly, sadly, i cant censor bad names without adding them to the code, so i'll just pray no bad names show up)
    protected  String name = "";

    private byte id;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Item helm;
    private Item armor;
    private Item weapon;
    private Behavior type;

    public Unit(byte pid, String pname, Role punitrole, Behavior ptype) {
        id = pid;
        name = pname;
        where = new Coordinates(0, 0);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
        type = ptype;
    }

    public Unit(byte pid, String pname, Role punitrole, Coordinates pwhere, Behavior ptype) {
        id = pid;
        name = pname;
        where = new Coordinates(pwhere);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
        type = ptype;
    }

    public Unit(byte pid, String pname, Role punitrole, double cd, boolean active, Behavior ptype) {
        id = pid;
        name = pname;
        where = new Coordinates(0, 0);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
        basecooldown = cd;
        cooldown = active ? 0 : basecooldown;
        type = ptype;
    }

    public Unit(byte pid, String pname, Role punitrole, Coordinates pwhere, double cd, boolean active, Behavior ptype) {
        id = pid;
        name = pname;
        where = new Coordinates(pwhere);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
        basecooldown = cd;
        cooldown = active ? 0 : basecooldown;
        type = ptype;
    }

    public Unit(Unit toCopy) {
        unitrole = toCopy.unitrole.getRole();
        where = new Coordinates(toCopy.getX(), toCopy.getY());
        currenthp = toCopy.currenthp;
        name = toCopy.name;
        id = toCopy.id;
        type = toCopy.type;
    }

    protected double getHit(double oatk) {
        double dmg = oatk - unitrole.getStats().getArmor();

        this.currenthp -= (dmg > 0) ? dmg : 1;
        return (currenthp);
    };

    public boolean activity(double ms, boolean disable) {
        this.cooldown -= ms;
        if (cooldown > 0)
            return (false);
        if (disable)
            cooldown = basecooldown;
        return (true);
    }

    public void resetActivity() {
        this.cooldown = basecooldown;
    }


    public int activity(double ms, GameData gamestate, boolean[][] map) {
        this.cooldown -= ms;
        if (cooldown > 0)
            return (-1);
        this.cooldown = this.basecooldown;
        return (this.move(gamestate, map));
    }

    private void moveup(GameData gameState) {
        if (!gameState.hasWall(new Coordinates(this.where.x, this.where.y - 1)))
            this.where = new Coordinates(this.where.x, this.where.y - 1);
    }

    private void movedown(GameData gameState) {
        if (!gameState.hasWall(new Coordinates(this.where.x, this.where.y + 1)))
            this.where = new Coordinates(this.where.x, this.where.y + 1);    
    }

    private void moveleft(GameData gameState) {
        if (!gameState.hasWall(new Coordinates(this.where.x - 1, this.where.y)))
            this.where = new Coordinates(this.where.x - 1, this.where.y);
    }

    private void moveright(GameData gameState) {
        if (!gameState.hasWall(new Coordinates(this.where.x + 1, this.where.y)))
            this.where = new Coordinates(this.where.x + 1, this.where.y);
    }

    public int move(GameData gameState, boolean[][] map) {
        byte direction;

        if (gameState == null)
            return (-1);

        if (map == null)
            direction = this.type.getDirection(gameState, null, this.where);
        else
            direction = this.type.getDirection(gameState, map, this.where);

        switch (direction) {
            case 1:
                moveup(gameState);
                return (1);
            case 2:
                moveleft(gameState);
                return (2);
            case 3:
                movedown(gameState);
                return (3);
            case 4:
                moveright(gameState);
                return (4);  
            default:
                return (direction);
        }
    }

    public long getX() {
        return (where.x);
    }

    public long getY() {
        return (where.y);
    }

    public byte getId() {
        return (id);
    }

    public Double getCurenthp() {
        return (this.currenthp);
    }

    public Double getDamage() {
        return (this.unitrole.getAttack());
    }

    public Stats getStats() {
        return (this.unitrole.getStats());
    }

    public long getLevel() {
        return (this.unitrole.level);
    }

    public void levelUp() {
        this.unitrole.levelUp();
    }

    public String getName() {
        return (name);
    }

    public Unit getUnit() {
        return (new Unit(this));
    }

    public Coordinates getCoords() {
        return (new Coordinates(this.where));
    }
}