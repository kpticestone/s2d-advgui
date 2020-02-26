package de.s2d_advgui.addons.selectbox;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import de.s2d_advgui.addons.SwtItemPanel;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

public class SwtSelectBox<T> extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    private final IMyRenderer<T> renderer;

    // -------------------------------------------------------------------------------------------------------------------------
    SwtPanel panna = null;
    private T sel = null;
    private T[] newItems = null;
    private SwtItemPanel core = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtSelectBox(ASwtWidget<? extends Group> pParent, IMyRenderer<T> pRenderer) {
        super(pParent, false);
        this.renderer = pRenderer;
        this.core = this.doRender(this);
        this.core.addLeftClickListener(() -> {
            switchIt();
        });
        this.core.addRightClickListener(() -> {
            setSelected(null);
        });
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtItemPanel doRender(ASwtWidget<? extends WidgetGroup> pParent) {
        SwtItemPanel pan = new SwtItemPanel(pParent);
        return pan;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void switchIt() {
        if (this.panna != null) {
            this.context.releaseHook();
        } else {
            // MyItemScrollBox pan = this.panna = new MyItemScrollBox(this.parent);
            SwtPanel pan = this.panna = new SwtPanel(this.parent, false);
            pan.getActor().toFront();
            // pan.setBounds(this.x, this.y + this.h, 200, 200);
            // MyItemScrollBox2 content = pan.getContent();
            float hhe = 0;
            AX: for (T x : this.newItems) {
                if (this.sel == x)
                    continue AX;
                SwtItemPanel bb = this.doRender(pan);
                fill(bb, x);
                bb.setBounds(0, hhe, 0, 36);
                bb.addLeftClickListener(() -> {
                    switchIt();
                    setSelected(x);
                });
                hhe += 36;
            }
            pan.setBounds(this.x, this.y + this.h, 200, Math.min(150, hhe));
//          content.setBounds(0, -hhe, 100, hhe);
            this.context.registerHook(() -> {
                this.panna.dispose();
                this.panna = null;
            });
            pan.getActor().setOrigin(0, -hhe);
            pan.getActor().layout();
            
//          pan.actor.setScrollBarPositions(true, true);
//          pan.actor.setForceScroll(true, true);
//          pan.actor.layout();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void fill(SwtItemPanel bb, T x) {
        if (x == null) {
            bb.setImage(null);
            bb.setText(null);
        } else {
            String kai = this.renderer.getIconImage(x);
            bb.setImage(kai);
            bb.setText(this.renderer.getTitle(x));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSelected(T sel) {
        this.sel = sel;
        this.fill(this.core, sel);
        this.calcPositions();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setItems(T... newItems) {
        this.newItems = newItems;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
