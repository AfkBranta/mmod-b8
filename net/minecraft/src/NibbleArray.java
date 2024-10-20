/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class NibbleArray {
    public final byte[] data;

    public NibbleArray(int i1) {
        this.data = new byte[i1 >> 1];
    }

    public NibbleArray(byte[] b1) {
        this.data = b1;
    }

    public int getNibble(int i1, int i2, int i3) {
        int i4 = i1 << 11 | i3 << 7 | i2;
        int i5 = i4 >> 1;
        int i6 = i4 & 1;
        return i6 == 0 ? this.data[i5] & 0xF : this.data[i5] >> 4 & 0xF;
    }

    public void setNibble(int i1, int i2, int i3, int i4) {
        int i5 = i1 << 11 | i3 << 7 | i2;
        int i6 = i5 >> 1;
        int i7 = i5 & 1;
        this.data[i6] = i7 == 0 ? (byte)(this.data[i6] & 0xF0 | i4 & 0xF) : (byte)(this.data[i6] & 0xF | (i4 & 0xF) << 4);
    }

    public boolean isValid() {
        return this.data != null;
    }
}

