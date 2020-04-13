package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.ActorCreatorWidgetGroup;
import de.s2d_advgui.core.dnd.IDragAndDropHandler;
import de.s2d_advgui.core.dnd.IDragAndDropHelper;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtPanelMovable extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanelMovable(ISwtWidget<? extends Group> pParent) {
        super(new SwtWidgetBuilder<>(pParent, false, ActorCreatorWidgetGroup.createInstance(true)));
        IDragAndDropHelper dnd = this.addDragAndDrop(new IDragAndDropHandler() {
            @Override
            public void onDrag(float newX, float newY) {
                setPosition(newX, newY);
            }
        });
        TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion cr1 = this.context.getTextureRegion(ATheme.UI_CORNER_1_PNG);
        this.addDrawerForeground((pBatchDrawer, pScreenCoords, dims) -> {
            try (SwtDrawer_Batch<?> batch = pBatchDrawer.startBatchDrawer()) {
                if (dnd.isInDrag()) {
                    batch.drawText("jasdjsao: " + getBounds() + "/" + getParent(), dims, Align.center, .5f, true,
                            Color.RED);
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
                }
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
