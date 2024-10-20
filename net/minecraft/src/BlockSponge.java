/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSponge
extends Block {
    protected BlockSponge(int i1) {
        super(i1, Material.sponge);
        this.blockIndexInTexture = 48;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        int b5 = 2;
        int i6 = i2 - b5;
        while (i6 <= i2 + b5) {
            int i7 = i3 - b5;
            while (i7 <= i3 + b5) {
                int i8 = i4 - b5;
                while (i8 <= i4 + b5) {
                    if (world1.getBlockMaterial(i6, i7, i8) == Material.water) {
                        // empty if block
                    }
                    ++i8;
                }
                ++i7;
            }
            ++i6;
        }
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        int b5 = 2;
        int i6 = i2 - b5;
        while (i6 <= i2 + b5) {
            int i7 = i3 - b5;
            while (i7 <= i3 + b5) {
                int i8 = i4 - b5;
                while (i8 <= i4 + b5) {
                    world1.notifyBlocksOfNeighborChange(i6, i7, i8, world1.getBlockId(i6, i7, i8));
                    ++i8;
                }
                ++i7;
            }
            ++i6;
        }
    }
}

