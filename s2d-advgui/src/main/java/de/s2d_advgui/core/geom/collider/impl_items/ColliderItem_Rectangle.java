package de.s2d_advgui.core.geom.collider.impl_items;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRect;
import de.s2d_advgui.core.utils.ShapeUtils;

public class ColliderItem_Rectangle extends ColliderItem<Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Rectangle(@Nonnull Rectangle pShape) {
        super(pShape, pShape, true);
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
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        Polygon poo = ShapeUtils.byRect(this.shape);
        return new ColliderItem_Polygon(ShapeUtils.clone2(poo, transform));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(@Nonnull Ray2D pRay, @Nonnull IntersectionContext pContext) {
        Intersector_RayRect.getInstance().calculateIntersections(pContext, pRay, this.shape);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
