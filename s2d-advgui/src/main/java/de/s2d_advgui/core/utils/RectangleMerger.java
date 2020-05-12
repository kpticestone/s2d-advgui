package de.s2d_advgui.core.utils;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RectangleMerger {
    public static List<Rectangle> merge(int size, CoordExistProvider existsProvider) {
        boolean[][] merged = new boolean[size][size];
        CoordExistProvider notMergedAndExists = (x, y) -> !merged[x][y] && existsProvider.exists(x, y);

        List<Rectangle> rects = new ArrayList<>();
        for (int ix = 0; ix < size; ix++) {
            for (int iy = 0; iy < size; iy++) {
                Rectangle r = searchRectangle(notMergedAndExists, merged, ix, iy, size);
                if (r != null) {
                    rects.add(r);
                }
            }
        }
        return rects;
    }

    private static Rectangle searchRectangle(CoordExistProvider coords, boolean[][] merged, int sx, int sy, int size) {
        if (!coords.exists(sx, sy)) {
            return null;
        }

        int width = 1;
        int height = 1;
        boolean continueY = true;
        boolean continueX = true;
        while ((continueY || continueX) && sx + width < size && sy + height < size) {
            if (continueY) {
                for (int cx = sx; cx <= sx + width; cx++) {
                    if (!coords.exists(cx, sy + height)) {
                        continueY = false;
                        break;
                    }
                }
            }
            if (continueX) {
                for (int cy = sy; cy < sy + height; cy++) {
                    if (!coords.exists(sx + width, cy)) {
                        continueX = false;
                        break;
                    }
                }
            }
            if (continueX) width++;
            if (continueY) height++;
        }

        for (int cx = 0; cx < width; cx++) {
            for (int cy = 0; cy < height; cy++) {
                merged[sx + cx][sy + cy] = true;
            }
        }
        return new Rectangle(sx, sy, width, height);
    }

    @FunctionalInterface
    public interface CoordExistProvider {
        boolean exists(int x, int y);
    }
}
