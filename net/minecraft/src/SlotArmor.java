/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingInventoryPlayerCB;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

class SlotArmor
extends Slot {
    final int armorType;
    final CraftingInventoryPlayerCB inventory;

    SlotArmor(CraftingInventoryPlayerCB craftingInventoryPlayerCB1, IInventory iInventory2, int i3, int i4, int i5, int i6) {
        super(iInventory2, i3, i4, i5);
        this.inventory = craftingInventoryPlayerCB1;
        this.armorType = i6;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack1) {
        return itemStack1.getItem() instanceof ItemArmor ? ((ItemArmor)itemStack1.getItem()).armorType == this.armorType : (itemStack1.getItem().shiftedIndex == Block.pumpkin.blockID ? this.armorType == 0 : false);
    }
}

