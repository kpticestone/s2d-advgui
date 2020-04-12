package de.s2d_advgui.demo.cases.layoutmanager;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

public class SwtShowPanel extends SwtPanel {
    private SwtLabel label;
    private SwtPanel content;

    public SwtShowPanel(ISwtWidget<? extends Group> pParent, String pTitle) {
        super(pParent, true);
        this.label = new SwtLabel(this, pTitle);
        this.label.setAlign(Align.center);
        this.label.setBounds(10, 10, -20, 25);
        
        this.content = new SwtPanel(this);
        this.content.setBounds(10, 40, -20, -50);
        
        this.setDelegatedParent(this.content);
    }
}
