package de.s2d_advgui.addons.framebuffer;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.IActorCreator;
import de.s2d_advgui.core.rendering.IRend123;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;

public class SwtWidget_FrameBuffer extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final String framebufferId;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidget_FrameBuffer(ASwtWidget<? extends Group> pParent, String framebufferId) {
        super(new SwtWidgetBuilder<>(pParent, false, new IActorCreator<WidgetGroup>() {
            @Override
            public WidgetGroup createActor(@Nonnull IRend123 pRend) {
                return new WidgetGroup() {
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
                    }
                };
            }
        }));

        this.framebufferId = framebufferId;
        this.addDrawerClipableMiddle(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle dims) {
                SwtFrameBuffer fb = SwtWidget_FrameBuffer.this.context.getResourceManager()
                        .getFrameBuffer(framebufferId);
                if (fb != null) {
                    TextureRegion rgx = fb.getRegion();
                    pBatch.draw(rgx, dims.x, dims.y, dims.width, dims.height);
                }
            }
        });
        this.setHeight(200);
        this.makeDrawBorderSupport();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup __createActor() {
        return new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
