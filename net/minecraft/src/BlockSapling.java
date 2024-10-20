/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenBigTree;
import net.minecraft.src.WorldGenTrees;
import net.minecraft.src.WorldGenerator;

public class BlockSapling
extends BlockFlower {
    protected BlockSapling(int i1, int i2) {
        super(i1, i2);
        float f3 = 0.4f;
        this.setBlockBounds(0.5f - f3, 0.0f, 0.5f - f3, 0.5f + f3, f3 * 2.0f, 0.5f + f3);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        super.updateTick(world1, i2, i3, i4, random5);
        if (world1.getBlockLightValue(i2, i3 + 1, i4) >= 9 && random5.nextInt(5) == 0) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            if (i6 < 15) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, i6 + 1);
            } else {
                this.growTree(world1, i2, i3, i4, random5);
            }
        }
    }

    public void growTree(World world1, int i2, int i3, int i4, Random random5) {
        world1.setBlock(i2, i3, i4, 0);
        WorldGenerator object6 = new WorldGenTrees();
        if (random5.nextInt(10) == 0) {
            object6 = new WorldGenBigTree();
        }
        if (!((WorldGenerator)object6).generate(world1, random5, i2, i3, i4)) {
            world1.setBlock(i2, i3, i4, this.blockID);
        }
    }
}

