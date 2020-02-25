package com.s2dwt.core.layoutmanager;

import java.util.function.Consumer;

import com.s2dwt.core.awidget.ISwtWidget;

public final class SwtLayoutManager_Row extends ASwtLayoutManager {
    // -------------------------------------------------------------------------------------------------------------------------
    int starty = 0;
    int ah = 24;
    int space = 5;
    Consumer<Integer> consumer;
    int padding = 10;

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Row() {
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public SwtLayoutManager_Row(Consumer<Integer> pConsumer) {
        this.consumer = pConsumer;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setStarty(int starty) {
        this.starty = starty;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setSpace(int space) {
        this.space = space;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setAh(int ah) {
        this.ah = ah;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void setPadding(int padding) {
        this.padding = padding;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public void calculate(ISwtWidget<?> pWidget, float width, float height) {
        int ay = this.starty;
        for (ISwtWidget<?> a : pWidget.getChildren()) {
            float wash = this.ah == -1 ? a.getHeight() : this.ah;
            a.setBounds(this.padding, ay, -this.padding * 2, wash);
            ay += wash + this.space;
        }
        ay += this.starty;
        if (this.consumer != null) {
            this.consumer.accept(ay);
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}