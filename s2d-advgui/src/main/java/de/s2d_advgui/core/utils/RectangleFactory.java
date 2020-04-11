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
    public final static Rectangle explode(@Nonnull Rectangle pDims, float pPix) {
        float dup = pPix * 2;
        return new Rectangle(pDims.x + pPix, pDims.y + pPix, pDims.width - dup, pDims.height - dup);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    /**
     * 
     * @param pDims
     * @param pPix
     * @return
     */
    @Nonnull
    public final static Rectangle explode(@Nonnull Rectangle pDims, float pPixW, float pPixH) {
        float dupW = pPixW * 2;
        float dupH = pPixH * 2;
        return new Rectangle(pDims.x + pPixW, pDims.y + pPixH, pDims.width - dupW, pDims.height - dupH);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
