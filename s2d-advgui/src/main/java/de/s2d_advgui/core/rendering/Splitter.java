package de.s2d_advgui.core.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Splitter {
    // -------------------------------------------------------------------------------------------------------------------------
    public final TextureRegion trTL;
    public final TextureRegion trT;
    public final TextureRegion trCrossTop;
    public final TextureRegion trTR;

    public final TextureRegion trCL;
    public final TextureRegion trC;
    public final TextureRegion trCrossVer;
    public final TextureRegion trCR;

    public final TextureRegion trCrossLeft;
    public final TextureRegion trCrossHor;
    public final TextureRegion trCrossCenter;
    public final TextureRegion trCrossRight;

    public final TextureRegion trBL;
    public final TextureRegion trB;
    public final TextureRegion trCrossBotton;
    public final TextureRegion trBR;

    // -------------------------------------------------------------------------------------------------------------------------
    public final int o1;
    public final int o2;
    public final int o3;

    // -------------------------------------------------------------------------------------------------------------------------
    private final Texture tx;

    // -------------------------------------------------------------------------------------------------------------------------
    public Splitter(Texture tx) {
        this.tx = tx;
        this.o1 = tx.getWidth() / 4;
        this.o2 = this.o1 * 2;
        this.o3 = this.o1 * 3;

        this.trTL = load(0, 0);
        this.trT = load(1, 0);
        this.trCrossTop = load(2, 0);
        this.trTR = load(3, 0);

        this.trCL = load(0, 1);
        this.trC = load(1, 1);
        this.trCrossVer = load(2, 1);
        this.trCR = load(3, 1);

        this.trCrossLeft = load(0, 2);
        this.trCrossHor = load(1, 2);
        this.trCrossCenter = load(2, 2);
        this.trCrossRight = load(3, 2);

        this.trBL = load(0, 3);
        this.trB = load(1, 3);
        this.trCrossBotton = load(2, 3);
        this.trBR = load(3, 3);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private TextureRegion load(int i, int j) {
        return new TextureRegion(this.tx, i * this.o1, j * this.o1, this.o1, this.o1);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}