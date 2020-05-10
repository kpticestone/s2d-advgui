package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;
import java.util.Random;

import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_ManyRays extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_ManyRays() {
        super("many rays", -1);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        int mimimi = 50;
        float atx = ciX;
        float aty = ciY;
        Random rnd = new Random(19031979);
        for (int i = 0; i < 1000; i++) {
            int x = rnd.nextInt(mimimi * 2) - mimimi;
            int y = rnd.nextInt(mimimi * 2) - mimimi;
            int a = rnd.nextInt(mimimi * 2) - mimimi;
            int b = rnd.nextInt(mimimi * 2) - mimimi;
            Ray2D r = new Ray2D(x + atx, y + aty, a + atx, b + aty);
            Collider x1 = new Collider(r);
            cols.add(x1);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
