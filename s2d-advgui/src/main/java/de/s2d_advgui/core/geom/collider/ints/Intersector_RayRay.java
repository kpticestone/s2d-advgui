package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;

public final class Intersector_RayRay extends AIntersector<Ray2D, Ray2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_RayRay INSTANCE = new Intersector_RayRay();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_RayRay getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_RayRay() {
        super(Ray2D.class, Ray2D.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Ray2D pRay1, Ray2D pRay2) {
        Vector2 pnt = new Vector2();
        return Intersector.intersectLines(
                pRay1.x1, pRay1.y1, pRay1.x2, pRay1.y2,
                pRay2.x1, pRay2.y1, pRay2.x2, pRay2.y2,
                pnt);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Ray2D pRay1, Ray2D pRay2) {
        Vector2 pnt = new Vector2();
        boolean back = Intersector.intersectLines(
                pRay1.x1, pRay1.y1, pRay1.x2, pRay1.y2,
                pRay2.x1, pRay2.y1, pRay2.x2, pRay2.y2,
                pnt);
        // System.err.println("ray vs ray: " + pRay1 + " vs " + pRay2);
        if (back && pRay1.contains(pnt) && pRay2.contains(pnt)) {
            pContext.send(pnt.x, pnt.y);
        } else {
            if (pRay1.contains(pRay2.x1, pRay2.y1)) {
                pContext.send(pRay2.x1, pRay2.y1);
            }
            if (pRay1.contains(pRay2.x2, pRay2.y2)) {
                pContext.send(pRay2.x2, pRay2.y2);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
