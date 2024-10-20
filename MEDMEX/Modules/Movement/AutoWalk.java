/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super("AutoWalk", "Automatically move forward", 0, Category.Movement);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            this.mc.thePlayer.moveEntityWithHeading(0.0f, 0.98f);
        }
    }

    @Override
    public void onEnable() {
    }
}

