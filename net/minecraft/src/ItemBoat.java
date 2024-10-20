/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class ItemBoat
extends Item {
    public ItemBoat(int i1) {
        super(i1);
        this.maxStackSize = 1;
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
        MovingObjectPosition movingObjectPosition24 = world2.rayTraceBlocks_do(vec3D13, vec3D23, true);
        if (movingObjectPosition24 == null) {
            return itemStack1;
        }
        if (movingObjectPosition24.typeOfHit == EnumMovingObjectType.TILE) {
            int i25 = movingObjectPosition24.blockX;
            int i26 = movingObjectPosition24.blockY;
            int i27 = movingObjectPosition24.blockZ;
            if (!world2.multiplayerWorld) {
                world2.entityJoinedWorld(new EntityBoat(world2, (float)i25 + 0.5f, (float)i26 + 1.5f, (float)i27 + 0.5f));
            }
            --itemStack1.stackSize;
        }
        return itemStack1;
    }
}

