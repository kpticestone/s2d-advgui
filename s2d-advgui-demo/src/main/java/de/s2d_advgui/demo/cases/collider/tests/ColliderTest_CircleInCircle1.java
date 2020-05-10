package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Circle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_CircleInCircle1 extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_CircleInCircle1() {
        super("circle in circle 1", 3);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Circle(ciX, ciY, 20)));
        cols.add(new Collider(new Circle(ciX, ciY, 15)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
