package com.s2dwt.core.awidget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.leo.commons.utils.TNull;
import com.s2dwt.core.rendering.SwtDrawer_Batch;
import com.s2dwt.core.stage.ISwtStage;

public abstract class ASwtWidget<ACTOR extends Actor> extends ASwtWidget_970_Rendering<ACTOR>
{
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(ISwtStage<?, ?> pContext)
    {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(ISwtWidget<? extends Group> pParent, boolean focusable)
    {
        super(TNull.checkNull(pParent), focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public ACTOR getActor_OnlyForDevelopmentAndHasToBeDeletedLater()
    {
        return this.actor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void makeNewBorder()
    {
        BorderDrawer2 borderDrawer = new BorderDrawer2(this.context);
        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle pDims)
            {
                if (isEnabled())
                {
                    batch.setColor(new Color(0f, 1f, 1f, .75f));
                }
                else
                {
                    batch.setColor(new Color(.5f, 0f, 0f, .75f));
                }
                borderDrawer.drawIt(batch.getBatch(), pDims);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void makeOldBorder()
    {
        TextureRegion trYEL = this.context.getColor_Selected();
        TextureRegion trREG = this.context.getColor_Unselected();
        TextureRegion tr = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion corner2 = this.context.getResourceManager().getTextureRegion("ui/corner-2.png");
        TextureRegion borderhor = this.context.getResourceManager().getTextureRegion("ui/border-1-hor.png");
        TextureRegion borderver = this.context.getResourceManager().getTextureRegion("ui/border-1-ver.png");
        TextureRegion trDIS = this.context.getColor_Disabled();

        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims)
            {
                if (isEnabled())
                {
                    pBatch.setColor(new Color(0f, 1f, 1f, .75f));
                }
                else
                {
                    pBatch.setColor(new Color(.5f, 0f, 0f, .75f));
                }
                pBatch.draw(tr, pDims.x + 2, pDims.y + 2, pDims.width - 4, pDims.height - 4);

                pBatch.setColor(new Color(.04f, .59f, .83f, 1f));
                pBatch.draw(corner2, pDims.x, pDims.y + pDims.height - 5, 5, 5);
                pBatch.draw(corner2, pDims.x + pDims.width, pDims.y + pDims.height - 5, -5, 5);

                pBatch.draw(corner2, pDims.x, pDims.y + 5, 5, -5);
                pBatch.draw(corner2, pDims.x + pDims.width, pDims.y + 5, -5, -5);

                pBatch.draw(borderhor, pDims.x + 5, pDims.y, pDims.width - 10, 4);
                pBatch.draw(borderhor, pDims.x + 5, pDims.y + pDims.height - 4, pDims.width - 10, 4);
                pBatch.draw(borderver, pDims.x, pDims.y + 5, 4, pDims.height - 10);
                pBatch.draw(borderver, pDims.x + pDims.width - 4, pDims.y + 5, 4, pDims.height - 10);
            }

        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
