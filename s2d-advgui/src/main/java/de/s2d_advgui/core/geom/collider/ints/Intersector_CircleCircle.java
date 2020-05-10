package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;

public final class Intersector_CircleCircle extends AIntersector<Circle, Circle> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_CircleCircle INSTANCE = new Intersector_CircleCircle();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_CircleCircle getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_CircleCircle() {
        super(Circle.class, Circle.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Circle pCircle1, Circle pCircle2) {
        return Intersector.overlaps(pCircle1, pCircle2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Circle pCircle1, Circle pCircle2) {
        float EPS = 0.0000001f;
        float r, R, d, dx, dy, cx, cy, Cx, Cy;

        if (pCircle1.contains(pCircle2.x, pCircle2.y)) {
            if (pContext.send(pCircle2.x, pCircle2.y)) return;
        }
        if (pCircle2.contains(pCircle1.x, pCircle1.y)) {
            if (pContext.send(pCircle1.x, pCircle1.y)) return;
        }

        if (pCircle1.radius < pCircle2.radius) {
            r = pCircle1.radius;
            R = pCircle2.radius;
            cx = pCircle1.x;
            cy = pCircle1.y;
            Cx = pCircle2.x;
            Cy = pCircle2.y;
        } else {
            r = pCircle2.radius;
            R = pCircle1.radius;
            Cx = pCircle1.x;
            Cy = pCircle1.y;
            cx = pCircle2.x;
            cy = pCircle2.y;
        }

        // Compute the vector <dx, dy>
        dx = cx - Cx;
        dy = cy - Cy;

        // Find the distance between two points.
        d = (float) Math.sqrt(dx * dx + dy * dy);

        // There are an infinite number of solutions
        // Seems appropriate to also return null
        if (d < EPS && Math.abs(R - r) < EPS) return;

        // No intersection (circles centered at the
        // same place with different size)
        else if (d < EPS) return;

        float x = (dx / d) * R + Cx;
        float y = (dy / d) * R + Cy;
        Vector2 P = new Vector2(x, y);

        // Single intersection (kissing circles)
        if (Math.abs((R + r) - d) < EPS || Math.abs(R - (r + d)) < EPS) {
            pContext.send(P.x, P.y);
            return;
        }

        // No intersection. Either the small circle contained within
        // big circle or circles are simply disjoint.
        if ((d + r) < R || (R + r < d)) return;

        Vector2 C = new Vector2(Cx, Cy);
        float angle = (float) Math.acos(((r * r - d * d - R * R) / (-2.0 * d * R)));

        Vector2 pt1 = rotatePoint(C, P, +angle);
        Vector2 pt2 = rotatePoint(C, P, -angle);

        if (pContext.send(pt1.x, pt1.y)) {
            return;
        }
        pContext.send(pt2.x, pt2.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    Vector2 rotatePoint(Vector2 fp, Vector2 pt, float a) {
        float x = pt.x - fp.x;
        float y = pt.y - fp.y;
        float xRot = (float) (x * Math.cos(a) + y * Math.sin(a));
        float yRot = (float) (y * Math.cos(a) - x * Math.sin(a));
        return new Vector2(fp.x + xRot, fp.y + yRot);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
