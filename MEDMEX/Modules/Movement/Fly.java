/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet19;
import org.lwjgl.input.Keyboard;

public class Fly
extends Module {
    public static Fly instance;

    public Fly() {
        super("Fly", "Fly hack", 0, Category.Movement);
        instance = this;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            this.mc.thePlayer.motionY = 0.0;
            if (this.mc.currentScreen != null) {
                return;
            }
            if (Keyboard.isKeyDown((int)57)) {
                Client.sendPacket(new Packet19(this.mc.thePlayer, 3));
                this.mc.thePlayer.motionY += 0.1;
            }
            if (Keyboard.isKeyDown((int)42)) {
                Client.sendPacket(new Packet19(this.mc.thePlayer, 3));
                this.mc.thePlayer.motionY -= 0.1;
            }
        }
    }

    @Override
    public void onEnable() {
    }
}

