/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.World;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Vec2D;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet53BlockChange;

public class NewChunks
extends Module {
    public static NewChunks instance;
    public CopyOnWriteArrayList<Vec2D> chunks = new CopyOnWriteArrayList();

    public NewChunks() {
        super("NewChunks", "Show newly loaded chunks", 0, Category.World);
        this.addSetting(new Setting("Height", this, 0.0, 0.0, 127.0, false));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            EventPacket ep = (EventPacket)e;
            if (!(ep.getPacket() instanceof Packet53BlockChange)) {
                return;
            }
            Packet53BlockChange p = (Packet53BlockChange)ep.getPacket();
            if (p.type == 9 || p.type == 11) {
                int cX = p.xPosition >> 4;
                int cZ = p.zPosition >> 4;
                this.processChunk(new Vec2D(cX, cZ));
            }
        }
        if (e instanceof EventRenderEntities) {
            for (Vec2D vec : this.chunks) {
                double dZ;
                double dX = this.mc.thePlayer.posX - vec.getX() * 16.0;
                double distance = MathHelper.sqrt_double(dX * dX + (dZ = this.mc.thePlayer.posZ - vec.getY() * 16.0) * dZ);
                if (distance > 500.0) {
                    this.chunks.remove(vec);
                    continue;
                }
                int minX = (int)vec.getX() * 16;
                int minZ = (int)vec.getY() * 16;
                int maxX = minX + 16;
                int maxZ = minZ + 16;
                AxisAlignedBB chunkBB = new AxisAlignedBB(minX, this.getSet("Height").getValDouble(), minZ, maxX, this.getSet("Height").getValDouble() + 0.01, maxZ);
                RenderUtils.boundingESPBox(chunkBB, new Color(255, 0, 0, 155));
            }
        }
    }

    public void processChunk(Vec2D chunkVec) {
        for (Vec2D vec : this.chunks) {
            if (!vec.equalsVec(chunkVec)) continue;
            return;
        }
        this.chunks.add(chunkVec);
    }

    @Override
    public void onDisable() {
    }
}

