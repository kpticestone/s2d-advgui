package com.s2dwt.core.stage;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.awidget.IMyWidgetUpdateHandler;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.MasterViewport;
import com.s2dwt.core.screens.SwtScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

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
}
