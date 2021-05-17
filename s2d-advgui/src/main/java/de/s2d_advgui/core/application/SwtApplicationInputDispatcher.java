package de.s2d_advgui.core.application;

import static com.badlogic.gdx.Input.Keys.ALT_LEFT;
import static com.badlogic.gdx.Input.Keys.ALT_RIGHT;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
import static com.badlogic.gdx.Input.Keys.TAB;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.common.collect.Sets;
import de.s2d_advgui.core.stage.ASwtStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public final class SwtApplicationInputDispatcher implements ControllerListener, InputProcessor {
    private static final Logger log = LoggerFactory.getLogger(SwtApplicationInputDispatcher.class);

    // -------------------------------------------------------------------------------------------------------------------------
    // If enabled, the Keyboard can be used for Controller-Stages as well, when pointed at it.
    public boolean shareKeyboard = false;
    // -------------------------------------------------------------------------------------------------------------------------
    private final Vector2 tmp = new Vector2();
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
    private final Set<Integer> connectionRequestIgnore = Sets.newHashSet(ALT_LEFT, ALT_RIGHT, TAB, CONTROL_LEFT, CONTROL_RIGHT);

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
            this.keyboardConnectionRequest = this.keyboardListener == null && !connectionRequestIgnore.contains(keycode);
            try {
                forEachKeyboardListener(k -> k.keyDown(keycode));
            } catch (Exception e) {
                log.error("error during keyDown " + keycode, e);
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyUp(int keycode) {
        try {
            forEachKeyboardListener(k -> k.keyUp(keycode));
        } catch (Exception e) {
            log.error("error during keyUp " + keycode, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean keyTyped(char character) {
        try {
            forEachKeyboardListener(k -> k.keyTyped(character));
        } catch (Exception e) {
            log.error("error during keyTyped " + character, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        try {
            forEachKeyboardListener(k -> k.touchDown(screenX, screenY, pointer, button));
        } catch (Exception e) {
            log.error("error during touchDown " + button, e);
        }
        return false;
    }

    private void forEachKeyboardListener(Consumer<InputProcessor> consumer) {
        if (!shareKeyboard) {
            if (this.keyboardListener != null) {
                consumer.accept(this.keyboardListener);
            }
            return;
        }

        if (hits(keyboardListener)) {
            consumer.accept(this.keyboardListener);
        }
        for (ControllerListener controllerListener : controllerListeners.values()) {
            if (hits(controllerListener)) {
                consumer.accept((InputProcessor) controllerListener);
            }
        }
    }

    private boolean hits(Object listener) {
        if (listener instanceof ASwtStage) {
            ASwtStage<?, ?> stage = (ASwtStage<?, ?>) listener;
            Vector2 stagePos = stage.screenToStageCoordinates(tmp.set(Gdx.input.getX(), Gdx.input.getY()));
            return stage.hit(stagePos.x, stagePos.y, false) != null;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        try {
            forEachKeyboardListener(k -> k.touchUp(screenX, screenY, pointer, button));
        } catch (Exception e) {
            log.error("error during touchUp " + button, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        try {
            forEachKeyboardListener(k -> k.touchDragged(screenX, screenY, pointer));
        } catch (Exception e) {
            log.error("error during touchDragged " + pointer, e);
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        try {
            forEachKeyboardListener(k -> k.mouseMoved(screenX, screenY));
        } catch (Exception e) {
            log.error("error during mouseMoved", e);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        try {
            forEachKeyboardListener(k -> k.scrolled(amountX, amountY));
        } catch (Exception e) {
            log.error("error during scrolled {}/{}", amountX, amountY, e);
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
