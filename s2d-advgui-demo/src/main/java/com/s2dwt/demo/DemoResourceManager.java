package com.s2dwt.demo;

import java.util.LinkedHashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.s2dwt.core.resourcemanager.AResourceManager;
import com.s2dwt.core.resourcemanager.ResourceLoader;

public class DemoResourceManager extends AResourceManager {
    // -------------------------------------------------------------------------------------------------------------------------
    public Skin skin;

    // -------------------------------------------------------------------------------------------------------------------------
    private Set<ResourceLoader> loaders = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoResourceManager() {
        // DON
    }

//  // -------------------------------------------------------------------------------------------------------------------------
//  @Override
//  public BitmapFont getFont(float scale, boolean useInt) {
//      return TNull.checkNull(this.scaledFonts.computeIfAbsent(scale, s -> {
//          FreeTypeFontGenerator generator = new FreeTypeFontGenerator(load("ui/font/Minecraftia.ttf"));
//          FreeTypeFontParameter parameter = new FreeTypeFontParameter();
//          parameter.size = 16;
//          BitmapFont back = generator.generateFont(parameter);
//          back.getData().setScale(scale * 0.01f);
//          back.setUseIntegerPositions(useInt);
//          back.setColor(Color.WHITE);
//          return back;
//      }));
//  }

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
