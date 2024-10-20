/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.EventBound;
import MEDMEX.Events.events.EventMoveEntity;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.Timer;
import java.util.ArrayList;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet19;

public class Speed
extends Module {
    public static Speed instance;
    public Timer timer = new Timer();

    public Speed() {
        super("Speedy Gonzales", "Go quick like speedy gonzales", 0, Category.Movement);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Normal");
        options.add("Boost");
        options.add("NoCheat");
        this.addSetting(new Setting("Mode", (Module)this, "Normal", options));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMoveEntity && this.getSet("Mode").getValString().equalsIgnoreCase("Normal")) {
            float strafe = this.mc.thePlayer.movementInput.moveStrafe;
            float forward = this.mc.thePlayer.movementInput.moveForward;
            if (strafe != 0.0f && forward != 0.0f) {
                return;
            }
            EventMoveEntity event = (EventMoveEntity)e;
            event.setMotionX(event.getMotionX() * 1.02);
            event.setMotionZ(event.getMotionZ() * 1.02);
        }
        if (e instanceof EventPlayer) {
            if (this.getSet("Mode").getValString().equalsIgnoreCase("Normal")) {
                EventPlayer event = (EventPlayer)e;
                event.setYaw(this.mc.thePlayer.rotationYaw - 45.0f);
            }
            if (this.getSet("Mode").getValString().equalsIgnoreCase("NoCheat") && this.timer.hasTimeElapsed(450L, true)) {
                Client.sendPacket(new Packet19(this.mc.thePlayer, 3));
            }
        }
        if (e instanceof EventPacket) {
            if (this.mc.thePlayer == null) {
                return;
            }
            if (this.getSet("Mode").getValString().equalsIgnoreCase("Normal") || this.getSet("Mode").getValString().equalsIgnoreCase("NoCheat")) {
                EventPacket event = (EventPacket)e;
                if (!(event.getPacket() instanceof Packet13PlayerLookMove)) {
                    return;
                }
                if (event.getBound() != EventBound.IN) {
                    return;
                }
                Packet13PlayerLookMove p = (Packet13PlayerLookMove)event.getPacket();
                p.yaw = this.mc.thePlayer.rotationYaw;
            }
            if (this.getSet("Mode").getValString().equalsIgnoreCase("Boost")) {
                EventPacket event = (EventPacket)e;
                if (!(event.getPacket() instanceof Packet13PlayerLookMove)) {
                    return;
                }
                if (event.getBound() != EventBound.IN) {
                    return;
                }
                Packet13PlayerLookMove p = (Packet13PlayerLookMove)event.getPacket();
                this.toggle();
            }
        }
    }

    @Override
    public void onDisable() {
        if (this.getSet("Mode").getValString().equalsIgnoreCase("NoCheat")) {
            Client.sendPacket(new Packet19(this.mc.thePlayer, 3));
        }
    }
}

