package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RayOverlaps extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RayOverlaps() {
        super("ray overlaps", 2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Ray2D(ciX - 20, ciY, ciX + 10, ciY)));
        cols.add(new Collider(new Ray2D(ciX - 10, ciY, ciX + 20, ciY)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
