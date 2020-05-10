package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RectOverlaps extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RectOverlaps() {
        super("rect overlaps", 4);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Rectangle(ciX+-30, ciY+-30, 40, 40)));
        cols.add(new Collider(new Rectangle(ciX+-10, ciY+-10, 40, 40)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
