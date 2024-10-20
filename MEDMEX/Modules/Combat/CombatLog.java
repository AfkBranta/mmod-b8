/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Combat;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;

public class CombatLog
extends Module {
    public static CombatLog instance;

    public CombatLog() {
        super("CombatLog", "Log out when low on health", 0, Category.Combat);
        this.addSetting(new Setting("Health", this, 6.0, 1.0, 20.0, true));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventPlayer)) {
            return;
        }
        int health = this.mc.thePlayer.health;
        int minHealth = (int)this.getSet("Health").getValDouble();
        if (health <= minHealth) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.toggle();
        }
    }

    @Override
    public void onDisable() {
    }
}

