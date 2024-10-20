/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryCraftResult
implements IInventory {
    private ItemStack[] stackResult = new ItemStack[1];

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return this.stackResult[i1];
    }

    @Override
    public String getInvName() {
        return "Result";
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.stackResult[i1] != null) {
            ItemStack itemStack3 = this.stackResult[i1];
            this.stackResult[i1] = null;
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.stackResult[i1] = itemStack2;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return true;
    }
}

