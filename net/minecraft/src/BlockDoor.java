/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class BlockDoor
extends Block {
    protected BlockDoor(int i1, Material material2) {
        super(i1, material2);
        this.blockIndexInTexture = 97;
        if (material2 == Material.iron) {
            ++this.blockIndexInTexture;
        }
        float f3 = 0.5f;
        float f4 = 1.0f;
        this.setBlockBounds(0.5f - f3, 0.0f, 0.5f - f3, 0.5f + f3, f4, 0.5f + f3);
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        if (i1 != 0 && i1 != 1) {
            int i3 = this.getState(i2);
            if ((i3 == 0 || i3 == 2) ^ i1 <= 3) {
                return this.blockIndexInTexture;
            }
            int i4 = i3 / 2 + (i1 & 1 ^ i3);
            int i5 = this.blockIndexInTexture - (i2 & 8) * 2;
            if (((i4 += (i2 & 4) / 4) & 1) != 0) {
                i5 = -i5;
            }
            return i5;
        }
        return this.blockIndexInTexture;
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
        return 7;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        return super.getSelectedBoundingBoxFromPool(world1, i2, i3, i4);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        return super.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        this.func_313_b(this.getState(iBlockAccess1.getBlockMetadata(i2, i3, i4)));
    }

    public void func_313_b(int i1) {
        float f2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        if (i1 == 0) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, f2);
        }
        if (i1 == 1) {
            this.setBlockBounds(1.0f - f2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (i1 == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - f2, 1.0f, 1.0f, 1.0f);
        }
        if (i1 == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, f2, 1.0f, 1.0f);
        }
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        this.blockActivated(world1, i2, i3, i4, entityPlayer5);
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (this.blockMaterial == Material.iron) {
            return true;
        }
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if ((i6 & 8) != 0) {
            if (world1.getBlockId(i2, i3 - 1, i4) == this.blockID) {
                this.blockActivated(world1, i2, i3 - 1, i4, entityPlayer5);
            }
            return true;
        }
        if (world1.getBlockId(i2, i3 + 1, i4) == this.blockID) {
            world1.setBlockMetadataWithNotify(i2, i3 + 1, i4, (i6 ^ 4) + 8);
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, i6 ^ 4);
        world1.markBlocksDirty(i2, i3 - 1, i4, i2, i3, i4);
        if (Math.random() < 0.5) {
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.door_open", 1.0f, world1.rand.nextFloat() * 0.1f + 0.9f);
        } else {
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.door_close", 1.0f, world1.rand.nextFloat() * 0.1f + 0.9f);
        }
        return true;
    }

    public void func_311_a(World world1, int i2, int i3, int i4, boolean z5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if ((i6 & 8) != 0) {
            if (world1.getBlockId(i2, i3 - 1, i4) == this.blockID) {
                this.func_311_a(world1, i2, i3 - 1, i4, z5);
            }
        } else {
            boolean z7;
            boolean bl = z7 = (world1.getBlockMetadata(i2, i3, i4) & 4) > 0;
            if (z7 != z5) {
                if (world1.getBlockId(i2, i3 + 1, i4) == this.blockID) {
                    world1.setBlockMetadataWithNotify(i2, i3 + 1, i4, (i6 ^ 4) + 8);
                }
                world1.setBlockMetadataWithNotify(i2, i3, i4, i6 ^ 4);
                world1.markBlocksDirty(i2, i3 - 1, i4, i2, i3, i4);
                if (Math.random() < 0.5) {
                    world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.door_open", 1.0f, world1.rand.nextFloat() * 0.1f + 0.9f);
                } else {
                    world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "random.door_close", 1.0f, world1.rand.nextFloat() * 0.1f + 0.9f);
                }
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if ((i6 & 8) != 0) {
            if (world1.getBlockId(i2, i3 - 1, i4) != this.blockID) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
            if (i5 > 0 && Block.blocksList[i5].canProvidePower()) {
                this.onNeighborBlockChange(world1, i2, i3 - 1, i4, i5);
            }
        } else {
            boolean z7 = false;
            if (world1.getBlockId(i2, i3 + 1, i4) != this.blockID) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
                z7 = true;
            }
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
                z7 = true;
                if (world1.getBlockId(i2, i3 + 1, i4) == this.blockID) {
                    world1.setBlockWithNotify(i2, i3 + 1, i4, 0);
                }
            }
            if (z7) {
                if (!world1.multiplayerWorld) {
                    this.dropBlockAsItem(world1, i2, i3, i4, i6);
                }
            } else if (i5 > 0 && Block.blocksList[i5].canProvidePower()) {
                boolean z8 = world1.isBlockIndirectlyGettingPowered(i2, i3, i4) || world1.isBlockIndirectlyGettingPowered(i2, i3 + 1, i4);
                this.func_311_a(world1, i2, i3, i4, z8);
            }
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return (i1 & 8) != 0 ? 0 : (this.blockMaterial == Material.iron ? Item.doorSteel.shiftedIndex : Item.doorWood.shiftedIndex);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        return super.collisionRayTrace(world1, i2, i3, i4, vec3D5, vec3D6);
    }

    public int getState(int i1) {
        return (i1 & 4) == 0 ? i1 - 1 & 3 : i1 & 3;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return i3 >= 127 ? false : world1.isBlockOpaqueCube(i2, i3 - 1, i4) && super.canPlaceBlockAt(world1, i2, i3, i4) && super.canPlaceBlockAt(world1, i2, i3 + 1, i4);
    }
}

