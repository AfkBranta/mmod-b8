/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public abstract class EntityAnimals
extends EntityCreature {
    public EntityAnimals(World world1) {
        super(world1);
    }

    @Override
    protected float getBlockPathWeight(int i1, int i2, int i3) {
        return this.worldObj.getBlockId(i1, i2 - 1, i3) == Block.grass.blockID ? 10.0f : this.worldObj.getLightBrightness(i1, i2, i3) - 0.5f;
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
    public boolean getCanSpawnHere() {
        int i3;
        int i2;
        int i1 = MathHelper.floor_double(this.posX);
        return this.worldObj.getBlockId(i1, (i2 = MathHelper.floor_double(this.boundingBox.minY)) - 1, i3 = MathHelper.floor_double(this.posZ)) == Block.grass.blockID && this.worldObj.getBlockLightValue(i1, i2, i3) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }
}

