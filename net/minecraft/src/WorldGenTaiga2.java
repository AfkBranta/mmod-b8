/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenTaiga2
extends WorldGenerator {
    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        int i6 = random2.nextInt(4) + 6;
        int i7 = 1 + random2.nextInt(2);
        int i8 = i6 - i7;
        int i9 = 2 + random2.nextInt(2);
        boolean z10 = true;
        if (i4 >= 1 && i4 + i6 + 1 <= 128) {
            int i15;
            int i13;
            int i21;
            int i11 = i4;
            while (i11 <= i4 + 1 + i6 && z10) {
                boolean z12 = true;
                i21 = i11 - i4 < i7 ? 0 : i9;
                i13 = i3 - i21;
                while (i13 <= i3 + i21 && z10) {
                    int i14 = i5 - i21;
                    while (i14 <= i5 + i21 && z10) {
                        if (i11 >= 0 && i11 < 128) {
                            i15 = world1.getBlockId(i13, i11, i14);
                            if (i15 != 0 && i15 != Block.leaves.blockID) {
                                z10 = false;
                            }
                        } else {
                            z10 = false;
                        }
                        ++i14;
                    }
                    ++i13;
                }
                ++i11;
            }
            if (!z10) {
                return false;
            }
            i11 = world1.getBlockId(i3, i4 - 1, i5);
            if ((i11 == Block.grass.blockID || i11 == Block.dirt.blockID) && i4 < 128 - i6 - 1) {
                int i17;
                int i16;
                world1.setBlock(i3, i4 - 1, i5, Block.dirt.blockID);
                i21 = random2.nextInt(2);
                i13 = 1;
                int b22 = 0;
                i15 = 0;
                while (i15 <= i8) {
                    i16 = i4 + i6 - i15;
                    i17 = i3 - i21;
                    while (i17 <= i3 + i21) {
                        int i18 = i17 - i3;
                        int i19 = i5 - i21;
                        while (i19 <= i5 + i21) {
                            int i20 = i19 - i5;
                            if (!(Math.abs(i18) == i21 && Math.abs(i20) == i21 && i21 > 0 || Block.opaqueCubeLookup[world1.getBlockId(i17, i16, i19)])) {
                                world1.setBlockAndMetadata(i17, i16, i19, Block.leaves.blockID, 1);
                            }
                            ++i19;
                        }
                        ++i17;
                    }
                    if (i21 >= i13) {
                        i21 = b22;
                        b22 = 1;
                        if (++i13 > i9) {
                            i13 = i9;
                        }
                    } else {
                        ++i21;
                    }
                    ++i15;
                }
                i15 = random2.nextInt(3);
                i16 = 0;
                while (i16 < i6 - i15) {
                    i17 = world1.getBlockId(i3, i4 + i16, i5);
                    if (i17 == 0 || i17 == Block.leaves.blockID) {
                        world1.setBlockAndMetadata(i3, i4 + i16, i5, Block.wood.blockID, 1);
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

