/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;

public class ItemSpade
extends ItemTool {
    private static Block[] blocksEffectiveAgainst = new Block[]{Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay};

    public ItemSpade(int i1, EnumToolMaterial enumToolMaterial2) {
        super(i1, 1, enumToolMaterial2, blocksEffectiveAgainst);
    }

    @Override
    public boolean canHarvestBlock(Block block1) {
        return block1 == Block.snow ? true : block1 == Block.blockSnow;
    }
}

