package de.s2d_advgui.core.geom;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.utils.MinMax;

public class Ray2D implements Shape2D {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Vector2 tmp1 = new Vector2();

    // -------------------------------------------------------------------------------------------------------------------------
    public float x1;
    public float y1;
    public float x2;
    public float y2;

    // -------------------------------------------------------------------------------------------------------------------------
    private Rectangle bnds = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public Ray2D() {
        this.x1 = this.y1 = this.x2 = this.y2 = 0f;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Ray2D(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void getTranslatedInstance(@Nonnull Affine2 pAffine, @Nonnull Ray2D pTarget) {
        this.tmp1.set(this.x1, this.y1);
        pAffine.applyTo(this.tmp1);
        pTarget.x1 = this.tmp1.x;
        pTarget.y1 = this.tmp1.y;
        this.tmp1.set(this.x2, this.y2);
        pAffine.applyTo(this.tmp1);
        pTarget.x2 = this.tmp1.x;
        pTarget.y2 = this.tmp1.y;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Ray2D getTranslatedInstance(Affine2 pAffine) {
        Ray2D back = new Ray2D();
        this.getTranslatedInstance(pAffine, back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(Vector2 point) {
        return this.contains(point.x, point.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(float x, float y) {
        if (this.x1 == x && this.y1 == y) {
            return true;
        }
        if (this.x2 == x && this.y2 == y) {
            return true;
        }
        float dist = Intersector.distanceSegmentPoint(this.x1, this.y1, this.x2, this.y2, x, y);
        return dist < 0.1f;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Rectangle getBounds() {
        if (this.bnds == null) {
            MinMax mm = new MinMax();
            mm.append(this.x1, this.y1);
            mm.append(this.x2, this.y2);
            mm.reCalcWiHe();
            this.bnds = mm.toRect();
        }
        return this.bnds;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "Ray2D[" + this.x1 + "," + this.y1 + "|" + this.x2 + "," + this.y2 + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
