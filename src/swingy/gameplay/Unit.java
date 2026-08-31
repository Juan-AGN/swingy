package swingy.gameplay;

import java.util.ArrayList;

public class Unit {
    protected Role unitrole;

    protected double currenthp;
    private String name = "";
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