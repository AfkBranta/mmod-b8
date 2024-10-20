/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;

public class EventPlayer
extends Event {
    public double posX;
    public double posY;
    public double minY;
    public double posZ;
    public float yaw;
    public float pitch;
    public boolean onground;

    public EventPlayer(double posX, double minY, double posY, double posZ, float yaw, float pitch, boolean onground) {
        this.posX = posX;
        this.posY = posY;
        this.minY = minY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onground = onground;
    }

    public double getPosX() {
        return this.posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getMinY() {
        return this.minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getPosY() {
        return this.posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnground() {
        return this.onground;
    }

    public void setOnground(boolean onground) {
        this.onground = onground;
    }
}

