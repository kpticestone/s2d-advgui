package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;

public class ColliderTest_RectInRect extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RectInRect() {
        super("rect in rect", 8);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(new Rectangle(ciX - 20, ciY - 20, 40, 40)));
        cols.add(new Collider(new Rectangle(ciX, ciY, 10, 10)));
        cols.add(new Collider(new Rectangle(ciX - 30, ciY - 10, 20, 20)));
        cols.add(new Collider(new Rectangle(ciX - 30, ciY - 30, 10, 10)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
