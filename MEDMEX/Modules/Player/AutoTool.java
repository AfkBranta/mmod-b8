/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.Block;
import net.minecraft.src.Packet14BlockDig;

public class AutoTool
extends Module {
    public static AutoTool instance;

    public AutoTool() {
        super("AutoTool", "Automatically swap to best tool when digging", 0, Category.Player);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventPacket)) {
            return;
        }
        EventPacket ep = (EventPacket)e;
        if (!(ep.getPacket() instanceof Packet14BlockDig)) {
            return;
        }
        Packet14BlockDig p = (Packet14BlockDig)ep.getPacket();
        int blockid = this.mc.theWorld.getBlockId(p.xPosition, p.yPosition, p.zPosition);
        Block b = Block.blocksList[blockid];
        float[] strs = new float[9];
        int i = 0;
        while (i < 9) {
            float str;
            strs[i] = this.mc.thePlayer.inventory.getStackInSlot(i) == null ? 0.0f : (str = this.mc.thePlayer.inventory.getStackInSlot(i).getStrVsBlock(b));
            ++i;
        }
        int max = 0;
        boolean found = false;
        int i2 = 0;
        while (i2 < 9) {
            if (strs[i2] > strs[max]) {
                max = i2;
                found = true;
            }
            ++i2;
        }
        if (found) {
            this.mc.thePlayer.inventory.currentItem = max;
        }
    }

    @Override
    public void onDisable() {
    }
}

