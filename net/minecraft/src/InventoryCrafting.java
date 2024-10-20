/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryCrafting
implements IInventory {
    private ItemStack[] stackList;
    private int field_21104_b;
    private CraftingInventoryCB eventHandler;

    public InventoryCrafting(CraftingInventoryCB craftingInventoryCB1, int i2, int i3) {
        int i4 = i2 * i3;
        this.stackList = new ItemStack[i4];
        this.eventHandler = craftingInventoryCB1;
        this.field_21104_b = i2;
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return i1 >= this.getSizeInventory() ? null : this.stackList[i1];
    }

    public ItemStack func_21103_b(int i1, int i2) {
        if (i1 >= 0 && i1 < this.field_21104_b) {
            int i3 = i1 + i2 * this.field_21104_b;
            return this.getStackInSlot(i3);
        }
        return null;
    }

    @Override
    public String getInvName() {
        return "Crafting";
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.stackList[i1] != null) {
            if (this.stackList[i1].stackSize <= i2) {
                ItemStack itemStack3 = this.stackList[i1];
                this.stackList[i1] = null;
                this.eventHandler.onCraftMatrixChanged(this);
                return itemStack3;
            }
            ItemStack itemStack3 = this.stackList[i1].splitStack(i2);
            if (this.stackList[i1].stackSize == 0) {
                this.stackList[i1] = null;
            }
            this.eventHandler.onCraftMatrixChanged(this);
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.stackList[i1] = itemStack2;
        this.eventHandler.onCraftMatrixChanged(this);
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

