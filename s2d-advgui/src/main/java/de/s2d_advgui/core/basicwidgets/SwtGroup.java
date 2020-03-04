package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;

public class SwtGroup extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtGroup(ISwtWidget<? extends Group> pParent, String title) {
        super(pParent, false);

        SwtLabel label = new SwtLabel(this, title);
        label.setBounds(0, 0, 0, 25);
        label.setAlign(Align.center);
        label.setFontScale(.5f);

        SwtPanel content = new SwtPanel(this, true);
        content.setBounds(0, 15, 0, -15);

        SwtPanel inner = new SwtPanel(content, false);
        inner.setBounds(5, 5, -10, -10);

        this.setDelegatedParent(inner);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
