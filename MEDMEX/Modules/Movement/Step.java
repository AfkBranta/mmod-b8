/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Movement.Fly;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;

public class Step
extends Module {
    public Step() {
        super("Step", "Move up blocks without jumping", 0, Category.Movement);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Vanilla");
        options.add("Custom");
        options.add("NCP");
        this.addSetting(new Setting("Mode", (Module)this, "Vanilla", options));
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.stepHeight = 0.05f;
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventPlayer)) {
            return;
        }
        if (this.getSet("Mode").getValString().equalsIgnoreCase("Custom")) {
            this.mc.thePlayer.stepHeight = 0.05f;
            List collisions = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.expand(0.1, 0.0, 0.1));
            if (collisions.isEmpty()) {
                return;
            }
            AxisAlignedBB closestCollision = (AxisAlignedBB)collisions.get(0);
            AxisAlignedBB player = this.mc.thePlayer.boundingBox;
            Vec3D playerCenter = new Vec3D(player.minX + (player.maxX - player.minX) / 2.0, player.minY + (player.maxY - player.minY) / 2.0, player.minZ + (player.maxZ - player.minZ) / 2.0);
            Vec3D firstCollisionCenter = new Vec3D(closestCollision.minX + (closestCollision.maxX - closestCollision.minX) / 2.0, closestCollision.minY + (closestCollision.maxY - closestCollision.minY) / 2.0, closestCollision.minZ + (closestCollision.maxZ - closestCollision.minZ) / 2.0);
            double closestDistance = Vec3D.getDistance(playerCenter, firstCollisionCenter);
            for (AxisAlignedBB collision : collisions) {
                Vec3D collisionCenter = new Vec3D(collision.minX + (collision.maxX - collision.minX) / 2.0, collision.minY + (collision.maxY - collision.minY) / 2.0, collision.minZ + (collision.maxZ - collision.minZ) / 2.0);
                double dist = Vec3D.getDistance(playerCenter, collisionCenter);
                if (!(dist < closestDistance)) continue;
                closestDistance = dist;
                closestCollision = collision;
            }
            Vec3D collisionVec = new Vec3D(closestCollision);
            boolean blockAboveCollision = !WorldUtils.isTransparentBlock(collisionVec.add(0.0, 1.0, 0.0));
            boolean block2AboveCollision = !WorldUtils.isTransparentBlock(collisionVec.add(0.0, 2.0, 0.0));
            double x = closestCollision.calculateXOffset(player, this.mc.thePlayer.motionX);
            double z = closestCollision.calculateZOffset(player, this.mc.thePlayer.motionZ);
            if (x == 0.0 && z == 0.0) {
                return;
            }
            if (blockAboveCollision && !block2AboveCollision && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.jump();
            }
            if (!blockAboveCollision && !block2AboveCollision && (this.mc.thePlayer.onGround || this.mc.thePlayer.motionY < -0.08 || Fly.instance.isEnabled)) {
                this.mc.thePlayer.boundingBox.setBB(new AxisAlignedBB(player.minX + x, player.minY + 1.0, player.minZ + z, player.maxX + x, player.maxY + 1.0, player.maxZ + z));
            }
        }
        if (this.getSet("Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.mc.thePlayer.stepHeight = 1.0f;
        }
        if (this.getSet("Mode").getValString().equalsIgnoreCase("NCP")) {
            this.mc.thePlayer.stepHeight = 0.05f;
            if (this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.motionY = 0.37;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.getSet("Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.mc.thePlayer.stepHeight = 1.0f;
        }
    }
}

