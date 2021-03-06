package de.s2d_advgui.core.awidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.s2d_advgui.core.input.ISwtScrollListener;
import de.s2d_advgui.core.input.axis.ASwtInputRegister_Axis;
import de.s2d_advgui.core.input.axis.SwtInputHolder_Axis;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.input.keys.ISwtActionRunner_Keys;
import de.s2d_advgui.core.input.keys.SwtInputHolder_Keys;

public class ControllerLevelImpl implements IControllerLevel {
    // -------------------------------------------------------------------------------------------------------------------------
    private Map<Integer, Set<ISwtActionRunner_Keys>> mouseButtonRunners = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final ISwtActionRunner_Keys[] workers = new ISwtActionRunner_Keys[256];

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<Integer, ISwtActionRunner_Keys> higherWorkers = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final List<ASwtInputRegister_Axis> inputRegisterAxis = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<PovDirection, ISwtActionRunner_Keys> povWorkers = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<ISwtScrollListener> scrollListeners = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private PovDirection lastPovValue = PovDirection.center;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final IIsControllerMode isCtrl;

    // -------------------------------------------------------------------------------------------------------------------------
    public ControllerLevelImpl(@Nonnull IIsControllerMode pIsCtrl) {
        this.isCtrl = pIsCtrl;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addKeyHandler(@Nonnull ASwtInputRegister_Keys pInputKeyHandler) {
        for (SwtInputHolder_Keys s : pInputKeyHandler.getActions()) {
            ISwtActionRunner_Keys worker = s.getRunner();
            for (int keyCode : s.getKeyCodes(this.isCtrl.isControllerMode())) {
                if (keyCode < 256) {
                    if (this.workers[keyCode] != null) throw new RuntimeException("keycode '" + keyCode + "' of handler " + pInputKeyHandler + " already used"); //$NON-NLS-1$ //$NON-NLS-2$
                    this.workers[keyCode] = worker;
                } else {
                    if (this.higherWorkers.containsKey(keyCode)) throw new RuntimeException("keycode '" + keyCode + "' of handler " + pInputKeyHandler + " already used"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    this.higherWorkers.put(keyCode, worker);
                }

            }
            for (PovDirection pov : s.getPovs()) {
                this.povWorkers.put(pov, worker);
            }
            this.scrollListeners.addAll(s.getScrollListeners());
            for (int buttons : s.getMouseButtons()) {
                Set<ISwtActionRunner_Keys> into = this.mouseButtonRunners.computeIfAbsent(buttons, b -> new LinkedHashSet<>());
                into.add(worker);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addControllerAxisHandler(ASwtInputRegister_Axis pInputHandler) {
        this.inputRegisterAxis.add(pInputHandler);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    protected final ISwtActionRunner_Keys getKeyWorker(int keyCode) {
        if (keyCode < 256) return this.workers[keyCode];
        return this.higherWorkers.get(keyCode);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyTyped(char character) {
        return false; // this.keyDown(character);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyUp(int keyCode) {
        ISwtActionRunner_Keys worker = this.getKeyWorker(keyCode);
        if (worker != null) {
            return worker.run(false);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyDown(int keyCode) {
        ISwtActionRunner_Keys worker = this.getKeyWorker(keyCode);
        if (worker != null) {
            return worker.run(true);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerButtonDown(int buttonCode) {
        ISwtActionRunner_Keys worker = this.getKeyWorker(buttonCode);
        if (worker != null) {
            return worker.run(true);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerButtonUp(int buttonCode) {
        ISwtActionRunner_Keys worker = this.getKeyWorker(buttonCode);
        if (worker != null) {
            return worker.run(false);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerAxisMoved(Controller controller, int axisCode, float value) {
        for (ASwtInputRegister_Axis jj : this.inputRegisterAxis) {
            for (SwtInputHolder_Axis action : jj.getActions()) {
                if (action.horAxisCode == axisCode || action.verAxisCode == axisCode) {
                    // if the event was just fired, controller.getAxis might not know about it yet.
                    // so we prefer the value from the event instead of the getAxis-method.
                    float hor = action.horAxisCode == axisCode ? value : controller.getAxis(action.horAxisCode);
                    float ver = action.verAxisCode == axisCode ? value : controller.getAxis(action.verAxisCode);
                    Vector2 axis = new Vector2(hor, ver);
                    if (action.runner.run(axis)) return true;
                }
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerPovMoved(Controller controller, int povCode, PovDirection value) {
        System.err.println("SwtScreen._onEvent_ControllerPovMoved(" + povCode + ", " + value + ");");

        if (this.lastPovValue != PovDirection.center) {
            ISwtActionRunner_Keys lastWorker = this.povWorkers.get(this.lastPovValue);
            if (lastWorker != null) {
                lastWorker.run(false);
            }
        }
        this.lastPovValue = value;
        ISwtActionRunner_Keys worker = this.povWorkers.get(value);
        if (worker != null) {
            if (worker.run(true)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerXSliderMoved(Controller controller, int sliderCode, boolean value) {
        System.err.println("SwtScreen._onEvent_ControllerXSliderMoved(" + sliderCode + ", " + value + ");");
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerYSliderMoved(Controller controller, int sliderCode, boolean value) {
        System.err.println("SwtScreen._onEvent_ControllerYSliderMoved(" + sliderCode + ", " + value + ");");
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerAccelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        System.err.println("SwtScreen._onEvent_ControllerAccelerometerMoved(" + accelerometerCode + ", " + value + ");");
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_TouchDown(int screenX, int screenY, int pointer, int button) {
        System.err.println("SwtScreen._onEvent_TouchDown(" + screenX + ", " + screenY + ", " + pointer + ", " + button + ");");
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_TouchUp(int screenX, int screenY, int pointer, int button) {
        System.err.println("SwtScreen._onEvent_TouchUp(" + screenX + ", " + screenY + ", " + pointer + ", " + button + ");");
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_MouseScrolled(int amount) {
        for (ISwtScrollListener a : this.scrollListeners) {
            if (a.onScroll(amount)) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
