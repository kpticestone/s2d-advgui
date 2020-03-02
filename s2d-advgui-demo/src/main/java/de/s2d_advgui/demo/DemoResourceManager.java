package de.s2d_advgui.demo;

import java.util.LinkedHashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;

import de.s2d_advgui.core.resourcemanager.AResourceManager;
import de.s2d_advgui.core.resourcemanager.ResourceLoader;

public class DemoResourceManager extends AResourceManager {
    // -------------------------------------------------------------------------------------------------------------------------
    public Skin skin;

    // -------------------------------------------------------------------------------------------------------------------------
    private Set<ResourceLoader> loaders = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoResourceManager() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Skin getSkin() {
        return this.skin;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public Set<ResourceLoader> getLoaders() {
        this.loaders.add(new ResourceLoader("skin", () -> {
            this.skin = new Skin(load("ui/skin/uiskin.json"), new TextureAtlas(load("ui/skin/uiskin.atlas")));
            if (!VisUI.isLoaded()) {
                VisUI.load(this.skin);
            }
        }));
        return this.loaders;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
