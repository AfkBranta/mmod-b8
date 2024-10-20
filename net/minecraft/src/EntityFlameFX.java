/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityFlameFX
extends EntityFX {
    private float field_672_a;

    public EntityFlameFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        super(world1, d2, d4, d6, d8, d10, d12);
        this.motionX = this.motionX * (double)0.01f + d8;
        this.motionY = this.motionY * (double)0.01f + d10;
        this.motionZ = this.motionZ * (double)0.01f + d12;
        double d10000 = d2 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        d10000 = d4 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        d10000 = d6 + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05f);
        this.field_672_a = this.particleScale;
        this.particleBlue = 1.0f;
        this.particleGreen = 1.0f;
        this.particleRed = 1.0f;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.noClip = true;
        this.particleTextureIndex = 48;
    }

    @Override
    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.particleAge + f2) / (float)this.particleMaxAge;
        this.particleScale = this.field_672_a * (1.0f - f8 * f8 * 0.5f);
        super.renderParticle(tessellator1, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public float getEntityBrightness(float f1) {
        float f2 = ((float)this.particleAge + f1) / (float)this.particleMaxAge;
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        float f3 = super.getEntityBrightness(f1);
        return f3 * f2 + (1.0f - f2);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)0.96f;
        this.motionY *= (double)0.96f;
        this.motionZ *= (double)0.96f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

