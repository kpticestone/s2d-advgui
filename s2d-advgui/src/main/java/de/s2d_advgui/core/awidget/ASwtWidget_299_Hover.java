package de.s2d_advgui.core.awidget;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_299_Hover<ACTOR extends Actor> extends ASwtWidget_200_Coordinates<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    private boolean hovered = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_299_Hover(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_299_Hover(@Nonnull SwtWidgetBuilder<ACTOR> pBuilder) {
        super(pBuilder);
        this.registerEventHandler(InputEvent.Type.enter, (event) -> {
            if (isEnabled()) {
                ASwtWidget_299_Hover.this.hovered = true;
                return true;
            }
            return false;
        });
        this.registerEventHandler(InputEvent.Type.exit, (event) -> {
            ASwtWidget_299_Hover.this.hovered = false;
            return true;
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean isHovered() {
        return this.hovered;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
