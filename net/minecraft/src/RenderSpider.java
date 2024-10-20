/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.ModelSpider;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSpider
extends RenderLiving {
    public RenderSpider() {
        super(new ModelSpider(), 1.0f);
        this.setRenderPassModel(new ModelSpider());
    }

    protected float func_191_a(EntitySpider entitySpider1) {
        return 180.0f;
    }

    protected boolean setSpiderEyeBrightness(EntitySpider entitySpider1, int i2, float f3) {
        if (i2 != 0) {
            return false;
        }
        if (i2 != 0) {
            return false;
        }
        this.loadTexture("/mob/spider_eyes.png");
        float f4 = (1.0f - entitySpider1.getEntityBrightness(1.0f)) * 0.5f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)f4);
        return true;
    }

    @Override
    protected float func_172_a(EntityLiving entityLiving1) {
        return this.func_191_a((EntitySpider)entityLiving1);
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return this.setSpiderEyeBrightness((EntitySpider)entityLiving1, i2, f3);
    }
}

