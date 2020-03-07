package de.s2d_advgui.core.utils;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Rectangle;

public final class RectangleFactory {
    // -------------------------------------------------------------------------------------------------------------------------
    private RectangleFactory() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param pDims
     * @param pPix
     * @return
     */
    @Nonnull
    public final static Rectangle explode(@Nonnull Rectangle pDims, int pPix) {
        int dup = pPix * 2;
        return new Rectangle(pDims.x + pPix, pDims.y + pPix, pDims.width - dup, pDims.height - dup);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
