/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;

public class Noclip
extends Module {
    public Noclip() {
        super("Noclip", "Clip through walls", 0, Category.Movement);
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.noClip = false;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            this.mc.thePlayer.noClip = true;
        }
    }

    @Override
    public void onEnable() {
    }
}

