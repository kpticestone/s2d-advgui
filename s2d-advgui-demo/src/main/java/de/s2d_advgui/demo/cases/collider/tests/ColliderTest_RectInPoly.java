package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.utils.AffineHelper;
import de.s2d_advgui.core.utils.ShapeUtils;

public class ColliderTest_RectInPoly extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_RectInPoly() {
        super("rect in poly", 4);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        cols.add(new Collider(ShapeUtils.createGear(30, 30, 33)).getTransformed(AffineHelper.getTranslate(ciX, ciY)));
        cols.add(new Collider(new Rectangle(ciX, ciY, 10, 10)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
