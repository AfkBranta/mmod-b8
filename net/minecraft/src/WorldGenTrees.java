/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenTrees
extends WorldGenerator {
    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        int i6 = random2.nextInt(3) + 4;
        boolean z7 = true;
        if (i4 >= 1 && i4 + i6 + 1 <= 128) {
            int i12;
            int i11;
            int i10;
            int i8 = i4;
            while (i8 <= i4 + 1 + i6) {
                int b9 = 1;
                if (i8 == i4) {
                    b9 = 0;
                }
                if (i8 >= i4 + 1 + i6 - 2) {
                    b9 = 2;
                }
                i10 = i3 - b9;
                while (i10 <= i3 + b9 && z7) {
                    i11 = i5 - b9;
                    while (i11 <= i5 + b9 && z7) {
                        if (i8 >= 0 && i8 < 128) {
                            i12 = world1.getBlockId(i10, i8, i11);
                            if (i12 != 0 && i12 != Block.leaves.blockID) {
                                z7 = false;
                            }
                        } else {
                            z7 = false;
                        }
                        ++i11;
                    }
                    ++i10;
                }
                ++i8;
            }
            if (!z7) {
                return false;
            }
            i8 = world1.getBlockId(i3, i4 - 1, i5);
            if ((i8 == Block.grass.blockID || i8 == Block.dirt.blockID) && i4 < 128 - i6 - 1) {
                world1.setBlock(i3, i4 - 1, i5, Block.dirt.blockID);
                int i16 = i4 - 3 + i6;
                while (i16 <= i4 + i6) {
                    i10 = i16 - (i4 + i6);
                    i11 = 1 - i10 / 2;
                    i12 = i3 - i11;
                    while (i12 <= i3 + i11) {
                        int i13 = i12 - i3;
                        int i14 = i5 - i11;
                        while (i14 <= i5 + i11) {
                            int i15 = i14 - i5;
                            if ((Math.abs(i13) != i11 || Math.abs(i15) != i11 || random2.nextInt(2) != 0 && i10 != 0) && !Block.opaqueCubeLookup[world1.getBlockId(i12, i16, i14)]) {
                                world1.setBlock(i12, i16, i14, Block.leaves.blockID);
                            }
                            ++i14;
                        }
                        ++i12;
                    }
                    ++i16;
                }
                i16 = 0;
                while (i16 < i6) {
                    i10 = world1.getBlockId(i3, i4 + i16, i5);
                    if (i10 == 0 || i10 == Block.leaves.blockID) {
                        world1.setBlock(i3, i4 + i16, i5, Block.wood.blockID);
                    }
                    ++i16;
                }
                return true;
            }
            return false;
        }
        return false;
    }
}

