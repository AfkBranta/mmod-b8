/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import org.lwjgl.opengl.GL11;

public class RenderSheep
extends RenderLiving {
    public RenderSheep(ModelBase modelBase1, ModelBase modelBase2, float f3) {
        super(modelBase1, f3);
        this.setRenderPassModel(modelBase2);
    }

    protected boolean func_176_a(EntitySheep entitySheep1, int i2, float f3) {
        if (i2 == 0 && !entitySheep1.getSheared()) {
            this.loadTexture("/mob/sheep_fur.png");
            float f4 = entitySheep1.getEntityBrightness(f3);
            int i5 = entitySheep1.getFleeceColor();
            GL11.glColor3f((float)(f4 * EntitySheep.fleeceColorTable[i5][0]), (float)(f4 * EntitySheep.fleeceColorTable[i5][1]), (float)(f4 * EntitySheep.fleeceColorTable[i5][2]));
            return true;
        }
        return false;
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return this.func_176_a((EntitySheep)entityLiving1, i2, f3);
    }
}

