package de.s2d_advgui.core.awidget;

import java.util.List;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.input.axis.ASwtInputRegister_Axis;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.stage.IControllerLevelTarget;
import de.s2d_advgui.core.stage.ISwtStage;

public abstract class ASwtWidget_ControllerLevel<T extends Actor> extends ASwtWidget<T> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ControllerLevelImpl controllerLevel;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(ISwtStage<?, ?> pContext) {
        super(pContext);
        this.controllerLevel = new ControllerLevelImpl(() -> getContext().isControllerMode());
        this.myInit();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(ISwtWidget<? extends Group> pParent, boolean focusable, boolean modal) {
        super(pParent, focusable);
        if (!modal && pParent instanceof ASwtWidget_ControllerLevel) {
            this.controllerLevel = ((ASwtWidget_ControllerLevel<?>) pParent).controllerLevel;
        } else {
            this.controllerLevel = new ControllerLevelImpl(() -> getContext().isControllerMode());
            this.myInit();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void myInit() {
        this.addUpdateHandler(delta -> {
            List<ISwtWidget<?>> ol = this.controllerLevel.getOrderedList();
            ol.clear();
            this.calculateTabOrderList(ol);
        });
        ((IControllerLevelTarget) this.context).setCurrentControllerLevel(this.controllerLevel);
        this.addDisposeListener(() -> {
            ((IControllerLevelTarget) this.context).removeCurrentControllerLevel();
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final IControllerLevel getControllerLevel() {
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
