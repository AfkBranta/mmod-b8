/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityChicken;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class RenderChicken
extends RenderLiving {
    public RenderChicken(ModelBase modelBase1, float f2) {
        super(modelBase1, f2);
    }

    public void func_181_a(EntityChicken entityChicken1, double d2, double d4, double d6, float f8, float f9) {
        super.doRenderLiving(entityChicken1, d2, d4, d6, f8, f9);
    }

    protected float func_182_a(EntityChicken entityChicken1, float f2) {
        float f3 = entityChicken1.field_756_e + (entityChicken1.field_752_b - entityChicken1.field_756_e) * f2;
        float f4 = entityChicken1.field_757_d + (entityChicken1.field_758_c - entityChicken1.field_757_d) * f2;
        return (MathHelper.sin(f3) + 1.0f) * f4;
    }

    @Override
    protected float func_170_d(EntityLiving entityLiving1, float f2) {
        return this.func_182_a((EntityChicken)entityLiving1, f2);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        this.func_181_a((EntityChicken)entityLiving1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_181_a((EntityChicken)entity1, d2, d4, d6, f8, f9);
    }
}

