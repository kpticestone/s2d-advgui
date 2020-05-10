package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.ShapeUtils;

public class ColliderTest_PolyInRect extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_PolyInRect() {
        super("poly in rect", 1);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(ShapeUtils.createGear(4, 10, 20)).getTransformed(AffineHelper.getTranslate(ciX, ciY)));
        cols.add(new Collider(new Rectangle(ciX - 30, ciY - 30, 60, 60)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
