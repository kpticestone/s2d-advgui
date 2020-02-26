package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import javax.annotation.Nonnull;

public class PolygonUtils {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Vector3 tmp = new Vector3();

    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean hits(Polygon polygon1, Polygon polygon2) {
        return polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle()) && Intersector.overlapConvexPolygons(polygon1, polygon2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Polygon getRoundPolygon(float x, float y, float x2, float y2) {
        float[] arr = new float[360 * 2];
        int j = 0;
        float widthHalf = (x2 - x) / 2;
        float heightHalf = (y2 - y) / 2;
        for (int i = 0; i < 360; i += 1) {
            arr[j++] = (x + widthHalf) + MathUtils.cos(i * MathUtils.degRad) * widthHalf;
            arr[j++] = (y + heightHalf) + MathUtils.sin(i * MathUtils.degRad) * heightHalf;
        }
        return new Polygon(arr);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public static Polygon getPolygon(float x1, float y1, float x2, float y2) {
        return new Polygon(new float[]{x1, y1, x2, y1, x2, y2, x1, y2});
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Polygon getPolygonNotch(float x1, float y1, float x2, float y2) {
        float d = .2f;
        return new Polygon(new float[]{x1, y1 + d, x1 + d, y1, x2, y1, x2, y2, x1, y2});
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Polygon clone(Polygon polygon) {
        Polygon clone = new Polygon(polygon.getVertices());
        clone.setPosition(polygon.getX(), polygon.getY());
        clone.setRotation(polygon.getRotation());
        return clone;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static final Polygon byRect(@Nonnull Rectangle pRect) {
        return new Polygon(new float[]{pRect.x, pRect.y, //
                pRect.x + pRect.width, pRect.y, //  
                pRect.x + pRect.width, pRect.y + pRect.height, //  
                pRect.x, pRect.y + pRect.height // 
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
