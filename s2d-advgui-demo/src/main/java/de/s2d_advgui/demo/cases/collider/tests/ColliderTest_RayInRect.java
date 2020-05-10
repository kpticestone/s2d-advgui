package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RayInRect extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RayInRect() {
        super("ray in rect", 2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Rectangle(ciX - 20, ciY - 20, 40, 40)));
        cols.add(new Collider(new Ray2D(ciX - 15, ciY - 15, ciX + 15, ciY + 15)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
