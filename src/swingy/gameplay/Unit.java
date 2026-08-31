package swingy.gameplay;

public class Unit {
    protected Role unitrole;
    protected double exp = 0;
    protected double tolevel = 100;
    protected double currenthp;

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
}