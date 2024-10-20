/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCake
extends Block {
    protected BlockCake(int i1, int i2) {
        super(i1, i2, Material.cakeMaterial);
        this.setTickOnLoad(true);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        float f6 = 0.0625f;
        float f7 = (float)(1 + i5 * 2) / 16.0f;
        float f8 = 0.5f;
        this.setBlockBounds(f7, 0.0f, f6, 1.0f - f6, f8, 1.0f - f6);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f1 = 0.0625f;
        float f2 = 0.5f;
        this.setBlockBounds(f1, 0.0f, f1, 1.0f - f1, f2, 1.0f - f1);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        float f6 = 0.0625f;
        float f7 = (float)(1 + i5 * 2) / 16.0f;
        float f8 = 0.5f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f7, i3, (float)i4 + f6, (float)(i2 + 1) - f6, (float)i3 + f8 - f6, (float)(i4 + 1) - f6);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockMetadata(i2, i3, i4);
        float f6 = 0.0625f;
        float f7 = (float)(1 + i5 * 2) / 16.0f;
        float f8 = 0.5f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f7, i3, (float)i4 + f6, (float)(i2 + 1) - f6, (float)i3 + f8, (float)(i4 + 1) - f6);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i1 == 1 ? this.blockIndexInTexture : (i1 == 0 ? this.blockIndexInTexture + 3 : (i2 > 0 && i1 == 4 ? this.blockIndexInTexture + 2 : this.blockIndexInTexture + 1));
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture : (i1 == 0 ? this.blockIndexInTexture + 3 : this.blockIndexInTexture + 1);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.eatCakeSlice(world1, i2, i3, i4, entityPlayer5);
        return true;
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.eatCakeSlice(world1, i2, i3, i4, entityPlayer5);
    }

    private void eatCakeSlice(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (entityPlayer5.health < 20) {
            entityPlayer5.heal(3);
            int i6 = world1.getBlockMetadata(i2, i3, i4) + 1;
            if (i6 >= 6) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            } else {
                world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
                world1.markBlockAsNeedsUpdate(i2, i3, i4);
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return !super.canPlaceBlockAt(world1, i2, i3, i4) ? false : this.canBlockStay(world1, i2, i3, i4);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (!this.canBlockStay(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public boolean canBlockStay(World world1, int i2, int i3, int i4) {
        return world1.getBlockMaterial(i2, i3 - 1, i4).isSolid();
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return 0;
    }
}

