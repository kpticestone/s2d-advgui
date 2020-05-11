package de.s2d_advgui.core.geom.collider.impl_items;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.ints.Intersector_CircleRay;

public final class ColliderItem_Circle extends ColliderItem<Circle> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Circle(@Nonnull Circle pShape) {
        super(pShape, new Rectangle(pShape.x - pShape.radius, pShape.y - pShape.radius, pShape.radius * 2,
                pShape.radius * 2), true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(@Nonnull ShapeRenderer pRenderer) {
        Circle c = this.shape;
        pRenderer.circle(c.x, c.y, c.radius, 32);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        Vector2 center = new Vector2(this.shape.x, this.shape.y);
        transform.applyTo(center);
        return new ColliderItem_Circle(new Circle(center, this.shape.radius));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(@Nonnull Ray2D pRay, @Nonnull IntersectionContext pContext) {
        Intersector_CircleRay.getInstance().calculateIntersections(pContext, this.shape, pRay);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Shape createBox2dShape() {
        CircleShape back = new CircleShape();
        back.setPosition(new Vector2(this.shape.x, this.shape.y));
        back.setRadius(this.shape.radius);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
