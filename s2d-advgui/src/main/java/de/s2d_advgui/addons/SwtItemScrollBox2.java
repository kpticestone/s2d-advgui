package de.s2d_advgui.addons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import de.s2d_advgui.core.awidget.ASwtWidget;

public class SwtItemScrollBox2 extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox2(ASwtWidget<? extends Group> pParent) {
        super(pParent, true);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup createActor() {
        WidgetGroup hrh = new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        return hrh;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
