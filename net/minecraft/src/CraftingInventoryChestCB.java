/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

public class CraftingInventoryChestCB
extends CraftingInventoryCB {
    private IInventory field_20125_a;

    public CraftingInventoryChestCB(IInventory iInventory1, IInventory iInventory2) {
        int i6;
        this.field_20125_a = iInventory2;
        int i3 = iInventory2.getSizeInventory() / 9;
        int i4 = (i3 - 4) * 18;
        int i5 = 0;
        while (i5 < i3) {
            i6 = 0;
            while (i6 < 9) {
                this.addSlot(new Slot(iInventory2, i6 + i5 * 9, 8 + i6 * 18, 18 + i5 * 18));
                ++i6;
            }
            ++i5;
        }
        i5 = 0;
        while (i5 < 3) {
            i6 = 0;
            while (i6 < 9) {
                this.addSlot(new Slot(iInventory1, i6 + i5 * 9 + 9, 8 + i6 * 18, 103 + i5 * 18 + i4));
                ++i6;
            }
            ++i5;
        }
        i5 = 0;
        while (i5 < 9) {
            this.addSlot(new Slot(iInventory1, i5, 8 + i5 * 18, 161 + i4));
            ++i5;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return this.field_20125_a.canInteractWith(entityPlayer1);
    }
}

