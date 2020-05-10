package de.s2d_advgui.core.geom.collider;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import de.s2d_advgui.core.utils.ShapeUtils;

public abstract class ColliderItem<T extends Shape2D> implements IColliderItem {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final T shape;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final Rectangle box;

    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderItem(@Nonnull T pShape, @Nonnull Rectangle box, boolean explodeBox) {
        this.shape = pShape;
        if (explodeBox) {
            this.box = ShapeUtils.explode(box, -.01f);
        } else {
            this.box = box;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public T getShape() {
        return this.shape;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public Rectangle getBox() {
        return this.box;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.box + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(@Nonnull Vector2 pPoint) {
        if (!this.box.contains(pPoint)) return false;
        return this.shape.contains(pPoint);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean contains(float x, float y) {
        if (!this.box.contains(x, y)) return false;
        return this.shape.contains(x, y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean hits(@Nonnull IColliderItem pItem) {
        if (!this.box.overlaps(pItem.getBox())) return false;
        return IntersectionFactory.getInstance().calculateOverlapping(this.shape, pItem.getShape());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public abstract void drawShape(@Nonnull ShapeRenderer pRenderer);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public abstract IColliderItem getTransformedItem(@Nonnull Affine2 transform);

    // -------------------------------------------------------------------------------------------------------------------------
}
