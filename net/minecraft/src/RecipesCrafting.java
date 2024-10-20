/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.ItemStack;

public class RecipesCrafting {
    public void addRecipes(CraftingManager craftingManager1) {
        craftingManager1.addRecipe(new ItemStack(Block.crate), "###", "# #", "###", Character.valueOf('#'), Block.planks);
        craftingManager1.addRecipe(new ItemStack(Block.stoneOvenIdle), "###", "# #", "###", Character.valueOf('#'), Block.cobblestone);
        craftingManager1.addRecipe(new ItemStack(Block.workbench), "##", "##", Character.valueOf('#'), Block.planks);
        craftingManager1.addRecipe(new ItemStack(Block.sandStone), "##", "##", Character.valueOf('#'), Block.sand);
    }
}

