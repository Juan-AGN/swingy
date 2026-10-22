package swingy.gameplay;

public class Coordinates {
    protected long x;
    protected long y;

    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public boolean compare(Coordinates toComp) {
        if (this.x == toComp.x)
            if (this.y == toComp.y)
                return (true);
        return  (false);
    }

    public boolean isFullyHigher(Coordinates toComp) {
        if (this.x < toComp.x)
            if (this.y < toComp.y)
                return (true);
        return  (false);
    }

    public boolean isFullyLower(Coordinates toComp) {
        if (this.x > toComp.x)
            if (this.y > toComp.y)
                return (true);
        return  (false);
    }

    public long getx() {
        return (this.x);
    }

    public long gety() {
        return (this.y);
    }

    protected void set(Coordinates toSet) {
        this.x = toSet.x;
        this.y = toSet.y;
    }

    protected void set(int tosetx, int tosety) {
        this.x = tosetx;
        this.y = tosety;
    }
}