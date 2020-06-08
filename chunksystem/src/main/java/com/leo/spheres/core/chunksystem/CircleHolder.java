package com.leo.spheres.core.chunksystem;

import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class CircleHolder {
    private static final Map<Float, CircleHolder> holderMap = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public static CircleHolder getInstance(float outlineRadius) {
        return holderMap.computeIfAbsent(outlineRadius, CircleHolder::new);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final float outlineRadius;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Ellipse2D.Float oasp;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int[] oaspHelper;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int si;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int siHalf;

    // -------------------------------------------------------------------------------------------------------------------------
    private CircleHolder(float outlineRadius) {
        this.outlineRadius = outlineRadius;
        this.oasp = new Ellipse2D.Float(-this.outlineRadius, -this.outlineRadius, this.outlineRadius * 2, this.outlineRadius * 2);
        int ps = AChunkSystem.calcBlockMapperSize(this.outlineRadius);
        int psh = ps / 2;
        int[] oak = new int[ps];
        for (int lY = 0; lY < ps; lY++) {
            AX:
            for (int lX = 0; lX < ps; lX++) {
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
    public final Ellipse2D getOutline() {
        return this.oasp;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
