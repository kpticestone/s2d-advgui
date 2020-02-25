package com.leo.spheres.core.collider;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.leo.commons.geom.Ray2D;
import com.leo.commons.utils.BiConsumerFloat;
import com.leo.spheres.core.utils.CalcUtils;

import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.function.Consumer;

public class Collider {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ColliderItem<?>[] items;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final MinMax box;

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider(@Nonnull Shape2D... shapes) {
        this(convert(shapes));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    Collider(@Nonnull ColliderItem<?>[] newItems) {
        // TODO: Sort the Items by volume... as bigger the shape is, the chance to hit em is also bigger
        this.items = newItems;

        MinMax minmax = new MinMax();
        for (ColliderItem<?> item : items) {
            minmax.append(item.getBox());
        }
        this.box = minmax;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static boolean hits(@Nonnull Collider pA, @Nonnull Collider pB) {
        return pA.hits(pB);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Rectangle getBox() {
        return this.box.toRect();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "Collider[" + Arrays.toString(this.items) + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(@Nonnull Consumer<ColliderItem<?>> pConsumer) {
        for (ColliderItem<?> item : items) {
            pConsumer.accept(item);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(@Nonnull Vector2 pPoint) {
        if (!this.box.contains(pPoint)) return false;
        for (ColliderItem<?> item : this.items) {
            if (item.contains(pPoint)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean contains(float x, float y) {
        if (!this.box.contains(x, y)) return false;
        for (ColliderItem<?> item : this.items) {
            if (item.contains(x, y)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hits(@Nonnull Collider pOther) {
        if (!this.box.overlaps(pOther.box)) return false;
        for (ColliderItem<?> itemOwn : this.items) {
            for (ColliderItem<?> itemOth : pOther.items) {
                if (itemOth != null && itemOwn.hits(itemOth)) return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public Collider getTransformed(@Nonnull Affine2 transform) {
        ColliderItem<?>[] transformed = new ColliderItem<?>[items.length];
        for (int i = 0; i < this.items.length; i++) {
            transformed[i] = this.items[i].getTransformedItem(transform);
        }
        return new Collider(transformed);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public float getDistance(@Nonnull Collider pOtherCollider) {
        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        this.getBox().getCenter(v1);
        pOtherCollider.getBox().getCenter(v2);
        return CalcUtils.distance(v1.x, v1.y, v2.x, v2.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull BiConsumerFloat pConsumer) {
        for (ColliderItem<?> item : this.items) {
            item.calcIntersectionPoints(target, pConsumer);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isEmpty() {
        return this.items.length == 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private static ColliderItem<?>[] convert(@Nonnull Shape2D[] shapes) {
        ColliderItem<?>[] collider = new ColliderItem<?>[shapes.length];
        for (int i = 0, shapesLength = shapes.length; i < shapesLength; i++) {
            collider[i] = ColliderItemFactory.createItem(shapes[i]);
        }
        return collider;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
