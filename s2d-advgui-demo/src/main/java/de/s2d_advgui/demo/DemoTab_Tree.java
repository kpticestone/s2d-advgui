package de.s2d_advgui.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.s2d_advgui.core.tabfolder.SwtTab;
import de.s2d_advgui.core.tabfolder.SwtTabFolder;
import de.s2d_advgui.core.tree.ProviderItem;
import de.s2d_advgui.core.tree.SwtTree;
import de.s2d_advgui.core.tree.SwtTreeContentProvider;

public class DemoTab_Tree extends SwtTab {
    // -------------------------------------------------------------------------------------------------------------------------
    private long lastDelta = System.currentTimeMillis();

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoTab_Tree(SwtTabFolder pParent) {
        super(pParent, "tree");
        ProviderItem itemA = new ProviderItem("A", "A", "icons/128/warning.png");
        ProviderItem itemB = new ProviderItem("B", "B", "icons/128/workplace.png");
        ProviderItem itemC = new ProviderItem("C", "C", "icons/128/yinyang.png");

        Map<ProviderItem, List<ProviderItem>> children = new HashMap<>();

        children.put(itemA, makeRandomList());
        children.put(itemB, makeRandomList());
        children.put(itemC, makeRandomList());

        SwtTreeContentProvider<ProviderItem> cp = new SwtTreeContentProvider<>() {
            @Override
            public String getLabel(ProviderItem item) {
                return item.label;
            }
            
            @Override
            public String getIcon(ProviderItem item) {
                return item.icon;
            }

            @Override
            public List<ProviderItem> getChildren(ProviderItem pItem) {
                return children.get(pItem);
            }
        };
        SwtTree<ProviderItem> someTree = new SwtTree<ProviderItem>(this, cp);
        someTree.setBounds(10, 10, -20, -20);
        someTree.setInput(itemA, itemC, itemB);
        this.addUpdateHandler((delta) -> {
            long cur = System.currentTimeMillis();
            float dif = cur - this.lastDelta;
            if (dif > 1000) {
                children.put(itemB, makeRandomList());
                this.lastDelta = cur;
                List<ProviderItem> was = children.get(itemC);
                ProviderItem j = was.remove(0);
                was.add(j);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public List<ProviderItem> makeRandomList() {
        List<ProviderItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String jj = UUID.randomUUID().toString();
            list.add(new ProviderItem(jj, jj, "icons/128/apple.png"));
        }
        return list;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
