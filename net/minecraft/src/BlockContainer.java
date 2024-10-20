/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class BlockContainer
extends Block {
    protected BlockContainer(int i1, Material material2) {
        super(i1, material2);
        BlockContainer.isBlockContainer[i1] = true;
    }

    protected BlockContainer(int i1, int i2, Material material3) {
        super(i1, i2, material3);
        BlockContainer.isBlockContainer[i1] = true;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        super.onBlockAdded(world1, i2, i3, i4);
        world1.setBlockTileEntity(i2, i3, i4, this.getBlockEntity());
    }

    @Override
    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
        super.onBlockRemoval(world1, i2, i3, i4);
        world1.removeBlockTileEntity(i2, i3, i4);
    }

    protected abstract TileEntity getBlockEntity();
}

