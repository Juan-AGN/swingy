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

    public Tank() {
        this.basestats = new Stats(chp, cad, carm);
    }

    public Tank(long plevel) {
        this.basestats = new Stats(chp + (lhp * plevel), cad + (lad * plevel), carm + (larm * plevel));
        this.level = plevel;
    }

    @Override
    protected Stats levelUp() {
        this.basestats = new Stats(basestats.getHp() + this.lhp, basestats.getAd() + this.lad, basestats.getArmor() + this.larm);
        this.setNewlevel();

        return (basestats);
    }

    @Override
    public Role getRole() {
        return (new Tank(this.level));
    }
}
