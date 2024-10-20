/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.BlockBed;
import net.minecraft.src.BlockDoor;
import net.minecraft.src.BlockFluids;
import net.minecraft.src.BlockRedstoneRepeater;
import net.minecraft.src.BlockRedstoneWire;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBed;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class RenderBlocks {
    private IBlockAccess blockAccess;
    private int overrideBlockTexture = -1;
    private boolean flipTexture = false;
    private boolean renderAllFaces = false;
    private boolean enableAO;
    private float field_22384_f;
    private float aoLightValueXNeg;
    private float aoLightValueYNeg;
    private float aoLightValueZNeg;
    private float aoLightValueXPos;
    private float aoLightValueYPos;
    private float aoLightValueZPos;
    private float field_22377_m;
    private float field_22376_n;
    private float field_22375_o;
    private float field_22374_p;
    private float field_22373_q;
    private float field_22372_r;
    private float field_22371_s;
    private float field_22370_t;
    private float field_22369_u;
    private float field_22368_v;
    private float field_22367_w;
    private float field_22366_x;
    private float field_22365_y;
    private float field_22364_z;
    private float field_22362_A;
    private float field_22360_B;
    private float field_22358_C;
    private float field_22356_D;
    private float field_22354_E;
    private float field_22353_F;
    private int field_22352_G = 1;
    private float colorRedTopLeft;
    private float colorRedBottomLeft;
    private float colorRedBottomRight;
    private float colorRedTopRight;
    private float colorGreenTopLeft;
    private float colorGreenBottomLeft;
    private float colorGreenBottomRight;
    private float colorGreenTopRight;
    private float colorBlueTopLeft;
    private float colorBlueBottomLeft;
    private float colorBlueBottomRight;
    private float colorBlueTopRight;
    private boolean field_22339_T;
    private boolean field_22338_U;
    private boolean field_22337_V;
    private boolean field_22336_W;
    private boolean field_22335_X;
    private boolean field_22334_Y;
    private boolean field_22333_Z;
    private boolean field_22363_aa;
    private boolean field_22361_ab;
    private boolean field_22359_ac;
    private boolean field_22357_ad;
    private boolean field_22355_ae;

    public RenderBlocks(IBlockAccess iBlockAccess1) {
        this.blockAccess = iBlockAccess1;
    }

    public RenderBlocks() {
    }

    public void renderBlockUsingTexture(Block block1, int i2, int i3, int i4, int i5) {
        this.overrideBlockTexture = i5;
        this.renderBlockByRenderType(block1, i2, i3, i4);
        this.overrideBlockTexture = -1;
    }

    public boolean renderBlockByRenderType(Block block1, int i2, int i3, int i4) {
        int i5 = block1.getRenderType();
        block1.setBlockBoundsBasedOnState(this.blockAccess, i2, i3, i4);
        return i5 == 0 ? this.renderStandardBlock(block1, i2, i3, i4) : (i5 == 4 ? this.renderBlockFluids(block1, i2, i3, i4) : (i5 == 13 ? this.renderBlockCactus(block1, i2, i3, i4) : (i5 == 1 ? this.renderBlockReed(block1, i2, i3, i4) : (i5 == 6 ? this.renderBlockCrops(block1, i2, i3, i4) : (i5 == 2 ? this.renderBlockTorch(block1, i2, i3, i4) : (i5 == 3 ? this.renderBlockFire(block1, i2, i3, i4) : (i5 == 5 ? this.renderBlockRedstoneWire(block1, i2, i3, i4) : (i5 == 8 ? this.renderBlockLadder(block1, i2, i3, i4) : (i5 == 7 ? this.renderBlockDoor(block1, i2, i3, i4) : (i5 == 9 ? this.renderBlockMinecartTrack(block1, i2, i3, i4) : (i5 == 10 ? this.renderBlockStairs(block1, i2, i3, i4) : (i5 == 11 ? this.renderBlockFence(block1, i2, i3, i4) : (i5 == 12 ? this.renderBlockLever(block1, i2, i3, i4) : (i5 == 14 ? this.renderBlockBed(block1, i2, i3, i4) : (i5 == 15 ? this.renderBlockRepeater(block1, i2, i3, i4) : false)))))))))))))));
    }

    private boolean renderBlockBed(Block block1, int i2, int i3, int i4) {
        float f66;
        Tessellator tessellator5 = Tessellator.instance;
        int i6 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        int i7 = BlockBed.getDirectionFromMetadata(i6);
        boolean z8 = BlockBed.isBlockFootOfBed(i6);
        float f9 = 0.5f;
        float f10 = 1.0f;
        float f11 = 0.8f;
        float f12 = 0.6f;
        float f25 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f9 * f25, f9 * f25, f9 * f25);
        int i26 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 0);
        int i27 = (i26 & 0xF) << 4;
        int i28 = i26 & 0xF0;
        double d29 = (float)i27 / 256.0f;
        double d31 = ((double)(i27 + 16) - 0.01) / 256.0;
        double d33 = (float)i28 / 256.0f;
        double d35 = ((double)(i28 + 16) - 0.01) / 256.0;
        double d37 = (double)i2 + block1.minX;
        double d39 = (double)i2 + block1.maxX;
        double d41 = (double)i3 + block1.minY + 0.1875;
        double d43 = (double)i4 + block1.minZ;
        double d45 = (double)i4 + block1.maxZ;
        tessellator5.addVertexWithUV(d37, d41, d45, d29, d35);
        tessellator5.addVertexWithUV(d37, d41, d43, d29, d33);
        tessellator5.addVertexWithUV(d39, d41, d43, d31, d33);
        tessellator5.addVertexWithUV(d39, d41, d45, d31, d35);
        float f64 = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
        tessellator5.setColorOpaque_F(f10 * f64, f10 * f64, f10 * f64);
        i27 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 1);
        i28 = (i27 & 0xF) << 4;
        int i67 = i27 & 0xF0;
        double d30 = (float)i28 / 256.0f;
        double d32 = ((double)(i28 + 16) - 0.01) / 256.0;
        double d34 = (float)i67 / 256.0f;
        double d36 = ((double)(i67 + 16) - 0.01) / 256.0;
        double d38 = d30;
        double d40 = d32;
        double d42 = d34;
        double d44 = d34;
        double d46 = d30;
        double d48 = d32;
        double d50 = d36;
        double d52 = d36;
        if (i7 == 0) {
            d40 = d30;
            d42 = d36;
            d46 = d32;
            d52 = d34;
        } else if (i7 == 2) {
            d38 = d32;
            d44 = d36;
            d48 = d30;
            d50 = d34;
        } else if (i7 == 3) {
            d38 = d32;
            d44 = d36;
            d48 = d30;
            d50 = d34;
            d40 = d30;
            d42 = d36;
            d46 = d32;
            d52 = d34;
        }
        double d54 = (double)i2 + block1.minX;
        double d56 = (double)i2 + block1.maxX;
        double d58 = (double)i3 + block1.maxY;
        double d60 = (double)i4 + block1.minZ;
        double d62 = (double)i4 + block1.maxZ;
        tessellator5.addVertexWithUV(d56, d58, d62, d46, d50);
        tessellator5.addVertexWithUV(d56, d58, d60, d38, d42);
        tessellator5.addVertexWithUV(d54, d58, d60, d40, d44);
        tessellator5.addVertexWithUV(d54, d58, d62, d48, d52);
        i26 = ModelBed.field_22280_a[i7];
        if (z8) {
            i26 = ModelBed.field_22280_a[ModelBed.field_22279_b[i7]];
        }
        int b65 = 4;
        switch (i7) {
            case 0: {
                b65 = 5;
                break;
            }
            case 1: {
                b65 = 3;
            }
            default: {
                break;
            }
            case 3: {
                b65 = 2;
            }
        }
        if (i26 != 2 && (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 - 1, 2))) {
            f66 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
            if (block1.minZ > 0.0) {
                f66 = f25;
            }
            tessellator5.setColorOpaque_F(f11 * f66, f11 * f66, f11 * f66);
            this.flipTexture = b65 == 2;
            this.renderEastFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 2));
        }
        if (i26 != 3 && (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 + 1, 3))) {
            f66 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
            if (block1.maxZ < 1.0) {
                f66 = f25;
            }
            tessellator5.setColorOpaque_F(f11 * f66, f11 * f66, f11 * f66);
            this.flipTexture = b65 == 3;
            this.renderWestFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 3));
        }
        if (i26 != 4 && (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 - 1, i3, i4, 4))) {
            f66 = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4);
            if (block1.minX > 0.0) {
                f66 = f25;
            }
            tessellator5.setColorOpaque_F(f12 * f66, f12 * f66, f12 * f66);
            this.flipTexture = b65 == 4;
            this.renderNorthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 4));
        }
        if (i26 != 5 && (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 + 1, i3, i4, 5))) {
            f66 = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
            if (block1.maxX < 1.0) {
                f66 = f25;
            }
            tessellator5.setColorOpaque_F(f12 * f66, f12 * f66, f12 * f66);
            this.flipTexture = b65 == 5;
            this.renderSouthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 5));
        }
        this.flipTexture = false;
        return true;
    }

    public boolean renderBlockTorch(Block block1, int i2, int i3, int i4) {
        int i5 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        Tessellator tessellator6 = Tessellator.instance;
        float f7 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        if (Block.lightValue[block1.blockID] > 0) {
            f7 = 1.0f;
        }
        tessellator6.setColorOpaque_F(f7, f7, f7);
        double d8 = 0.4f;
        double d10 = 0.5 - d8;
        double d12 = 0.2f;
        if (i5 == 1) {
            this.renderTorchAtAngle(block1, (double)i2 - d10, (double)i3 + d12, i4, -d8, 0.0);
        } else if (i5 == 2) {
            this.renderTorchAtAngle(block1, (double)i2 + d10, (double)i3 + d12, i4, d8, 0.0);
        } else if (i5 == 3) {
            this.renderTorchAtAngle(block1, i2, (double)i3 + d12, (double)i4 - d10, 0.0, -d8);
        } else if (i5 == 4) {
            this.renderTorchAtAngle(block1, i2, (double)i3 + d12, (double)i4 + d10, 0.0, d8);
        } else {
            this.renderTorchAtAngle(block1, i2, i3, i4, 0.0, 0.0);
        }
        return true;
    }

    private boolean renderBlockRepeater(Block block1, int i2, int i3, int i4) {
        int i5 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        int i6 = i5 & 3;
        int i7 = (i5 & 0xC) >> 2;
        this.renderStandardBlock(block1, i2, i3, i4);
        Tessellator tessellator8 = Tessellator.instance;
        float f9 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        if (Block.lightValue[block1.blockID] > 0) {
            f9 = (f9 + 1.0f) * 0.5f;
        }
        tessellator8.setColorOpaque_F(f9, f9, f9);
        double d10 = -0.1875;
        double d12 = 0.0;
        double d14 = 0.0;
        double d16 = 0.0;
        double d18 = 0.0;
        switch (i6) {
            case 0: {
                d18 = -0.3125;
                d14 = BlockRedstoneRepeater.field_22024_a[i7];
                break;
            }
            case 1: {
                d16 = 0.3125;
                d12 = -BlockRedstoneRepeater.field_22024_a[i7];
                break;
            }
            case 2: {
                d18 = 0.3125;
                d14 = -BlockRedstoneRepeater.field_22024_a[i7];
                break;
            }
            case 3: {
                d16 = -0.3125;
                d12 = BlockRedstoneRepeater.field_22024_a[i7];
            }
        }
        this.renderTorchAtAngle(block1, (double)i2 + d12, (double)i3 + d10, (double)i4 + d14, 0.0, 0.0);
        this.renderTorchAtAngle(block1, (double)i2 + d16, (double)i3 + d10, (double)i4 + d18, 0.0, 0.0);
        int i20 = block1.getBlockTextureFromSide(1);
        int i21 = (i20 & 0xF) << 4;
        int i22 = i20 & 0xF0;
        double d23 = (float)i21 / 256.0f;
        double d25 = ((float)i21 + 15.99f) / 256.0f;
        double d27 = (float)i22 / 256.0f;
        double d29 = ((float)i22 + 15.99f) / 256.0f;
        float f31 = 0.125f;
        float f32 = i2 + 1;
        float f33 = i2 + 1;
        float f34 = i2 + 0;
        float f35 = i2 + 0;
        float f36 = i4 + 0;
        float f37 = i4 + 1;
        float f38 = i4 + 1;
        float f39 = i4 + 0;
        float f40 = (float)i3 + f31;
        if (i6 == 2) {
            f32 = f33 = (float)(i2 + 0);
            f34 = f35 = (float)(i2 + 1);
            f36 = f39 = (float)(i4 + 1);
            f37 = f38 = (float)(i4 + 0);
        } else if (i6 == 3) {
            f32 = f35 = (float)(i2 + 0);
            f33 = f34 = (float)(i2 + 1);
            f36 = f37 = (float)(i4 + 0);
            f38 = f39 = (float)(i4 + 1);
        } else if (i6 == 1) {
            f32 = f35 = (float)(i2 + 1);
            f33 = f34 = (float)(i2 + 0);
            f36 = f37 = (float)(i4 + 1);
            f38 = f39 = (float)(i4 + 0);
        }
        tessellator8.addVertexWithUV(f35, f40, f39, d23, d27);
        tessellator8.addVertexWithUV(f34, f40, f38, d23, d29);
        tessellator8.addVertexWithUV(f33, f40, f37, d25, d29);
        tessellator8.addVertexWithUV(f32, f40, f36, d25, d27);
        return true;
    }

    public boolean renderBlockLever(Block block1, int i2, int i3, int i4) {
        boolean z9;
        int i5 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        int i6 = i5 & 7;
        boolean z7 = (i5 & 8) > 0;
        Tessellator tessellator8 = Tessellator.instance;
        boolean bl = z9 = this.overrideBlockTexture >= 0;
        if (!z9) {
            this.overrideBlockTexture = Block.cobblestone.blockIndexInTexture;
        }
        float f10 = 0.25f;
        float f11 = 0.1875f;
        float f12 = 0.1875f;
        if (i6 == 5) {
            block1.setBlockBounds(0.5f - f11, 0.0f, 0.5f - f10, 0.5f + f11, f12, 0.5f + f10);
        } else if (i6 == 6) {
            block1.setBlockBounds(0.5f - f10, 0.0f, 0.5f - f11, 0.5f + f10, f12, 0.5f + f11);
        } else if (i6 == 4) {
            block1.setBlockBounds(0.5f - f11, 0.5f - f10, 1.0f - f12, 0.5f + f11, 0.5f + f10, 1.0f);
        } else if (i6 == 3) {
            block1.setBlockBounds(0.5f - f11, 0.5f - f10, 0.0f, 0.5f + f11, 0.5f + f10, f12);
        } else if (i6 == 2) {
            block1.setBlockBounds(1.0f - f12, 0.5f - f10, 0.5f - f11, 1.0f, 0.5f + f10, 0.5f + f11);
        } else if (i6 == 1) {
            block1.setBlockBounds(0.0f, 0.5f - f10, 0.5f - f11, f12, 0.5f + f10, 0.5f + f11);
        }
        this.renderStandardBlock(block1, i2, i3, i4);
        if (!z9) {
            this.overrideBlockTexture = -1;
        }
        float f13 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator8.setColorOpaque_F(f13, f13, f13);
        int i14 = block1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            i14 = this.overrideBlockTexture;
        }
        int i15 = (i14 & 0xF) << 4;
        int i16 = i14 & 0xF0;
        float f17 = (float)i15 / 256.0f;
        float f18 = ((float)i15 + 15.99f) / 256.0f;
        float f19 = (float)i16 / 256.0f;
        float f20 = ((float)i16 + 15.99f) / 256.0f;
        Vec3D[] vec3D21 = new Vec3D[8];
        float f22 = 0.0625f;
        float f23 = 0.0625f;
        float f24 = 0.625f;
        vec3D21[0] = Vec3D.createVector(-f22, 0.0, -f23);
        vec3D21[1] = Vec3D.createVector(f22, 0.0, -f23);
        vec3D21[2] = Vec3D.createVector(f22, 0.0, f23);
        vec3D21[3] = Vec3D.createVector(-f22, 0.0, f23);
        vec3D21[4] = Vec3D.createVector(-f22, f24, -f23);
        vec3D21[5] = Vec3D.createVector(f22, f24, -f23);
        vec3D21[6] = Vec3D.createVector(f22, f24, f23);
        vec3D21[7] = Vec3D.createVector(-f22, f24, f23);
        int i25 = 0;
        while (i25 < 8) {
            if (z7) {
                vec3D21[i25].zCoord -= 0.0625;
                vec3D21[i25].rotateAroundX(0.69813174f);
            } else {
                vec3D21[i25].zCoord += 0.0625;
                vec3D21[i25].rotateAroundX(-0.69813174f);
            }
            if (i6 == 6) {
                vec3D21[i25].rotateAroundY(1.5707964f);
            }
            if (i6 < 5) {
                vec3D21[i25].yCoord -= 0.375;
                vec3D21[i25].rotateAroundX(1.5707964f);
                if (i6 == 4) {
                    vec3D21[i25].rotateAroundY(0.0f);
                }
                if (i6 == 3) {
                    vec3D21[i25].rotateAroundY((float)Math.PI);
                }
                if (i6 == 2) {
                    vec3D21[i25].rotateAroundY(1.5707964f);
                }
                if (i6 == 1) {
                    vec3D21[i25].rotateAroundY(-1.5707964f);
                }
                vec3D21[i25].xCoord += (double)i2 + 0.5;
                vec3D21[i25].yCoord += (double)((float)i3 + 0.5f);
                vec3D21[i25].zCoord += (double)i4 + 0.5;
            } else {
                vec3D21[i25].xCoord += (double)i2 + 0.5;
                vec3D21[i25].yCoord += (double)((float)i3 + 0.125f);
                vec3D21[i25].zCoord += (double)i4 + 0.5;
            }
            ++i25;
        }
        Vec3D vec3D30 = null;
        Vec3D vec3D26 = null;
        Vec3D vec3D27 = null;
        Vec3D vec3D28 = null;
        int i29 = 0;
        while (i29 < 6) {
            if (i29 == 0) {
                f17 = (float)(i15 + 7) / 256.0f;
                f18 = ((float)(i15 + 9) - 0.01f) / 256.0f;
                f19 = (float)(i16 + 6) / 256.0f;
                f20 = ((float)(i16 + 8) - 0.01f) / 256.0f;
            } else if (i29 == 2) {
                f17 = (float)(i15 + 7) / 256.0f;
                f18 = ((float)(i15 + 9) - 0.01f) / 256.0f;
                f19 = (float)(i16 + 6) / 256.0f;
                f20 = ((float)(i16 + 16) - 0.01f) / 256.0f;
            }
            if (i29 == 0) {
                vec3D30 = vec3D21[0];
                vec3D26 = vec3D21[1];
                vec3D27 = vec3D21[2];
                vec3D28 = vec3D21[3];
            } else if (i29 == 1) {
                vec3D30 = vec3D21[7];
                vec3D26 = vec3D21[6];
                vec3D27 = vec3D21[5];
                vec3D28 = vec3D21[4];
            } else if (i29 == 2) {
                vec3D30 = vec3D21[1];
                vec3D26 = vec3D21[0];
                vec3D27 = vec3D21[4];
                vec3D28 = vec3D21[5];
            } else if (i29 == 3) {
                vec3D30 = vec3D21[2];
                vec3D26 = vec3D21[1];
                vec3D27 = vec3D21[5];
                vec3D28 = vec3D21[6];
            } else if (i29 == 4) {
                vec3D30 = vec3D21[3];
                vec3D26 = vec3D21[2];
                vec3D27 = vec3D21[6];
                vec3D28 = vec3D21[7];
            } else if (i29 == 5) {
                vec3D30 = vec3D21[0];
                vec3D26 = vec3D21[3];
                vec3D27 = vec3D21[7];
                vec3D28 = vec3D21[4];
            }
            tessellator8.addVertexWithUV(vec3D30.xCoord, vec3D30.yCoord, vec3D30.zCoord, f17, f20);
            tessellator8.addVertexWithUV(vec3D26.xCoord, vec3D26.yCoord, vec3D26.zCoord, f18, f20);
            tessellator8.addVertexWithUV(vec3D27.xCoord, vec3D27.yCoord, vec3D27.zCoord, f18, f19);
            tessellator8.addVertexWithUV(vec3D28.xCoord, vec3D28.yCoord, vec3D28.zCoord, f17, f19);
            ++i29;
        }
        return true;
    }

    public boolean renderBlockFire(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        int i6 = block1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            i6 = this.overrideBlockTexture;
        }
        float f7 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f7, f7, f7);
        int i8 = (i6 & 0xF) << 4;
        int i9 = i6 & 0xF0;
        double d10 = (float)i8 / 256.0f;
        double d12 = ((float)i8 + 15.99f) / 256.0f;
        double d14 = (float)i9 / 256.0f;
        double d16 = ((float)i9 + 15.99f) / 256.0f;
        float f18 = 1.4f;
        if (!this.blockAccess.isBlockOpaqueCube(i2, i3 - 1, i4) && !Block.fire.canBlockCatchFire(this.blockAccess, i2, i3 - 1, i4)) {
            double d21;
            float f37 = 0.2f;
            float f20 = 0.0625f;
            if ((i2 + i3 + i4 & 1) == 1) {
                d10 = (float)i8 / 256.0f;
                d12 = ((float)i8 + 15.99f) / 256.0f;
                d14 = (float)(i9 + 16) / 256.0f;
                d16 = ((float)i9 + 15.99f + 16.0f) / 256.0f;
            }
            if ((i2 / 2 + i3 / 2 + i4 / 2 & 1) == 1) {
                d21 = d12;
                d12 = d10;
                d10 = d21;
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, i2 - 1, i3, i4)) {
                tessellator5.addVertexWithUV((float)i2 + f37, (float)i3 + f18 + f20, i4 + 1, d12, d14);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 1, d12, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV((float)i2 + f37, (float)i3 + f18 + f20, i4 + 0, d10, d14);
                tessellator5.addVertexWithUV((float)i2 + f37, (float)i3 + f18 + f20, i4 + 0, d10, d14);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 1, d12, d16);
                tessellator5.addVertexWithUV((float)i2 + f37, (float)i3 + f18 + f20, i4 + 1, d12, d14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, i2 + 1, i3, i4)) {
                tessellator5.addVertexWithUV((float)(i2 + 1) - f37, (float)i3 + f18 + f20, i4 + 0, d10, d14);
                tessellator5.addVertexWithUV(i2 + 1 - 0, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 1 - 0, (float)(i3 + 0) + f20, i4 + 1, d12, d16);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f37, (float)i3 + f18 + f20, i4 + 1, d12, d14);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f37, (float)i3 + f18 + f20, i4 + 1, d12, d14);
                tessellator5.addVertexWithUV(i2 + 1 - 0, (float)(i3 + 0) + f20, i4 + 1, d12, d16);
                tessellator5.addVertexWithUV(i2 + 1 - 0, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f37, (float)i3 + f18 + f20, i4 + 0, d10, d14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, i2, i3, i4 - 1)) {
                tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18 + f20, (float)i4 + f37, d12, d14);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 0, d12, d16);
                tessellator5.addVertexWithUV(i2 + 1, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18 + f20, (float)i4 + f37, d10, d14);
                tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18 + f20, (float)i4 + f37, d10, d14);
                tessellator5.addVertexWithUV(i2 + 1, (float)(i3 + 0) + f20, i4 + 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 0, d12, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18 + f20, (float)i4 + f37, d12, d14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, i2, i3, i4 + 1)) {
                tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18 + f20, (float)(i4 + 1) - f37, d10, d14);
                tessellator5.addVertexWithUV(i2 + 1, (float)(i3 + 0) + f20, i4 + 1 - 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 1 - 0, d12, d16);
                tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18 + f20, (float)(i4 + 1) - f37, d12, d14);
                tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18 + f20, (float)(i4 + 1) - f37, d12, d14);
                tessellator5.addVertexWithUV(i2 + 0, (float)(i3 + 0) + f20, i4 + 1 - 0, d12, d16);
                tessellator5.addVertexWithUV(i2 + 1, (float)(i3 + 0) + f20, i4 + 1 - 0, d10, d16);
                tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18 + f20, (float)(i4 + 1) - f37, d10, d14);
            }
            if (Block.fire.canBlockCatchFire(this.blockAccess, i2, i3 + 1, i4)) {
                d21 = (double)i2 + 0.5 + 0.5;
                double d23 = (double)i2 + 0.5 - 0.5;
                double d25 = (double)i4 + 0.5 + 0.5;
                double d27 = (double)i4 + 0.5 - 0.5;
                double d29 = (double)i2 + 0.5 - 0.5;
                double d31 = (double)i2 + 0.5 + 0.5;
                double d33 = (double)i4 + 0.5 - 0.5;
                double d35 = (double)i4 + 0.5 + 0.5;
                d10 = (float)i8 / 256.0f;
                d12 = ((float)i8 + 15.99f) / 256.0f;
                d14 = (float)i9 / 256.0f;
                d16 = ((float)i9 + 15.99f) / 256.0f;
                f18 = -0.2f;
                if ((i2 + ++i3 + i4 & 1) == 0) {
                    tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 0, d12, d14);
                    tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 0, d12, d16);
                    tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 1, d10, d16);
                    tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 1, d10, d14);
                    d10 = (float)i8 / 256.0f;
                    d12 = ((float)i8 + 15.99f) / 256.0f;
                    d14 = (float)(i9 + 16) / 256.0f;
                    d16 = ((float)i9 + 15.99f + 16.0f) / 256.0f;
                    tessellator5.addVertexWithUV(d31, (float)i3 + f18, i4 + 1, d12, d14);
                    tessellator5.addVertexWithUV(d23, i3 + 0, i4 + 1, d12, d16);
                    tessellator5.addVertexWithUV(d23, i3 + 0, i4 + 0, d10, d16);
                    tessellator5.addVertexWithUV(d31, (float)i3 + f18, i4 + 0, d10, d14);
                } else {
                    tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d35, d12, d14);
                    tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d27, d12, d16);
                    tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d27, d10, d16);
                    tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d35, d10, d14);
                    d10 = (float)i8 / 256.0f;
                    d12 = ((float)i8 + 15.99f) / 256.0f;
                    d14 = (float)(i9 + 16) / 256.0f;
                    d16 = ((float)i9 + 15.99f + 16.0f) / 256.0f;
                    tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d33, d12, d14);
                    tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d25, d12, d16);
                    tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d25, d10, d16);
                    tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d33, d10, d14);
                }
            }
        } else {
            double d19 = (double)i2 + 0.5 + 0.2;
            double d21 = (double)i2 + 0.5 - 0.2;
            double d23 = (double)i4 + 0.5 + 0.2;
            double d25 = (double)i4 + 0.5 - 0.2;
            double d27 = (double)i2 + 0.5 - 0.3;
            double d29 = (double)i2 + 0.5 + 0.3;
            double d31 = (double)i4 + 0.5 - 0.3;
            double d33 = (double)i4 + 0.5 + 0.3;
            tessellator5.addVertexWithUV(d27, (float)i3 + f18, i4 + 1, d12, d14);
            tessellator5.addVertexWithUV(d19, i3 + 0, i4 + 1, d12, d16);
            tessellator5.addVertexWithUV(d19, i3 + 0, i4 + 0, d10, d16);
            tessellator5.addVertexWithUV(d27, (float)i3 + f18, i4 + 0, d10, d14);
            tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 0, d12, d14);
            tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 0, d12, d16);
            tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 1, d10, d16);
            tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 1, d10, d14);
            d10 = (float)i8 / 256.0f;
            d12 = ((float)i8 + 15.99f) / 256.0f;
            d14 = (float)(i9 + 16) / 256.0f;
            d16 = ((float)i9 + 15.99f + 16.0f) / 256.0f;
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d33, d12, d14);
            tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d25, d12, d16);
            tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d25, d10, d16);
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d33, d10, d14);
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d31, d12, d14);
            tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d23, d12, d16);
            tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d23, d10, d16);
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d31, d10, d14);
            d19 = (double)i2 + 0.5 - 0.5;
            d21 = (double)i2 + 0.5 + 0.5;
            d23 = (double)i4 + 0.5 - 0.5;
            d25 = (double)i4 + 0.5 + 0.5;
            d27 = (double)i2 + 0.5 - 0.4;
            d29 = (double)i2 + 0.5 + 0.4;
            d31 = (double)i4 + 0.5 - 0.4;
            d33 = (double)i4 + 0.5 + 0.4;
            tessellator5.addVertexWithUV(d27, (float)i3 + f18, i4 + 0, d10, d14);
            tessellator5.addVertexWithUV(d19, i3 + 0, i4 + 0, d10, d16);
            tessellator5.addVertexWithUV(d19, i3 + 0, i4 + 1, d12, d16);
            tessellator5.addVertexWithUV(d27, (float)i3 + f18, i4 + 1, d12, d14);
            tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 1, d10, d14);
            tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 1, d10, d16);
            tessellator5.addVertexWithUV(d21, i3 + 0, i4 + 0, d12, d16);
            tessellator5.addVertexWithUV(d29, (float)i3 + f18, i4 + 0, d12, d14);
            d10 = (float)i8 / 256.0f;
            d12 = ((float)i8 + 15.99f) / 256.0f;
            d14 = (float)i9 / 256.0f;
            d16 = ((float)i9 + 15.99f) / 256.0f;
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d33, d10, d14);
            tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d25, d10, d16);
            tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d25, d12, d16);
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d33, d12, d14);
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f18, d31, d10, d14);
            tessellator5.addVertexWithUV(i2 + 1, i3 + 0, d23, d10, d16);
            tessellator5.addVertexWithUV(i2 + 0, i3 + 0, d23, d12, d16);
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f18, d31, d12, d14);
        }
        return true;
    }

    public boolean renderBlockRedstoneWire(Block block1, int i2, int i3, int i4) {
        boolean z28;
        Tessellator tessellator5 = Tessellator.instance;
        int i6 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        int i7 = block1.getBlockTextureFromSideAndMetadata(1, i6);
        if (this.overrideBlockTexture >= 0) {
            i7 = this.overrideBlockTexture;
        }
        float f8 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        float f9 = (float)i6 / 15.0f;
        float f10 = f9 * 0.6f + 0.4f;
        if (i6 == 0) {
            f10 = 0.0f;
        }
        float f11 = f9 * f9 * 0.7f - 0.5f;
        float f12 = f9 * f9 * 0.6f - 0.7f;
        if (f11 < 0.0f) {
            f11 = 0.0f;
        }
        if (f12 < 0.0f) {
            f12 = 0.0f;
        }
        tessellator5.setColorOpaque_F(f8 * f10, f8 * f11, f8 * f12);
        int i13 = (i7 & 0xF) << 4;
        int i14 = i7 & 0xF0;
        double d15 = (float)i13 / 256.0f;
        double d17 = ((float)i13 + 15.99f) / 256.0f;
        double d19 = (float)i14 / 256.0f;
        double d21 = ((float)i14 + 15.99f) / 256.0f;
        float f23 = 0.0f;
        float f24 = 0.03125f;
        boolean z25 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 - 1, i3, i4) || !this.blockAccess.isBlockOpaqueCube(i2 - 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 - 1, i3 - 1, i4);
        boolean z26 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 + 1, i3, i4) || !this.blockAccess.isBlockOpaqueCube(i2 + 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 + 1, i3 - 1, i4);
        boolean z27 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3, i4 - 1) || !this.blockAccess.isBlockOpaqueCube(i2, i3, i4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3 - 1, i4 - 1);
        boolean bl = z28 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3, i4 + 1) || !this.blockAccess.isBlockOpaqueCube(i2, i3, i4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3 - 1, i4 + 1);
        if (!this.blockAccess.isBlockOpaqueCube(i2, i3 + 1, i4)) {
            if (this.blockAccess.isBlockOpaqueCube(i2 - 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 - 1, i3 + 1, i4)) {
                z25 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(i2 + 1, i3, i4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2 + 1, i3 + 1, i4)) {
                z26 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(i2, i3, i4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3 + 1, i4 - 1)) {
                z27 = true;
            }
            if (this.blockAccess.isBlockOpaqueCube(i2, i3, i4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, i2, i3 + 1, i4 + 1)) {
                z28 = true;
            }
        }
        float f29 = 0.3125f;
        float f30 = i2 + 0;
        float f31 = i2 + 1;
        float f32 = i4 + 0;
        float f33 = i4 + 1;
        int b34 = 0;
        if ((z25 || z26) && !z27 && !z28) {
            b34 = 1;
        }
        if ((z27 || z28) && !z26 && !z25) {
            b34 = 2;
        }
        if (b34 != 0) {
            d15 = (float)(i13 + 16) / 256.0f;
            d17 = ((float)(i13 + 16) + 15.99f) / 256.0f;
            d19 = (float)i14 / 256.0f;
            d21 = ((float)i14 + 15.99f) / 256.0f;
        }
        if (b34 == 0) {
            if (z26 || z27 || z28 || z25) {
                if (!z25) {
                    f30 += f29;
                }
                if (!z25) {
                    d15 += (double)(f29 / 16.0f);
                }
                if (!z26) {
                    f31 -= f29;
                }
                if (!z26) {
                    d17 -= (double)(f29 / 16.0f);
                }
                if (!z27) {
                    f32 += f29;
                }
                if (!z27) {
                    d19 += (double)(f29 / 16.0f);
                }
                if (!z28) {
                    f33 -= f29;
                }
                if (!z28) {
                    d21 -= (double)(f29 / 16.0f);
                }
            }
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f33 + f23, d17, d21);
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f32 - f23, d17, d19);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f32 - f23, d15, d19);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f33 + f23, d15, d21);
        }
        if (b34 == 1) {
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f33 + f23, d17, d21);
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f32 - f23, d17, d19);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f32 - f23, d15, d19);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f33 + f23, d15, d21);
        }
        if (b34 == 2) {
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f33 + f23, d17, d21);
            tessellator5.addVertexWithUV(f31 + f23, (float)i3 + f24, f32 - f23, d15, d21);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f32 - f23, d15, d19);
            tessellator5.addVertexWithUV(f30 - f23, (float)i3 + f24, f33 + f23, d17, d19);
        }
        d15 = (float)(i13 + 16) / 256.0f;
        d17 = ((float)(i13 + 16) + 15.99f) / 256.0f;
        d19 = (float)i14 / 256.0f;
        d21 = ((float)i14 + 15.99f) / 256.0f;
        if (!this.blockAccess.isBlockOpaqueCube(i2, i3 + 1, i4)) {
            if (this.blockAccess.isBlockOpaqueCube(i2 - 1, i3, i4) && this.blockAccess.getBlockId(i2 - 1, i3 + 1, i4) == Block.redstoneWire.blockID) {
                tessellator5.addVertexWithUV((float)i2 + f24, (float)(i3 + 1) + f23, (float)(i4 + 1) + f23, d17, d19);
                tessellator5.addVertexWithUV((float)i2 + f24, (float)(i3 + 0) - f23, (float)(i4 + 1) + f23, d15, d19);
                tessellator5.addVertexWithUV((float)i2 + f24, (float)(i3 + 0) - f23, (float)(i4 + 0) - f23, d15, d21);
                tessellator5.addVertexWithUV((float)i2 + f24, (float)(i3 + 1) + f23, (float)(i4 + 0) - f23, d17, d21);
            }
            if (this.blockAccess.isBlockOpaqueCube(i2 + 1, i3, i4) && this.blockAccess.getBlockId(i2 + 1, i3 + 1, i4) == Block.redstoneWire.blockID) {
                tessellator5.addVertexWithUV((float)(i2 + 1) - f24, (float)(i3 + 0) - f23, (float)(i4 + 1) + f23, d15, d21);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f24, (float)(i3 + 1) + f23, (float)(i4 + 1) + f23, d17, d21);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f24, (float)(i3 + 1) + f23, (float)(i4 + 0) - f23, d17, d19);
                tessellator5.addVertexWithUV((float)(i2 + 1) - f24, (float)(i3 + 0) - f23, (float)(i4 + 0) - f23, d15, d19);
            }
            if (this.blockAccess.isBlockOpaqueCube(i2, i3, i4 - 1) && this.blockAccess.getBlockId(i2, i3 + 1, i4 - 1) == Block.redstoneWire.blockID) {
                tessellator5.addVertexWithUV((float)(i2 + 1) + f23, (float)(i3 + 0) - f23, (float)i4 + f24, d15, d21);
                tessellator5.addVertexWithUV((float)(i2 + 1) + f23, (float)(i3 + 1) + f23, (float)i4 + f24, d17, d21);
                tessellator5.addVertexWithUV((float)(i2 + 0) - f23, (float)(i3 + 1) + f23, (float)i4 + f24, d17, d19);
                tessellator5.addVertexWithUV((float)(i2 + 0) - f23, (float)(i3 + 0) - f23, (float)i4 + f24, d15, d19);
            }
            if (this.blockAccess.isBlockOpaqueCube(i2, i3, i4 + 1) && this.blockAccess.getBlockId(i2, i3 + 1, i4 + 1) == Block.redstoneWire.blockID) {
                tessellator5.addVertexWithUV((float)(i2 + 1) + f23, (float)(i3 + 1) + f23, (float)(i4 + 1) - f24, d17, d19);
                tessellator5.addVertexWithUV((float)(i2 + 1) + f23, (float)(i3 + 0) - f23, (float)(i4 + 1) - f24, d15, d19);
                tessellator5.addVertexWithUV((float)(i2 + 0) - f23, (float)(i3 + 0) - f23, (float)(i4 + 1) - f24, d15, d21);
                tessellator5.addVertexWithUV((float)(i2 + 0) - f23, (float)(i3 + 1) + f23, (float)(i4 + 1) - f24, d17, d21);
            }
        }
        return true;
    }

    public boolean renderBlockMinecartTrack(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        int i6 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        int i7 = block1.getBlockTextureFromSideAndMetadata(0, i6);
        if (this.overrideBlockTexture >= 0) {
            i7 = this.overrideBlockTexture;
        }
        float f8 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f8, f8, f8);
        int i9 = (i7 & 0xF) << 4;
        int i10 = i7 & 0xF0;
        double d11 = (float)i9 / 256.0f;
        double d13 = ((float)i9 + 15.99f) / 256.0f;
        double d15 = (float)i10 / 256.0f;
        double d17 = ((float)i10 + 15.99f) / 256.0f;
        float f19 = 0.0625f;
        float f20 = i2 + 1;
        float f21 = i2 + 1;
        float f22 = i2 + 0;
        float f23 = i2 + 0;
        float f24 = i4 + 0;
        float f25 = i4 + 1;
        float f26 = i4 + 1;
        float f27 = i4 + 0;
        float f28 = (float)i3 + f19;
        float f29 = (float)i3 + f19;
        float f30 = (float)i3 + f19;
        float f31 = (float)i3 + f19;
        if (i6 != 1 && i6 != 2 && i6 != 3 && i6 != 7) {
            if (i6 == 8) {
                f20 = f21 = (float)(i2 + 0);
                f22 = f23 = (float)(i2 + 1);
                f24 = f27 = (float)(i4 + 1);
                f25 = f26 = (float)(i4 + 0);
            } else if (i6 == 9) {
                f20 = f23 = (float)(i2 + 0);
                f21 = f22 = (float)(i2 + 1);
                f24 = f25 = (float)(i4 + 0);
                f26 = f27 = (float)(i4 + 1);
            }
        } else {
            f20 = f23 = (float)(i2 + 1);
            f21 = f22 = (float)(i2 + 0);
            f24 = f25 = (float)(i4 + 1);
            f26 = f27 = (float)(i4 + 0);
        }
        if (i6 != 2 && i6 != 4) {
            if (i6 == 3 || i6 == 5) {
                f29 += 1.0f;
                f30 += 1.0f;
            }
        } else {
            f28 += 1.0f;
            f31 += 1.0f;
        }
        tessellator5.addVertexWithUV(f20, f28, f24, d13, d15);
        tessellator5.addVertexWithUV(f21, f29, f25, d13, d17);
        tessellator5.addVertexWithUV(f22, f30, f26, d11, d17);
        tessellator5.addVertexWithUV(f23, f31, f27, d11, d15);
        tessellator5.addVertexWithUV(f23, f31, f27, d11, d15);
        tessellator5.addVertexWithUV(f22, f30, f26, d11, d17);
        tessellator5.addVertexWithUV(f21, f29, f25, d13, d17);
        tessellator5.addVertexWithUV(f20, f28, f24, d13, d15);
        return true;
    }

    public boolean renderBlockLadder(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        int i6 = block1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            i6 = this.overrideBlockTexture;
        }
        float f7 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f7, f7, f7);
        int i8 = (i6 & 0xF) << 4;
        int i9 = i6 & 0xF0;
        double d10 = (float)i8 / 256.0f;
        double d12 = ((float)i8 + 15.99f) / 256.0f;
        double d14 = (float)i9 / 256.0f;
        double d16 = ((float)i9 + 15.99f) / 256.0f;
        int i18 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        float f19 = 0.0f;
        float f20 = 0.05f;
        if (i18 == 5) {
            tessellator5.addVertexWithUV((float)i2 + f20, (float)(i3 + 1) + f19, (float)(i4 + 1) + f19, d10, d14);
            tessellator5.addVertexWithUV((float)i2 + f20, (float)(i3 + 0) - f19, (float)(i4 + 1) + f19, d10, d16);
            tessellator5.addVertexWithUV((float)i2 + f20, (float)(i3 + 0) - f19, (float)(i4 + 0) - f19, d12, d16);
            tessellator5.addVertexWithUV((float)i2 + f20, (float)(i3 + 1) + f19, (float)(i4 + 0) - f19, d12, d14);
        }
        if (i18 == 4) {
            tessellator5.addVertexWithUV((float)(i2 + 1) - f20, (float)(i3 + 0) - f19, (float)(i4 + 1) + f19, d12, d16);
            tessellator5.addVertexWithUV((float)(i2 + 1) - f20, (float)(i3 + 1) + f19, (float)(i4 + 1) + f19, d12, d14);
            tessellator5.addVertexWithUV((float)(i2 + 1) - f20, (float)(i3 + 1) + f19, (float)(i4 + 0) - f19, d10, d14);
            tessellator5.addVertexWithUV((float)(i2 + 1) - f20, (float)(i3 + 0) - f19, (float)(i4 + 0) - f19, d10, d16);
        }
        if (i18 == 3) {
            tessellator5.addVertexWithUV((float)(i2 + 1) + f19, (float)(i3 + 0) - f19, (float)i4 + f20, d12, d16);
            tessellator5.addVertexWithUV((float)(i2 + 1) + f19, (float)(i3 + 1) + f19, (float)i4 + f20, d12, d14);
            tessellator5.addVertexWithUV((float)(i2 + 0) - f19, (float)(i3 + 1) + f19, (float)i4 + f20, d10, d14);
            tessellator5.addVertexWithUV((float)(i2 + 0) - f19, (float)(i3 + 0) - f19, (float)i4 + f20, d10, d16);
        }
        if (i18 == 2) {
            tessellator5.addVertexWithUV((float)(i2 + 1) + f19, (float)(i3 + 1) + f19, (float)(i4 + 1) - f20, d10, d14);
            tessellator5.addVertexWithUV((float)(i2 + 1) + f19, (float)(i3 + 0) - f19, (float)(i4 + 1) - f20, d10, d16);
            tessellator5.addVertexWithUV((float)(i2 + 0) - f19, (float)(i3 + 0) - f19, (float)(i4 + 1) - f20, d12, d16);
            tessellator5.addVertexWithUV((float)(i2 + 0) - f19, (float)(i3 + 1) + f19, (float)(i4 + 1) - f20, d12, d14);
        }
        return true;
    }

    public boolean renderBlockReed(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        float f6 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f6, f6, f6);
        this.renderCrossedSquares(block1, this.blockAccess.getBlockMetadata(i2, i3, i4), i2, i3, i4);
        return true;
    }

    public boolean renderBlockCrops(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        float f6 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        tessellator5.setColorOpaque_F(f6, f6, f6);
        this.func_1245_b(block1, this.blockAccess.getBlockMetadata(i2, i3, i4), i2, (float)i3 - 0.0625f, i4);
        return true;
    }

    public void renderTorchAtAngle(Block block1, double d2, double d4, double d6, double d8, double d10) {
        Tessellator tessellator12 = Tessellator.instance;
        int i13 = block1.getBlockTextureFromSide(0);
        if (this.overrideBlockTexture >= 0) {
            i13 = this.overrideBlockTexture;
        }
        int i14 = (i13 & 0xF) << 4;
        int i15 = i13 & 0xF0;
        float f16 = (float)i14 / 256.0f;
        float f17 = ((float)i14 + 15.99f) / 256.0f;
        float f18 = (float)i15 / 256.0f;
        float f19 = ((float)i15 + 15.99f) / 256.0f;
        double d20 = (double)f16 + 0.02734375;
        double d22 = (double)f18 + 0.0234375;
        double d24 = (double)f16 + 0.03515625;
        double d26 = (double)f18 + 0.03125;
        double d28 = (d2 += 0.5) - 0.5;
        double d30 = d2 + 0.5;
        double d32 = (d6 += 0.5) - 0.5;
        double d34 = d6 + 0.5;
        double d36 = 0.0625;
        double d38 = 0.625;
        tessellator12.addVertexWithUV(d2 + d8 * (1.0 - d38) - d36, d4 + d38, d6 + d10 * (1.0 - d38) - d36, d20, d22);
        tessellator12.addVertexWithUV(d2 + d8 * (1.0 - d38) - d36, d4 + d38, d6 + d10 * (1.0 - d38) + d36, d20, d26);
        tessellator12.addVertexWithUV(d2 + d8 * (1.0 - d38) + d36, d4 + d38, d6 + d10 * (1.0 - d38) + d36, d24, d26);
        tessellator12.addVertexWithUV(d2 + d8 * (1.0 - d38) + d36, d4 + d38, d6 + d10 * (1.0 - d38) - d36, d24, d22);
        tessellator12.addVertexWithUV(d2 - d36, d4 + 1.0, d32, f16, f18);
        tessellator12.addVertexWithUV(d2 - d36 + d8, d4 + 0.0, d32 + d10, f16, f19);
        tessellator12.addVertexWithUV(d2 - d36 + d8, d4 + 0.0, d34 + d10, f17, f19);
        tessellator12.addVertexWithUV(d2 - d36, d4 + 1.0, d34, f17, f18);
        tessellator12.addVertexWithUV(d2 + d36, d4 + 1.0, d34, f16, f18);
        tessellator12.addVertexWithUV(d2 + d8 + d36, d4 + 0.0, d34 + d10, f16, f19);
        tessellator12.addVertexWithUV(d2 + d8 + d36, d4 + 0.0, d32 + d10, f17, f19);
        tessellator12.addVertexWithUV(d2 + d36, d4 + 1.0, d32, f17, f18);
        tessellator12.addVertexWithUV(d28, d4 + 1.0, d6 + d36, f16, f18);
        tessellator12.addVertexWithUV(d28 + d8, d4 + 0.0, d6 + d36 + d10, f16, f19);
        tessellator12.addVertexWithUV(d30 + d8, d4 + 0.0, d6 + d36 + d10, f17, f19);
        tessellator12.addVertexWithUV(d30, d4 + 1.0, d6 + d36, f17, f18);
        tessellator12.addVertexWithUV(d30, d4 + 1.0, d6 - d36, f16, f18);
        tessellator12.addVertexWithUV(d30 + d8, d4 + 0.0, d6 - d36 + d10, f16, f19);
        tessellator12.addVertexWithUV(d28 + d8, d4 + 0.0, d6 - d36 + d10, f17, f19);
        tessellator12.addVertexWithUV(d28, d4 + 1.0, d6 - d36, f17, f18);
    }

    public void renderCrossedSquares(Block block1, int i2, double d3, double d5, double d7) {
        Tessellator tessellator9 = Tessellator.instance;
        int i10 = block1.getBlockTextureFromSideAndMetadata(0, i2);
        if (this.overrideBlockTexture >= 0) {
            i10 = this.overrideBlockTexture;
        }
        int i11 = (i10 & 0xF) << 4;
        int i12 = i10 & 0xF0;
        double d13 = (float)i11 / 256.0f;
        double d15 = ((float)i11 + 15.99f) / 256.0f;
        double d17 = (float)i12 / 256.0f;
        double d19 = ((float)i12 + 15.99f) / 256.0f;
        double d21 = d3 + 0.5 - (double)0.45f;
        double d23 = d3 + 0.5 + (double)0.45f;
        double d25 = d7 + 0.5 - (double)0.45f;
        double d27 = d7 + 0.5 + (double)0.45f;
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d15, d17);
    }

    public void func_1245_b(Block block1, int i2, double d3, double d5, double d7) {
        Tessellator tessellator9 = Tessellator.instance;
        int i10 = block1.getBlockTextureFromSideAndMetadata(0, i2);
        if (this.overrideBlockTexture >= 0) {
            i10 = this.overrideBlockTexture;
        }
        int i11 = (i10 & 0xF) << 4;
        int i12 = i10 & 0xF0;
        double d13 = (float)i11 / 256.0f;
        double d15 = ((float)i11 + 15.99f) / 256.0f;
        double d17 = (float)i12 / 256.0f;
        double d19 = ((float)i12 + 15.99f) / 256.0f;
        double d21 = d3 + 0.5 - 0.25;
        double d23 = d3 + 0.5 + 0.25;
        double d25 = d7 + 0.5 - 0.5;
        double d27 = d7 + 0.5 + 0.5;
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d15, d17);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d15, d17);
        d21 = d3 + 0.5 - 0.5;
        d23 = d3 + 0.5 + 0.5;
        d25 = d7 + 0.5 - 0.25;
        d27 = d7 + 0.5 + 0.25;
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d25, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d25, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d25, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d25, d15, d17);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d15, d17);
        tessellator9.addVertexWithUV(d21, d5 + 1.0, d27, d13, d17);
        tessellator9.addVertexWithUV(d21, d5 + 0.0, d27, d13, d19);
        tessellator9.addVertexWithUV(d23, d5 + 0.0, d27, d15, d19);
        tessellator9.addVertexWithUV(d23, d5 + 1.0, d27, d15, d17);
    }

    public boolean renderBlockFluids(Block block1, int i2, int i3, int i4) {
        float f34;
        float f33;
        float f32;
        int i27;
        int i24;
        Tessellator tessellator5 = Tessellator.instance;
        boolean z6 = block1.shouldSideBeRendered(this.blockAccess, i2, i3 + 1, i4, 1);
        boolean z7 = block1.shouldSideBeRendered(this.blockAccess, i2, i3 - 1, i4, 0);
        boolean[] z8 = new boolean[]{block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 - 1, 2), block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 + 1, 3), block1.shouldSideBeRendered(this.blockAccess, i2 - 1, i3, i4, 4), block1.shouldSideBeRendered(this.blockAccess, i2 + 1, i3, i4, 5)};
        if (!(z6 || z7 || z8[0] || z8[1] || z8[2] || z8[3])) {
            return false;
        }
        boolean z9 = false;
        float f10 = 0.5f;
        float f11 = 1.0f;
        float f12 = 0.8f;
        float f13 = 0.6f;
        double d14 = 0.0;
        double d16 = 1.0;
        Material material18 = block1.blockMaterial;
        int i19 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        float f20 = this.func_1224_a(i2, i3, i4, material18);
        float f21 = this.func_1224_a(i2, i3, i4 + 1, material18);
        float f22 = this.func_1224_a(i2 + 1, i3, i4 + 1, material18);
        float f23 = this.func_1224_a(i2 + 1, i3, i4, material18);
        if (this.renderAllFaces || z6) {
            z9 = true;
            i24 = block1.getBlockTextureFromSideAndMetadata(1, i19);
            float f25 = (float)BlockFluids.func_293_a(this.blockAccess, i2, i3, i4, material18);
            if (f25 > -999.0f) {
                i24 = block1.getBlockTextureFromSideAndMetadata(2, i19);
            }
            int i26 = (i24 & 0xF) << 4;
            i27 = i24 & 0xF0;
            double d28 = ((double)i26 + 8.0) / 256.0;
            double d30 = ((double)i27 + 8.0) / 256.0;
            if (f25 < -999.0f) {
                f25 = 0.0f;
            } else {
                d28 = (float)(i26 + 16) / 256.0f;
                d30 = (float)(i27 + 16) / 256.0f;
            }
            f32 = MathHelper.sin(f25) * 8.0f / 256.0f;
            f33 = MathHelper.cos(f25) * 8.0f / 256.0f;
            f34 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
            tessellator5.setColorOpaque_F(f11 * f34, f11 * f34, f11 * f34);
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f20, i4 + 0, d28 - (double)f33 - (double)f32, d30 - (double)f33 + (double)f32);
            tessellator5.addVertexWithUV(i2 + 0, (float)i3 + f21, i4 + 1, d28 - (double)f33 + (double)f32, d30 + (double)f33 + (double)f32);
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f22, i4 + 1, d28 + (double)f33 + (double)f32, d30 + (double)f33 - (double)f32);
            tessellator5.addVertexWithUV(i2 + 1, (float)i3 + f23, i4 + 0, d28 + (double)f33 - (double)f32, d30 - (double)f33 - (double)f32);
        }
        if (this.renderAllFaces || z7) {
            float f48 = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
            tessellator5.setColorOpaque_F(f10 * f48, f10 * f48, f10 * f48);
            this.renderBottomFace(block1, i2, i3, i4, block1.getBlockTextureFromSide(0));
            z9 = true;
        }
        i24 = 0;
        while (i24 < 4) {
            int i49 = i2;
            i27 = i4;
            if (i24 == 0) {
                i27 = i4 - 1;
            }
            if (i24 == 1) {
                ++i27;
            }
            if (i24 == 2) {
                i49 = i2 - 1;
            }
            if (i24 == 3) {
                ++i49;
            }
            int i50 = block1.getBlockTextureFromSideAndMetadata(i24 + 2, i19);
            int i29 = (i50 & 0xF) << 4;
            int i51 = i50 & 0xF0;
            if (this.renderAllFaces || z8[i24]) {
                float f36;
                float f35;
                float f31;
                if (i24 == 0) {
                    f31 = f20;
                    f32 = f23;
                    f33 = i2;
                    f35 = i2 + 1;
                    f34 = i4;
                    f36 = i4;
                } else if (i24 == 1) {
                    f31 = f22;
                    f32 = f21;
                    f33 = i2 + 1;
                    f35 = i2;
                    f34 = i4 + 1;
                    f36 = i4 + 1;
                } else if (i24 == 2) {
                    f31 = f21;
                    f32 = f20;
                    f33 = i2;
                    f35 = i2;
                    f34 = i4 + 1;
                    f36 = i4;
                } else {
                    f31 = f23;
                    f32 = f22;
                    f33 = i2 + 1;
                    f35 = i2 + 1;
                    f34 = i4;
                    f36 = i4 + 1;
                }
                z9 = true;
                double d37 = (float)(i29 + 0) / 256.0f;
                double d39 = ((double)(i29 + 16) - 0.01) / 256.0;
                double d41 = ((float)i51 + (1.0f - f31) * 16.0f) / 256.0f;
                double d43 = ((float)i51 + (1.0f - f32) * 16.0f) / 256.0f;
                double d45 = ((double)(i51 + 16) - 0.01) / 256.0;
                float f47 = block1.getBlockBrightness(this.blockAccess, i49, i3, i27);
                f47 = i24 < 2 ? (f47 *= f12) : (f47 *= f13);
                tessellator5.setColorOpaque_F(f11 * f47, f11 * f47, f11 * f47);
                tessellator5.addVertexWithUV(f33, (float)i3 + f31, f34, d37, d41);
                tessellator5.addVertexWithUV(f35, (float)i3 + f32, f36, d39, d43);
                tessellator5.addVertexWithUV(f35, i3 + 0, f36, d39, d45);
                tessellator5.addVertexWithUV(f33, i3 + 0, f34, d37, d45);
            }
            ++i24;
        }
        block1.minY = d14;
        block1.maxY = d16;
        return z9;
    }

    private float func_1224_a(int i1, int i2, int i3, Material material4) {
        int i5 = 0;
        float f6 = 0.0f;
        int i7 = 0;
        while (i7 < 4) {
            int i8 = i1 - (i7 & 1);
            int i10 = i3 - (i7 >> 1 & 1);
            if (this.blockAccess.getBlockMaterial(i8, i2 + 1, i10) == material4) {
                return 1.0f;
            }
            Material material11 = this.blockAccess.getBlockMaterial(i8, i2, i10);
            if (material11 != material4) {
                if (!material11.isSolid()) {
                    f6 += 1.0f;
                    ++i5;
                }
            } else {
                int i12 = this.blockAccess.getBlockMetadata(i8, i2, i10);
                if (i12 >= 8 || i12 == 0) {
                    f6 += BlockFluids.getPercentAir(i12) * 10.0f;
                    i5 += 10;
                }
                f6 += BlockFluids.getPercentAir(i12);
                ++i5;
            }
            ++i7;
        }
        return 1.0f - f6 / (float)i5;
    }

    public void renderBlockFallingSand(Block block1, World world2, int i3, int i4, int i5) {
        float f6 = 0.5f;
        float f7 = 1.0f;
        float f8 = 0.8f;
        float f9 = 0.6f;
        Tessellator tessellator10 = Tessellator.instance;
        tessellator10.startDrawingQuads();
        float f11 = block1.getBlockBrightness(world2, i3, i4, i5);
        float f12 = block1.getBlockBrightness(world2, i3, i4 - 1, i5);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f6 * f12, f6 * f12, f6 * f12);
        this.renderBottomFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(0));
        f12 = block1.getBlockBrightness(world2, i3, i4 + 1, i5);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f7 * f12, f7 * f12, f7 * f12);
        this.renderTopFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(1));
        f12 = block1.getBlockBrightness(world2, i3, i4, i5 - 1);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f8 * f12, f8 * f12, f8 * f12);
        this.renderEastFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(2));
        f12 = block1.getBlockBrightness(world2, i3, i4, i5 + 1);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f8 * f12, f8 * f12, f8 * f12);
        this.renderWestFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(3));
        f12 = block1.getBlockBrightness(world2, i3 - 1, i4, i5);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f9 * f12, f9 * f12, f9 * f12);
        this.renderNorthFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(4));
        f12 = block1.getBlockBrightness(world2, i3 + 1, i4, i5);
        if (f12 < f11) {
            f12 = f11;
        }
        tessellator10.setColorOpaque_F(f9 * f12, f9 * f12, f9 * f12);
        this.renderSouthFace(block1, -0.5, -0.5, -0.5, block1.getBlockTextureFromSide(5));
        tessellator10.draw();
    }

    public boolean renderStandardBlock(Block block1, int i2, int i3, int i4) {
        int i5 = block1.colorMultiplier(this.blockAccess, i2, i3, i4);
        float f6 = (float)(i5 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(i5 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(i5 & 0xFF) / 255.0f;
        return Minecraft.isAmbientOcclusionEnabled() ? this.renderStandardBlockWithAmbientOcclusion(block1, i2, i3, i4, f6, f7, f8) : this.renderStandardBlockWithColorMultiplier(block1, i2, i3, i4, f6, f7, f8);
    }

    public boolean renderStandardBlockWithAmbientOcclusion(Block block1, int i2, int i3, int i4, float f5, float f6, float f7) {
        this.enableAO = true;
        boolean z8 = false;
        float f9 = this.field_22384_f;
        float f10 = this.field_22384_f;
        float f11 = this.field_22384_f;
        float f12 = this.field_22384_f;
        boolean z13 = true;
        boolean z14 = true;
        boolean z15 = true;
        boolean z16 = true;
        boolean z17 = true;
        boolean z18 = true;
        this.field_22384_f = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        this.aoLightValueXNeg = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4);
        this.aoLightValueYNeg = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
        this.aoLightValueZNeg = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
        this.aoLightValueXPos = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
        this.aoLightValueYPos = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
        this.aoLightValueZPos = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
        this.field_22338_U = Block.field_340_s[this.blockAccess.getBlockId(i2 + 1, i3 + 1, i4)];
        this.field_22359_ac = Block.field_340_s[this.blockAccess.getBlockId(i2 + 1, i3 - 1, i4)];
        this.field_22334_Y = Block.field_340_s[this.blockAccess.getBlockId(i2 + 1, i3, i4 + 1)];
        this.field_22363_aa = Block.field_340_s[this.blockAccess.getBlockId(i2 + 1, i3, i4 - 1)];
        this.field_22337_V = Block.field_340_s[this.blockAccess.getBlockId(i2 - 1, i3 + 1, i4)];
        this.field_22357_ad = Block.field_340_s[this.blockAccess.getBlockId(i2 - 1, i3 - 1, i4)];
        this.field_22335_X = Block.field_340_s[this.blockAccess.getBlockId(i2 - 1, i3, i4 - 1)];
        this.field_22333_Z = Block.field_340_s[this.blockAccess.getBlockId(i2 - 1, i3, i4 + 1)];
        this.field_22336_W = Block.field_340_s[this.blockAccess.getBlockId(i2, i3 + 1, i4 + 1)];
        this.field_22339_T = Block.field_340_s[this.blockAccess.getBlockId(i2, i3 + 1, i4 - 1)];
        this.field_22355_ae = Block.field_340_s[this.blockAccess.getBlockId(i2, i3 - 1, i4 + 1)];
        this.field_22361_ab = Block.field_340_s[this.blockAccess.getBlockId(i2, i3 - 1, i4 - 1)];
        if (block1.blockIndexInTexture == 3) {
            z18 = false;
            z17 = false;
            z16 = false;
            z15 = false;
            z13 = false;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 - 1, i4, 0)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueYNeg;
                f11 = this.aoLightValueYNeg;
                f10 = this.aoLightValueYNeg;
                f9 = this.aoLightValueYNeg;
            } else {
                this.field_22376_n = block1.getBlockBrightness(this.blockAccess, i2 - 1, --i3, i4);
                this.field_22374_p = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
                this.field_22373_q = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
                this.field_22371_s = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
                this.field_22377_m = !this.field_22361_ab && !this.field_22357_ad ? this.field_22376_n : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4 - 1);
                this.field_22375_o = !this.field_22355_ae && !this.field_22357_ad ? this.field_22376_n : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4 + 1);
                this.field_22372_r = !this.field_22361_ab && !this.field_22359_ac ? this.field_22371_s : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4 - 1);
                this.field_22370_t = !this.field_22355_ae && !this.field_22359_ac ? this.field_22371_s : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4 + 1);
                ++i3;
                f9 = (this.field_22375_o + this.field_22376_n + this.field_22373_q + this.aoLightValueYNeg) / 4.0f;
                f12 = (this.field_22373_q + this.aoLightValueYNeg + this.field_22370_t + this.field_22371_s) / 4.0f;
                f11 = (this.aoLightValueYNeg + this.field_22374_p + this.field_22371_s + this.field_22372_r) / 4.0f;
                f10 = (this.field_22376_n + this.field_22377_m + this.aoLightValueYNeg + this.field_22374_p) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (z13 ? f5 : 1.0f) * 0.5f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (z13 ? f6 : 1.0f) * 0.5f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (z13 ? f7 : 1.0f) * 0.5f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderBottomFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 0));
            z8 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 + 1, i4, 1)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueYPos;
                f11 = this.aoLightValueYPos;
                f10 = this.aoLightValueYPos;
                f9 = this.aoLightValueYPos;
            } else {
                this.field_22368_v = block1.getBlockBrightness(this.blockAccess, i2 - 1, ++i3, i4);
                this.field_22364_z = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
                this.field_22366_x = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
                this.field_22362_A = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
                this.field_22369_u = !this.field_22339_T && !this.field_22337_V ? this.field_22368_v : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4 - 1);
                this.field_22365_y = !this.field_22339_T && !this.field_22338_U ? this.field_22364_z : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4 - 1);
                this.field_22367_w = !this.field_22336_W && !this.field_22337_V ? this.field_22368_v : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4 + 1);
                this.field_22360_B = !this.field_22336_W && !this.field_22338_U ? this.field_22364_z : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4 + 1);
                --i3;
                f12 = (this.field_22367_w + this.field_22368_v + this.field_22362_A + this.aoLightValueYPos) / 4.0f;
                f9 = (this.field_22362_A + this.aoLightValueYPos + this.field_22360_B + this.field_22364_z) / 4.0f;
                f10 = (this.aoLightValueYPos + this.field_22366_x + this.field_22364_z + this.field_22365_y) / 4.0f;
                f11 = (this.field_22368_v + this.field_22369_u + this.aoLightValueYPos + this.field_22366_x) / 4.0f;
            }
            this.colorRedTopRight = z14 ? f5 : 1.0f;
            this.colorRedBottomRight = this.colorRedTopRight;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenTopRight = z14 ? f6 : 1.0f;
            this.colorGreenBottomRight = this.colorGreenTopRight;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueTopRight = z14 ? f7 : 1.0f;
            this.colorBlueBottomRight = this.colorBlueTopRight;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderTopFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 1));
            z8 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 - 1, 2)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueZNeg;
                f11 = this.aoLightValueZNeg;
                f10 = this.aoLightValueZNeg;
                f9 = this.aoLightValueZNeg;
            } else {
                this.field_22358_C = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, --i4);
                this.field_22374_p = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
                this.field_22366_x = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
                this.field_22356_D = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
                this.field_22377_m = !this.field_22335_X && !this.field_22361_ab ? this.field_22358_C : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3 - 1, i4);
                this.field_22369_u = !this.field_22335_X && !this.field_22339_T ? this.field_22358_C : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3 + 1, i4);
                this.field_22372_r = !this.field_22363_aa && !this.field_22361_ab ? this.field_22356_D : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3 - 1, i4);
                this.field_22365_y = !this.field_22363_aa && !this.field_22339_T ? this.field_22356_D : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3 + 1, i4);
                ++i4;
                f9 = (this.field_22358_C + this.field_22369_u + this.aoLightValueZNeg + this.field_22366_x) / 4.0f;
                f10 = (this.aoLightValueZNeg + this.field_22366_x + this.field_22356_D + this.field_22365_y) / 4.0f;
                f11 = (this.field_22374_p + this.aoLightValueZNeg + this.field_22372_r + this.field_22356_D) / 4.0f;
                f12 = (this.field_22377_m + this.field_22358_C + this.field_22374_p + this.aoLightValueZNeg) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (z15 ? f5 : 1.0f) * 0.8f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (z15 ? f6 : 1.0f) * 0.8f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (z15 ? f7 : 1.0f) * 0.8f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderEastFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 2));
            z8 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 + 1, 3)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueZPos;
                f11 = this.aoLightValueZPos;
                f10 = this.aoLightValueZPos;
                f9 = this.aoLightValueZPos;
            } else {
                this.field_22354_E = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, ++i4);
                this.field_22353_F = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
                this.field_22373_q = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
                this.field_22362_A = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
                this.field_22375_o = !this.field_22333_Z && !this.field_22355_ae ? this.field_22354_E : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3 - 1, i4);
                this.field_22367_w = !this.field_22333_Z && !this.field_22336_W ? this.field_22354_E : block1.getBlockBrightness(this.blockAccess, i2 - 1, i3 + 1, i4);
                this.field_22370_t = !this.field_22334_Y && !this.field_22355_ae ? this.field_22353_F : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3 - 1, i4);
                this.field_22360_B = !this.field_22334_Y && !this.field_22336_W ? this.field_22353_F : block1.getBlockBrightness(this.blockAccess, i2 + 1, i3 + 1, i4);
                --i4;
                f9 = (this.field_22354_E + this.field_22367_w + this.aoLightValueZPos + this.field_22362_A) / 4.0f;
                f12 = (this.aoLightValueZPos + this.field_22362_A + this.field_22353_F + this.field_22360_B) / 4.0f;
                f11 = (this.field_22373_q + this.aoLightValueZPos + this.field_22370_t + this.field_22353_F) / 4.0f;
                f10 = (this.field_22375_o + this.field_22354_E + this.field_22373_q + this.aoLightValueZPos) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (z16 ? f5 : 1.0f) * 0.8f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (z16 ? f6 : 1.0f) * 0.8f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (z16 ? f7 : 1.0f) * 0.8f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderWestFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 3));
            z8 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 - 1, i3, i4, 4)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueXNeg;
                f11 = this.aoLightValueXNeg;
                f10 = this.aoLightValueXNeg;
                f9 = this.aoLightValueXNeg;
            } else {
                this.field_22376_n = block1.getBlockBrightness(this.blockAccess, --i2, i3 - 1, i4);
                this.field_22358_C = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
                this.field_22354_E = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
                this.field_22368_v = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
                this.field_22377_m = !this.field_22335_X && !this.field_22357_ad ? this.field_22358_C : block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4 - 1);
                this.field_22375_o = !this.field_22333_Z && !this.field_22357_ad ? this.field_22354_E : block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4 + 1);
                this.field_22369_u = !this.field_22335_X && !this.field_22337_V ? this.field_22358_C : block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4 - 1);
                this.field_22367_w = !this.field_22333_Z && !this.field_22337_V ? this.field_22354_E : block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4 + 1);
                ++i2;
                f12 = (this.field_22376_n + this.field_22375_o + this.aoLightValueXNeg + this.field_22354_E) / 4.0f;
                f9 = (this.aoLightValueXNeg + this.field_22354_E + this.field_22368_v + this.field_22367_w) / 4.0f;
                f10 = (this.field_22358_C + this.aoLightValueXNeg + this.field_22369_u + this.field_22368_v) / 4.0f;
                f11 = (this.field_22377_m + this.field_22376_n + this.field_22358_C + this.aoLightValueXNeg) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (z17 ? f5 : 1.0f) * 0.6f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (z17 ? f6 : 1.0f) * 0.6f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (z17 ? f7 : 1.0f) * 0.6f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderNorthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 4));
            z8 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 + 1, i3, i4, 5)) {
            if (this.field_22352_G <= 0) {
                f12 = this.aoLightValueXPos;
                f11 = this.aoLightValueXPos;
                f10 = this.aoLightValueXPos;
                f9 = this.aoLightValueXPos;
            } else {
                this.field_22371_s = block1.getBlockBrightness(this.blockAccess, ++i2, i3 - 1, i4);
                this.field_22356_D = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
                this.field_22353_F = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
                this.field_22364_z = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
                this.field_22372_r = !this.field_22359_ac && !this.field_22363_aa ? this.field_22356_D : block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4 - 1);
                this.field_22370_t = !this.field_22359_ac && !this.field_22334_Y ? this.field_22353_F : block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4 + 1);
                this.field_22365_y = !this.field_22338_U && !this.field_22363_aa ? this.field_22356_D : block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4 - 1);
                this.field_22360_B = !this.field_22338_U && !this.field_22334_Y ? this.field_22353_F : block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4 + 1);
                --i2;
                f9 = (this.field_22371_s + this.field_22370_t + this.aoLightValueXPos + this.field_22353_F) / 4.0f;
                f12 = (this.aoLightValueXPos + this.field_22353_F + this.field_22364_z + this.field_22360_B) / 4.0f;
                f11 = (this.field_22356_D + this.aoLightValueXPos + this.field_22365_y + this.field_22364_z) / 4.0f;
                f10 = (this.field_22372_r + this.field_22371_s + this.field_22356_D + this.aoLightValueXPos) / 4.0f;
            }
            this.colorRedBottomRight = this.colorRedTopRight = (z18 ? f5 : 1.0f) * 0.6f;
            this.colorRedBottomLeft = this.colorRedTopRight;
            this.colorRedTopLeft = this.colorRedTopRight;
            this.colorGreenBottomRight = this.colorGreenTopRight = (z18 ? f6 : 1.0f) * 0.6f;
            this.colorGreenBottomLeft = this.colorGreenTopRight;
            this.colorGreenTopLeft = this.colorGreenTopRight;
            this.colorBlueBottomRight = this.colorBlueTopRight = (z18 ? f7 : 1.0f) * 0.6f;
            this.colorBlueBottomLeft = this.colorBlueTopRight;
            this.colorBlueTopLeft = this.colorBlueTopRight;
            this.colorRedTopLeft *= f9;
            this.colorGreenTopLeft *= f9;
            this.colorBlueTopLeft *= f9;
            this.colorRedBottomLeft *= f10;
            this.colorGreenBottomLeft *= f10;
            this.colorBlueBottomLeft *= f10;
            this.colorRedBottomRight *= f11;
            this.colorGreenBottomRight *= f11;
            this.colorBlueBottomRight *= f11;
            this.colorRedTopRight *= f12;
            this.colorGreenTopRight *= f12;
            this.colorBlueTopRight *= f12;
            this.renderSouthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 5));
            z8 = true;
        }
        this.enableAO = false;
        return z8;
    }

    public boolean renderStandardBlockWithColorMultiplier(Block block1, int i2, int i3, int i4, float f5, float f6, float f7) {
        float f27;
        this.enableAO = false;
        Tessellator tessellator8 = Tessellator.instance;
        boolean z9 = false;
        float f10 = 0.5f;
        float f11 = 1.0f;
        float f12 = 0.8f;
        float f13 = 0.6f;
        float f14 = f11 * f5;
        float f15 = f11 * f6;
        float f16 = f11 * f7;
        if (block1 == Block.grass) {
            f7 = 1.0f;
            f6 = 1.0f;
            f5 = 1.0f;
        }
        float f17 = f10 * f5;
        float f18 = f12 * f5;
        float f19 = f13 * f5;
        float f20 = f10 * f6;
        float f21 = f12 * f6;
        float f22 = f13 * f6;
        float f23 = f10 * f7;
        float f24 = f12 * f7;
        float f25 = f13 * f7;
        float f26 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 - 1, i4, 0)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
            tessellator8.setColorOpaque_F(f17 * f27, f20 * f27, f23 * f27);
            this.renderBottomFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 0));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 + 1, i4, 1)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
            if (block1.maxY != 1.0 && !block1.blockMaterial.getIsLiquid()) {
                f27 = f26;
            }
            tessellator8.setColorOpaque_F(f14 * f27, f15 * f27, f16 * f27);
            this.renderTopFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 1));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 - 1, 2)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
            if (block1.minZ > 0.0) {
                f27 = f26;
            }
            tessellator8.setColorOpaque_F(f18 * f27, f21 * f27, f24 * f27);
            this.renderEastFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 2));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 + 1, 3)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
            if (block1.maxZ < 1.0) {
                f27 = f26;
            }
            tessellator8.setColorOpaque_F(f18 * f27, f21 * f27, f24 * f27);
            this.renderWestFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 3));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 - 1, i3, i4, 4)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4);
            if (block1.minX > 0.0) {
                f27 = f26;
            }
            tessellator8.setColorOpaque_F(f19 * f27, f22 * f27, f25 * f27);
            this.renderNorthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 4));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 + 1, i3, i4, 5)) {
            f27 = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
            if (block1.maxX < 1.0) {
                f27 = f26;
            }
            tessellator8.setColorOpaque_F(f19 * f27, f22 * f27, f25 * f27);
            this.renderSouthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 5));
            z9 = true;
        }
        return z9;
    }

    public boolean renderBlockCactus(Block block1, int i2, int i3, int i4) {
        int i5 = block1.colorMultiplier(this.blockAccess, i2, i3, i4);
        float f6 = (float)(i5 >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(i5 >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(i5 & 0xFF) / 255.0f;
        return this.func_1230_b(block1, i2, i3, i4, f6, f7, f8);
    }

    public boolean func_1230_b(Block block1, int i2, int i3, int i4, float f5, float f6, float f7) {
        float f28;
        Tessellator tessellator8 = Tessellator.instance;
        boolean z9 = false;
        float f10 = 0.5f;
        float f11 = 1.0f;
        float f12 = 0.8f;
        float f13 = 0.6f;
        float f14 = f10 * f5;
        float f15 = f11 * f5;
        float f16 = f12 * f5;
        float f17 = f13 * f5;
        float f18 = f10 * f6;
        float f19 = f11 * f6;
        float f20 = f12 * f6;
        float f21 = f13 * f6;
        float f22 = f10 * f7;
        float f23 = f11 * f7;
        float f24 = f12 * f7;
        float f25 = f13 * f7;
        float f26 = 0.0625f;
        float f27 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 - 1, i4, 0)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
            tessellator8.setColorOpaque_F(f14 * f28, f18 * f28, f22 * f28);
            this.renderBottomFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 0));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3 + 1, i4, 1)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
            if (block1.maxY != 1.0 && !block1.blockMaterial.getIsLiquid()) {
                f28 = f27;
            }
            tessellator8.setColorOpaque_F(f15 * f28, f19 * f28, f23 * f28);
            this.renderTopFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 1));
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 - 1, 2)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
            if (block1.minZ > 0.0) {
                f28 = f27;
            }
            tessellator8.setColorOpaque_F(f16 * f28, f20 * f28, f24 * f28);
            tessellator8.setTranslationF(0.0f, 0.0f, f26);
            this.renderEastFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 2));
            tessellator8.setTranslationF(0.0f, 0.0f, -f26);
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2, i3, i4 + 1, 3)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
            if (block1.maxZ < 1.0) {
                f28 = f27;
            }
            tessellator8.setColorOpaque_F(f16 * f28, f20 * f28, f24 * f28);
            tessellator8.setTranslationF(0.0f, 0.0f, -f26);
            this.renderWestFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 3));
            tessellator8.setTranslationF(0.0f, 0.0f, f26);
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 - 1, i3, i4, 4)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4);
            if (block1.minX > 0.0) {
                f28 = f27;
            }
            tessellator8.setColorOpaque_F(f17 * f28, f21 * f28, f25 * f28);
            tessellator8.setTranslationF(f26, 0.0f, 0.0f);
            this.renderNorthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 4));
            tessellator8.setTranslationF(-f26, 0.0f, 0.0f);
            z9 = true;
        }
        if (this.renderAllFaces || block1.shouldSideBeRendered(this.blockAccess, i2 + 1, i3, i4, 5)) {
            f28 = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
            if (block1.maxX < 1.0) {
                f28 = f27;
            }
            tessellator8.setColorOpaque_F(f17 * f28, f21 * f28, f25 * f28);
            tessellator8.setTranslationF(-f26, 0.0f, 0.0f);
            this.renderSouthFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 5));
            tessellator8.setTranslationF(f26, 0.0f, 0.0f);
            z9 = true;
        }
        return z9;
    }

    public boolean renderBlockFence(Block block1, int i2, int i3, int i4) {
        float f19;
        boolean z13;
        boolean z5 = false;
        float f6 = 0.375f;
        float f7 = 0.625f;
        block1.setBlockBounds(f6, 0.0f, f6, f7, 1.0f, f7);
        this.renderStandardBlock(block1, i2, i3, i4);
        boolean z8 = false;
        boolean z9 = false;
        if (this.blockAccess.getBlockId(i2 - 1, i3, i4) == block1.blockID || this.blockAccess.getBlockId(i2 + 1, i3, i4) == block1.blockID) {
            z8 = true;
        }
        if (this.blockAccess.getBlockId(i2, i3, i4 - 1) == block1.blockID || this.blockAccess.getBlockId(i2, i3, i4 + 1) == block1.blockID) {
            z9 = true;
        }
        boolean z10 = this.blockAccess.getBlockId(i2 - 1, i3, i4) == block1.blockID;
        boolean z11 = this.blockAccess.getBlockId(i2 + 1, i3, i4) == block1.blockID;
        boolean z12 = this.blockAccess.getBlockId(i2, i3, i4 - 1) == block1.blockID;
        boolean bl = z13 = this.blockAccess.getBlockId(i2, i3, i4 + 1) == block1.blockID;
        if (!z8 && !z9) {
            z8 = true;
        }
        f6 = 0.4375f;
        f7 = 0.5625f;
        float f14 = 0.75f;
        float f15 = 0.9375f;
        float f16 = z10 ? 0.0f : f6;
        float f17 = z11 ? 1.0f : f7;
        float f18 = z12 ? 0.0f : f6;
        float f = f19 = z13 ? 1.0f : f7;
        if (z8) {
            block1.setBlockBounds(f16, f14, f6, f17, f15, f7);
            this.renderStandardBlock(block1, i2, i3, i4);
        }
        if (z9) {
            block1.setBlockBounds(f6, f14, f18, f7, f15, f19);
            this.renderStandardBlock(block1, i2, i3, i4);
        }
        f14 = 0.375f;
        f15 = 0.5625f;
        if (z8) {
            block1.setBlockBounds(f16, f14, f6, f17, f15, f7);
            this.renderStandardBlock(block1, i2, i3, i4);
        }
        if (z9) {
            block1.setBlockBounds(f6, f14, f18, f7, f15, f19);
            this.renderStandardBlock(block1, i2, i3, i4);
        }
        block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return z5;
    }

    public boolean renderBlockStairs(Block block1, int i2, int i3, int i4) {
        boolean z5 = false;
        int i6 = this.blockAccess.getBlockMetadata(i2, i3, i4);
        if (i6 == 0) {
            block1.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
            block1.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
        } else if (i6 == 1) {
            block1.setBlockBounds(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
            block1.setBlockBounds(0.5f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
        } else if (i6 == 2) {
            block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.5f);
            this.renderStandardBlock(block1, i2, i3, i4);
            block1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
        } else if (i6 == 3) {
            block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            this.renderStandardBlock(block1, i2, i3, i4);
            block1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
            this.renderStandardBlock(block1, i2, i3, i4);
        }
        block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        return z5;
    }

    public boolean renderBlockDoor(Block block1, int i2, int i3, int i4) {
        Tessellator tessellator5 = Tessellator.instance;
        BlockDoor blockDoor6 = (BlockDoor)block1;
        boolean z7 = false;
        float f8 = 0.5f;
        float f9 = 1.0f;
        float f10 = 0.8f;
        float f11 = 0.6f;
        float f12 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4);
        float f13 = block1.getBlockBrightness(this.blockAccess, i2, i3 - 1, i4);
        if (blockDoor6.minY > 0.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f8 * f13, f8 * f13, f8 * f13);
        this.renderBottomFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 0));
        z7 = true;
        f13 = block1.getBlockBrightness(this.blockAccess, i2, i3 + 1, i4);
        if (blockDoor6.maxY < 1.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f9 * f13, f9 * f13, f9 * f13);
        this.renderTopFace(block1, i2, i3, i4, block1.getBlockTexture(this.blockAccess, i2, i3, i4, 1));
        z7 = true;
        f13 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 - 1);
        if (blockDoor6.minZ > 0.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f10 * f13, f10 * f13, f10 * f13);
        int i14 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 2);
        if (i14 < 0) {
            this.flipTexture = true;
            i14 = -i14;
        }
        this.renderEastFace(block1, i2, i3, i4, i14);
        z7 = true;
        this.flipTexture = false;
        f13 = block1.getBlockBrightness(this.blockAccess, i2, i3, i4 + 1);
        if (blockDoor6.maxZ < 1.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f10 * f13, f10 * f13, f10 * f13);
        i14 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 3);
        if (i14 < 0) {
            this.flipTexture = true;
            i14 = -i14;
        }
        this.renderWestFace(block1, i2, i3, i4, i14);
        z7 = true;
        this.flipTexture = false;
        f13 = block1.getBlockBrightness(this.blockAccess, i2 - 1, i3, i4);
        if (blockDoor6.minX > 0.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f11 * f13, f11 * f13, f11 * f13);
        i14 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 4);
        if (i14 < 0) {
            this.flipTexture = true;
            i14 = -i14;
        }
        this.renderNorthFace(block1, i2, i3, i4, i14);
        z7 = true;
        this.flipTexture = false;
        f13 = block1.getBlockBrightness(this.blockAccess, i2 + 1, i3, i4);
        if (blockDoor6.maxX < 1.0) {
            f13 = f12;
        }
        if (Block.lightValue[block1.blockID] > 0) {
            f13 = 1.0f;
        }
        tessellator5.setColorOpaque_F(f11 * f13, f11 * f13, f11 * f13);
        i14 = block1.getBlockTexture(this.blockAccess, i2, i3, i4, 5);
        if (i14 < 0) {
            this.flipTexture = true;
            i14 = -i14;
        }
        this.renderSouthFace(block1, i2, i3, i4, i14);
        z7 = true;
        this.flipTexture = false;
        return z7;
    }

    public void renderBottomFace(Block block1, double d2, double d4, double d6, int i8) {
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minX * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxX * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minZ * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxZ * 16.0 - 0.01) / 256.0;
        if (block1.minX < 0.0 || block1.maxX > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minZ < 0.0 || block1.maxZ > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        double d20 = d2 + block1.minX;
        double d22 = d2 + block1.maxX;
        double d24 = d4 + block1.minY;
        double d26 = d6 + block1.minZ;
        double d28 = d6 + block1.maxZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d22, d24, d26, d14, d16);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
        } else {
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.addVertexWithUV(d22, d24, d26, d14, d16);
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
        }
    }

    public void renderTopFace(Block block1, double d2, double d4, double d6, int i8) {
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minX * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxX * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minZ * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxZ * 16.0 - 0.01) / 256.0;
        if (block1.minX < 0.0 || block1.maxX > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minZ < 0.0 || block1.maxZ > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        double d20 = d2 + block1.minX;
        double d22 = d2 + block1.maxX;
        double d24 = d4 + block1.maxY;
        double d26 = d6 + block1.minZ;
        double d28 = d6 + block1.maxZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d22, d24, d26, d14, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
        } else {
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
            tessellator9.addVertexWithUV(d22, d24, d26, d14, d16);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
        }
    }

    public void renderEastFace(Block block1, double d2, double d4, double d6, int i8) {
        double d20;
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minX * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxX * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minY * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            d20 = d12;
            d12 = d14;
            d14 = d20;
        }
        if (block1.minX < 0.0 || block1.maxX > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minY < 0.0 || block1.maxY > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        d20 = d2 + block1.minX;
        double d22 = d2 + block1.maxX;
        double d24 = d4 + block1.minY;
        double d26 = d4 + block1.maxY;
        double d28 = d6 + block1.minZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d20, d26, d28, d14, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d22, d26, d28, d12, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d22, d24, d28, d12, d18);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d20, d24, d28, d14, d18);
        } else {
            tessellator9.addVertexWithUV(d20, d26, d28, d14, d16);
            tessellator9.addVertexWithUV(d22, d26, d28, d12, d16);
            tessellator9.addVertexWithUV(d22, d24, d28, d12, d18);
            tessellator9.addVertexWithUV(d20, d24, d28, d14, d18);
        }
    }

    public void renderWestFace(Block block1, double d2, double d4, double d6, int i8) {
        double d20;
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minX * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxX * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minY * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            d20 = d12;
            d12 = d14;
            d14 = d20;
        }
        if (block1.minX < 0.0 || block1.maxX > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minY < 0.0 || block1.maxY > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        d20 = d2 + block1.minX;
        double d22 = d2 + block1.maxX;
        double d24 = d4 + block1.minY;
        double d26 = d4 + block1.maxY;
        double d28 = d6 + block1.maxZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d20, d26, d28, d12, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d22, d26, d28, d14, d16);
        } else {
            tessellator9.addVertexWithUV(d20, d26, d28, d12, d16);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d18);
            tessellator9.addVertexWithUV(d22, d24, d28, d14, d18);
            tessellator9.addVertexWithUV(d22, d26, d28, d14, d16);
        }
    }

    public void renderNorthFace(Block block1, double d2, double d4, double d6, int i8) {
        double d20;
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minZ * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxZ * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minY * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            d20 = d12;
            d12 = d14;
            d14 = d20;
        }
        if (block1.minZ < 0.0 || block1.maxZ > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minY < 0.0 || block1.maxY > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        d20 = d2 + block1.minX;
        double d22 = d4 + block1.minY;
        double d24 = d4 + block1.maxY;
        double d26 = d6 + block1.minZ;
        double d28 = d6 + block1.maxZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d20, d24, d28, d14, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d20, d22, d26, d12, d18);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d20, d22, d28, d14, d18);
        } else {
            tessellator9.addVertexWithUV(d20, d24, d28, d14, d16);
            tessellator9.addVertexWithUV(d20, d24, d26, d12, d16);
            tessellator9.addVertexWithUV(d20, d22, d26, d12, d18);
            tessellator9.addVertexWithUV(d20, d22, d28, d14, d18);
        }
    }

    public void renderSouthFace(Block block1, double d2, double d4, double d6, int i8) {
        double d20;
        Tessellator tessellator9 = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i8 = this.overrideBlockTexture;
        }
        int i10 = (i8 & 0xF) << 4;
        int i11 = i8 & 0xF0;
        double d12 = ((double)i10 + block1.minZ * 16.0) / 256.0;
        double d14 = ((double)i10 + block1.maxZ * 16.0 - 0.01) / 256.0;
        double d16 = ((double)i11 + block1.minY * 16.0) / 256.0;
        double d18 = ((double)i11 + block1.maxY * 16.0 - 0.01) / 256.0;
        if (this.flipTexture) {
            d20 = d12;
            d12 = d14;
            d14 = d20;
        }
        if (block1.minZ < 0.0 || block1.maxZ > 1.0) {
            d12 = ((float)i10 + 0.0f) / 256.0f;
            d14 = ((float)i10 + 15.99f) / 256.0f;
        }
        if (block1.minY < 0.0 || block1.maxY > 1.0) {
            d16 = ((float)i11 + 0.0f) / 256.0f;
            d18 = ((float)i11 + 15.99f) / 256.0f;
        }
        d20 = d2 + block1.maxX;
        double d22 = d4 + block1.minY;
        double d24 = d4 + block1.maxY;
        double d26 = d6 + block1.minZ;
        double d28 = d6 + block1.maxZ;
        if (this.enableAO) {
            tessellator9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator9.addVertexWithUV(d20, d22, d28, d12, d18);
            tessellator9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator9.addVertexWithUV(d20, d22, d26, d14, d18);
            tessellator9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator9.addVertexWithUV(d20, d24, d26, d14, d16);
            tessellator9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d16);
        } else {
            tessellator9.addVertexWithUV(d20, d22, d28, d12, d18);
            tessellator9.addVertexWithUV(d20, d22, d26, d14, d18);
            tessellator9.addVertexWithUV(d20, d24, d26, d14, d16);
            tessellator9.addVertexWithUV(d20, d24, d28, d12, d16);
        }
    }

    public void renderBlockOnInventory(Block block1, int i2) {
        Tessellator tessellator3 = Tessellator.instance;
        int i4 = block1.getRenderType();
        if (i4 == 0) {
            block1.setBlockBoundsForItemRender();
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderBottomFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(0, i2));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 1.0f, 0.0f);
            this.renderTopFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(1, i2));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 0.0f, -1.0f);
            this.renderEastFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(2, i2));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 0.0f, 1.0f);
            this.renderWestFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(3, i2));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(-1.0f, 0.0f, 0.0f);
            this.renderNorthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(4, i2));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(1.0f, 0.0f, 0.0f);
            this.renderSouthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSideAndMetadata(5, i2));
            tessellator3.draw();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        } else if (i4 == 1) {
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderCrossedSquares(block1, i2, -0.5, -0.5, -0.5);
            tessellator3.draw();
        } else if (i4 == 13) {
            block1.setBlockBoundsForItemRender();
            GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
            float f5 = 0.0625f;
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderBottomFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(0));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 1.0f, 0.0f);
            this.renderTopFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(1));
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 0.0f, -1.0f);
            tessellator3.setTranslationF(0.0f, 0.0f, f5);
            this.renderEastFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(2));
            tessellator3.setTranslationF(0.0f, 0.0f, -f5);
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, 0.0f, 1.0f);
            tessellator3.setTranslationF(0.0f, 0.0f, -f5);
            this.renderWestFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(3));
            tessellator3.setTranslationF(0.0f, 0.0f, f5);
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(-1.0f, 0.0f, 0.0f);
            tessellator3.setTranslationF(f5, 0.0f, 0.0f);
            this.renderNorthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(4));
            tessellator3.setTranslationF(-f5, 0.0f, 0.0f);
            tessellator3.draw();
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(1.0f, 0.0f, 0.0f);
            tessellator3.setTranslationF(-f5, 0.0f, 0.0f);
            this.renderSouthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(5));
            tessellator3.setTranslationF(f5, 0.0f, 0.0f);
            tessellator3.draw();
            GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
        } else if (i4 == 6) {
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, -1.0f, 0.0f);
            this.func_1245_b(block1, i2, -0.5, -0.5, -0.5);
            tessellator3.draw();
        } else if (i4 == 2) {
            tessellator3.startDrawingQuads();
            tessellator3.setNormal(0.0f, -1.0f, 0.0f);
            this.renderTorchAtAngle(block1, -0.5, -0.5, -0.5, 0.0, 0.0);
            tessellator3.draw();
        } else if (i4 == 10) {
            int i7 = 0;
            while (i7 < 2) {
                if (i7 == 0) {
                    block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
                }
                if (i7 == 1) {
                    block1.setBlockBounds(0.0f, 0.0f, 0.5f, 1.0f, 0.5f, 1.0f);
                }
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBottomFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(0));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 1.0f, 0.0f);
                this.renderTopFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(1));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 0.0f, -1.0f);
                this.renderEastFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(2));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 0.0f, 1.0f);
                this.renderWestFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(3));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(-1.0f, 0.0f, 0.0f);
                this.renderNorthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(4));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(1.0f, 0.0f, 0.0f);
                this.renderSouthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(5));
                tessellator3.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                ++i7;
            }
        } else if (i4 == 11) {
            int i7 = 0;
            while (i7 < 4) {
                float f6 = 0.125f;
                if (i7 == 0) {
                    block1.setBlockBounds(0.5f - f6, 0.0f, 0.0f, 0.5f + f6, 1.0f, f6 * 2.0f);
                }
                if (i7 == 1) {
                    block1.setBlockBounds(0.5f - f6, 0.0f, 1.0f - f6 * 2.0f, 0.5f + f6, 1.0f, 1.0f);
                }
                f6 = 0.0625f;
                if (i7 == 2) {
                    block1.setBlockBounds(0.5f - f6, 1.0f - f6 * 3.0f, -f6 * 2.0f, 0.5f + f6, 1.0f - f6, 1.0f + f6 * 2.0f);
                }
                if (i7 == 3) {
                    block1.setBlockBounds(0.5f - f6, 0.5f - f6 * 3.0f, -f6 * 2.0f, 0.5f + f6, 0.5f - f6, 1.0f + f6 * 2.0f);
                }
                GL11.glTranslatef((float)-0.5f, (float)-0.5f, (float)-0.5f);
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, -1.0f, 0.0f);
                this.renderBottomFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(0));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 1.0f, 0.0f);
                this.renderTopFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(1));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 0.0f, -1.0f);
                this.renderEastFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(2));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(0.0f, 0.0f, 1.0f);
                this.renderWestFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(3));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(-1.0f, 0.0f, 0.0f);
                this.renderNorthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(4));
                tessellator3.draw();
                tessellator3.startDrawingQuads();
                tessellator3.setNormal(1.0f, 0.0f, 0.0f);
                this.renderSouthFace(block1, 0.0, 0.0, 0.0, block1.getBlockTextureFromSide(5));
                tessellator3.draw();
                GL11.glTranslatef((float)0.5f, (float)0.5f, (float)0.5f);
                ++i7;
            }
            block1.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static boolean renderItemIn3d(int i0) {
        return i0 == 0 ? true : (i0 == 13 ? true : (i0 == 10 ? true : i0 == 11));
    }
}

