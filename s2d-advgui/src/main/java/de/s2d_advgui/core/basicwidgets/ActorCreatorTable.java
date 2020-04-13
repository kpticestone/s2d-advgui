package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.ATheme;

final class ActorCreatorTable implements IActorCreator<Table> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Table createActor(IRend123 pRend) {
        Table back = new Table() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.setBackground(pRend.getResourceManager().getDrawable(ATheme.UI_SLOT_TYPE_MINOR_PNG));
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(Table pActor, boolean pEnabled) {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
}