package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.utils.PolyPath;

public final class Intersector_CirclePoly extends AIntersector<Circle, Polygon> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_CirclePoly INSTANCE = new Intersector_CirclePoly();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_CirclePoly getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_CirclePoly() {
        super(Circle.class, Polygon.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Circle pCircle, Polygon pPoly) {
        if (pPoly.contains(pCircle.x, pCircle.y)) {
            return true;
        }
        PolyPath pa = new PolyPath(pPoly);
        Ray2D ray = new Ray2D();
        while (pa.getNextRay(ray)) {
            if (Intersector_CircleRay.getInstance().calculateOverlapping(pContext, pCircle, ray)) {
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Circle pCircle, Polygon pPoly) {
        if (pPoly.contains(pCircle.x, pCircle.y)) {
            if (pContext.send(pCircle.x, pCircle.y)) {
                return;
            }
        }
        Ray2D ray = new Ray2D();
        PolyPath path = new PolyPath(pPoly);
        while (path.getNextRay(ray)) {
            Intersector_CircleRay.getInstance().calculateIntersections(pContext, pCircle, ray);
            if (pContext.isBreaked()) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
