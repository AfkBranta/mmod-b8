/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesIngots {
    private Object[][] recipeItems = new Object[][]{{Block.blockGold, new ItemStack(Item.ingotGold, 9)}, {Block.blockSteel, new ItemStack(Item.ingotIron, 9)}, {Block.blockDiamond, new ItemStack(Item.diamond, 9)}, {Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4)}};

    public void addRecipes(CraftingManager craftingManager1) {
        int i2 = 0;
        while (i2 < this.recipeItems.length) {
            Block block3 = (Block)this.recipeItems[i2][0];
            ItemStack itemStack4 = (ItemStack)this.recipeItems[i2][1];
            craftingManager1.addRecipe(new ItemStack(block3), "###", "###", "###", Character.valueOf('#'), itemStack4);
            craftingManager1.addRecipe(itemStack4, "#", Character.valueOf('#'), block3);
            ++i2;
        }
    }
}

