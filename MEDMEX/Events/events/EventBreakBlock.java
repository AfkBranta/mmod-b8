/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Events.events;

import MEDMEX.Events.Event;

public class EventBreakBlock
extends Event {
    int x;
    int y;
    int z;
    int side;

    public EventBreakBlock(int x, int y, int z, int side) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.side = side;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getSide() {
        return this.side;
    }

    public void setSide(int side) {
        this.side = side;
    }
}

