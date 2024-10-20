/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemSoup
extends ItemFood {
    public ItemSoup(int i1, int i2) {
        super(i1, i2, false);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        super.onItemRightClick(itemStack1, world2, entityPlayer3);
        return new ItemStack(Item.bowlEmpty);
    }
}

