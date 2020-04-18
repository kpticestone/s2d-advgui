package de.s2d_advgui.core.pagebook;

import com.badlogic.gdx.scenes.scene2d.Group;
import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public final class SwtPagebook extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<String, SwtPagebookItem> bookitems = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private String selectedItemName;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPagebook(ISwtWidget<? extends Group> pParent) {
        super(pParent);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPagebookItem addItem(String name) {
        SwtPagebookItem back = new SwtPagebookItem(this);
        boolean empty = this.getChildren().isEmpty();
        if (empty) {
            selectedItemName = name;
        }
        back.setVisible(empty);

        this.bookitems.put(name, back);
        return back;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSelectedItem(String itemName) {
        this.selectedItemName = itemName;
        SwtPagebookItem sel = this.bookitems.get(itemName);
        for (SwtPagebookItem item : this.bookitems.values()) {
            item.setVisible(item == sel);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public String getSelectedItemName() {
        return selectedItemName;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtPagebookItem getSelectedItemPanel() {
        return bookitems.get(selectedItemName);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
