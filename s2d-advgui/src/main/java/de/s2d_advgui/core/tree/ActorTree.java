package de.s2d_advgui.core.tree;

import javax.annotation.Nonnull;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;

import de.s2d_advgui.core.rendering.IRend123;

public class ActorTree<PR> extends Table {
    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    public final Tree tree;

    // -------------------------------------------------------------------------------------------------------------------------
    @Nonnull
    private IRend123 rend;

    // -------------------------------------------------------------------------------------------------------------------------
    public ActorTree(@Nonnull IRend123 pRend) {
        this.rend = pRend;
        Skin useSkin = pRend.getResourceManager().getSkin();
        this.setSkin(useSkin);

        this.tree = new Tree(useSkin) {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
            }
        };
        this.add(new ScrollPane(tree, useSkin)).expand().fill();
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.rend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
