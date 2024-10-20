/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Combat;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Player.Friends;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Timer;
import MEDMEX.Utils.Vec2D;
import MEDMEX.Utils.Vec3D;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.MathHelper;

public class AutoAttack
extends Module {
    public static AutoAttack instance;
    Timer t = new Timer();
    public Entity target;
    public Vec2D rotation;

    public AutoAttack() {
        super("AutoAttack", "Automatically attacks entities around you", 0, Category.Combat);
        this.addSetting(new Setting("Players", (Module)this, true));
        this.addSetting(new Setting("Monsters", (Module)this, true));
        this.addSetting(new Setting("Animals", (Module)this, false));
        this.addSetting(new Setting("Rotate", (Module)this, true));
        this.addSetting(new Setting("Movement Correction", (Module)this, false));
        this.addSetting(new Setting("Distance", this, 4.0, 1.0, 6.0, false));
        this.addSetting(new Setting("Attack Delay", this, 100.0, 0.0, 1000.0, true));
        this.addSetting(new Setting("Add Random Delay", this, 10.0, 0.0, 50.0, true));
        this.addSetting(new Setting("Debug Lines", (Module)this, false));
        this.addSetting(new Setting("Simulate Safe", (Module)this, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event e) {
        Event event;
        if (e instanceof EventPlayer) {
            event = (EventPlayer)e;
            List entities = this.mc.theWorld.loadedEntityList;
            Collections.sort(entities, new DistanceComparator());
            for (Entity ent : entities) {
                float simZ;
                float simY;
                Vec3D simulatedViewVec;
                float simX;
                AxisAlignedBB viewBB;
                Vec2D rotationVec;
                if (!this.shouldAttack(ent) || !this.canAttack(ent)) continue;
                this.rotation = rotationVec = this.getRotationsToEntity(ent);
                this.target = ent;
                if (rotationVec == null) {
                    return;
                }
                if (this.getSet("Rotate").getValBoolean()) {
                    ((EventPlayer)event).setYaw((float)rotationVec.getX());
                    ((EventPlayer)event).setPitch((float)rotationVec.getY());
                }
                if (!(viewBB = new AxisAlignedBB((double)(simX = (float)((simulatedViewVec = this.getSimulatedViewVec(ent, this.rotation)).getX() + this.mc.thePlayer.posX)) - 0.05, (double)(simY = (float)(simulatedViewVec.getY() + this.mc.thePlayer.posY)) - 0.05, (double)(simZ = (float)(simulatedViewVec.getZ() + this.mc.thePlayer.posZ)) - 0.05, (double)simX + 0.05, (double)simY + 0.05, (double)simZ + 0.05)).intersectsWith(ent.boundingBox) && this.getSet("Simulate Safe").getValBoolean()) {
                    return;
                }
                this.getSet("Movement Correction").getValBoolean();
                long delay = (long)this.getSet("Attack Delay").getValDouble();
                if (this.getSet("Add Random Delay").getValDouble() > 0.0) {
                    Random r = new Random();
                    delay += (long)r.nextInt((int)this.getSet("Add Random Delay").getValDouble());
                }
                if (this.t.hasTimeElapsed(delay, true)) {
                    this.attackEntity(ent);
                }
                return;
            }
            this.target = null;
        }
        if (e instanceof EventRenderEntities) {
            if (!this.getSet("Debug Lines").getValBoolean()) {
                return;
            }
            if (this.target == null || this.rotation == null) {
                return;
            }
            event = (EventRenderEntities)e;
            Vec3D viewVec = this.getSimulatedViewVec(this.target, this.rotation);
            RenderUtils.drawRotationLine(viewVec.getX(), viewVec.getY(), viewVec.getZ(), 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            RenderUtils.drawTracerLine(this.target.posX, this.target.posY, this.target.posZ, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f);
        }
    }

    public Vec3D getSimulatedViewVec(Entity e, Vec2D rotation) {
        float diffX = (float)(e.posX - this.mc.thePlayer.posX);
        float diffZ = (float)(e.posZ - this.mc.thePlayer.posZ);
        float diffY = (float)(e.posY - this.mc.thePlayer.posY);
        float distY = MathHelper.sqrt_float(Math.abs(diffY * diffY));
        float dist = MathHelper.sqrt_float(Math.abs(diffX * diffX + diffZ * diffZ));
        float sin = (float)(-Math.sin(Math.toRadians(rotation.x))) * dist;
        float cos = (float)Math.cos(Math.toRadians(rotation.x)) * dist;
        float tan = (float)(-Math.atan(Math.toRadians(rotation.y))) * (distY * dist);
        Vec3D viewVec = new Vec3D(sin, tan + this.mc.thePlayer.getEyeHeight(), cos);
        return viewVec;
    }

    public boolean shouldAttack(Entity e) {
        if (e == this.mc.thePlayer) {
            return false;
        }
        if ((e instanceof EntityMobs || e instanceof EntitySlime) && this.getSet("Monsters").getValBoolean()) {
            return true;
        }
        if ((e instanceof EntityAnimals || e instanceof EntitySquid) && this.getSet("Animals").getValBoolean()) {
            return true;
        }
        if (e instanceof EntityPlayer && this.getSet("Players").getValBoolean()) {
            EntityPlayer player = (EntityPlayer)e;
            if (Friends.instance.isEnabled && Friends.friends.contains(player.username.toLowerCase())) {
                return false;
            }
            return player.username != "freecam";
        }
        return false;
    }

    public boolean canAttack(Entity e) {
        if (e.isDead) {
            return false;
        }
        return (double)this.mc.thePlayer.getDistanceToEntity(e) <= this.getSet("Distance").getValDouble();
    }

    public Vec2D getRotationsToEntity(Entity e) {
        float diffX = (float)(e.posX - this.mc.thePlayer.posX);
        float diffY = (float)(e.boundingBox.minY - this.mc.thePlayer.posY);
        float diffZ = (float)(e.posZ - this.mc.thePlayer.posZ);
        float dist = MathHelper.sqrt_float(Math.abs(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        if (!Float.isNaN(pitch) && !Float.isNaN(yaw)) {
            return new Vec2D(yaw, pitch);
        }
        return null;
    }

    public void attackEntity(Entity e) {
        this.mc.thePlayer.swingItem();
        this.mc.playerController.func_6472_b(this.mc.thePlayer, e);
    }

    public static class DistanceComparator
    implements Comparator<Entity> {
        @Override
        public int compare(Entity o1, Entity o2) {
            if (Minecraft.theMinecraft.thePlayer.getDistanceToEntity(o1) > Minecraft.theMinecraft.thePlayer.getDistanceToEntity(o2)) {
                return 1;
            }
            if (Minecraft.theMinecraft.thePlayer.getDistanceToEntity(o1) < Minecraft.theMinecraft.thePlayer.getDistanceToEntity(o2)) {
                return -1;
            }
            return 0;
        }
    }
}

