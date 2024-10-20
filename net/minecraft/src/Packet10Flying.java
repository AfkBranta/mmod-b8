/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

public class Packet10Flying
extends Packet {
    public double xPosition;
    public double yPosition;
    public double zPosition;
    public double stance;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean moving;
    public boolean rotating;

    public Packet10Flying() {
    }

    public Packet10Flying(boolean z1) {
        this.onGround = z1;
    }

    @Override
    public void processPacket(NetHandler netHandler1) {
        netHandler1.handleFlying(this);
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream1) throws IOException {
        this.onGround = dataInputStream1.read() != 0;
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
        dataOutputStream1.write(this.onGround ? 1 : 0);
    }

    @Override
    public int getPacketSize() {
        return 1;
    }
}

