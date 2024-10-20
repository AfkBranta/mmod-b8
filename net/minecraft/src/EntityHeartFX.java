/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityHeartFX
extends EntityFX {
    float field_25022_a;

    public EntityHeartFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        this(world1, d2, d4, d6, d8, d10, d12, 2.0f);
    }

    public EntityHeartFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12, float f14) {
        super(world1, d2, d4, d6, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.01f;
        this.motionY *= (double)0.01f;
        this.motionZ *= (double)0.01f;
        this.motionY += 0.1;
        this.particleScale *= 0.75f;
        this.particleScale *= f14;
        this.field_25022_a = this.particleScale;
        this.particleMaxAge = 16;
        this.noClip = false;
        this.particleTextureIndex = 80;
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
        this.particleScale = this.field_25022_a * f8;
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
        this.motionX *= (double)0.86f;
        this.motionY *= (double)0.86f;
        this.motionZ *= (double)0.86f;
        if (this.onGround) {
            this.motionX *= (double)0.7f;
            this.motionZ *= (double)0.7f;
        }
    }
}

