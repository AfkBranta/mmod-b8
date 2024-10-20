/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class ItemBucket
extends Item {
    private int isFull;

    public ItemBucket(int i1, int i2) {
        super(i1);
        this.maxStackSize = 1;
        this.isFull = i2;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
        float f20;
        float f17;
        double d21;
        float f16;
        float f4 = 1.0f;
        float f5 = entityPlayer3.prevRotationPitch + (entityPlayer3.rotationPitch - entityPlayer3.prevRotationPitch) * f4;
        float f6 = entityPlayer3.prevRotationYaw + (entityPlayer3.rotationYaw - entityPlayer3.prevRotationYaw) * f4;
        double d7 = entityPlayer3.prevPosX + (entityPlayer3.posX - entityPlayer3.prevPosX) * (double)f4;
        double d9 = entityPlayer3.prevPosY + (entityPlayer3.posY - entityPlayer3.prevPosY) * (double)f4 + 1.62 - (double)entityPlayer3.yOffset;
        double d11 = entityPlayer3.prevPosZ + (entityPlayer3.posZ - entityPlayer3.prevPosZ) * (double)f4;
        Vec3D vec3D13 = Vec3D.createVector(d7, d9, d11);
        float f14 = MathHelper.cos(-f6 * ((float)Math.PI / 180) - (float)Math.PI);
        float f15 = MathHelper.sin(-f6 * ((float)Math.PI / 180) - (float)Math.PI);
        float f18 = f15 * (f16 = -MathHelper.cos(-f5 * ((float)Math.PI / 180)));
        Vec3D vec3D23 = vec3D13.addVector((double)f18 * (d21 = 5.0), (double)(f17 = MathHelper.sin(-f5 * ((float)Math.PI / 180))) * d21, (double)(f20 = f14 * f16) * d21);
        MovingObjectPosition movingObjectPosition24 = world2.rayTraceBlocks_do(vec3D13, vec3D23, this.isFull == 0);
        if (movingObjectPosition24 == null) {
            return itemStack1;
        }
        if (movingObjectPosition24.typeOfHit == EnumMovingObjectType.TILE) {
            int i25 = movingObjectPosition24.blockX;
            int i26 = movingObjectPosition24.blockY;
            int i27 = movingObjectPosition24.blockZ;
            if (!world2.func_6466_a(entityPlayer3, i25, i26, i27)) {
                return itemStack1;
            }
            if (this.isFull == 0) {
                if (world2.getBlockMaterial(i25, i26, i27) == Material.water && world2.getBlockMetadata(i25, i26, i27) == 0) {
                    world2.setBlockWithNotify(i25, i26, i27, 0);
                    return new ItemStack(Item.bucketWater);
                }
                if (world2.getBlockMaterial(i25, i26, i27) == Material.lava && world2.getBlockMetadata(i25, i26, i27) == 0) {
                    world2.setBlockWithNotify(i25, i26, i27, 0);
                    return new ItemStack(Item.bucketLava);
                }
            } else {
                if (this.isFull < 0) {
                    return new ItemStack(Item.bucketEmpty);
                }
                if (movingObjectPosition24.sideHit == 0) {
                    --i26;
                }
                if (movingObjectPosition24.sideHit == 1) {
                    ++i26;
                }
                if (movingObjectPosition24.sideHit == 2) {
                    --i27;
                }
                if (movingObjectPosition24.sideHit == 3) {
                    ++i27;
                }
                if (movingObjectPosition24.sideHit == 4) {
                    --i25;
                }
                if (movingObjectPosition24.sideHit == 5) {
                    ++i25;
                }
                if (world2.isAirBlock(i25, i26, i27) || !world2.getBlockMaterial(i25, i26, i27).isSolid()) {
                    if (world2.worldProvider.isHellWorld && this.isFull == Block.waterMoving.blockID) {
                        world2.playSoundEffect(d7 + 0.5, d9 + 0.5, d11 + 0.5, "random.fizz", 0.5f, 2.6f + (world2.rand.nextFloat() - world2.rand.nextFloat()) * 0.8f);
                        int i28 = 0;
                        while (i28 < 8) {
                            world2.spawnParticle("largesmoke", (double)i25 + Math.random(), (double)i26 + Math.random(), (double)i27 + Math.random(), 0.0, 0.0, 0.0);
                            ++i28;
                        }
                    } else {
                        world2.setBlockAndMetadataWithNotify(i25, i26, i27, this.isFull, 0);
                    }
                    return new ItemStack(Item.bucketEmpty);
                }
            }
        } else if (this.isFull == 0 && movingObjectPosition24.entityHit instanceof EntityCow) {
            return new ItemStack(Item.bucketMilk);
        }
        return itemStack1;
    }
}

