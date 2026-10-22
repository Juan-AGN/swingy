package swingy.gameplay;

import java.util.ArrayList;

public class Unit {
    protected Role unitrole;
    protected Coordinates where;
    protected double currenthp;

    // My tame is Pteo Uoz, son of Ian Oxisa and distant relative of Eih Ajzeveñiat. (examples of random names, the name is generated totally randomly, sadly, i cant censor bad names without adding them to the code, so i'll just pray no bad names show up)
    private String name = "";

    private Long id;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Item helm;
    private Item armor;
    private Item weapon;

    protected double getHit(double oatk) {
        double dmg = oatk - unitrole.getStats().getArmor();

        this.currenthp -= (dmg > 0) ? dmg : 1;
        return (currenthp);
    };

    public Double getCurenthp() {
        return (this.currenthp);
    }

    public Double getDamage() {
        return (this.getDamage());
    }

    public Stats getStats() {
        return (this.getStats());
    }

    public String getName() {
        return (name);
    }
}