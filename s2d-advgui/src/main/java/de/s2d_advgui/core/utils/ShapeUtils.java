package de.s2d_advgui.core.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ShapeUtils {
    // -------------------------------------------------------------------------------------------------------------------------
    public static boolean hits(Polygon polygon1, Polygon polygon2) {
        return polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())
                && Intersector.overlapConvexPolygons(polygon1, polygon2);
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
        return new Polygon(new float[] { x1, y1, x2, y1, x2, y2, x1, y2 });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Polygon getPolygonNotch(float x1, float y1, float x2, float y2) {
        float d = .2f;
        return new Polygon(new float[] { x1, y1 + d, x1 + d, y1, x2, y1, x2, y2, x1, y2 });
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
        return new Polygon(new float[] {
                pRect.x, pRect.y, //
                pRect.x + pRect.width, pRect.y, //
                pRect.x + pRect.width, pRect.y + pRect.height, //
                pRect.x, pRect.y + pRect.height //
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Polygon clone2(Polygon shape, Affine2 transform) {
        float[] org = shape.getTransformedVertices();
        float[] cpy = new float[org.length];
        System.arraycopy(org, 0, cpy, 0, org.length);
        Vector2 vec = new Vector2();
        for (int i = 0, n = cpy.length; i < n; i += 2) {
            vec.set(cpy[i], cpy[i + 1]);
            transform.applyTo(vec);
            cpy[i] = vec.x;
            cpy[i + 1] = vec.y;
        }
        return new Polygon(cpy);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static Rectangle explode(@Nonnull Rectangle pDims, float pPix) {
        float dup = pPix * 2;
        return new Rectangle(pDims.x + pPix, pDims.y + pPix, pDims.width - dup, pDims.height - dup);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final static Rectangle explode(@Nonnull Rectangle pDims, float pPixW, float pPixH) {
        float dupW = pPixW * 2;
        float dupH = pPixH * 2;
        return new Rectangle(pDims.x + pPixW, pDims.y + pPixH, pDims.width - dupW, pDims.height - dupH);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Rectangle explode(Vector2 pnt, float surroundSize) {
        float dup = surroundSize * 2;
        return new Rectangle(pnt.x-surroundSize, pnt.y-surroundSize, dup, dup);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Rectangle explode(Vector2 pnt, float surroundSizeX, float surroundSizeY) {
        float dupW = surroundSizeX * 2;
        float dupH = surroundSizeY * 2;
        return new Rectangle(pnt.x-surroundSizeX, pnt.y-surroundSizeY, dupW, dupH);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Polygon createGear(int zaehne, double rFuss, float rKopf) {
        List<Float> myStar = new ArrayList<>();

        double fiver = (Math.PI / zaehne) / 2.0f;
        double dif = fiver / 2d;

        for (int i = 0; i < zaehne * 4; i += 4) {
            double kopf_1_x = rKopf * Math.cos((i + 0) * fiver - dif);
            double kopf_1_y = rKopf * Math.sin((i + 0) * fiver - dif);

            double kopf_2_x = rKopf * Math.cos((i + 1) * fiver - dif);
            double kopf_2_y = rKopf * Math.sin((i + 1) * fiver - dif);

            double fuss_1_x = rFuss * Math.cos((i + 2) * fiver - dif);
            double fuss_1_y = rFuss * Math.sin((i + 2) * fiver - dif);

            double fuss_2_x = rFuss * Math.cos((i + 3) * fiver - dif);
            double fuss_2_y = rFuss * Math.sin((i + 3) * fiver - dif);

            if (i == 0) {
                myStar.add((float) kopf_1_x);
                myStar.add((float) kopf_1_y);
            } else {
                myStar.add((float) kopf_1_x);
                myStar.add((float) kopf_1_y);
            }
            myStar.add((float) kopf_2_x);
            myStar.add((float) kopf_2_y);
            myStar.add((float) fuss_1_x);
            myStar.add((float) fuss_1_y);
            myStar.add((float) fuss_2_x);
            myStar.add((float) fuss_2_y);
        }

        float[] verts = new float[myStar.size()];
        for (int i = 0; i < myStar.size(); i++) {
            Float any = myStar.get(i);
            verts[i] = any.floatValue();
        }
        return new Polygon(verts);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
