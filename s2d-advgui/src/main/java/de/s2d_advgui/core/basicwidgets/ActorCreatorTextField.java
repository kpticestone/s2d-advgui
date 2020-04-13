package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ATheme;

final class ActorCreatorTextField implements IActorCreator<TextField> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public TextField createActor(IRend123 pRend) {
        AResourceManager rm = pRend.getResourceManager();
        TextFieldStyle style = new TextFieldStyle();
        BitmapFont fo = rm.getFont(.5f, true);
        style.font = fo;
        style.fontColor = Color.WHITE;
        style.font = fo;
        style.cursor = rm.getDrawable(ATheme.UI_PIPE1_PNG);
        style.messageFont = fo;
        style.messageFontColor = Color.BLUE;
        style.selection = rm.getDrawable(ATheme.UI_PIPE1_PNG);
        style.background = null;
        style.focusedFontColor = Color.WHITE;
        style.disabledFontColor = rm.getTheme().getLabelColorDisabled();
        style.disabledBackground = null;
        style.focusedBackground = null;
        style.background = new TextureRegionDrawable(
                rm.getColorTextureRegion(SwtColor.TRANSPARENT, 0, 0));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        TextField back = new TextField(null, style) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(TextField pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}