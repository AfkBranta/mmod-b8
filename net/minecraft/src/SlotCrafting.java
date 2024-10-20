/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AchievementList;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.StatList;

public class SlotCrafting
extends Slot {
    private final IInventory craftMatrix;
    private EntityPlayer field_25015_e;

    public SlotCrafting(EntityPlayer entityPlayer1, IInventory iInventory2, IInventory iInventory3, int i4, int i5, int i6) {
        super(iInventory3, i4, i5, i6);
        this.field_25015_e = entityPlayer1;
        this.craftMatrix = iInventory2;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack1) {
        return false;
    }

    @Override
    public void onPickupFromSlot(ItemStack itemStack1) {
        this.field_25015_e.addStat(StatList.field_25158_z[itemStack1.itemID], 1);
        if (itemStack1.itemID == Block.workbench.blockID) {
            this.field_25015_e.addStat(AchievementList.field_25197_d, 1);
        }
        int i2 = 0;
        while (i2 < this.craftMatrix.getSizeInventory()) {
            ItemStack itemStack3 = this.craftMatrix.getStackInSlot(i2);
            if (itemStack3 != null) {
                this.craftMatrix.decrStackSize(i2, 1);
                if (itemStack3.getItem().hasContainerItem()) {
                    this.craftMatrix.setInventorySlotContents(i2, new ItemStack(itemStack3.getItem().getContainerItem()));
                }
            }
            ++i2;
        }
    }

    @Override
    public boolean func_25014_f() {
        return true;
    }
}

