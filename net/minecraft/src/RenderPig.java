/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPig;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

public class RenderPig
extends RenderLiving {
    public RenderPig(ModelBase modelBase1, ModelBase modelBase2, float f3) {
        super(modelBase1, f3);
        this.setRenderPassModel(modelBase2);
    }

    protected boolean renderSaddledPig(EntityPig entityPig1, int i2, float f3) {
        this.loadTexture("/mob/saddle.png");
        return i2 == 0 && entityPig1.getSaddled();
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return this.renderSaddledPig((EntityPig)entityLiving1, i2, f3);
    }
}

