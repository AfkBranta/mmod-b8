/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Chunk;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IMobs;
import net.minecraft.src.Item;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySlime
extends EntityLiving
implements IMobs {
    public float field_768_a;
    public float field_767_b;
    private int slimeJumpDelay = 0;

    public EntitySlime(World world1) {
        super(world1);
        this.texture = "/mob/slime.png";
        int i2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0f;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(i2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(1));
    }

    public void setSlimeSize(int i1) {
        this.dataWatcher.updateObject(16, new Byte((byte)i1));
        this.setSize(0.6f * (float)i1, 0.6f * (float)i1);
        this.health = i1 * i1;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setInteger("Size", this.getSlimeSize() - 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        this.setSlimeSize(nBTTagCompound1.getInteger("Size") + 1);
    }

    @Override
    public void onUpdate() {
        this.field_767_b = this.field_768_a;
        boolean z1 = this.onGround;
        super.onUpdate();
        if (this.onGround && !z1) {
            int i2 = this.getSlimeSize();
            int i3 = 0;
            while (i3 < i2 * 8) {
                float f4 = this.rand.nextFloat() * (float)Math.PI * 2.0f;
                float f5 = this.rand.nextFloat() * 0.5f + 0.5f;
                float f6 = MathHelper.sin(f4) * (float)i2 * 0.5f * f5;
                float f7 = MathHelper.cos(f4) * (float)i2 * 0.5f * f5;
                this.worldObj.spawnParticle("slime", this.posX + (double)f6, this.boundingBox.minY, this.posZ + (double)f7, 0.0, 0.0, 0.0);
                ++i3;
            }
            if (i2 > 2) {
                this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.field_768_a = -0.5f;
        }
        this.field_768_a *= 0.6f;
    }

    @Override
    protected void updatePlayerActionState() {
        EntityPlayer entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
        if (entityPlayer1 != null) {
            this.faceEntity(entityPlayer1, 10.0f, 20.0f);
        }
        if (this.onGround && this.slimeJumpDelay-- <= 0) {
            this.slimeJumpDelay = this.rand.nextInt(20) + 10;
            if (entityPlayer1 != null) {
                this.slimeJumpDelay /= 3;
            }
            this.isJumping = true;
            if (this.getSlimeSize() > 1) {
                this.worldObj.playSoundAtEntity(this, "mob.slime", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.field_768_a = 1.0f;
            this.moveStrafing = 1.0f - this.rand.nextFloat() * 2.0f;
            this.moveForward = 1 * this.getSlimeSize();
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveForward = 0.0f;
                this.moveStrafing = 0.0f;
            }
        }
    }

    @Override
    public void setEntityDead() {
        int i1 = this.getSlimeSize();
        if (!this.worldObj.multiplayerWorld && i1 > 1 && this.health == 0) {
            int i2 = 0;
            while (i2 < 4) {
                float f3 = ((float)(i2 % 2) - 0.5f) * (float)i1 / 4.0f;
                float f4 = ((float)(i2 / 2) - 0.5f) * (float)i1 / 4.0f;
                EntitySlime entitySlime5 = new EntitySlime(this.worldObj);
                entitySlime5.setSlimeSize(i1 / 2);
                entitySlime5.setLocationAndAngles(this.posX + (double)f3, this.posY + 0.5, this.posZ + (double)f4, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.entityJoinedWorld(entitySlime5);
                ++i2;
            }
        }
        super.setEntityDead();
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
        int i2 = this.getSlimeSize();
        if (i2 > 1 && this.canEntityBeSeen(entityPlayer1) && (double)this.getDistanceToEntity(entityPlayer1) < 0.6 * (double)i2 && entityPlayer1.attackEntityFrom(this, i2)) {
            this.worldObj.playSoundAtEntity(this, "mob.slimeattack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }

    @Override
    protected String getHurtSound() {
        return "mob.slime";
    }

    @Override
    protected String getDeathSound() {
        return "mob.slime";
    }

    @Override
    protected int getDropItemId() {
        return this.getSlimeSize() == 1 ? Item.slimeBall.shiftedIndex : 0;
    }

    @Override
    public boolean getCanSpawnHere() {
        Chunk chunk1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        return (this.getSlimeSize() == 1 || this.worldObj.difficultySetting > 0) && this.rand.nextInt(10) == 0 && chunk1.func_997_a(987234911L).nextInt(10) == 0 && this.posY < 16.0;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6f;
    }
}

