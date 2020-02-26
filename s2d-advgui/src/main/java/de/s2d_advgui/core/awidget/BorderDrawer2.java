package de.s2d_advgui.core.awidget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import de.s2d_advgui.core.SwtColor;
import de.s2d_advgui.core.stage.ISwtStage;

public class BorderDrawer2 {

    private final ISwtStage<?, ?> context;
    private TextureRegion tr;
    private TextureRegion corner2;
    private TextureRegion borderhor;
    private TextureRegion borderver;

    private Color borderColor = SwtColor.BORDER_COLOR_CYAN;
    private Color backgroundColor = SwtColor.BORDER_COLOR_CYAN_DARK;

    public BorderDrawer2(ISwtStage<?, ?> context) {
        this.context = context;
        this.tr = this.context.getResourceManager().getColorTextureRegion(Color.WHITE, 1, 1);
        this.corner2 = this.context.getResourceManager().getTextureRegion("ui/button-corner-2019-08-11.png");
        this.borderhor = this.context.getResourceManager().getTextureRegion("ui/button-hor-2019-08-11.png");
        this.borderver = this.context.getResourceManager().getTextureRegion("ui/button-ver-2019-08-11.png");
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void drawIt(Batch batch, Rectangle dims) {
        this.drawIt(batch, dims.x, dims.y, dims.width, dims.height);
    }

    public void drawIt(Batch batch, float x, float y, float width, float height) {
        int si = 7;

        batch.setColor(this.backgroundColor);
        batch.draw(this.tr, x + 2, y + 2, width - 4, height - 4);

        batch.setColor(this.borderColor);

        batch.draw(this.corner2, x, y + height - si, si, si);
        batch.draw(this.corner2, x + width, y + height - si, -si, si);

        batch.draw(this.corner2, x, y + si, si, -si);
        batch.draw(this.corner2, x + width, y + si, -si, -si);

        batch.draw(this.borderhor, x + si, y + si, width - si * 2, -si);
        batch.draw(this.borderhor, x + si, y + height - si, width - si * 2, si);
        batch.draw(this.borderver, x, y + si, si, height - si * 2);
        batch.draw(this.borderver, x + width, y + si, -si, height - si * 2);
    }

    public void setGenericColors(boolean enabled, boolean focused, boolean hovered) {
        if (!enabled) {
            this.setBackgroundColor(Color.DARK_GRAY);
            this.setBorderColor(Color.GRAY);
        } else {
            this.setBackgroundColor((focused ? SwtColor.FOCUS : SwtColor.BORDER_COLOR_CYAN_HALF));
            this.setBorderColor(hovered ? new Color(1f, 0f, 0f, 1f) : SwtColor.BORDER_COLOR_CYAN);
        }
    }

}
