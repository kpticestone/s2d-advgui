package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

final class ActorCreatorSelectBox<T> implements IActorCreator<ExSelectBox<T>> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ExSelectBox<T> createActor(IRend123 pRend) {
        AResourceManager rm = pRend.getResourceManager();
        BitmapFont fo = rm.getFont(.5f, true);
        Skin najs = rm.getSkin();
        SelectBoxStyle style = najs.get(SelectBoxStyle.class);
        style.listStyle.selection.setBottomHeight(4);
        style.listStyle.selection.setTopHeight(4);
        style.background = new TextureRegionDrawable(
                rm.getColorTextureRegion(SwtColor.TRANSPARENT));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        style.font = fo;
        style.disabledFontColor = rm.getTheme().getLabelColorDisabled();
        style.backgroundDisabled = new TextureRegionDrawable(
                rm.getColorTextureRegion(Color.BLACK));
        style.backgroundDisabled.setLeftWidth(10);
        style.backgroundDisabled.setRightWidth(10);
        style.backgroundOpen = style.background;
        style.backgroundOver = style.background;
        style.listStyle.font = fo;
        ExSelectBox<T> back = new ExSelectBox<>(style, pRend);
        back.setTouchable(Touchable.enabled);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setEnabledOnActor(ExSelectBox<T> pActor, boolean pEnabled) {
        pActor.setDisabled(!pEnabled);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}