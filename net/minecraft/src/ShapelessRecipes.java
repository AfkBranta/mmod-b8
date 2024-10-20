/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.ItemStack;

public class ShapelessRecipes
implements IRecipe {
    private final ItemStack recipeOutput;
    private final List recipeItems;

    public ShapelessRecipes(ItemStack itemStack1, List list2) {
        this.recipeOutput = itemStack1;
        this.recipeItems = list2;
    }

    @Override
    public ItemStack func_25117_b() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting1) {
        ArrayList arrayList2 = new ArrayList(this.recipeItems);
        int i3 = 0;
        while (i3 < 3) {
            int i4 = 0;
            while (i4 < 3) {
                ItemStack itemStack5 = inventoryCrafting1.func_21103_b(i4, i3);
                if (itemStack5 != null) {
                    boolean z6 = false;
                    for (ItemStack itemStack8 : arrayList2) {
                        if (itemStack5.itemID != itemStack8.itemID || itemStack8.getItemDamage() != -1 && itemStack5.getItemDamage() != itemStack8.getItemDamage()) continue;
                        z6 = true;
                        arrayList2.remove(itemStack8);
                        break;
                    }
                    if (!z6) {
                        return false;
                    }
                }
                ++i4;
            }
            ++i3;
        }
        return arrayList2.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting1) {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }
}

