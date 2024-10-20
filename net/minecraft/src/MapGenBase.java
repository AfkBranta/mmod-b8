/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;

public class MapGenBase {
    protected int field_1306_a = 8;
    protected Random rand = new Random();

    public void func_867_a(IChunkProvider iChunkProvider1, World world2, int i3, int i4, byte[] b5) {
        int i6 = this.field_1306_a;
        this.rand.setSeed(world2.getRandomSeed());
        long j7 = this.rand.nextLong() / 2L * 2L + 1L;
        long j9 = this.rand.nextLong() / 2L * 2L + 1L;
        int i11 = i3 - i6;
        while (i11 <= i3 + i6) {
            int i12 = i4 - i6;
            while (i12 <= i4 + i6) {
                this.rand.setSeed((long)i11 * j7 + (long)i12 * j9 ^ world2.getRandomSeed());
                this.func_868_a(world2, i11, i12, i3, i4, b5);
                ++i12;
            }
            ++i11;
        }
    }

    protected void func_868_a(World world1, int i2, int i3, int i4, int i5, byte[] b6) {
    }
}

