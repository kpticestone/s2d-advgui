package com.s2dwt.core.window;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.leo.commons.utils.Trigger;
import com.s2dwt.core.awidget.ASwtWidget_ControllerLevel;
import com.s2dwt.core.awidget.ISwtWidget;
import com.s2dwt.core.awidget.InternalWidgetDrawerBatch;
import com.s2dwt.core.basicwidgets.SwtButton;
import com.s2dwt.core.basicwidgets.SwtLabel;
import com.s2dwt.core.basicwidgets.SwtPanel;
import com.s2dwt.core.input.keys.ASwtInputRegister_Keys;
import com.s2dwt.core.layoutmanager.ASwtLayoutManager;
import com.s2dwt.core.rendering.SwtDrawer_Batch;

public class SwtWindow extends ASwtWidget_ControllerLevel<WidgetGroup> implements ISwtWindow
{
    // -------------------------------------------------------------------------------------------------------------------------
    public static void showMessage(ISwtWidget<? extends Group> pParent, String title, String string)
    {
        SwtWindow window = new SwtWindow(pParent, title, 320, 200);
        window.addCloseButton("OKAY");
        SwtLabel lab = new SwtLabel(window);
        lab.setWrap(true);
        lab.setBounds(0, 0, 0, 0);
        lab.setText(string);
        lab.setAlign(Align.center);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtPanel buttonPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtButton btnClose;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtPanel windowPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtLabel title;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtPanel contentPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtButton buttonClose;

    // -------------------------------------------------------------------------------------------------------------------------
    private int prefWidth;

    // -------------------------------------------------------------------------------------------------------------------------
    private int prefHeight;

    // -------------------------------------------------------------------------------------------------------------------------
    static ISwtWidget<? extends Group> top(ISwtWidget<? extends Group> someP)
    {
        ISwtWidget<? extends Group> cur = someP;
        while (cur.getParent() != null)
        {
            cur = cur.getParent();
        }
        return cur;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindow(ISwtWidget<? extends Group> pParent, String titleLabel, int prefWidth, int prefHeight)
    {
        super(top(pParent), false);
        ASwtInputRegister_Keys akx = new ASwtInputRegister_Keys();
        akx.bind(Keys.ESCAPE).target(down ->
        {
            if (!down)
            {
                dispose();
                return true;
            }
            return false;
        });
        this.addKeyHandler(akx);

        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;

        TextureRegion rx = this.context.getResourceManager().getColorTextureRegion(new Color(0f, .5f, .5f, 0.9f), 1, 1);
        this.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle dims)
            {
                pBatch.draw(rx, dims.x, dims.y, dims.width, dims.height);
            }
        });

        this.windowPanel = new SwtPanel(this, false);

        TextureRegion br1 = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        TextureRegion cr1 = this.context.getTextureRegion("ui/corner-1.png");
        Texture bg = this.context.getResourceManager().getTexture("ui/background-1.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.windowPanel.addDrawerBackground(new InternalWidgetDrawerBatch()
        {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims)
            {
                // if( TOldCompatibilityCode.FALSE )
                {
                    // batch.setColor(new Color(.04f, .59f, .83f, .9f));
                    batch.setColor(new Color(.5f, .59f, .83f, .9f));
                    batch.draw(br1, dims.x + 2, dims.y + 2, dims.width - 4, 40);
                    batch.draw(br1, dims.x + 2, dims.y + dims.height - 34, dims.width - 4, 32);
                }

                batch.setColor(new Color(.04f, .59f, .83f, 1f));

                batch.getBatch().draw(bg, dims.x + 4, dims.y + 4, 0, 0, (int)dims.width - 8, (int)dims.height - 8);

                batch.draw(br1, dims.x + 2, dims.y + dims.height - 4, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + 40, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + 2, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + dims.height - 34, dims.width - 4, 2);
                batch.draw(br1, dims.x + 2, dims.y + 2, 2, dims.height - 4);
                batch.draw(br1, dims.x + dims.width - 4, dims.y + 2, 2, dims.height - 4);

                batch.setColor(new Color(0f, 1f, 1f, 1f));

                batch.draw(cr1, dims.x, dims.y + dims.height - 11, 11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + dims.height - 11, -11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + 11, -11, -11);
                batch.draw(cr1, dims.x, dims.y + 11, 11, -11);

                batch.setColor(Color.WHITE);
            }

        });

        this.windowPanel.setBounds(200, 200, 400, 400);

        this.title = new SwtLabel(this.windowPanel, titleLabel);
        this.title.setColor(Color.CYAN);
        this.title.setBounds(25, 2, -80, 32);

        this.btnClose = new SwtButton(this.windowPanel);
        this.btnClose.setBounds(-38, 8, 30, 30);
        this.btnClose.setText("X");
        this.btnClose.addLeftClickListener(() -> dispose());

        this.buttonPanel = new SwtPanel(this.windowPanel, false);
        this.buttonPanel.setBounds(0, -42, 0, 35);

        this.setLayoutManager(new ASwtLayoutManager()
        {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height)
            {
                float useWidth = Math.min(prefWidth, width);
                float useHeight = Math.min(prefHeight, height);
                float ax = (width - useWidth) / 2f;
                float ay = (height - useHeight) / 2f;
                SwtWindow.this.windowPanel.setBounds(ax, ay, useWidth, useHeight);
            }
        });

        this.buttonPanel.setLayoutManager(new ASwtLayoutManager()
        {
            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height)
            {
                int ax = 10;
                int wi = 100;
                int space = 10;
                for (ISwtWidget<?> some : pWidget.getChildren())
                {
                    some.setBounds(ax, 5, wi, 30);
                    ax += wi + space;
                }

            }
        });
        this.contentPanel = new SwtPanel(this.windowPanel, false);
        this.contentPanel.setBounds(10, 40, -20, -90);

        this.addUpdateHandler((delta) ->
        {
            this.btnClose.setVisible(this.buttonClose == null);

        });

        this.setDelegatedParent(this.contentPanel);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(String text, Trigger trigger)
    {
        SwtButton save = new SwtButton(this.buttonPanel, text != null ? text : "SAVE");
        save.addLeftClickListener(() ->
        {
            if (trigger != null)
            {
                trigger.onTrigger();
            }
            SwtWindow.this.dispose();
        });
        return save;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(Trigger trigger)
    {
        return this.addSaveButton(null, trigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addSaveButton(String text)
    {
        return this.addSaveButton(text, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(String text, Trigger trigger)
    {
        this.buttonClose = new SwtButton(this.buttonPanel, text != null ? text : "CANCEL");
        this.buttonClose.addLeftClickListener(() ->
        {
            if (trigger != null)
            {
                trigger.onTrigger();
            }
            SwtWindow.this.dispose();
        });
        return this.buttonClose;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton()
    {
        return this.addCloseButton(null, null);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(Trigger trigger)
    {
        return this.addCloseButton(null, trigger);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtButton addCloseButton(String text)
    {
        return this.addCloseButton(text, null);
    }

    //  public SwtButton addButton(String label, Trigger trigger) {
    //      SwtButton back = new SwtButton(this.buttonPanel);
    //      back.setText(label);
    //      back.addLeftClickListener(()->{
    //
    //      });
    //      return back;
    //  }
    //
    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup createActor()
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
}
