package com.s2dwt.core.rendering;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.leo.commons.utils.TNull;
import com.s2dwt.core.resourcemanager.AResourceManager;

public final class SwtDrawer_Batch<RM extends AResourceManager> extends ASwtDrawer
{
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
    public SwtDrawer_Batch(@Nonnull RM pResourceManager, @Nonnull Batch pBatch)
    {
        this.resourceManager = pResourceManager;
        this.white = pResourceManager.getColorTextureRegion(Color.WHITE);
        this.batch = pBatch;
        this.active = pBatch.isDrawing();
        if (!this.active) this.batch.begin();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public RM getResourceManager()
    {
        return this.resourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public Batch getBatch()
    {
        return this.batch;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void close()
    {
        if (!this.active) this.batch.end();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(@Nonnull TextureRegion res, float x, float y, float w, float h)
    {
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
    public void drawDownward(@Nonnull BitmapFont font, @Nullable Object pStrg, float rX, float rY)
    {
        if (pStrg == null) return;
        this.drawDownward(font, TNull.checkNull(pStrg.toString()), rX, rY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawDownward(@Nonnull BitmapFont font, float pStrg, float rX, float rY)
    {
        this.drawDownward(font, TNull.checkNull(String.valueOf(pStrg)), rX, rY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawDownward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY)
    {
        drawDownward(font, pStrg, rX, rY, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void drawDownward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY, boolean center)
    {
        if (pStrg == null) return;
        String[] lines = pStrg.toString().split("\n"); //$NON-NLS-1$
        float lh = 0;
        for (String line : lines)
        {
            GlyphLayout gl = new GlyphLayout(font, line);
            font.draw(this.batch, line, rX - (center ? gl.width / 2f : 0), rY + lh, 0, Align.left, false);
            lh -= gl.height;
            lh -= gl.height * 0.25f;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawUpward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY)
    {
        drawUpward(font, pStrg, rX, rY, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void drawUpward(@Nonnull BitmapFont font, @Nullable CharSequence pStrg, float rX, float rY, boolean center)
    {
        if (pStrg == null) return;
        String[] lines = pStrg.toString().split("\n"); //$NON-NLS-1$
        float lh = 0;
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[lines.length - i - 1];
            GlyphLayout gl = new GlyphLayout(font, line);
            lh += gl.height;
            font.draw(this.batch, line, rX - (center ? gl.width / 2f : 0), rY + lh, 0, Align.left, false);
            lh += gl.height * 0.25f;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(@Nonnull Color pColor)
    {
        this.batch.setColor(pColor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(float r, float g, float b, float a)
    {
        this.batch.setColor(r, g, b, a);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void drawRotatedLines(int pThickness, float radius, float atxj)
    {
        float segments = 64f;
        float anglePerSegment = 360f / segments;
        float gamma = (180f - anglePerSegment) / 2f;
        float len = 2f * radius * MathUtils.sinDeg(anglePerSegment / 2f);
        for (float i = 0; i < segments; i++)
        {
            float curAngle = atxj + i * anglePerSegment;
            float x = MathUtils.cosDeg(curAngle) * radius;
            float y = MathUtils.sinDeg(curAngle) * radius;
            this.batch.setColor(1f, 1f, 1f, 1f / segments * i);
            this.batch.draw(this.white, x, y, 0, 0, len, pThickness, 1f, 1f, curAngle + (180 - gamma));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(TextureRegion ppo, Rectangle rr)
    {
        this.draw(ppo, rr.x, rr.y, rr.width, rr.height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawText(String pStrg, Rectangle pDims, int pAlign, float pFontSize, boolean pFontUseInt, Color useColor)
    {
        BitmapFont font = this.resourceManager.getFont(pFontSize, pFontUseInt);
        font.setColor(useColor);

        if (pStrg == null) return;
        String[] lines = pStrg.toString().split("\n"); //$NON-NLS-1$
        float aay = 0;
        float aaw = 0;
        float aah = 0;
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            GlyphLayout gl = new GlyphLayout(font, line);
            aaw = Math.max(aaw, gl.width);
            aah = Math.max(aah, gl.height);
            aay -= aah;
            if (i < lines.length - 1)
            {
                aay -= aah * 0.25f;
            }
        }

        float lh;
        switch (pAlign)
        {
            default:
            case Align.top:
            case Align.topLeft:
            case Align.topRight:
                lh = pDims.y + pDims.height;
                break;
            case Align.bottom:
            case Align.bottomLeft:
            case Align.bottomRight:
                lh = pDims.y - aay;
                break;
            case Align.center:
            case Align.left:
            case Align.right:
                lh = pDims.y + (pDims.height - aay) / 2f;
                break;
        }

        int useLineAlign = Align.center;
        switch (pAlign)
        {
            case Align.left:
            case Align.bottomLeft:
            case Align.topLeft:
                useLineAlign = Align.left;
                break;
            case Align.right:
            case Align.bottomRight:
            case Align.topRight:
                useLineAlign = Align.right;
                break;
            case Align.top:
            case Align.bottom:
            case Align.center:
                useLineAlign = Align.center;
                break;

        }
        for (String line : lines)
        {
            font.draw(this.batch, line, pDims.x, lh, pDims.width, useLineAlign, false);
            lh -= aah;
            lh -= aah * 0.25f;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawBorder(String string, Rectangle pDims)
    {
        Texture tx = this.resourceManager.getTexture(string);

        int o1 = tx.getWidth() / 4;
        int o2 = o1 * 2;
        int o3 = o1 * 3;

        TextureRegion trTL = new TextureRegion(tx, 0, 0, o1, o1);
        TextureRegion trT = new TextureRegion(tx, o1, 0, o1, o1);
        TextureRegion trTR = new TextureRegion(tx, o3, 0, o1, o1);

        TextureRegion trCL = new TextureRegion(tx, 0, o1, o1, o1);
        TextureRegion trC = new TextureRegion(tx, o1, o1, o1, o1);
        TextureRegion trCR = new TextureRegion(tx, o3, o1, o1, o1);

        TextureRegion trBL = new TextureRegion(tx, 0, o3, o1, o1);
        TextureRegion trB = new TextureRegion(tx, o1, o3, o1, o1);
        TextureRegion trBR = new TextureRegion(tx, o3, o3, o1, o1);

        float tr = pDims.y + pDims.height - o1;
        float tc = pDims.y + o1;
        float nok = pDims.height - o2;
        float ajs = pDims.x + pDims.width - o1;
        float fu = pDims.x + o1;
        float twi = pDims.width - o2;

        this.batch.draw(trTL, pDims.x, tr);
        this.batch.draw(trT, fu, tr, twi, o1);
        this.batch.draw(trTR, ajs, tr);
        this.batch.draw(trCL, pDims.x, tc, o1, nok);
        this.batch.draw(trC, fu, tc, twi, nok);
        this.batch.draw(trCR, ajs, tc, o1, nok);
        this.batch.draw(trBL, pDims.x, pDims.y);
        this.batch.draw(trB, fu, pDims.y, twi, o1);
        this.batch.draw(trBR, ajs, pDims.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
