/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.StatBasic;

public class StatCrafting
extends StatBasic {
    private final int field_25073_a;

    public StatCrafting(int i1, String string2, int i3) {
        super(i1, string2);
        this.field_25073_a = i3;
    }

    public int func_25072_b() {
        return this.field_25073_a;
    }
}

