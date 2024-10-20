/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class BlockOreBlock
extends Block {
    public BlockOreBlock(int i1, int i2) {
        super(i1, Material.iron);
        this.blockIndexInTexture = i2;
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return this.blockIndexInTexture;
    }
}

