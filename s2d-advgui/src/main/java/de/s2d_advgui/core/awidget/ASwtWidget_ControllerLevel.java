package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import de.s2d_advgui.core.input.axis.ASwtInputRegister_Axis;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.stage.IControllerLevelTarget;
import de.s2d_advgui.core.stage.ISwtStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public abstract class ASwtWidget_ControllerLevel<T extends Actor> extends ASwtWidget<T> {
    private static final Logger log = LoggerFactory.getLogger(ASwtWidget_ControllerLevel.class);
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ControllerLevelImpl controllerLevel;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(ISwtStage<?, ?> pContext) {
        super(pContext);
        this.controllerLevel = new ControllerLevelImpl(this, () -> getContext().isControllerMode());
        this.myInit();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget_ControllerLevel(@Nonnull SwtWidgetBuilder<T> pBuilder, boolean modal) {
        super(pBuilder);
        if (!modal && pBuilder.parent instanceof ASwtWidget_ControllerLevel) {
            this.controllerLevel = ((ASwtWidget_ControllerLevel<?>) pBuilder.parent).controllerLevel;
        } else {
            this.controllerLevel = new ControllerLevelImpl(this, () -> getContext().isControllerMode());
            this.myInit();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void myInit() {
        this.addUpdateHandler(delta -> {
            this.controllerLevel.doUpdateOrderedList();
        });
        ((IControllerLevelTarget) this.context).setCurrentControllerLevel(this.controllerLevel);
        this.addDisposeListener(() -> {
            IControllerLevel removedLevel = ((IControllerLevelTarget) this.context).removeCurrentControllerLevel();
            if (removedLevel != this.controllerLevel) {
                log.warn("!!removed unknown controller level!! {}", removedLevel);
            }
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
