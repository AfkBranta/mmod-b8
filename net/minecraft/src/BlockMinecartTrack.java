/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MinecartTrackLogic;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockMinecartTrack
extends Block {
    protected BlockMinecartTrack(int i1, int i2) {
        super(i1, i2, Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        return super.collisionRayTrace(world1, i2, i3, i4, vec3D5, vec3D6);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        if (i5 >= 2 && i5 <= 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        } else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return i2 >= 6 ? this.blockIndexInTexture - 16 : this.blockIndexInTexture;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 9;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 1;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2, i3 - 1, i4);
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        if (!world1.multiplayerWorld) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 15);
            this.func_4031_h(world1, i2, i3, i4);
        }
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (!world1.multiplayerWorld) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            boolean z7 = false;
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
                z7 = true;
            }
            if (i6 == 2 && !world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
                z7 = true;
            }
            if (i6 == 3 && !world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
                z7 = true;
            }
            if (i6 == 4 && !world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
                z7 = true;
            }
            if (i6 == 5 && !world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
                z7 = true;
            }
            if (z7) {
                this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
                world1.setBlockWithNotify(i2, i3, i4, 0);
            } else if (i5 > 0 && Block.blocksList[i5].canProvidePower() && MinecartTrackLogic.getNAdjacentTracks(new MinecartTrackLogic(this, world1, i2, i3, i4)) == 3) {
                this.func_4031_h(world1, i2, i3, i4);
            }
        }
    }

    private void func_4031_h(World world1, int i2, int i3, int i4) {
        if (!world1.multiplayerWorld) {
            new MinecartTrackLogic(this, world1, i2, i3, i4).func_792_a(world1.isBlockIndirectlyGettingPowered(i2, i3, i4));
        }
    }
}

