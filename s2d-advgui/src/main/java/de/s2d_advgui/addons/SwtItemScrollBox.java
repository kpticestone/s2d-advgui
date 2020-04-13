package de.s2d_advgui.addons;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;

public class SwtItemScrollBox extends ASwtWidget<ScrollPane> {
    // -------------------------------------------------------------------------------------------------------------------------
    private SwtItemScrollBox2 content;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox(ASwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, true, new ActorCreatorItemScrollBox()));
        this.content = new SwtItemScrollBox2(this);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemScrollBox2 getContent() {
        return this.content;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
