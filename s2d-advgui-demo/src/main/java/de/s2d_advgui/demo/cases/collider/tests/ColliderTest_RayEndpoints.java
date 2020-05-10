package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RayEndpoints extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RayEndpoints() {
        super("ray end points", 4);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Ray2D(ciX, ciY + 20, ciX + 20, ciY)));
        cols.add(new Collider(new Ray2D(ciX, ciY - 20, ciX + 20, ciY)));
        cols.add(new Collider(new Ray2D(ciX, ciY + 20, ciX - 20, ciY)));
        cols.add(new Collider(new Ray2D(ciX, ciY - 20, ciX - 20, ciY)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
