package de.s2d_advgui.core.canvas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BoundingBoxCalc {
    // -------------------------------------------------------------------------------------------------------------------------
    public float minX = Float.MAX_VALUE;
    public float minY = Float.MAX_VALUE;
    public float maxX = Float.MIN_VALUE;
    public float maxY = Float.MIN_VALUE;

    // -------------------------------------------------------------------------------------------------------------------------
    public float relWidth;
    public float relHeight;

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle rr = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    public void reset() {
        this.minX = Float.MAX_VALUE;
        this.minY = Float.MAX_VALUE;
        this.maxX = Float.MIN_VALUE;
        this.maxY = Float.MIN_VALUE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void add(Vector2 jjj) {
        this.minX = Math.min(this.minX, jjj.x);
        this.minY = Math.min(this.minY, jjj.y);
        this.maxX = Math.max(this.maxX, jjj.x);
        this.maxY = Math.max(this.maxY, jjj.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void end() {
        this.relWidth = this.maxX - this.minX;
        this.relHeight = this.maxY - this.minY;

        this.rr.x = this.minX;
        this.rr.y = this.minY;
        this.rr.width = this.maxX - this.minX;
        this.rr.height = this.maxY - this.minY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return this.minX + "|" + this.minY + "-" + this.maxX + "|" + this.maxY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Rectangle rect() {
        return this.rr;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    public float getMaxX() {
        return this.maxX;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMaxY() {
        return this.maxY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMinX() {
        return this.minX;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getMinY() {
        return this.minY;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}