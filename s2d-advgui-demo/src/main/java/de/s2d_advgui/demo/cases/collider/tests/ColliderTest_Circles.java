package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Circle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_Circles extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_Circles() {
        super("four circles", 8);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Circle(ciX, ciY-30, 25)));
        cols.add(new Collider(new Circle(ciX-30, ciY, 25)));
        cols.add(new Collider(new Circle(ciX+30, ciY, 25)));
        cols.add(new Collider(new Circle(ciX, ciY+30, 25)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
