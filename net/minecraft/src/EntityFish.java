/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.StatList;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityFish
extends Entity {
    private int tileX = -1;
    private int tileY = -1;
    private int tileZ = -1;
    private int field_4092_g = 0;
    private boolean field_4091_h = false;
    public int field_4098_a = 0;
    public EntityPlayer angler;
    private int field_4090_i;
    private int field_4089_j = 0;
    private int field_4088_k = 0;
    public Entity field_4096_c = null;
    private int field_6388_l;
    private double field_6387_m;
    private double field_6386_n;
    private double field_6385_o;
    private double field_6384_p;
    private double field_6383_q;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntityFish(World world1) {
        super(world1);
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public boolean isInRangeToRenderDist(double d1) {
        double d3 = this.boundingBox.getAverageEdgeLength() * 4.0;
        return d1 < (d3 *= 64.0) * d3;
    }

    public EntityFish(World world1, double d2, double d4, double d6) {
        this(world1);
        this.setPosition(d2, d4, d6);
    }

    public EntityFish(World world1, EntityPlayer entityPlayer2) {
        super(world1);
        this.angler = entityPlayer2;
        this.angler.fishEntity = this;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(entityPlayer2.posX, entityPlayer2.posY + 1.62 - (double)entityPlayer2.yOffset, entityPlayer2.posZ, entityPlayer2.rotationYaw, entityPlayer2.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.posY -= (double)0.1f;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        float f3 = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * (float)Math.PI) * f3;
        this.func_4042_a(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }

    public void func_4042_a(double d1, double d3, double d5, float f7, float f8) {
        float f9 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
        d1 /= (double)f9;
        d3 /= (double)f9;
        d5 /= (double)f9;
        d1 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        d3 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        d5 += this.rand.nextGaussian() * (double)0.0075f * (double)f8;
        this.motionX = d1 *= (double)f7;
        this.motionY = d3 *= (double)f7;
        this.motionZ = d5 *= (double)f7;
        float f10 = MathHelper.sqrt_double(d1 * d1 + d5 * d5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d1, d5) * 180.0 / 3.1415927410125732);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d3, f10) * 180.0 / 3.1415927410125732);
        this.field_4090_i = 0;
    }

    @Override
    public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
        this.field_6387_m = d1;
        this.field_6386_n = d3;
        this.field_6385_o = d5;
        this.field_6384_p = f7;
        this.field_6383_q = f8;
        this.field_6388_l = i9;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @Override
    public void setVelocity(double d1, double d3, double d5) {
        this.velocityX = this.motionX = d1;
        this.velocityY = this.motionY = d3;
        this.velocityZ = this.motionZ = d5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.field_6388_l > 0) {
            double d21 = this.posX + (this.field_6387_m - this.posX) / (double)this.field_6388_l;
            double d22 = this.posY + (this.field_6386_n - this.posY) / (double)this.field_6388_l;
            double d23 = this.posZ + (this.field_6385_o - this.posZ) / (double)this.field_6388_l;
            double d7 = this.field_6384_p - (double)this.rotationYaw;
            while (d7 < -180.0) {
                d7 += 360.0;
            }
            while (d7 >= 180.0) {
                d7 -= 360.0;
            }
            this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.field_6388_l);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.field_6383_q - (double)this.rotationPitch) / (double)this.field_6388_l);
            --this.field_6388_l;
            this.setPosition(d21, d22, d23);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else {
            double d13;
            if (!this.worldObj.multiplayerWorld) {
                ItemStack itemStack1 = this.angler.getCurrentEquippedItem();
                if (this.angler.isDead || !this.angler.isEntityAlive() || itemStack1 == null || itemStack1.getItem() != Item.fishingRod || this.getDistanceSqToEntity(this.angler) > 1024.0) {
                    this.setEntityDead();
                    this.angler.fishEntity = null;
                    return;
                }
                if (this.field_4096_c != null) {
                    if (!this.field_4096_c.isDead) {
                        this.posX = this.field_4096_c.posX;
                        this.posY = this.field_4096_c.boundingBox.minY + (double)this.field_4096_c.height * 0.8;
                        this.posZ = this.field_4096_c.posZ;
                        return;
                    }
                    this.field_4096_c = null;
                }
            }
            if (this.field_4098_a > 0) {
                --this.field_4098_a;
            }
            if (this.field_4091_h) {
                int i19 = this.worldObj.getBlockId(this.tileX, this.tileY, this.tileZ);
                if (i19 == this.field_4092_g) {
                    ++this.field_4090_i;
                    if (this.field_4090_i == 1200) {
                        this.setEntityDead();
                    }
                    return;
                }
                this.field_4091_h = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.field_4090_i = 0;
                this.field_4089_j = 0;
            } else {
                ++this.field_4089_j;
            }
            Vec3D vec3D20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
            Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D20, vec3D2);
            vec3D20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
            vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (movingObjectPosition3 != null) {
                vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
            }
            Entity entity4 = null;
            List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double d6 = 0.0;
            int i8 = 0;
            while (i8 < list5.size()) {
                float f10;
                AxisAlignedBB axisAlignedBB11;
                MovingObjectPosition movingObjectPosition12;
                Entity entity9 = (Entity)list5.get(i8);
                if (entity9.canBeCollidedWith() && (entity9 != this.angler || this.field_4089_j >= 5) && (movingObjectPosition12 = (axisAlignedBB11 = entity9.boundingBox.expand(f10 = 0.3f, f10, f10)).func_1169_a(vec3D20, vec3D2)) != null && ((d13 = vec3D20.distanceTo(movingObjectPosition12.hitVec)) < d6 || d6 == 0.0)) {
                    entity4 = entity9;
                    d6 = d13;
                }
                ++i8;
            }
            if (entity4 != null) {
                movingObjectPosition3 = new MovingObjectPosition(entity4);
            }
            if (movingObjectPosition3 != null) {
                if (movingObjectPosition3.entityHit != null) {
                    if (movingObjectPosition3.entityHit.attackEntityFrom(this.angler, 0)) {
                        this.field_4096_c = movingObjectPosition3.entityHit;
                    }
                } else {
                    this.field_4091_h = true;
                }
            }
            if (!this.field_4091_h) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                float f24 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.1415927410125732);
                this.rotationPitch = (float)(Math.atan2(this.motionY, f24) * 180.0 / 3.1415927410125732);
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
                float f25 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    f25 = 0.5f;
                }
                int b26 = 5;
                double d27 = 0.0;
                int i28 = 0;
                while (i28 < b26) {
                    double d14 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i28 + 0) / (double)b26 - 0.125 + 0.125;
                    double d16 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i28 + 1) / (double)b26 - 0.125 + 0.125;
                    AxisAlignedBB axisAlignedBB18 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d14, this.boundingBox.minZ, this.boundingBox.maxX, d16, this.boundingBox.maxZ);
                    if (this.worldObj.isAABBInMaterial(axisAlignedBB18, Material.water)) {
                        d27 += 1.0 / (double)b26;
                    }
                    ++i28;
                }
                if (d27 > 0.0) {
                    if (this.field_4088_k > 0) {
                        --this.field_4088_k;
                    } else if (this.rand.nextInt(500) == 0) {
                        this.field_4088_k = this.rand.nextInt(30) + 10;
                        this.motionY -= (double)0.2f;
                        this.worldObj.playSoundAtEntity(this, "random.splash", 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                        float f29 = MathHelper.floor_double(this.boundingBox.minY);
                        int i30 = 0;
                        while ((float)i30 < 1.0f + this.width * 20.0f) {
                            float f15 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            float f31 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.worldObj.spawnParticle("bubble", this.posX + (double)f15, f29 + 1.0f, this.posZ + (double)f31, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2f), this.motionZ);
                            ++i30;
                        }
                        i30 = 0;
                        while ((float)i30 < 1.0f + this.width * 20.0f) {
                            float f15 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            float f31 = (this.rand.nextFloat() * 2.0f - 1.0f) * this.width;
                            this.worldObj.spawnParticle("splash", this.posX + (double)f15, f29 + 1.0f, this.posZ + (double)f31, this.motionX, this.motionY, this.motionZ);
                            ++i30;
                        }
                    }
                }
                if (this.field_4088_k > 0) {
                    this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
                }
                d13 = d27 * 2.0 - 1.0;
                this.motionY += (double)0.04f * d13;
                if (d27 > 0.0) {
                    f25 = (float)((double)f25 * 0.9);
                    this.motionY *= 0.8;
                }
                this.motionX *= (double)f25;
                this.motionY *= (double)f25;
                this.motionZ *= (double)f25;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        nBTTagCompound1.setShort("xTile", (short)this.tileX);
        nBTTagCompound1.setShort("yTile", (short)this.tileY);
        nBTTagCompound1.setShort("zTile", (short)this.tileZ);
        nBTTagCompound1.setByte("inTile", (byte)this.field_4092_g);
        nBTTagCompound1.setByte("shake", (byte)this.field_4098_a);
        nBTTagCompound1.setByte("inGround", (byte)(this.field_4091_h ? 1 : 0));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        this.tileX = nBTTagCompound1.getShort("xTile");
        this.tileY = nBTTagCompound1.getShort("yTile");
        this.tileZ = nBTTagCompound1.getShort("zTile");
        this.field_4092_g = nBTTagCompound1.getByte("inTile") & 0xFF;
        this.field_4098_a = nBTTagCompound1.getByte("shake") & 0xFF;
        this.field_4091_h = nBTTagCompound1.getByte("inGround") == 1;
    }

    @Override
    public float getShadowSize() {
        return 0.0f;
    }

    public int catchFish() {
        int b1 = 0;
        if (this.field_4096_c != null) {
            double d2 = this.angler.posX - this.posX;
            double d4 = this.angler.posY - this.posY;
            double d6 = this.angler.posZ - this.posZ;
            double d8 = MathHelper.sqrt_double(d2 * d2 + d4 * d4 + d6 * d6);
            double d10 = 0.1;
            this.field_4096_c.motionX += d2 * d10;
            this.field_4096_c.motionY += d4 * d10 + (double)MathHelper.sqrt_double(d8) * 0.08;
            this.field_4096_c.motionZ += d6 * d10;
            b1 = 3;
        } else if (this.field_4088_k > 0) {
            EntityItem entityItem13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.fishRaw));
            double d3 = this.angler.posX - this.posX;
            double d5 = this.angler.posY - this.posY;
            double d7 = this.angler.posZ - this.posZ;
            double d9 = MathHelper.sqrt_double(d3 * d3 + d5 * d5 + d7 * d7);
            double d11 = 0.1;
            entityItem13.motionX = d3 * d11;
            entityItem13.motionY = d5 * d11 + (double)MathHelper.sqrt_double(d9) * 0.08;
            entityItem13.motionZ = d7 * d11;
            this.worldObj.entityJoinedWorld(entityItem13);
            this.angler.addStat(StatList.field_25160_x, 1);
            b1 = 1;
        }
        if (this.field_4091_h) {
            b1 = 2;
        }
        this.setEntityDead();
        this.angler.fishEntity = null;
        return b1;
    }
}

