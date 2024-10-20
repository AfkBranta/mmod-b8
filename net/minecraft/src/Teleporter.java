/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class Teleporter {
    private Random field_4232_a = new Random();

    public void func_4107_a(World world1, Entity entity2) {
        if (!this.func_4106_b(world1, entity2)) {
            this.func_4108_c(world1, entity2);
            this.func_4106_b(world1, entity2);
        }
    }

    public boolean func_4106_b(World world1, Entity entity2) {
        double d18;
        int s3 = 128;
        double d4 = -1.0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = MathHelper.floor_double(entity2.posX);
        int i10 = MathHelper.floor_double(entity2.posZ);
        int i11 = i9 - s3;
        while (i11 <= i9 + s3) {
            double d12 = (double)i11 + 0.5 - entity2.posX;
            int i14 = i10 - s3;
            while (i14 <= i10 + s3) {
                double d15 = (double)i14 + 0.5 - entity2.posZ;
                int i17 = 127;
                while (i17 >= 0) {
                    if (world1.getBlockId(i11, i17, i14) == Block.portal.blockID) {
                        while (world1.getBlockId(i11, i17 - 1, i14) == Block.portal.blockID) {
                            --i17;
                        }
                        d18 = (double)i17 + 0.5 - entity2.posY;
                        double d20 = d12 * d12 + d18 * d18 + d15 * d15;
                        if (d4 < 0.0 || d20 < d4) {
                            d4 = d20;
                            i6 = i11;
                            i7 = i17;
                            i8 = i14;
                        }
                    }
                    --i17;
                }
                ++i14;
            }
            ++i11;
        }
        if (d4 >= 0.0) {
            double d22 = (double)i6 + 0.5;
            double d16 = (double)i7 + 0.5;
            d18 = (double)i8 + 0.5;
            if (world1.getBlockId(i6 - 1, i7, i8) == Block.portal.blockID) {
                d22 -= 0.5;
            }
            if (world1.getBlockId(i6 + 1, i7, i8) == Block.portal.blockID) {
                d22 += 0.5;
            }
            if (world1.getBlockId(i6, i7, i8 - 1) == Block.portal.blockID) {
                d18 -= 0.5;
            }
            if (world1.getBlockId(i6, i7, i8 + 1) == Block.portal.blockID) {
                d18 += 0.5;
            }
            System.out.println("Teleporting to " + d22 + ", " + d16 + ", " + d18);
            entity2.setLocationAndAngles(d22, d16, d18, entity2.rotationYaw, 0.0f);
            entity2.motionZ = 0.0;
            entity2.motionY = 0.0;
            entity2.motionX = 0.0;
            return true;
        }
        return false;
    }

    public boolean func_4108_c(World world1, Entity entity2) {
        boolean z34;
        double d33;
        double d32;
        int i28;
        int i27;
        int i26;
        int i25;
        int i24;
        int i23;
        int i22;
        int i21;
        int i20;
        double d18;
        int i17;
        double d15;
        int b3 = 16;
        double d4 = -1.0;
        int i6 = MathHelper.floor_double(entity2.posX);
        int i7 = MathHelper.floor_double(entity2.posY);
        int i8 = MathHelper.floor_double(entity2.posZ);
        int i9 = i6;
        int i10 = i7;
        int i11 = i8;
        int i12 = 0;
        int i13 = this.field_4232_a.nextInt(4);
        int i14 = i6 - b3;
        while (i14 <= i6 + b3) {
            d15 = (double)i14 + 0.5 - entity2.posX;
            i17 = i8 - b3;
            while (i17 <= i8 + b3) {
                d18 = (double)i17 + 0.5 - entity2.posZ;
                i20 = 127;
                while (i20 >= 0) {
                    if (world1.isAirBlock(i14, i20, i17)) {
                        while (i20 > 0 && world1.isAirBlock(i14, i20 - 1, i17)) {
                            --i20;
                        }
                        i21 = i13;
                        block4: while (i21 < i13 + 4) {
                            i22 = i21 % 2;
                            i23 = 1 - i22;
                            if (i21 % 4 >= 2) {
                                i22 = -i22;
                                i23 = -i23;
                            }
                            i24 = 0;
                            while (i24 < 3) {
                                i25 = 0;
                                while (i25 < 4) {
                                    i26 = -1;
                                    while (i26 < 4) {
                                        i27 = i14 + (i25 - 1) * i22 + i24 * i23;
                                        i28 = i20 + i26;
                                        int i29 = i17 + (i25 - 1) * i23 - i24 * i22;
                                        if (i26 < 0 && !world1.getBlockMaterial(i27, i28, i29).isSolid() || i26 >= 0 && !world1.isAirBlock(i27, i28, i29)) break block4;
                                        ++i26;
                                    }
                                    ++i25;
                                }
                                ++i24;
                            }
                            d32 = (double)i20 + 0.5 - entity2.posY;
                            d33 = d15 * d15 + d32 * d32 + d18 * d18;
                            if (d4 < 0.0 || d33 < d4) {
                                d4 = d33;
                                i9 = i14;
                                i10 = i20;
                                i11 = i17;
                                i12 = i21 % 4;
                            }
                            ++i21;
                        }
                    }
                    --i20;
                }
                ++i17;
            }
            ++i14;
        }
        if (d4 < 0.0) {
            i14 = i6 - b3;
            while (i14 <= i6 + b3) {
                d15 = (double)i14 + 0.5 - entity2.posX;
                i17 = i8 - b3;
                while (i17 <= i8 + b3) {
                    d18 = (double)i17 + 0.5 - entity2.posZ;
                    i20 = 127;
                    while (i20 >= 0) {
                        if (world1.isAirBlock(i14, i20, i17)) {
                            while (world1.isAirBlock(i14, i20 - 1, i17)) {
                                --i20;
                            }
                            i21 = i13;
                            block12: while (i21 < i13 + 2) {
                                i22 = i21 % 2;
                                i23 = 1 - i22;
                                i24 = 0;
                                while (i24 < 4) {
                                    i25 = -1;
                                    while (i25 < 4) {
                                        i26 = i14 + (i24 - 1) * i22;
                                        i27 = i20 + i25;
                                        i28 = i17 + (i24 - 1) * i23;
                                        if (i25 < 0 && !world1.getBlockMaterial(i26, i27, i28).isSolid() || i25 >= 0 && !world1.isAirBlock(i26, i27, i28)) break block12;
                                        ++i25;
                                    }
                                    ++i24;
                                }
                                d32 = (double)i20 + 0.5 - entity2.posY;
                                d33 = d15 * d15 + d32 * d32 + d18 * d18;
                                if (d4 < 0.0 || d33 < d4) {
                                    d4 = d33;
                                    i9 = i14;
                                    i10 = i20;
                                    i11 = i17;
                                    i12 = i21 % 2;
                                }
                                ++i21;
                            }
                        }
                        --i20;
                    }
                    ++i17;
                }
                ++i14;
            }
        }
        int i30 = i9;
        int i16 = i10;
        i17 = i11;
        int i31 = i12 % 2;
        int i19 = 1 - i31;
        if (i12 % 4 >= 2) {
            i31 = -i31;
            i19 = -i19;
        }
        if (d4 < 0.0) {
            if (i10 < 70) {
                i10 = 70;
            }
            if (i10 > 118) {
                i10 = 118;
            }
            i16 = i10;
            i20 = -1;
            while (i20 <= 1) {
                i21 = 1;
                while (i21 < 3) {
                    i22 = -1;
                    while (i22 < 3) {
                        i23 = i30 + (i21 - 1) * i31 + i20 * i19;
                        i24 = i16 + i22;
                        i25 = i17 + (i21 - 1) * i19 - i20 * i31;
                        z34 = i22 < 0;
                        world1.setBlockWithNotify(i23, i24, i25, z34 ? Block.obsidian.blockID : 0);
                        ++i22;
                    }
                    ++i21;
                }
                ++i20;
            }
        }
        i20 = 0;
        while (i20 < 4) {
            world1.editingBlocks = true;
            i21 = 0;
            while (i21 < 4) {
                i22 = -1;
                while (i22 < 4) {
                    i23 = i30 + (i21 - 1) * i31;
                    i24 = i16 + i22;
                    i25 = i17 + (i21 - 1) * i19;
                    z34 = i21 == 0 || i21 == 3 || i22 == -1 || i22 == 3;
                    world1.setBlockWithNotify(i23, i24, i25, z34 ? Block.obsidian.blockID : Block.portal.blockID);
                    ++i22;
                }
                ++i21;
            }
            world1.editingBlocks = false;
            i21 = 0;
            while (i21 < 4) {
                i22 = -1;
                while (i22 < 4) {
                    i23 = i30 + (i21 - 1) * i31;
                    i24 = i16 + i22;
                    i25 = i17 + (i21 - 1) * i19;
                    world1.notifyBlocksOfNeighborChange(i23, i24, i25, world1.getBlockId(i23, i24, i25));
                    ++i22;
                }
                ++i21;
            }
            ++i20;
        }
        return true;
    }
}

