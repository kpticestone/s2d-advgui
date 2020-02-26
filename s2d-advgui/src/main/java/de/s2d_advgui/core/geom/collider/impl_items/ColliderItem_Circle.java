package de.s2d_advgui.core.geom.collider.impl_items;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.s2d_advgui.core.geom.collider.ColliderItem;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.commons.BiConsumerFloat;
import javax.annotation.Nonnull;

public final class ColliderItem_Circle extends ColliderItem<Circle> {
    // -------------------------------------------------------------------------------------------------------------------------
    static Vector3 tmp = new Vector3();
    static Vector3 tmp1 = new Vector3();
    static Vector3 tmp2 = new Vector3();
    static Vector3 tmp3 = new Vector3();

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem_Circle(@Nonnull Circle pShape) {
        super(pShape, new Rectangle(pShape.x - pShape.radius, pShape.y - pShape.radius, pShape.radius * 2, pShape.radius * 2));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean _hits(@Nonnull ColliderItem<?> pItem) {
        if (pItem.shape instanceof Circle) return Intersector.overlaps(this.shape, (Circle) pItem.shape);
        if (pItem.shape instanceof Rectangle) return Intersector.overlaps(this.shape, (Rectangle) pItem.shape);
        if (pItem.shape instanceof Polygon) return overlaps(((Polygon) pItem.shape), this.shape);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void drawShape(@Nonnull ShapeRenderer pRenderer) {
        Circle c = this.shape;
        pRenderer.circle(c.x, c.y, c.radius, 32);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem_Circle getTransformedItem(float x, float y) {
        return new ColliderItem_Circle(new Circle(this.shape.x + x, this.shape.y + y, this.shape.radius));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public ColliderItem<?> getTransformedItem(@Nonnull Affine2 transform) {
        Vector2 center = new Vector2(this.shape.x, this.shape.y);
        transform.applyTo(center);
        return new ColliderItem_Circle(new Circle(center, this.shape.radius));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull BiConsumerFloat pConsumer) {

        if (this.shape.contains(target.x1, target.y1)) {
            pConsumer.accept(target.x1, target.y1);
        }
        if (this.shape.contains(target.x2, target.y2)) {
            pConsumer.accept(target.x2, target.y2);
        }

        BiConsumerFloat pConsumer2 = (x, y) -> {
            boolean x1 = x >= target.x1 && x <= target.x2;
            boolean y1 = y >= target.y1 && y <= target.y2;
            boolean x2 = x >= target.x2 && x <= target.x1;
            boolean y2 = y >= target.y2 && y <= target.y1;
            if ((x1 && y1) || (x2 && y2) || (x1 && y2) || (x2 && y1)) {
                pConsumer.accept(x, y);
            }
        };

        tmp.set(target.x2 - target.x1, target.y2 - target.y1, 0);
        tmp1.set(this.shape.x - target.x1, this.shape.y - target.y1, 0);
        float d = tmp.len();
        float u = tmp1.dot(tmp.nor());
        boolean inCircle = u <= 0;
        //            System.err.println("d: " + d);
        //            System.err.println("u: "  + u);
        //            double aka = Math.sqrt(u);
        //            System.err.println("aka: " + aka);
        tmp3.set(tmp.scl(u));
        float p1x = tmp3.x + target.x1;
        float p1y = tmp3.y + target.y1;
        tmp2.set(p1x, p1y, 0);
        float x = this.shape.x - tmp2.x;
        float y = this.shape.y - tmp2.y;
        boolean hasPoints = x * x + y * y <= (this.shape.radius * this.shape.radius);
        if (hasPoints) {
            float yeta = (float) (Math.atan2(target.x2 - target.x1, target.y2 - target.y1) * MathUtils.radiansToDegrees);
            float r1 = Vector3.len(this.shape.x - tmp2.x, this.shape.y - tmp2.y, 0);
            float aoa = this.shape.radius * this.shape.radius - r1 * r1;
            float wolve = Math.abs(aoa);
            float r3 = (float) Math.sqrt(wolve);
            //            System.err.println("----------------------------------");
            //            System.err.println("shape: " + this.shape.x + ", " + this.shape.y);
            //            System.err.println("d: " + d);
            //            System.err.println("u: " + u);
            //            System.err.println("x/y: " + x + ", " + y);
            //            System.err.println("aoa: " + aoa);
            //            System.err.println("wolve: " + wolve);
            //            System.err.println("intersects: " + target.x1 + ", " + target.y1 + " - " + target.x2 + ", " + target.y2);
            //            System.err.println("yeta: " + yeta);
            //            System.err.println("radius: " + this.shape.radius);
            //            System.err.println("r1: " + r1);
            //            System.err.println("r3: " + r3);
            if (r3 == 0) {
                pConsumer2.accept(tmp2.x, tmp2.y);
            } else {
                float gx = MathUtils.sin(yeta * MathUtils.degRad) * r3;
                float gy = MathUtils.cos(yeta * MathUtils.degRad) * r3;
                //                System.err.println("gx/y: " + gx + "/" + gy);
                //                gx-= this.shape.x;
                //                gy-= this.shape.y;
                //                System.err.println("gx/y: " + gx + "/" + gy);
                //                pConsumer.accept(gx, gy);
                //                pConsumer.accept(this.shape.x, this.shape.y);                
                //                System.err.println("tmp2: " + tmp2.x + ", " + tmp2.y + " (" + tmp2.len() + ")");
                //                System.err.println("tmp3: " + tmp3.x + ", " + tmp3.y + " (" + tmp3.len() + ")");
                //                pConsumer.accept(tmp2.x, tmp2.y);

                pConsumer2.accept(tmp2.x + gx, tmp2.y + gy);
                pConsumer2.accept(tmp2.x - gx, tmp2.y - gy);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
