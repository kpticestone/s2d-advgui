package de.s2d_advgui.core.geom.collider;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Shape2D;

import de.s2d_advgui.core.geom.collider.ints.Intersector_CircleCircle;
import de.s2d_advgui.core.geom.collider.ints.Intersector_CirclePoly;
import de.s2d_advgui.core.geom.collider.ints.Intersector_CircleRay;
import de.s2d_advgui.core.geom.collider.ints.Intersector_CircleRect;
import de.s2d_advgui.core.geom.collider.ints.Intersector_PolyPoly;
import de.s2d_advgui.core.geom.collider.ints.Intersector_PolyRay;
import de.s2d_advgui.core.geom.collider.ints.Intersector_PolyRect;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRay;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RayRect;
import de.s2d_advgui.core.geom.collider.ints.Intersector_RectRect;

public final class IntersectionFactory {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static IntersectionFactory INSTANCE = new IntersectionFactory();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static IntersectionFactory getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<Class<? extends Shape2D>, Map<Class<? extends Shape2D>, AIntersector<?, ?>>> handlers_a2b = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final Map<Class<? extends Shape2D>, Map<Class<? extends Shape2D>, AIntersector<?, ?>>> handlers_b2a = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private IntersectionFactory() {
        add(Intersector_CircleCircle.getInstance());
        add(Intersector_CirclePoly.getInstance());
        add(Intersector_CircleRay.getInstance());
        add(Intersector_CircleRect.getInstance());
        add(Intersector_PolyPoly.getInstance());
        add(Intersector_PolyRay.getInstance());
        add(Intersector_PolyRect.getInstance());
        add(Intersector_RayRay.getInstance());
        add(Intersector_RayRect.getInstance());
        add(Intersector_RectRect.getInstance());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void add(AIntersector<?, ?> pIn) {
        Map<Class<? extends Shape2D>, AIntersector<?, ?>> intoA = this.handlers_a2b
                .computeIfAbsent(pIn.getClzA(), k -> new HashMap<>());
        intoA.put(pIn.getClzB(), pIn);

        intoA = this.handlers_b2a.computeIfAbsent(pIn.getClzB(), k -> new HashMap<>());
        intoA.put(pIn.getClzA(), pIn);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public <A extends Shape2D, B extends Shape2D> boolean calculateOverlapping(A pShapeA, B pShapeB) {
        return this.calculateOverlapping(IntersectionContext.OVERLAPCONTEXT, pShapeA, pShapeB);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <A extends Shape2D, B extends Shape2D> boolean calculateOverlapping(IntersectionContext pContext, A pShapeA,
            B pShapeB) {
        AIntersector ha;
        Map<Class<? extends Shape2D>, AIntersector<?, ?>> xa;

        xa = this.handlers_a2b.get(pShapeA.getClass());
        if (xa != null) {
            ha = xa.get(pShapeB.getClass());
            if (ha != null) {
                try {
                    return ha.calculateOverlapping(pContext, pShapeA, pShapeB);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }

        xa = this.handlers_b2a.get(pShapeA.getClass());
        if (xa != null) {
            ha = xa.get(pShapeB.getClass());
            if (ha != null) {
                try {
                    return ha.calculateOverlapping(pContext, pShapeB, pShapeA);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <A extends Shape2D, B extends Shape2D> void calculateIntersections(IntersectionContext pContext, A pShapeA,
            B pShapeB) {
        AIntersector ha;
        Map<Class<? extends Shape2D>, AIntersector<?, ?>> xa;
        xa = this.handlers_a2b.get(pShapeA.getClass());
        if (xa != null) {
            ha = xa.get(pShapeB.getClass());
            if (ha != null) {
                try {
                    ha.calculateIntersections(pContext, pShapeA, pShapeB);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
        xa = this.handlers_b2a.get(pShapeA.getClass());
        if (xa != null) {
            ha = xa.get(pShapeB.getClass());
            if (ha != null) {
                try {
                    ha.calculateIntersections(pContext, pShapeB, pShapeA);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
