/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Combat;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet28;

public class Velocity
extends Module {
    public static Velocity instance;

    public Velocity() {
        super("Velocity", "Don't take knockback", 0, Category.Combat);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
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
        }
    }

    @Override
    public void onDisable() {
    }
}

