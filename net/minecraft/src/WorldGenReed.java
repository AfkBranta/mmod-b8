/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenReed
extends WorldGenerator {
    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        int i6 = 0;
        while (i6 < 20) {
            int i7 = i3 + random2.nextInt(4) - random2.nextInt(4);
            int i8 = i4;
            int i9 = i5 + random2.nextInt(4) - random2.nextInt(4);
            if (world1.isAirBlock(i7, i4, i9) && (world1.getBlockMaterial(i7 - 1, i4 - 1, i9) == Material.water || world1.getBlockMaterial(i7 + 1, i4 - 1, i9) == Material.water || world1.getBlockMaterial(i7, i4 - 1, i9 - 1) == Material.water || world1.getBlockMaterial(i7, i4 - 1, i9 + 1) == Material.water)) {
                int i10 = 2 + random2.nextInt(random2.nextInt(3) + 1);
                int i11 = 0;
                while (i11 < i10) {
                    if (Block.reed.canBlockStay(world1, i7, i8 + i11, i9)) {
                        world1.setBlock(i7, i8 + i11, i9, Block.reed.blockID);
                    }
                    ++i11;
                }
            }
            ++i6;
        }
        return true;
    }
}

