/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityFireball
extends Entity {
    private int field_9402_e = -1;
    private int field_9401_f = -1;
    private int field_9400_g = -1;
    private int field_9399_h = 0;
    private boolean field_9398_i = false;
    public int field_9406_a = 0;
    private EntityLiving field_9397_j;
    private int field_9396_k;
    private int field_9395_l = 0;
    public double field_9405_b;
    public double field_9404_c;
    public double field_9403_d;

    public EntityFireball(World world1) {
        super(world1);
        this.setSize(1.0f, 1.0f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double d1) {
        double d3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        return d1 < (d3 *= 64.0) * d3;
    }

    public EntityFireball(World world1, EntityLiving entityLiving2, double d3, double d5, double d7) {
        super(world1);
        this.field_9397_j = entityLiving2;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(entityLiving2.posX, entityLiving2.posY, entityLiving2.posZ, entityLiving2.rotationYaw, entityLiving2.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        double d9 = MathHelper.sqrt_double((d3 += this.rand.nextGaussian() * 0.4) * d3 + (d5 += this.rand.nextGaussian() * 0.4) * d5 + (d7 += this.rand.nextGaussian() * 0.4) * d7);
        this.field_9405_b = d3 / d9 * 0.1;
        this.field_9404_c = d5 / d9 * 0.1;
        this.field_9403_d = d7 / d9 * 0.1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.fire = 10;
        if (this.field_9406_a > 0) {
            --this.field_9406_a;
        }
        if (this.field_9398_i) {
            int i1 = this.worldObj.getBlockId(this.field_9402_e, this.field_9401_f, this.field_9400_g);
            if (i1 == this.field_9399_h) {
                ++this.field_9396_k;
                if (this.field_9396_k == 1200) {
                    this.setEntityDead();
                }
                return;
            }
            this.field_9398_i = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
            this.field_9396_k = 0;
            this.field_9395_l = 0;
        } else {
            ++this.field_9395_l;
        }
        Vec3D vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D15, vec3D2);
        vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
        vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingObjectPosition3 != null) {
            vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
        }
        Entity entity4 = null;
        List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
        double d6 = 0.0;
        int i8 = 0;
        while (i8 < list5.size()) {
            double d13;
            float f10;
            AxisAlignedBB axisAlignedBB11;
            MovingObjectPosition movingObjectPosition12;
            Entity entity9 = (Entity)list5.get(i8);
            if (entity9.canBeCollidedWith() && (entity9 != this.field_9397_j || this.field_9395_l >= 25) && (movingObjectPosition12 = (axisAlignedBB11 = entity9.boundingBox.expand(f10 = 0.3f, f10, f10)).func_1169_a(vec3D15, vec3D2)) != null && ((d13 = vec3D15.distanceTo(movingObjectPosition12.hitVec)) < d6 || d6 == 0.0)) {
                entity4 = entity9;
                d6 = d13;
            }
            ++i8;
        }
        if (entity4 != null) {
            movingObjectPosition3 = new MovingObjectPosition(entity4);
        }
        if (movingObjectPosition3 != null) {
            if (movingObjectPosition3.entityHit == null || movingObjectPosition3.entityHit.attackEntityFrom(this.field_9397_j, 0)) {
                // empty if block
            }
            this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, 1.0f, true);
            this.setEntityDead();
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
        this.rotationPitch = (float)(Math.atan2(this.motionY, f16) * 180.0 / 3.1415927410125732);
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        float f17 = 0.95f;
        if (this.handleWaterMovement()) {
            int i18 = 0;
            while (i18 < 4) {
                float f19 = 0.25f;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f19, this.posY - this.motionY * (double)f19, this.posZ - this.motionZ * (double)f19, this.motionX, this.motionY, this.motionZ);
                ++i18;
            }
            f17 = 0.8f;
        }
        this.motionX += this.field_9405_b;
        this.motionY += this.field_9404_c;
        this.motionZ += this.field_9403_d;
        this.motionX *= (double)f17;
        this.motionY *= (double)f17;
        this.motionZ *= (double)f17;
        this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("xTile", (short)this.field_9402_e);
        nBTTagCompound1.setShort("yTile", (short)this.field_9401_f);
        nBTTagCompound1.setShort("zTile", (short)this.field_9400_g);
        nBTTagCompound1.setByte("inTile", (byte)this.field_9399_h);
        nBTTagCompound1.setByte("shake", (byte)this.field_9406_a);
        nBTTagCompound1.setByte("inGround", (byte)(this.field_9398_i ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.field_9402_e = nBTTagCompound1.getShort("xTile");
        this.field_9401_f = nBTTagCompound1.getShort("yTile");
        this.field_9400_g = nBTTagCompound1.getShort("zTile");
        this.field_9399_h = nBTTagCompound1.getByte("inTile") & 0xFF;
        this.field_9406_a = nBTTagCompound1.getByte("shake") & 0xFF;
        this.field_9398_i = nBTTagCompound1.getByte("inGround") == 1;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        this.setBeenAttacked();
        if (entity1 != null) {
            Vec3D vec3D3 = entity1.getLookVec();
            if (vec3D3 != null) {
                this.motionX = vec3D3.xCoord;
                this.motionY = vec3D3.yCoord;
                this.motionZ = vec3D3.zCoord;
                this.field_9405_b = this.motionX * 0.1;
                this.field_9404_c = this.motionY * 0.1;
                this.field_9403_d = this.motionZ * 0.1;
            }
            return true;
        }
        return false;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }
}

