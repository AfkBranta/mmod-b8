/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Player;

import MEDMEX.Client;
import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import java.awt.Color;
import net.minecraft.src.Packet15Place;

public class Automine
extends Module {
    public static Automine instance;
    public Vec3D lastBlock;
    public float blockDamage;

    public Automine() {
        super("Automine", "Automatically mines in front of you", 0, Category.Player);
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        Vec3D toMine;
        if (e instanceof EventPlayer) {
            toMine = this.getBlockToMine();
            if (toMine == null) {
                return;
            }
            PlayerUtils.breakBlockPacket(toMine);
            Client.sendPacket(new Packet15Place((int)toMine.getX(), (int)toMine.getY(), (int)toMine.getZ(), 0, this.mc.thePlayer.inventory.getCurrentItem()));
        }
        if (e instanceof EventRenderEntities) {
            toMine = this.getBlockToMine();
            if (toMine == null) {
                return;
            }
            RenderUtils.boundingESPBoxFilled(toMine.toBB(), new Color(255, 255, 255, 80));
        }
    }

    public void resetBlockMem(Vec3D newBlock) {
        this.lastBlock = newBlock;
        this.blockDamage = 0.0f;
    }

    public Vec3D getBlockToMine() {
        Vec3D directionOffset = PlayerUtils.getDirectionOffset();
        Vec3D playerPos = PlayerUtils.getOffsetPos(PlayerUtils.getPos());
        int horizontal = 1;
        while (horizontal < 5) {
            int vertical = 0;
            while (vertical >= -1) {
                int z;
                int y;
                int x = (int)directionOffset.getX() * horizontal;
                Vec3D miningPos = new Vec3D(playerPos.x + (double)x, playerPos.y + (double)(y = (int)directionOffset.getY() + vertical), playerPos.z + (double)(z = (int)directionOffset.getZ() * horizontal));
                if (WorldUtils.getBlockID(miningPos) != 0) {
                    return miningPos;
                }
                --vertical;
            }
            ++horizontal;
        }
        return null;
    }

    @Override
    public void onDisable() {
    }
}

