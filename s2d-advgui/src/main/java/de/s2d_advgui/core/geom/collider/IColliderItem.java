package de.s2d_advgui.core.geom.collider;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

import de.s2d_advgui.core.geom.Ray2D;

public interface IColliderItem {
    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle getBox();

    // -------------------------------------------------------------------------------------------------------------------------
    boolean contains(Vector2 pPoint);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean contains(float x, float y);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean hits(IColliderItem pItem);

    // -------------------------------------------------------------------------------------------------------------------------
    void drawShape(ShapeRenderer pRenderer);

    // -------------------------------------------------------------------------------------------------------------------------
    Shape2D getShape();

    // -------------------------------------------------------------------------------------------------------------------------
    void calculateIntersections(Ray2D pRay, IntersectionContext pContext);

    // -------------------------------------------------------------------------------------------------------------------------
    IColliderItem getTransformedItem(Affine2 transform);

    // -------------------------------------------------------------------------------------------------------------------------
    Shape createBox2dShape();

    // -------------------------------------------------------------------------------------------------------------------------
}
