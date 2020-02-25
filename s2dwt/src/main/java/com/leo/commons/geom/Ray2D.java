package com.leo.commons.geom;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import javax.annotation.Nonnull;

public class Ray2D {

    private final Vector2 tmp1 = new Vector2();
    public float x1 = 0;
    public float y1 = 0;
    public float x2 = 0;
    public float y2 = 0;

    public Ray2D() {
        // DON
    }

    public Ray2D(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

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

}
