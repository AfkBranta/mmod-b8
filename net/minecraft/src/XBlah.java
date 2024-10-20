/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.StatBasic;

public class XBlah {
    private Map field_25102_a = new HashMap();
    private Map field_25101_b = new HashMap();

    public void func_25100_a(StatBasic statBasic1, int i2) {
        Integer integer3 = (Integer)this.field_25101_b.get(statBasic1);
        if (integer3 != null) {
            i2 += integer3.intValue();
        }
        this.field_25101_b.put(statBasic1, i2);
    }
}

