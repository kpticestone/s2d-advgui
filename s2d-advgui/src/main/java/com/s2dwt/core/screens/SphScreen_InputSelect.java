package com.s2dwt.core.screens;

import com.s2dwt.core.basicwidgets.SwtImage;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.stage.ASwtStage;

import javax.annotation.Nonnull;

public final class SphScreen_InputSelect<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreen<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreen_InputSelect(@Nonnull ASwtStage<RM, DM> gameInstance) {
        super(gameInstance);
        SwtImage img = new SwtImage(this);
        img.setImage("/ui/inputSelect.png");
        img.setBounds(100, 50, -200, -100);

        // For Input-Registration see
        // com.s2dwt.core.application.ASwtApplication_300_Input.handleInput
    }

    // -------------------------------------------------------------------------------------------------------------------------
}