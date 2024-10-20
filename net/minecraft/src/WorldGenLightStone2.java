/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenLightStone2
extends WorldGenerator {
    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        if (!world1.isAirBlock(i3, i4, i5)) {
            return false;
        }
        if (world1.getBlockId(i3, i4 + 1, i5) != Block.bloodStone.blockID) {
            return false;
        }
        world1.setBlockWithNotify(i3, i4, i5, Block.lightStone.blockID);
        int i6 = 0;
        while (i6 < 1500) {
            int i9;
            int i8;
            int i7 = i3 + random2.nextInt(8) - random2.nextInt(8);
            if (world1.getBlockId(i7, i8 = i4 - random2.nextInt(12), i9 = i5 + random2.nextInt(8) - random2.nextInt(8)) == 0) {
                int i10 = 0;
                int i11 = 0;
                while (i11 < 6) {
                    int i12 = 0;
                    if (i11 == 0) {
                        i12 = world1.getBlockId(i7 - 1, i8, i9);
                    }
                    if (i11 == 1) {
                        i12 = world1.getBlockId(i7 + 1, i8, i9);
                    }
                    if (i11 == 2) {
                        i12 = world1.getBlockId(i7, i8 - 1, i9);
                    }
                    if (i11 == 3) {
                        i12 = world1.getBlockId(i7, i8 + 1, i9);
                    }
                    if (i11 == 4) {
                        i12 = world1.getBlockId(i7, i8, i9 - 1);
                    }
                    if (i11 == 5) {
                        i12 = world1.getBlockId(i7, i8, i9 + 1);
                    }
                    if (i12 == Block.lightStone.blockID) {
                        ++i10;
                    }
                    ++i11;
                }
                if (i10 == 1) {
                    world1.setBlockWithNotify(i7, i8, i9, Block.lightStone.blockID);
                }
            }
            ++i6;
        }
        return true;
    }
}

