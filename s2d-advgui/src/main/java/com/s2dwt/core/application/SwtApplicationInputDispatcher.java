package com.s2dwt.core.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public final class SwtApplicationInputDispatcher implements ControllerListener, InputProcessor {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<Controller, ControllerListener> controllerListeners = new HashMap<>();
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<ControllerListener, Controller> reverseControllerListeners = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    private InputProcessor keyboardListener;

    // -------------------------------------------------------------------------------------------------------------------------
    private Stack<Controller> controllerConnectionRequests = new Stack<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private boolean keyboardConnectionRequest = false;

    // -------------------------------------------------------------------------------------------------------------------------
    public void attach(InputProcessor someStage) {
        this.keyboardListener = someStage;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void detachKeyboard() {
        this.keyboardListener = null;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void attach(Controller controller, ControllerListener controllerListener) {
        this.controllerListeners.put(controller, controllerListener);
        this.reverseControllerListeners.put(controllerListener, controller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void detach(ControllerListener controllerListener) {
        Controller controller = this.reverseControllerListeners.remove(controllerListener);
        this.controllerListeners.remove(controller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void fetchUnassignedControllerActivity(Consumer<Controller> pConsumer) {
        for (Controller d : this.controllerConnectionRequests) {
            pConsumer.accept(d);
        }
        this.controllerConnectionRequests.clear();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public boolean hasKeyboardActivity() {
        boolean b = this.keyboardConnectionRequest;
        this.keyboardConnectionRequest = false;
        return b;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyDown(int keycode) {
        if (keycode > 0) {
            if (this.keyboardListener != null) return this.keyboardListener.keyDown(keycode);
            this.keyboardConnectionRequest = true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyUp(int keycode) {
        if (this.keyboardListener != null) return this.keyboardListener.keyUp(keycode);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyTyped(char character) {
        if (this.keyboardListener != null) return this.keyboardListener.keyTyped(character);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (this.keyboardListener != null) return this.keyboardListener.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.keyboardListener != null) return this.keyboardListener.touchUp(screenX, screenY, pointer, button);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (this.keyboardListener != null) return this.keyboardListener.touchDragged(screenX, screenY, pointer);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (this.keyboardListener != null) return this.keyboardListener.mouseMoved(screenX, screenY);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean scrolled(int amount) {
        if (this.keyboardListener != null) return this.keyboardListener.scrolled(amount);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void connected(Controller controller) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) c.connected(controller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void disconnected(Controller controller) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) c.disconnected(controller);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.buttonDown(controller, buttonCode);
        if (!this.controllerConnectionRequests.contains(controller)) {
            this.controllerConnectionRequests.add(controller);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.buttonUp(controller, buttonCode);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.axisMoved(controller, axisCode, value);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.povMoved(controller, povCode, value);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.xSliderMoved(controller, sliderCode, value);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.ySliderMoved(controller, sliderCode, value);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        ControllerListener c = this.controllerListeners.get(controller);
        if (c != null) return c.accelerometerMoved(controller, accelerometerCode, value);
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
