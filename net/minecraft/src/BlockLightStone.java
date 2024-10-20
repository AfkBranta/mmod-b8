/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.Material;

public class BlockLightStone
extends Block {
    public BlockLightStone(int i1, int i2, Material material3) {
        super(i1, i2, material3);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Item.lightStoneDust.shiftedIndex;
    }
}

