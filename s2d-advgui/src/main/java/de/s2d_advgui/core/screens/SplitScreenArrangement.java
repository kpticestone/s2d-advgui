package de.s2d_advgui.core.screens;

public class SplitScreenArrangement {
    // -------------------------------------------------------------------------------------------------------------------------
    public static final SplitScreenArrangement ZERO = new SplitScreenArrangement(1, 1);

    // -------------------------------------------------------------------------------------------------------------------------
    public final static int MAX = 17;

    // -------------------------------------------------------------------------------------------------------------------------
    final static SplitScreenArrangement[] INSTANCES = new SplitScreenArrangement[MAX];
    static {
        for (int i = 1; i < MAX; i++) {
            int as = (int) Math.ceil(Math.sqrt(i));
            INSTANCES[i] = new SplitScreenArrangement(as, (int) Math.ceil((float) (i) / as));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public final static SplitScreenArrangement get(int size) {
        return INSTANCES[size];
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private int mod;

    // -------------------------------------------------------------------------------------------------------------------------
    private int rows;

    // -------------------------------------------------------------------------------------------------------------------------
    public SplitScreenArrangement(int mod, int rows) {
        if (mod == 0) throw new RuntimeException();
        this.mod = mod;
        this.rows = rows;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public void calc(int nr, float width, float height, SplitScreenConsumer pConsumer) {
        float someWidth = width / this.mod;
        float someHeight = height / this.rows;
        int fx = Math.floorMod(nr, this.mod);
        int fy = Math.floorDiv(nr, this.mod);
        float x = someWidth * fx;
        float y = someHeight * fy;
        pConsumer.on(x, y, someWidth, someHeight);
    }

    // -------------------------------------------------------------------------------------------------------------------------
}