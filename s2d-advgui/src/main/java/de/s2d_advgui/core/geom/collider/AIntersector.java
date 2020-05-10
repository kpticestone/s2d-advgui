package de.s2d_advgui.core.geom.collider;

import com.badlogic.gdx.math.Shape2D;

public abstract class AIntersector<A extends Shape2D, B extends Shape2D> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Class<? extends A> shapeClazzA;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Class<? extends B> shapeClazzB;

    // -------------------------------------------------------------------------------------------------------------------------
    public AIntersector(Class<? extends A> pShapeClazzA, Class<? extends B> pShapeClazzB) {
        this.shapeClazzA = pShapeClazzA;
        this.shapeClazzB = pShapeClazzB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final Class<? extends A> getClzA() {
        return this.shapeClazzA;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final Class<? extends B> getClzB() {
        return this.shapeClazzB;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract boolean calculateOverlapping(IntersectionContext pContext, A pShapeA, B pShapeB);

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract void calculateIntersections(IntersectionContext pContext, A pShapeA, B pShapeB);

    // -------------------------------------------------------------------------------------------------------------------------
}