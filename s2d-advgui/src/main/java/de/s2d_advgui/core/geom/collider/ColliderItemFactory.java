package de.s2d_advgui.core.geom.collider;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

import de.s2d_advgui.commons.TNull;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Circle;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Polygon;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Ray2D;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Rectangle;

public final class ColliderItemFactory {
    // -------------------------------------------------------------------------------------------------------------------------
    static Map<Class<? extends Shape2D>, Shape2DHandler<? extends Shape2D>> handlers = new HashMap<>();
    static {
        handlers.put(Circle.class, (Shape2DHandler<Circle>) ColliderItem_Circle::new);
        handlers.put(Rectangle.class, (Shape2DHandler<Rectangle>) ColliderItem_Rectangle::new);
        handlers.put(Polygon.class, (Shape2DHandler<Polygon>) ColliderItem_Polygon::new);
        handlers.put(Ray2D.class, (Shape2DHandler<Ray2D>) ColliderItem_Ray2D::new);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public final static <TS extends Shape2D> ColliderItem<TS> createItem(TS pShape) {
        Class<TS> theClazz = (Class<TS>) pShape.getClass();
        Shape2DHandler<TS> handler = TNull.checkNull((Shape2DHandler<TS>) handlers.get(theClazz));
        return handler.detectBoxBoundaries(pShape);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    static IColliderItem[] convert(@Nonnull Shape2D[] shapes) {
        IColliderItem[] collider = new IColliderItem[shapes.length];
        for (int i = 0, shapesLength = shapes.length; i < shapesLength; i++) {
            collider[i] = createItem(shapes[i]);
        }
        return collider;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    static IColliderItem[] convert(@Nonnull Collection<? extends Shape2D> shapes) {
        IColliderItem[] collider = new IColliderItem[shapes.size()];
        int i = 0;
        for (Shape2D s : shapes) {
            collider[i++] = createItem(s);
        }
        return collider;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    static Set<IColliderItem> convert2(IColliderItem[] newItems) {
        // TODO: Sort the Items by volume... as bigger the shape is, the chance to hit
        // em is also bigger
        Set<IColliderItem> l = new LinkedHashSet<>(newItems.length);
        for (IColliderItem x : newItems) {
            l.add(x);
        }
        return l;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private ColliderItemFactory() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
