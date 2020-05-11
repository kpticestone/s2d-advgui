package com.leo.commons.utils;

import com.leo.commons.geom.Point;
import de.s2d_advgui.commons.Pnt;

import java.awt.geom.Ellipse2D;
import java.util.LinkedHashSet;
import java.util.Set;

public class Brush {
    public static final Set<Point> neighborPoints4 = new LinkedHashSet<>();
    public static final Set<Point> neighborPointsDiagonal4 = new LinkedHashSet<>();
    public static final Set<Point> neighborPoints8 = new LinkedHashSet<>();

    static {
        neighborPoints4.add(new Point(0, -1));
        neighborPoints4.add(new Point(1, 0));
        neighborPoints4.add(new Point(0, 1));
        neighborPoints4.add(new Point(-1, 0));
    }

    static {
        neighborPoints8.add(new Point(0, -1));
        neighborPoints8.add(new Point(1, -1));
        neighborPoints8.add(new Point(1, 0));
        neighborPoints8.add(new Point(1, 1));
        neighborPoints8.add(new Point(0, 1));
        neighborPoints8.add(new Point(-1, 1));
        neighborPoints8.add(new Point(-1, 0));
        neighborPoints8.add(new Point(-1, -1));
    }

    static {
        neighborPointsDiagonal4.add(new Point(1, -1));
        neighborPointsDiagonal4.add(new Point(1, 1));
        neighborPointsDiagonal4.add(new Point(-1, 1));
        neighborPointsDiagonal4.add(new Point(-1, -1));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean[][] arrs;
    private int size;

    // -------------------------------------------------------------------------------------------------------------------------
    public Brush(int pSize) {
        this.size = pSize;
        this.arrs = new boolean[this.size][this.size];

        Ellipse2D.Float okla = new Ellipse2D.Float(0, 0, this.size - 1, this.size - 1);
        for (int y = 0; y < this.size; y++) {
            this.arrs[this.size / 2][y] = true;
            this.arrs[y][this.size / 2] = true;
            for (int x = 0; x < this.size; x++) {
                if (okla.contains(x, y)) {
                    this.arrs[x][y] = true;
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            Brush br = new Brush(21);
            br.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(Pnt pnt) {
        int halfSize = this.size / 2;
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                if (this.arrs[x][y]) {
                    pnt.accept(x - halfSize, y - halfSize);
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void print() {
        StringBuilder back = new StringBuilder();
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                back.append(this.arrs[x][y] ? "*" : "#"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            back.append("\n"); //$NON-NLS-1$
        }
        System.err.println(back.toString());
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
