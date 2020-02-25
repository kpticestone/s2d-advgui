package com.leo.spheres.core.collider;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.leo.spheres.core.utils.PolygonUtils;
import com.leo.commons.geom.Ray2D;
import com.leo.commons.utils.BiConsumerFloat;
import javax.annotation.Nonnull;

public abstract class ColliderItem<T extends Shape2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final T shape;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final Rectangle box;

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem(@Nonnull T pShape, @Nonnull Rectangle box) {
        this.shape = pShape;
        this.box = box;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean overlaps(Polygon pPolygon, Circle pCircle) {
        Vector2 circleMid = new Vector2(pCircle.x, pCircle.y);
        float squareRadius = pCircle.radius * pCircle.radius;
        float[] vertices = pPolygon.getTransformedVertices();
        Vector2 tmpV1 = new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]);
        Vector2 tmpV2 = new Vector2(vertices[0], vertices[1]);
        if (Intersector.intersectSegmentCircle(tmpV1, tmpV2, circleMid, squareRadius)) return true;
        for (int i = 2; i < vertices.length; i += 2) {
            tmpV1.set(vertices[i - 2], vertices[i - 1]);
            tmpV2.set(vertices[i], vertices[i + 1]);
            if (Intersector.intersectSegmentCircle(tmpV1, tmpV2, circleMid, squareRadius)) return true;
        }
        return pPolygon.contains(pCircle.x, pCircle.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean overlaps(@Nonnull Polygon pPolygon, @Nonnull Rectangle pRect) {
        Polygon rPoly = PolygonUtils.byRect(pRect);
        //rPoly.setPosition(pRect.x, pRect.y);
        return Intersector.overlapConvexPolygons(rPoly, pPolygon);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public T getShape() {
        return this.shape;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public Rectangle getBox() {
        return this.box;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.box + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(@Nonnull Vector2 pPoint) {
        if (!this.box.contains(pPoint)) return false;
        return this.shape.contains(pPoint);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(float x, float y) {
        if (!this.box.contains(x, y)) return false;
        return this.shape.contains(x, y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hits(@Nonnull ColliderItem<?> pItem) {
        if (!this.box.overlaps(pItem.box)) return false;
        return this._hits(pItem);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract boolean _hits(@Nonnull ColliderItem<?> pItem);

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void drawShape(@Nonnull ShapeRenderer pRenderer);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public abstract ColliderItem<T> getTransformedItem(float x, float y);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public abstract ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform);

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull BiConsumerFloat pConsumer);

    // -------------------------------------------------------------------------------------------------------------------------
}
