/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AchievementList;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLog
extends Block {
    protected BlockLog(int i1) {
        super(i1, Material.wood);
        this.blockIndexInTexture = 20;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 1;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.wood.blockID;
    }

    @Override
    public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
        super.harvestBlock(world1, entityPlayer2, i3, i4, i5, i6);
        entityPlayer2.addStat(AchievementList.field_25198_c, 1);
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        int b5 = 4;
        int i6 = b5 + 1;
        if (world1.checkChunksExist(i2 - i6, i3 - i6, i4 - i6, i2 + i6, i3 + i6, i4 + i6)) {
            int i7 = -b5;
            while (i7 <= b5) {
                int i8 = -b5;
                while (i8 <= b5) {
                    int i9 = -b5;
                    while (i9 <= b5) {
                        int i11;
                        int i10 = world1.getBlockId(i2 + i7, i3 + i8, i4 + i9);
                        if (i10 == Block.leaves.blockID && ((i11 = world1.getBlockMetadata(i2 + i7, i3 + i8, i4 + i9)) & 4) == 0) {
                            world1.setBlockMetadata(i2 + i7, i3 + i8, i4 + i9, i11 | 4);
                        }
                        ++i9;
                    }
                    ++i8;
                }
                ++i7;
            }
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i1 == 1 ? 21 : (i1 == 0 ? 21 : (i2 == 1 ? 116 : (i2 == 2 ? 117 : 20)));
    }

    @Override
    protected int damageDropped(int i1) {
        return i1;
    }
}

