/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotArmor;
import net.minecraft.src.SlotCrafting;

public class CraftingInventoryPlayerCB
extends CraftingInventoryCB {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public IInventory craftResult = new InventoryCraftResult();
    public boolean isSinglePlayer = false;

    public CraftingInventoryPlayerCB(InventoryPlayer inventoryPlayer1) {
        this(inventoryPlayer1, true);
    }

    public CraftingInventoryPlayerCB(InventoryPlayer inventoryPlayer1, boolean z2) {
        int i4;
        this.isSinglePlayer = z2;
        this.addSlot(new SlotCrafting(inventoryPlayer1.player, this.craftMatrix, this.craftResult, 0, 144, 36));
        int i3 = 0;
        while (i3 < 2) {
            i4 = 0;
            while (i4 < 2) {
                this.addSlot(new Slot(this.craftMatrix, i4 + i3 * 2, 88 + i4 * 18, 26 + i3 * 18));
                ++i4;
            }
            ++i3;
        }
        i3 = 0;
        while (i3 < 4) {
            this.addSlot(new SlotArmor(this, inventoryPlayer1, inventoryPlayer1.getSizeInventory() - 1 - i3, 8, 8 + i3 * 18, i3));
            ++i3;
        }
        i3 = 0;
        while (i3 < 3) {
            i4 = 0;
            while (i4 < 9) {
                this.addSlot(new Slot(inventoryPlayer1, i4 + (i3 + 1) * 9, 8 + i4 * 18, 84 + i3 * 18));
                ++i4;
            }
            ++i3;
        }
        i3 = 0;
        while (i3 < 9) {
            this.addSlot(new Slot(inventoryPlayer1, i3, 8 + i3 * 18, 142));
            ++i3;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory1) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix));
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer entityPlayer1) {
        super.onCraftGuiClosed(entityPlayer1);
        int i2 = 0;
        while (i2 < 4) {
            ItemStack itemStack3 = this.craftMatrix.getStackInSlot(i2);
            if (itemStack3 != null) {
                entityPlayer1.dropPlayerItem(itemStack3);
                this.craftMatrix.setInventorySlotContents(i2, null);
            }
            ++i2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}

