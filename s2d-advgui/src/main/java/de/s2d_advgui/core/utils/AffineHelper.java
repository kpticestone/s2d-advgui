package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Affine2;

public final class AffineHelper {
    // -------------------------------------------------------------------------------------------------------------------------
    public final static Affine2 getTranslate(float x, float y) {
        Affine2 back = new Affine2();
        back.translate(x, y);
        return back;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    private AffineHelper() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
