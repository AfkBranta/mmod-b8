/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockCloth
extends Block {
    public BlockCloth() {
        super(35, 64, Material.cloth);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        if (i2 == 0) {
            return this.blockIndexInTexture;
        }
        i2 = ~(i2 & 0xF);
        return 113 + ((i2 & 8) >> 3) + (i2 & 7) * 16;
    }

    @Override
    protected int damageDropped(int i1) {
        return i1;
    }

    public static int func_21034_c(int i0) {
        return ~i0 & 0xF;
    }

    public static int func_21035_d(int i0) {
        return ~i0 & 0xF;
    }
}

