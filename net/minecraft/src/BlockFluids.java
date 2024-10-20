/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Modules.Movement.Jesus;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public abstract class BlockFluids
extends Block {
    protected BlockFluids(int i1, Material material2) {
        super(i1, (material2 == Material.lava ? 14 : 12) * 16 + 13, material2);
        float f3 = 0.0f;
        float f4 = 0.0f;
        this.setBlockBounds(0.0f + f4, 0.0f + f3, 0.0f + f4, 1.0f + f4, 1.0f + f3, 1.0f + f4);
        this.setTickOnLoad(true);
    }

    public static float getPercentAir(int i0) {
        if (i0 >= 8) {
            i0 = 0;
        }
        float f1 = (float)(i0 + 1) / 9.0f;
        return f1;
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 != 0 && i1 != 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
    }

    protected int getFlowDecay(World world1, int i2, int i3, int i4) {
        return world1.getBlockMaterial(i2, i3, i4) != this.blockMaterial ? -1 : world1.getBlockMetadata(i2, i3, i4);
    }

    protected int getEffectiveFlowDecay(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        if (iBlockAccess1.getBlockMaterial(i2, i3, i4) != this.blockMaterial) {
            return -1;
        }
        int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        if (i5 >= 8) {
            i5 = 0;
        }
        return i5;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean canCollideCheck(int i1, boolean z2) {
        return z2 && i1 == 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        Material material6 = iBlockAccess1.getBlockMaterial(i2, i3, i4);
        return material6 == this.blockMaterial ? false : (material6 == Material.ice ? false : (i5 == 1 ? true : super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5)));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        if (Jesus.instance.isEnabled && Minecraft.theMinecraft.thePlayer != null && Minecraft.theMinecraft.thePlayer.fallDistance < 2.0f) {
            return new AxisAlignedBB(i2, i3, i4, i2 + 1, i3 + 1, i4 + 1);
        }
        return null;
    }

    @Override
    public int getRenderType() {
        return 4;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return 0;
    }

    @Override
    public int quantityDropped(Random random1) {
        return 0;
    }

    private Vec3D getFlowVector(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        Vec3D vec3D5 = Vec3D.createVector(0.0, 0.0, 0.0);
        int i6 = this.getEffectiveFlowDecay(iBlockAccess1, i2, i3, i4);
        int i7 = 0;
        while (i7 < 4) {
            int i12;
            int i11;
            int i8 = i2;
            int i10 = i4;
            if (i7 == 0) {
                i8 = i2 - 1;
            }
            if (i7 == 1) {
                i10 = i4 - 1;
            }
            if (i7 == 2) {
                ++i8;
            }
            if (i7 == 3) {
                ++i10;
            }
            if ((i11 = this.getEffectiveFlowDecay(iBlockAccess1, i8, i3, i10)) < 0) {
                if (!iBlockAccess1.getBlockMaterial(i8, i3, i10).getIsSolid() && (i11 = this.getEffectiveFlowDecay(iBlockAccess1, i8, i3 - 1, i10)) >= 0) {
                    i12 = i11 - (i6 - 8);
                    vec3D5 = vec3D5.addVector((i8 - i2) * i12, (i3 - i3) * i12, (i10 - i4) * i12);
                }
            } else if (i11 >= 0) {
                i12 = i11 - i6;
                vec3D5 = vec3D5.addVector((i8 - i2) * i12, (i3 - i3) * i12, (i10 - i4) * i12);
            }
            ++i7;
        }
        if (iBlockAccess1.getBlockMetadata(i2, i3, i4) >= 8) {
            boolean z13 = false;
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2, i3, i4 - 1, 2)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2, i3, i4 + 1, 3)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2 - 1, i3, i4, 4)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2 + 1, i3, i4, 5)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2, i3 + 1, i4 - 1, 2)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2, i3 + 1, i4 + 1, 3)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2 - 1, i3 + 1, i4, 4)) {
                z13 = true;
            }
            if (z13 || this.shouldSideBeRendered(iBlockAccess1, i2 + 1, i3 + 1, i4, 5)) {
                z13 = true;
            }
            if (z13) {
                vec3D5 = vec3D5.normalize().addVector(0.0, -6.0, 0.0);
            }
        }
        vec3D5 = vec3D5.normalize();
        return vec3D5;
    }

    @Override
    public void velocityToAddToEntity(World world1, int i2, int i3, int i4, Entity entity5, Vec3D vec3D6) {
        Vec3D vec3D7 = this.getFlowVector(world1, i2, i3, i4);
        vec3D6.xCoord += vec3D7.xCoord;
        vec3D6.yCoord += vec3D7.yCoord;
        vec3D6.zCoord += vec3D7.zCoord;
    }

    @Override
    public int tickRate() {
        return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? 30 : 0);
    }

    @Override
    public float getBlockBrightness(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        float f6;
        float f5 = iBlockAccess1.getLightBrightness(i2, i3, i4);
        return f5 > (f6 = iBlockAccess1.getLightBrightness(i2, i3 + 1, i4)) ? f5 : f6;
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        super.updateTick(world1, i2, i3, i4, random5);
    }

    @Override
    public int getRenderBlockPass() {
        return this.blockMaterial == Material.water ? 1 : 0;
    }

    @Override
    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
        int i6;
        if (this.blockMaterial == Material.water && random5.nextInt(64) == 0 && (i6 = world1.getBlockMetadata(i2, i3, i4)) > 0 && i6 < 8) {
            world1.playSoundEffect((float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f, "liquid.water", random5.nextFloat() * 0.25f + 0.75f, random5.nextFloat() * 1.0f + 0.5f);
        }
        if (this.blockMaterial == Material.lava && world1.getBlockMaterial(i2, i3 + 1, i4) == Material.air && !world1.isBlockOpaqueCube(i2, i3 + 1, i4) && random5.nextInt(100) == 0) {
            double d12 = (float)i2 + random5.nextFloat();
            double d8 = (double)i3 + this.maxY;
            double d10 = (float)i4 + random5.nextFloat();
            world1.spawnParticle("lava", d12, d8, d10, 0.0, 0.0, 0.0);
        }
    }

    public static double func_293_a(IBlockAccess iBlockAccess0, int i1, int i2, int i3, Material material4) {
        Vec3D vec3D5 = null;
        if (material4 == Material.water) {
            vec3D5 = ((BlockFluids)Block.waterMoving).getFlowVector(iBlockAccess0, i1, i2, i3);
        }
        if (material4 == Material.lava) {
            vec3D5 = ((BlockFluids)Block.lavaMoving).getFlowVector(iBlockAccess0, i1, i2, i3);
        }
        return vec3D5.xCoord == 0.0 && vec3D5.zCoord == 0.0 ? -1000.0 : Math.atan2(vec3D5.zCoord, vec3D5.xCoord) - 1.5707963267948966;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        this.checkForHarden(world1, i2, i3, i4);
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        this.checkForHarden(world1, i2, i3, i4);
    }

    private void checkForHarden(World world1, int i2, int i3, int i4) {
        if (world1.getBlockId(i2, i3, i4) == this.blockID && this.blockMaterial == Material.lava) {
            boolean z5 = false;
            if (z5 || world1.getBlockMaterial(i2, i3, i4 - 1) == Material.water) {
                z5 = true;
            }
            if (z5 || world1.getBlockMaterial(i2, i3, i4 + 1) == Material.water) {
                z5 = true;
            }
            if (z5 || world1.getBlockMaterial(i2 - 1, i3, i4) == Material.water) {
                z5 = true;
            }
            if (z5 || world1.getBlockMaterial(i2 + 1, i3, i4) == Material.water) {
                z5 = true;
            }
            if (z5 || world1.getBlockMaterial(i2, i3 + 1, i4) == Material.water) {
                z5 = true;
            }
            if (z5) {
                int i6 = world1.getBlockMetadata(i2, i3, i4);
                if (i6 == 0) {
                    world1.setBlockWithNotify(i2, i3, i4, Block.obsidian.blockID);
                } else if (i6 <= 4) {
                    world1.setBlockWithNotify(i2, i3, i4, Block.cobblestone.blockID);
                }
                this.triggerLavaMixEffects(world1, i2, i3, i4);
            }
        }
    }

    protected void triggerLavaMixEffects(World world1, int i2, int i3, int i4) {
        world1.playSoundEffect((float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f, "random.fizz", 0.5f, 2.6f + (world1.rand.nextFloat() - world1.rand.nextFloat()) * 0.8f);
        int i5 = 0;
        while (i5 < 8) {
            world1.spawnParticle("largesmoke", (double)i2 + Math.random(), (double)i3 + 1.2, (double)i4 + Math.random(), 0.0, 0.0, 0.0);
            ++i5;
        }
    }
}

