/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Combat;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet102;

public class AutoHeal
extends Module {
    public static AutoHeal instance;
    int delay = 0;
    int syncslot = -1;
    ItemStack syncis = null;

    public AutoHeal() {
        super("AutoHeal", "Eats food automatically", 0, Category.Combat);
        this.addSetting(new Setting("Health", this, 10.0, 1.0, 19.0, true));
        this.addSetting(new Setting("Refill", (Module)this, true));
        instance = this;
    }

    @Override
    public void onEnable() {
        this.reset();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            if (this.delay > 0) {
                if (this.delay == 1 && this.syncslot != 1) {
                    this.mc.thePlayer.craftingInventory.putStackInSlot(this.syncslot + 36, this.syncis);
                    this.syncslot = -1;
                    this.syncis = null;
                }
                --this.delay;
                return;
            }
            if ((double)this.mc.thePlayer.health > this.getSet("Health").getValDouble()) {
                return;
            }
            int slot = InventoryUtils.getFoodInHotbar();
            if (slot == -1) {
                return;
            }
            int oldSlot = this.mc.thePlayer.inventory.currentItem;
            this.mc.thePlayer.inventory.currentItem = slot;
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
            this.delay = 10;
            this.mc.thePlayer.inventory.currentItem = oldSlot;
            if (!this.getSet("Refill").getValBoolean()) {
                return;
            }
            int foodSlot = InventoryUtils.getFoodInInventory();
            if (foodSlot == -1) {
                return;
            }
            short nextAction = this.mc.thePlayer.craftingInventory.func_20111_a(this.mc.thePlayer.inventory);
            ItemStack is = this.mc.thePlayer.craftingInventory.getSlot(foodSlot).getStack();
            this.syncslot = slot;
            this.syncis = is;
            Client.sendPacketNoEvent(new Packet102(0, foodSlot, 0, is, nextAction));
            Client.sendPacketNoEvent(new Packet102(0, slot + 36, 0, null, (short)(nextAction + 1)));
        }
    }

    public void reset() {
        this.delay = 0;
    }

    @Override
    public void onDisable() {
        this.reset();
    }
}

