package de.s2d_advgui.core.basicwidgets;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import de.s2d_advgui.core.rendering.IRend123;

import java.util.Objects;
import java.util.function.Function;

class ExSelectBox<T> extends SelectBox<T> {
    private final IRend123 pRend;

    private Function<T, String> toStringProvider = Objects::toString;

    public ExSelectBox(SelectBoxStyle style, IRend123 pRend) {
        super(style);
        this.pRend = pRend;
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        pRend.doRender(batch, parentAlpha, () -> super.draw(batch, parentAlpha));
    }

    @Override
    public String toString(T item) {
        return toStringProvider.apply(item);
    }

    public void setToStringProvider(Function<T, String> toStringProvider) {
        this.toStringProvider = toStringProvider;
    }
}
