/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;

public class BlockLeavesBase
extends Block {
    protected boolean graphicsLevel;

    protected BlockLeavesBase(int i1, int i2, Material material3, boolean z4) {
        super(i1, i2, material3);
        this.graphicsLevel = z4;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        int i6 = iBlockAccess1.getBlockId(i2, i3, i4);
        return !this.graphicsLevel && i6 == this.blockID ? false : super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
    }
}

