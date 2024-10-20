/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotFurnace;
import net.minecraft.src.TileEntityFurnace;

public class CraftingInventoryFurnaceCB
extends CraftingInventoryCB {
    private TileEntityFurnace furnace;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public CraftingInventoryFurnaceCB(IInventory iInventory1, TileEntityFurnace tileEntityFurnace2) {
        this.furnace = tileEntityFurnace2;
        this.addSlot(new Slot(tileEntityFurnace2, 0, 56, 17));
        this.addSlot(new Slot(tileEntityFurnace2, 1, 56, 53));
        this.addSlot(new SlotFurnace(tileEntityFurnace2, 2, 116, 35));
        int i3 = 0;
        while (i3 < 3) {
            int i4 = 0;
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
    public void updateCraftingResults() {
        super.updateCraftingResults();
        int i1 = 0;
        while (i1 < this.field_20121_g.size()) {
            ICrafting iCrafting2 = (ICrafting)this.field_20121_g.get(i1);
            if (this.cookTime != this.furnace.furnaceCookTime) {
                iCrafting2.func_20158_a(this, 0, this.furnace.furnaceCookTime);
            }
            if (this.burnTime != this.furnace.furnaceBurnTime) {
                iCrafting2.func_20158_a(this, 1, this.furnace.furnaceBurnTime);
            }
            if (this.itemBurnTime != this.furnace.currentItemBurnTime) {
                iCrafting2.func_20158_a(this, 2, this.furnace.currentItemBurnTime);
            }
            ++i1;
        }
        this.cookTime = this.furnace.furnaceCookTime;
        this.burnTime = this.furnace.furnaceBurnTime;
        this.itemBurnTime = this.furnace.currentItemBurnTime;
    }

    @Override
    public void func_20112_a(int i1, int i2) {
        if (i1 == 0) {
            this.furnace.furnaceCookTime = i2;
        }
        if (i1 == 1) {
            this.furnace.furnaceBurnTime = i2;
        }
        if (i1 == 2) {
            this.furnace.currentItemBurnTime = i2;
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return this.furnace.canInteractWith(entityPlayer1);
    }
}

