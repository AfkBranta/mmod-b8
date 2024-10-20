/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Comparator;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.ShapelessRecipes;

class RecipeSorter
implements Comparator {
    final CraftingManager craftingManager;

    RecipeSorter(CraftingManager craftingManager1) {
        this.craftingManager = craftingManager1;
    }

    public int compareRecipes(IRecipe iRecipe1, IRecipe iRecipe2) {
        return iRecipe1 instanceof ShapelessRecipes && iRecipe2 instanceof ShapedRecipes ? 1 : (iRecipe2 instanceof ShapelessRecipes && iRecipe1 instanceof ShapedRecipes ? -1 : (iRecipe2.getRecipeSize() < iRecipe1.getRecipeSize() ? -1 : (iRecipe2.getRecipeSize() > iRecipe1.getRecipeSize() ? 1 : 0)));
    }

    public int compare(Object object1, Object object2) {
        return this.compareRecipes((IRecipe)object1, (IRecipe)object2);
    }
}

