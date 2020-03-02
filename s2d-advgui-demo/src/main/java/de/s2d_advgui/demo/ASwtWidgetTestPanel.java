package de.s2d_advgui.demo;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

public abstract class ASwtWidgetTestPanel extends SwtPanel {
    public ASwtWidgetTestPanel(ISwtWidget<? extends Group> pParent) {
        super(pParent, true);
    }
}
