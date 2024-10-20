/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityNote;
import net.minecraft.src.World;

public class BlockNote
extends BlockContainer {
    public BlockNote(int i1) {
        super(i1, 74, Material.wood);
    }

    @Override
    public int getBlockTextureFromSide(int i1) {
        return this.blockIndexInTexture;
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        if (i5 > 0 && Block.blocksList[i5].canProvidePower()) {
            boolean z6 = world1.isBlockGettingPowered(i2, i3, i4);
            TileEntityNote tileEntityNote7 = (TileEntityNote)world1.getBlockTileEntity(i2, i3, i4);
            if (tileEntityNote7.previousRedstoneState != z6) {
                if (z6) {
                    tileEntityNote7.triggerNote(world1, i2, i3, i4);
                }
                tileEntityNote7.previousRedstoneState = z6;
            }
        }
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (world1.multiplayerWorld) {
            return true;
        }
        TileEntityNote tileEntityNote6 = (TileEntityNote)world1.getBlockTileEntity(i2, i3, i4);
        tileEntityNote6.changePitch();
        tileEntityNote6.triggerNote(world1, i2, i3, i4);
        return true;
    }

    @Override
    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        if (!world1.multiplayerWorld) {
            TileEntityNote tileEntityNote6 = (TileEntityNote)world1.getBlockTileEntity(i2, i3, i4);
            tileEntityNote6.triggerNote(world1, i2, i3, i4);
        }
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityNote();
    }

    @Override
    public void playBlock(World world1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = (float)Math.pow(2.0, (double)(i6 - 12) / 12.0);
        String string8 = "harp";
        if (i5 == 1) {
            string8 = "bd";
        }
        if (i5 == 2) {
            string8 = "snare";
        }
        if (i5 == 3) {
            string8 = "hat";
        }
        if (i5 == 4) {
            string8 = "bassattack";
        }
        world1.playSoundEffect((double)i2 + 0.5, (double)i3 + 0.5, (double)i4 + 0.5, "note." + string8, 3.0f, f7);
        world1.spawnParticle("note", (double)i2 + 0.5, (double)i3 + 1.2, (double)i4 + 0.5, (double)i6 / 24.0, 0.0, 0.0);
    }
}

