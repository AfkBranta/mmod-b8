/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.src.RegionFileChunkBuffer;

public class RegionFile {
    private static final byte[] field_22213_a = new byte[4096];
    private final File field_22212_b;
    private RandomAccessFile field_22219_c;
    private final int[] field_22218_d = new int[1024];
    private final int[] field_22217_e = new int[1024];
    private ArrayList field_22216_f;
    private int field_22215_g;
    private long field_22214_h = 0L;

    public RegionFile(File file1) {
        this.field_22212_b = file1;
        this.func_22204_b("REGION LOAD " + this.field_22212_b);
        this.field_22215_g = 0;
        try {
            int i4;
            int i2;
            if (file1.exists()) {
                this.field_22214_h = file1.lastModified();
            }
            this.field_22219_c = new RandomAccessFile(file1, "rw");
            if (this.field_22219_c.length() < 4096L) {
                i2 = 0;
                while (i2 < 1024) {
                    this.field_22219_c.writeInt(0);
                    ++i2;
                }
                i2 = 0;
                while (i2 < 1024) {
                    this.field_22219_c.writeInt(0);
                    ++i2;
                }
                this.field_22215_g += 8192;
            }
            if ((this.field_22219_c.length() & 0xFFFL) != 0L) {
                i2 = 0;
                while ((long)i2 < (this.field_22219_c.length() & 0xFFFL)) {
                    this.field_22219_c.write(0);
                    ++i2;
                }
            }
            i2 = (int)this.field_22219_c.length() / 4096;
            this.field_22216_f = new ArrayList(i2);
            int i3 = 0;
            while (i3 < i2) {
                this.field_22216_f.add(true);
                ++i3;
            }
            this.field_22216_f.set(0, false);
            this.field_22216_f.set(1, false);
            this.field_22219_c.seek(0L);
            i3 = 0;
            while (i3 < 1024) {
                this.field_22218_d[i3] = i4 = this.field_22219_c.readInt();
                if (i4 != 0 && (i4 >> 8) + (i4 & 0xFF) <= this.field_22216_f.size()) {
                    int i5 = 0;
                    while (i5 < (i4 & 0xFF)) {
                        this.field_22216_f.set((i4 >> 8) + i5, false);
                        ++i5;
                    }
                }
                ++i3;
            }
            i3 = 0;
            while (i3 < 1024) {
                this.field_22217_e[i3] = i4 = this.field_22219_c.readInt();
                ++i3;
            }
        }
        catch (IOException iOException6) {
            iOException6.printStackTrace();
        }
    }

    public synchronized int func_22209_a() {
        int i1 = this.field_22215_g;
        this.field_22215_g = 0;
        return i1;
    }

    private void func_22211_a(String string1) {
    }

    private void func_22204_b(String string1) {
        this.func_22211_a(String.valueOf(string1) + "\n");
    }

    private void func_22199_a(String string1, int i2, int i3, String string4) {
        this.func_22211_a("REGION " + string1 + " " + this.field_22212_b.getName() + "[" + i2 + "," + i3 + "] = " + string4);
    }

    private void func_22197_a(String string1, int i2, int i3, int i4, String string5) {
        this.func_22211_a("REGION " + string1 + " " + this.field_22212_b.getName() + "[" + i2 + "," + i3 + "] " + i4 + "B = " + string5);
    }

    private void func_22201_b(String string1, int i2, int i3, String string4) {
        this.func_22199_a(string1, i2, i3, String.valueOf(string4) + "\n");
    }

