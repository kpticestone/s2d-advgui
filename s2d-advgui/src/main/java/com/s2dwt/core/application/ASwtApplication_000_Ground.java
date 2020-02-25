package com.s2dwt.core.application;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.screens.SplitScreenArrangement;
import com.s2dwt.core.stage.ASwtStage;

public abstract class ASwtApplication_000_Ground<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>, STAGE extends ASwtStage<RM, DM>> {
    // -------------------------------------------------------------------------------------------------------------------------
    protected static final Logger log = LoggerFactory.getLogger(ASwtApplication_000_Ground.class);

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final DM drawerManager;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected final RM resourceManager;

    // -------------------------------------------------------------------------------------------------------------------------
    protected int width;

    // -------------------------------------------------------------------------------------------------------------------------
    protected int height;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    protected SplitScreenArrangement splitscreenarrangement = SplitScreenArrangement.ZERO;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtApplication_000_Ground(DM pDrawerManager) {
        Gdx.app.setLogLevel(3);
        this.drawerManager = pDrawerManager;
        this.resourceManager = pDrawerManager.getResourceManager();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected abstract String getTitle();

    // -------------------------------------------------------------------------------------------------------------------------
    protected final void updateSplitScreenArrangements(int size) {
        this.splitscreenarrangement = SplitScreenArrangement.get(size);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
