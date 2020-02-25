package com.s2dwt.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.ISwtWidget;

public class SwtPanel extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanel(ISwtWidget<? extends Group> pParent, boolean standardBackground) {
        super(pParent, false);
        if (standardBackground) {
            this.setClip(false);
            TextureRegion br1 = context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
            TextureRegion cr1 = context.getTextureRegion("ui2/corner-1.png");
            Texture bg = context.getResourceManager().getTexture("ui2/background-1.png");
            bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            this.addDrawerBackground((batch, pScreenCoords, dims) -> {
                batch.setColor(new Color(.04f, .59f, .83f, 1f));
                batch.draw(bg, dims.x + 4, dims.y + 4, 0, 0, (int) dims.width - 8, (int) dims.height - 8);
            });
            this.addDrawerForeground((batch, pScreenCoords, dims) -> {
                batch.setColor(new Color(.04f, .59f, .83f, 1f));
                batch.draw(br1, dims.x + 2, dims.y + 2, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + dims.height - 4, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + 2, 2, dims.height - 4);
                batch.draw(br1, dims.x + dims.width - 4, dims.y + 2, 2, dims.height - 4);

                batch.setColor(new Color(0f, 1f, 1f, 1f));

                batch.draw(cr1, dims.x, dims.y + dims.height - 11, 11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + dims.height - 11, -11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + 11, -11, -11);
                batch.draw(cr1, dims.x, dims.y + 11, 11, -11);
            });
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected final WidgetGroup createActor() {
        return new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}