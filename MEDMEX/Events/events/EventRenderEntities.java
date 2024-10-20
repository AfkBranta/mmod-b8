/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;

public class EventRenderEntities
extends Event {
    float partialTicks;

    public EventRenderEntities(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}

