package de.s2d_advgui.demo.cases.canvas;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.tabledata.ESwtTableMode;
import de.s2d_advgui.demo.cases.ASwtWidgetTestPanelWidth2DimRaster;
import de.s2d_advgui.demo.cases.ICons;

public class SwtWidgetTestPanel_Canvas extends ASwtWidgetTestPanelWidth2DimRaster {
    // -------------------------------------------------------------------------------------------------------------------------
    public SwtWidgetTestPanel_Canvas(ISwtWidget<? extends Group> pParent) {
        super(pParent, ESwtTableMode.FULLFILL, 100, 2);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void fillBuilds(List<ICons> builds) {
        if (TOldCompatibilityCode.FALSE) {
            builds.add(new ICons("regular", pParent -> {
                return new SwtCanvas_DemoScene1(pParent);
            }));
            builds.add(new ICons("disabled", pParent -> {
                SwtCanvas_DemoScene1 back = new SwtCanvas_DemoScene1(pParent);
                back.setDisabled();
                return back;
            }));
        }
        builds.add(new ICons("mouse coordinates 1", pParent -> {
            return new SwtPanel_DemoScene2b(pParent);
        }));
        builds.add(new ICons("entity coordinates 1", pParent -> {
            return new SwtPanel_DemoScene2(pParent);
        }));
        builds.add(new ICons("entity coordinates 2", pParent -> {
            return new SwtPanel_DemoScene2(pParent);
        }));
        builds.add(new ICons("entity coordinates 3", pParent -> {
            return new SwtPanel_DemoScene2(pParent);
        }));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
