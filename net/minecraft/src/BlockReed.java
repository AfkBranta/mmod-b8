/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockReed
extends Block {
    protected BlockReed(int i1, int i2) {
        super(i1, Material.plants);
        this.blockIndexInTexture = i2;
        float f3 = 0.375f;
        this.setBlockBounds(0.5f - f3, 0.0f, 0.5f - f3, 0.5f + f3, 1.0f, 0.5f + f3);
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
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3 - 1, i4);
        return i5 == this.blockID ? true : (i5 != Block.grass.blockID && i5 != Block.dirt.blockID ? false : (world1.getBlockMaterial(i2 - 1, i3 - 1, i4) == Material.water ? true : (world1.getBlockMaterial(i2 + 1, i3 - 1, i4) == Material.water ? true : (world1.getBlockMaterial(i2, i3 - 1, i4 - 1) == Material.water ? true : world1.getBlockMaterial(i2, i3 - 1, i4 + 1) == Material.water))));
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        this.checkBlockCoordValid(world1, i2, i3, i4);
    }

    protected final void checkBlockCoordValid(World world1, int i2, int i3, int i4) {
        if (!this.canBlockStay(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public boolean canBlockStay(World world1, int i2, int i3, int i4) {
        return this.canPlaceBlockAt(world1, i2, i3, i4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.reed.shiftedIndex;
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
    public int getRenderType() {
        return 1;
    }
}

