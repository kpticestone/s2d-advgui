package de.s2d_advgui.demo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.animations.IAnimationListener_Close;
import de.s2d_advgui.commons.IInfoSupport;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.geom.animations.AnimationListener_RectangleSupport;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.screens.SwtScreen;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;
import de.s2d_advgui.singstar.IC_ClazzRegisterObjectContainer;
import de.s2d_advgui.singstar.SingStar;

public class DemoScreen extends SwtScreen<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static IC_ClazzRegisterObjectContainer<ASwtWidgetTestCase> CONT = SingStar.getInstance()
            .getObjectContainer(ASwtWidgetTestCase.class);

    // -------------------------------------------------------------------------------------------------------------------------
    private Map<String, DemoTab> x1s = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    DemoTab lastX1 = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoScreen(ASwtStage<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pStage) {
        super(pStage);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onFirstCalculationDone() {
        super.onFirstCalculationDone();

        float wii = this.actor.getWidth();
        float hee = this.actor.getHeight();

        int aty = 0;
        for (ASwtWidgetTestCase someTestCase : CONT.values()) {

            IInfoSupport infoSupport = someTestCase.getInfo();

            SwtButton btn = new SwtButton(this);
            btn.setText(infoSupport.getLabel());
            btn.setBounds(0, aty, 150, 30);

            DemoTab x1 = new DemoTab(someTestCase);

            x1.oo = new Rectangle(150, aty, 30, 30);
            x1.button = btn;

            SwtPanel border = new SwtPanel(this, false);
            border.setLayoutNegativePositions(false);
            border.setBounds(x1.oo);
            x1.border = border;

            btn.addLeftClickListener(() -> {
                activate(x1);
            });

            this.x1s.put(someTestCase.getID(), x1);

            aty += 35;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void activate(DemoTab x1) {
        float wii = this.actor.getWidth();
        float hee = this.actor.getHeight();

        DemoTab ello = this.lastX1;
        if (ello != null) {
            ello.button.setEnabled();
            Rectangle dst = ello.oo;
            Animation_ChangeBounds ani = new Animation_ChangeBounds(0f, 500f, ello.border.getBounds(), dst,
                    new AnimationListener_RectangleSupport(ello.border));
            ani.setCloseListener(new IAnimationListener_Close() {
                @Override
                public void onAnimationClosed() throws Exception {
                    ello.border.disposeChildren();
                }
            });
            AnimationManager.getInstance().startAnimation(ani);
        }

        x1.someTestCase.buildTests(x1.border);
        x1.button.setDisabled();

        SwtPanel border = x1.border;

        this.lastX1 = x1;

        Rectangle dst = new Rectangle(200, 0, wii - 200, hee);
        Animation_ChangeBounds ani = new Animation_ChangeBounds(0f, 1000f, border.getBounds(), dst,
                new AnimationListener_RectangleSupport(border));
        AnimationManager.getInstance().startAnimation(ani);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
