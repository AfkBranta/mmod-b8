/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class ShapedRecipes
implements IRecipe {
    private int recipeWidth;
    private int recipeHeight;
    private ItemStack[] recipeItems;
    private ItemStack recipeOutput;
    public final int recipeOutputItemID;

    public ShapedRecipes(int i1, int i2, ItemStack[] itemStack3, ItemStack itemStack4) {
        this.recipeOutputItemID = itemStack4.itemID;
        this.recipeWidth = i1;
        this.recipeHeight = i2;
        this.recipeItems = itemStack3;
        this.recipeOutput = itemStack4;
    }

    @Override
    public ItemStack func_25117_b() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting1) {
        int i2 = 0;
        while (i2 <= 3 - this.recipeWidth) {
            int i3 = 0;
            while (i3 <= 3 - this.recipeHeight) {
                if (this.func_21137_a(inventoryCrafting1, i2, i3, true)) {
                    return true;
                }
                if (this.func_21137_a(inventoryCrafting1, i2, i3, false)) {
                    return true;
                }
                ++i3;
            }
            ++i2;
        }
        return false;
    }

    private boolean func_21137_a(InventoryCrafting inventoryCrafting1, int i2, int i3, boolean z4) {
        int i5 = 0;
        while (i5 < 3) {
            int i6 = 0;
            while (i6 < 3) {
                ItemStack itemStack10;
                int i7 = i5 - i2;
                int i8 = i6 - i3;
                ItemStack itemStack9 = null;
                if (i7 >= 0 && i8 >= 0 && i7 < this.recipeWidth && i8 < this.recipeHeight) {
                    itemStack9 = z4 ? this.recipeItems[this.recipeWidth - i7 - 1 + i8 * this.recipeWidth] : this.recipeItems[i7 + i8 * this.recipeWidth];
                }
                if ((itemStack10 = inventoryCrafting1.func_21103_b(i5, i6)) != null || itemStack9 != null) {
                    if (itemStack10 == null && itemStack9 != null || itemStack10 != null && itemStack9 == null) {
                        return false;
                    }
                    if (itemStack9.itemID != itemStack10.itemID) {
                        return false;
                    }
                    if (itemStack9.getItemDamage() != -1 && itemStack9.getItemDamage() != itemStack10.getItemDamage()) {
                        return false;
                    }
                }
                ++i6;
            }
            ++i5;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting1) {
        return new ItemStack(this.recipeOutput.itemID, this.recipeOutput.stackSize, this.recipeOutput.getItemDamage());
    }

    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
}

