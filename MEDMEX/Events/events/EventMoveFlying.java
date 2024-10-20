/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;

public class EventMoveFlying
extends Event {
    float yaw;

    public EventMoveFlying(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}

