package de.s2d_advgui.core.awidget;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControllerLevelImpl implements IControllerLevel {
    private static final Logger log = LoggerFactory.getLogger(ControllerLevelImpl.class);

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
    @Nullable
    private ISwtWidget<?> focusedWidget = null;

    // -------------------------------------------------------------------------------------------------------------------------
    private final List<ISwtWidget<?>> orderedList = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private final ISwtWidget<?> rootWidget;

    // -------------------------------------------------------------------------------------------------------------------------
    private long lastRev = 0;

    // -------------------------------------------------------------------------------------------------------------------------
    public ControllerLevelImpl(ISwtWidget<?> pWidget, @Nonnull IIsControllerMode pIsCtrl) {
        this.rootWidget = pWidget;
        this.isCtrl = pIsCtrl;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addKeyHandler(@Nonnull ASwtInputRegister_Keys pInputKeyHandler) {
        for (SwtInputHolder_Keys s : pInputKeyHandler.getActions()) {
            ISwtActionRunner_Keys worker = s.getRunner();
            for (int keyCode : s.getKeyCodes(this.isCtrl.isControllerMode())) {
                if (keyCode < 256) {
                    if (this.workers[keyCode] != null) throw new RuntimeException(
                            String.format("keycode '%d' of handler %s already used", keyCode, pInputKeyHandler)); //$NON-NLS-1$
                    this.workers[keyCode] = worker;
                } else {
                    if (this.higherWorkers.containsKey(keyCode)) throw new RuntimeException(
                            String.format("keycode '%d' of handler %s already used", keyCode, pInputKeyHandler)); //$NON-NLS-1$
                    this.higherWorkers.put(keyCode, worker);
                }

            }
            for (PovDirection pov : s.getPovs()) {
                this.povWorkers.put(pov, worker);
            }
            this.scrollListeners.addAll(s.getScrollListeners());
            for (int buttons : s.getMouseButtons()) {
                Set<ISwtActionRunner_Keys> into = this.mouseButtonRunners.computeIfAbsent(buttons,
                        b -> new LinkedHashSet<>());
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
        log.debug("SwtScreen._onEvent_ControllerXSliderMoved({}, {});", sliderCode, value); //$NON-NLS-1$
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerYSliderMoved(Controller controller, int sliderCode, boolean value) {
        log.debug("SwtScreen._onEvent_ControllerYSliderMoved({}, {});", sliderCode, value); //$NON-NLS-1$
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_ControllerAccelerometerMoved(Controller controller, int accelerometerCode,
                                                               Vector3 value) {
        log.debug("SwtScreen._onEvent_ControllerAccelerometerMoved({}, {});", accelerometerCode, value); //$NON-NLS-1$
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_TouchDown(int screenX, int screenY, int pointer, int button) {
        Set<ISwtActionRunner_Keys> runners = this.mouseButtonRunners.get(button);
        if (runners != null) {
            for (ISwtActionRunner_Keys runner : runners) {
                if (runner.run(true)) return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean _onEvent_TouchUp(int screenX, int screenY, int pointer, int button) {
        Set<ISwtActionRunner_Keys> runners = this.mouseButtonRunners.get(button);
        if (runners != null) {
            for (ISwtActionRunner_Keys runner : runners) {
                if (runner.run(false)) return true;
            }
        }
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
    @Override
    public final boolean traverseTabNext() {
        int idx = this.orderedList.indexOf(this.focusedWidget) + 1;
        int sio = this.orderedList.size();
        for (int i = 0; i < sio; i++) {
            int useIdx = idx + i;
            if (useIdx >= sio) useIdx -= sio;
            ISwtWidget<?> kk = this.orderedList.get(useIdx);
            if (kk.isEnabled() && kk.isVisible()) {
                this.focusedWidget = kk;
                kk.focus();
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean traverseTabPrev() {
        int idx = this.orderedList.indexOf(this.focusedWidget);
        if (idx == -1) idx = 0;
        else
            idx -= 1;
        int sio = this.orderedList.size();
        for (int i = 0; i < sio; i++) {
            int useIdx = idx - i;
            if (useIdx < 0) useIdx += sio;
            ISwtWidget<?> kk = this.orderedList.get(useIdx);
            if (kk.isEnabled() && kk.isVisible()) {
                this.focusedWidget = kk;
                kk.focus();
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void doFocusCurrentWidget() {
        ISwtWidget<?> fw = this.focusedWidget;
        if (fw != null) {
            fw.focus();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public ISwtWidget<?> getFocusedWidget() {
        return this.focusedWidget;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setFocusedWidget(@Nullable ISwtWidget<?> focusedWidget) {
        this.focusedWidget = focusedWidget;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void doUpdateOrderedList() {
        long curRev = this.rootWidget.getContext().getRevision();
        if (this.lastRev != curRev) {
            // System.err.println("update orderedlist cause rev changed to " + curRev);
            this.lastRev = curRev;
            List<ISwtWidget<?>> ol = this.orderedList;
            ol.clear();
            this.calculateTabOrderList(this.rootWidget, ol);
            if (this.focusedWidget != null) {
                if (!this.focusedWidget.isVisible() || !this.focusedWidget.isEnabled()) {
                    this.traverseTabNext();
                }
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void calculateTabOrderList(ISwtWidget<?> pParent, List<ISwtWidget<?>> pOrderedList) {
        if (pParent.isFocusable()) {
            pOrderedList.add(pParent);
        }
        for (ISwtWidget<?> child : pParent.getChildren()) {
            this.calculateTabOrderList(child, pOrderedList);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
