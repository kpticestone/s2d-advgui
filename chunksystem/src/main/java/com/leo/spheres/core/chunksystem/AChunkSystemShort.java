package com.leo.spheres.core.chunksystem;

import com.leo.commons.geom.Point;
import com.leo.commons.utils.Brush;

public abstract class AChunkSystemShort extends AChunkSystem {
    // -------------------------------------------------------------------------------------------------------------------------
    short[] allData;

    // -------------------------------------------------------------------------------------------------------------------------
    public AChunkSystemShort(AChunkSystem other) {
        this(other.planetSize);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public AChunkSystemShort(int pPlanetSize) {
        super(pPlanetSize);
        this.allData = new short[this.planetSize * this.planetSize];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.planetSize + ";" + this.allData.length + ";" + this.rev + "]";
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public short[] save() {
        return this.allData;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void load(short[] x) {
        this.allData = x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private final int tr(int x, int y) {
        return y * this.planetSize + x;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean doReset(int x, int y) {
        int o = tr(x, y);
        if (this.allData[o] != 0) {
            this.allData[o] = 0;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doSet(int pTranslatedX, int pTranslatedY, short pValue) {
        int o = tr(pTranslatedX, pTranslatedY);
        if (this.allData[o] != pValue) {
            this.allData[o] = pValue;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected short doGet(int translatedX, int translatedY) {
        return this.allData[tr(translatedX, translatedY)];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doUpgrade(int pTranslatedX, int pTranslatedY, short pValue) {
        int o = tr(pTranslatedX, pTranslatedY);
        if (this.allData[o] < pValue) {
            this.allData[o] = pValue;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void forEach(CoordConsumerShort pCallback) {
        for (int x = 0; x < this.planetSize; x++)
            for (int y = 0; y < this.planetSize; y++) {
                pCallback.accept(x - this.half, y - this.half, this.allData[tr(x, y)]);
            }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean set(int pAbsoluteX, int pAbsoluteY, int pValue) {
        return this.set(pAbsoluteX, pAbsoluteY, (short) pValue);
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * @param pAbsoluteX
     * @param pAbsoluteY
     * @param pValue
     * @return
     */
    public final boolean set(int pAbsoluteX, int pAbsoluteY, short pValue) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        if (this.doSet(translatedX, translatedY, pValue)) {
            if (!this.inImport) {
                this.getChunk(translatedX, translatedY).rev = this.rev++;
            }
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean upgrade(int pAbsoluteX, int pAbsoluteY, short pValue) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        if (this.doUpgrade(translatedX, translatedY, pValue)) {
            if (!this.inImport) {
                this.getChunk(translatedX, translatedY).rev = this.rev++;
            }
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final short get(int pAbsoluteX, int pAbsoluteY) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return 0;
        return this.doGet(translatedX, translatedY);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void neighbors4(int onex, int oney, CoordConsumerShort pCallback) {
        int ax, ay;
        for (Point add : Brush.neighborPoints4) {
            ax = add.x + onex;
            ay = add.y + oney;
            pCallback.accept(ax, ay, get(ax, ay));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void neighbors8(int onex, int oney, CoordConsumerShort pCallback) {
        int ax, ay;
        for (Point add : Brush.neighborPoints8) {
            ax = add.x + onex;
            ay = add.y + oney;
            pCallback.accept(ax, ay, get(ax, ay));
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------
}
