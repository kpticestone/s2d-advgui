package com.leo.spheres.core.collider;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.leo.commons.utils.TNull;
import com.leo.spheres.core.collider.impl_items.ColliderItem_Circle;
import com.leo.spheres.core.collider.impl_items.ColliderItem_Polygon;
import com.leo.spheres.core.collider.impl_items.ColliderItem_Rectangle;

import javax.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

public final class ColliderItemFactory {
    // -------------------------------------------------------------------------------------------------------------------------
    static Map<Class<? extends Shape2D>, Shape2DHandler<? extends Shape2D>> handlers = new HashMap<>();

    static {
        handlers.put(Circle.class, (Shape2DHandler<Circle>) ColliderItem_Circle::new);
        handlers.put(Rectangle.class, (Shape2DHandler<Rectangle>) ColliderItem_Rectangle::new);
        handlers.put(Polygon.class, (Shape2DHandler<Polygon>) ColliderItem_Polygon::new);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private ColliderItemFactory() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    public final static <TS extends Shape2D> ColliderItem<TS> createItem(TS pShape) {
        Class<TS> theClazz = (Class<TS>) pShape.getClass();
        Shape2DHandler<TS> handler = TNull.checkNull((Shape2DHandler<TS>) handlers.get(theClazz));
        return handler.detectBoxBoundaries(pShape);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    interface Shape2DHandler<T extends Shape2D> {
        ColliderItem<T> detectBoxBoundaries(@Nonnull T pShape);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
