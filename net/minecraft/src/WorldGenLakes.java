/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

public class WorldGenLakes
extends WorldGenerator {
    private int field_15235_a;

    public WorldGenLakes(int i1) {
        this.field_15235_a = i1;
    }

    @Override
    public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
        int i32;
        i3 -= 8;
        i5 -= 8;
        while (i4 > 0 && world1.isAirBlock(i3, i4, i5)) {
            --i4;
        }
        i4 -= 4;
        boolean[] z6 = new boolean[2048];
        int i7 = random2.nextInt(4) + 4;
        int i8 = 0;
        while (i8 < i7) {
            double d9 = random2.nextDouble() * 6.0 + 3.0;
            double d11 = random2.nextDouble() * 4.0 + 2.0;
            double d13 = random2.nextDouble() * 6.0 + 3.0;
            double d15 = random2.nextDouble() * (16.0 - d9 - 2.0) + 1.0 + d9 / 2.0;
            double d17 = random2.nextDouble() * (8.0 - d11 - 4.0) + 2.0 + d11 / 2.0;
            double d19 = random2.nextDouble() * (16.0 - d13 - 2.0) + 1.0 + d13 / 2.0;
            int i21 = 1;
            while (i21 < 15) {
                int i22 = 1;
                while (i22 < 15) {
                    int i23 = 1;
                    while (i23 < 7) {
                        double d24 = ((double)i21 - d15) / (d9 / 2.0);
                        double d26 = ((double)i23 - d17) / (d11 / 2.0);
                        double d28 = ((double)i22 - d19) / (d13 / 2.0);
                        double d30 = d24 * d24 + d26 * d26 + d28 * d28;
                        if (d30 < 1.0) {
                            z6[(i21 * 16 + i22) * 8 + i23] = true;
                        }
                        ++i23;
                    }
                    ++i22;
                }
                ++i21;
            }
            ++i8;
        }
        i8 = 0;
        while (i8 < 16) {
            i32 = 0;
            while (i32 < 16) {
                int i10 = 0;
                while (i10 < 8) {
                    boolean z33;
                    boolean bl = z33 = !z6[(i8 * 16 + i32) * 8 + i10] && (i8 < 15 && z6[((i8 + 1) * 16 + i32) * 8 + i10] || i8 > 0 && z6[((i8 - 1) * 16 + i32) * 8 + i10] || i32 < 15 && z6[(i8 * 16 + i32 + 1) * 8 + i10] || i32 > 0 && z6[(i8 * 16 + (i32 - 1)) * 8 + i10] || i10 < 7 && z6[(i8 * 16 + i32) * 8 + i10 + 1] || i10 > 0 && z6[(i8 * 16 + i32) * 8 + (i10 - 1)]);
                    if (z33) {
                        Material material12 = world1.getBlockMaterial(i3 + i8, i4 + i10, i5 + i32);
                        if (i10 >= 4 && material12.getIsLiquid()) {
                            return false;
                        }
                        if (i10 < 4 && !material12.isSolid() && world1.getBlockId(i3 + i8, i4 + i10, i5 + i32) != this.field_15235_a) {
                            return false;
                        }
                    }
                    ++i10;
                }
                ++i32;
            }
            ++i8;
        }
        i8 = 0;
        while (i8 < 16) {
            i32 = 0;
            while (i32 < 16) {
                int i10 = 0;
                while (i10 < 8) {
                    if (z6[(i8 * 16 + i32) * 8 + i10]) {
                        world1.setBlock(i3 + i8, i4 + i10, i5 + i32, i10 >= 4 ? 0 : this.field_15235_a);
                    }
                    ++i10;
                }
                ++i32;
            }
            ++i8;
        }
        i8 = 0;
        while (i8 < 16) {
            i32 = 0;
            while (i32 < 16) {
                int i10 = 4;
                while (i10 < 8) {
                    if (z6[(i8 * 16 + i32) * 8 + i10] && world1.getBlockId(i3 + i8, i4 + i10 - 1, i5 + i32) == Block.dirt.blockID && world1.getSavedLightValue(EnumSkyBlock.Sky, i3 + i8, i4 + i10, i5 + i32) > 0) {
                        world1.setBlock(i3 + i8, i4 + i10 - 1, i5 + i32, Block.grass.blockID);
                    }
                    ++i10;
                }
                ++i32;
            }
            ++i8;
        }
        if (Block.blocksList[this.field_15235_a].blockMaterial == Material.lava) {
            i8 = 0;
            while (i8 < 16) {
                i32 = 0;
                while (i32 < 16) {
                    int i10 = 0;
                    while (i10 < 8) {
                        boolean z33;
                        boolean bl = z33 = !z6[(i8 * 16 + i32) * 8 + i10] && (i8 < 15 && z6[((i8 + 1) * 16 + i32) * 8 + i10] || i8 > 0 && z6[((i8 - 1) * 16 + i32) * 8 + i10] || i32 < 15 && z6[(i8 * 16 + i32 + 1) * 8 + i10] || i32 > 0 && z6[(i8 * 16 + (i32 - 1)) * 8 + i10] || i10 < 7 && z6[(i8 * 16 + i32) * 8 + i10 + 1] || i10 > 0 && z6[(i8 * 16 + i32) * 8 + (i10 - 1)]);
                        if (z33 && (i10 < 4 || random2.nextInt(2) != 0) && world1.getBlockMaterial(i3 + i8, i4 + i10, i5 + i32).isSolid()) {
                            world1.setBlock(i3 + i8, i4 + i10, i5 + i32, Block.stone.blockID);
                        }
                        ++i10;
                    }
                    ++i32;
                }
                ++i8;
            }
        }
        return true;
    }
}

