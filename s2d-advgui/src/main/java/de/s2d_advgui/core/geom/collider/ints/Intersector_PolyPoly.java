package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.utils.PolyPath;

public final class Intersector_PolyPoly extends AIntersector<Polygon, Polygon> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_PolyPoly INSTANCE = new Intersector_PolyPoly();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_PolyPoly getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_PolyPoly() {
        super(Polygon.class, Polygon.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Polygon pPoly1, Polygon pPoly2) {
        return Intersector.overlapConvexPolygons(pPoly1, pPoly2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Polygon pPoly1, Polygon pPoly2) {
        Ray2D ray = new Ray2D();
        {
            PolyPath pp = new PolyPath(pPoly2);
            while (pp.getNextRay(ray)) {
                Intersector_PolyRay.getInstance().calculateIntersections(pContext, pPoly1, ray);
                if (pContext.isBreaked()) return;
            }
        }
        {
            PolyPath pp = new PolyPath(pPoly1);
            while (pp.getNextRay(ray)) {
                Intersector_PolyRay.getInstance().calculateIntersections(pContext, pPoly2, ray);
                if (pContext.isBreaked()) return;
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
