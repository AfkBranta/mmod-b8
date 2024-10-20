/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Modules.Movement.NoSlow;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockSlowSand
extends Block {
    public BlockSlowSand(int i1, int i2) {
        super(i1, i2, Material.sand);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        float f5 = 0.125f;
        return AxisAlignedBB.getBoundingBoxFromPool(i2, i3, i4, i2 + 1, (float)(i3 + 1) - f5, i4 + 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
        if (NoSlow.instance.isEnabled) {
            return;
        }
        entity5.motionX *= 0.4;
        entity5.motionZ *= 0.4;
    }
}

