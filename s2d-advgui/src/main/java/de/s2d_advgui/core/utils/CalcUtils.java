package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import de.s2d_advgui.commons.BiConsumerFloat;

import java.awt.geom.Point2D;

/**
 * math stuff
 */
public class CalcUtils {
    // -------------------------------------------------------------------------------------------------------------------------
    public final static float g = 9.80665f;

    // -------------------------------------------------------------------------------------------------------------------------
    public final static float g_half = g * .5f;

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * random value between 0 and 360
     */
    public static float randomAngle() {
        return (float) (Math.random() * 360F);
    }


    public static float getAngle(Vector2 vector) {
        return getAngle(vector.x, vector.y);
    }

    /**
     * get rotation of gameobject around 0/0
     */
    public static float getAngle(float x, float y) {
        return getAngle(x, y, 0, 0);
    }

    public static float getAngle(float x1, float y1, float x2, float y2) {
        return translateRotation((float) (Math.atan2(y1 - y2, x1 - x2) * MathUtils.radiansToDegrees));
    }

    /**
     * make sure rotation is between 0 and 360
     */
    public static float translateRotation(float degrees) {
        while (degrees > 360f) {
            degrees -= 360f;
        }
        while (degrees < 0f) {
            degrees += 360f;
        }
        return degrees;
    }

    /**
     * distance between to angles.
     * e.g. a=340, b=10, result: -30
     */
    public static float angleDistance(float a, float b) {
        float d = Math.abs(a - b) % 360f;
        float r = d > 180f ? 360f - d : d;
        int sign = (a - b >= 0f && a - b <= 180f) || (a - b <= -180f && a - b >= -360f) ? 1 : -1;
        return r * sign;
    }

    public static float distance(Vector2 p1, Vector2 p2) {
        return distance(p1.x, p1.y, p2.x, p2.y);
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static float roundish(float v) {
        return Math.round(v * 100F) / 100F;
    }

    public static String roundish2(float v) {
        return String.format("%.2f", v);
    }

    public static float approachExponential(float current, float wanted, float delta) {
        if (current == wanted) {
            return current;
        }
        return approach(current, wanted, Math.max(delta * Math.abs(wanted - current), 0.0005F));
    }

    public static float approach(float current, float wanted, float speed) {
        if (current != wanted) {
            if (current > wanted) {
                return Math.max(current - speed, wanted);
            } else {
                return Math.min(current + speed, wanted);
            }
        }
        return current;
    }

    public static Vector2 calcPoint(Vector2 vec, float angle, float radius) {
        return vec.set(radius * ((float) Math.cos(angle * MathUtils.degreesToRadians)), radius * ((float) Math.sin(angle * MathUtils.degreesToRadians)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static Point2D.Float getCoordByRange(Point2D p1, float range, float angle) {
        return new Point2D.Float((float) (p1.getX() + MathUtils.cos(angle) * range), (float) (p1.getY() + MathUtils.sin(angle) * range));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static Point2D[] intersect2Circles(Point2D A, double a, Point2D B, double b) {
        double AB0 = B.getX() - A.getX();
        double AB1 = B.getY() - A.getY();
        double c = Math.sqrt(AB0 * AB0 + AB1 * AB1);
        if (c == 0) return null;

        double x = (a * a + c * c - b * b) / (2 * c);
        double y = a * a - x * x;
        if (y < 0) return null;

        if (y > 0) y = Math.sqrt(y);

        double ex0 = AB0 / c;
        double ex1 = AB1 / c;
        double ey0 = -ex1;
        double ey1 = ex0;
        double Q1x = A.getX() + x * ex0;
        double Q1y = A.getY() + x * ex1;
        if (y == 0) return new Point2D[] { new Point2D.Double(Q1x, Q1y) };

        double Q2x = Q1x - y * ey0;
        double Q2y = Q1y - y * ey1;
        Q1x += y * ey0;
        Q1y += y * ey1;
        return new Point2D[] { new Point2D.Double(Q1x, Q1y), new Point2D.Double(Q2x, Q2y) };
    }

    // http://antigrain.com/research/adaptive_bezier/#toc0003
    // -------------------------------------------------------------------------------------------------------------------------
    public static void bezier3p(Point2D p1, Point2D p2, Point2D p3, float t, BiConsumerFloat pCallback) {
        Point2D[] p = { p1, p2, p3 };
        float nt = 1 - t;
        float qt = nt * nt;
        float dt = t * t;
        double x = qt * p[0].getX() + 2 * nt * t * p[1].getX() + dt * p[2].getX();
        double y = qt * p[0].getY() + 2 * nt * t * p[1].getY() + dt * p[2].getY();
        pCallback.accept((float) x, (float) y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void bezier4p(Point2D p1, Point2D p2, Point2D p3, Point2D p4, float t, BiConsumerFloat pCallback) {
        Point2D[] p = { p1, p2, p3, p4 };
        double x = (1 - t) * (1 - t) * (1 - t) * p[0].getX() + 3 * (1 - t) * (1 - t) * t * p[1].getX() + 3 * (1 - t) * t * t * p[2].getX() + t * t * t * p[3].getX();
        double y = (1 - t) * (1 - t) * (1 - t) * p[0].getY() + 3 * (1 - t) * (1 - t) * t * p[1].getY() + 3 * (1 - t) * t * t * p[2].getY() + t * t * t * p[3].getY();
        pCallback.accept((float) x, (float) y);
    }

    // -----------------------------------------------------------------------------------------------------------
    public static Point2D getCoordByRange2f(Point2D p1, float range, float angle) {
        Point2D.Double back = new Point2D.Double();
        back.x = (p1.getX() + MathUtils.cos(angle) * range);
        back.y = (p1.getY() + MathUtils.sin(angle) * range);
        return back;
    }

    public static float sign(float wantedZoom) {
        return (wantedZoom < 0) ? -1F : 1F;
    }

    // -----------------------------------------------------------------------------------------------------------
    public static float normalizeDegrees(float pInput) {
        float back = pInput;
        while (back < 0) {
            back += 360;
        }
        return back % 360f;
    }

    // -----------------------------------------------------------------------------------------------------------
}
