/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import net.minecraft.src.CodecMus;

class MusInputStream
extends InputStream {
    private int hash;
    private InputStream inputStream;
    byte[] buffer;
    final CodecMus codec;

    public MusInputStream(CodecMus codecMus1, URL uRL2, InputStream inputStream3) {
        this.codec = codecMus1;
        this.buffer = new byte[1];
        this.inputStream = inputStream3;
        String string4 = uRL2.getPath();
        string4 = string4.substring(string4.lastIndexOf("/") + 1);
        this.hash = string4.hashCode();
    }

    @Override
    public int read() throws IOException {
        int i1 = this.read(this.buffer, 0, 1);
        return i1 < 0 ? i1 : this.buffer[0];
    }

    @Override
    public int read(byte[] b1, int i2, int i3) throws IOException {
        i3 = this.inputStream.read(b1, i2, i3);
        int i4 = 0;
        while (i4 < i3) {
            byte by = (byte)(b1[i2 + i4] ^ this.hash >> 8);
            b1[i2 + i4] = by;
            byte b5 = by;
            this.hash = this.hash * 498729871 + 85731 * b5;
            ++i4;
        }
        return i3;
    }
}

