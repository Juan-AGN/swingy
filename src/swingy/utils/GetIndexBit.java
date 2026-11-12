package swingy.utils;

import swingy.gameplay.Coordinates;

public final class GetIndexBit {
    static public int getIndex(long x, long y, long length) {
        return ((int)(y * length + x));
    }

    static public int getIndex(Coordinates where, long length) {
        return ((int)(where.gety() * length + where.getx()));
    }
}
