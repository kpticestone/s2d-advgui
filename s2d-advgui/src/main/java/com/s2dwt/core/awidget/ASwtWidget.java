package com.s2dwt.core.awidget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.leo.commons.utils.TNull;
import com.s2dwt.core.stage.ISwtStage;

public abstract class ASwtWidget<ACTOR extends Actor> extends ASwtWidget_970_Rendering<ACTOR> {
    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(ISwtStage<?, ?> pContext) {
        super(pContext);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidget(ISwtWidget<? extends Group> pParent, boolean focusable) {
        super(TNull.checkNull(pParent), focusable);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Deprecated
    public ACTOR getActor_OnlyForDevelopmentAndHasToBeDeletedLater() {
        return this.actor;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void makeNewBorder() {
        BorderDrawer2 borderDrawer = new BorderDrawer2(this.context);
        this.addDrawerBackground((batch, pScreenCoords, dims) -> {
            if (isEnabled()) {
                batch.setColor(new Color(0f, 1f, 1f, .75f));
            } else {
                batch.setColor(new Color(.5f, 0f, 0f, .75f));
            }
            borderDrawer.drawIt(batch, dims);
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void makeOldBorder() {
        TextureRegion trYEL = this.context.getColor_Selected();
        TextureRegion trREG = this.context.getColor_Unselected();
        TextureRegion tr = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion corner2 = this.context.getResourceManager().getTextureRegion("ui2/corner-2.png");
        TextureRegion borderhor = this.context.getResourceManager().getTextureRegion("ui2/border-1-hor.png");
        TextureRegion borderver = this.context.getResourceManager().getTextureRegion("ui2/border-1-ver.png");
        TextureRegion trDIS = this.context.getColor_Disabled();

        this.addDrawerBackground((batch, pScreenCoords, dims) -> {
            if (isEnabled()) {
                batch.setColor(new Color(0f, 1f, 1f, .75f));
            } else {
                batch.setColor(new Color(.5f, 0f, 0f, .75f));
            }
            batch.draw(tr, dims.x + 2, dims.y + 2, dims.width - 4, dims.height - 4);

            batch.setColor(new Color(.04f, .59f, .83f, 1f));
            batch.draw(corner2, dims.x, dims.y + dims.height - 5, 5, 5);
            batch.draw(corner2, dims.x + dims.width, dims.y + dims.height - 5, -5, 5);

            batch.draw(corner2, dims.x, dims.y + 5, 5, -5);
            batch.draw(corner2, dims.x + dims.width, dims.y + 5, -5, -5);

            batch.draw(borderhor, dims.x + 5, dims.y, dims.width - 10, 4);
            batch.draw(borderhor, dims.x + 5, dims.y + dims.height - 4, dims.width - 10, 4);
            batch.draw(borderver, dims.x, dims.y + 5, 4, dims.height - 10);
            batch.draw(borderver, dims.x + dims.width - 4, dims.y + 5, 4, dims.height - 10);
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}