/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockStone;

public class BlockObsidian
extends BlockStone {
    public BlockObsidian(int i1, int i2) {
        super(i1, i2);
    }

    @Override
    public int quantityDropped(Random random1) {
        return 1;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.obsidian.blockID;
    }
}

