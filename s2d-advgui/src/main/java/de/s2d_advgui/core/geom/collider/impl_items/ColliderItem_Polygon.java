package de.s2d_advgui.core.geom.collider.impl_items;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Polygon;

import de.s2d_advgui.commons.TNull;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.ints.Intersector_PolyRay;
import de.s2d_advgui.core.utils.ShapeUtils;

public final class ColliderItem_Polygon extends ColliderItem<Polygon> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Polygon(@Nonnull Polygon pShape) {
        super(pShape, TNull.checkNull(pShape.getBoundingRectangle()), true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(@Nonnull ShapeRenderer pRenderer) {
        pRenderer.polygon(this.shape.getVertices());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        Polygon p2 = ShapeUtils.clone2(this.shape, transform);
        return new ColliderItem_Polygon(p2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(@Nonnull Ray2D pRay, @Nonnull IntersectionContext pContext) {
        Intersector_PolyRay.getInstance().calculateIntersections(pContext, this.shape, pRay);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
