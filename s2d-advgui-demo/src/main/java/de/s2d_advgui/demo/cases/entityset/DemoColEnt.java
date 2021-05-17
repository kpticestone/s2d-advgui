package de.s2d_advgui.demo.cases.entityset;

import java.util.Collections;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.leo.spheres.core.chunksystem.IColEnt;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Circle;

class DemoColEnt implements IColEnt {
    float x;
    float y;
    Vector2 direction;

    @Override
    public ICollider getCollider() {
        ColliderItem_Circle ci = new ColliderItem_Circle(new Circle(x, y, 2));
        return new Collider(Collections.singleton(ci));
    }

    @Override
    public boolean canHit() {
        return true;
    }
}