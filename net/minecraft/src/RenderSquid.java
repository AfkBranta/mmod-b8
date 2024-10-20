/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSquid
extends RenderLiving {
    public RenderSquid(ModelBase modelBase1, float f2) {
        super(modelBase1, f2);
    }

    public void func_21008_a(EntitySquid entitySquid1, double d2, double d4, double d6, float f8, float f9) {
        super.doRenderLiving(entitySquid1, d2, d4, d6, f8, f9);
    }

    protected void func_21007_a(EntitySquid entitySquid1, float f2, float f3, float f4) {
        float f5 = entitySquid1.field_21088_b + (entitySquid1.field_21089_a - entitySquid1.field_21088_b) * f4;
        float f6 = entitySquid1.field_21086_f + (entitySquid1.field_21087_c - entitySquid1.field_21086_f) * f4;
        GL11.glTranslatef((float)0.0f, (float)0.5f, (float)0.0f);
        GL11.glRotatef((float)(180.0f - f3), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)f5, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)f6, (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)-1.2f, (float)0.0f);
    }

    protected void func_21005_a(EntitySquid entitySquid1, float f2) {
    }

    protected float func_21006_b(EntitySquid entitySquid1, float f2) {
        float f3 = entitySquid1.field_21082_j + (entitySquid1.field_21083_i - entitySquid1.field_21082_j) * f2;
        return f3;
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.func_21005_a((EntitySquid)entityLiving1, f2);
    }

    @Override
    protected float func_170_d(EntityLiving entityLiving1, float f2) {
        return this.func_21006_b((EntitySquid)entityLiving1, f2);
    }

    @Override
    protected void func_21004_a(EntityLiving entityLiving1, float f2, float f3, float f4) {
        this.func_21007_a((EntitySquid)entityLiving1, f2, f3, f4);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        this.func_21008_a((EntitySquid)entityLiving1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_21008_a((EntitySquid)entity1, d2, d4, d6, f8, f9);
    }
}

