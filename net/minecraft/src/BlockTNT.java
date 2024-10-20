/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockTNT
extends Block {
    public BlockTNT(int i1, int i2) {
        super(i1, i2, Material.tnt);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 0 ? this.blockIndexInTexture + 2 : (i1 == 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (i5 > 0 && Block.blocksList[i5].canProvidePower() && world1.isBlockIndirectlyGettingPowered(i2, i3, i4)) {
            this.onBlockDestroyedByPlayer(world1, i2, i3, i4, 0);
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
        EntityTNTPrimed entityTNTPrimed5 = new EntityTNTPrimed(world1, (float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f);
        entityTNTPrimed5.fuse = world1.rand.nextInt(entityTNTPrimed5.fuse / 4) + entityTNTPrimed5.fuse / 8;
        world1.entityJoinedWorld(entityTNTPrimed5);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
        if (!world1.multiplayerWorld) {
            EntityTNTPrimed entityTNTPrimed6 = new EntityTNTPrimed(world1, (float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f);
            world1.entityJoinedWorld(entityTNTPrimed6);
            world1.playSoundAtEntity(entityTNTPrimed6, "random.fuse", 1.0f, 1.0f);
        }
    }
}

