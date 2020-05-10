package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.geom.Ray2D;

public final class PolyPath {
    // -------------------------------------------------------------------------------------------------------------------------
    private int cur = 0;
    private float[] vertices;

    // -------------------------------------------------------------------------------------------------------------------------
    public PolyPath(Polygon pPoly) {
        this.vertices = pPoly.getTransformedVertices();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void reset() {
        this.cur = 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hasMorePoints() {
        return this.cur < this.vertices.length;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void getNextPoint(Vector2 pnt) {
        pnt.x = this.vertices[this.cur++];
        pnt.y = this.vertices[this.cur++];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean getNextRay(Ray2D ray) {
        if (this.cur >= this.vertices.length - 1) return false;
        ray.x1 = this.vertices[this.cur++];
        ray.y1 = this.vertices[this.cur++];
        if (this.cur >= this.vertices.length) {
            ray.x2 = this.vertices[0];
            ray.y2 = this.vertices[1];
            return true;
        }
        ray.x2 = this.vertices[this.cur];
        ray.y2 = this.vertices[this.cur + 1];
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            Rectangle rect = new Rectangle(0, 0, 100, 100);
            Polygon po = new Polygon(ShapeUtils.byRect(rect).getTransformedVertices());
            PolyPath pp = new PolyPath(po);
            Ray2D ray = new Ray2D();
            while (pp.getNextRay(ray)) {
                System.err.println(ray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
