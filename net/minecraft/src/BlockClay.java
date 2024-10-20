/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;

public class BlockClay
extends Block {
    public BlockClay(int i1, int i2) {
        super(i1, i2, Material.clay);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.clay.shiftedIndex;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 4;
    }
}

