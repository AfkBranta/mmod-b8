/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBreakable;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockIce
extends BlockBreakable {
    public BlockIce(int i1, int i2) {
        super(i1, i2, Material.ice, false);
        this.slipperiness = 0.98f;
        this.setTickOnLoad(true);
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, 1 - i5);
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        Material material5 = world1.getBlockMaterial(i2, i3 - 1, i4);
        if (material5.getIsSolid() || material5.getIsLiquid()) {
            world1.setBlockWithNotify(i2, i3, i4, Block.waterMoving.blockID);
        }
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (world1.getSavedLightValue(EnumSkyBlock.Block, i2, i3, i4) > 11 - Block.lightOpacity[this.blockID]) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, Block.waterStill.blockID);
        }
    }
}

