/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet28;

public class Glide
extends Module {
    public static Glide instance;

    public Glide() {
        super("Glide", "Fall Slowly", 0, Category.Movement);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            if (this.mc.thePlayer.fallDistance == 0.0f) {
                return;
            }
            this.mc.thePlayer.motionY = -0.15;
        }
        if (e instanceof EventPacket) {
            if (this.mc.thePlayer == null || this.mc.theWorld == null) {
                return;
            }
            EventPacket event = (EventPacket)e;
            if (!(event.getPacket() instanceof Packet28)) {
                return;
            }
            Packet28 p = (Packet28)event.getPacket();
            if (p.entityId != this.mc.thePlayer.entityId) {
                return;
            }
            event.setCancelled(true);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ);
        }
    }

    @Override
    public void onDisable() {
    }
}

