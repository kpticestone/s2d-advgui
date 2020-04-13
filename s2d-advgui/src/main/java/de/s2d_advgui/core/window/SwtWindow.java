package de.s2d_advgui.core.window;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ASwtWidget_ControllerLevel;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.InternalWidgetDrawerBatch;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;
import de.s2d_advgui.core.awidget.acc.ActorCreatorWidgetGroup;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;
import de.s2d_advgui.core.dnd.IDragAndDropHandler;
import de.s2d_advgui.core.input.keys.ASwtInputRegister_Keys;
import de.s2d_advgui.core.layoutmanager.ASwtLayoutManager;
import de.s2d_advgui.core.rendering.SwtDrawer_Batch;
import de.s2d_advgui.core.resourcemanager.ATheme;
import de.s2d_advgui.core.tabledata.ESwtTableMode;
import de.s2d_advgui.core.tabledata.SwtLayoutDataCellPosition;
import de.s2d_advgui.core.tabledata.TableDataManager;
import de.s2d_advgui.core.utils.RectangleFactory;

public class SwtWindow extends ASwtWidget_ControllerLevel<WidgetGroup> implements ISwtWindow {
    // -------------------------------------------------------------------------------------------------------------------------
    public static void showMessage(ISwtWidget<? extends Group> pParent, String title, String string) {
        SwtWindow window = new SwtWindow(pParent, title, 320, 200);
        window.getButtonPanel().addCloseButton("OKAY");
        SwtLabel lab = new SwtLabel(window);
        lab.setWrap(true);
        lab.setBounds(0, 0, 0, 0);
        lab.setText(string);
        lab.setAlign(Align.center);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtPanel windowPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtWindowTopPanel windowPanelTop;

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtWindowContentPanel contentPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtWindowBottomPanel buttonPanel;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int prefWidth;

    // -------------------------------------------------------------------------------------------------------------------------
    private final int prefHeight;

    // -------------------------------------------------------------------------------------------------------------------------
    private final boolean modal;

    // -------------------------------------------------------------------------------------------------------------------------
    boolean inDrag = false;

    // -------------------------------------------------------------------------------------------------------------------------
    static ISwtWidget<? extends Group> top(ISwtWidget<? extends Group> someP) {
        ISwtWidget<? extends Group> cur = someP;
        while (cur.getParent() != null) {
            cur = cur.getParent();
        }
        return cur;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindow(ISwtWidget<? extends Group> pParent, String titleLabel, int prefWidth, int prefHeight) {
        this(pParent, titleLabel, true, prefWidth, prefHeight);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWindow(ISwtWidget<? extends Group> pParent, String titleLabel, boolean modal, int prefWidth,
            int prefHeight) {
        super(new SwtWidgetBuilder<>(top(pParent), false, ActorCreatorWidgetGroup.createInstance(true)), modal);
        this.modal = modal;
        this.setBounds(-prefWidth, -prefHeight, prefWidth, prefHeight);
        this.setLayoutNegativePositions(false);
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
        if (modal) {
            ASwtInputRegister_Keys akx = new ASwtInputRegister_Keys();
            akx.bind(Keys.ESCAPE).target(down -> {
                if (!down) {
                    dispose();
                    return true;
                }
                return false;
            });
            this.addKeyHandler(akx);

            Color modalWindowOverlayColor = this.getTheme().getWidgetWindowOverlayColor();
            TextureRegion rx = this.getResourceManager().getColorTextureRegion(modalWindowOverlayColor, 1, 1);
            this.addDrawerBackground(new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle dims) {
                    pBatch.draw(rx, dims.x, dims.y, dims.width, dims.height);
                }
            });
        } else {
            this.addDrawerBackground(new InternalWidgetDrawerBatch() {
                @Override
                protected void _drawIt(SwtDrawer_Batch<?> pBatch, Vector2 pScreenCoords, Rectangle pDims) {
                    if (SwtWindow.this.inDrag) {
                        pBatch.drawText("jasdjsao: " + getBounds() + "/" + getParent(), pDims, Align.center, .5f, true,
                                Color.RED);
                        pBatch.drawBorder("/borders/white-round-5.png", RectangleFactory.explode(pDims, -10));
                    } else if (TOldCompatibilityCode.FALSE) {
                        Color col = pBatch.getResourceManager().getTheme().getWidgetPrimaryBorderColor();
                        pBatch.setColor(col);
                        pBatch.drawBorder("/borders/white-round-5.png", RectangleFactory.explode(pDims, -10));
                    }
                }
            });
        }

        this.windowPanel = new SwtPanel(this);

        this.windowPanelTop = new SwtWindowTopPanel(this.windowPanel, titleLabel, () -> dispose());
        this.windowPanelTop.setLayoutData(new SwtLayoutDataCellPosition(0, 0));
        this.windowPanelTop.addDragAndDrop(new IDragAndDropHandler() {
            @Override
            public void onDragStart() {
                SwtWindow.this.inDrag = true;
                SwtWindow.this.parent.bringToFront(SwtWindow.this);
            }

            @Override
            public void onDrag(float newX, float newY) {
                Rectangle bnds = getBounds();
                newX = bnds.getX() + newX;
                newY = bnds.getY() - newY;
                SwtWindow.this.setPosition(newX, newY);
            }

            @Override
            public void onDragStop() {
                SwtWindow.this.inDrag = false;
            }
        });

        this.contentPanel = new SwtWindowContentPanel(this.windowPanel);
        this.contentPanel.setLayoutData(new SwtLayoutDataCellPosition(0, 1));

        this.buttonPanel = new SwtWindowBottomPanel(this.windowPanel, () -> {
            SwtWindow.this.calcPositions();
            this.windowPanelTop.btnClose.setVisible(false);
        }, () -> dispose());
        this.buttonPanel.setLayoutData(new SwtLayoutDataCellPosition(0, 2));

        this.setLayoutManager(new ASwtLayoutManager() {
            private float oldWidth;
            private float oldHeight;

            @Override
            public void calculate(ISwtWidget<?> pWidget, float width, float height) {
                if (this.oldWidth != width && this.oldHeight != height) {
                    this.oldWidth = width;
                    this.oldHeight = height;
                    float useWidth = Math.min(prefWidth, width);
                    float useHeight = Math.min(prefHeight, height);
                    float ax = (width - useWidth) / 2f;
                    float ay = (height - useHeight) / 2f;
                    for (ISwtWidget<?> a : pWidget.getChildren()) {
                        a.setBounds(ax, ay, useWidth, useHeight);
                    }
                }
            }
        });

        TableDataManager tableDataManager = new TableDataManager(this.getResourceManager(),
                ATheme.BORDERS_WHITE_NB_ROUND_5_PNG, ESwtTableMode.CUSTOM, 1, 3);
        tableDataManager.setCustomCalc((pWidget, width, height, o1) -> {
            float butPanHe = this.buttonPanel.isPanelVisible() ? 35 : 0;
            this.windowPanelTop.setSize(width - o1 * 2, 25);
            this.contentPanel.setSize(width - o1 * 2, height - 25 - butPanHe - o1 * 4);
            this.buttonPanel.setSize(width - o1 * 2, butPanHe);
        });
        this.windowPanel.setLayoutManager(tableDataManager.getLayoutManager());
        this.windowPanel
                .addDrawerBackground(tableDataManager.getBatchDrawer(this.getTheme().getWidgetPrimaryBorderColor()));

        TextureRegion cr1 = this.context.getTextureRegion("ui/corner-1.png");
        this.windowPanel.addDrawerBackground(new InternalWidgetDrawerBatch() {
            @Override
            protected void _drawIt(SwtDrawer_Batch<?> batch, Vector2 pScreenCoords, Rectangle dims) {
                batch.setColor(new Color(0f, 1f, 1f, 1f));
                batch.draw(cr1, dims.x, dims.y + dims.height - 11, 11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + dims.height - 11, -11, 11);
                batch.draw(cr1, dims.x + dims.width, dims.y + 11, -11, -11);
                batch.draw(cr1, dims.x, dims.y + 11, 11, -11);
            }
        });

        this.setDelegatedParent(this.contentPanel);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final SwtWindowBottomPanel getButtonPanel() {
        return this.buttonPanel;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public final void applyWindowDims(Rectangle dims) {
        if (this.modal) {
            this.actor.setBounds(0, 0, dims.width, dims.height);
        } else {
            Rectangle myBnds = this.getBounds();
            if (myBnds.x == -this.prefWidth && myBnds.y == -this.prefHeight) {
                this.setPosition((dims.width - myBnds.width) / 2f, (dims.height - myBnds.height) / 2f);
            }
            myBnds = this.getBounds();
            float tx = myBnds.x;
            float ty = dims.height - myBnds.y - myBnds.height;
            if (tx < 0) tx = 0;
            if (tx > dims.width - myBnds.width) tx = dims.width - myBnds.width;
            if (ty < 0) ty = 0;
            if (ty > dims.height - myBnds.height) ty = dims.height - myBnds.height;
            this.actor.setBounds(tx, ty, myBnds.width, myBnds.height);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
