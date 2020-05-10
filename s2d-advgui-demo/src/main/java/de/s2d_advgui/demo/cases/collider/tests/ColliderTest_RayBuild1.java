package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RayBuild1 extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RayBuild1() {
        super("ray build 1", 2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        int steps = 10;
        float max = 90;
        float abs = max/steps;
        Set<Ray2D> shapes = new HashSet<>();
        for (int i = 0; i <= steps; i++) {
            float ak = i * abs;
            shapes.add(new Ray2D(ciX - max / 2f, ciY + ak - max / 2f, ciX - max / 2f + ak, ciY + max / 2f));
        }
        cols.add(new Collider(shapes));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
