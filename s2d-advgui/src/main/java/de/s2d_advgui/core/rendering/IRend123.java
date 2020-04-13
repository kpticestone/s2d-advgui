package de.s2d_advgui.core.rendering;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.resourcemanager.AResourceManager;

public interface IRend123 {
    // -------------------------------------------------------------------------------------------------------------------------
    void doRender(@Nonnull Batch batch, float parentAlpha, @Nonnull Trigger pOrg);

    // -------------------------------------------------------------------------------------------------------------------------
    AResourceManager getResourceManager();

    // -------------------------------------------------------------------------------------------------------------------------
}
