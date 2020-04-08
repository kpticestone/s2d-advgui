package de.s2d_advgui.core.window;

import java.util.Collection;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public final class SwtWindowBottomPanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    private SwtButton buttonClose;

    // -------------------------------------------------------------------------------------------------------------------------
    private Trigger closeTrigger;

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean panelVisible = false;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Trigger panelButtonTrigger;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindowBottomPanel(ISwtWidget<? extends Group> pParent, Trigger pPanelButtonTrigger,
            Trigger pCloseTrigger) {
        super(pParent);
        this.panelButtonTrigger = pPanelButtonTrigger;
        this.closeTrigger = pCloseTrigger;
        this.setLayoutManager(new ASwtLayoutManager() {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                int ax = 5;
                int wi = 100;
                int space = 5;
                Collection<ISwtWidget<?>> chx = pWidget.getChildren();
                SwtWindowBottomPanel.this.panelVisible = !chx.isEmpty();
                for (ISwtWidget<?> some : pWidget.getChildren()) {
                    some.setBounds(ax, 5, wi, 25);
                    ax += wi + space;
                }
            }
        });

        ATheme theme = this.context.getResourceManager().getTheme();
        TextureRegion bl = this.context.getResourceManager().getColorTextureRegion(theme.getWidgetPrimaryBackgroundColor());
        this.addDrawerClipableBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                if (SwtWindowBottomPanel.this.panelVisible) {
                    pBatch.draw(bl, pDims);
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void onDoAdd() {
        if (this.panelVisible) return;
        Collection<ISwtWidget<?>> chx = this.getChildren();
        boolean nw = !chx.isEmpty();
        if (nw) {
            this.panelVisible = true;
            this.panelButtonTrigger.onTrigger();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean isPanelVisible() {
        return this.panelVisible;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(String text, Trigger trigger) {
        SwtButton save = new SwtButton(this, text != null ? text : "SAVE");
        save.addLeftClickListener(() -> {
            if (trigger != null) {
                trigger.onTrigger();
            }
            this.closeTrigger.onTrigger();
        });
        this.onDoAdd();
        return save;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(Trigger trigger) {
        return this.addSaveButton(null, trigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(String text) {
        return this.addSaveButton(text, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(String text, Trigger trigger) {
        this.buttonClose = new SwtButton(this, text != null ? text : "CANCEL");
        this.buttonClose.addLeftClickListener(() -> {
            if (trigger != null) {
                trigger.onTrigger();
            }
            this.closeTrigger.onTrigger();
        });
        this.onDoAdd();
        return this.buttonClose;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton() {
        return this.addCloseButton(null, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(Trigger trigger) {
        return this.addCloseButton(null, trigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(String text) {
        return this.addCloseButton(text, null);
    }

    // public SwtButton addButton(String label, Trigger trigger) {
    // SwtButton back = new SwtButton(this.buttonPanel);
    // back.setText(label);
    // back.addLeftClickListener(()->{
    //
    // });
    // return back;
    // }
    //
    // -------------------------------------------------------------------------------------------------------------------------
}
