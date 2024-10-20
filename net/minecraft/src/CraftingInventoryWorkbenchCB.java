/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import net.minecraft.src.World;

public class CraftingInventoryWorkbenchCB
extends CraftingInventoryCB {
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World field_20133_c;
    private int field_20132_h;
    private int field_20131_i;
    private int field_20130_j;

    public CraftingInventoryWorkbenchCB(InventoryPlayer inventoryPlayer1, World world2, int i3, int i4, int i5) {
        int i7;
        this.field_20133_c = world2;
        this.field_20132_h = i3;
        this.field_20131_i = i4;
        this.field_20130_j = i5;
        this.addSlot(new SlotCrafting(inventoryPlayer1.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        int i6 = 0;
        while (i6 < 3) {
            i7 = 0;
            while (i7 < 3) {
                this.addSlot(new Slot(this.craftMatrix, i7 + i6 * 3, 30 + i7 * 18, 17 + i6 * 18));
                ++i7;
            }
            ++i6;
        }
        i6 = 0;
        while (i6 < 3) {
            i7 = 0;
            while (i7 < 9) {
                this.addSlot(new Slot(inventoryPlayer1, i7 + i6 * 9 + 9, 8 + i7 * 18, 84 + i6 * 18));
                ++i7;
            }
            ++i6;
        }
        i6 = 0;
        while (i6 < 9) {
            this.addSlot(new Slot(inventoryPlayer1, i6, 8 + i6 * 18, 142));
            ++i6;
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
        while (i2 < 9) {
            ItemStack itemStack3 = this.craftMatrix.getStackInSlot(i2);
            if (itemStack3 != null) {
                entityPlayer1.dropPlayerItem(itemStack3);
            }
            ++i2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return this.field_20133_c.getBlockId(this.field_20132_h, this.field_20131_i, this.field_20130_j) != Block.workbench.blockID ? false : entityPlayer1.getDistanceSq((double)this.field_20132_h + 0.5, (double)this.field_20131_i + 0.5, (double)this.field_20130_j + 0.5) <= 64.0;
    }
}

