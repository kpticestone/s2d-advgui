package de.s2d_advgui.core.pagebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

public final class SwtPagebook extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<String, SwtPagebookItem> bookitems = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final List<String> tabNames = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPagebook(ISwtWidget<? extends Group> pParent) {
        super(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPagebookItem addItem(String name) {
        SwtPagebookItem back = new SwtPagebookItem(this);
        back.setVisible(this.getChildren().isEmpty());
        this.bookitems.put(name, back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSelectedItem(String name) {
        SwtPagebookItem sel = this.bookitems.get(name);
        for (SwtPagebookItem item : this.bookitems.values()) {
            item.setVisible(item == sel);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
