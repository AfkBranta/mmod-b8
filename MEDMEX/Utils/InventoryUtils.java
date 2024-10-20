/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;

public class InventoryUtils {
    public static int getHotbarslotBlocks() {
        Minecraft mc = Minecraft.theMinecraft;
        if (mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            return mc.thePlayer.inventory.currentItem;
        }
        int i = 0;
        while (i < 9) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static int getFoodInHotbar() {
        Minecraft mc = Minecraft.theMinecraft;
        int i = 0;
        while (i < 9) {
            ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && is.getItem() instanceof ItemFood) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static int getFoodInInventory() {
        Minecraft mc = Minecraft.theMinecraft;
        int i = 9;
        while (i < 36) {
            ItemStack is = mc.thePlayer.craftingInventory.getSlot(i).getStack();
            if (is != null && is.getItem() instanceof ItemFood) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static int getItemInHotbar(int itemid) {
        Minecraft mc = Minecraft.theMinecraft;
        int i = 0;
        while (i < 9) {
            ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && is.itemID == itemid) {
                return i;
            }
            ++i;
        }
        return -1;
    }
}

