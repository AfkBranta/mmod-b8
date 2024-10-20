/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSlime
extends RenderLiving {
    private ModelBase scaleAmount;

    public RenderSlime(ModelBase modelBase1, ModelBase modelBase2, float f3) {
        super(modelBase1, f3);
        this.scaleAmount = modelBase2;
    }

    protected boolean func_179_a(EntitySlime entitySlime1, int i2, float f3) {
        if (i2 == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable((int)2977);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            return true;
        }
        if (i2 == 1) {
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
        return false;
    }

    protected void func_178_a(EntitySlime entitySlime1, float f2) {
        int i3 = entitySlime1.getSlimeSize();
        float f4 = (entitySlime1.field_767_b + (entitySlime1.field_768_a - entitySlime1.field_767_b) * f2) / ((float)i3 * 0.5f + 1.0f);
        float f5 = 1.0f / (f4 + 1.0f);
        float f6 = i3;
        GL11.glScalef((float)(f5 * f6), (float)(1.0f / f5 * f6), (float)(f5 * f6));
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.func_178_a((EntitySlime)entityLiving1, f2);
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return this.func_179_a((EntitySlime)entityLiving1, i2, f3);
    }
}

