package de.s2d_advgui.core.awidget;

import java.util.Stack;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.input.axis.ASwtInputRegister_Axis;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_ControllerLevel<T extends Actor> extends ASwtWidget<T> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Stack<ASwtWidget_ControllerLevel<?>> subWidgets = new Stack<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ControllerLevelImpl controllerLevel;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(ISwtStage<?, ?> pContext) {
        super(pContext);
        this.controllerLevel = new ControllerLevelImpl(() -> getContext().isControllerMode());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(ISwtWidget<? extends Group> pParent, boolean focusable, boolean modal) {
        super(pParent, focusable);
        if (!modal && pParent instanceof ASwtWidget_ControllerLevel) {
            System.err.println("..."); //$NON-NLS-1$
            this.controllerLevel = ((ASwtWidget_ControllerLevel) pParent).controllerLevel;
        }
        else
        {
            this.controllerLevel = new ControllerLevelImpl(() -> getContext().isControllerMode());
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addSubWidget(ASwtWidget_ControllerLevel<?> pSub) {
        this.subWidgets.push(pSub);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final IControllerLevel getControllerLevel() {
        if (!this.subWidgets.isEmpty()) {
            ASwtWidget_ControllerLevel<?> lv = this.subWidgets.peek();
            if (lv.actor.getStage() == null) {
                // The widget is no longer part of the stage
                this.subWidgets.pop();
                return getControllerLevel();
            }
            return lv.getControllerLevel();
        }
        return this.controllerLevel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addKeyHandler(@Nonnull ASwtInputRegister_Keys pInputKeyHandler) {
        this.controllerLevel.addKeyHandler(pInputKeyHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addControllerAxisHandler(ASwtInputRegister_Axis pInputHandler) {
        this.controllerLevel.addControllerAxisHandler(pInputHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
