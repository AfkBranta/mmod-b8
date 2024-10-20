/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class StepSound {
    public final String field_1678_a;
    public final float field_1677_b;
    public final float field_1679_c;

    public StepSound(String string1, float f2, float f3) {
        this.field_1678_a = string1;
        this.field_1677_b = f2;
        this.field_1679_c = f3;
    }

    public float func_1147_b() {
        return this.field_1677_b;
    }

    public float func_1144_c() {
        return this.field_1679_c;
    }

    public String func_1146_a() {
        return "step." + this.field_1678_a;
    }

    public String func_1145_d() {
        return "step." + this.field_1678_a;
    }
}

