package com.leo.spheres.core.chunksystem;

import com.badlogic.gdx.math.Circle;

public class CircleBrush {
    // -------------------------------------------------------------------------------------------------------------------------
    public static CircleBrush getInstance(float outlineRadius) {
        return new CircleBrush(outlineRadius);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final float outlineRadius;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Circle oasp;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int[] oaspHelper;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int si;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int siHalf;

    // -------------------------------------------------------------------------------------------------------------------------
    public CircleBrush(float outlineRadius) {
        this.outlineRadius = outlineRadius;
        this.oasp = new Circle(0, 0, this.outlineRadius);
        int ps = AChunkSystem.calcBlockMapperSize(this.outlineRadius);
        int psh = ps / 2;
        int[] oak = new int[ps];
        for (int lY = 0; lY < ps; lY++) {
            AX: for (int lX = 0; lX < ps; lX++) {
                if (this.oasp.contains(lX - psh, lY - psh)) {
                    oak[lY] = lX;
                    break AX;
                }
                oak[lY] = -1;
            }
        }
        this.oaspHelper = oak;
        this.si = this.oaspHelper.length;
        this.siHalf = this.si / 2;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean inCircle(int pX, int pY) {
        pY += this.siHalf;
        if (pY < 0 || pY >= this.si) return false;

        int st = this.oaspHelper[pY];
        if (st == -1) return false;

        pX += this.siHalf;
        return pX >= st && pX <= this.si - st;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final Circle getOutline() {
        return this.oasp;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
