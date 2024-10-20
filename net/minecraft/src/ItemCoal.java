/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ItemCoal
extends Item {
    public ItemCoal(int i1) {
        super(i1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public String getItemNameIS(ItemStack itemStack1) {
        return itemStack1.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
    }
}

