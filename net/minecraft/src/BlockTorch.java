/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockTorch
extends Block {
    protected BlockTorch(int i1, int i2) {
        super(i1, i2, Material.circuits);
        this.setTickOnLoad(true);
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
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return 2;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2 - 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2 + 1, i3, i4) ? true : (world1.isBlockOpaqueCube(i2, i3, i4 - 1) ? true : (world1.isBlockOpaqueCube(i2, i3, i4 + 1) ? true : world1.isBlockOpaqueCube(i2, i3 - 1, i4))));
    }

    @Override
    public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if (i5 == 1 && world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
            i6 = 5;
        }
        if (i5 == 2 && world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
            i6 = 4;
        }
        if (i5 == 3 && world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
            i6 = 3;
        }
        if (i5 == 4 && world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
            i6 = 2;
        }
        if (i5 == 5 && world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
            i6 = 1;
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        super.updateTick(world1, i2, i3, i4, random5);
        if (world1.getBlockMetadata(i2, i3, i4) == 0) {
            this.onBlockAdded(world1, i2, i3, i4);
        }
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        if (world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 1);
        } else if (world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 2);
        } else if (world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 3);
        } else if (world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 4);
        } else if (world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 5);
        }
        this.dropTorchIfCantStay(world1, i2, i3, i4);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (this.dropTorchIfCantStay(world1, i2, i3, i4)) {
            int i6 = world1.getBlockMetadata(i2, i3, i4);
            boolean z7 = false;
            if (!world1.isBlockOpaqueCube(i2 - 1, i3, i4) && i6 == 1) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2 + 1, i3, i4) && i6 == 2) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3, i4 - 1) && i6 == 3) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3, i4 + 1) && i6 == 4) {
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4) && i6 == 5) {
                z7 = true;
            }
            if (z7) {
                this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        }
    }

    private boolean dropTorchIfCantStay(World world1, int i2, int i3, int i4) {
        if (!this.canPlaceBlockAt(world1, i2, i3, i4)) {
            this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
            world1.setBlockWithNotify(i2, i3, i4, 0);
            return false;
        }
        return true;
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
        int i7 = world1.getBlockMetadata(i2, i3, i4) & 7;
        float f8 = 0.15f;
        if (i7 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - f8, f8 * 2.0f, 0.8f, 0.5f + f8);
        } else if (i7 == 2) {
            this.setBlockBounds(1.0f - f8 * 2.0f, 0.2f, 0.5f - f8, 1.0f, 0.8f, 0.5f + f8);
        } else if (i7 == 3) {
            this.setBlockBounds(0.5f - f8, 0.2f, 0.0f, 0.5f + f8, 0.8f, f8 * 2.0f);
        } else if (i7 == 4) {
            this.setBlockBounds(0.5f - f8, 0.2f, 1.0f - f8 * 2.0f, 0.5f + f8, 0.8f, 1.0f);
        } else {
            f8 = 0.1f;
            this.setBlockBounds(0.5f - f8, 0.0f, 0.5f - f8, 0.5f + f8, 0.6f, 0.5f + f8);
        }
        return super.collisionRayTrace(world1, i2, i3, i4, vec3D5, vec3D6);
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        double d7 = (float)i2 + 0.5f;
        double d9 = (float)i3 + 0.7f;
        double d11 = (float)i4 + 0.5f;
        double d13 = 0.22f;
        double d15 = 0.27f;
        if (i6 == 1) {
            world1.spawnParticle("smoke", d7 - d15, d9 + d13, d11, 0.0, 0.0, 0.0);
            world1.spawnParticle("flame", d7 - d15, d9 + d13, d11, 0.0, 0.0, 0.0);
        } else if (i6 == 2) {
            world1.spawnParticle("smoke", d7 + d15, d9 + d13, d11, 0.0, 0.0, 0.0);
            world1.spawnParticle("flame", d7 + d15, d9 + d13, d11, 0.0, 0.0, 0.0);
        } else if (i6 == 3) {
            world1.spawnParticle("smoke", d7, d9 + d13, d11 - d15, 0.0, 0.0, 0.0);
            world1.spawnParticle("flame", d7, d9 + d13, d11 - d15, 0.0, 0.0, 0.0);
        } else if (i6 == 4) {
            world1.spawnParticle("smoke", d7, d9 + d13, d11 + d15, 0.0, 0.0, 0.0);
            world1.spawnParticle("flame", d7, d9 + d13, d11 + d15, 0.0, 0.0, 0.0);
        } else {
            world1.spawnParticle("smoke", d7, d9, d11, 0.0, 0.0, 0.0);
            world1.spawnParticle("flame", d7, d9, d11, 0.0, 0.0, 0.0);
        }
    }
}

