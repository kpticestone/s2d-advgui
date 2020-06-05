package com.leo.spheres.core.chunksystem;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.Ray2D;

public final class RayBlockHandler implements Iterable<int[]> {
    // -------------------------------------------------------------------------------------------------------------------------
    // private final Set<Point> pnt = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private Ray2D ray;

    // -------------------------------------------------------------------------------------------------------------------------
    private float length;

    // -------------------------------------------------------------------------------------------------------------------------
    private float sunDirection;

    private final Vector2 src = new Vector2();

    private float len;

    private Vector2 nn;

    // -------------------------------------------------------------------------------------------------------------------------
    public RayBlockHandler(Vector2 src, float length, float sunDirection) {
        this.length = length;
        this.sunDirection = sunDirection;
        this.setSrc(src);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public RayBlockHandler(float planetSize, float sunDirection) {
        this.length = planetSize;
        this.sunDirection = sunDirection;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSrc(Vector2 src) {
        this.setSrc(src.x, src.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSrc(float x, float y) {
        if (this.ray == null || this.src.x != x || this.src.y != y) {
            // this.pnt.clear();
            this.src.x = x;
            this.src.y = y;
            Vector2 v0 = new Vector2();
            Vector2 v1 = new Vector2(this.length, 0);
            v1.rotate(this.sunDirection);
            this.ray = new Ray2D(new Vector2(v0).add(src), new Vector2(v1).add(src));
            len = v0.dst(v1);
            nn = v1.nor();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Ray2D getRay() {
        return this.ray;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Iterator<int[]> iterator() {
        float[] curI = { 0f };
        int[] ax = { 0, 0 };
        int[] ox = { 0, 0 };
        return new Iterator<>() {
            @Override
            public int[] next() {
                return ax;
            }

            @Override
            public boolean hasNext() {
                boolean changed = false;
                if (curI[0] <= len) {
                    while (!changed) {
                        ox[0] = MathUtils.floor(src.x + nn.x * curI[0]);
                        ox[1] = MathUtils.floor(src.y + nn.y * curI[0]);
                        if (curI[0] == 0 || ox[0] != ax[0] || ox[1] != ax[1]) {
                            ax[0] = ox[0];
                            ax[1] = ox[1];
                            changed = true;
                        }
                        curI[0] += .25f;
                    }
                    return true;
                }
                return false;
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}