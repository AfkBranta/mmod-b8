/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class Explosion {
    public boolean field_12257_a = false;
    private Random ExplosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public Set destroyedBlockPositions = new HashSet();

    public Explosion(World world1, Entity entity2, double d3, double d5, double d7, float f9) {
        this.worldObj = world1;
        this.exploder = entity2;
        this.explosionSize = f9;
        this.explosionX = d3;
        this.explosionY = d5;
        this.explosionZ = d7;
    }

    public void doExplosionA() {
        double d19;
        double d17;
        double d15;
        int i5;
        int i4;
        float f1 = this.explosionSize;
        int b2 = 16;
        int i3 = 0;
        while (i3 < b2) {
            i4 = 0;
            while (i4 < b2) {
                i5 = 0;
                while (i5 < b2) {
                    if (i3 == 0 || i3 == b2 - 1 || i4 == 0 || i4 == b2 - 1 || i5 == 0 || i5 == b2 - 1) {
                        double d6 = (float)i3 / ((float)b2 - 1.0f) * 2.0f - 1.0f;
                        double d8 = (float)i4 / ((float)b2 - 1.0f) * 2.0f - 1.0f;
                        double d10 = (float)i5 / ((float)b2 - 1.0f) * 2.0f - 1.0f;
                        double d12 = Math.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
                        d6 /= d12;
                        d8 /= d12;
                        d10 /= d12;
                        float f14 = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        d15 = this.explosionX;
                        d17 = this.explosionY;
                        d19 = this.explosionZ;
                        float f21 = 0.3f;
                        while (f14 > 0.0f) {
                            int i24;
                            int i23;
                            int i22 = MathHelper.floor_double(d15);
                            int i25 = this.worldObj.getBlockId(i22, i23 = MathHelper.floor_double(d17), i24 = MathHelper.floor_double(d19));
                            if (i25 > 0) {
                                f14 -= (Block.blocksList[i25].getExplosionResistance(this.exploder) + 0.3f) * f21;
                            }
                            if (f14 > 0.0f) {
                                this.destroyedBlockPositions.add(new ChunkPosition(i22, i23, i24));
                            }
                            d15 += d6 * (double)f21;
                            d17 += d8 * (double)f21;
                            d19 += d10 * (double)f21;
                            f14 -= f21 * 0.75f;
                        }
                    }
                    ++i5;
                }
                ++i4;
            }
            ++i3;
        }
        this.explosionSize *= 2.0f;
        i3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0);
        i4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0);
        i5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0);
        int i29 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0);
        int i7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0);
        int i30 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0);
        List list9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBoxFromPool(i3, i5, i7, i4, i29, i30));
        Vec3D vec3D31 = Vec3D.createVector(this.explosionX, this.explosionY, this.explosionZ);
        int i11 = 0;
        while (i11 < list9.size()) {
            Entity entity33 = (Entity)list9.get(i11);
            double d13 = entity33.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;
            if (d13 <= 1.0) {
                d15 = entity33.posX - this.explosionX;
                d17 = entity33.posY - this.explosionY;
                d19 = entity33.posZ - this.explosionZ;
                double d39 = MathHelper.sqrt_double(d15 * d15 + d17 * d17 + d19 * d19);
                d15 /= d39;
                d17 /= d39;
                d19 /= d39;
                double d40 = this.worldObj.func_675_a(vec3D31, entity33.boundingBox);
                double d41 = (1.0 - d13) * d40;
                entity33.attackEntityFrom(this.exploder, (int)((d41 * d41 + d41) / 2.0 * 8.0 * (double)this.explosionSize + 1.0));
                entity33.motionX += d15 * d41;
                entity33.motionY += d17 * d41;
                entity33.motionZ += d19 * d41;
            }
            ++i11;
        }
        this.explosionSize = f1;
        ArrayList arrayList32 = new ArrayList();
        arrayList32.addAll(this.destroyedBlockPositions);
        if (this.field_12257_a) {
            int i34 = arrayList32.size() - 1;
            while (i34 >= 0) {
                ChunkPosition chunkPosition35 = (ChunkPosition)arrayList32.get(i34);
                int i36 = chunkPosition35.x;
                int i37 = chunkPosition35.y;
                int i16 = chunkPosition35.z;
                int i38 = this.worldObj.getBlockId(i36, i37, i16);
                int i18 = this.worldObj.getBlockId(i36, i37 - 1, i16);
                if (i38 == 0 && Block.opaqueCubeLookup[i18] && this.ExplosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlockWithNotify(i36, i37, i16, Block.fire.blockID);
                }
                --i34;
            }
        }
    }

    public void doExplosionB() {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        ArrayList arrayList1 = new ArrayList();
        arrayList1.addAll(this.destroyedBlockPositions);
        int i2 = arrayList1.size() - 1;
        while (i2 >= 0) {
            ChunkPosition chunkPosition3 = (ChunkPosition)arrayList1.get(i2);
            int i4 = chunkPosition3.x;
            int i5 = chunkPosition3.y;
            int i6 = chunkPosition3.z;
            int i7 = this.worldObj.getBlockId(i4, i5, i6);
            int i8 = 0;
            while (i8 < 1) {
                double d9 = (float)i4 + this.worldObj.rand.nextFloat();
                double d11 = (float)i5 + this.worldObj.rand.nextFloat();
                double d13 = (float)i6 + this.worldObj.rand.nextFloat();
                double d15 = d9 - this.explosionX;
                double d17 = d11 - this.explosionY;
                double d19 = d13 - this.explosionZ;
                double d21 = MathHelper.sqrt_double(d15 * d15 + d17 * d17 + d19 * d19);
                d15 /= d21;
                d17 /= d21;
                d19 /= d21;
                double d23 = 0.5 / (d21 / (double)this.explosionSize + 0.1);
                this.worldObj.spawnParticle("explode", (d9 + this.explosionX * 1.0) / 2.0, (d11 + this.explosionY * 1.0) / 2.0, (d13 + this.explosionZ * 1.0) / 2.0, d15 *= (d23 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f)), d17 *= d23, d19 *= d23);
                this.worldObj.spawnParticle("smoke", d9, d11, d13, d15, d17, d19);
                ++i8;
            }
            if (i7 > 0) {
                Block.blocksList[i7].dropBlockAsItemWithChance(this.worldObj, i4, i5, i6, this.worldObj.getBlockMetadata(i4, i5, i6), 0.3f);
                this.worldObj.setBlockWithNotify(i4, i5, i6, 0);
                Block.blocksList[i7].onBlockDestroyedByExplosion(this.worldObj, i4, i5, i6);
            }
            --i2;
        }
    }
}

