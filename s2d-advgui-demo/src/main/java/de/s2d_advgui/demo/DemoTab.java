package de.s2d_advgui.demo;

import com.badlogic.gdx.math.Rectangle;

import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.demo.cases.ASwtWidgetTestCase;

class DemoTab {
    ASwtWidgetTestCase someTestCase;
    SwtButton button;
    SwtPanel border;
    Rectangle oo;

    public DemoTab(ASwtWidgetTestCase someTestCase) {
        this.someTestCase = someTestCase;
    }
}