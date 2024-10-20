/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesTools {
    private String[][] recipePatterns = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
    private Object[][] recipeItems = new Object[][]{{Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeSteel, Item.pickaxeDiamond, Item.pickaxeGold}, {Item.shovelWood, Item.shovelStone, Item.shovelSteel, Item.shovelDiamond, Item.shovelGold}, {Item.axeWood, Item.axeStone, Item.axeSteel, Item.axeDiamond, Item.axeGold}, {Item.hoeWood, Item.hoeStone, Item.hoeSteel, Item.hoeDiamond, Item.hoeGold}};

    public void addRecipes(CraftingManager craftingManager1) {
        int i2 = 0;
        while (i2 < this.recipeItems[0].length) {
            Object object3 = this.recipeItems[0][i2];
            int i4 = 0;
            while (i4 < this.recipeItems.length - 1) {
                Item item5 = (Item)this.recipeItems[i4 + 1][i2];
                craftingManager1.addRecipe(new ItemStack(item5), this.recipePatterns[i4], Character.valueOf('#'), Item.stick, Character.valueOf('X'), object3);
                ++i4;
            }
            ++i2;
        }
    }
}

