/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityMobs;
import net.minecraft.src.EntitySkeleton;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityCreeper
extends EntityMobs {
    int timeSinceIgnited;
    int lastActiveTime;

    public EntityCreeper(World world1) {
        super(world1);
        this.texture = "/mob/creeper.png";
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)-1);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
    }

    @Override
    public void onUpdate() {
        this.lastActiveTime = this.timeSinceIgnited;
        if (this.worldObj.multiplayerWorld) {
            int i1 = this.func_21091_q();
            if (i1 > 0 && this.timeSinceIgnited == 0) {
                this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 0.5f);
            }
            this.timeSinceIgnited += i1;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
            if (this.timeSinceIgnited >= 30) {
                this.timeSinceIgnited = 30;
            }
        }
        super.onUpdate();
    }

    @Override
    protected String getHurtSound() {
        return "mob.creeper";
    }

    @Override
    protected String getDeathSound() {
        return "mob.creeperdeath";
    }

    @Override
    public void onDeath(Entity entity1) {
        super.onDeath(entity1);
        if (entity1 instanceof EntitySkeleton) {
            this.dropItem(Item.record13.shiftedIndex + this.rand.nextInt(2), 1);
        }
    }

    @Override
    protected void attackEntity(Entity entity1, float f2) {
        int i3 = this.func_21091_q();
        if (i3 <= 0 && f2 < 3.0f || i3 > 0 && f2 < 7.0f) {
            if (this.timeSinceIgnited == 0) {
                this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0f, 0.5f);
            }
            this.func_21090_e(1);
            ++this.timeSinceIgnited;
            if (this.timeSinceIgnited >= 30) {
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0f);
                this.setEntityDead();
            }
            this.hasAttacked = true;
        } else {
            this.func_21090_e(-1);
            --this.timeSinceIgnited;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }
        }
    }

    public float setCreeperFlashTime(float f1) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * f1) / 28.0f;
    }

    @Override
    protected int getDropItemId() {
        return Item.gunpowder.shiftedIndex;
    }

    private int func_21091_q() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    private void func_21090_e(int i1) {
        this.dataWatcher.updateObject(16, (byte)i1);
    }
}

