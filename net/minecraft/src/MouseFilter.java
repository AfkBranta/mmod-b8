/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class MouseFilter {
    private float field_22388_a;
    private float field_22387_b;
    private float field_22389_c;

    public float func_22386_a(float f1, float f2) {
        this.field_22388_a += f1;
        f1 = (this.field_22388_a - this.field_22387_b) * f2;
        this.field_22389_c += (f1 - this.field_22389_c) * 0.5f;
        if (f1 > 0.0f && f1 > this.field_22389_c || f1 < 0.0f && f1 < this.field_22389_c) {
            f1 = this.field_22389_c;
        }
        this.field_22387_b += f1;
        return f1;
    }
}

