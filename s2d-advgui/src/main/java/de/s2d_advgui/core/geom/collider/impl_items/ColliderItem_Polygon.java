package de.s2d_advgui.core.geom.collider.impl_items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.commons.BiConsumerFloat;
import de.s2d_advgui.commons.TNull;
import javax.annotation.Nonnull;

public final class ColliderItem_Polygon extends ColliderItem<Polygon> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Polygon(@Nonnull Polygon pShape) {
        super(pShape, TNull.checkNull(pShape.getBoundingRectangle()));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean _hits(@Nonnull ColliderItem<?> pItem) {
        if (pItem.shape instanceof Circle) return overlaps(this.shape, (Circle) pItem.shape);
        if (pItem.shape instanceof Rectangle) return overlaps(this.shape, (Rectangle) pItem.shape);
        if (pItem.shape instanceof Polygon) return Intersector.overlapConvexPolygons(this.shape, (Polygon) pItem.shape);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(@Nonnull ShapeRenderer pRenderer) {
        pRenderer.polygon(this.shape.getVertices());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem_Polygon getTransformedItem(float x, float y) {
        Polygon copy = new Polygon(this.shape.getVertices());
        copy.setPosition(x, y);
        return new ColliderItem_Polygon(copy);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        float[] org = this.shape.getTransformedVertices();
        float[] cpy = new float[org.length];
        System.arraycopy(org, 0, cpy, 0, org.length);
        Vector2 vec = new Vector2();
        for (int i = 0, n = cpy.length; i < n; i += 2) {
            vec.set(cpy[i], cpy[i + 1]);
            transform.applyTo(vec);
            cpy[i] = vec.x;
            cpy[i + 1] = vec.y;
        }
        return new ColliderItem_Polygon(new Polygon(cpy));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull BiConsumerFloat pConsumer) {
        Vector2 inter = new Vector2();
        float[] vertices = this.shape.getTransformedVertices();
        float x1 = target.x1, y1 = target.y1, x2 = target.x2, y2 = target.y2;
        if (this.shape.contains(x1, y1)) {
            pConsumer.accept(x1, y1);
        }
        if (this.shape.contains(x2, y2)) {
            pConsumer.accept(x2, y2);
        }
        float lastX1 = vertices[vertices.length - 2];
        float lastY1 = vertices[vertices.length - 1];
        for (int i = 0; i < vertices.length; i += 2) {
            if (Intersector.intersectSegments(x1, y1, x2, y2, lastX1, lastY1, vertices[i], vertices[i + 1], inter)) {
                pConsumer.accept(inter.x, inter.y);
            }
            lastX1 = vertices[i];
            lastY1 = vertices[i + 1];
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
