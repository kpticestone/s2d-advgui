package com.leo.spheres.core.chunksystem;

import javax.annotation.Nonnull;

import com.leo.commons.utils.Brush;

public class OutlineCalculator {
    // -------------------------------------------------------------------------------------------------------------------------
    private static final Brush brush = new Brush(4);

    // -------------------------------------------------------------------------------------------------------------------------
    private OutlineCalculator() {
        // DON
    }

    // -------------------------------------------------------------------------------------------------------------------------
    public static void calcVisibleOutline(PlanetChunkSystem mapper) {
        if (mapper == null) throw new IllegalStateException("can't generate visibleOutline");
        mapper.doReset(PlanetChunkSystem.CUSTOM_FIELD_OUTLINE);
        try {
            mapper.floodOuttaSpace(mapper.half - 1, mapper.half - 1);
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
            short sv = visible == 1 ? isType : blockMapper.getBlockHiddenId();
            if (blockMapper.set(PlanetChunkSystem.CUSTOM_FIELD_VISIBLE, x, y, sv)) {
                pConsumer.accept(x, y, sv);
            }
        });
    }

    // -------------------------------------------------------------------------------------------------------------------------
}
