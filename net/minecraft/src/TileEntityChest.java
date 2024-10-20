/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityChest
extends TileEntity
implements IInventory {
    private ItemStack[] chestContents = new ItemStack[36];

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return this.chestContents[i1];
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.chestContents[i1] != null) {
            if (this.chestContents[i1].stackSize <= i2) {
                ItemStack itemStack3 = this.chestContents[i1];
                this.chestContents[i1] = null;
                this.onInventoryChanged();
                return itemStack3;
            }
            ItemStack itemStack3 = this.chestContents[i1].splitStack(i2);
            if (this.chestContents[i1].stackSize == 0) {
                this.chestContents[i1] = null;
            }
            this.onInventoryChanged();
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.chestContents[i1] = itemStack2;
        if (itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
            itemStack2.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Chest";
    }

    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];
        int i3 = 0;
        while (i3 < nBTTagList2.tagCount()) {
            NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
            int i5 = nBTTagCompound4.getByte("Slot") & 0xFF;
            if (i5 >= 0 && i5 < this.chestContents.length) {
                this.chestContents[i5] = new ItemStack(nBTTagCompound4);
            }
            ++i3;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = new NBTTagList();
        int i3 = 0;
        while (i3 < this.chestContents.length) {
            if (this.chestContents[i3] != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.chestContents[i3].writeToNBT(nBTTagCompound4);
                nBTTagList2.setTag(nBTTagCompound4);
            }
            ++i3;
        }
        nBTTagCompound1.setTag("Items", nBTTagList2);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer1.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }
}

