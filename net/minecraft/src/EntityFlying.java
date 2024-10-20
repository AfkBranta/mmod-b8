/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityFlying
extends EntityLiving {
    public EntityFlying(World world1) {
        super(world1);
    }

    @Override
    protected void fall(float f1) {
    }

    @Override
    public void moveEntityWithHeading(float f1, float f2) {
        if (this.handleWaterMovement()) {
            this.moveFlying(f1, f2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.8f;
            this.motionY *= (double)0.8f;
            this.motionZ *= (double)0.8f;
        } else if (this.handleLavaMovement()) {
            this.moveFlying(f1, f2, 0.02f);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        } else {
            float f3 = 0.91f;
            if (this.onGround) {
                f3 = 0.54600006f;
                int i4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (i4 > 0) {
                    f3 = Block.blocksList[i4].slipperiness * 0.91f;
                }
            }
            float f8 = 0.16277136f / (f3 * f3 * f3);
            this.moveFlying(f1, f2, this.onGround ? 0.1f * f8 : 0.02f);
            f3 = 0.91f;
            if (this.onGround) {
                f3 = 0.54600006f;
                int i5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
                if (i5 > 0) {
                    f3 = Block.blocksList[i5].slipperiness * 0.91f;
                }
            }
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f3;
            this.motionY *= (double)f3;
            this.motionZ *= (double)f3;
        }
        this.field_705_Q = this.field_704_R;
        double d10 = this.posX - this.prevPosX;
        double d9 = this.posZ - this.prevPosZ;
        float f7 = MathHelper.sqrt_double(d10 * d10 + d9 * d9) * 4.0f;
        if (f7 > 1.0f) {
            f7 = 1.0f;
        }
        this.field_704_R += (f7 - this.field_704_R) * 0.4f;
        this.field_703_S += this.field_704_R;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}

