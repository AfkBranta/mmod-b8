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

public class BlockSoil
extends Block {
    protected BlockSoil(int i1) {
        super(i1, Material.ground);
        this.blockIndexInTexture = 87;
        this.setTickOnLoad(true);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return AxisAlignedBB.getBoundingBoxFromPool(i2 + 0, i3 + 0, i4 + 0, i2 + 1, i3 + 1, i4 + 1);
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
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i1 == 1 && i2 > 0 ? this.blockIndexInTexture - 1 : (i1 == 1 ? this.blockIndexInTexture : 2);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (random5.nextInt(5) == 0) {
            if (this.isWaterNearby(world1, i2, i3, i4)) {
                world1.setBlockMetadataWithNotify(i2, i3, i4, 7);
            } else {
                int i6 = world1.getBlockMetadata(i2, i3, i4);
                if (i6 > 0) {
                    world1.setBlockMetadataWithNotify(i2, i3, i4, i6 - 1);
                } else if (!this.isCropsNearby(world1, i2, i3, i4)) {
                    world1.setBlockWithNotify(i2, i3, i4, Block.dirt.blockID);
                }
            }
        }
    }

    @Override
    public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
        if (world1.rand.nextInt(4) == 0) {
            world1.setBlockWithNotify(i2, i3, i4, Block.dirt.blockID);
        }
    }

    private boolean isCropsNearby(World world1, int i2, int i3, int i4) {
        int b5 = 0;
        int i6 = i2 - b5;
        while (i6 <= i2 + b5) {
            int i7 = i4 - b5;
            while (i7 <= i4 + b5) {
                if (world1.getBlockId(i6, i3 + 1, i7) == Block.crops.blockID) {
                    return true;
                }
                ++i7;
            }
            ++i6;
        }
        return false;
    }

    private boolean isWaterNearby(World world1, int i2, int i3, int i4) {
        int i5 = i2 - 4;
        while (i5 <= i2 + 4) {
            int i6 = i3;
            while (i6 <= i3 + 1) {
                int i7 = i4 - 4;
                while (i7 <= i4 + 4) {
                    if (world1.getBlockMaterial(i5, i6, i7) == Material.water) {
                        return true;
                    }
                    ++i7;
                }
                ++i6;
            }
            ++i5;
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        super.onNeighborBlockChange(world1, i2, i3, i4, i5);
        Material material6 = world1.getBlockMaterial(i2, i3 + 1, i4);
        if (material6.isSolid()) {
            world1.setBlockWithNotify(i2, i3, i4, Block.dirt.blockID);
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.dirt.idDropped(0, random2);
    }
}

