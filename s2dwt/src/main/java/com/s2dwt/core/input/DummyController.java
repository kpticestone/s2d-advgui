package com.s2dwt.core.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class DummyController implements Controller {
    @Override
    public boolean getButton(int i) {
        return false;
    }

    @Override
    public float getAxis(int i) {
        return 0;
    }

    @Override
    public PovDirection getPov(int i) {
        return null;
    }

    @Override
    public boolean getSliderX(int i) {
        return false;
    }

    @Override
    public boolean getSliderY(int i) {
        return false;
    }

    @Override
    public Vector3 getAccelerometer(int i) {
        return null;
    }

    @Override
    public void setAccelerometerSensitivity(float v) {
        // DON
    }

    @Override
    public String getName() {
        return "DummyController"; //$NON-NLS-1$
    }

    @Override
    public void addListener(ControllerListener controllerListener) {
        // DON
    }

    @Override
    public void removeListener(ControllerListener controllerListener) {
        // DON
    }
}
