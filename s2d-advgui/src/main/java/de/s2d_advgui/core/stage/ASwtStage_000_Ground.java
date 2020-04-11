package de.s2d_advgui.core.stage;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.s2d_advgui.core.application.ISwtApplicationController;
import de.s2d_advgui.core.awidget.IMyWidgetUpdateHandler;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.screens.MasterViewport;
import de.s2d_advgui.core.screens.SwtScreen;

public abstract class ASwtStage_000_Ground<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends Stage implements ISwtStage<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected static final Logger log = LoggerFactory.getLogger(ASwtStage_000_Ground.class);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final ISwtApplicationController<RM, DM> applicationController;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final DM drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final RM resourceManager;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nullable
    protected SwtScreen<RM, DM> currentScreen = null;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final Rectangle stageDimensions = new Rectangle();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Set<IMyWidgetUpdateHandler> updateHandler = new LinkedHashSet<>();
    
    // -------------------------------------------------------------------------------------------------------------------------
    private float guiScale = 1f;

    // -------------------------------------------------------------------------------------------------------------------------
    private float delta = -1f;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_000_Ground(@Nonnull ISwtApplicationController<RM, DM> pApplicationController) {
        super(new MasterViewport());
        this.applicationController = pApplicationController;
        this.drawerManager = pApplicationController.getDrawerManager();
        this.resourceManager = this.drawerManager.getResourceManager();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final MasterViewport getViewport() {
        return (MasterViewport) super.getViewport();
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final ISwtApplicationController<RM, DM> getApplicationController() {
        return this.applicationController;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final RM getResourceManager() {
        return this.resourceManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    @Override
    public final DM getDrawerManager() {
        return this.drawerManager;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public int getScreenWidth() {
        return this.getViewport().getScreenWidth();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public int getScreenHeight() {
        return this.getViewport().getScreenHeight();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final Rectangle getStageDimensions() {
        return this.stageDimensions;
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * Wird von der SwtApplication immer dann aufgerufen, wenn sich die Dimensionen
     * der Stage verändert haben.
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public final void setBounds(float x, float y, float w, float h) {
        this.stageDimensions.set(x, y, w, h);
        SwtScreen<RM, DM> sc = this.currentScreen;
        if (sc != null) {
            sc.setBounds(0, 0, w, h);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void onSetCurrentScreen(SwtScreen<RM, DM> theScreen) {
        this.currentScreen = theScreen;
        ((IControllerLevelTarget)this).setCurrentControllerLevel(theScreen.getControllerLevel());
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtScreen<RM, DM> getScreen() {
        return this.currentScreen;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void act() {
        super.act();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void act(float delta) {
        super.act(delta);
        this.delta = delta;
        for (IMyWidgetUpdateHandler a : this.updateHandler) {
            try {
                a.act(delta);
            } catch (Exception e) {
                log.error("unable to draw '{}'", a, e);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final void addUpdateHandler(IMyWidgetUpdateHandler pUpdateHandler) {
        this.updateHandler.add(pUpdateHandler);
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public float getGuiScale() {
        return this.guiScale;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void setGuiScale(float pGuiScale) {
        this.guiScale = pGuiScale;
    }
    
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final float getDelta()
    {
        return this.delta;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
