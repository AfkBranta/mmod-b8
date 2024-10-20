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
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Vec2D;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.WorldUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.src.Packet15Place;

public class Nuker
extends Module {
    public static Nuker instance;
    public static CopyOnWriteArrayList<String> selectionblocks;
    Vec3D current;

    static {
        selectionblocks = new CopyOnWriteArrayList();
    }

    public Nuker() {
        super("Nuker", "Mine blocks around you", 0, Category.Player);
        this.addSetting(new Setting("blocks", (Module)this, selectionblocks, "Integer"));
        this.addSetting(new Setting("Flatten", (Module)this, false));
        ArrayList<String> options = new ArrayList<String>();
        options.add("Selection");
        options.add("All");
        this.addSetting(new Setting("Mode", (Module)this, "Selection", options));
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPlayer) {
            Vec3D toMine = this.findBlockToMine();
            if (toMine == null) {
                return;
            }
            this.current = toMine;
            EventPlayer event = (EventPlayer)e;
            Vec2D rotations = PlayerUtils.getRotationsToVec3D(toMine);
            event.setYaw((float)rotations.getX());
            event.setPitch((float)rotations.getY());
            this.mc.thePlayer.swingItem();
            Client.playerUtils.breakBlock(toMine);
            Client.sendPacket(new Packet15Place((int)toMine.getX(), (int)toMine.getY(), (int)toMine.getZ(), 0, this.mc.thePlayer.inventory.getCurrentItem()));
        }
        if (e instanceof EventRenderEntities) {
            if (this.current == null) {
                return;
            }
            if (WorldUtils.getBlockID(this.current) == 0) {
                this.current = null;
                return;
            }
            RenderUtils.drawBreakingBlock(this.current.toBB(), new Color(200, 50, 50, 200));
            Vec3D pos = PlayerUtils.getPos();
            RenderUtils.drawLineFromTo(pos, this.current.add(0.5, 0.5, 0.5), 200.0f, 50.0f, 50.0f, 200.0f, 1.0f);
        }
    }

    public Vec3D findBlockToMine() {
        Vec3D pos = PlayerUtils.getOffsetPos(PlayerUtils.getPos());
        int i = -3;
        while (i < 4) {
            int j = this.getSet("Flatten").getValBoolean() ? -1 : -4;
            while (j < 4) {
                int k = -3;
                while (k < 4) {
                    int blockid = this.mc.theWorld.getBlockId((int)pos.getX() + i, (int)pos.getY() + j, (int)pos.getZ() + k);
                    if (this.getSet("Mode").getValString().equalsIgnoreCase("Selection") && selectionblocks.contains(blockid)) {
                        return new Vec3D(pos.getX() + (double)i, pos.getY() + (double)j, pos.getZ() + (double)k);
                    }
                    if (!this.getSet("Mode").getValString().equalsIgnoreCase("Selection") && blockid != 0 && blockid != 7) {
                        int x = (int)Math.floor(pos.getX() + (double)i);
                        int y = (int)Math.floor(pos.getY() + (double)j);
                        int z = (int)Math.floor(pos.getZ() + (double)k);
                        return new Vec3D(x, y, z);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return null;
    }

    @Override
    public void onDisable() {
    }
}

