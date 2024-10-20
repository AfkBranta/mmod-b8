/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemTool;

public class ItemAxe
extends ItemTool {
    private static Block[] blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookShelf, Block.wood, Block.crate};

    protected ItemAxe(int i1, EnumToolMaterial enumToolMaterial2) {
        super(i1, 3, enumToolMaterial2, blocksEffectiveAgainst);
    }
}

