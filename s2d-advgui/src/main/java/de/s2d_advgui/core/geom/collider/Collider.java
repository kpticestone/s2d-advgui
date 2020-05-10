package de.s2d_advgui.core.geom.collider;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Shape2D;

import de.s2d_advgui.core.utils.MinMax;

public class Collider implements ICollider {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<IColliderItem> items;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final MinMax box;

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider(@Nonnull Shape2D... shapes) {
        this(ColliderItemFactory.convert(shapes));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider(@Nonnull Collection<? extends Shape2D> shapes) {
        this(ColliderItemFactory.convert(shapes));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider(@Nonnull IColliderItem[] newItems) {
        this(ColliderItemFactory.convert2(newItems));
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public Collider(Set<IColliderItem> newItems) {
        this.items = newItems;
        MinMax minmax = new MinMax();
        for (IColliderItem item : this.items) {
            minmax.append(item.getBox());
        }
        this.box = minmax;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public MinMax getBox() {
        return this.box;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "Collider[" + this.items + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Collection<IColliderItem> getItems() {
        return this.items;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
