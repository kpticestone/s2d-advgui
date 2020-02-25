package com.s2dwt.addons.framebuffer;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.s2dwt.core.awidget.ASwtWidget;
import com.s2dwt.core.awidget.IInternalWidgetDrawer;

public class SwtWidget_FrameBuffer extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final String framebufferId;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidget_FrameBuffer(ASwtWidget<? extends Group> pParent, String framebufferId) {
        super(pParent, false);
        this.framebufferId = framebufferId;
        this.addDrawerClipableMiddle(new IInternalWidgetDrawer() {
            @Override
            public void drawIt(Batch batch, Vector2 pScreenCoords, Rectangle dims) {
                SwtFrameBuffer fb = SwtWidget_FrameBuffer.this.context.getResourceManager().getFrameBuffer(framebufferId);
                if (fb != null) {
                    TextureRegion rgx = fb.getRegion();
                    batch.draw(rgx, dims.x, dims.y, dims.width, dims.height);
                }
            }
        });
        this.h = 200;
        this.makeDrawBorderSupport();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup createActor() {
        return new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
