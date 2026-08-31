package swingy.gameplay;

public abstract class Role{
    protected Stats basestats;
    protected long level = 1;
    protected double exp = 0;
    protected double tolevel = 1000;

    protected abstract Stats levelUp();

    public Stats getStats() {
        return (this.basestats.getStats());
    }

    protected double getAttack() {
        return (this.basestats.getAd());
    };

    protected void setNewlevel() {
        level += 1;
        exp = exp - tolevel;
        tolevel = (level * 1000) + (((level - 1) * (level - 1)) * 450);
    }
}