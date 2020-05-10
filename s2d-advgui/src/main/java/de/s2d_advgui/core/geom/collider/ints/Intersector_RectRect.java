package de.s2d_advgui.core.geom.collider.ints;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.AIntersector;
import de.s2d_advgui.core.geom.collider.IntersectionContext;

public final class Intersector_RectRect extends AIntersector<Rectangle, Rectangle> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static Intersector_RectRect INSTANCE = new Intersector_RectRect();

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Intersector_RectRect getInstance() {
        return INSTANCE;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private Intersector_RectRect() {
        super(Rectangle.class, Rectangle.class);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean calculateOverlapping(IntersectionContext pContext, Rectangle pRect1, Rectangle pRect2) {
        return Intersector.overlaps(pRect1, pRect2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculateIntersections(IntersectionContext pContext, Rectangle pRect1, Rectangle pRect2) {
        if (pRect1.overlaps(pRect2)) {
            float x1 = Math.max(pRect1.x, pRect2.x);
            float x2 = Math.min(pRect1.x + pRect1.width, pRect2.x + pRect2.width);
            float y1 = Math.max(pRect1.y, pRect2.y);
            float y2 = Math.min(pRect1.y + pRect1.height, pRect2.y + pRect2.height);
            if (pContext.send(x1, y1)) return;
            if (pContext.send(x1, y2)) return;
            if (pContext.send(x2, y2)) return;
            if (pContext.send(x2, y1)) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
