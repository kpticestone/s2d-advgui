package de.s2d_advgui.demo.cases.collider.tests;

import java.util.Collection;

import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.geom.collider.Collider;
import de.s2d_advgui.core.geom.collider.ICollider;
import de.s2d_advgui.core.utils.AffineHelper;

public class ColliderTest_OverlapedPolys extends AColliderTest {
    // -------------------------------------------------------------------------------------------------------------------------
    public ColliderTest_OverlapedPolys() {
        super("overlaped polygons", 4);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void createCollider(float ciX, float ciY, Collection<ICollider> cols) {
        Rectangle base = new Rectangle(-25, -25, 50, 50);
        cols.add(new Collider(base)
                .getTransformed(new Affine2().rotate(-45)).getTransformed(AffineHelper.getTranslate(ciX, ciY)));
        cols.add(new Collider(base)
                .getTransformed(new Affine2().rotate(0)).getTransformed(AffineHelper.getTranslate(ciX, ciY)));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
