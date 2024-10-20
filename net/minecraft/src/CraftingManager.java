/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.src.Block;
import net.minecraft.src.IRecipe;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RecipeSorter;
import net.minecraft.src.RecipesArmor;
import net.minecraft.src.RecipesCrafting;
import net.minecraft.src.RecipesDyes;
import net.minecraft.src.RecipesFood;
import net.minecraft.src.RecipesIngots;
import net.minecraft.src.RecipesTools;
import net.minecraft.src.RecipesWeapons;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.ShapelessRecipes;

public class CraftingManager {
    private static final CraftingManager instance = new CraftingManager();
    private List recipes = new ArrayList();

    public static final CraftingManager getInstance() {
        return instance;
    }

    private CraftingManager() {
        new RecipesTools().addRecipes(this);
        new RecipesWeapons().addRecipes(this);
        new RecipesIngots().addRecipes(this);
        new RecipesFood().addRecipes(this);
        new RecipesCrafting().addRecipes(this);
        new RecipesArmor().addRecipes(this);
        new RecipesDyes().addRecipes(this);
        this.addRecipe(new ItemStack(Item.paper, 3), "###", Character.valueOf('#'), Item.reed);
        this.addRecipe(new ItemStack(Item.book, 1), "#", "#", "#", Character.valueOf('#'), Item.paper);
        this.addRecipe(new ItemStack(Block.fence, 2), "###", "###", Character.valueOf('#'), Item.stick);
        this.addRecipe(new ItemStack(Block.jukebox, 1), "###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.diamond);
        this.addRecipe(new ItemStack(Block.musicBlock, 1), "###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.redstone);
        this.addRecipe(new ItemStack(Block.bookShelf, 1), "###", "XXX", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.book);
        this.addRecipe(new ItemStack(Block.blockSnow, 1), "##", "##", Character.valueOf('#'), Item.snowball);
        this.addRecipe(new ItemStack(Block.blockClay, 1), "##", "##", Character.valueOf('#'), Item.clay);
        this.addRecipe(new ItemStack(Block.brick, 1), "##", "##", Character.valueOf('#'), Item.brick);
        this.addRecipe(new ItemStack(Block.lightStone, 1), "###", "###", "###", Character.valueOf('#'), Item.lightStoneDust);
        this.addRecipe(new ItemStack(Block.cloth, 1), "###", "###", "###", Character.valueOf('#'), Item.silk);
        this.addRecipe(new ItemStack(Block.tnt, 1), "X#X", "#X#", "X#X", Character.valueOf('X'), Item.gunpowder, Character.valueOf('#'), Block.sand);
        this.addRecipe(new ItemStack(Block.stairSingle, 3, 3), "###", Character.valueOf('#'), Block.cobblestone);
        this.addRecipe(new ItemStack(Block.stairSingle, 3, 0), "###", Character.valueOf('#'), Block.stone);
        this.addRecipe(new ItemStack(Block.stairSingle, 3, 1), "###", Character.valueOf('#'), Block.sandStone);
        this.addRecipe(new ItemStack(Block.stairSingle, 3, 2), "###", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Block.ladder, 1), "# #", "###", "# #", Character.valueOf('#'), Item.stick);
        this.addRecipe(new ItemStack(Item.doorWood, 1), "##", "##", "##", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Item.doorSteel, 1), "##", "##", "##", Character.valueOf('#'), Item.ingotIron);
        this.addRecipe(new ItemStack(Item.sign, 1), "###", "###", " X ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.stick);
        this.addRecipe(new ItemStack(Item.cake, 1), "AAA", "BEB", "CCC", Character.valueOf('A'), Item.bucketMilk, Character.valueOf('B'), Item.sugar, Character.valueOf('C'), Item.wheat, Character.valueOf('E'), Item.egg);
        this.addRecipe(new ItemStack(Item.sugar, 1), "#", Character.valueOf('#'), Item.reed);
        this.addRecipe(new ItemStack(Block.planks, 4), "#", Character.valueOf('#'), Block.wood);
        this.addRecipe(new ItemStack(Item.stick, 4), "#", "#", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Block.torchWood, 4), "X", "#", Character.valueOf('X'), Item.coal, Character.valueOf('#'), Item.stick);
        this.addRecipe(new ItemStack(Block.torchWood, 4), "X", "#", Character.valueOf('X'), new ItemStack(Item.coal, 1, 1), Character.valueOf('#'), Item.stick);
        this.addRecipe(new ItemStack(Item.bowlEmpty, 4), "# #", " # ", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Block.minecartTrack, 16), "X X", "X#X", "X X", Character.valueOf('X'), Item.ingotIron, Character.valueOf('#'), Item.stick);
        this.addRecipe(new ItemStack(Item.minecartEmpty, 1), "# #", "###", Character.valueOf('#'), Item.ingotIron);
        this.addRecipe(new ItemStack(Block.pumpkinLantern, 1), "A", "B", Character.valueOf('A'), Block.pumpkin, Character.valueOf('B'), Block.torchWood);
        this.addRecipe(new ItemStack(Item.minecartCrate, 1), "A", "B", Character.valueOf('A'), Block.crate, Character.valueOf('B'), Item.minecartEmpty);
        this.addRecipe(new ItemStack(Item.minecartPowered, 1), "A", "B", Character.valueOf('A'), Block.stoneOvenIdle, Character.valueOf('B'), Item.minecartEmpty);
        this.addRecipe(new ItemStack(Item.boat, 1), "# #", "###", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Item.bucketEmpty, 1), "# #", " # ", Character.valueOf('#'), Item.ingotIron);
        this.addRecipe(new ItemStack(Item.flintAndSteel, 1), "A ", " B", Character.valueOf('A'), Item.ingotIron, Character.valueOf('B'), Item.flint);
        this.addRecipe(new ItemStack(Item.bread, 1), "###", Character.valueOf('#'), Item.wheat);
        this.addRecipe(new ItemStack(Block.stairCompactPlanks, 4), "#  ", "## ", "###", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Item.fishingRod, 1), "  #", " #X", "# X", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.silk);
        this.addRecipe(new ItemStack(Block.stairCompactCobblestone, 4), "#  ", "## ", "###", Character.valueOf('#'), Block.cobblestone);
        this.addRecipe(new ItemStack(Item.painting, 1), "###", "#X#", "###", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Block.cloth);
        this.addRecipe(new ItemStack(Item.appleGold, 1), "###", "#X#", "###", Character.valueOf('#'), Block.blockGold, Character.valueOf('X'), Item.appleRed);
        this.addRecipe(new ItemStack(Block.lever, 1), "X", "#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick);
        this.addRecipe(new ItemStack(Block.torchRedstoneActive, 1), "X", "#", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.redstone);
        this.addRecipe(new ItemStack(Item.redstoneRepeater, 1), "#X#", "III", Character.valueOf('#'), Block.torchRedstoneActive, Character.valueOf('X'), Item.redstone, Character.valueOf('I'), Block.stone);
        this.addRecipe(new ItemStack(Item.pocketSundial, 1), " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotGold, Character.valueOf('X'), Item.redstone);
        this.addRecipe(new ItemStack(Item.compass, 1), " # ", "#X#", " # ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.redstone);
        this.addRecipe(new ItemStack(Block.button, 1), "#", "#", Character.valueOf('#'), Block.stone);
        this.addRecipe(new ItemStack(Block.pressurePlateStone, 1), "##", Character.valueOf('#'), Block.stone);
        this.addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), "##", Character.valueOf('#'), Block.planks);
        this.addRecipe(new ItemStack(Block.dispenser, 1), "###", "#X#", "#R#", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.bow, Character.valueOf('R'), Item.redstone);
        this.addRecipe(new ItemStack(Item.bed, 1), "###", "XXX", Character.valueOf('#'), Block.cloth, Character.valueOf('X'), Block.planks);
        Collections.sort(this.recipes, new RecipeSorter(this));
        System.out.println(String.valueOf(this.recipes.size()) + " recipes");
    }

    /*
     * Unable to fully structure code
     */
    void addRecipe(ItemStack itemStack1, Object ... object2) {
        block9: {
            string3 = "";
            i4 = 0;
            i5 = 0;
            i6 = 0;
            if (!(object2[i4] instanceof String[])) ** GOTO lbl20
            string11 = (String[])object2[i4++];
            i8 = 0;
            while (i8 < string11.length) {
                string9 = string11[i8];
                ++i6;
                i5 = string9.length();
                string3 = String.valueOf(string3) + string9;
                ++i8;
            }
            break block9;
lbl-1000:
            // 1 sources

            {
                string7 = (String)object2[i4++];
                ++i6;
                i5 = string7.length();
                string3 = String.valueOf(string3) + string7;
lbl20:
                // 2 sources

                ** while (object2[i4] instanceof String)
            }
        }
        hashMap12 = new HashMap<Character, ItemStack>();
        while (i4 < object2.length) {
            character13 = (Character)object2[i4];
            itemStack15 = null;
            if (object2[i4 + 1] instanceof Item) {
                itemStack15 = new ItemStack((Item)object2[i4 + 1]);
            } else if (object2[i4 + 1] instanceof Block) {
                itemStack15 = new ItemStack((Block)object2[i4 + 1], 1, -1);
            } else if (object2[i4 + 1] instanceof ItemStack) {
                itemStack15 = (ItemStack)object2[i4 + 1];
            }
            hashMap12.put(character13, itemStack15);
            i4 += 2;
        }
        itemStack14 = new ItemStack[i5 * i6];
        i16 = 0;
        while (i16 < i5 * i6) {
            c10 = string3.charAt(i16);
            itemStack14[i16] = hashMap12.containsKey(Character.valueOf(c10)) != false ? ((ItemStack)hashMap12.get(Character.valueOf(c10))).copy() : null;
            ++i16;
        }
        this.recipes.add(new ShapedRecipes(i5, i6, itemStack14, itemStack1));
    }

    void addShapelessRecipe(ItemStack itemStack1, Object ... object2) {
        ArrayList<ItemStack> arrayList3 = new ArrayList<ItemStack>();
        Object[] object4 = object2;
        int i5 = object2.length;
        int i6 = 0;
        while (i6 < i5) {
            Object object7 = object4[i6];
            if (object7 instanceof ItemStack) {
                arrayList3.add(((ItemStack)object7).copy());
            } else if (object7 instanceof Item) {
                arrayList3.add(new ItemStack((Item)object7));
            } else {
                if (!(object7 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }
                arrayList3.add(new ItemStack((Block)object7));
            }
            ++i6;
        }
        this.recipes.add(new ShapelessRecipes(itemStack1, arrayList3));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting inventoryCrafting1) {
        int i2 = 0;
        while (i2 < this.recipes.size()) {
            IRecipe iRecipe3 = (IRecipe)this.recipes.get(i2);
            if (iRecipe3.matches(inventoryCrafting1)) {
                return iRecipe3.getCraftingResult(inventoryCrafting1);
            }
            ++i2;
        }
        return null;
    }

    public List func_25193_b() {
        return this.recipes;
    }
}

