/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockStone
extends Block {
    public BlockStone(int i1, int i2) {
        super(i1, i2, Material.rock);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.cobblestone.blockID;
    }
}

