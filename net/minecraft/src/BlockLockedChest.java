/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockLockedChest
extends Block {
    protected BlockLockedChest(int i1) {
        super(i1, Material.wood);
        this.blockIndexInTexture = 26;
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (i5 == 1) {
            return this.blockIndexInTexture - 1;
        }
        if (i5 == 0) {
            return this.blockIndexInTexture - 1;
        }
        int i6 = iBlockAccess1.getBlockId(i2, i3, i4 - 1);
        int i7 = iBlockAccess1.getBlockId(i2, i3, i4 + 1);
        int i8 = iBlockAccess1.getBlockId(i2 - 1, i3, i4);
        int i9 = iBlockAccess1.getBlockId(i2 + 1, i3, i4);
        int b10 = 3;
        if (Block.opaqueCubeLookup[i6] && !Block.opaqueCubeLookup[i7]) {
            b10 = 3;
        }
        if (Block.opaqueCubeLookup[i7] && !Block.opaqueCubeLookup[i6]) {
            b10 = 2;
        }
        if (Block.opaqueCubeLookup[i8] && !Block.opaqueCubeLookup[i9]) {
            b10 = 5;
        }
        if (Block.opaqueCubeLookup[i9] && !Block.opaqueCubeLookup[i8]) {
            b10 = 4;
        }
        return i5 == b10 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture - 1 : (i1 == 0 ? this.blockIndexInTexture - 1 : (i1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return true;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        world1.setBlockWithNotify(i2, i3, i4, 0);
    }
}

