package de.s2d_advgui.core.screens;

import java.util.Set;

import javax.annotation.Nonnull;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.animations.AnimationGroup_Sequence;
import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.impl.Animation_Sleep;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.geom.animations.AnimationListener_RectangleSupport;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ResourceLoader;
import de.s2d_advgui.core.stage.ASwtStage;

public class SphScreen_Loading<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>>
        extends SwtScreen<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    private SwtLabel label;

    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreen_Loading(@Nonnull ASwtStage<RM, DM> pStage) {
        super(pStage);
//        SwtImage logo = new SwtImage(this);
//        logo.setImage("splashscreens/s2d-advgui.jpg");
//        logo.setBounds(0, 0, 0, 0);
//        logo.setScalingMode(Scaling.fill);

        this.label = new SwtLabel(this);
        this.label.setText("s2d-advgui loading resources...");
        this.label.setAlign(Align.center);
        this.label.setBounds(0, -150, 0, 150);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onFirstCalculationDone() {
        int boxWi = 300;
        int boxHe = 100;
        float wiwi = this.actor.getWidth();
        float hehe = this.actor.getHeight();
        float heha = (hehe - boxHe) / 2f;
        AnimationGroup_Sequence animGroup = new AnimationGroup_Sequence();
        Set<ResourceLoader> myLoaders = this.drawerManager.getResourceManager().getLoaders();
        for (ResourceLoader ll : myLoaders) {
            Rectangle src = new Rectangle(-boxWi, heha, boxWi, boxHe);
            Rectangle half = new Rectangle((wiwi - boxWi) / 2f, heha, boxWi, boxHe);
            Rectangle dst = new Rectangle(wiwi, heha, boxWi, boxHe);

            SwtLoadingBox someBox = new SwtLoadingBox(this, ll);
            someBox.setLayoutNegativePositions(false);
            someBox.setBounds(src);

            AnimationListener_RectangleSupport r1 = new AnimationListener_RectangleSupport(someBox);
            animGroup.add(new Animation_ChangeBounds(0f, 100f, src, half, r1));
            animGroup.add(new Animation_Sleep(0f, 500f, () -> ll.doLoad()));
            animGroup.add(new Animation_ChangeBounds(0f, 100f, half, dst, r1));
        }

        animGroup.setCloseListener(() -> {
            this.label.setText("loadings ready... press any key or button");
            this.applicationController.registerLoadReady();
        });
        AnimationManager.getInstance().startAnimation(animGroup);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
