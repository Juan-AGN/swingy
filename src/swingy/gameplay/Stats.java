package swingy.gameplay;

public class Stats {
    private double hp;
    private double ad;
    private double armor;

    public Stats(double php, double pad, double parmor) {
        hp = php;
        ad = pad;
        armor = parmor;
    }

    public double getAd() {
        return (ad);
    }

    public double getArmor() {
        return (armor);
    }

    public double getHp() {
        return (hp);
    }

    public Stats getStats() {
        return (new Stats(hp, ad, armor));
    }
}