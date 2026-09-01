package swingy.gameplay.roles;
import swingy.gameplay.Role;
import swingy.gameplay.Stats;

public class Assasin extends Role {
    final private double chp = 150;
    final private double cad = 100;
    final private double carm = 20;
    final private double lhp = 25;
    final private double lad = 25;
    final private double larm = 10;

    Assasin() {
        this.basestats = new Stats(chp, cad, carm);
    }

    Assasin(long level) {
        this.basestats = new Stats(chp + (lhp * level), cad + (lad * level), carm + (larm * level));
    }

    @Override
    protected Stats levelUp() {
        this.basestats = new Stats(basestats.getHp() + this.lhp, basestats.getAd() + this.lad, basestats.getArmor() + this.larm);
        this.setNewlevel();

        return (basestats);
    }
}
