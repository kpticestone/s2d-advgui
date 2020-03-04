package de.s2d_advgui.core.geom.collider;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MinMax {
    private boolean empty = true;
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    private float width = 0;
    private float height = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("null")
    public void append(@Nonnull Rectangle box) {
        float boxX2 = box.x + box.width;
        float boxY2 = box.y + box.height;
        float boxX = box.x;
        float boxY = box.y;

        apply(boxX, boxY);
        apply(boxX2, boxY2);

        this.reCalcWiHe();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void append(float x, float y) {
        apply(x, y);

        this.reCalcWiHe();
    }

    private void apply(float x, float y) {
        if (empty) {
            this.x1 = x;
            this.x2 = x;
            this.y1 = y;
            this.y2 = y;
            this.empty = false;
        } else {
            this.x1 = Math.min(this.x1, x);
            this.x2 = Math.max(this.x2, x);
            this.y1 = Math.min(this.y1, y);
            this.y2 = Math.max(this.y2, y);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void reCalcWiHe() {
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getHeight() {
        return this.height;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getWidth() {
        return this.width;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(Vector2 pPoint) {
        if (empty) return false;
        if (pPoint.x < this.x1) return false;
        if (pPoint.x > this.x2) return false;
        if (pPoint.y < this.y1) return false;
        return !(pPoint.y > this.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(float x, float y) {
        if (empty) return false;
        if (x < this.x1) return false;
        if (x > this.x2) return false;
        if (y < this.y1) return false;
        return !(y > this.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean overlaps(@Nonnull MinMax pOther) {
        if (empty) return false;
        return this.x1 < pOther.x2 && this.x2 > pOther.x1 && this.y1 < pOther.y2 && y2 > pOther.y1;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public Rectangle toRect() {
        return new Rectangle(this.x1, this.y1, this.width, this.height);
    }

    // -------------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder back = new StringBuilder();
        back.append(this.x1);
        back.append("-");
        back.append(this.y1);
        back.append("-");
        back.append(this.x2);
        back.append("-");
        back.append(this.y2);
        back.append(" (");
        back.append(this.width);
        back.append("x");
        back.append(this.height);
        back.append(")");
        return back.toString();
    }
}
