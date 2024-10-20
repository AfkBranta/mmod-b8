/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntityDiggingFX
extends EntityFX {
    private Block field_4082_a;

    public EntityDiggingFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12, Block block14) {
        super(world1, d2, d4, d6, d8, d10, d12);
        this.field_4082_a = block14;
        this.particleTextureIndex = block14.blockIndexInTexture;
        this.particleGravity = block14.blockParticleGravity;
        this.particleBlue = 0.6f;
        this.particleGreen = 0.6f;
        this.particleRed = 0.6f;
        this.particleScale /= 2.0f;
    }

    public EntityDiggingFX func_4041_a(int i1, int i2, int i3) {
        if (this.field_4082_a == Block.grass) {
            return this;
        }
        int i4 = this.field_4082_a.colorMultiplier(this.worldObj, i1, i2, i3);
        this.particleRed *= (float)(i4 >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (float)(i4 >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (float)(i4 & 0xFF) / 255.0f;
        return this;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)(this.particleTextureIndex % 16) + this.particleTextureJitterX / 4.0f) / 16.0f;
        float f9 = f8 + 0.015609375f;
        float f10 = ((float)(this.particleTextureIndex / 16) + this.particleTextureJitterY / 4.0f) / 16.0f;
        float f11 = f10 + 0.015609375f;
        float f12 = 0.1f * this.particleScale;
        float f13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f2 - interpPosX);
        float f14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f2 - interpPosY);
        float f15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f2 - interpPosZ);
        float f16 = this.getEntityBrightness(f2);
        tessellator1.setColorOpaque_F(f16 * this.particleRed, f16 * this.particleGreen, f16 * this.particleBlue);
        tessellator1.addVertexWithUV(f13 - f3 * f12 - f6 * f12, f14 - f4 * f12, f15 - f5 * f12 - f7 * f12, f8, f11);
        tessellator1.addVertexWithUV(f13 - f3 * f12 + f6 * f12, f14 + f4 * f12, f15 - f5 * f12 + f7 * f12, f8, f10);
        tessellator1.addVertexWithUV(f13 + f3 * f12 + f6 * f12, f14 + f4 * f12, f15 + f5 * f12 + f7 * f12, f9, f10);
        tessellator1.addVertexWithUV(f13 + f3 * f12 - f6 * f12, f14 - f4 * f12, f15 + f5 * f12 - f7 * f12, f9, f11);
    }
}

