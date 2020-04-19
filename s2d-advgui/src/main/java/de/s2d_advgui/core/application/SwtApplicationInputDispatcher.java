package de.s2d_advgui.core.application;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

public final class SwtApplicationInputDispatcher implements ControllerListener, InputProcessor {
    private static final Logger log = LoggerFactory.getLogger(SwtApplicationInputDispatcher.class);
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
    private final Stack<Controller> controllerConnectionRequests = new Stack<>();

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
            try {
                if (this.keyboardListener != null) return this.keyboardListener.keyDown(keycode);
            } catch (Exception e) {
                log.error("error during keyDown " + keycode, e);
            }
            this.keyboardConnectionRequest = true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyUp(int keycode) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.keyUp(keycode);
        } catch (Exception e) {
            log.error("error during keyUp " + keycode, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyTyped(char character) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.keyTyped(character);
        } catch (Exception e) {
            log.error("error during keyTyped " + character, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.touchDown(screenX, screenY, pointer, button);
        } catch (Exception e) {
            log.error("error during touchDown " + button, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.touchUp(screenX, screenY, pointer, button);
        } catch (Exception e) {
            log.error("error during touchUp " + button, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.touchDragged(screenX, screenY, pointer);
        } catch (Exception e) {
            log.error("error during touchDragged " + pointer, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.mouseMoved(screenX, screenY);
        } catch (Exception e) {
            log.error("error during mouseMoved", e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean scrolled(int amount) {
        try {
            if (this.keyboardListener != null) return this.keyboardListener.scrolled(amount);
        } catch (Exception e) {
            log.error("error during scrolled " + amount, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void connected(Controller controller) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) c.connected(controller);
        } catch (Exception e) {
            log.error("error during connected " + controller, e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void disconnected(Controller controller) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) c.disconnected(controller);
        } catch (Exception e) {
            log.error("error during connected " + controller, e);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.buttonDown(controller, buttonCode);
            if (!this.controllerConnectionRequests.contains(controller)) {
                this.controllerConnectionRequests.add(controller);
            }
        } catch (Exception e) {
            log.error("error during buttonDown " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.buttonUp(controller, buttonCode);
        } catch (Exception e) {
            log.error("error during buttonUp " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.axisMoved(controller, axisCode, value);
        } catch (Exception e) {
            log.error("error during axisMoved " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.povMoved(controller, povCode, value);
        } catch (Exception e) {
            log.error("error during povMoved " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.xSliderMoved(controller, sliderCode, value);
        } catch (Exception e) {
            log.error("error during xSliderMoved " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.ySliderMoved(controller, sliderCode, value);
        } catch (Exception e) {
            log.error("error during ySliderMoved " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        try {
            ControllerListener c = this.controllerListeners.get(controller);
            if (c != null) return c.accelerometerMoved(controller, accelerometerCode, value);
        } catch (Exception e) {
            log.error("error during accelerometerMoved " + controller, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
