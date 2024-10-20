/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.Modules.Player;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventTick;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import org.lwjgl.input.Keyboard;

public class Freecam
extends Module {
    public static Freecam instance;
    public static double x;
    public static double y;
    public static double z;
    Entity ent;

    public Freecam() {
        super("Freecam", "Move around freely", 0, Category.Player);
        this.addSetting(new Setting("Speed", this, 1.0, 1.0, 5.0, false));
        this.addSetting(new Setting("Fake Player", (Module)this, true));
        instance = this;
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.setPosition(x, y, z);
        if (this.ent != null) {
            this.mc.theWorld.setEntityDead(this.ent);
        }
        this.mc.thePlayer.noClip = false;
    }

    @Override
    public void onEnable() {
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            if (this.getSet("Fake Player").getValBoolean()) {
                EntityOtherPlayerMP entity = new EntityOtherPlayerMP(this.mc.theWorld, "freecam");
                entity.copyDataFrom(this.mc.thePlayer, true);
                entity.posY -= (double)this.mc.thePlayer.yOffset;
                this.ent = entity;
                this.mc.theWorld.entityJoinedWorld(this.ent);
            }
            x = this.mc.thePlayer.posX;
            y = this.mc.thePlayer.posY;
            z = this.mc.thePlayer.posZ;
        }
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            EventPacket event = (EventPacket)e;
            if (event.getPacket() instanceof Packet10Flying) {
                e.setCancelled(true);
            }
            if (event.getPacket() instanceof Packet11PlayerPosition) {
                e.setCancelled(true);
            }
            if (event.getPacket() instanceof Packet12PlayerLook) {
                e.setCancelled(true);
            }
            if (event.getPacket() instanceof Packet13PlayerLookMove) {
                e.setCancelled(true);
            }
        }
        if (e instanceof EventTick) {
            if (this.mc.thePlayer.ticksExisted % 60 == 0) {
                Client.sendPacket(new Packet0KeepAlive());
            }
            this.mc.thePlayer.noClip = true;
            this.mc.thePlayer.motionY = 0.0;
            if (this.mc.currentScreen == null) {
                if (Keyboard.isKeyDown((int)57)) {
                    this.mc.thePlayer.motionY = 1.0 * this.getSet("Speed").getValDouble();
                }
                if (Keyboard.isKeyDown((int)42)) {
                    this.mc.thePlayer.motionY = -1.0 * this.getSet("Speed").getValDouble();
                }
            }
            float var1 = this.mc.thePlayer.moveStrafing;
            float var2 = this.mc.thePlayer.moveForward;
            float var3 = 0.2f * (float)this.getSet("Speed").getValDouble();
            float var4 = MathHelper.sqrt_float(var1 * var1 + var2 * var2);
            if (var4 >= 0.01f) {
                if (var4 < 1.0f) {
                    var4 = 1.0f;
                }
                var4 = var3 / var4;
                float var5 = MathHelper.sin(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
                float var6 = MathHelper.cos(this.mc.thePlayer.rotationYaw * (float)Math.PI / 180.0f);
                this.mc.thePlayer.motionX += (double)((var1 *= var4) * var6 - (var2 *= var4) * var5);
                this.mc.thePlayer.motionZ += (double)(var2 * var6 + var1 * var5);
            }
        }
    }
}

