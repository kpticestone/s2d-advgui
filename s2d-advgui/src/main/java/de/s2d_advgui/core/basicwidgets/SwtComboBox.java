package de.s2d_advgui.core.basicwidgets;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.awidget.ASwtWidgetDisableable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtComboBox<T> extends ASwtWidgetDisableable<SelectBox<T>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<T>> selectListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtComboBox(ISwtWidget<? extends Group> pParent) {
        super(pParent, true);
        this.addDrawerForeground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                if (isEnabled()) {
                    pBatch.setColor(getTheme().getWidgetPrimaryBorderColor());
                } else {
                    pBatch.setColor(getTheme().getWidgetPrimaryBorderColorDisabled());
                }
                pBatch.drawBorder(ATheme.BORDERS_WHITE_NB_ROUND_5_PNG, pDims);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void applyDisabledOnActor(boolean b) {
        this.actor.setDisabled(b);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected SelectBox<T> createActor() {
        BitmapFont fo = this.context.getResourceManager().getFont(.5f, true);
        Skin najs = this.context.getResourceManager().getSkin();
        SelectBoxStyle style = najs.get(SelectBoxStyle.class);
        style.background = new TextureRegionDrawable(
                this.context.getResourceManager().getColorTextureRegion(SwtColor.TRANSPARENT));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        style.font = fo;
        style.disabledFontColor = getTheme().getLabelColorDisabled();
        style.backgroundDisabled = new TextureRegionDrawable(
                getResourceManager().getColorTextureRegion(Color.BLACK));
        style.backgroundDisabled.setLeftWidth(10);
        style.backgroundDisabled.setRightWidth(10);
        style.backgroundOpen = style.background;
        style.backgroundOver = style.background;
        style.listStyle.font = fo;
        SelectBox<T> back = new SelectBox<>(style) {
            @Override
            public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent pChangeEvent, Actor pActor) {
                T value = back.getSelected();
                for (Consumer<T> a : SwtComboBox.this.selectListener) {
                    a.accept(value);
                }
            }
        });
        back.setTouchable(Touchable.enabled);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addSelectionListener(Consumer<T> pListener) {
        this.selectListener.add(pListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(Array<T> values) {
        this.actor.setItems(values);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(Collection<T> ents) {
        Array<T> use = new Array<>(ents.size());
        for (T x : ents) {
            use.add(x);
        }
        this.actor.setItems(use);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(T... ents) {
        this.actor.setItems(ents);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSelection(T pValue) {
        this.actor.setSelected(pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public T getSelected() {
        return this.actor.getSelected();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
