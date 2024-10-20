/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventBlock;
import MEDMEX.Events.events.EventListUpdate;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.BlockPos;
import MEDMEX.Utils.GuiUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.WorldUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Search
extends Module {
    public static Search instance;
    public static CopyOnWriteArrayList<Integer> searchblocks;
    List<BlockPos> toRender = new ArrayList<BlockPos>();
    Color col = new Color(175, 175, 175, 120);

    static {
        searchblocks = new CopyOnWriteArrayList();
    }

    public Search() {
        super("Search", "Render specific blocks", 0, Category.Visual);
        this.addSetting(new Setting("blocks", (Module)this, searchblocks, "Integer"));
        this.addSetting(new Setting("Tracers", (Module)this, false));
        instance = this;
    }

    @Override
    public void onDisable() {
        this.toRender.clear();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventListUpdate) {
            this.mc.renderGlobal.loadRenderers();
        }
        if (e instanceof EventBlock) {
            EventBlock eb = (EventBlock)e;
            if (!searchblocks.contains(eb.getBlockid())) {
                return;
            }
            for (BlockPos bp : this.toRender) {
                if (!bp.getPos().equalsVec(eb.getPos())) continue;
                return;
            }
            this.toRender.add(new BlockPos(eb.getPos(), eb.getBlockid()));
        }
        if (e instanceof EventRenderEntities) {
            try {
                for (BlockPos bp : this.toRender) {
                    if (WorldUtils.getBlockID(bp.getPos()) != bp.getBlockid() || !searchblocks.contains(WorldUtils.getBlockID(bp.getPos()))) {
                        this.toRender.remove(bp);
                        continue;
                    }
                    RenderUtils.boundingESPBoxFilled(bp.getPos().toBB(), GuiUtils.numToColor(bp.getBlockid(), 0.0, 100.0));
                    if (!this.getSet("Tracers").getValBoolean()) continue;
                    RenderUtils.drawTracerLine(bp.getPos().toBB(), GuiUtils.numToColor(bp.getBlockid(), 0.0, 100.0), 2.0f);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public void onEnable() {
        this.mc.renderGlobal.loadRenderers();
    }
}

