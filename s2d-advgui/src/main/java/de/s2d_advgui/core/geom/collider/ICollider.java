package de.s2d_advgui.core.geom.collider;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.utils.CalcUtils;
import de.s2d_advgui.core.utils.MinMax;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

public interface ICollider {
    // -------------------------------------------------------------------------------------------------------------------------
    public default boolean hits(@Nonnull ICollider pA, @Nonnull ICollider pB) {
        return pA.hits(pB);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    boolean isEmpty();

    // -------------------------------------------------------------------------------------------------------------------------
    default boolean calcIntersectionPoints(@Nonnull Ray2D target, boolean searchAll,
            @Nonnull IntersectionListener pListener) {
        IntersectionContext context = new IntersectionContext(searchAll, pListener);
        this.calcIntersectionPoints(target, context);
        return context.hasHits();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default void calcIntersectionPoints(@Nonnull Ray2D target, @Nonnull IntersectionContext pConsumer) {
        for (IColliderItem item : this.getItems()) {
            item.calculateIntersections(target, pConsumer);
            if (pConsumer.isBreaked()) return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default float getCenterDistance(float x, float y) {
        Vector2 colliderCenter = this.getBox().toRect().getCenter(new Vector2());
        return CalcUtils.distance(colliderCenter.x, colliderCenter.y, x, y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default float getCenterDistance(@Nonnull ICollider pOtherCollider) {
        Vector2 v1 = new Vector2();
        Vector2 v2 = new Vector2();
        this.getBox().toRect().getCenter(v1);
        pOtherCollider.getBox().toRect().getCenter(v2);
        return CalcUtils.distance(v1.x, v1.y, v2.x, v2.y);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    default ICollider getTransformed(@Nonnull Affine2 transform) {
        Collection<? extends IColliderItem> items = this.getItems();
        Set<IColliderItem> transformed = new LinkedHashSet<>(items.size());
        for (IColliderItem a : items) {
            transformed.add(a.getTransformedItem(transform));
        }
        return new Collider(transformed);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default boolean hits(@Nonnull ICollider pOther) {
        if (!this.getBox().overlaps(pOther.getBox())) return false;
        for (IColliderItem itemOwn : this.getItems()) {
            for (IColliderItem itemOth : pOther.getItems()) {
                if (itemOth != null && itemOwn.hits(itemOth)) return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default boolean contains(@Nonnull Vector2 pPoint) {
        if (!this.getBox().contains(pPoint)) return false;
        for (IColliderItem item : this.getItems()) {
            if (item.contains(pPoint)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default boolean contains(float x, float y) {
        if (!this.getBox().contains(x, y)) return false;
        for (IColliderItem item : this.getItems()) {
            if (item.contains(x, y)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    MinMax getBox();

    // -------------------------------------------------------------------------------------------------------------------------
    Collection<? extends IColliderItem> getItems();

    // -------------------------------------------------------------------------------------------------------------------------
    default void forEach(@Nonnull Consumer<IColliderItem> pConsumer) {
        for (IColliderItem item : this.getItems()) {
            pConsumer.accept(item);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default boolean calcIntersections(ICollider pOther, boolean searchAll, IntersectionListener il) {
        IntersectionContext context = new IntersectionContext(searchAll, il);
        this.calcIntersections(pOther, context);
        return context.hasHits();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    default void calcIntersections(ICollider pOther, IntersectionContext il) {
        il.setColliderA(this);
        il.setColliderB(pOther);
        if (!this.getBox().overlaps(pOther.getBox())) return;
        for (IColliderItem itemOwn : this.getItems()) {
            il.setColliderItemA(itemOwn);
            for (IColliderItem itemOth : pOther.getItems()) {
                il.setColliderItemB(itemOth);
                IntersectionFactory.getInstance().calculateIntersections(il, itemOwn.getShape(), itemOth.getShape());
                if (il.isBreaked()) return;
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
