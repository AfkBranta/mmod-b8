/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class RecipesArmor {
    private String[][] recipePatterns = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] recipeItems = new Object[][]{{Item.leather, Block.fire, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.helmetLeather, Item.helmetChain, Item.helmetSteel, Item.helmetDiamond, Item.helmetGold}, {Item.plateLeather, Item.plateChain, Item.plateSteel, Item.plateDiamond, Item.plateGold}, {Item.legsLeather, Item.legsChain, Item.legsSteel, Item.legsDiamond, Item.legsGold}, {Item.bootsLeather, Item.bootsChain, Item.bootsSteel, Item.bootsDiamond, Item.bootsGold}};

    public void addRecipes(CraftingManager craftingManager1) {
        int i2 = 0;
        while (i2 < this.recipeItems[0].length) {
            Object object3 = this.recipeItems[0][i2];
            int i4 = 0;
            while (i4 < this.recipeItems.length - 1) {
                Item item5 = (Item)this.recipeItems[i4 + 1][i2];
                craftingManager1.addRecipe(new ItemStack(item5), this.recipePatterns[i4], Character.valueOf('X'), object3);
                ++i4;
            }
            ++i2;
        }
    }
}

