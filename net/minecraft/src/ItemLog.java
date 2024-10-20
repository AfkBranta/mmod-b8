/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemLog
extends ItemBlock {
    public ItemLog(int i1) {
        super(i1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getIconIndex(ItemStack itemStack1) {
        return Block.wood.getBlockTextureFromSideAndMetadata(2, itemStack1.getItemDamage());
    }

    @Override
    public int func_21012_a(int i1) {
        return i1;
    }
}

