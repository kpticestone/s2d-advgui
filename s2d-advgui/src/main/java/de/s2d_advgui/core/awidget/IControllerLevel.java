package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public interface IControllerLevel {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean keyTyped(char character);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean keyUp(int keyCode);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean keyDown(int keyCode);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerButtonDown(int buttonCode);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerButtonUp(int buttonCode);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerAxisMoved(Controller controller, int axisCode, float value);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerPovMoved(Controller controller, int povCode, PovDirection value);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerXSliderMoved(Controller controller, int sliderCode, boolean value);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerYSliderMoved(Controller controller, int sliderCode, boolean value);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_ControllerAccelerometerMoved(Controller controller, int accelerometerCode, Vector3 value);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_TouchDown(int screenX, int screenY, int pointer, int button);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_TouchUp(int screenX, int screenY, int pointer, int button);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean _onEvent_MouseScrolled(int amount);

    // -------------------------------------------------------------------------------------------------------------------------
}
