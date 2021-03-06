package de.s2d_advgui.core.tree;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import de.s2d_advgui.core.awidget.ASwtWidget;
import de.s2d_advgui.core.awidget.ISwtWidget;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SwtTree<PR> extends ASwtWidget<WidgetGroup> {
    // -------------------------------------------------------------------------------------------------------------------------
    private final SwtTreeContentProvider<PR> provider;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<PR> rootItems = new LinkedHashSet<PR>();

    // -------------------------------------------------------------------------------------------------------------------------
    private Tree tree;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Set<Consumer<Collection<PR>>> selectionListener = new LinkedHashSet<>();

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtTree(ISwtWidget<? extends Group> pParent, SwtTreeContentProvider<PR> pProvider) {
        super(pParent, true);
        this.provider = pProvider;
        this.addUpdateHandler((delta) -> {
            doUpdate();
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected WidgetGroup createActor() {
        Skin skin = this.context.getResourceManager().getSkin();
        this.tree = new Tree(skin) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        this.tree.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeEvent) {
                    callSelectionListeners();
                }
                return false;
            }
        });
        Table table = new Table(skin) {
            public void draw(Batch batch, float parentAlpha) {
                _internalDrawWidget(this, batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }

            ;
        };
        table.add(new ScrollPane(tree, skin)).expand().fill();
        return table;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setInput(PR... pRootItems) {
        this.rootItems.clear();
        for (PR a : pRootItems) {
            this.rootItems.add(a);
        }
        this.doUpdate();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void clearInput() {
        this.rootItems.clear();
        this.doUpdate();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private void doUpdate() {
        this.updateTree(this.tree);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final void updateTree(Tree no0) {
        this.updateNN(no0::getNodes, this.rootItems, no0::remove, no0::add);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final void updateNode(Node no0) {
        boolean ex = no0.isExpanded();
        List<PR> tobeChildren = this.provider.getChildren((PR) no0.getValue());
        this.updateNN(no0::getChildren, tobeChildren, no0::remove, no0::add);
        no0.setExpanded(ex);

        Array<Node> jj = no0.getChildren();
        jj.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                int i1 = tobeChildren.indexOf((PR) o1.getValue());
                int i2 = tobeChildren.indexOf((PR) o2.getValue());
                return Integer.compare(i1, i2);
            }
        });
        no0.updateChildren();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final void updateNN(Supplier<Array<Node>> existingChildren, Collection<PR> tobeChildren, Consumer<Node> removeItem, Consumer<Node> addItem) {
        Map<PR, Node> myExistingItems = new HashMap<>();
        if (existingChildren != null) {
            for (Node xx : existingChildren.get()) {
                myExistingItems.put((PR) xx.getValue(), xx);
            }
        }
        Set<PR> addList = new LinkedHashSet<>();
        if (tobeChildren != null) {
            for (PR jj : tobeChildren) {
                if (myExistingItems.remove(jj) == null) {
                    addList.add(jj);
                }
            }
        }
        for (Node jja : myExistingItems.values()) {
            removeItem.accept(jja);
        }
        for (PR na : addList) {
//          WidgetGroup grp = new WidgetGroup();
            LabelStyle style = new LabelStyle();
            style.font = this.context.getResourceManager().getFont(.5f, true);
            Label lab = new Label(this.provider.getLabel(na), style);
            lab.setAlignment(Align.center);
            lab.setHeight(16);
            //grp.addActor(lab);
            String resourceId = this.provider.getIcon(na);
            Node node = new Node<>(lab) {

            };
            if (resourceId != null) {
                Drawable drx = this.context.getDrawable(resourceId);
                drx.setMinHeight(16);
                drx.setMinWidth(16);
                node.setIcon(drx);
            }
            node.setValue(na);
            addItem.accept(node);
        }
        for (Node xx : existingChildren.get()) {
            updateNode(xx);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void addSelectionListener(Consumer<Collection<PR>> selectionListener) {
        this.selectionListener.add(selectionListener);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected void callSelectionListeners() {
        Set<PR> use = new LinkedHashSet<>();
        Selection<Node> sels = this.tree.getSelection();
        for (Node ns : sels) {
            PR pr = (PR) ns.getValue();
            use.add(pr);
        }
        for (Consumer<Collection<PR>> a : this.selectionListener) {
            a.accept(use);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
