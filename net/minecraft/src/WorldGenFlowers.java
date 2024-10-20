/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenFlowers
extends WorldGenerator {
    private int plantBlockId;

    public WorldGenFlowers(int i1) {
        this.plantBlockId = i1;
    }

    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        int i6 = 0;
        while (i6 < 64) {
            int i9;
            int i8;
            int i7 = i3 + random2.nextInt(8) - random2.nextInt(8);
            if (world1.isAirBlock(i7, i8 = i4 + random2.nextInt(4) - random2.nextInt(4), i9 = i5 + random2.nextInt(8) - random2.nextInt(8)) && ((BlockFlower)Block.blocksList[this.plantBlockId]).canBlockStay(world1, i7, i8, i9)) {
                world1.setBlock(i7, i8, i9, this.plantBlockId);
            }
            ++i6;
        }
        return true;
    }
}

