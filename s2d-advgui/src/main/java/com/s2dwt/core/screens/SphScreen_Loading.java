package com.s2dwt.core.screens;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.s2dwt.core.basicwidgets.SwtImage;
import com.s2dwt.core.basicwidgets.SwtLabel;
import com.s2dwt.core.basicwidgets.SwtProgressBar;
import com.s2dwt.core.rendering.ISwtDrawerManager;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.resourcemanager.ResourceLoader;
import com.s2dwt.core.stage.ASwtStage;

public class SphScreen_Loading<RM extends AResourceManager, DM extends ISwtDrawerManager<RM>> extends SwtScreen<RM, DM> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SphScreen_Loading(@Nonnull ASwtStage<RM, DM> pStage) {
        super(pStage);

        SwtImage starfield = new SwtImage(this);
        starfield.setImage("ui/starfield.png");
        starfield.setScalingMode(Scaling.fill);
        starfield.setBounds(0, 0, 0, 0);

        SwtImage logo = new SwtImage(this);
        logo.setImage("icons/128/yinyang.png");
        logo.setBounds(0, 0, 0, 0);
        logo.setScalingMode(Scaling.fit);
        logo.setAlign(Align.top);

        SwtLabel loadingLabel = new SwtLabel(this);
        loadingLabel.setBounds(0, -50, 0, 50);
        loadingLabel.setAlign(Align.center);
        loadingLabel.setFontColor(Color.WHITE);

        SwtProgressBar loadingBar = new SwtProgressBar(this);
        loadingBar.setBounds(100, -100, -200, 50);

        List<ResourceLoader> loaderIterator = new ArrayList<>(this.drawerManager.getResourceManager().getLoaders());

        loadingBar.setMax(loaderIterator.size());
        int[] current = { 0 };

        this.addUpdateHandler(delta -> {
            if (current[0] < loaderIterator.size()) {
                loadingBar.setValue(current[0] + 1);
                ResourceLoader loader = loaderIterator.get(current[0]);
                loadingLabel.setText(loader.getTitle());
                loader.doLoad();
                ++current[0];
            } else {
                applicationController.activateScreen(SphScreenDescriptor_InputSelect.ID);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
