/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInvBasic;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryBasic
implements IInventory {
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_20073_d;

    public InventoryBasic(String string1, int i2) {
        this.inventoryTitle = string1;
        this.slotsCount = i2;
        this.inventoryContents = new ItemStack[i2];
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return this.inventoryContents[i1];
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.inventoryContents[i1] != null) {
            if (this.inventoryContents[i1].stackSize <= i2) {
                ItemStack itemStack3 = this.inventoryContents[i1];
                this.inventoryContents[i1] = null;
                this.onInventoryChanged();
                return itemStack3;
            }
            ItemStack itemStack3 = this.inventoryContents[i1].splitStack(i2);
            if (this.inventoryContents[i1].stackSize == 0) {
                this.inventoryContents[i1] = null;
            }
            this.onInventoryChanged();
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.inventoryContents[i1] = itemStack2;
        if (itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
            itemStack2.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public int getSizeInventory() {
        return this.slotsCount;
    }

    @Override
    public String getInvName() {
        return this.inventoryTitle;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
        if (this.field_20073_d != null) {
            int i1 = 0;
            while (i1 < this.field_20073_d.size()) {
                ((IInvBasic)this.field_20073_d.get(i1)).func_20134_a(this);
                ++i1;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return true;
    }
}

