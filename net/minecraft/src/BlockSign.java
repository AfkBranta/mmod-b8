/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class BlockSign
extends BlockContainer {
    private Class signEntityClass;
    private boolean isFreestanding;

    protected BlockSign(int i1, Class class2, boolean z3) {
        super(i1, Material.wood);
        this.isFreestanding = z3;
        this.blockIndexInTexture = 4;
        this.signEntityClass = class2;
        float f4 = 0.25f;
        float f5 = 1.0f;
        this.setBlockBounds(0.5f - f4, 0.0f, 0.5f - f4, 0.5f + f4, f5, 0.5f + f4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        return super.getSelectedBoundingBoxFromPool(world1, i2, i3, i4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        if (!this.isFreestanding) {
            int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
            float f6 = 0.28125f;
            float f7 = 0.78125f;
            float f8 = 0.0f;
            float f9 = 1.0f;
            float f10 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            if (i5 == 2) {
                this.setBlockBounds(f8, f6, 1.0f - f10, f9, f7, 1.0f);
            }
            if (i5 == 3) {
                this.setBlockBounds(f8, f6, 0.0f, f9, f7, f10);
            }
            if (i5 == 4) {
                this.setBlockBounds(1.0f - f10, f6, f8, 1.0f, f7, f9);
            }
            if (i5 == 5) {
                this.setBlockBounds(0.0f, f6, f8, f10, f7, f9);
            }
        }
    }

    @Override
    public int getRenderType() {
        return -1;
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
    protected TileEntity getBlockEntity() {
        try {
            return (TileEntity)this.signEntityClass.newInstance();
        }
        catch (Exception exception2) {
            throw new RuntimeException(exception2);
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.sign.shiftedIndex;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        boolean z6 = false;
        if (this.isFreestanding) {
            if (!world1.getBlockMaterial(i2, i3 - 1, i4).isSolid()) {
                z6 = true;
            }
        } else {
            int i7 = world1.getBlockMetadata(i2, i3, i4);
            z6 = true;
            if (i7 == 2 && world1.getBlockMaterial(i2, i3, i4 + 1).isSolid()) {
                z6 = false;
            }
            if (i7 == 3 && world1.getBlockMaterial(i2, i3, i4 - 1).isSolid()) {
                z6 = false;
            }
            if (i7 == 4 && world1.getBlockMaterial(i2 + 1, i3, i4).isSolid()) {
                z6 = false;
            }
            if (i7 == 5 && world1.getBlockMaterial(i2 - 1, i3, i4).isSolid()) {
                z6 = false;
            }
        }
        if (z6) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
        super.onNeighborBlockChange(world1, i2, i3, i4, i5);
    }
}

