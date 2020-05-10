package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.utils.ShapeUtils;

public final class Intersector_CircleRect extends AIntersector<Circle, Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_CircleRect INSTANCE = new Intersector_CircleRect();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_CircleRect getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_CircleRect() {
        super(Circle.class, Rectangle.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Circle pCircle, Rectangle pRect) {
        return Intersector.overlaps(pCircle, pRect);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Circle pCircle, Rectangle pRect) {
        Polygon rPoly = ShapeUtils.byRect(pRect);
        Intersector_CirclePoly.getInstance().calculateIntersections(pContext, pCircle, rPoly);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
