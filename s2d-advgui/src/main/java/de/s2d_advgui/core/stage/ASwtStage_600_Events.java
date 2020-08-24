package de.s2d_advgui.core.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Pools;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.IControllerLevel;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.input.ISwtWidgetSelectable;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Stack;

public abstract class ASwtStage_600_Events<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>>
        extends ASwtStage_200_Widgets<RM, DM> implements IControllerLevelTarget, ControllerListener {
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
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        final boolean handled = cu._onEvent_ControllerButtonDown(pButtonCode);
        if (!handled) {
            if (pButtonCode == Xbox.R_BUMPER) {
                return cu.traverseTabNext();
            }
            if (pButtonCode == Xbox.L_BUMPER) {
                return cu.traverseTabPrev();
            }
        }
        return handled;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean buttonUp(Controller pController, int pButtonCode) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        Actor actor = getKeyboardFocus();
        ISwtWidget<?> swt = ASwtStage_600_Events.this.actorMappings.get(actor);
        if (swt instanceof ISwtWidgetSelectable) {
            if (Xbox.A == pButtonCode) {
                ((ISwtWidgetSelectable) swt).callListeners(0);
                return true;
            }
        }
        return cu._onEvent_ControllerButtonUp(pButtonCode);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean axisMoved(Controller pController, int pAxisCode, float pValue) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        return cu._onEvent_ControllerAxisMoved(pController, pAxisCode, pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean povMoved(Controller pController, int pPovCode, PovDirection pValue) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        return cu._onEvent_ControllerPovMoved(pController, pPovCode, pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean xSliderMoved(Controller pController, int pSliderCode, boolean pValue) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        return cu._onEvent_ControllerXSliderMoved(pController, pSliderCode, pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean ySliderMoved(Controller pController, int pSliderCode, boolean pValue) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        return cu._onEvent_ControllerYSliderMoved(pController, pSliderCode, pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean accelerometerMoved(Controller pController, int pAccelerometerCode, Vector3 pValue) {
        IControllerLevel cu = ASwtStage_600_Events.this.currentControllerLevel;
        if (cu == null) return false;
        return cu._onEvent_ControllerAccelerometerMoved(pController, pAccelerometerCode, pValue);
    }
    // -------------------------------------------------------------------------------------------------------------------------

    @Nullable
    Controller controller;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Stack<IControllerLevel> controllerStack = new Stack<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    IControllerLevel currentControllerLevel = null;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_600_Events(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final ControllerListener getControllerListener(Controller pController) {
        this.controller = pController;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final IControllerLevel getControllerLevel() {
        return this.currentControllerLevel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setCurrentControllerLevel(IControllerLevel pControllerLevel) {
        if (currentControllerLevel != pControllerLevel) {
            this.controllerStack.push(pControllerLevel);
            this.currentControllerLevel = pControllerLevel;
            this.sendControllerLevelChangeEvent();
            setKeyboardFocus(null);
        }
    }

    private void sendControllerLevelChangeEvent() {
        ControllerLevelEvent event = Pools.obtain(ControllerLevelEvent.class);
        event.setStage(this);
        getRoot().fire(event);
        Pools.free(event);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public IControllerLevel removeCurrentControllerLevel() {
        IControllerLevel pop = this.controllerStack.pop();
        this.currentControllerLevel = this.controllerStack.peek();
        this.currentControllerLevel.doFocusCurrentWidget();
        return pop;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void removeControllerLevel(IControllerLevel level) {
        if (this.currentControllerLevel != level) {
            log.warn("removing controller-level that was not current {}, {}", currentControllerLevel, level);
        }
        controllerStack.remove(level);
        this.currentControllerLevel = this.controllerStack.peek();
        this.currentControllerLevel.doFocusCurrentWidget();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyDown(int keyCode) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        if (keyCode == Keys.TAB) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                return cu.traverseTabPrev();
            }
            return cu.traverseTabNext();
        }
        boolean back = super.keyDown(keyCode);
        if (!back) {
            if (cu.keyDown(keyCode)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyUp(int keyCode) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        boolean back = super.keyUp(keyCode);
        if (!back) {
            if (cu.keyUp(keyCode)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean keyTyped(char character) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        if ('\t' == character) return false; // Die Standard-Implementierung vom Scene2D-Tab-System wird hier
        // übersprungen
        boolean back = super.keyTyped(character);
        if (!back) {
            if (cu.keyTyped(character)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        boolean back = super.touchDown(screenX, screenY, pointer, button);
        if (!back) {
            if (cu._onEvent_TouchDown(screenX, screenY, pointer, button)) {
                return true;
            }
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        boolean back = super.touchUp(screenX, screenY, pointer, button);
        if (!back) {
            if (cu._onEvent_TouchUp(screenX, screenY, pointer, button)) return true;
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
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;
        boolean back = super.scrolled(amount);
        if (!back) {
            if (cu._onEvent_MouseScrolled(amount)) return true;
        }
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean setKeyboardFocus(Actor actor) {
        IControllerLevel cu = this.currentControllerLevel;
        if (cu == null) return false;

        ISwtWidget<?> widget = this.actorMappings.get(actor);
        if (widget == null) return false;

        cu.setFocusedWidget(widget);

        return super.setKeyboardFocus(actor);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Actor getKeyboardFocus() {
        return super.getKeyboardFocus();
    }

    public static final class ControllerLevelEvent extends Event {
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------
}
