package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.utils.PolyPath;

public final class Intersector_PolyRay extends AIntersector<Polygon, Ray2D> {
    // ----------------------------------------------------------------------------------
    private final static Intersector_PolyRay INSTANCE = new Intersector_PolyRay();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_PolyRay getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_PolyRay() {
        super(Polygon.class, Ray2D.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Polygon pPoly, Ray2D pRay) {
        return Intersector.intersectSegmentPolygon(new Vector2(pRay.x1, pRay.y1), new Vector2(pRay.x2, pRay.y2), pPoly);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Polygon pPoly, Ray2D pRay) {
        if (pPoly.contains(pRay.x1, pRay.y1)) {
            if (pContext.send(pRay.x1, pRay.y1)) return;
        }
        if (pPoly.contains(pRay.x2, pRay.y2)) {
            if (pContext.send(pRay.x2, pRay.y2)) return;
        }
        Ray2D ray = new Ray2D();
        PolyPath path = new PolyPath(pPoly);
        while (path.getNextRay(ray)) {
            Intersector_RayRay.getInstance().calculateIntersections(pContext, pRay, ray);
            if (pContext.isBreaked()) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
