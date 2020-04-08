package de.s2d_advgui.core.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public final class SwtWindowContentPanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindowContentPanel(ISwtWidget<? extends Group> pParent) {
        super(pParent);
        Texture bg = this.getResourceManager().getTexture(ATheme.UI_BACKGROUND_1);
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                batch.setColor(new Color(.04f, .59f, .83f, 1f));
                batch.getBatch().draw(bg, dims.x, dims.y, 0, 0, (int) dims.width,
                        (int) dims.height);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
