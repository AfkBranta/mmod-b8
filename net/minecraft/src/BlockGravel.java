/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.BlockSand;
import net.minecraft.src.Item;

public class BlockGravel
extends BlockSand {
    public BlockGravel(int i1, int i2) {
        super(i1, i2);
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return random2.nextInt(10) == 0 ? Item.flint.shiftedIndex : this.blockID;
    }
}

