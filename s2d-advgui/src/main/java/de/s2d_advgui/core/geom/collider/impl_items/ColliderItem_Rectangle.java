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
import javax.annotation.Nonnull;

public class ColliderItem_Rectangle extends ColliderItem<Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Rectangle(@Nonnull Rectangle pShape) {
        super(pShape, pShape);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean _hits(@Nonnull ColliderItem<?> pItem) {
        if (pItem.shape instanceof Circle) return Intersector.overlaps((Circle) pItem.shape, this.shape);
        if (pItem.shape instanceof Rectangle) return Intersector.overlaps(this.shape, (Rectangle) pItem.shape);
        if (pItem.shape instanceof Polygon) return overlaps((Polygon) pItem.shape, this.shape);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(@Nonnull ShapeRenderer pRenderer) {
        Rectangle r = this.shape;
        pRenderer.rect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem_Rectangle getTransformedItem(float x, float y) {
        return new ColliderItem_Rectangle(new Rectangle(this.shape.x + x, this.shape.y + y, this.shape.width, this.shape.height));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        float x2 = this.shape.x + this.shape.width;
        float y2 = this.shape.y + this.shape.height;
        float[] floats = new float[]{this.shape.x, this.shape.y, x2, this.shape.y, x2, y2, this.shape.x, y2};
        Vector2 vec = new Vector2();
        for (int i = 0, n = floats.length; i < n; i += 2) {
            vec.set(floats[i], floats[i + 1]);
            transform.applyTo(vec);
            floats[i] = vec.x;
            floats[i + 1] = vec.y;
        }
        return new ColliderItem_Polygon(new Polygon(floats));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull BiConsumerFloat pConsumer) {
        //        if( TOldCompatibilityCode.TRUE ) return;
        if (this.shape.contains(target.x1, target.y1)) {
            pConsumer.accept(target.x1, target.y1);
        }
        if (this.shape.contains(target.x2, target.y2)) {
            pConsumer.accept(target.x2, target.y2);
        }
        Vector2 intersection = new Vector2();
        if (Intersector.intersectLines(this.shape.x, this.shape.y, this.shape.x + this.shape.width, this.shape.y, target.x1, target.y1, target.x2, target.y2, intersection)) {
            pConsumer.accept(intersection.x, intersection.y);
        }
        if (Intersector.intersectLines(this.shape.x, this.shape.y, this.shape.x, this.shape.y + this.shape.height, target.x1, target.y1, target.x2, target.y2, intersection)) {
            pConsumer.accept(intersection.x, intersection.y);
        }
        if (Intersector.intersectLines(this.shape.x, this.shape.y + this.shape.height, this.shape.x + this.shape.width, this.shape.y + this.shape.height, target.x1, target.y1, target.x2, target.y2, intersection)) {
            pConsumer.accept(intersection.x, intersection.y);
        }
        if (Intersector.intersectLines(this.shape.x + this.shape.width, this.shape.y, this.shape.x + this.shape.width, this.shape.y + this.shape.height, target.x1, target.y1, target.x2, target.y2, intersection)) {
            pConsumer.accept(intersection.x, intersection.y);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
