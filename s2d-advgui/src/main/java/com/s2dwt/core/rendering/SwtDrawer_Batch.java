package com.s2dwt.core.rendering;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.leo.commons.utils.TNull;
import com.s2dwt.core.resourcemanager.AResourceManager;

public final class SwtDrawer_Batch<RM extends AResourceManager> extends ASwtDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Batch batch;

    // -------------------------------------------------------------------------------------------------------------------------
    private final TextureRegion white;

    // -------------------------------------------------------------------------------------------------------------------------
    private final boolean active;

    // -------------------------------------------------------------------------------------------------------------------------
    private final RM resourceManager;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawer_Batch(@Nonnull RM pResourceManager, @Nonnull Batch pBatch) {
        this.resourceManager = pResourceManager;
        this.white = pResourceManager.getColorTextureRegion(Color.WHITE);
        this.batch = pBatch;
        this.active = pBatch.isDrawing();
        if (!this.active) this.batch.begin();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public RM getResourceManager() {
        return this.resourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public Batch getBatch() {
        return this.batch;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void close() {
        if (!this.active) this.batch.end();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(@Nonnull TextureRegion res, float x, float y, float w, float h) {
        this.batch.draw(res, // resource
                x, // x
                y, // y
                0, // origin x
                0, // origin y
                w, // width
                h, // height
                1f, // scale x
                1f, // scale y
                0);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawDownward(@Nonnull BitmapFont font, @Nullable Object pStrg, float rX, float rY) {
        if (pStrg == null) return;
        this.drawDownward(font, TNull.checkNull(pStrg.toString()), rX, rY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawDownward(@Nonnull BitmapFont font, float pStrg, float rX, float rY) {
        this.drawDownward(font, TNull.checkNull(String.valueOf(pStrg)), rX, rY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawDownward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY) {
        drawDownward(font, pStrg, rX, rY, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void drawDownward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY, boolean center) {
        if (pStrg == null) return;
        String[] lines = pStrg.toString().split("\n"); //$NON-NLS-1$
        float lh = 0;
        for (String line : lines) {
            GlyphLayout gl = new GlyphLayout(font, line);
            font.draw(this.batch, line, rX - (center ? gl.width / 2f : 0), rY + lh, 0, Align.left, false);
            lh -= gl.height;
            lh -= gl.height * 0.25f;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawUpward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY) {
        drawUpward(font, pStrg, rX, rY, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void drawUpward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY, boolean center) {
        if (pStrg == null) return;
        String[] lines = pStrg.toString().split("\n"); //$NON-NLS-1$
        float lh = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[lines.length - i - 1];
            GlyphLayout gl = new GlyphLayout(font, line);
            lh += gl.height;
            font.draw(this.batch, line, rX - (center ? gl.width / 2f : 0), rY + lh, 0, Align.left, false);
            lh += gl.height * 0.25f;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(@Nonnull Color pColor) {
        this.batch.setColor(pColor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(float r, float g, float b, float a) {
        this.batch.setColor(r, g, b, a);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void drawRotatedLines(int pThickness, float radius, float atxj) {
        float segments = 64f;
        float anglePerSegment = 360f / segments;
        float gamma = (180f - anglePerSegment) / 2f;
        float len = 2f * radius * MathUtils.sinDeg(anglePerSegment / 2f);
        for (float i = 0; i < segments; i++) {
            float curAngle = atxj + i * anglePerSegment;
            float x = MathUtils.cosDeg(curAngle) * radius;
            float y = MathUtils.sinDeg(curAngle) * radius;
            this.batch.setColor(1f, 1f, 1f, 1f / segments * i);
            this.batch.draw(this.white, x, y, 0, 0, len, pThickness, 1f, 1f, curAngle + (180 - gamma));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
