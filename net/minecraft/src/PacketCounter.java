/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Empty1;

class PacketCounter {
    private int totalPackets;
    private long totalBytes;

    private PacketCounter() {
    }

    public void addPacket(int i1) {
        ++this.totalPackets;
        this.totalBytes += (long)i1;
    }

    PacketCounter(Empty1 empty11) {
        this();
    }
}

