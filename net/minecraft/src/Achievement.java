/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.StatBasic;

public class Achievement
extends StatBasic {
    public final int field_25075_a;
    public final int field_25074_b;
    public final Achievement field_25076_c;

    public Achievement(int i1, String string2, int i3, int i4, Achievement achievement5) {
        super(i1, string2);
        this.field_25075_a = i3 + 46;
        this.field_25074_b = i4 + 23;
        this.field_25076_c = achievement5;
    }

    @Override
    public boolean func_25067_a() {
        return true;
    }
}

