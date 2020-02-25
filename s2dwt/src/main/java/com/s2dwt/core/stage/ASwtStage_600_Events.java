package com.s2dwt.core.stage;

import javax.annotation.Nullable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.awidget.ISwtWidget;
import com.s2dwt.core.input.ISwtWidgetSelectable;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SwtScreen;

public abstract class ASwtStage_600_Events<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_300_Traverse<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final ControllerListener controllerListener = new ControllerListener() {
        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public void connected(Controller pController) {
            System.err.println("ASwtStage_600_Events.connected(" + pController + ");");
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public void disconnected(Controller pController) {
            System.err.println("ASwtStage_600_Events.disconnected(" + pController + ");");
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean buttonDown(Controller pController, int pButtonCode) {
            System.out.println(pButtonCode);
            if (pButtonCode == Xbox.R_BUMPER) {
                return ASwtStage_600_Events.this.traverseTabNext();
            }
            if (pButtonCode == Xbox.L_BUMPER) {
                return ASwtStage_600_Events.this.traverseTabPrev();
            }
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerButtonDown(pButtonCode);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean buttonUp(Controller pController, int pButtonCode) {
            Actor actor = getKeyboardFocus();
            ISwtWidget<?> swt = ASwtStage_600_Events.this.actorMappings.get(actor);
            if (swt instanceof ISwtWidgetSelectable) {
                if (Xbox.A == pButtonCode) {
                    ((ISwtWidgetSelectable) swt).callListeners(0);
                    return true;
                }
            }
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerButtonUp(pButtonCode);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean axisMoved(Controller pController, int pAxisCode, float pValue) {
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerAxisMoved(pController, pAxisCode, pValue);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean povMoved(Controller pController, int pPovCode, PovDirection pValue) {
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerPovMoved(pController, pPovCode, pValue);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean xSliderMoved(Controller pController, int pSliderCode, boolean pValue) {
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerXSliderMoved(pController, pSliderCode, pValue);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean ySliderMoved(Controller pController, int pSliderCode, boolean pValue) {
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerYSliderMoved(pController, pSliderCode, pValue);
        }

        // -------------------------------------------------------------------------------------------------------------------------
        @Override
        public boolean accelerometerMoved(Controller pController, int pAccelerometerCode, Vector3 pValue) {
            SwtScreen<RM, DM> cu = ASwtStage_600_Events.this.currentScreen;
            return cu != null && cu.getControllerLevel()._onEvent_ControllerAccelerometerMoved(pController, pAccelerometerCode, pValue);
        }

        // -------------------------------------------------------------------------------------------------------------------------
    };

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    Controller controller;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_600_Events(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final ControllerListener getControllerListener(Controller pController) {
        this.controller = pController;
        return this.controllerListener;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyDown(int keyCode) {
        if (keyCode == Keys.TAB) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                return this.traverseTabPrev();
            }
            return this.traverseTabNext();
        }
        boolean back = super.keyDown(keyCode);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel().keyDown(keyCode)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyUp(int keyCode) {
        boolean back = super.keyUp(keyCode);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel().keyUp(keyCode)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyTyped(char character) {
        if ('\t' == character) return false; // Die Standard-Implementierung vom Scene2D-Tab-System wird hier übersprungen
        boolean back = super.keyTyped(character);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel().keyTyped(character)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean back = super.touchDown(screenX, screenY, pointer, button);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel()._onEvent_TouchDown(screenX, screenY, pointer, button)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean back = super.touchUp(screenX, screenY, pointer, button);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel()._onEvent_TouchUp(screenX, screenY, pointer, button)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean back = super.touchDragged(screenX, screenY, pointer);
//      if (!back) {
//          SwtScreen<RT> cu = this.currentScreen;
//          if (cu != null && cu._onEvent_TouchDragged(screenX, screenY, pointer)) return true;
//      }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        boolean back = super.mouseMoved(screenX, screenY);
//      if (!back) {
//          System.err.println("--> " + this.stageDimensions);
//          int localScreenX = (int) (screenX - this.stageDimensions.x);
//          int localScreenY = (int) (screenY - this.stageDimensions.y);
//          System.err.println("locl: " + localScreenX + "|" + localScreenY);
//          if (localScreenX <= this.stageDimensions.width && localScreenY <= this.stageDimensions.height) {
//              SwtScreen<RT> cu = this.currentScreen;
//              if (cu != null && cu._onEvent_MouseMoved(localScreenX, localScreenY)) return true;
//          }
//      }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean scrolled(int amount) {
        boolean back = super.scrolled(amount);
        if (!back) {
            SwtScreen<RM, DM> cu = this.currentScreen;
            if (cu != null && cu.getControllerLevel()._onEvent_MouseScrolled(amount)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------
}
