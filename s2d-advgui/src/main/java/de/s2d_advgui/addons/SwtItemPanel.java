package de.s2d_advgui.addons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ASwtWidgetSelectable;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.basicwidgets.SwtImage;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;

public class SwtItemPanel extends ASwtWidgetSelectable<WidgetGroup>
{
    // -------------------------------------------------------------------------------------------------------------------------
    private SwtImage img;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtLabel txt;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtItemPanel(ASwtWidget<? extends Group> pParent)
    {
        super(pParent);
        this.img = new SwtImage(this);
        this.img.setBounds(9 + 3, 2 + 3, 26, 26);
        this.txt = new SwtLabel(this);
        this.txt.setBounds(45, 2, -45, 15);

        this.makeDrawBorderSupport();

        TextureRegion trYEL = this.context.getColor_Selected();
        TextureRegion trREG = this.context.getColor_Unselected();
        TextureRegion bbb = this.context.getTextureRegion("/ui/back_white_075.png");
        TextureRegion yyy = this.context.getTextureRegion("/ui/back_cyan.png");

        this.addDrawerForeground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle dims)
            {
                float ax = dims.x, ay = dims.y, aw = dims.width, ah = dims.height;
                TextureRegion use = isFocused() ? trYEL : trREG;
                pBatch.draw(use, ax + 2, ay + 2, 5, ah - 4);
                if (img.getImage() == null)
                {
                    if (isFocused())
                    {
                        pBatch.draw(yyy, ax + 10, ay + 2, 32, 32);
                    }
                    else
                    {
                        pBatch.draw(bbb, ax + 10, ay + 2, 32, 32);
                    }
                }
            }
        });

        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims)
            {
                float ax = dims.x, ay = dims.y, aw = dims.width, ah = dims.height;
                TextureRegion use = isFocused() ? trYEL : trREG;
                batch.draw(use, ax + 9, ay + 2, 6, 1);
                batch.draw(use, ax + 9, ay + 2, 1, 6);
                batch.draw(use, ax + aw - 9, ay + 2, 6, 1);
                batch.draw(use, ax + aw - 3, ay + 2, 1, 6);
                batch.draw(use, ax + aw - 8, ay + ah - 3, 6, 1);
                batch.draw(use, ax + aw - 3, ay + ah - 8, 1, 6);
                batch.draw(use, ax + 35, ay + ah - 3, 6, 1);
                batch.draw(use, ax + 40, ay + ah - 8, 1, 6);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup _createActor()
    {
        WidgetGroup back = new WidgetGroup()
        {
            @Override
            public void draw(Batch batch, float parentAlpha)
            {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setText(String pText)
    {
        this.txt.setText(pText);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setImage(String pResourceId)
    {
        this.img.setImage(pResourceId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
