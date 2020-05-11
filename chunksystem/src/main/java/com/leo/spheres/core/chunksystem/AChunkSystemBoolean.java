package com.leo.spheres.core.chunksystem;

public abstract class AChunkSystemBoolean extends AChunkSystem {
    // -------------------------------------------------------------------------------------------------------------------------
    final boolean[][] alldata;

    // -------------------------------------------------------------------------------------------------------------------------
    public AChunkSystemBoolean(AChunkSystem other) {
        this(other.planetSize);
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public AChunkSystemBoolean(int pPlanetSize) {
        super(pPlanetSize);
        this.alldata = new boolean[this.planetSize][this.planetSize];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    @Override
    protected boolean doReset(int x, int y) {
        if (this.alldata[x][y]) {
            this.alldata[x][y] = false;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doSet(int pTranslatedX, int pTranslatedY, boolean pValue) {
        boolean was = this.alldata[pTranslatedX][pTranslatedY];
        if (was != pValue) {
            this.alldata[pTranslatedX][pTranslatedY] = pValue;
            return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    protected boolean doGet(int translatedX, int translatedY) {
        return this.alldata[translatedX][translatedY];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final boolean get(int pAbsoluteX, int pAbsoluteY) {
        int translatedX = pAbsoluteX + this.half;
        int translatedY = pAbsoluteY + this.half;
        if (translatedX < 0 || translatedY < 0 || translatedX >= this.planetSize || translatedY >= this.planetSize)
            return false;
        return this.doGet(translatedX, translatedY);
    }

    // -------------------------------------------------------------------------------------------------------------------------

    /**
     * @param pAbsoluteX
     * @param pAbsoluteY
     * @param pValue
     * @return
     */
    public final boolean set(int pAbsoluteX, int pAbsoluteY, boolean pValue) {
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
    public void forEach(CoordConsumerBoolean pCallback) {
        for (int x = 0; x < this.planetSize; x++)
            for (int y = 0; y < this.planetSize; y++) {
                pCallback.accept(x - this.half, y - this.half, this.alldata[x][y]);
            }
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
