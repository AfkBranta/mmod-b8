/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockGrass
extends Block {
    protected BlockGrass(int i1) {
        super(i1, Material.ground);
        this.blockIndexInTexture = 3;
        this.setTickOnLoad(true);
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (i5 == 1) {
            return 0;
        }
        if (i5 == 0) {
            return 2;
        }
        Material material6 = iBlockAccess1.getBlockMaterial(i2, i3 + 1, i4);
        return material6 != Material.snow && material6 != Material.builtSnow ? 3 : 68;
    }

    @Override
    public int colorMultiplier(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        iBlockAccess1.getWorldChunkManager().func_4069_a(i2, i4, 1, 1);
        double d5 = iBlockAccess1.getWorldChunkManager().temperature[0];
        double d7 = iBlockAccess1.getWorldChunkManager().humidity[0];
        return ColorizerGrass.getGrassColor(d5, d7);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (!world1.multiplayerWorld) {
            int i8;
            int i7;
            int i6;
            if (world1.getBlockLightValue(i2, i3 + 1, i4) < 4 && world1.getBlockMaterial(i2, i3 + 1, i4).getCanBlockGrass()) {
                if (random5.nextInt(4) != 0) {
                    return;
                }
                world1.setBlockWithNotify(i2, i3, i4, Block.dirt.blockID);
            } else if (world1.getBlockLightValue(i2, i3 + 1, i4) >= 9 && world1.getBlockId(i6 = i2 + random5.nextInt(3) - 1, i7 = i3 + random5.nextInt(5) - 3, i8 = i4 + random5.nextInt(3) - 1) == Block.dirt.blockID && world1.getBlockLightValue(i6, i7 + 1, i8) >= 4 && !world1.getBlockMaterial(i6, i7 + 1, i8).getCanBlockGrass()) {
                world1.setBlockWithNotify(i6, i7, i8, Block.grass.blockID);
            }
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.dirt.idDropped(0, random2);
    }
}

