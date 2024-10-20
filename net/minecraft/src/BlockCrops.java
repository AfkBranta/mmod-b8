/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.EntityItem;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockCrops
extends BlockFlower {
    protected BlockCrops(int i1, int i2) {
        super(i1, i2);
        this.blockIndexInTexture = i2;
        this.setTickOnLoad(true);
        float f3 = 0.5f;
        this.setBlockBounds(0.5f - f3, 0.0f, 0.5f - f3, 0.5f + f3, 0.25f, 0.5f + f3);
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int i1) {
        return i1 == Block.tilledField.blockID;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        float f7;
        int i6;
        super.updateTick(world1, i2, i3, i4, random5);
        if (world1.getBlockLightValue(i2, i3 + 1, i4) >= 9 && (i6 = world1.getBlockMetadata(i2, i3, i4)) < 7 && random5.nextInt((int)(100.0f / (f7 = this.getGrowthRate(world1, i2, i3, i4)))) == 0) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, ++i6);
        }
    }

    public void fertilize(World world1, int i2, int i3, int i4) {
        world1.setBlockMetadataWithNotify(i2, i3, i4, 7);
    }

    private float getGrowthRate(World world1, int i2, int i3, int i4) {
        float f5 = 1.0f;
        int i6 = world1.getBlockId(i2, i3, i4 - 1);
        int i7 = world1.getBlockId(i2, i3, i4 + 1);
        int i8 = world1.getBlockId(i2 - 1, i3, i4);
        int i9 = world1.getBlockId(i2 + 1, i3, i4);
        int i10 = world1.getBlockId(i2 - 1, i3, i4 - 1);
        int i11 = world1.getBlockId(i2 + 1, i3, i4 - 1);
        int i12 = world1.getBlockId(i2 + 1, i3, i4 + 1);
        int i13 = world1.getBlockId(i2 - 1, i3, i4 + 1);
        boolean z14 = i8 == this.blockID || i9 == this.blockID;
        boolean z15 = i6 == this.blockID || i7 == this.blockID;
        boolean z16 = i10 == this.blockID || i11 == this.blockID || i12 == this.blockID || i13 == this.blockID;
        int i17 = i2 - 1;
        while (i17 <= i2 + 1) {
            int i18 = i4 - 1;
            while (i18 <= i4 + 1) {
                int i19 = world1.getBlockId(i17, i3 - 1, i18);
                float f20 = 0.0f;
                if (i19 == Block.tilledField.blockID) {
                    f20 = 1.0f;
                    if (world1.getBlockMetadata(i17, i3 - 1, i18) > 0) {
                        f20 = 3.0f;
                    }
                }
                if (i17 != i2 || i18 != i4) {
                    f20 /= 4.0f;
                }
                f5 += f20;
                ++i18;
            }
            ++i17;
        }
        if (z16 || z14 && z15) {
            f5 /= 2.0f;
        }
        return f5;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        if (i2 < 0) {
            i2 = 7;
        }
        return this.blockIndexInTexture + i2;
    }

    @Override
    public int getRenderType() {
        return 6;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
        super.onBlockDestroyedByPlayer(world1, i2, i3, i4, i5);
        if (!world1.multiplayerWorld) {
            int i6 = 0;
            while (i6 < 3) {
                if (world1.rand.nextInt(15) <= i5) {
                    float f7 = 0.7f;
                    float f8 = world1.rand.nextFloat() * f7 + (1.0f - f7) * 0.5f;
                    float f9 = world1.rand.nextFloat() * f7 + (1.0f - f7) * 0.5f;
                    float f10 = world1.rand.nextFloat() * f7 + (1.0f - f7) * 0.5f;
                    EntityItem entityItem11 = new EntityItem(world1, (float)i2 + f8, (float)i3 + f9, (float)i4 + f10, new ItemStack(Item.seeds));
                    entityItem11.delayBeforeCanPickup = 10;
                    world1.entityJoinedWorld(entityItem11);
                }
                ++i6;
            }
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return i1 == 7 ? Item.wheat.shiftedIndex : -1;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 1;
    }
}

