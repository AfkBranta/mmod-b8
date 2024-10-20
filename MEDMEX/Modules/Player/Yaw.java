/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventMoveFlying;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;

public class Yaw
extends Module {
    public static Yaw instance;

    public Yaw() {
        super("Yaw", "Yaw Lock", 0, Category.Player);
        this.addSetting(new Setting("Yaw", this, 0.0, 0.0, 360.0, true));
        this.addSetting(new Setting("Silent", (Module)this, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMoveFlying) {
            boolean silent = this.getSet("Silent").getValBoolean();
            int yaw = (int)this.getSet("Yaw").getValDouble();
            if (silent) {
                EventMoveFlying event = (EventMoveFlying)e;
                event.setYaw(yaw);
            } else {
                this.mc.thePlayer.rotationYaw = yaw;
            }
        }
    }

    @Override
    public void onDisable() {
    }
}

