/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenMinable
extends WorldGenerator {
    private int minableBlockId;
    private int numberOfBlocks;

    public WorldGenMinable(int i1, int i2) {
        this.minableBlockId = i1;
        this.numberOfBlocks = i2;
    }

    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        float f6 = random2.nextFloat() * (float)Math.PI;
        double d7 = (float)(i3 + 8) + MathHelper.sin(f6) * (float)this.numberOfBlocks / 8.0f;
        double d9 = (float)(i3 + 8) - MathHelper.sin(f6) * (float)this.numberOfBlocks / 8.0f;
        double d11 = (float)(i5 + 8) + MathHelper.cos(f6) * (float)this.numberOfBlocks / 8.0f;
        double d13 = (float)(i5 + 8) - MathHelper.cos(f6) * (float)this.numberOfBlocks / 8.0f;
        double d15 = i4 + random2.nextInt(3) + 2;
        double d17 = i4 + random2.nextInt(3) + 2;
        int i19 = 0;
        while (i19 <= this.numberOfBlocks) {
            double d20 = d7 + (d9 - d7) * (double)i19 / (double)this.numberOfBlocks;
            double d22 = d15 + (d17 - d15) * (double)i19 / (double)this.numberOfBlocks;
            double d24 = d11 + (d13 - d11) * (double)i19 / (double)this.numberOfBlocks;
            double d26 = random2.nextDouble() * (double)this.numberOfBlocks / 16.0;
            double d28 = (double)(MathHelper.sin((float)i19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * d26 + 1.0;
            double d30 = (double)(MathHelper.sin((float)i19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0f) * d26 + 1.0;
            int i32 = (int)(d20 - d28 / 2.0);
            int i33 = (int)(d22 - d30 / 2.0);
            int i34 = (int)(d24 - d28 / 2.0);
            int i35 = (int)(d20 + d28 / 2.0);
            int i36 = (int)(d22 + d30 / 2.0);
            int i37 = (int)(d24 + d28 / 2.0);
            int i38 = i32;
            while (i38 <= i35) {
                double d39 = ((double)i38 + 0.5 - d20) / (d28 / 2.0);
                if (d39 * d39 < 1.0) {
                    int i41 = i33;
                    while (i41 <= i36) {
                        double d42 = ((double)i41 + 0.5 - d22) / (d30 / 2.0);
                        if (d39 * d39 + d42 * d42 < 1.0) {
                            int i44 = i34;
                            while (i44 <= i37) {
                                double d45 = ((double)i44 + 0.5 - d24) / (d28 / 2.0);
                                if (d39 * d39 + d42 * d42 + d45 * d45 < 1.0 && world1.getBlockId(i38, i41, i44) == Block.stone.blockID) {
                                    world1.setBlock(i38, i41, i44, this.minableBlockId);
                                }
                                ++i44;
                            }
                        }
                        ++i41;
                    }
                }
                ++i38;
            }
            ++i19;
        }
        return true;
    }
}

