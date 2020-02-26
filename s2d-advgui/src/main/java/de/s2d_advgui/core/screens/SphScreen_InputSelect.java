package de.s2d_advgui.core.screens;

import javax.annotation.Nonnull;

import de.s2d_advgui.core.basicwidgets.SwtImage;
import de.s2d_advgui.core.rendering.ISwtDrawerManager;
import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.stage.ASwtStage;

public final class SphScreen_InputSelect<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreen<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreen_InputSelect(@Nonnull ASwtStage<RM, DM> gameInstance) {
        super(gameInstance);
        SwtImage img = new SwtImage(this);
        img.setImage("/icons/128/remotecontrol2.png");
        img.setBounds(100, 50, -200, -100);

        // For Input-Registration see
        // com.s2dwt.core.application.ASwtApplication_300_Input.handleInput
    }

    // -------------------------------------------------------------------------------------------------------------------------
}