    public synchronized DataInputStream func_22210_a(int i1, int i2) {
        int i6;
        block10: {
            int i5;
            int i4;
            block9: {
                int i3;
                block8: {
                    if (this.func_22206_d(i1, i2)) {
                        this.func_22201_b("READ", i1, i2, "out of bounds");
                        return null;
                    }
                    try {
                        i3 = this.func_22207_e(i1, i2);
                        if (i3 != 0) break block8;
                        return null;
                    }
                    catch (IOException iOException10) {
                        this.func_22201_b("READ", i1, i2, "exception");
                        return null;
                    }
                }
                i4 = i3 >> 8;
                i5 = i3 & 0xFF;
                if (i4 + i5 <= this.field_22216_f.size()) break block9;
                this.func_22201_b("READ", i1, i2, "invalid sector");
                return null;
            }
            this.field_22219_c.seek(i4 * 4096);
            i6 = this.field_22219_c.readInt();
            if (i6 <= 4096 * i5) break block10;
            this.func_22201_b("READ", i1, i2, "invalid length: " + i6 + " > 4096 * " + i5);
            return null;
        }
        byte b7 = this.field_22219_c.readByte();
        if (b7 == 1) {
            byte[] b8 = new byte[i6 - 1];
            this.field_22219_c.read(b8);
            DataInputStream dataInputStream9 = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(b8)));
            return dataInputStream9;
        }
        if (b7 == 2) {
            byte[] b8 = new byte[i6 - 1];
            this.field_22219_c.read(b8);
            DataInputStream dataInputStream9 = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(b8)));
            return dataInputStream9;
        }
        this.func_22201_b("READ", i1, i2, "unknown version " + b7);
        return null;
    }

    public DataOutputStream func_22205_b(int i1, int i2) {
        return this.func_22206_d(i1, i2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, i1, i2)));
    }

    protected synchronized void func_22203_a(int i1, int i2, byte[] b3, int i4) {
        try {
            int i5 = this.func_22207_e(i1, i2);
            int i6 = i5 >> 8;
            int i7 = i5 & 0xFF;
            int i8 = (i4 + 5) / 4096 + 1;
            if (i8 >= 256) {
                return;
            }
            if (i6 != 0 && i7 == i8) {
                this.func_22197_a("SAVE", i1, i2, i4, "rewrite");
                this.func_22200_a(i6, b3, i4);
            } else {
                int i11;
                int i9 = 0;
                while (i9 < i7) {
                    this.field_22216_f.set(i6 + i9, true);
                    ++i9;
                }
                i9 = this.field_22216_f.indexOf(true);
                int i10 = 0;
                if (i9 != -1) {
                    i11 = i9;
                    while (i11 < this.field_22216_f.size()) {
                        if (i10 != 0) {
                            i10 = ((Boolean)this.field_22216_f.get(i11)).booleanValue() ? ++i10 : 0;
                        } else if (((Boolean)this.field_22216_f.get(i11)).booleanValue()) {
                            i9 = i11;
                            i10 = 1;
                        }
                        if (i10 >= i8) break;
                        ++i11;
                    }
                }
                if (i10 >= i8) {
                    this.func_22197_a("SAVE", i1, i2, i4, "reuse");
                    i6 = i9;
                    this.func_22198_a(i1, i2, i9 << 8 | i8);
                    i11 = 0;
                    while (i11 < i8) {
                        this.field_22216_f.set(i6 + i11, false);
                        ++i11;
                    }
                    this.func_22200_a(i6, b3, i4);
                } else {
                    this.func_22197_a("SAVE", i1, i2, i4, "grow");
                    this.field_22219_c.seek(this.field_22219_c.length());
                    i6 = this.field_22216_f.size();
                    i11 = 0;
                    while (i11 < i8) {
                        this.field_22219_c.write(field_22213_a);
                        this.field_22216_f.add(false);
                        ++i11;
                    }
                    this.field_22215_g += 4096 * i8;
                    this.func_22200_a(i6, b3, i4);
                    this.func_22198_a(i1, i2, i6 << 8 | i8);
                }
            }
            this.func_22208_b(i1, i2, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException iOException12) {
            iOException12.printStackTrace();
        }
    }

    private void func_22200_a(int i1, byte[] b2, int i3) throws IOException {
        this.func_22204_b(" " + i1);
        this.field_22219_c.seek(i1 * 4096);
        this.field_22219_c.writeInt(i3 + 1);
        this.field_22219_c.writeByte(2);
        this.field_22219_c.write(b2, 0, i3);
    }

    private boolean func_22206_d(int i1, int i2) {
        return i1 < 0 || i1 >= 32 || i2 < 0 || i2 >= 32;
    }

    private int func_22207_e(int i1, int i2) {
        return this.field_22218_d[i1 + i2 * 32];
    }

    public boolean func_22202_c(int i1, int i2) {
        return this.func_22207_e(i1, i2) != 0;
    }

    private void func_22198_a(int i1, int i2, int i3) throws IOException {
        this.field_22218_d[i1 + i2 * 32] = i3;
        this.field_22219_c.seek((i1 + i2 * 32) * 4);
        this.field_22219_c.writeInt(i3);
    }

    private void func_22208_b(int i1, int i2, int i3) throws IOException {
        this.field_22217_e[i1 + i2 * 32] = i3;
        this.field_22219_c.seek(4096 + (i1 + i2 * 32) * 4);
        this.field_22219_c.writeInt(i3);
    }

    public void func_22196_b() throws IOException {
        this.field_22219_c.close();
    }
}

