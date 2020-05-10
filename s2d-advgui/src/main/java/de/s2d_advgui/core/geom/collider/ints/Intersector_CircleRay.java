package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;

public final class Intersector_CircleRay extends AIntersector<Circle, Ray2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_CircleRay INSTANCE = new Intersector_CircleRay();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_CircleRay getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_CircleRay() {
        super(Circle.class, Ray2D.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Circle pCircle, Ray2D pRay) {
        return Intersector.intersectSegmentCircle(
                new Vector2(pRay.x1, pRay.y1),
                new Vector2(pRay.x2, pRay.y2),
                new Vector2(pCircle.x, pCircle.y),
                pCircle.radius);
    }

    @FunctionalInterface
    static interface IWorry {
        boolean apply(float x, float y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Circle pCircle, Ray2D pRay) {
        Vector3 tmp = new Vector3();
        Vector3 tmp1 = new Vector3();
        Vector3 tmp2 = new Vector3();
        Vector3 tmp3 = new Vector3();

        {
            if (pCircle.contains(pRay.x1, pRay.y1)) {
                if (pContext.send(pRay.x1, pRay.y1)) return;
            }
            if (pCircle.contains(pRay.x2, pRay.y2)) {
                if (pContext.send(pRay.x2, pRay.y2)) return;
            }
        }

        IWorry pListener2 = (x, y) -> {
            if (pRay.contains(x, y)) {
                return pContext.send(x, y);
            }
            return false;
        };

        tmp.set(pRay.x2 - pRay.x1, pRay.y2 - pRay.y1, 0);
        tmp1.set(pCircle.x - pRay.x1, pCircle.y - pRay.y1, 0);
        float d = tmp.len();
        float u = tmp1.dot(tmp.nor());
        boolean inCircle = u <= 0;
        // System.err.println("d: " + d);
        // System.err.println("u: " + u);
        // double aka = Math.sqrt(u);
        // System.err.println("aka: " + aka);
        tmp3.set(tmp.scl(u));
        float p1x = tmp3.x + pRay.x1;
        float p1y = tmp3.y + pRay.y1;
        tmp2.set(p1x, p1y, 0);
        float x = pCircle.x - tmp2.x;
        float y = pCircle.y - tmp2.y;
        boolean hasPoints = x * x + y * y <= (pCircle.radius * pCircle.radius);
        if (hasPoints) {
            float yeta = (float) (Math.atan2(pRay.x2 - pRay.x1, pRay.y2 - pRay.y1)
                    * MathUtils.radiansToDegrees);
            float r1 = Vector3.len(pCircle.x - tmp2.x, pCircle.y - tmp2.y, 0);
            float aoa = pCircle.radius * pCircle.radius - r1 * r1;
            float wolve = Math.abs(aoa);
            float r3 = (float) Math.sqrt(wolve);
            // System.err.println("----------------------------------");
            // System.err.println("shape: " + pCircle.x + ", " + pCircle.y);
            // System.err.println("d: " + d);
            // System.err.println("u: " + u);
            // System.err.println("x/y: " + x + ", " + y);
            // System.err.println("aoa: " + aoa);
            // System.err.println("wolve: " + wolve);
            // System.err.println("intersects: " + pRay.x1 + ", " + pRay.y1 + " - " +
            // pRay.x2 + ", " + pRay.y2);
            // System.err.println("yeta: " + yeta);
            // System.err.println("radius: " + pCircle.radius);
            // System.err.println("r1: " + r1);
            // System.err.println("r3: " + r3);
            if (r3 == 0) {
                pListener2.apply(tmp2.x, tmp2.y);
            } else {
                float gx = MathUtils.sin(yeta * MathUtils.degRad) * r3;
                float gy = MathUtils.cos(yeta * MathUtils.degRad) * r3;
                // System.err.println("gx/y: " + gx + "/" + gy);
                // gx-= pCircle.x;
                // gy-= pCircle.y;
                // System.err.println("gx/y: " + gx + "/" + gy);
                // pListener.accept(gx, gy);
                // pListener.accept(pCircle.x, pCircle.y);
                // System.err.println("tmp2: " + tmp2.x + ", " + tmp2.y + " (" + tmp2.len() +
                // ")");
                // System.err.println("tmp3: " + tmp3.x + ", " + tmp3.y + " (" + tmp3.len() +
                // ")");
                // pListener.accept(tmp2.x, tmp2.y);

                if (pListener2.apply(tmp2.x + gx, tmp2.y + gy)) {
                    return;
                }
                if (pListener2.apply(tmp2.x - gx, tmp2.y - gy)) {
                    return;
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
