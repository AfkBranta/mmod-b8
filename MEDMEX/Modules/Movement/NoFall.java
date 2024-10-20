/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Modules.Movement;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.Vec3D;
import java.util.ArrayList;
import net.minecraft.src.Packet10Flying;

public class NoFall
extends Module {
    public static NoFall instance;
    public int cancel = 0;
    Vec3D savedPosition;

    public NoFall() {
        super("NoFall", "Prevents fall damage", 0, Category.Movement);
        ArrayList<String> options = new ArrayList<String>();
        options.add("OnGround");
        options.add("NoGround");
        options.add("Anti");
        this.addSetting(new Setting("Mode", (Module)this, "OnGround", options));
        this.alwaysReceiveEvents = true;
        instance = this;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        Event ep;
        if (e instanceof EventPlayer) {
            if (this.getSet("Mode").getValString().equalsIgnoreCase("NoGround")) {
                if (this.isEnabled) {
                    ep = (EventPlayer)e;
                    ((EventPlayer)ep).setOnground(false);
                    this.cancel = 5;
                } else if (this.cancel > 0) {
                    ep = (EventPlayer)e;
                    ((EventPlayer)ep).setOnground(false);
                    --this.cancel;
                }
            }
            if (this.getSet("Mode").getValString().equalsIgnoreCase("Anti")) {
                if (!this.isEnabled) {
                    return;
                }
                if (this.mc.thePlayer.onGround) {
                    this.savedPosition = PlayerUtils.getPos();
                }
                if (this.mc.thePlayer.fallDistance > 2.0f && this.savedPosition != null) {
                    this.mc.thePlayer.setPosition(this.savedPosition.getX(), this.savedPosition.getY(), this.savedPosition.getZ());
                }
            }
        }
        if (e instanceof EventPacket) {
            if (!this.isEnabled) {
                return;
            }
            if (!this.getSet("Mode").getValString().equalsIgnoreCase("OnGround")) {
                return;
            }
            ep = (EventPacket)e;
            if (((EventPacket)ep).getPacket() instanceof Packet10Flying) {
                Packet10Flying p = (Packet10Flying)((EventPacket)ep).getPacket();
                p.onGround = true;
            }
        }
    }

    @Override
    public void onDisable() {
        if (this.getSet("Mode").getValString().equalsIgnoreCase("NoGround")) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
        }
    }
}

