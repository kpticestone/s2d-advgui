package de.s2d_advgui.demo.cases;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;

public interface IButtonProducer {
    // -------------------------------------------------------------------------------------------------------------------------
    ASwtWidget<?> create(ISwtWidget<? extends Group> pParent);

    // -------------------------------------------------------------------------------------------------------------------------
}