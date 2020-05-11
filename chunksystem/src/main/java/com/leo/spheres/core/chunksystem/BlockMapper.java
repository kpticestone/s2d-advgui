package com.leo.spheres.core.chunksystem;


/**
 * short: 0xffff
 * ff ff
 * 1111 1111 1111 1111
 * \--- 0-15 (16)
 * \-------- 0-255 (256)
 * \------------- 0-4095 (4096)
 * <p>
 * \-------- 0-1023 (1024)
 * \-----/             0-63 (64)
 * <p>
 * <p>
 * block-types based on enums with numeric ids (short)
 * split the short into two parts
 * the higher one can store 64 different values and the
 * lower one 1024. the lower one is for the block-types,
 * the higher one for custom meta informations
 *
 * @author dhartmann
 */
public final class BlockMapper extends AChunkSystemShort {
    // -------------------------------------------------------------------------------------------------------------------------
    public BlockMapper(int pPlanetSize) {
        super(pPlanetSize);
    }

    // -------------------------------------------------------------------------------------------------------------------------    
    public static void main(String[] args) {
        System.err.println("split");
        short f = 0;
        f |= (short) (63 << 8);
        f |= (short) (1023);
//        f = Short.MAX_VALUE;
        System.err.println(Integer.toBinaryString(f));
        System.err.println(f);
        System.err.println();
    }

    // -------------------------------------------------------------------------------------------------------------------------    
}
