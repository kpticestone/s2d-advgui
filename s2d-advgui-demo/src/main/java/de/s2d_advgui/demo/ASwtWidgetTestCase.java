package de.s2d_advgui.demo;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.commons.IInfoSupport;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.singstar.ASingStarDescriptorString;

public abstract class ASwtWidgetTestCase extends ASingStarDescriptorString {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final IInfoSupport _info;

    // -------------------------------------------------------------------------------------------------------------------------
    public ASwtWidgetTestCase(@Nonnull String id, @Nonnull IInfoSupport pInfo) {
        super(id);
        this._info = pInfo;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final IInfoSupport getInfo() {
        return this._info;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public abstract ASwtWidgetTestPanel buildTests(ASwtWidget<? extends Group> pParent);

    // -------------------------------------------------------------------------------------------------------------------------
}
