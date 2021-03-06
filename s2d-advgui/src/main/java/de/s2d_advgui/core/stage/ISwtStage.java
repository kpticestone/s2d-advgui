package de.s2d_advgui.core.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.window.SwtWindow;
import de.s2d_advgui.core.window.WindowID;
import de.s2d_advgui.commons.Trigger;

public interface ISwtStage<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    Actor getRoot();

    // -------------------------------------------------------------------------------------------------------------------------
    <XT extends Actor> void registerMapping(XT pActor, ISwtWidget<XT> pWidget);

    // -------------------------------------------------------------------------------------------------------------------------
    RM getResourceManager();

    // -------------------------------------------------------------------------------------------------------------------------
    TextureRegion getTextureRegion(String pResourceId);

    // -------------------------------------------------------------------------------------------------------------------------
    float getWidth();

    // -------------------------------------------------------------------------------------------------------------------------
    float getHeight();

    // -------------------------------------------------------------------------------------------------------------------------
    Actor getKeyboardFocus();

    // -------------------------------------------------------------------------------------------------------------------------
    boolean setKeyboardFocus(Actor pActor);

    // -------------------------------------------------------------------------------------------------------------------------
    Drawable getDrawable(String pResourceId);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean setScrollFocus(Actor pActor);

    // -------------------------------------------------------------------------------------------------------------------------
    void releaseHook();

    // -------------------------------------------------------------------------------------------------------------------------
    void registerHook(Trigger pTrigger);

    // -------------------------------------------------------------------------------------------------------------------------
    Color getColor_Label();

    // -------------------------------------------------------------------------------------------------------------------------
    TextureRegion getColor_Selected();

    // -------------------------------------------------------------------------------------------------------------------------
    TextureRegion getColor_Unselected();

    // -------------------------------------------------------------------------------------------------------------------------
    TextureRegion getColor_Disabled();

    // -------------------------------------------------------------------------------------------------------------------------
    DM getDrawerManager();

    // -------------------------------------------------------------------------------------------------------------------------
    Rectangle getStageDimensions();

    // -------------------------------------------------------------------------------------------------------------------------
    ISwtApplicationController<RM, DM> getApplicationController();

    // -------------------------------------------------------------------------------------------------------------------------
    SwtWindow activateWindow(WindowID id);

    // -------------------------------------------------------------------------------------------------------------------------
    boolean isControllerMode();

    // -------------------------------------------------------------------------------------------------------------------------
    float getGuiScale();

    // -------------------------------------------------------------------------------------------------------------------------
    void setGuiScale(float pGuiScale);

    // -------------------------------------------------------------------------------------------------------------------------
    float getDelta();

    // -------------------------------------------------------------------------------------------------------------------------
}
