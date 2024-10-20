/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityPortalFX
extends EntityFX {
    private float field_4083_a;
    private double field_4086_p;
    private double field_4085_q;
    private double field_4084_r;

    public EntityPortalFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        super(world1, d2, d4, d6, d8, d10, d12);
        this.motionX = d8;
        this.motionY = d10;
        this.motionZ = d12;
        this.field_4086_p = this.posX = d2;
        this.field_4085_q = this.posY = d4;
        this.field_4084_r = this.posZ = d6;
        float f14 = this.rand.nextFloat() * 0.6f + 0.4f;
        this.field_4083_a = this.particleScale = this.rand.nextFloat() * 0.2f + 0.5f;
        this.particleGreen = this.particleBlue = 1.0f * f14;
        this.particleRed = this.particleBlue;
        this.particleGreen *= 0.3f;
        this.particleRed *= 0.9f;
        this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
        this.noClip = true;
        this.particleTextureIndex = (int)(Math.random() * 8.0);
    }

    @Override
    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.particleAge + f2) / (float)this.particleMaxAge;
        f8 = 1.0f - f8;
        f8 *= f8;
        f8 = 1.0f - f8;
        this.particleScale = this.field_4083_a * f8;
        super.renderParticle(tessellator1, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public float getEntityBrightness(float f1) {
        float f2 = super.getEntityBrightness(f1);
        float f3 = (float)this.particleAge / (float)this.particleMaxAge;
        f3 *= f3;
        f3 *= f3;
        return f2 * (1.0f - f3) + f3;
    }

    @Override
    public void onUpdate() {
        float f1;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f2 = f1 = (float)this.particleAge / (float)this.particleMaxAge;
        f1 = -f1 + f1 * f1 * 2.0f;
        f1 = 1.0f - f1;
        this.posX = this.field_4086_p + this.motionX * (double)f1;
        this.posY = this.field_4085_q + this.motionY * (double)f1 + (double)(1.0f - f2);
        this.posZ = this.field_4084_r + this.motionZ * (double)f1;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
    }
}

