/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StatList;
import net.minecraft.src.World;

public class BlockSnow
extends Block {
    protected BlockSnow(int i1, int i2) {
        super(i1, i2, Material.snow);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickOnLoad(true);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3 - 1, i4);
        return i5 != 0 && Block.blocksList[i5].isOpaqueCube() ? world1.getBlockMaterial(i2, i3 - 1, i4).getIsSolid() : false;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        this.func_314_h(world1, i2, i3, i4);
    }

    private boolean func_314_h(World world1, int i2, int i3, int i4) {
        if (!this.canPlaceBlockAt(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
            return false;
        }
        return true;
    }

    @Override
    public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
        int i7 = Item.snowball.shiftedIndex;
        float f8 = 0.7f;
        double d9 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0f - f8) * 0.5;
        double d11 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0f - f8) * 0.5;
        double d13 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0f - f8) * 0.5;
        EntityItem entityItem15 = new EntityItem(world1, (double)i3 + d9, (double)i4 + d11, (double)i5 + d13, new ItemStack(i7, 1, 0));
        entityItem15.delayBeforeCanPickup = 10;
        world1.entityJoinedWorld(entityItem15);
        world1.setBlockWithNotify(i3, i4, i5, 0);
        entityPlayer2.addStat(StatList.field_25159_y[this.blockID], 1);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.snowball.shiftedIndex;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (world1.getSavedLightValue(EnumSkyBlock.Block, i2, i3, i4) > 11) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        Material material6 = iBlockAccess1.getBlockMaterial(i2, i3, i4);
        return i5 == 1 ? true : (material6 == this.blockMaterial ? false : super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5));
    }
}

