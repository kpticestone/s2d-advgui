package com.leo.spheres.core.chunksystem;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.leo.commons.geom.PerlinNoiseGenerator;

public class PlanetGenerator {
    // -------------------------------------------------------------------------------------------------------------------------
    public static final int BEDROCK = 1;
    public static final int STONE = 2;
    public static final int DIRT = 3;
    public static final int COAL_ORE = 4;
    public static final int GOLD_ORE = 5;

    // -------------------------------------------------------------------------------------------------------------------------
    public static PlanetChunkSystem doGenerate(int blockRadius) {
        PerlinNoiseGenerator environment = new PerlinNoiseGenerator(new Random().nextLong());
        float outlineRadius = blockRadius+15;
        PlanetChunkSystem blockMapper = new PlanetChunkSystem(AChunkSystem.calcBlockMapperSize(outlineRadius));
        for (int lX = -blockRadius; lX <= blockRadius; lX++) {
            for (int lY = -blockRadius; lY <= blockRadius; lY++) {
                int bt = getBlockType(environment, lX, lY, blockRadius);
                if (bt > 0) {
                    blockMapper.set(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, lX, lY, bt);
                }
            }
        }
        return blockMapper;
    }

    // -------------------------------------------------------------------------------------------------------------------------
    private static int getBlockType(PerlinNoiseGenerator environment, int pX, int pY, int blockRadius) {
        if (pX == 0 && pY == 0) {
            return BEDROCK;
        }
        float angle = MathUtils.atan2(pY, pX) * 10f;
        float edge = 1f + (environment.noise1(angle) / 10f);
        float distance = (float) Math.sqrt(Math.pow(pX, 2) + Math.pow(pY, 2)) / blockRadius;
        if (distance < edge) {
            float groundNoise = (environment.noise2(pX / 50f, pY / 50f) * .5f + .5f) / 2f;
            float groundType = distance / 2 + groundNoise;
            if (groundType <= 0.3) return BEDROCK;
            int noiseGold = (int) Math.floor((environment.noise2(pX / 3f, pY / 3f) * .5f + .5f) * 50);
            int noiseCoal = (int) Math.floor((environment.noise2(pX / 5f, pY / 5f) * .5f + .5f) * 50);
            int type = (int) Math.floor((environment.noise2(pX / 10f, pY / 10f) * .5f + .5f) * 100);
            if (type > 55) return 0;
            if (noiseGold <= 15) return GOLD_ORE;
            if (noiseCoal >= 15 && noiseCoal <= 16) return COAL_ORE;
            if (type < 30) return STONE;
            if (type > 50) return DIRT;
            if (groundType > 0.6) return DIRT;
            if (groundType > 0.3) return STONE;
        }
        return 0;
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
