package com.s2dwt.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leo.commons.utils.UUID;
import com.s2dwt.core.tabfolder.SwtTab;
import com.s2dwt.core.tabfolder.SwtTabFolder;
import com.s2dwt.core.tree.ProviderItem;
import com.s2dwt.core.tree.SwtTree;
import com.s2dwt.core.tree.SwtTreeContentProvider;

public class DemoTab_Tree extends SwtTab {
    // -------------------------------------------------------------------------------------------------------------------------
    private long lastDelta = System.currentTimeMillis();

    // -------------------------------------------------------------------------------------------------------------------------
    public DemoTab_Tree(SwtTabFolder pParent) {
        super(pParent, "tree");
        ProviderItem itemA = new ProviderItem("A", "A", "ui/1.jpg");
        ProviderItem itemB = new ProviderItem("B", "B", "ui/icon.png");
        ProviderItem itemC = new ProviderItem("C", "C", "ui/slotType_minor.png");

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
            String jj = UUID.random().toString();
            list.add(new ProviderItem(jj, jj, "ui/icon.png"));
        }
        return list;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
