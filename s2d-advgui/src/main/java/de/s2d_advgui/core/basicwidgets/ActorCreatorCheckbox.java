package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;

import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

final class ActorCreatorCheckbox implements IActorCreator<CheckBox> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public CheckBox createActor(IRend123 pRend) {
        AResourceManager rm = pRend.getResourceManager();
        CheckBoxStyle cbs = new CheckBoxStyle();
        cbs.font = rm.getFont(.5f, false);
        cbs.checkedOffsetX = 0;
        cbs.checkboxOff = rm.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_GREEN_PNG);
        cbs.checkboxOff.setMinWidth(16);
        cbs.checkboxOff.setMinHeight(16);
        cbs.checkboxOff.setLeftWidth(100);
        cbs.checkboxOn = rm.getDrawable(ATheme.ICONS_128_SIGNALING_DISK_RED_PNG);
        cbs.checkboxOn.setMinHeight(16);
        cbs.checkboxOn.setMinWidth(16);
        CheckBox back = new CheckBox(null, cbs) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> {
                    // DON
                });
            }
        };
        back.getLabel().setFontScale(.75f);
        back.left();
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(CheckBox pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}