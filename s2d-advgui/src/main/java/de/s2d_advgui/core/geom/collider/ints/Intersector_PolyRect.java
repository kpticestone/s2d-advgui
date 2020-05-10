package de.s2d_advgui.core.geom.collider.ints;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.utils.ShapeUtils;

public final class Intersector_PolyRect extends AIntersector<Polygon, Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_PolyRect INSTANCE = new Intersector_PolyRect();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_PolyRect getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_PolyRect() {
        super(Polygon.class, Rectangle.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, @Nonnull Polygon pPoly, @Nonnull Rectangle pRect) {
        Polygon rPoly = ShapeUtils.byRect(pRect);
        return Intersector.overlapConvexPolygons(rPoly, pPoly);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Polygon pPoly, Rectangle pRect) {
        Polygon rPoly = ShapeUtils.byRect(pRect);
        Intersector_PolyPoly.getInstance().calculateIntersections(pContext, pPoly, rPoly);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
