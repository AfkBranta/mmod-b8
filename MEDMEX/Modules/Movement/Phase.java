/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;

public class Phase
extends Module {
    public static Phase instance;

    public Phase() {
        super("Phase", "Walk through walls", 0, Category.Movement);
        instance = this;
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }

    @Override
    public void onEvent(Event e) {
        EventPacket event;
        if (e instanceof EventPlayer) {
            double multiplier = 0.3;
            double mx = -Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw));
            double mz = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw));
            double x = (double)this.mc.thePlayer.movementInput.moveForward * multiplier * mx + (double)this.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            double z = (double)this.mc.thePlayer.movementInput.moveForward * multiplier * mz - (double)this.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            if (this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isOnLadder()) {
                Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.boundingBox.minY, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z, false));
                int i = 1;
                while (i < 10) {
                    Client.sendPacket(new Packet11PlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.boundingBox.minY - 8.988465674311579E307, this.mc.thePlayer.posY - 8.988465674311579E307, this.mc.thePlayer.posZ, false));
                    ++i;
                }
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z);
                double moveSpeed = MathHelper.sqrt_double(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ);
                float forward = this.mc.thePlayer.movementInput.moveForward;
                float strafe = this.mc.thePlayer.movementInput.moveStrafe;
                float yaw = this.mc.thePlayer.rotationYaw;
                if (forward == 0.0f && strafe == 0.0f) {
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                } else if (forward != 0.0f) {
                    if (strafe >= 1.0f) {
                        yaw += (float)(forward > 0.0f ? -45 : 45);
                        strafe = 0.0f;
                    } else if (strafe <= -1.0f) {
                        yaw += (float)(forward > 0.0f ? 45 : -45);
                        strafe = 0.0f;
                    }
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
                this.mc.thePlayer.motionX = (double)forward * moveSpeed * mx + (double)strafe * moveSpeed * mz;
                this.mc.thePlayer.motionZ = (double)forward * moveSpeed * mz - (double)strafe * moveSpeed * mx;
                this.mc.timer.timerSpeed = 1.0888f;
            }
        }
        if (e instanceof EventPacket && (event = (EventPacket)e).getPacket() instanceof Packet12PlayerLook) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onEnable() {
    }
}

