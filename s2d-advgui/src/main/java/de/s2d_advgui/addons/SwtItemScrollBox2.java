package de.s2d_advgui.addons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;

public class SwtItemScrollBox2 extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox2(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new IActorCreator<WidgetGroup>() {
            @Override
            public WidgetGroup createActor(IRend123 pRend) {
                WidgetGroup hrh = new WidgetGroup() {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
                    }
                };
                return hrh;
            }
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
