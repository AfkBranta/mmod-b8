/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.StatList;

public abstract class CraftingInventoryCB {
    public List field_20123_d = new ArrayList();
    public List slots = new ArrayList();
    public int windowId = 0;
    private short field_20917_a = 0;
    protected List field_20121_g = new ArrayList();
    private Set field_20918_b = new HashSet();

    protected void addSlot(Slot slot1) {
        slot1.slotNumber = this.slots.size();
        this.slots.add(slot1);
        this.field_20123_d.add(null);
    }

    public void updateCraftingResults() {
        int i1 = 0;
        while (i1 < this.slots.size()) {
            ItemStack itemStack2 = ((Slot)this.slots.get(i1)).getStack();
            ItemStack itemStack3 = (ItemStack)this.field_20123_d.get(i1);
            if (!ItemStack.areItemStacksEqual(itemStack3, itemStack2)) {
                itemStack3 = itemStack2 == null ? null : itemStack2.copy();
                this.field_20123_d.set(i1, itemStack3);
                int i4 = 0;
                while (i4 < this.field_20121_g.size()) {
                    ((ICrafting)this.field_20121_g.get(i4)).func_20159_a(this, i1, itemStack3);
                    ++i4;
                }
            }
            ++i1;
        }
    }

    public Slot getSlot(int i1) {
        return (Slot)this.slots.get(i1);
    }

    public ItemStack func_20116_a(int i1, int i2, EntityPlayer entityPlayer3) {
        ItemStack itemStack4 = null;
        if (i2 == 0 || i2 == 1) {
            InventoryPlayer inventoryPlayer5 = entityPlayer3.inventory;
            if (i1 == -999) {
                if (inventoryPlayer5.getItemStack() != null && i1 == -999) {
                    if (i2 == 0) {
                        entityPlayer3.dropPlayerItem(inventoryPlayer5.getItemStack());
                        inventoryPlayer5.setItemStack(null);
                    }
                    if (i2 == 1) {
                        entityPlayer3.dropPlayerItem(inventoryPlayer5.getItemStack().splitStack(1));
                        if (inventoryPlayer5.getItemStack().stackSize == 0) {
                            inventoryPlayer5.setItemStack(null);
                        }
                    }
                }
            } else {
                Slot slot6 = (Slot)this.slots.get(i1);
                if (slot6 != null) {
                    int i9;
                    slot6.onSlotChanged();
                    ItemStack itemStack7 = slot6.getStack();
                    ItemStack itemStack8 = inventoryPlayer5.getItemStack();
                    if (itemStack7 != null) {
                        itemStack4 = itemStack7.copy();
                    }
                    if (itemStack7 == null) {
                        if (itemStack8 != null && slot6.isItemValid(itemStack8)) {
                            int i92;
                            int n = i92 = i2 == 0 ? itemStack8.stackSize : 1;
                            if (i92 > slot6.getSlotStackLimit()) {
                                i92 = slot6.getSlotStackLimit();
                            }
                            slot6.putStack(itemStack8.splitStack(i92));
                            if (itemStack8.stackSize == 0) {
                                inventoryPlayer5.setItemStack(null);
                            }
                        }
                    } else if (itemStack8 == null) {
                        int i93 = i2 == 0 ? itemStack7.stackSize : (itemStack7.stackSize + 1) / 2;
                        ItemStack itemStack10 = slot6.decrStackSize(i93);
                        if (itemStack10 != null && slot6.func_25014_f()) {
                            entityPlayer3.addStat(StatList.field_25158_z[itemStack10.itemID], itemStack10.stackSize);
                        }
                        inventoryPlayer5.setItemStack(itemStack10);
                        if (itemStack7.stackSize == 0) {
                            slot6.putStack(null);
                        }
                        slot6.onPickupFromSlot(inventoryPlayer5.getItemStack());
                    } else if (slot6.isItemValid(itemStack8)) {
                        if (itemStack7.itemID != itemStack8.itemID || itemStack7.getHasSubtypes() && itemStack7.getItemDamage() != itemStack8.getItemDamage()) {
                            if (itemStack8.stackSize <= slot6.getSlotStackLimit()) {
                                slot6.putStack(itemStack8);
                                inventoryPlayer5.setItemStack(itemStack7);
                            }
                        } else {
                            int i94;
                            int n = i94 = i2 == 0 ? itemStack8.stackSize : 1;
                            if (i94 > slot6.getSlotStackLimit() - itemStack7.stackSize) {
                                i94 = slot6.getSlotStackLimit() - itemStack7.stackSize;
                            }
                            if (i94 > itemStack8.getMaxStackSize() - itemStack7.stackSize) {
                                i94 = itemStack8.getMaxStackSize() - itemStack7.stackSize;
                            }
                            itemStack8.splitStack(i94);
                            if (itemStack8.stackSize == 0) {
                                inventoryPlayer5.setItemStack(null);
                            }
                            itemStack7.stackSize += i94;
                        }
                    } else if (!(itemStack7.itemID != itemStack8.itemID || itemStack8.getMaxStackSize() <= 1 || itemStack7.getHasSubtypes() && itemStack7.getItemDamage() != itemStack8.getItemDamage() || (i9 = itemStack7.stackSize) <= 0 || i9 + itemStack8.stackSize > itemStack8.getMaxStackSize())) {
                        itemStack8.stackSize += i9;
                        ItemStack itemStack10 = itemStack7.splitStack(i9);
                        if (itemStack10 != null && slot6.func_25014_f()) {
                            entityPlayer3.addStat(StatList.field_25158_z[itemStack10.itemID], itemStack10.stackSize);
                        }
                        if (itemStack7.stackSize == 0) {
                            slot6.putStack(null);
                        }
                        slot6.onPickupFromSlot(inventoryPlayer5.getItemStack());
                    }
                }
            }
        }
        return itemStack4;
    }

    public void onCraftGuiClosed(EntityPlayer entityPlayer1) {
        InventoryPlayer inventoryPlayer2 = entityPlayer1.inventory;
        if (inventoryPlayer2.getItemStack() != null) {
            entityPlayer1.dropPlayerItem(inventoryPlayer2.getItemStack());
            inventoryPlayer2.setItemStack(null);
        }
    }

    public void onCraftMatrixChanged(IInventory iInventory1) {
        this.updateCraftingResults();
    }

    public void putStackInSlot(int i1, ItemStack itemStack2) {
        this.getSlot(i1).putStack(itemStack2);
    }

    public void putStacksInSlots(ItemStack[] itemStack1) {
        int i2 = 0;
        while (i2 < itemStack1.length) {
            this.getSlot(i2).putStack(itemStack1[i2]);
            ++i2;
        }
    }

    public void func_20112_a(int i1, int i2) {
    }

    public short func_20111_a(InventoryPlayer inventoryPlayer1) {
        this.field_20917_a = (short)(this.field_20917_a + 1);
        return this.field_20917_a;
    }

    public void func_20113_a(short s1) {
    }

    public void func_20110_b(short s1) {
    }

    public abstract boolean isUsableByPlayer(EntityPlayer var1);
}

