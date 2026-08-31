package swingy.gameplay;

public abstract class Role{
    protected Stats basestats;
    protected long level = 1;

    protected abstract Stats levelUp();

    public Stats getStats() {
        return (this.basestats.getStats());
    }

    protected double getAttack() {
        return (this.basestats.getAd());
    };


}