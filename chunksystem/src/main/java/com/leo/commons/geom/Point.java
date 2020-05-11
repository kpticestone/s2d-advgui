package com.leo.commons.geom;

import com.badlogic.gdx.math.Vector2;

/**
 * just a simple position-entity but with integers
 */
public class Point {
    //    public static WeakHashMap<Point, Point> pointMap = new WeakHashMap<>();
    //
    //    //TODO Improve performance somehow
    //    public static Point get(int x, int y) {
    //        Point p = new Point(x, y);
    //        return pointMap.computeIfAbsent(p, k -> p);
    //    }

    public final int x;
    public final int y;
    private final int hash;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.hash = x * 31 + y;
    }

    public final static boolean areEqual(Point a, Point b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.x == b.x && a.y == b.y;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point
                && ((Point) obj).x == this.x
                && ((Point) obj).y == this.y;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "Point[" + this.x + "|" + this.y + "]";
    }

    public Point sub(Point p2) {
        return new Point(x - p2.x, y - p2.y);
    }

    public Vector2 toVector() {
        return new Vector2(x, y);
    }
}
