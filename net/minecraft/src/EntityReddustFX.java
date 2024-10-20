/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityReddustFX
extends EntityFX {
    float field_673_a;

    public EntityReddustFX(World world1, double d2, double d4, double d6, float f8, float f9, float f10) {
        this(world1, d2, d4, d6, 1.0f, f8, f9, f10);
    }

    public EntityReddustFX(World world1, double d2, double d4, double d6, float f8, float f9, float f10, float f11) {
        super(world1, d2, d4, d6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.1f;
        this.motionY *= (double)0.1f;
        this.motionZ *= (double)0.1f;
        if (f9 == 0.0f) {
            f9 = 1.0f;
        }
        float f12 = (float)Math.random() * 0.4f + 0.6f;
        this.particleRed = ((float)(Math.random() * (double)0.2f) + 0.8f) * f9 * f12;
        this.particleGreen = ((float)(Math.random() * (double)0.2f) + 0.8f) * f10 * f12;
        this.particleBlue = ((float)(Math.random() * (double)0.2f) + 0.8f) * f11 * f12;
        this.particleScale *= 0.75f;
        this.particleScale *= f8;
        this.field_673_a = this.particleScale;
        this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int)((float)this.particleMaxAge * f8);
        this.noClip = false;
    }

    @Override
    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.particleAge + f2) / (float)this.particleMaxAge * 32.0f;
        if (f8 < 0.0f) {
            f8 = 0.0f;
        }
        if (f8 > 1.0f) {
            f8 = 1.0f;
        }
        this.particleScale = this.field_673_a * f8;
        super.renderParticle(tessellator1, f2, f3, f4, f5, f6, f7);
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setEntityDead();
        }
        this.particleTextureIndex = 7 - this.particleAge * 8 / this.particleMaxAge;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= (double)0.96f;
        this.motionY *= (double)0.96f;
        this.motionZ *= (double)0.96f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

