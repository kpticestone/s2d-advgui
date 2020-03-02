package de.s2d_advgui.core.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.resourcemanager.ResourceLoader;

public class SwtLoadingBox extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    private final ResourceLoader resourceLoader;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLoadingBox(ISwtWidget<? extends Group> pParent, ResourceLoader pResourceLoader) {
        super(pParent, true);
        this.resourceLoader = pResourceLoader;

        SwtLabel label = new SwtLabel(this, this.resourceLoader.getTitle());
        label.setAlign(Align.center);
        label.setBounds(0, 0, 0, 0);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
