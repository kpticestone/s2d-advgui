package de.s2d_advgui.core.basicwidgets;

import java.util.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.awidget.SwtWidgetBuilder;

public class SwtImage extends ASwtWidget<Image> {
    // -------------------------------------------------------------------------------------------------------------------------
    private String img;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtImage(ISwtWidget<? extends Group> pParent) {
        // super(pParent, false);
        super(new SwtWidgetBuilder<>(pParent, false, new ActorCreatorImage()));
        this.actor.setScaling(Scaling.fit);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtImage(ISwtWidget<? extends Group> pParent, String pResourceId) {
        this(pParent);
        this.setImage(pResourceId);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setImage(String pResourceId) {
        if (!Objects.equals(pResourceId, this.img)) {
            this.img = pResourceId;
            if (pResourceId != null) {
                this.actor.setDrawable(this.context.getDrawable(pResourceId));
            } else {
                this.actor.setDrawable(null);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getImage() {
        return this.img;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setScalingMode(Scaling fill) {
        this.actor.setScaling(fill);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setAlign(int align) {
        this.actor.setAlign(align);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearImage() {
        this.setImage(null);
    }

    public void setColor(Color color) {
        actor.setColor(color);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
