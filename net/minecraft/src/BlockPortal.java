/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBreakable;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockPortal
extends BlockBreakable {
    public BlockPortal(int i1, int i2) {
        super(i1, i2, Material.portal, false);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return null;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        if (iBlockAccess1.getBlockId(i2 - 1, i3, i4) != this.blockID && iBlockAccess1.getBlockId(i2 + 1, i3, i4) != this.blockID) {
            float f5 = 0.125f;
            float f6 = 0.5f;
            this.setBlockBounds(0.5f - f5, 0.0f, 0.5f - f6, 0.5f + f5, 1.0f, 0.5f + f6);
        } else {
            float f5 = 0.5f;
            float f6 = 0.125f;
            this.setBlockBounds(0.5f - f5, 0.0f, 0.5f - f6, 0.5f + f5, 1.0f, 0.5f + f6);
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean tryToCreatePortal(World world1, int i2, int i3, int i4) {
        int i8;
        int b5 = 0;
        int b6 = 0;
        if (world1.getBlockId(i2 - 1, i3, i4) == Block.obsidian.blockID || world1.getBlockId(i2 + 1, i3, i4) == Block.obsidian.blockID) {
            b5 = 1;
        }
        if (world1.getBlockId(i2, i3, i4 - 1) == Block.obsidian.blockID || world1.getBlockId(i2, i3, i4 + 1) == Block.obsidian.blockID) {
            b6 = 1;
        }
        System.out.println(String.valueOf(b5) + ", " + b6);
        if (b5 == b6) {
            return false;
        }
        if (world1.getBlockId(i2 - b5, i3, i4 - b6) == 0) {
            i2 -= b5;
            i4 -= b6;
        }
        int i7 = -1;
        while (i7 <= 2) {
            i8 = -1;
            while (i8 <= 3) {
                boolean z9;
                boolean bl = z9 = i7 == -1 || i7 == 2 || i8 == -1 || i8 == 3;
                if (i7 != -1 && i7 != 2 || i8 != -1 && i8 != 3) {
                    int i10 = world1.getBlockId(i2 + b5 * i7, i3 + i8, i4 + b6 * i7);
                    if (z9 ? i10 != Block.obsidian.blockID : i10 != 0 && i10 != Block.fire.blockID) {
                        return false;
                    }
                }
                ++i8;
            }
            ++i7;
        }
        world1.editingBlocks = true;
        i7 = 0;
        while (i7 < 2) {
            i8 = 0;
            while (i8 < 3) {
                world1.setBlockWithNotify(i2 + b5 * i7, i3 + i8, i4 + b6 * i7, Block.portal.blockID);
                ++i8;
            }
            ++i7;
        }
        world1.editingBlocks = false;
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        int b6 = 0;
        int b7 = 1;
        if (world1.getBlockId(i2 - 1, i3, i4) == this.blockID || world1.getBlockId(i2 + 1, i3, i4) == this.blockID) {
            b6 = 1;
            b7 = 0;
        }
        int i8 = i3;
        while (world1.getBlockId(i2, i8 - 1, i4) == this.blockID) {
            --i8;
        }
        if (world1.getBlockId(i2, i8 - 1, i4) != Block.obsidian.blockID) {
            world1.setBlockWithNotify(i2, i3, i4, 0);
        } else {
            int i9 = 1;
            while (i9 < 4 && world1.getBlockId(i2, i8 + i9, i4) == this.blockID) {
                ++i9;
            }
            if (i9 == 3 && world1.getBlockId(i2, i8 + i9, i4) == Block.obsidian.blockID) {
                boolean z11;
                boolean z10 = world1.getBlockId(i2 - 1, i3, i4) == this.blockID || world1.getBlockId(i2 + 1, i3, i4) == this.blockID;
                boolean bl = z11 = world1.getBlockId(i2, i3, i4 - 1) == this.blockID || world1.getBlockId(i2, i3, i4 + 1) == this.blockID;
                if (z10 && z11) {
                    world1.setBlockWithNotify(i2, i3, i4, 0);
                } else if (!(world1.getBlockId(i2 + b6, i3, i4 + b7) == Block.obsidian.blockID && world1.getBlockId(i2 - b6, i3, i4 - b7) == this.blockID || world1.getBlockId(i2 - b6, i3, i4 - b7) == Block.obsidian.blockID && world1.getBlockId(i2 + b6, i3, i4 + b7) == this.blockID)) {
                    world1.setBlockWithNotify(i2, i3, i4, 0);
                }
            } else {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
        if (!world1.multiplayerWorld) {
            entity5.setInPortal();
        }
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        if (random5.nextInt(100) == 0) {
            world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "portal.portal", 1.0f, random5.nextFloat() * 0.4f + 0.8f);
        }
        int i6 = 0;
        while (i6 < 4) {
            double d7 = (float)i2 + random5.nextFloat();
            double d9 = (float)i3 + random5.nextFloat();
            double d11 = (float)i4 + random5.nextFloat();
            double d13 = 0.0;
            double d15 = 0.0;
            double d17 = 0.0;
            int i19 = random5.nextInt(2) * 2 - 1;
            d13 = ((double)random5.nextFloat() - 0.5) * 0.5;
            d15 = ((double)random5.nextFloat() - 0.5) * 0.5;
            d17 = ((double)random5.nextFloat() - 0.5) * 0.5;
            if (world1.getBlockId(i2 - 1, i3, i4) != this.blockID && world1.getBlockId(i2 + 1, i3, i4) != this.blockID) {
                d7 = (double)i2 + 0.5 + 0.25 * (double)i19;
                d13 = random5.nextFloat() * 2.0f * (float)i19;
            } else {
                d11 = (double)i4 + 0.5 + 0.25 * (double)i19;
                d17 = random5.nextFloat() * 2.0f * (float)i19;
            }
            world1.spawnParticle("portal", d7, d9, d11, d13, d15, d17);
            ++i6;
        }
    }
}

