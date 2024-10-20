/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelCreeper;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderCreeper
extends RenderLiving {
    public RenderCreeper() {
        super(new ModelCreeper(), 0.5f);
    }

    protected void updateCreeperScale(EntityCreeper entityCreeper1, float f2) {
        float f4 = entityCreeper1.setCreeperFlashTime(f2);
        float f5 = 1.0f + MathHelper.sin(f4 * 100.0f) * f4 * 0.01f;
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 > 1.0f) {
            f4 = 1.0f;
        }
        f4 *= f4;
        f4 *= f4;
        float f6 = (1.0f + f4 * 0.4f) * f5;
        float f7 = (1.0f + f4 * 0.1f) / f5;
        GL11.glScalef((float)f6, (float)f7, (float)f6);
    }

    protected int updateCreeperColorMultiplier(EntityCreeper entityCreeper1, float f2, float f3) {
        float f5 = entityCreeper1.setCreeperFlashTime(f3);
        if ((int)(f5 * 10.0f) % 2 == 0) {
            return 0;
        }
        int i6 = (int)(f5 * 0.2f * 255.0f);
        if (i6 < 0) {
            i6 = 0;
        }
        if (i6 > 255) {
            i6 = 255;
        }
        int s7 = 255;
        int s8 = 255;
        int s9 = 255;
        return i6 << 24 | s7 << 16 | s8 << 8 | s9;
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.updateCreeperScale((EntityCreeper)entityLiving1, f2);
    }

    @Override
    protected int getColorMultiplier(EntityLiving entityLiving1, float f2, float f3) {
        return this.updateCreeperColorMultiplier((EntityCreeper)entityLiving1, f2, f3);
    }
}

