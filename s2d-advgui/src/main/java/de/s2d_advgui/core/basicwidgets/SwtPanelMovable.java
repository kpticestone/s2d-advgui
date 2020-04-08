package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;

public class SwtPanelMovable extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    boolean inDrag = false;
    float dragX;
    float dragY;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPanelMovable(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);
        TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion cr1 = this.context.getTextureRegion(ATheme.UI_CORNER_1_PNG);
        this.addDrawerForeground((pBatchDrawer, pScreenCoords, dims) -> {
            try (SwtDrawer_Batch<?> batch = pBatchDrawer.startBatchDrawer()) {
                if (inDrag) {
                    batch.drawText("jasdjsao: " + getBounds() + "/" + getParent(), dims, Align.center, .5f, true, Color.RED);
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
    @Override
    protected final WidgetGroup createActor() {
        WidgetGroup back = new WidgetGroup() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        back.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent) {
                    InputEvent inputEvent = (InputEvent) event;
                    Type ty = inputEvent.getType();
                    if (ty == Type.touchDown) {
                        // System.err.println(getBounds());
                        inDrag = true;
                        dragX = inputEvent.getStageX();
                        dragY = inputEvent.getStageY();
                        return true;
                    } else if (ty == Type.touchDragged) {
                        if (inDrag) {
                            Rectangle bnds = getBounds();
                            float newX = bnds.getX() + (inputEvent.getStageX() - dragX);
                            float newY = bnds.getY() - (inputEvent.getStageY() - dragY);
                            setPosition(newX, newY);
                            dragX = inputEvent.getStageX();
                            dragY = inputEvent.getStageY();
                            return true;
                        }
                        return false;
                    } else if (ty == Type.touchUp) {
                        inDrag = false;
                    } else
                        System.err.println("inputevent: " + ty);

                } else {
                    System.err.println("event: " + event.getClass());
                }
                return false;
            }
        });
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
