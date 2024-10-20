/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.World;

public class BlockDispenser
extends BlockContainer {
    protected BlockDispenser(int i1) {
        super(i1, Material.rock);
        this.blockIndexInTexture = 45;
    }

    @Override
    public int tickRate() {
        return 4;
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return Block.dispenser.blockID;
    }

    @Override
    public void onBlockAdded(World world1, int i2, int i3, int i4) {
        super.onBlockAdded(world1, i2, i3, i4);
        this.setDispenserDefaultDirection(world1, i2, i3, i4);
    }

    private void setDispenserDefaultDirection(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3, i4 - 1);
        int i6 = world1.getBlockId(i2, i3, i4 + 1);
        int i7 = world1.getBlockId(i2 - 1, i3, i4);
        int i8 = world1.getBlockId(i2 + 1, i3, i4);
        int b9 = 3;
        if (Block.opaqueCubeLookup[i5] && !Block.opaqueCubeLookup[i6]) {
            b9 = 3;
        }
        if (Block.opaqueCubeLookup[i6] && !Block.opaqueCubeLookup[i5]) {
            b9 = 2;
        }
        if (Block.opaqueCubeLookup[i7] && !Block.opaqueCubeLookup[i8]) {
            b9 = 5;
        }
        if (Block.opaqueCubeLookup[i8] && !Block.opaqueCubeLookup[i7]) {
            b9 = 4;
        }
        world1.setBlockMetadataWithNotify(i2, i3, i4, b9);
    }

    @Override
    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (i5 == 1) {
            return this.blockIndexInTexture + 17;
        }
        if (i5 == 0) {
            return this.blockIndexInTexture + 17;
        }
        int i6 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
        return i5 != i6 ? this.blockIndexInTexture : this.blockIndexInTexture + 1;
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return i1 == 1 ? this.blockIndexInTexture + 17 : (i1 == 0 ? this.blockIndexInTexture + 17 : (i1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (world1.multiplayerWorld) {
            return true;
        }
        TileEntityDispenser tileEntityDispenser6 = (TileEntityDispenser)world1.getBlockTileEntity(i2, i3, i4);
        entityPlayer5.displayGUIDispenser(tileEntityDispenser6);
        return true;
    }

    private void dispenseItem(World world1, int i2, int i3, int i4, Random random5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        float f9 = 0.0f;
        float f10 = 0.0f;
        if (i6 == 3) {
            f10 = 1.0f;
        } else if (i6 == 2) {
            f10 = -1.0f;
        } else {
            f9 = i6 == 5 ? 1.0f : -1.0f;
        }
        TileEntityDispenser tileEntityDispenser11 = (TileEntityDispenser)world1.getBlockTileEntity(i2, i3, i4);
        ItemStack itemStack12 = tileEntityDispenser11.getRandomStackFromInventory();
        double d13 = (double)i2 + (double)f9 * 0.5 + 0.5;
        double d15 = (double)i3 + 0.5;
        double d17 = (double)i4 + (double)f10 * 0.5 + 0.5;
        if (itemStack12 == null) {
            world1.playSoundEffect(i2, i3, i4, "random.click", 1.0f, 1.2f);
        } else {
            double d20;
            if (itemStack12.itemID == Item.arrow.shiftedIndex) {
                EntityArrow entityArrow19 = new EntityArrow(world1, d13, d15, d17);
                entityArrow19.setArrowHeading(f9, 0.1f, f10, 1.1f, 6.0f);
                world1.entityJoinedWorld(entityArrow19);
                world1.playSoundEffect(i2, i3, i4, "random.bow", 1.0f, 1.2f);
            } else if (itemStack12.itemID == Item.egg.shiftedIndex) {
                EntityEgg entityEgg34 = new EntityEgg(world1, d13, d15, d17);
                entityEgg34.func_20048_a(f9, 0.1f, f10, 1.1f, 6.0f);
                world1.entityJoinedWorld(entityEgg34);
                world1.playSoundEffect(i2, i3, i4, "random.bow", 1.0f, 1.2f);
            } else if (itemStack12.itemID == Item.snowball.shiftedIndex) {
                EntitySnowball entitySnowball35 = new EntitySnowball(world1, d13, d15, d17);
                entitySnowball35.func_467_a(f9, 0.1f, f10, 1.1f, 6.0f);
                world1.entityJoinedWorld(entitySnowball35);
                world1.playSoundEffect(i2, i3, i4, "random.bow", 1.0f, 1.2f);
            } else {
                EntityItem entityItem36 = new EntityItem(world1, d13, d15 - 0.3, d17, itemStack12);
                d20 = random5.nextDouble() * 0.1 + 0.2;
                entityItem36.motionX = (double)f9 * d20;
                entityItem36.motionY = 0.2f;
                entityItem36.motionZ = (double)f10 * d20;
                entityItem36.motionX += random5.nextGaussian() * (double)0.0075f * 6.0;
                entityItem36.motionY += random5.nextGaussian() * (double)0.0075f * 6.0;
                entityItem36.motionZ += random5.nextGaussian() * (double)0.0075f * 6.0;
                world1.entityJoinedWorld(entityItem36);
                world1.playSoundEffect(i2, i3, i4, "random.click", 1.0f, 1.0f);
            }
            int i37 = 0;
            while (i37 < 10) {
                d20 = random5.nextDouble() * 0.2 + 0.01;
                double d22 = d13 + (double)f9 * 0.01 + (random5.nextDouble() - 0.5) * (double)f10 * 0.5;
                double d24 = d15 + (random5.nextDouble() - 0.5) * 0.5;
                double d26 = d17 + (double)f10 * 0.01 + (random5.nextDouble() - 0.5) * (double)f9 * 0.5;
                double d28 = (double)f9 * d20 + random5.nextGaussian() * 0.01;
                double d30 = -0.03 + random5.nextGaussian() * 0.01;
                double d32 = (double)f10 * d20 + random5.nextGaussian() * 0.01;
                world1.spawnParticle("smoke", d22, d24, d26, d28, d30, d32);
                ++i37;
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (i5 > 0 && Block.blocksList[i5].canProvidePower()) {
            boolean z6;
            boolean bl = z6 = world1.isBlockIndirectlyGettingPowered(i2, i3, i4) || world1.isBlockIndirectlyGettingPowered(i2, i3 + 1, i4);
            if (z6) {
                world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
            }
        }
    }

    @Override
    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
        if (world1.isBlockIndirectlyGettingPowered(i2, i3, i4) || world1.isBlockIndirectlyGettingPowered(i2, i3 + 1, i4)) {
            this.dispenseItem(world1, i2, i3, i4, random5);
        }
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityDispenser();
    }

    @Override
    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
        int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0f / 360.0f) + 0.5) & 3;
        if (i6 == 0) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 2);
        }
        if (i6 == 1) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 5);
        }
        if (i6 == 2) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 3);
        }
        if (i6 == 3) {
            world1.setBlockMetadataWithNotify(i2, i3, i4, 4);
        }
    }
}

