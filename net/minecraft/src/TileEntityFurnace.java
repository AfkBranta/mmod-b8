/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityFurnace
extends TileEntity
implements IInventory {
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    public int furnaceBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int furnaceCookTime = 0;

    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int i1) {
        return this.furnaceItemStacks[i1];
    }

    @Override
    public ItemStack decrStackSize(int i1, int i2) {
        if (this.furnaceItemStacks[i1] != null) {
            if (this.furnaceItemStacks[i1].stackSize <= i2) {
                ItemStack itemStack3 = this.furnaceItemStacks[i1];
                this.furnaceItemStacks[i1] = null;
                return itemStack3;
            }
            ItemStack itemStack3 = this.furnaceItemStacks[i1].splitStack(i2);
            if (this.furnaceItemStacks[i1].stackSize == 0) {
                this.furnaceItemStacks[i1] = null;
            }
            return itemStack3;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.furnaceItemStacks[i1] = itemStack2;
        if (itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
            itemStack2.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return "Furnace";
    }

    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        int i3 = 0;
        while (i3 < nBTTagList2.tagCount()) {
            NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
            byte b5 = nBTTagCompound4.getByte("Slot");
            if (b5 >= 0 && b5 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[b5] = new ItemStack(nBTTagCompound4);
            }
            ++i3;
        }
        this.furnaceBurnTime = nBTTagCompound1.getShort("BurnTime");
        this.furnaceCookTime = nBTTagCompound1.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
    }

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        nBTTagCompound1.setShort("BurnTime", (short)this.furnaceBurnTime);
        nBTTagCompound1.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nBTTagList2 = new NBTTagList();
        int i3 = 0;
        while (i3 < this.furnaceItemStacks.length) {
            if (this.furnaceItemStacks[i3] != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.furnaceItemStacks[i3].writeToNBT(nBTTagCompound4);
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

    public int getCookProgressScaled(int i1) {
        return this.furnaceCookTime * i1 / 200;
    }

    public int getBurnTimeRemainingScaled(int i1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 200;
        }
        return this.furnaceBurnTime * i1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean z1 = this.furnaceBurnTime > 0;
        boolean z2 = false;
        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }
        if (!this.worldObj.multiplayerWorld) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.furnaceBurnTime = this.getItemBurnTime(this.furnaceItemStacks[1]);
                if (this.furnaceBurnTime > 0) {
                    z2 = true;
                    if (this.furnaceItemStacks[1] != null) {
                        --this.furnaceItemStacks[1].stackSize;
                        if (this.furnaceItemStacks[1].stackSize == 0) {
                            this.furnaceItemStacks[1] = null;
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt()) {
                ++this.furnaceCookTime;
                if (this.furnaceCookTime == 200) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    z2 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }
            if (z1 != this.furnaceBurnTime > 0) {
                z2 = true;
                BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (z2) {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        ItemStack itemStack1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().shiftedIndex);
        return itemStack1 == null ? false : (this.furnaceItemStacks[2] == null ? true : (!this.furnaceItemStacks[2].isItemEqual(itemStack1) ? false : (this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit() && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize() ? true : this.furnaceItemStacks[2].stackSize < itemStack1.getMaxStackSize())));
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack itemStack1 = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[0].getItem().shiftedIndex);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemStack1.copy();
            } else if (this.furnaceItemStacks[2].itemID == itemStack1.itemID) {
                ++this.furnaceItemStacks[2].stackSize;
            }
            --this.furnaceItemStacks[0].stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    private int getItemBurnTime(ItemStack itemStack1) {
        if (itemStack1 == null) {
            return 0;
        }
        int i2 = itemStack1.getItem().shiftedIndex;
        return i2 < 256 && Block.blocksList[i2].blockMaterial == Material.wood ? 300 : (i2 == Item.stick.shiftedIndex ? 100 : (i2 == Item.coal.shiftedIndex ? 1600 : (i2 == Item.bucketLava.shiftedIndex ? 20000 : 0)));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer1.getDistanceSq((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5) <= 64.0;
    }
}

