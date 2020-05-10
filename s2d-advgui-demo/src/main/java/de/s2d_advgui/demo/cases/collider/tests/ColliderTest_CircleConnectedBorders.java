package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Circle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_CircleConnectedBorders extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_CircleConnectedBorders() {
        super("five circles - connected borders", 4);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Circle(ciX, ciY, 10)));
        cols.add(new Collider(new Circle(ciX, ciY-20, 10)));
        cols.add(new Collider(new Circle(ciX, ciY+20, 10)));
        cols.add(new Collider(new Circle(ciX-20, ciY, 10)));
        cols.add(new Collider(new Circle(ciX+20, ciY, 10)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
