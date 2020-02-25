package com.s2dwt.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leo.commons.geom.Ray2D;
import com.leo.spheres.core.collider.Collider;

public final class SwtDrawer_Shapes extends ASwtDrawer {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ShapeRenderer sr;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawer_Shapes(@Nonnull ShapeRenderer sr) {
        this.sr = sr;
        this.sr.setAutoShapeType(true);
        this.sr.begin();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtDrawer_Shapes(@Nonnull ShapeRenderer sr, ShapeType type) {
        this.sr = sr;
        this.sr.begin(type);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void close() {
        this.sr.end();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public ShapeRenderer getRenderer() {
        return this.sr;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setColor(@Nonnull Color pColor) {
        this.sr.setColor(pColor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawCross(float pX, float pY, float pSize) {
        this.sr.line(pX - pSize, pY - pSize, pX + pSize, pY + pSize);
        this.sr.line(pX - pSize, pY + pSize, pX + pSize, pY - pSize);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawRay(Ray2D target) {
        this.sr.line(target.x1, target.y1, target.x2, target.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawCollider(Collider collider) {
        collider.forEach(item -> item.drawShape(this.sr));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void rect(float x, float y, float width, float height) {
        this.sr.rect(x, y, width, height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
