package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;

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
    public static boolean equals(Matrix4 m1, Matrix4 m2) {
        if (m1 == m2) return true;
        if (m1 == null || m2 == null) return false;
        for (int i = 0; i < m1.val.length; i++) {
            if (m1.val[i] != m2.val[i]) return false;
        }
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
