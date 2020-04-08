package de.s2d_advgui.core.tabfolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import de.s2d_advgui.core.awidget.ISwtWidget;
import de.s2d_advgui.core.basicwidgets.SwtButton;
import de.s2d_advgui.core.basicwidgets.SwtLabel;
import de.s2d_advgui.core.basicwidgets.SwtPanel;

public class SwtTabFolder extends SwtPanel {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final SwtPanel contentpanel;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final Map<String, ISwtTab> tabs = new HashMap<>();

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private final List<String> tabNames = new ArrayList<>();

    // -------------------------------------------------------------------------------------------------------------------------
    private int selectedTab = -1;

    // -------------------------------------------------------------------------------------------------------------------------
    private SwtLabel label;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTabFolder(ISwtWidget<? extends Group> pParent) {
        super(pParent, false);

        SwtPanel buttonpanel = new SwtPanel(this, false);
        buttonpanel.setBounds(0, 0, 0, 30);

        SwtButton btnLeft = new SwtButton(buttonpanel);
        btnLeft.setText("<");
        btnLeft.setBounds(8, 8, 24, 24);
        btnLeft.addLeftClickListener(() -> {
            flip(-1);
        });

        this.label = new SwtLabel(buttonpanel);
        this.label.setBounds(32, 8, -64, 24);
        this.label.setAlign(Align.center);

        SwtButton btnRight = new SwtButton(buttonpanel);
        btnRight.setText(">");
        btnRight.setBounds(-32, 8, 24, 24);
        btnRight.addLeftClickListener(() -> {
            flip(+1);
        });

        this.contentpanel = new SwtPanel(this, false);
        this.contentpanel.setBounds(0, 40, 0, -50);

        this.setDelegatedParent(this.contentpanel);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void flip(int i) {
        int newSelTab = this.selectedTab + i;
        if (newSelTab < 0) newSelTab = 0;
        if (newSelTab > this.tabNames.size() - 1) newSelTab = this.tabNames.size() - 1;
        this.selectedTab = newSelTab;
        String name = this.tabNames.get(newSelTab);
        this.label.setText(name);
        ISwtTab as = this.tabs.get(name);
        for (ISwtWidget<?> a : this.contentpanel.getChildren()) {
            a.setVisible(a == as);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    void setTabMapping(String pId, ISwtTab as) {
        int idx = this.tabNames.indexOf(pId);
        if (idx != -1) throw new RuntimeException("tab with id '" + pId + "' already exists"); //$NON-NLS-1$ //$NON-NLS-2$
        this.tabNames.add(pId);
        as.setVisible(false);
        this.tabs.put(pId, as);
        if (this.selectedTab == -1) {
            this.label.setText(pId);
            this.selectedTab = this.tabNames.indexOf(pId);
            for (ISwtWidget<?> a : this.contentpanel.getChildren()) {
                a.setVisible(a == as);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
