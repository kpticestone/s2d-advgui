package com.s2dwt.core.basicwidgets;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

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
import com.s2dwt.core.SwtColor;
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.BorderDrawer2;
import com.s2dwt.core.awidget.ISwtWidget;
import com.s2dwt.core.awidget.InternalWidgetDrawerBatch;
import com.s2dwt.core.rendering.SwtDrawer_Batch;

public class SwtComboBox<T> extends ASwtWidget<SelectBox<T>>
{
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<T>> selectListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtComboBox(ISwtWidget<? extends Group> pParent)
    {
        super(pParent, true);
        BorderDrawer2 bodr = new BorderDrawer2(this.context);
        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims)
            {
                bodr.setGenericColors(enabled, isFocused(), hovered);
                bodr.drawIt(pBatch.getBatch(), pDims);
                bodr.drawIt(pBatch.getBatch(), pDims.x + pDims.width - pDims.height, pDims.y, pDims.height, pDims.height);
            }

        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected SelectBox<T> createActor()
    {
        BitmapFont fo = this.context.getResourceManager().getFont(.5f, true);
        Skin najs = this.context.getResourceManager().getSkin();
        SelectBoxStyle style = najs.get(SelectBoxStyle.class);
        style.background = new TextureRegionDrawable(this.context.getResourceManager().getColorTextureRegion(SwtColor.TRANSPARENT));
        style.background.setLeftWidth(10);
        style.background.setRightWidth(10);
        style.font = fo;
        style.listStyle.font = fo;
        SelectBox<T> back = new SelectBox<>(style)
        {
            @Override
            public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha)
            {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent pChangeEvent, Actor pActor)
            {
                T value = back.getSelected();
                for (Consumer<T> a : SwtComboBox.this.selectListener)
                {
                    a.accept(value);
                }
            }
        });
        back.setTouchable(Touchable.enabled);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addSelectionListener(Consumer<T> pListener)
    {
        this.selectListener.add(pListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(Array<T> values)
    {
        this.actor.setItems(values);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(Collection<T> ents)
    {
        Array<T> use = new Array<>(ents.size());
        for (T x : ents)
        {
            use.add(x);
        }
        this.actor.setItems(use);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setValues(T... ents)
    {
        this.actor.setItems(ents);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSelection(T pValue)
    {
        this.actor.setSelected(pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public T getSelected()
    {
        return this.actor.getSelected();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
