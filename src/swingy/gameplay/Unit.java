package swingy.gameplay;

import java.util.ArrayList;
import java.util.logging.Level;

public class Unit {
    protected Role unitrole;
    protected Coordinates where;
    protected double currenthp;

    // My tame is Pteo Uoz, son of Ian Oxisa and distant relative of Eih Ajzeveñiat. (examples of random names, the name is generated totally randomly, sadly, i cant censor bad names without adding them to the code, so i'll just pray no bad names show up)
    protected  String name = "";

    private Long id;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Item helm;
    private Item armor;
    private Item weapon;
    private byte type;

    public Unit(long pid, String pname, Role punitrole) {
        id = pid;
        name = pname;
        where = new Coordinates(0, 0);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
    }

    public Unit(long pid, String pname, Role punitrole, Coordinates pwhere) {
        id = pid;
        name = pname;
        where = new Coordinates(pwhere);
        unitrole = punitrole;
        currenthp = unitrole.getStats().getHp();
    }

    public Unit(Unit toCopy) {
        unitrole = toCopy.unitrole.getRole();
        where = new Coordinates(toCopy.getX(), toCopy.getY());
        currenthp = toCopy.currenthp;
        name = toCopy.name;
        id = toCopy.id;
    }

    protected double getHit(double oatk) {
        double dmg = oatk - unitrole.getStats().getArmor();

        this.currenthp -= (dmg > 0) ? dmg : 1;
        return (currenthp);
    };

    public long getX() {
        return (where.x);
    }

    public long getY() {
        return (where.y);
    }

    public Double getCurenthp() {
        return (this.currenthp);
    }

    public Double getDamage() {
        return (this.getDamage());
    }

    public Stats getStats() {
        return (this.getStats());
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