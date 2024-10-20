/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

public class InventoryLargeChest
implements IInventory {
    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;

    public InventoryLargeChest(String string1, IInventory iInventory2, IInventory iInventory3) {
        this.name = string1;
        this.upperChest = iInventory2;
        this.lowerChest = iInventory3;
    }

    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }

    @Override
    public String getInvName() {
        return this.name;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return i1 >= this.upperChest.getSizeInventory() ? this.lowerChest.getStackInSlot(i1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(i1);
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        return i1 >= this.upperChest.getSizeInventory() ? this.lowerChest.decrStackSize(i1 - this.upperChest.getSizeInventory(), i2) : this.upperChest.decrStackSize(i1, i2);
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        if (i1 >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(i1 - this.upperChest.getSizeInventory(), itemStack2);
        } else {
            this.upperChest.setInventorySlotContents(i1, itemStack2);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }

    @Override
    public void onInventoryChanged() {
        this.upperChest.onInventoryChanged();
        this.lowerChest.onInventoryChanged();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.upperChest.canInteractWith(entityPlayer1) && this.lowerChest.canInteractWith(entityPlayer1);
    }
}

