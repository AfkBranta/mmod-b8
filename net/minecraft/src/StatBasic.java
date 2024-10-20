/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.StatList;

public class StatBasic {
    public final int field_25071_d;
    public final String field_25070_e;
    public String field_25069_f;

    public StatBasic(int i1, String string2) {
        this.field_25071_d = i1;
        this.field_25070_e = string2;
    }

    public StatBasic func_25068_c() {
        StatList.func_25152_a(this);
        return this;
    }

    public boolean func_25067_a() {
        return false;
    }
}

