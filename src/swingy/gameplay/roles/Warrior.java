package swingy.gameplay.roles;
import swingy.gameplay.Role;
import swingy.gameplay.Stats;

public class Warrior extends Role {
    final private double chp = 250;
    final private double cad = 50;
    final private double carm = 50;
    final private double lhp = 100;
    final private double lad = 20;
    final private double larm = 20;

    public Warrior() {
        this.basestats = new Stats(chp, cad, carm);
    }

    public Warrior(long plevel) {
        this.basestats = new Stats(chp + (lhp * (plevel - 1)), cad + (lad * (plevel - 1)), carm + (larm * (plevel - 1)));
        this.level = plevel;
    }

    @Override
    protected Stats levelUp() {
        this.basestats = new Stats(basestats.getHp() + this.lhp, basestats.getAd() + this.lad, basestats.getArmor() + this.larm);
        this.setNewlevel();

        return (basestats);
    }
}
