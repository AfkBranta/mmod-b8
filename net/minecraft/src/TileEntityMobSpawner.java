/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntityMobSpawner
extends TileEntity {
    public int delay = 20;
    private String mobID = "Pig";
    public double yaw;
    public double yaw2 = 0.0;

    public String getMobID() {
        return this.mobID;
    }

    public void setMobID(String string1) {
        this.mobID = string1;
    }

    public boolean anyPlayerInRange() {
        return this.worldObj.getClosestPlayer((double)this.xCoord + 0.5, (double)this.yCoord + 0.5, (double)this.zCoord + 0.5, 16.0) != null;
    }

    @Override
    public void updateEntity() {
        this.yaw2 = this.yaw;
        if (this.anyPlayerInRange()) {
            double d1 = (float)this.xCoord + this.worldObj.rand.nextFloat();
            double d3 = (float)this.yCoord + this.worldObj.rand.nextFloat();
            double d5 = (float)this.zCoord + this.worldObj.rand.nextFloat();
            this.worldObj.spawnParticle("smoke", d1, d3, d5, 0.0, 0.0, 0.0);
            this.worldObj.spawnParticle("flame", d1, d3, d5, 0.0, 0.0, 0.0);
            this.yaw += (double)(1000.0f / ((float)this.delay + 200.0f));
            while (this.yaw > 360.0) {
                this.yaw -= 360.0;
                this.yaw2 -= 360.0;
            }
            if (this.delay == -1) {
                this.updateDelay();
            }
            if (this.delay > 0) {
                --this.delay;
            } else {
                int b7 = 4;
                int i8 = 0;
                while (i8 < b7) {
                    EntityLiving entityLiving9 = (EntityLiving)EntityList.createEntityInWorld(this.mobID, this.worldObj);
                    if (entityLiving9 == null) {
                        return;
                    }
                    int i10 = this.worldObj.getEntitiesWithinAABB(entityLiving9.getClass(), AxisAlignedBB.getBoundingBoxFromPool(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(8.0, 4.0, 8.0)).size();
                    if (i10 >= 6) {
                        this.updateDelay();
                        return;
                    }
                    if (entityLiving9 != null) {
                        double d11 = (double)this.xCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                        double d13 = this.yCoord + this.worldObj.rand.nextInt(3) - 1;
                        double d15 = (double)this.zCoord + (this.worldObj.rand.nextDouble() - this.worldObj.rand.nextDouble()) * 4.0;
                        entityLiving9.setLocationAndAngles(d11, d13, d15, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
                        if (entityLiving9.getCanSpawnHere()) {
                            this.worldObj.entityJoinedWorld(entityLiving9);
                            int i17 = 0;
                            while (i17 < 20) {
                                d1 = (double)this.xCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                d3 = (double)this.yCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                d5 = (double)this.zCoord + 0.5 + ((double)this.worldObj.rand.nextFloat() - 0.5) * 2.0;
                                this.worldObj.spawnParticle("smoke", d1, d3, d5, 0.0, 0.0, 0.0);
                                this.worldObj.spawnParticle("flame", d1, d3, d5, 0.0, 0.0, 0.0);
                                ++i17;
                            }
                            entityLiving9.spawnExplosionParticle();
                            this.updateDelay();
                        }
                    }
                    ++i8;
                }
                super.updateEntity();
            }
        }
    }

    private void updateDelay() {
        this.delay = 200 + this.worldObj.rand.nextInt(600);
    }

    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        this.mobID = nBTTagCompound1.getString("EntityId");
        this.delay = nBTTagCompound1.getShort("Delay");
    }

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        nBTTagCompound1.setString("EntityId", this.mobID);
        nBTTagCompound1.setShort("Delay", (short)this.delay);
    }
}

