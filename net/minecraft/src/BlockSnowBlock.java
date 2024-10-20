/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSnowBlock
extends Block {
    protected BlockSnowBlock(int i1, int i2) {
        super(i1, i2, Material.builtSnow);
        this.setTickOnLoad(true);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.snowball.shiftedIndex;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 4;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (world1.getSavedLightValue(EnumSkyBlock.Block, i2, i3, i4) > 11) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }
}

