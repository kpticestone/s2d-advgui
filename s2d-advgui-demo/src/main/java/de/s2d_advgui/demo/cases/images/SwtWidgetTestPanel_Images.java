package de.s2d_advgui.demo.cases.images;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Scaling;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtImage;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

final class SwtWidgetTestPanel_Images extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    SwtWidgetTestPanel_Images(ISwtWidget<? extends Group> pParent) {
        super(pParent, 50);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
        builds.add(new ICons("fill", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fill);
            return img;
        }));
        builds.add(new ICons("fillX", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fillX);
            return img;
        }));
        builds.add(new ICons("fillY", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fillY);
            return img;
        }));
        builds.add(new ICons("fit", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.fit);
            return img;
        }));
        builds.add(new ICons("none", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.none);
            return img;
        }));
        builds.add(new ICons("stretch", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretch);
            return img;
        }));
        builds.add(new ICons("stretchX", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretchX);
            return img;
        }));
        builds.add(new ICons("stretchY", (x) -> {
            SwtImage img = new SwtImage(x);
            img.setImage("icons/128/workplace.png");
            img.setScalingMode(Scaling.stretchY);
            return img;
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}