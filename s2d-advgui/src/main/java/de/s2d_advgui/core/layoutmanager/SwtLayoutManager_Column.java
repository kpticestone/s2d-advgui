package de.s2d_advgui.core.layoutmanager;

import de.s2d_advgui.core.awidget.ISwtWidget;

import java.util.function.Consumer;

public final class SwtLayoutManager_Column extends ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    int startx = 0;
    int ah = 24;
    int space = 5;
    Consumer<Integer> consumer;
    int padding = 10;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Column() {
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Column(Consumer<Integer> pConsumer) {
        this.consumer = pConsumer;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setStartx(int startx) {
        this.startx = startx;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Column setSpace(int space) {
        this.space = space;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Column setAh(int ah) {
        this.ah = ah;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Column setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        int ax = this.startx;
        for (ISwtWidget<?> w : pWidget.getChildren()) {
            float wash = this.ah == -1 ? w.getWidth() : this.ah;
            w.setBounds(ax, this.padding, wash, -this.padding * 2);
            ax += wash + this.space;
        }
        ax += this.startx;
        if (this.consumer != null) {
            this.consumer.accept(ax);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}