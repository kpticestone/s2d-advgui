package de.s2d_advgui.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.geom.collider.IColliderItem;

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
    @Deprecated
    public void drawRay(Ray2D target) {
        this.sr.line(target.x1, target.y1, target.x2, target.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(Ray2D target) {
        this.sr.line(target.x1, target.y1, target.x2, target.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(ICollider collider) {
        collider.forEach(item -> item.drawShape(this.sr));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public void drawCollider(ICollider collider) {
        collider.forEach(item -> item.drawShape(this.sr));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(IColliderItem pColliderItem) {
        if (pColliderItem != null) {
            pColliderItem.drawShape(this.sr);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void rect(float x, float y, float width, float height) {
        this.sr.rect(x, y, width, height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public void drawRect(Rectangle rr) {
        this.sr.rect(rr.x, rr.y, rr.width, rr.height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(Rectangle rr) {
        this.sr.rect(rr.x, rr.y, rr.width, rr.height);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void drawCross(Rectangle pDims) {
        float ux = pDims.x;
        float uy = pDims.y;
        float vx = pDims.x + pDims.width;
        float vy = pDims.y + pDims.height;
        this.sr.line(ux, uy, vx, vy);
        this.sr.line(ux, vy, vx, uy);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public void drawRect(Rectangle pDims, int x, int y) {
        float ux = pDims.x + x;
        float uy = pDims.y + y;
        float uw = pDims.width - x * 2;
        float uh = pDims.height - y * 2;
        this.sr.rect(ux, uy, uw, uh);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(Rectangle pDims, int x, int y) {
        float ux = pDims.x + x;
        float uy = pDims.y + y;
        float uw = pDims.width - x * 2;
        float uh = pDims.height - y * 2;
        this.sr.rect(ux, uy, uw, uh);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(Circle pCircle) {
        this.sr.circle(pCircle.x, pCircle.y, pCircle.radius);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void draw(Polygon poly) {
        this.sr.polygon(poly.getTransformedVertices());
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
