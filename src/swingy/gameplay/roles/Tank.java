package swingy.gameplay.roles;
import swingy.gameplay.Role;
import swingy.gameplay.Stats;

public class Tank extends Role {
    final private double chp = 300;
    final private double cad = 30;
    final private double carm = 70;
    final private double lhp = 100;
    final private double lad = 10;
    final private double larm = 30;

    Tank() {
        this.basestats = new Stats(chp, cad, carm);
    }

    Tank(long level) {
        this.basestats = new Stats(chp + (lhp * level), cad + (lad * level), carm + (larm * level));
    }

    @Override
    protected Stats levelUp() {
        this.basestats = new Stats(basestats.getHp() + this.lhp, basestats.getAd() + this.lad, basestats.getArmor() + this.larm);
        this.setNewlevel();

        return (basestats);
    }
}
