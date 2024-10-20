/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Player.Friends;
import MEDMEX.Utils.RenderUtils;
import java.awt.Color;
import java.util.List;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;

public class Tracers
extends Module {
    public static Tracers instance;

    public Tracers() {
        super("Tracers", "Draws lines to players", 0, Category.Visual);
        instance = this;
    }

    @Override
    public void onEvent(Event e) {
        if (!(e instanceof EventRenderEntities)) {
            return;
        }
        List entities = this.mc.theWorld.loadedEntityList;
        for (Entity ent : entities) {
            if (!(ent instanceof EntityPlayer) || ent == this.mc.thePlayer) continue;
            EntityPlayer player = (EntityPlayer)ent;
            Color col = Friends.friends.contains(player.username) ? new Color(110, 250, 150, 100) : new Color(170, 31, 240, 100);
            RenderUtils.drawTracerLine(player.boundingBox, col, 2.0f);
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
    }
}

