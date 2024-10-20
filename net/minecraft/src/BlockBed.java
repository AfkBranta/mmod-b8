/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumStatus;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.ModelBed;
import net.minecraft.src.World;

public class BlockBed
extends Block {
    public static final int[][] headBlockToFootBlockMap;

    static {
        int[][] nArrayArray = new int[4][];
        int[] nArray = new int[2];
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[0] = -1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[2];
        nArray3[1] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[2];
        nArray4[0] = 1;
        nArrayArray[3] = nArray4;
        headBlockToFootBlockMap = nArrayArray;
    }

    public BlockBed(int i1) {
        super(i1, 134, Material.cloth);
        this.setBounds();
    }

    @Override
    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        if (!BlockBed.isBlockFootOfBed(i6)) {
            int i7 = BlockBed.getDirectionFromMetadata(i6);
            if (world1.getBlockId(i2 += headBlockToFootBlockMap[i7][0], i3, i4 += headBlockToFootBlockMap[i7][1]) != this.blockID) {
                return true;
            }
            i6 = world1.getBlockMetadata(i2, i3, i4);
        }
        if (BlockBed.isBedOccupied(i6)) {
            entityPlayer5.addChatMessage("tile.bed.occupied");
            return true;
        }
        EnumStatus enumStatus8 = entityPlayer5.sleepInBedAt(i2, i3, i4);
        if (enumStatus8 == EnumStatus.OK) {
            BlockBed.setBedOccupied(world1, i2, i3, i4, true);
            return true;
        }
        if (enumStatus8 == EnumStatus.NOT_POSSIBLE_NOW) {
            entityPlayer5.addChatMessage("tile.bed.noSleep");
        }
        return true;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        if (i1 == 0) {
            return Block.planks.blockIndexInTexture;
        }
        int i3 = BlockBed.getDirectionFromMetadata(i2);
        int i4 = ModelBed.bedDirection[i3][i1];
        return BlockBed.isBlockFootOfBed(i2) ? (i4 == 2 ? this.blockIndexInTexture + 2 + 16 : (i4 != 5 && i4 != 4 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture + 1 + 16)) : (i4 == 3 ? this.blockIndexInTexture - 1 + 16 : (i4 != 5 && i4 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 16));
    }

    @Override
    public int getRenderType() {
        return 14;
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
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        this.setBounds();
    }

    @Override
    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
        int i6 = world1.getBlockMetadata(i2, i3, i4);
        int i7 = BlockBed.getDirectionFromMetadata(i6);
        if (BlockBed.isBlockFootOfBed(i6)) {
            if (world1.getBlockId(i2 - headBlockToFootBlockMap[i7][0], i3, i4 - headBlockToFootBlockMap[i7][1]) != this.blockID) {
                world1.setBlockWithNotify(i2, i3, i4, 0);
            }
        } else if (world1.getBlockId(i2 + headBlockToFootBlockMap[i7][0], i3, i4 + headBlockToFootBlockMap[i7][1]) != this.blockID) {
            world1.setBlockWithNotify(i2, i3, i4, 0);
            if (!world1.multiplayerWorld) {
                this.dropBlockAsItem(world1, i2, i3, i4, i6);
            }
        }
    }

    @Override
    public int idDropped(int i1, Random random2) {
        return BlockBed.isBlockFootOfBed(i1) ? 0 : Item.bed.shiftedIndex;
    }

    private void setBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }

    public static int getDirectionFromMetadata(int i0) {
        return i0 & 3;
    }

    public static boolean isBlockFootOfBed(int i0) {
        return (i0 & 8) != 0;
    }

    public static boolean isBedOccupied(int i0) {
        return (i0 & 4) != 0;
    }

    public static void setBedOccupied(World world0, int i1, int i2, int i3, boolean z4) {
        int i5 = world0.getBlockMetadata(i1, i2, i3);
        i5 = z4 ? (i5 |= 4) : (i5 &= 0xFFFFFFFB);
        world0.setBlockMetadataWithNotify(i1, i2, i3, i5);
    }

    public static ChunkCoordinates getNearestEmptyChunkCoordinates(World world0, int i1, int i2, int i3, int i4) {
        int i5 = world0.getBlockMetadata(i1, i2, i3);
        int i6 = BlockBed.getDirectionFromMetadata(i5);
        int i7 = 0;
        while (i7 <= 1) {
            int i8 = i1 - headBlockToFootBlockMap[i6][0] * i7 - 1;
            int i9 = i3 - headBlockToFootBlockMap[i6][1] * i7 - 1;
            int i10 = i8 + 2;
            int i11 = i9 + 2;
            int i12 = i8;
            while (i12 <= i10) {
                int i13 = i9;
                while (i13 <= i11) {
                    if (world0.isBlockOpaqueCube(i12, i2 - 1, i13) && world0.isAirBlock(i12, i2, i13) && world0.isAirBlock(i12, i2 + 1, i13)) {
                        if (i4 <= 0) {
                            return new ChunkCoordinates(i12, i2, i13);
                        }
                        --i4;
                    }
                    ++i13;
                }
                ++i12;
            }
            ++i7;
        }
        return null;
    }
}

