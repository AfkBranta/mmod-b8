/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.RegionFile;

public class RegionFileCache {
    private static final Map field_22195_a = new HashMap();

    public static synchronized RegionFile func_22193_a(File file0, int i1, int i2) {
        RegionFile regionFile6;
        File file3 = new File(file0, "region");
        File file4 = new File(file3, "r." + (i1 >> 5) + "." + (i2 >> 5) + ".mcr");
        Reference reference5 = (Reference)field_22195_a.get(file4);
        if (reference5 != null && (regionFile6 = (RegionFile)reference5.get()) != null) {
            return regionFile6;
        }
        if (!file3.exists()) {
            file3.mkdirs();
        }
        if (field_22195_a.size() >= 256) {
            RegionFileCache.func_22192_a();
        }
        regionFile6 = new RegionFile(file4);
        field_22195_a.put(file4, new SoftReference<RegionFile>(regionFile6));
        return regionFile6;
    }

    public static synchronized void func_22192_a() {
        for (Reference reference1 : field_22195_a.values()) {
            try {
                RegionFile regionFile2 = (RegionFile)reference1.get();
                if (regionFile2 == null) continue;
                regionFile2.func_22196_b();
            }
            catch (IOException iOException3) {
                iOException3.printStackTrace();
            }
        }
        field_22195_a.clear();
    }

    public static int func_22191_b(File file0, int i1, int i2) {
        RegionFile regionFile3 = RegionFileCache.func_22193_a(file0, i1, i2);
        return regionFile3.func_22209_a();
    }

    public static DataInputStream getChunkInputStream(File file0, int i1, int i2) {
        RegionFile regionFile3 = RegionFileCache.func_22193_a(file0, i1, i2);
        return regionFile3.func_22210_a(i1 & 0x1F, i2 & 0x1F);
    }

    public static DataOutputStream getChunkOutputStream(File file0, int i1, int i2) {
        RegionFile regionFile3 = RegionFileCache.func_22193_a(file0, i1, i2);
        return regionFile3.func_22205_b(i1 & 0x1F, i2 & 0x1F);
    }
}

