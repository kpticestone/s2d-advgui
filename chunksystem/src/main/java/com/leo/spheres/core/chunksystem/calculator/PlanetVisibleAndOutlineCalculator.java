package com.leo.spheres.core.chunksystem.calculator;

import com.leo.commons.utils.Brush;
import com.leo.spheres.core.chunksystem.CoordConsumerShort;
import com.leo.spheres.core.chunksystem.PlanetChunkSystem;

import javax.annotation.Nonnull;

public class PlanetVisibleAndOutlineCalculator {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Brush brush = new Brush(4);

    // -------------------------------------------------------------------------------------------------------------------------
    private PlanetVisibleAndOutlineCalculator() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void calcVisibleOutline(PlanetChunkSystem mapper) {
        if (mapper == null) throw new IllegalStateException("can't generate visibleOutline");
        mapper.doReset(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE);
        try {
            mapper.floodOuttaSpace(mapper.getHalf() - 1, mapper.getHalf() - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("error!!!!");
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void generateVisibleBlockMapper(@Nonnull PlanetChunkSystem blockMapper,
            @Nonnull CoordConsumerShort pConsumer) {
        calcVisibleOutline(blockMapper);
        blockMapper.forEach(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE, (x, y, visible) -> {
            short isType = blockMapper.get(PlanetChunkSystem.CUSTOM_FIELD_SOURCE, x, y);
            short sv = visible > 0 ? isType : blockMapper.getBlockHiddenId();
            if (blockMapper.set(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, x, y, sv)) {
                pConsumer.accept(x, y, sv);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
