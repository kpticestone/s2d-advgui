package de.s2d_advgui.core.basicwidgets;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Array;

import de.s2d_advgui.core.awidget.ASwtWidgetDisableable;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtComboBox<T> extends ASwtWidgetDisableable<SelectBox<T>> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<T>> selectListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtComboBox(ISwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new ActorCreatorSelectBox<T>()));
        this.registerChangeEventHandler(event -> {
            T value = SwtComboBox.this.actor.getSelected();
            for (Consumer<T> a : SwtComboBox.this.selectListener) {
                a.accept(value);
            }
        });
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
