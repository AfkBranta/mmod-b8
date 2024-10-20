/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class RenderCow
extends RenderLiving {
    public RenderCow(ModelBase modelBase1, float f2) {
        super(modelBase1, f2);
    }

    public void func_177_a(EntityCow entityCow1, double d2, double d4, double d6, float f8, float f9) {
        super.doRenderLiving(entityCow1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        this.func_177_a((EntityCow)entityLiving1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_177_a((EntityCow)entity1, d2, d4, d6, f8, f9);
    }
}

