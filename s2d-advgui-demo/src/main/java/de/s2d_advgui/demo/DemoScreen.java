package de.s2d_advgui.demo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.animations.AnimationManager;
import de.s2d_advgui.commons.IInfoSupport;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.geom.animations.AnimationListener_RectangleSupport;
import de.s2d_advgui.core.geom.animations.Animation_ChangeBounds;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.screens.SwtScreen;
import de.s2d_advgui.core.stage.ASwtStage;
import de.s2d_advgui.singstar.IC_ClazzRegisterObjectContainer;
import de.s2d_advgui.singstar.SingStar;

public class DemoScreen extends SwtScreen<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final static IC_ClazzRegisterObjectContainer<ASwtWidgetTestCase> CONT = SingStar.getInstance()
            .getObjectContainer(ASwtWidgetTestCase.class);

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoScreen(ASwtStage<DemoResourceManager, ISwtDrawerManager<DemoResourceManager>> pStage) {
        super(pStage);
    }

    static class X1 {
        ASwtWidgetTestCase someTestCase;
        SwtButton button;
        SwtPanel border;
        Rectangle oo;

        public X1(ASwtWidgetTestCase someTestCase) {
            this.someTestCase = someTestCase;
        }
    }

    private Map<String, X1> x1s = new HashMap<>();

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

            X1 x1 = new X1(someTestCase);

            x1.oo = new Rectangle(150, aty, 30, 30);
            x1.button = btn;

            SwtPanel border = new SwtPanel(this, true);
            border.setLayoutNegativePositions(false);
            border.setBounds(x1.oo);

            someTestCase.buildTests(border);

            x1.border = border;

            btn.addLeftClickListener(() -> {
                activate(x1);
            });

            this.x1s.put(someTestCase.getID(), x1);

            aty += 35;
        }
    }

//    SwtButton lastButton = null;
//    SwtPanel lastPanel = null;

    X1 lastX1 = null;

    void activate(X1 x1) {
        float wii = this.actor.getWidth();
        float hee = this.actor.getHeight();

        if (this.lastX1 != null) {
            this.lastX1.button.setEnabled();
            Rectangle dst = this.lastX1.oo;
            Animation_ChangeBounds ani = new Animation_ChangeBounds(0f, 500f, this.lastX1.border.getBounds(), dst,
                    new AnimationListener_RectangleSupport(this.lastX1.border));
            AnimationManager.getInstance().startAnimation(ani);
        }

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
