package com.s2dwt.core.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.s2dwt.core.application.ISwtApplicationController;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;

public abstract class ASwtStage_010_QuickDraw<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends ASwtStage_000_Ground<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtStage_010_QuickDraw(ISwtApplicationController<RM, DM> pApplicationController) {
        super(pApplicationController);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Color getColor_Label() {
        return Color.CYAN;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final Drawable getDrawable(String jj) {
        TextureRegion tr = this.resourceManager.getTextureRegion(jj);
        return new TextureRegionDrawable(tr);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final TextureRegion getTextureRegion(String useBackgroundImage) {
        return this.resourceManager.getTextureRegion(useBackgroundImage);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final TextureRegion getColor_Disabled() {
        return this.resourceManager.getColorTextureRegion(Color.DARK_GRAY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final TextureRegion getColor_Selected() {
        return this.resourceManager.getColorTextureRegion(Color.CYAN);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final TextureRegion getColor_Unselected() {
        return this.resourceManager.getColorTextureRegion(Color.BLACK);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
