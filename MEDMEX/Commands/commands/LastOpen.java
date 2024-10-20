/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Commands.Command;
import MEDMEX.Events.Event;
import MEDMEX.Events.EventBound;
import MEDMEX.Events.events.EventPacket;
import MEDMEX.Events.events.EventPlayer;
import net.minecraft.src.GuiChest;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet101;

public class LastOpen
extends Command {
    public GuiContainer container;

    public LastOpen() {
        super("LastOpen", "Opens last container", "lastopen", "lo");
    }

    @Override
    public void onCommand(String[] args) {
        if (this.container == null) {
            return;
        }
        this.mc.displayGuiScreen(this.container);
    }

    @Override
    public void onEvent(Event e) {
        EventPacket event;
        Packet p;
        if (e instanceof EventPacket && (p = (event = (EventPacket)e).getPacket()) instanceof Packet101) {
            Packet101 packet = (Packet101)p;
            if (event.getBound() == EventBound.OUT && this.container instanceof GuiChest) {
                event.setCancelled(true);
            }
            if (event.getBound() == EventBound.IN) {
                if (this.container == null) {
                    return;
                }
                if (packet.windowId == this.container.inventorySlots.windowId) {
                    this.container = null;
                }
            }
        }
        if (e instanceof EventPlayer && this.mc.currentScreen != null && this.mc.currentScreen instanceof GuiContainer) {
            this.container = (GuiContainer)this.mc.currentScreen;
        }
    }
}

