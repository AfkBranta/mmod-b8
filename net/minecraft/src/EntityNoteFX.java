/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityNoteFX
extends EntityFX {
    float field_21065_a;

    public EntityNoteFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        this(world1, d2, d4, d6, d8, d10, d12, 2.0f);
    }

    public EntityNoteFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12, float f14) {
        super(world1, d2, d4, d6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.01f;
        this.motionY *= (double)0.01f;
        this.motionZ *= (double)0.01f;
        this.motionY += 0.2;
        this.particleRed = MathHelper.sin(((float)d8 + 0.0f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleGreen = MathHelper.sin(((float)d8 + 0.33333334f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleBlue = MathHelper.sin(((float)d8 + 0.6666667f) * (float)Math.PI * 2.0f) * 0.65f + 0.35f;
        this.particleScale *= 0.75f;
        this.particleScale *= f14;
        this.field_21065_a = this.particleScale;
        this.particleMaxAge = 6;
        this.noClip = false;
        this.particleTextureIndex = 64;
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
        this.particleScale = this.field_21065_a * f8;
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
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
        this.motionX *= (double)0.66f;
        this.motionY *= (double)0.66f;
        this.motionZ *= (double)0.66f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

