/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockBookshelf
extends Block {
    public BlockBookshelf(int i1, int i2) {
        super(i1, i2, Material.wood);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 <= 1 ? 4 : this.blockIndexInTexture;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }
}

