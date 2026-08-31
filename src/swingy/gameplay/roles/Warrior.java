package swingy.gameplay.roles;
import swingy.gameplay.Role;
import swingy.gameplay.Stats;

public class Warrior extends Role {
    final private double chp = 500;
    final private double cad = 50;
    final private double carm = 50;
    final private double lhp = 300;
    final private double lad = 30;
    final private double larm = 30;

    Warrior() {
        this.basestats = new Stats(chp, cad, carm);
    }

    Warrior(long level) {
        this.basestats = new Stats(chp + (lhp * (level - 1)), cad + (lad * (level - 1)), carm + (larm * (level - 1)));
    }

    @Override
    protected Stats levelUp() {
        this.basestats = new Stats(basestats.getHp() + this.lhp, basestats.getAd() + this.lad, basestats.getArmor() + this.larm);
        this.setNewlevel();

        return (basestats);
    }
}
