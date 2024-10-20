/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PathEntity;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class EntityCreature
extends EntityLiving {
    private PathEntity pathToEntity;
    protected Entity playerToAttack;
    protected boolean hasAttacked = false;

    public EntityCreature(World world1) {
        super(world1);
    }

    protected boolean func_25028_d_() {
        return false;
    }

    @Override
    protected void updatePlayerActionState() {
        this.hasAttacked = this.func_25028_d_();
        float f1 = 16.0f;
        if (this.playerToAttack == null) {
            this.playerToAttack = this.findPlayerToAttack();
            if (this.playerToAttack != null) {
                this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, f1);
            }
        } else if (!this.playerToAttack.isEntityAlive()) {
            this.playerToAttack = null;
        } else {
            float f2 = this.playerToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.playerToAttack)) {
                this.attackEntity(this.playerToAttack, f2);
            }
        }
        if (!(this.hasAttacked || this.playerToAttack == null || this.pathToEntity != null && this.rand.nextInt(20) != 0)) {
            this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, f1);
        } else if (!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(80) == 0 || this.rand.nextInt(80) == 0)) {
            boolean z21 = false;
            int i3 = -1;
            int i4 = -1;
            int i5 = -1;
            float f6 = -99999.0f;
            int i7 = 0;
            while (i7 < 10) {
                int i10;
                int i9;
                int i8 = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0);
                float f11 = this.getBlockPathWeight(i8, i9 = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0), i10 = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0));
                if (f11 > f6) {
                    f6 = f11;
                    i3 = i8;
                    i4 = i9;
                    i5 = i10;
                    z21 = true;
                }
                ++i7;
            }
            if (z21) {
                this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, i3, i4, i5, 10.0f);
            }
        }
        int i22 = MathHelper.floor_double(this.boundingBox.minY);
        boolean z23 = this.handleWaterMovement();
        boolean z24 = this.handleLavaMovement();
        this.rotationPitch = 0.0f;
        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            Vec3D vec3D25 = this.pathToEntity.getPosition(this);
            double d26 = this.width * 2.0f;
            while (vec3D25 != null && vec3D25.squareDistanceTo(this.posX, vec3D25.yCoord, this.posZ) < d26 * d26) {
                this.pathToEntity.incrementPathIndex();
                if (this.pathToEntity.isFinished()) {
                    vec3D25 = null;
                    this.pathToEntity = null;
                    continue;
                }
                vec3D25 = this.pathToEntity.getPosition(this);
            }
            this.isJumping = false;
            if (vec3D25 != null) {
                double d27 = vec3D25.xCoord - this.posX;
                double d28 = vec3D25.zCoord - this.posZ;
                double d12 = vec3D25.yCoord - (double)i22;
                float f14 = (float)(Math.atan2(d28, d27) * 180.0 / 3.1415927410125732) - 90.0f;
                float f15 = f14 - this.rotationYaw;
                this.moveForward = this.moveSpeed;
                while (f15 < -180.0f) {
                    f15 += 360.0f;
                }
                while (f15 >= 180.0f) {
                    f15 -= 360.0f;
                }
                if (f15 > 30.0f) {
                    f15 = 30.0f;
                }
                if (f15 < -30.0f) {
                    f15 = -30.0f;
                }
                this.rotationYaw += f15;
                if (this.hasAttacked && this.playerToAttack != null) {
                    double d16 = this.playerToAttack.posX - this.posX;
                    double d18 = this.playerToAttack.posZ - this.posZ;
                    float f20 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(d18, d16) * 180.0 / 3.1415927410125732) - 90.0f;
                    f15 = (f20 - this.rotationYaw + 90.0f) * (float)Math.PI / 180.0f;
                    this.moveStrafing = -MathHelper.sin(f15) * this.moveForward * 1.0f;
                    this.moveForward = MathHelper.cos(f15) * this.moveForward * 1.0f;
                }
                if (d12 > 0.0) {
                    this.isJumping = true;
                }
            }
            if (this.playerToAttack != null) {
                this.faceEntity(this.playerToAttack, 30.0f, 30.0f);
            }
            if (this.isCollidedHorizontally) {
                this.isJumping = true;
            }
            if (this.rand.nextFloat() < 0.8f && (z23 || z24)) {
                this.isJumping = true;
            }
        } else {
            super.updatePlayerActionState();
            this.pathToEntity = null;
        }
    }

    protected void attackEntity(Entity entity1, float f2) {
    }

    protected float getBlockPathWeight(int i1, int i2, int i3) {
        return 0.0f;
    }

    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public boolean getCanSpawnHere() {
        int i1 = MathHelper.floor_double(this.posX);
        int i2 = MathHelper.floor_double(this.boundingBox.minY);
        int i3 = MathHelper.floor_double(this.posZ);
        return super.getCanSpawnHere() && this.getBlockPathWeight(i1, i2, i3) >= 0.0f;
    }

    public boolean hasPath() {
        return this.pathToEntity != null;
    }

    public void setPathToEntity(PathEntity pathEntity1) {
        this.pathToEntity = pathEntity1;
    }

    public Entity getTarget() {
        return this.playerToAttack;
    }

    public void setTarget(Entity entity1) {
        this.playerToAttack = entity1;
    }
}

