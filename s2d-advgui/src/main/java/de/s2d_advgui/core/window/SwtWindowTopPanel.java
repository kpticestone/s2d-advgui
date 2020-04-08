package de.s2d_advgui.core.window;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.commons.Trigger;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public final class SwtWindowTopPanel extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtLabel title;

    // -------------------------------------------------------------------------------------------------------------------------
    SwtButton btnClose;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindowTopPanel(ISwtWidget<? extends Group> pParent, String titleLabel, Trigger pCloseTrigger) {
        super(pParent);
        this.title = new SwtLabel(this, titleLabel);
        this.title.setColor(Color.CYAN);
        this.title.setBounds(10, 0, -40, 0);
        this.title.setAlign(Align.left);

        this.btnClose = new SwtButton(this);
        this.btnClose.setText("X");
        this.btnClose.addLeftClickListener(pCloseTrigger);
        this.btnClose.setBounds(-40, 0, 30, 0);

        ATheme theme = this.getTheme();
        TextureRegion bl = this.getResourceManager().getColorTextureRegion(theme.getWidgetPrimaryBackgroundColor());
        this.addDrawerClipableBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                pBatch.draw(bl, pDims);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
