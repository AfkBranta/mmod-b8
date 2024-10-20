/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventBreakBlock;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.Vec3D;
import java.util.ArrayList;

public class SpeedMine
extends Module {
    public static SpeedMine instance;

    public SpeedMine() {
        super("SpeedMine", "Mine Quicker", 0, Category.Player);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Packet");
        options.add("Speed");
        this.addSetting(new Setting("Mode", (Module)this, "Packet", options));
        this.addSetting(new Setting("Speed", this, 1.0, 1.0, 5.0, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventBreakBlock && this.getSet("Mode").getValString().equalsIgnoreCase("Packet")) {
            EventBreakBlock event = (EventBreakBlock)e;
            PlayerUtils.breakBlockPacket(new Vec3D(event.getX(), event.getY(), event.getZ()));
        }
    }

    @Override
    public void onDisable() {
    }
}

