/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class RenderWolf
extends RenderLiving {
    public RenderWolf(ModelBase modelBase1, float f2) {
        super(modelBase1, f2);
    }

    public void func_25005_a(EntityWolf entityWolf1, double d2, double d4, double d6, float f8, float f9) {
        super.doRenderLiving(entityWolf1, d2, d4, d6, f8, f9);
    }

    protected float func_25004_a(EntityWolf entityWolf1, float f2) {
        return entityWolf1.func_25037_z();
    }

    protected void func_25006_b(EntityWolf entityWolf1, float f2) {
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.func_25006_b((EntityWolf)entityLiving1, f2);
    }

    @Override
    protected float func_170_d(EntityLiving entityLiving1, float f2) {
        return this.func_25004_a((EntityWolf)entityLiving1, f2);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        this.func_25005_a((EntityWolf)entityLiving1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_25005_a((EntityWolf)entity1, d2, d4, d6, f8, f9);
    }
}

