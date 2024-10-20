/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.Modules.Player;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import net.minecraft.src.GuiCrafting;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet102;
import org.lwjgl.input.Keyboard;

public class Inventory
extends Module {
    public static Inventory instance;

    public Inventory() {
        super("Inventory+", "Inventory Utilities", 0, Category.Player);
        this.addSetting(new Setting("Shift Click", (Module)this, true));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            if (!this.getSet("Shift Click").getValBoolean()) {
                return;
            }
            EventPacket ep = (EventPacket)e;
            if (!(ep.getPacket() instanceof Packet102)) {
                return;
            }
            if (!Keyboard.isKeyDown((int)42)) {
                return;
            }
            int slot = -1;
            Packet102 p = (Packet102)ep.getPacket();
            try {
                slot = this.findPossibleSlot(p);
            }
            catch (Exception ex) {
                return;
            }
            if (slot == -1) {
                return;
            }
            Client.sendPacketNoEvent(new Packet102(p.window_Id, slot, 0, null, (short)(p.action + 1)));
            this.mc.thePlayer.craftingInventory.putStackInSlot(slot, p.itemStack);
            this.mc.thePlayer.inventory.setItemStack(null);
        }
    }

    public int findPossibleSlot(Packet102 p) {
        ItemStack is;
        int i;
        int slots = this.mc.thePlayer.craftingInventory.field_20123_d.size();
        if (p.inventorySlot >= slots - 36) {
            i = 0;
            while (i < slots - 36) {
                if (i != 0 || !(this.mc.currentScreen instanceof GuiCrafting) && !(this.mc.currentScreen instanceof GuiInventory)) {
                    is = this.mc.thePlayer.craftingInventory.getSlot(i).getStack();
                    if (is == null) {
                        return i;
                    }
                    if (is.getItem() == p.itemStack.getItem() && is.stackSize + p.itemStack.stackSize <= is.getMaxStackSize() && is.getItemDamage() == p.itemStack.getItemDamage()) {
                        return i;
                    }
                }
                ++i;
            }
        }
        if (p.inventorySlot < slots - 36) {
            i = slots - 1;
            while (i >= slots - 36) {
                is = this.mc.thePlayer.craftingInventory.getSlot(i).getStack();
                if (is == null) {
                    return i;
                }
                if (is.getItem() == p.itemStack.getItem() && is.stackSize + p.itemStack.stackSize <= is.getMaxStackSize() && is.getItemDamage() == p.itemStack.getItemDamage()) {
                    return i;
                }
                --i;
            }
        }
        return -1;
    }

    @Override
    public void onDisable() {
    }
}

