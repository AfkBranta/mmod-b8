/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntityDispenser;

public class CraftingInventoryDispenserCB
extends CraftingInventoryCB {
    private TileEntityDispenser field_21149_a;

    public CraftingInventoryDispenserCB(IInventory iInventory1, TileEntityDispenser tileEntityDispenser2) {
        int i4;
        this.field_21149_a = tileEntityDispenser2;
        int i3 = 0;
        while (i3 < 3) {
            i4 = 0;
            while (i4 < 3) {
                this.addSlot(new Slot(tileEntityDispenser2, i4 + i3 * 3, 61 + i4 * 18, 17 + i3 * 18));
                ++i4;
            }
            ++i3;
        }
        i3 = 0;
        while (i3 < 3) {
            i4 = 0;
            while (i4 < 9) {
                this.addSlot(new Slot(iInventory1, i4 + i3 * 9 + 9, 8 + i4 * 18, 84 + i3 * 18));
                ++i4;
            }
            ++i3;
        }
        i3 = 0;
        while (i3 < 9) {
            this.addSlot(new Slot(iInventory1, i3, 8 + i3 * 18, 142));
            ++i3;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return this.field_21149_a.canInteractWith(entityPlayer1);
    }
}

