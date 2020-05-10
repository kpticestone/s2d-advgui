package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;

public final class Intersector_RayRect extends AIntersector<Ray2D, Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_RayRect INSTANCE = new Intersector_RayRect();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_RayRect getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_RayRect() {
        super(Ray2D.class, Rectangle.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Ray2D pRay, Rectangle pRect) {
        return Intersector.intersectSegmentRectangle(new Vector2(pRay.x1, pRay.y1), new Vector2(pRay.x2, pRay.y2),
                pRect);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Ray2D pRay, Rectangle pRect) {
//        if( TOldCompatibilityCode.TRUE ) return false;
        float startX = pRay.x1;
        float startY = pRay.y1;
        float endX = pRay.x2;
        float endY = pRay.y2;
        float rectangleEndX = pRect.x + pRect.width;
        float rectangleEndY = pRect.y + pRect.height;
        Vector2 p1 = new Vector2();
        if (Intersector.intersectSegments(startX, startY, endX, endY, pRect.x, pRect.y, pRect.x,
                rectangleEndY, p1)) {
            if (pContext.send(p1.x, p1.y)) return;
        }
        if (Intersector.intersectSegments(startX, startY, endX, endY, pRect.x, pRect.y, rectangleEndX,
                pRect.y, p1)) {
            if (pContext.send(p1.x, p1.y)) return;
        }
        if (Intersector.intersectSegments(startX, startY, endX, endY, rectangleEndX, pRect.y, rectangleEndX,
                rectangleEndY, p1)) {
            if (pContext.send(p1.x, p1.y)) return;
        }
        if (Intersector.intersectSegments(startX, startY, endX, endY, pRect.x, rectangleEndY, rectangleEndX,
                rectangleEndY, p1)) {
            if (pContext.send(p1.x, p1.y)) return;
        }
        if (pRect.contains(startX, startY)) {
            if (pContext.send(startX, startY)) return;
        }
        if (pRect.contains(endX, endY)) {
            if (pContext.send(endX, endY)) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
