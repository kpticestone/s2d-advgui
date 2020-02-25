package com.s2dwt.addons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.ISwtWidget;
import com.s2dwt.core.basicwidgets.SwtLabel;
import com.s2dwt.core.basicwidgets.SwtPanel;
import com.s2dwt.core.basicwidgets.SwtTextField;
import com.s2dwt.core.layoutmanager.ASwtLayoutManager;

public final class MyInputPanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtLabel label;
    private final SwtTextField value;

    // -------------------------------------------------------------------------------------------------------------------------
    public MyInputPanel(ASwtWidget<? extends Group> pParent, String pLabel, String pValue, boolean pIsPassword) {
        super(pParent, false);
        this.label = new SwtLabel(this, pLabel);
        this.label.setAlign(Align.right);
        this.label.setColor(Color.LIGHT_GRAY);
        this.value = new SwtTextField(this, pValue);
        this.value.setPassword(pIsPassword);
        this.setLayoutManager(new ASwtLayoutManager() {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                float hw = width * .5f;
                MyInputPanel.this.label.setBounds(0, 0, hw - 3, 35);
                MyInputPanel.this.value.setBounds(hw + 3, 0, hw - 3, 35);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getText() {
        return this.value.getText();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean focus() {
        return this.value.focus();
    }

    // -------------------------------------------------------------------------------------------------------------------------
}