package com.leo.spheres.core.chunksystem.calculator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leo.commons.geom.Point;
import com.leo.commons.utils.Brush;
import com.leo.spheres.core.chunksystem.AChunkSystem;
import com.leo.spheres.core.chunksystem.Chunk;
import com.leo.spheres.core.chunksystem.CircleBrush;
import com.leo.spheres.core.chunksystem.CoordConsumerShort;
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;
import com.leo.spheres.core.chunksystem.RayBlockHandler;
import de.s2d_advgui.commons.TOldCompatibilityCode;
import de.s2d_advgui.commons.TimeTracker;
import de.s2d_advgui.core.geom.Ray2D;
import de.s2d_advgui.core.geom.collider.IntersectionContext;
import de.s2d_advgui.core.geom.collider.impl_items.ColliderItem_Rectangle;
import de.s2d_advgui.core.geom.collider.ints.Intersector_CircleRect;
import de.s2d_advgui.core.rendering.SwtDrawer_Shapes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlanetLightMapCalculator {
    // -------------------------------------------------------------------------------------------------------------------------
    private PlanetLightMapCalculator() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static String calcLightsBySun(PlanetChunkSystem planetChunkSystem, float localDegreesToSun,
            boolean showDarkSide, boolean showDebugMetric, SwtDrawer_Shapes sdr) {

        TimeTracker tt = new TimeTracker();

        float size = planetChunkSystem.getPlanetSize() / 2f;
        float dist = size * 1.1f;
        CircleBrush cirlce = CircleBrush.getInstance(dist);

        tt.track("init"); //$NON-NLS-1$

        if (TOldCompatibilityCode.TRUE) {
            int cc = planetChunkSystem.getChunkCount();
            IntersectionContext pContext = new IntersectionContext(true, null);
            for (int lX = 0; lX < cc; lX++) {
                for (int lY = 0; lY < cc; lY++) {
                    Chunk ch = planetChunkSystem.getNChunk(lX, lY);
                    int rx = ch.getX() * AChunkSystem.CHUNKSIZE;
                    int ry = ch.getY() * AChunkSystem.CHUNKSIZE;
                    Rectangle re = new Rectangle(rx, ry, AChunkSystem.CHUNKSIZE, AChunkSystem.CHUNKSIZE);
                    ch.outofatmos = !Intersector_CircleRect.getInstance().calculateOverlapping(pContext,
                            cirlce.getOutline(), re);
                }
            }
            tt.track("outofatoms-calc"); //$NON-NLS-1$
        }

        if (TOldCompatibilityCode.TRUE) {
            int cc = planetChunkSystem.getChunkCount();
            for (int x = 0; x < cc; x++) {
                for (int y = 0; y < cc; y++) {
                    Chunk ch = planetChunkSystem.getNChunk(x, y);
                    if (!ch.outofatmos) {
                        planetChunkSystem.resetChunk(PlanetChunkSystem.CUSTOM_FIELD_LIGHTMAP, ch, (short) 255);
                        planetChunkSystem.resetChunk(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, ch, (short) 0);
                        planetChunkSystem.resetChunk(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, ch,
                                planetChunkSystem.getBlockHiddenId());
                    }
                }
            }
            tt.track("reset lightmap"); //$NON-NLS-1$
        }

        if (TOldCompatibilityCode.TRUE) {
            PlanetVisibleAndOutlineCalculator.calcVisibleOutline(planetChunkSystem);
            tt.track("outline calc"); //$NON-NLS-1$
        }

        if (TOldCompatibilityCode.FALSE) {
            int[] maxX = { 0 };
            CoordConsumerShort as = new CoordConsumerShort() {
                @Override
                public void accept(int x, int y, short i) {
                    if (i != 0) {
                        boolean foundFree = false;
                        AX: for (Point add : Brush.neighborPoints8) {
                            int ax = add.x + x;
                            int ay = add.y + y;
                            short v = planetChunkSystem.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, ax, ay);
                            if (v == 0) {
                                foundFree = true;
                                break AX;
                            }
                        }
                        if (foundFree) {
                            planetChunkSystem.set(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, x, y, (short) 1);
                            if (y == 0) {
                                maxX[0] = Math.max(maxX[0], x);
                            }
                        }
                    }
                }
            };
            planetChunkSystem.forEach(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, as);
            tt.track("search all outlines"); //$NON-NLS-1$
        }

        RayBlockHandler rbh = new RayBlockHandler(planetChunkSystem.getPlanetSize() * 2, localDegreesToSun);
        RayBlockHandler rbhShad = new RayBlockHandler(planetChunkSystem.getPlanetSize() * 2, 180 + localDegreesToSun);
        if (TOldCompatibilityCode.TRUE) {

            float[][] rdx = new float[5][2];
            planetChunkSystem.forEach(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, (x, y, lv) -> {
                if (lv > 1) {
                    float ixo = 0.001f;
                    rdx[0][0] = x + .5f;
                    rdx[0][1] = y + .5f;
                    rdx[1][0] = x + ixo;
                    rdx[1][1] = y + ixo;
                    rdx[2][0] = x + 1 - ixo;
                    rdx[2][1] = y + ixo;
                    rdx[3][0] = x + 1 - ixo;
                    rdx[3][1] = y + 1 - ixo;
                    rdx[4][0] = x + ixo;
                    rdx[4][1] = y + 1 - ixo;
                    int lightOn = 5;
                    AY: for (int i = 0; i < 5; i++) {
                        float[] a = rdx[i];
                        rbh.setSrc(a[0], a[1]);
                        if (TOldCompatibilityCode.FALSE) {
                            sdr.setColor(Color.YELLOW);
                            sdr.draw(rbh.getRay());
                        }
                        AX: for (int[] p : rbh) {
                            if (p[0] == x && p[1] == y) {
                                continue AX;
                            }
                            if (planetChunkSystem.contains(p[0], p[1])) {
                                if (planetChunkSystem.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, p[0], p[1]) > 0) {
                                    lightOn--;
                                    continue AY;
                                }
                            }
                        }
                    }
                    if (lightOn > 0) {
                        if (TOldCompatibilityCode.FALSE) {
                            sdr.setColor(Color.YELLOW);
                            sdr.draw(rbh.getRay());
                        }

                        planetChunkSystem.forceFastSet(PlanetChunkSystem.CUSTOM_FIELD_LIGHTMAP, x,
                                y,
                                (short) 128);

                        if (TOldCompatibilityCode.TRUE) {
                            for (int i = 0; i < 5; i++) {
                                float[] a = rdx[i];
                                rbhShad.setSrc(a[0], a[1]);

                                if (TOldCompatibilityCode.FALSE) {
                                    sdr.setColor(Color.MAGENTA);
                                    sdr.draw(rbhShad.getRay());
                                }

                                //                                if (TOldCompatibilityCode.FALSE) 
                                {
                                    AX: for (int[] p : rbhShad) {
                                        rbhShad.setSrc(x, y);
                                        if (p[0] == x && p[1] == y) {
                                            continue AX;
                                        }
                                        if (planetChunkSystem.contains(p[0], p[1])) {
                                            planetChunkSystem.forceFastSet(PlanetChunkSystem.CUSTOM_FIELD_LIGHTMAP,
                                                    p[0],
                                                    p[1],
                                                    (short) 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            tt.track("light rays"); //$NON-NLS-1$
        }

        if (TOldCompatibilityCode.TRUE) {
            Brush brush = new Brush(5);
            Set<int[]> brx = new HashSet<>();
            brush.forEach((x2, y2) -> {
                brx.add(new int[] { x2, y2 });
            });
            planetChunkSystem.forEach(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, (x, y, visible) -> {
                if (visible == 1) {
                    // brush.forEach((x2, y2) ->
                    for (int[] j : brx) {
                        // planetChunkSystem.set(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, x + x2, y + y2, 2);
                        planetChunkSystem.copy(x + j[0], y + j[1], PlanetChunkSystem.CUSTOM_FIELD_SOURCE,
                                PlanetChunkSystem.CUSTOM_FIELD_VISIBLE);
                        //                        short ka = planetChunkSystem.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, x + x2, y + y2);
                        //                        planetChunkSystem.forceFastSet(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, x + x2, y + y2, ka);
                    }
                    //);
                }
            });
            tt.track("outline brushing");
        }

        StringBuilder txt = new StringBuilder();
        tt.report(txt);
        txt.append("planet size: " + planetChunkSystem.getPlanetSize() + "\n");

        int[] counter = { 0 };
        planetChunkSystem.forEach(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, new CoordConsumerShort() {
            @Override
            public void accept(int x, int y, short i) {
                ++counter[0];
            }
        });

        txt.append("blocks: " + counter[0] + "\n");
        return txt.toString();
        //        tt.print("asdasd");
    }

    public static void calcChunkBasics(float sunDirection, SwtDrawer_Shapes sdr) {
        float maxChunkDiameter = (float) Math
                .sqrt(Math.pow(AChunkSystem.CHUNKSIZE, 2) * Math.pow(AChunkSystem.CHUNKSIZE, 2));
        Vector2 direction = new Vector2(maxChunkDiameter, 0); // look @ right are 0°
        direction.rotate(sunDirection); // bring vector in the correct direction of sun-rays
        // x, y -> jeder Block kann mehreren Rays angehören. Diese Zuordnung ist aber bei
        // allen Chunks identisch!
        // Iteriert man durch die Rays, so würde man wahrscheinlich immer Artefakt-Leer-Punkte riskieren.
        // Jeder Block definiert sich durch mindestens vier Eckpunkte und rein theoretisch durch viele
        // innenliegende Punkte.

        ColliderItem_Rectangle chunkRect = new ColliderItem_Rectangle(
                new Rectangle(-.1f, -.1f, AChunkSystem.CHUNKSIZE + .2f, AChunkSystem.CHUNKSIZE + .2f));

        Ray2D[] chunkLines = new Ray2D[4];
        chunkLines[0] = new Ray2D(0, 0, AChunkSystem.CHUNKSIZE, 0);
        chunkLines[1] = new Ray2D(AChunkSystem.CHUNKSIZE, 0, AChunkSystem.CHUNKSIZE, AChunkSystem.CHUNKSIZE);
        chunkLines[2] = new Ray2D(AChunkSystem.CHUNKSIZE, AChunkSystem.CHUNKSIZE, 0, AChunkSystem.CHUNKSIZE);
        chunkLines[3] = new Ray2D(0, AChunkSystem.CHUNKSIZE, 0, 0);

        sdr.setColor(Color.CYAN);
        for (Ray2D re : chunkLines) {
            sdr.draw(re);
        }

        Map<String, Integer> raysaa = new HashMap<>();
        int allRays = 0;
        Vector2[] blockPoints = new Vector2[5];
        Vector2 intersection = new Vector2();
        for (int i = 0; i < blockPoints.length; i++)
            blockPoints[i] = new Vector2();
        for (int x = 0; x < AChunkSystem.CHUNKSIZE; x++) {
            for (int y = 0; y < AChunkSystem.CHUNKSIZE; y++) {
                sdr.setColor(Color.GRAY);
                sdr.draw(new Rectangle(x, y, 1, 1));
                // if (x == 0 || y == 0 || x == AChunkSystem.CHUNKSIZE - 1 || y == AChunkSystem.CHUNKSIZE - 1)
                {
                    blockPoints[0].set(x, y);
                    blockPoints[1].set(x + 1, y);
                    blockPoints[2].set(x + 1, y + 1);
                    blockPoints[3].set(x, y + 1);
                    blockPoints[4].set(x + .5f, y + .5f);
                    for (Vector2 blockPointA : blockPoints)
                    // Vector2 blockPointA = blockPoints[4];
                    {
                        Vector2 blockPointB = new Vector2(direction).add(blockPointA);
                        for (Ray2D chunkLine : chunkLines) {
                            if (Intersector.intersectLines(
                                    blockPointA.x, blockPointA.y,
                                    blockPointB.x, blockPointB.y,
                                    chunkLine.x1, chunkLine.y1,
                                    chunkLine.x2, chunkLine.y2,
                                    intersection)) {
                            }
                            if (chunkRect.contains(intersection)) {
                                ++allRays;
                                sdr.setColor(Color.RED);
                                Ray2D someRay = new Ray2D(blockPointA, intersection);
                                String rayName = someRay.x1 + "_" + someRay.y1 + "_" + someRay.x2 + "_" + someRay.y2;
                                sdr.draw(someRay);
                                Integer was = raysaa.get(rayName);
                                if (was == null) was = 1;
                                else
                                    was = was + 1;
                                raysaa.put(rayName, was);
                            }
                        }
                    }
                }
            }
        }
        //        System.err.println("unique rays: " + raysaa.size()+ " (of " + allRays +")");

    }

    // -------------------------------------------------------------------------------------------------------------------------
}
