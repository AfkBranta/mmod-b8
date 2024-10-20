/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCactus
extends Block {
    protected BlockCactus(int i1, int i2) {
        super(i1, i2, Material.cactus);
        this.setTickOnLoad(true);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (world1.isAirBlock(i2, i3 + 1, i4)) {
            int i6 = 1;
            while (world1.getBlockId(i2, i3 - i6, i4) == this.blockID) {
                ++i6;
            }
            if (i6 < 3) {
                int i7 = world1.getBlockMetadata(i2, i3, i4);
                if (i7 == 15) {
                    world1.setBlockWithNotify(i2, i3 + 1, i4, this.blockID);
                    world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
                } else {
                    world1.setBlockMetadataWithNotify(i2, i3, i4, i7 + 1);
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        float f5 = 0.0625f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f5, i3, (float)i4 + f5, (float)(i2 + 1) - f5, (float)(i3 + 1) - f5, (float)(i4 + 1) - f5);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        float f5 = 0.0625f;
        return AxisAlignedBB.getBoundingBoxFromPool((float)i2 + f5, i3, (float)i4 + f5, (float)(i2 + 1) - f5, i3 + 1, (float)(i4 + 1) - f5);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture - 1 : (i1 == 0 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture);
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
    public int getRenderType() {
        return 13;
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
        if (world1.getBlockMaterial(i2 - 1, i3, i4).isSolid()) {
            return false;
        }
        if (world1.getBlockMaterial(i2 + 1, i3, i4).isSolid()) {
            return false;
        }
        if (world1.getBlockMaterial(i2, i3, i4 - 1).isSolid()) {
            return false;
        }
        if (world1.getBlockMaterial(i2, i3, i4 + 1).isSolid()) {
            return false;
        }
        int i5 = world1.getBlockId(i2, i3 - 1, i4);
        return i5 == Block.cactus.blockID || i5 == Block.sand.blockID;
    }

    @Override
    public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
        entity5.attackEntityFrom(null, 1);
    }
}

