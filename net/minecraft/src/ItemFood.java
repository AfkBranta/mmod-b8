/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFood
extends Item {
    private int healAmount;
    private boolean isWolfsFavoriteMeat;

    public ItemFood(int i1, int i2, boolean z3) {
        super(i1);
        this.healAmount = i2;
        this.isWolfsFavoriteMeat = z3;
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        --itemStack1.stackSize;
        entityPlayer3.heal(this.healAmount);
        return itemStack1;
    }

    public int getHealAmount() {
        return this.healAmount;
    }

    public boolean getIsWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
}

