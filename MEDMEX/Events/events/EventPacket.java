/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;
import MEDMEX.Events.EventBound;
import net.minecraft.src.Packet;

public class EventPacket
extends Event {
    Packet packet;
    public EventBound bound;

    public EventPacket(Packet p, EventBound bound) {
        this.packet = p;
        this.bound = bound;
    }

    public EventBound getBound() {
        return this.bound;
    }

    public void setBound(EventBound bound) {
        this.bound = bound;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

