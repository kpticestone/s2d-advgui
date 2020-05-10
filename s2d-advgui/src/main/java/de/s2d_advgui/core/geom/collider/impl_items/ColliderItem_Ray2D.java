package de.s2d_advgui.core.geom.collider.impl_items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.collider.IColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRay;

public class ColliderItem_Ray2D extends ColliderItem<Ray2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Ray2D(Ray2D pShape) {
        super(pShape, pShape.getBounds(), true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(ShapeRenderer pRenderer) {
        pRenderer.line(this.shape.x1, this.shape.y1, this.shape.x2, this.shape.y2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public IColliderItem getTransformedItem(Affine2 transform) {
        Ray2D back = this.shape.getTranslatedInstance(transform);
        return new ColliderItem_Ray2D(back);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(Ray2D pRay, IntersectionContext pContext) {
        Intersector_RayRay.getInstance().calculateIntersections(pContext, this.shape, pRay);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
