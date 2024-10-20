/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.BlockPlacement;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import net.minecraft.src.AxisAlignedBB;

public class Scaffold
extends Module {
    public static Scaffold instance;
    Vec3D[] corners = new Vec3D[4];

    public Scaffold() {
        super("Scaffold", "Place blocks under you", 0, Category.Player);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            AxisAlignedBB bb = this.mc.thePlayer.boundingBox.copy().contract(0.1, 0.0, 0.1);
            this.corners[0] = new Vec3D(bb.minX, bb.maxY, bb.minZ);
            this.corners[1] = new Vec3D(bb.minX, bb.maxY, bb.maxZ);
            this.corners[2] = new Vec3D(bb.maxX, bb.maxY, bb.minZ);
            this.corners[3] = new Vec3D(bb.maxX, bb.maxY, bb.maxZ);
            Vec3D[] vec3DArray = this.corners;
            int n = this.corners.length;
            int n2 = 0;
            while (n2 < n) {
                Vec3D vec = vec3DArray[n2];
                Vec3D pos = PlayerUtils.getBelowPos(vec);
                if (WorldUtils.isTransparentBlock(pos)) {
                    int blockSlot = InventoryUtils.getHotbarslotBlocks();
                    if (blockSlot == -1) {
                        return;
                    }
                    this.mc.thePlayer.inventory.currentItem = blockSlot;
                    BlockPlacement placement = PlayerUtils.getPossiblePlacement(pos);
                    if (placement != null) {
                        if (placement.getSide() == 1 && this.mc.thePlayer.motionY > 0.0) {
                            return;
                        }
                        this.mc.playerController.sendPlaceBlock(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem(), placement.getX(), placement.getY(), placement.getZ(), placement.getSide());
                    }
                }
                ++n2;
            }
        }
    }

    @Override
    public void onDisable() {
    }
}

