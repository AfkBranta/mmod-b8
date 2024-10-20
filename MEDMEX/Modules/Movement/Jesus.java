/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.EventBound;
import MEDMEX.Events.events.EventMoveEntity;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import java.util.ArrayList;
import net.minecraft.src.MaterialLiquid;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet13PlayerLookMove;

public class Jesus
extends Module {
    public static Jesus instance;
    private int ticks = 0;
    float[] values = new float[]{0.0f, 0.019f, 0.06f, 0.08f, 0.02f};

    public Jesus() {
        super("Jesus", "Walk on water", 0, Category.Movement);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Vanilla");
        options.add("NCP");
        this.addSetting(new Setting("Mode", (Module)this, "Vanilla", options));
        instance = this;
    }

    @Override
    public void onEvent(Event e) {
        Event event;
        if (e instanceof EventPacket) {
            Packet10Flying p;
            if (this.mc.thePlayer == null || this.mc.theWorld == null) {
                return;
            }
            if (!(this.mc.theWorld.getBlockMaterial((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY - 2, (int)this.mc.thePlayer.posZ) instanceof MaterialLiquid)) {
                return;
            }
            event = (EventPacket)e;
            if (((EventPacket)event).getBound() == EventBound.IN) {
                return;
            }
            if (((EventPacket)event).getPacket() instanceof Packet10Flying) {
                p = (Packet10Flying)((EventPacket)event).getPacket();
                p.stance = this.mc.thePlayer.posY - (double)this.values[this.ticks++ % this.values.length];
            }
            if (((EventPacket)event).getPacket() instanceof Packet13PlayerLookMove) {
                p = (Packet13PlayerLookMove)((EventPacket)event).getPacket();
                ((Packet13PlayerLookMove)p).stance = this.mc.thePlayer.posY;
            }
        }
        if (e instanceof EventMoveEntity) {
            if (this.getSet("Mode").getValString().equalsIgnoreCase("Vanilla")) {
                return;
            }
            if (this.mc.theWorld == null) {
                return;
            }
            if (!(this.mc.theWorld.getBlockMaterial((int)this.mc.thePlayer.posX, (int)this.mc.thePlayer.posY - 2, (int)this.mc.thePlayer.posZ) instanceof MaterialLiquid)) {
                return;
            }
            event = (EventMoveEntity)e;
            ((EventMoveEntity)event).setMotionX(((EventMoveEntity)event).getMotionX() * 0.42);
            ((EventMoveEntity)event).setMotionZ(((EventMoveEntity)event).getMotionZ() * 0.42);
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}

