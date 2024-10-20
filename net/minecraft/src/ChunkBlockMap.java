/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;

public class ChunkBlockMap {
    private static byte[] field_26003_a = new byte[256];

    static {
        try {
            int i0 = 0;
            while (i0 < 256) {
                byte b1 = (byte)i0;
                if (b1 != 0 && Block.blocksList[b1 & 0xFF] == null) {
                    b1 = 0;
                }
                ChunkBlockMap.field_26003_a[i0] = b1;
                ++i0;
            }
        }
        catch (Exception exception2) {
            exception2.printStackTrace();
        }
    }

    public static void func_26002_a(byte[] b0) {
        int i1 = 0;
        while (i1 < b0.length) {
            b0[i1] = field_26003_a[b0[i1] & 0xFF];
            ++i1;
        }
    }
}

