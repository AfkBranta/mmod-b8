/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityMobSpawner;

public class BlockMobSpawner
extends BlockContainer {
    protected BlockMobSpawner(int i1, int i2) {
        super(i1, i2, Material.rock);
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMobSpawner();
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return 0;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}

