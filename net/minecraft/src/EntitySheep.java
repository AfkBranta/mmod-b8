/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimals;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntitySheep
extends EntityAnimals {
    public static final float[][] fleeceColorTable = new float[][]{{1.0f, 1.0f, 1.0f}, {0.95f, 0.7f, 0.2f}, {0.9f, 0.5f, 0.85f}, {0.6f, 0.7f, 0.95f}, {0.9f, 0.9f, 0.2f}, {0.5f, 0.8f, 0.1f}, {0.95f, 0.7f, 0.8f}, {0.3f, 0.3f, 0.3f}, {0.6f, 0.6f, 0.6f}, {0.3f, 0.6f, 0.7f}, {0.7f, 0.4f, 0.9f}, {0.2f, 0.4f, 0.8f}, {0.5f, 0.4f, 0.3f}, {0.4f, 0.5f, 0.2f}, {0.8f, 0.3f, 0.3f}, {0.1f, 0.1f, 0.1f}};

    public EntitySheep(World world1) {
        super(world1);
        this.texture = "/mob/sheep.png";
        this.setSize(0.9f, 1.3f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte(0));
    }

    @Override
    public boolean attackEntityFrom(Entity entity1, int i2) {
        if (!this.worldObj.multiplayerWorld && !this.getSheared() && entity1 instanceof EntityLiving) {
            this.setSheared(true);
            int i3 = 1 + this.rand.nextInt(3);
            int i4 = 0;
            while (i4 < i3) {
                EntityItem entityItem5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0f);
                entityItem5.motionY += (double)(this.rand.nextFloat() * 0.05f);
                entityItem5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                entityItem5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f);
                ++i4;
            }
        }
        return super.attackEntityFrom(entity1, i2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeEntityToNBT(nBTTagCompound1);
        nBTTagCompound1.setBoolean("Sheared", this.getSheared());
        nBTTagCompound1.setByte("Color", (byte)this.getFleeceColor());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readEntityFromNBT(nBTTagCompound1);
        this.setSheared(nBTTagCompound1.getBoolean("Sheared"));
        this.setFleeceColor(nBTTagCompound1.getByte("Color"));
    }

    @Override
    protected String getLivingSound() {
        return "mob.sheep";
    }

    @Override
    protected String getHurtSound() {
        return "mob.sheep";
    }

    @Override
    protected String getDeathSound() {
        return "mob.sheep";
    }

    public int getFleeceColor() {
        return this.dataWatcher.getWatchableObjectByte(16) & 0xF;
    }

    public void setFleeceColor(int i1) {
        byte b2 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte)(b2 & 0xF0 | i1 & 0xF));
    }

    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0;
    }

    public void setSheared(boolean z1) {
        byte b2 = this.dataWatcher.getWatchableObjectByte(16);
        if (z1) {
            this.dataWatcher.updateObject(16, (byte)(b2 | 0x10));
        } else {
            this.dataWatcher.updateObject(16, (byte)(b2 & 0xFFFFFFEF));
        }
    }

    public static int getRandomFleeceColor(Random random0) {
        int i1 = random0.nextInt(100);
        return i1 < 5 ? 15 : (i1 < 10 ? 7 : (i1 < 15 ? 8 : (i1 < 18 ? 12 : (random0.nextInt(500) == 0 ? 6 : 0))));
    }
}

