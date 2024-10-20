/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockFire
extends Block {
    private int[] chanceToEncourageFire = new int[256];
    private int[] abilityToCatchFire = new int[256];

    protected BlockFire(int i1, int i2) {
        super(i1, i2, Material.fire);
        this.setBurnRate(Block.planks.blockID, 5, 20);
        this.setBurnRate(Block.wood.blockID, 5, 5);
        this.setBurnRate(Block.leaves.blockID, 30, 60);
        this.setBurnRate(Block.bookShelf.blockID, 30, 20);
        this.setBurnRate(Block.tnt.blockID, 15, 100);
        this.setBurnRate(Block.cloth.blockID, 30, 60);
        this.setTickOnLoad(true);
    }

    private void setBurnRate(int i1, int i2, int i3) {
        this.chanceToEncourageFire[i1] = i2;
        this.abilityToCatchFire[i1] = i3;
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
        return 3;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    @Override
    public int tickRate() {
        return 10;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        boolean z6 = world1.getBlockId(i2, i3 - 1, i4) == Block.bloodStone.blockID;
        int i7 = world1.getBlockMetadata(i2, i3, i4);
        if (i7 < 15) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, i7 + 1);
            world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
        }
        if (!z6 && !this.func_263_h(world1, i2, i3, i4)) {
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4) || i7 > 3) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        } else if (!z6 && !this.canBlockCatchFire(world1, i2, i3 - 1, i4) && i7 == 15 && random5.nextInt(4) == 0) {
            world1.setBlockWithNotify(i2, i3, i4, 0);
        } else {
            if (i7 % 2 == 0 && i7 > 2) {
                this.tryToCatchBlockOnFire(world1, i2 + 1, i3, i4, 300, random5);
                this.tryToCatchBlockOnFire(world1, i2 - 1, i3, i4, 300, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3 - 1, i4, 250, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3 + 1, i4, 250, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3, i4 - 1, 300, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3, i4 + 1, 300, random5);
                int i8 = i2 - 1;
                while (i8 <= i2 + 1) {
                    int i9 = i4 - 1;
                    while (i9 <= i4 + 1) {
                        int i10 = i3 - 1;
                        while (i10 <= i3 + 4) {
                            if (i8 != i2 || i10 != i3 || i9 != i4) {
                                int i12;
                                int i11 = 100;
                                if (i10 > i3 + 1) {
                                    i11 += (i10 - (i3 + 1)) * 100;
                                }
                                if ((i12 = this.getChanceOfNeighborsEncouragingFire(world1, i8, i10, i9)) > 0 && random5.nextInt(i11) <= i12) {
                                    world1.setBlockWithNotify(i8, i10, i9, this.blockID);
                                }
                            }
                            ++i10;
                        }
                        ++i9;
                    }
                    ++i8;
                }
            }
            if (i7 == 15) {
                this.tryToCatchBlockOnFire(world1, i2 + 1, i3, i4, 1, random5);
                this.tryToCatchBlockOnFire(world1, i2 - 1, i3, i4, 1, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3 - 1, i4, 1, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3 + 1, i4, 1, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3, i4 - 1, 1, random5);
                this.tryToCatchBlockOnFire(world1, i2, i3, i4 + 1, 1, random5);
            }
        }
    }

    private void tryToCatchBlockOnFire(World world1, int i2, int i3, int i4, int i5, Random random6) {
        int i7 = this.abilityToCatchFire[world1.getBlockId(i2, i3, i4)];
        if (random6.nextInt(i5) < i7) {
            boolean z8;
            boolean bl = z8 = world1.getBlockId(i2, i3, i4) == Block.tnt.blockID;
            if (random6.nextInt(2) == 0) {
                world1.setBlockWithNotify(i2, i3, i4, this.blockID);
            } else {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
            if (z8) {
                Block.tnt.onBlockDestroyedByPlayer(world1, i2, i3, i4, 0);
            }
        }
    }

    private boolean func_263_h(World world1, int i2, int i3, int i4) {
        return this.canBlockCatchFire(world1, i2 + 1, i3, i4) ? true : (this.canBlockCatchFire(world1, i2 - 1, i3, i4) ? true : (this.canBlockCatchFire(world1, i2, i3 - 1, i4) ? true : (this.canBlockCatchFire(world1, i2, i3 + 1, i4) ? true : (this.canBlockCatchFire(world1, i2, i3, i4 - 1) ? true : this.canBlockCatchFire(world1, i2, i3, i4 + 1)))));
    }

    private int getChanceOfNeighborsEncouragingFire(World world1, int i2, int i3, int i4) {
        int b5 = 0;
        if (!world1.isAirBlock(i2, i3, i4)) {
            return 0;
        }
        int i6 = this.getChanceToEncourageFire(world1, i2 + 1, i3, i4, b5);
        i6 = this.getChanceToEncourageFire(world1, i2 - 1, i3, i4, i6);
        i6 = this.getChanceToEncourageFire(world1, i2, i3 - 1, i4, i6);
        i6 = this.getChanceToEncourageFire(world1, i2, i3 + 1, i4, i6);
        i6 = this.getChanceToEncourageFire(world1, i2, i3, i4 - 1, i6);
        i6 = this.getChanceToEncourageFire(world1, i2, i3, i4 + 1, i6);
        return i6;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    public boolean canBlockCatchFire(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        return this.chanceToEncourageFire[iBlockAccess1.getBlockId(i2, i3, i4)] > 0;
    }

    public int getChanceToEncourageFire(World world1, int i2, int i3, int i4, int i5) {
        int i6 = this.chanceToEncourageFire[world1.getBlockId(i2, i3, i4)];
        return i6 > i5 ? i6 : i5;
    }

    @Override
    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        return world1.isBlockOpaqueCube(i2, i3 - 1, i4) || this.func_263_h(world1, i2, i3, i4);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4) && !this.func_263_h(world1, i2, i3, i4)) {
            world1.setBlockWithNotify(i2, i3, i4, 0);
        }
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        if (world1.getBlockId(i2, i3 - 1, i4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(world1, i2, i3, i4)) {
            if (!world1.isBlockOpaqueCube(i2, i3 - 1, i4) && !this.func_263_h(world1, i2, i3, i4)) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            } else {
                world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
            }
        }
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        block12: {
            block11: {
                float f9;
                float f8;
                float f7;
                int i6;
                if (random5.nextInt(24) == 0) {
                    world1.playSoundEffect((float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f, "fire.fire", 1.0f + random5.nextFloat(), random5.nextFloat() * 0.7f + 0.3f);
                }
                if (world1.isBlockOpaqueCube(i2, i3 - 1, i4) || Block.fire.canBlockCatchFire(world1, i2, i3 - 1, i4)) break block11;
                if (Block.fire.canBlockCatchFire(world1, i2 - 1, i3, i4)) {
                    i6 = 0;
                    while (i6 < 2) {
                        f7 = (float)i2 + random5.nextFloat() * 0.1f;
                        f8 = (float)i3 + random5.nextFloat();
                        f9 = (float)i4 + random5.nextFloat();
                        world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                        ++i6;
                    }
                }
                if (Block.fire.canBlockCatchFire(world1, i2 + 1, i3, i4)) {
                    i6 = 0;
                    while (i6 < 2) {
                        f7 = (float)(i2 + 1) - random5.nextFloat() * 0.1f;
                        f8 = (float)i3 + random5.nextFloat();
                        f9 = (float)i4 + random5.nextFloat();
                        world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                        ++i6;
                    }
                }
                if (Block.fire.canBlockCatchFire(world1, i2, i3, i4 - 1)) {
                    i6 = 0;
                    while (i6 < 2) {
                        f7 = (float)i2 + random5.nextFloat();
                        f8 = (float)i3 + random5.nextFloat();
                        f9 = (float)i4 + random5.nextFloat() * 0.1f;
                        world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                        ++i6;
                    }
                }
                if (Block.fire.canBlockCatchFire(world1, i2, i3, i4 + 1)) {
                    i6 = 0;
                    while (i6 < 2) {
                        f7 = (float)i2 + random5.nextFloat();
                        f8 = (float)i3 + random5.nextFloat();
                        f9 = (float)(i4 + 1) - random5.nextFloat() * 0.1f;
                        world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                        ++i6;
                    }
                }
                if (!Block.fire.canBlockCatchFire(world1, i2, i3 + 1, i4)) break block12;
                i6 = 0;
                while (i6 < 2) {
                    f7 = (float)i2 + random5.nextFloat();
                    f8 = (float)(i3 + 1) - random5.nextFloat() * 0.1f;
                    f9 = (float)i4 + random5.nextFloat();
                    world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                    ++i6;
                }
                break block12;
            }
            int i6 = 0;
            while (i6 < 3) {
                float f7 = (float)i2 + random5.nextFloat();
                float f8 = (float)i3 + random5.nextFloat() * 0.5f + 0.5f;
                float f9 = (float)i4 + random5.nextFloat();
                world1.spawnParticle("largesmoke", f7, f8, f9, 0.0, 0.0, 0.0);
                ++i6;
            }
        }
    }
}

