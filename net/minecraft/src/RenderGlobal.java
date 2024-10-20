/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.ARBOcclusionQuery
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBubbleFX;
import net.minecraft.src.EntityExplodeFX;
import net.minecraft.src.EntityFlameFX;
import net.minecraft.src.EntityHeartFX;
import net.minecraft.src.EntityLavaFX;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityNoteFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.EntityReddustFX;
import net.minecraft.src.EntitySlimeFX;
import net.minecraft.src.EntitySmokeFX;
import net.minecraft.src.EntitySorter;
import net.minecraft.src.EntitySplashFX;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.ICamera;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderList;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderSorter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal
implements IWorldAccess {
    public List tileEntities = new ArrayList();
    private World worldObj;
    private RenderEngine renderEngine;
    private List worldRenderersToUpdate = new ArrayList();
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    private int field_1440_s;
    private Minecraft mc;
    private RenderBlocks field_1438_u;
    private IntBuffer field_1437_v;
    private boolean occlusionEnabled = false;
    private int cloudOffsetX = 0;
    private int starGLCallList;
    private int field_1433_z;
    private int field_1432_A;
    private int field_1431_B;
    private int field_1430_C;
    private int field_1429_D;
    private int field_1428_E;
    private int field_1427_F;
    private int field_1426_G;
    private int renderDistance = -1;
    private int field_1424_I = 2;
    private int field_1423_J;
    private int field_1422_K;
    private int field_1421_L;
    int[] field_1457_b = new int[50000];
    IntBuffer field_1456_c = GLAllocation.createDirectIntBuffer(64);
    private int renderersLoaded;
    private int renderersBeingClipped;
    private int renderersBeingOccluded;
    private int renderersBeingRendered;
    private int renderersSkippingRenderPass;
    private int field_21156_R;
    private List field_1415_R = new ArrayList();
    private RenderList[] field_1414_S = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
    int field_1455_d = 0;
    int field_1454_e = GLAllocation.generateDisplayLists(1);
    double prevSortX = -9999.0;
    double prevSortY = -9999.0;
    double prevSortZ = -9999.0;
    public float field_1450_i;
    int frustrumCheckOffset = 0;

    public RenderGlobal(Minecraft minecraft1, RenderEngine renderEngine2) {
        int i9;
        this.mc = minecraft1;
        this.renderEngine = renderEngine2;
        int b3 = 64;
        this.field_1440_s = GLAllocation.generateDisplayLists(b3 * b3 * b3 * 3);
        this.occlusionEnabled = minecraft1.func_6251_l().checkARBOcclusion();
        if (this.occlusionEnabled) {
            this.field_1456_c.clear();
            this.field_1437_v = GLAllocation.createDirectIntBuffer(b3 * b3 * b3);
            this.field_1437_v.clear();
            this.field_1437_v.position(0);
            this.field_1437_v.limit(b3 * b3 * b3);
            ARBOcclusionQuery.glGenQueriesARB((IntBuffer)this.field_1437_v);
        }
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList((int)this.starGLCallList, (int)4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        Tessellator tessellator4 = Tessellator.instance;
        this.field_1433_z = this.starGLCallList + 1;
        GL11.glNewList((int)this.field_1433_z, (int)4864);
        int b6 = 64;
        int i7 = 256 / b6 + 2;
        float f5 = 16.0f;
        int i8 = -b6 * i7;
        while (i8 <= b6 * i7) {
            i9 = -b6 * i7;
            while (i9 <= b6 * i7) {
                tessellator4.startDrawingQuads();
                tessellator4.addVertex(i8 + 0, f5, i9 + 0);
                tessellator4.addVertex(i8 + b6, f5, i9 + 0);
                tessellator4.addVertex(i8 + b6, f5, i9 + b6);
                tessellator4.addVertex(i8 + 0, f5, i9 + b6);
                tessellator4.draw();
                i9 += b6;
            }
            i8 += b6;
        }
        GL11.glEndList();
        this.field_1432_A = this.starGLCallList + 2;
        GL11.glNewList((int)this.field_1432_A, (int)4864);
        f5 = -16.0f;
        tessellator4.startDrawingQuads();
        i8 = -b6 * i7;
        while (i8 <= b6 * i7) {
            i9 = -b6 * i7;
            while (i9 <= b6 * i7) {
                tessellator4.addVertex(i8 + b6, f5, i9 + 0);
                tessellator4.addVertex(i8 + 0, f5, i9 + 0);
                tessellator4.addVertex(i8 + 0, f5, i9 + b6);
                tessellator4.addVertex(i8 + b6, f5, i9 + b6);
                i9 += b6;
            }
            i8 += b6;
        }
        tessellator4.draw();
        GL11.glEndList();
    }

    private void renderStars() {
        Random random1 = new Random(10842L);
        Tessellator tessellator2 = Tessellator.instance;
        tessellator2.startDrawingQuads();
        int i3 = 0;
        while (i3 < 1500) {
            double d4 = random1.nextFloat() * 2.0f - 1.0f;
            double d6 = random1.nextFloat() * 2.0f - 1.0f;
            double d8 = random1.nextFloat() * 2.0f - 1.0f;
            double d10 = 0.25f + random1.nextFloat() * 0.25f;
            double d12 = d4 * d4 + d6 * d6 + d8 * d8;
            if (d12 < 1.0 && d12 > 0.01) {
                d12 = 1.0 / Math.sqrt(d12);
                double d14 = (d4 *= d12) * 100.0;
                double d16 = (d6 *= d12) * 100.0;
                double d18 = (d8 *= d12) * 100.0;
                double d20 = Math.atan2(d4, d8);
                double d22 = Math.sin(d20);
                double d24 = Math.cos(d20);
                double d26 = Math.atan2(Math.sqrt(d4 * d4 + d8 * d8), d6);
                double d28 = Math.sin(d26);
                double d30 = Math.cos(d26);
                double d32 = random1.nextDouble() * Math.PI * 2.0;
                double d34 = Math.sin(d32);
                double d36 = Math.cos(d32);
                int i38 = 0;
                while (i38 < 4) {
                    double d39 = 0.0;
                    double d41 = (double)((i38 & 2) - 1) * d10;
                    double d43 = (double)((i38 + 1 & 2) - 1) * d10;
                    double d47 = d41 * d36 - d43 * d34;
                    double d49 = d43 * d36 + d41 * d34;
                    double d53 = d47 * d28 + d39 * d30;
                    double d55 = d39 * d28 - d47 * d30;
                    double d57 = d55 * d22 - d49 * d24;
                    double d61 = d49 * d22 + d55 * d24;
                    tessellator2.addVertex(d14 + d57, d16 + d53, d18 + d61);
                    ++i38;
                }
            }
            ++i3;
        }
        tessellator2.draw();
    }

    public void func_946_a(World world1) {
        if (this.worldObj != null) {
            this.worldObj.removeWorldAccess(this);
        }
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        RenderManager.instance.func_852_a(world1);
        this.worldObj = world1;
        this.field_1438_u = new RenderBlocks(world1);
        if (world1 != null) {
            world1.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    public void loadRenderers() {
        EntityLiving entityLiving7;
        int i1;
        Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
        this.renderDistance = this.mc.gameSettings.renderDistance;
        if (this.worldRenderers != null) {
            i1 = 0;
            while (i1 < this.worldRenderers.length) {
                this.worldRenderers[i1].func_1204_c();
                ++i1;
            }
        }
        if ((i1 = 64 << 3 - this.renderDistance) > 400) {
            i1 = 400;
        }
        this.renderChunksWide = i1 / 16 + 1;
        this.renderChunksTall = 8;
        this.renderChunksDeep = i1 / 16 + 1;
        this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
        this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
        int i2 = 0;
        int i3 = 0;
        this.field_1431_B = 0;
        this.field_1430_C = 0;
        this.field_1429_D = 0;
        this.field_1428_E = this.renderChunksWide;
        this.field_1427_F = this.renderChunksTall;
        this.field_1426_G = this.renderChunksDeep;
        int i4 = 0;
        while (i4 < this.worldRenderersToUpdate.size()) {
            ((WorldRenderer)this.worldRenderersToUpdate.get((int)i4)).needsUpdate = false;
            ++i4;
        }
        this.worldRenderersToUpdate.clear();
        this.tileEntities.clear();
        i4 = 0;
        while (i4 < this.renderChunksWide) {
            int i5 = 0;
            while (i5 < this.renderChunksTall) {
                int i6 = 0;
                while (i6 < this.renderChunksDeep) {
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4] = new WorldRenderer(this.worldObj, this.tileEntities, i4 * 16, i5 * 16, i6 * 16, 16, this.field_1440_s + i2);
                    if (this.occlusionEnabled) {
                        this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].field_1732_z = this.field_1437_v.get(i3);
                    }
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isWaitingOnOcclusionQuery = false;
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isVisible = true;
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isInFrustum = true;
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].field_1735_w = i3++;
                    this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].markDirty();
                    this.sortedWorldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4] = this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4];
                    this.worldRenderersToUpdate.add(this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4]);
                    i2 += 3;
                    ++i6;
                }
                ++i5;
            }
            ++i4;
        }
        if (this.worldObj != null && (entityLiving7 = this.mc.renderViewEntity) != null) {
            this.markRenderersForNewPosition(MathHelper.floor_double(entityLiving7.posX), MathHelper.floor_double(entityLiving7.posY), MathHelper.floor_double(entityLiving7.posZ));
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entityLiving7));
        }
        this.field_1424_I = 2;
    }

    public void renderEntities(Vec3D vec3D1, ICamera iCamera2, float f3) {
        if (this.field_1424_I > 0) {
            --this.field_1424_I;
        } else {
            TileEntityRenderer.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, f3);
            RenderManager.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.gameSettings, f3);
            this.field_1423_J = 0;
            this.field_1422_K = 0;
            this.field_1421_L = 0;
            EntityLiving entityLiving4 = this.mc.renderViewEntity;
            RenderManager.renderPosX = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f3;
            RenderManager.renderPosY = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f3;
            RenderManager.renderPosZ = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f3;
            TileEntityRenderer.staticPlayerX = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f3;
            TileEntityRenderer.staticPlayerY = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f3;
            TileEntityRenderer.staticPlayerZ = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f3;
            List list5 = this.worldObj.getLoadedEntityList();
            this.field_1423_J = list5.size();
            int i6 = 0;
            while (i6 < list5.size()) {
                Entity entity7 = (Entity)list5.get(i6);
                if (entity7.isInRangeToRenderVec3D(vec3D1) && iCamera2.isBoundingBoxInFrustum(entity7.boundingBox) && (entity7 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView || this.mc.renderViewEntity.isPlayerSleeping()) && this.worldObj.blockExists(MathHelper.floor_double(entity7.posX), MathHelper.floor_double(entity7.posY), MathHelper.floor_double(entity7.posZ))) {
                    ++this.field_1422_K;
                    RenderManager.instance.renderEntity(entity7, f3);
                }
                ++i6;
            }
            i6 = 0;
            while (i6 < this.tileEntities.size()) {
                TileEntityRenderer.instance.renderTileEntity((TileEntity)this.tileEntities.get(i6), f3);
                ++i6;
            }
        }
    }

    public String func_953_b() {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }

    public String func_957_c() {
        return "E: " + this.field_1422_K + "/" + this.field_1423_J + ". B: " + this.field_1421_L + ", I: " + (this.field_1423_J - this.field_1421_L - this.field_1422_K);
    }

    private void markRenderersForNewPosition(int i1, int i2, int i3) {
        i1 -= 8;
        i2 -= 8;
        i3 -= 8;
        this.field_1431_B = Integer.MAX_VALUE;
        this.field_1430_C = Integer.MAX_VALUE;
        this.field_1429_D = Integer.MAX_VALUE;
        this.field_1428_E = Integer.MIN_VALUE;
        this.field_1427_F = Integer.MIN_VALUE;
        this.field_1426_G = Integer.MIN_VALUE;
        int i4 = this.renderChunksWide * 16;
        int i5 = i4 / 2;
        int i6 = 0;
        while (i6 < this.renderChunksWide) {
            int i7 = i6 * 16;
            int i8 = i7 + i5 - i1;
            if (i8 < 0) {
                i8 -= i4 - 1;
            }
            if ((i7 -= (i8 /= i4) * i4) < this.field_1431_B) {
                this.field_1431_B = i7;
            }
            if (i7 > this.field_1428_E) {
                this.field_1428_E = i7;
            }
            int i9 = 0;
            while (i9 < this.renderChunksDeep) {
                int i10 = i9 * 16;
                int i11 = i10 + i5 - i3;
                if (i11 < 0) {
                    i11 -= i4 - 1;
                }
                if ((i10 -= (i11 /= i4) * i4) < this.field_1429_D) {
                    this.field_1429_D = i10;
                }
                if (i10 > this.field_1426_G) {
                    this.field_1426_G = i10;
                }
                int i12 = 0;
                while (i12 < this.renderChunksTall) {
                    int i13 = i12 * 16;
                    if (i13 < this.field_1430_C) {
                        this.field_1430_C = i13;
                    }
                    if (i13 > this.field_1427_F) {
                        this.field_1427_F = i13;
                    }
                    WorldRenderer worldRenderer14 = this.worldRenderers[(i9 * this.renderChunksTall + i12) * this.renderChunksWide + i6];
                    boolean z15 = worldRenderer14.needsUpdate;
                    worldRenderer14.setPosition(i7, i13, i10);
                    if (!z15 && worldRenderer14.needsUpdate) {
                        this.worldRenderersToUpdate.add(worldRenderer14);
                    }
                    ++i12;
                }
                ++i9;
            }
            ++i6;
        }
    }

    public int sortAndRender(EntityLiving entityLiving1, int i2, double d3) {
        int i34;
        int i5 = 0;
        while (i5 < 10) {
            this.field_21156_R = (this.field_21156_R + 1) % this.worldRenderers.length;
            WorldRenderer worldRenderer6 = this.worldRenderers[this.field_21156_R];
            if (worldRenderer6.needsUpdate && !this.worldRenderersToUpdate.contains(worldRenderer6)) {
                this.worldRenderersToUpdate.add(worldRenderer6);
            }
            ++i5;
        }
        if (this.mc.gameSettings.renderDistance != this.renderDistance) {
            this.loadRenderers();
        }
        if (i2 == 0) {
            this.renderersLoaded = 0;
            this.renderersBeingClipped = 0;
            this.renderersBeingOccluded = 0;
            this.renderersBeingRendered = 0;
            this.renderersSkippingRenderPass = 0;
        }
        double d33 = entityLiving1.lastTickPosX + (entityLiving1.posX - entityLiving1.lastTickPosX) * d3;
        double d7 = entityLiving1.lastTickPosY + (entityLiving1.posY - entityLiving1.lastTickPosY) * d3;
        double d9 = entityLiving1.lastTickPosZ + (entityLiving1.posZ - entityLiving1.lastTickPosZ) * d3;
        double d11 = entityLiving1.posX - this.prevSortX;
        double d13 = entityLiving1.posY - this.prevSortY;
        double d15 = entityLiving1.posZ - this.prevSortZ;
        if (d11 * d11 + d13 * d13 + d15 * d15 > 16.0) {
            this.prevSortX = entityLiving1.posX;
            this.prevSortY = entityLiving1.posY;
            this.prevSortZ = entityLiving1.posZ;
            this.markRenderersForNewPosition(MathHelper.floor_double(entityLiving1.posX), MathHelper.floor_double(entityLiving1.posY), MathHelper.floor_double(entityLiving1.posZ));
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entityLiving1));
        }
        int b17 = 0;
        if (this.occlusionEnabled && !this.mc.gameSettings.anaglyph && i2 == 0) {
            int b18 = 0;
            int i19 = 16;
            this.func_962_a(b18, i19);
            int i20 = b18;
            while (i20 < i19) {
                this.sortedWorldRenderers[i20].isVisible = true;
                ++i20;
            }
            i34 = b17 + this.renderSortedRenderers(b18, i19, i2, d3);
            do {
                int i35 = i19;
                if ((i19 *= 2) > this.sortedWorldRenderers.length) {
                    i19 = this.sortedWorldRenderers.length;
                }
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)3008);
                GL11.glDisable((int)2912);
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                GL11.glDepthMask((boolean)false);
                this.func_962_a(i35, i19);
                GL11.glPushMatrix();
                float f36 = 0.0f;
                float f21 = 0.0f;
                float f22 = 0.0f;
                int i23 = i35;
                while (i23 < i19) {
                    if (this.sortedWorldRenderers[i23].canRender()) {
                        this.sortedWorldRenderers[i23].isInFrustum = false;
                    } else {
                        float f24;
                        int i25;
                        if (!this.sortedWorldRenderers[i23].isInFrustum) {
                            this.sortedWorldRenderers[i23].isVisible = true;
                        }
                        if (this.sortedWorldRenderers[i23].isInFrustum && !this.sortedWorldRenderers[i23].isWaitingOnOcclusionQuery && this.cloudOffsetX % (i25 = (int)(1.0f + (f24 = MathHelper.sqrt_float(this.sortedWorldRenderers[i23].distanceToEntitySquared(entityLiving1))) / 128.0f)) == i23 % i25) {
                            WorldRenderer worldRenderer26 = this.sortedWorldRenderers[i23];
                            float f27 = (float)((double)worldRenderer26.field_1755_i - d33);
                            float f28 = (float)((double)worldRenderer26.field_1754_j - d7);
                            float f29 = (float)((double)worldRenderer26.field_1753_k - d9);
                            float f30 = f27 - f36;
                            float f31 = f28 - f21;
                            float f32 = f29 - f22;
                            if (f30 != 0.0f || f31 != 0.0f || f32 != 0.0f) {
                                GL11.glTranslatef((float)f30, (float)f31, (float)f32);
                                f36 += f30;
                                f21 += f31;
                                f22 += f32;
                            }
                            ARBOcclusionQuery.glBeginQueryARB((int)35092, (int)this.sortedWorldRenderers[i23].field_1732_z);
                            this.sortedWorldRenderers[i23].callOcclusionQueryList();
                            ARBOcclusionQuery.glEndQueryARB((int)35092);
                            this.sortedWorldRenderers[i23].isWaitingOnOcclusionQuery = true;
                        }
                    }
                    ++i23;
                }
                GL11.glPopMatrix();
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                GL11.glDepthMask((boolean)true);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)2912);
                i34 += this.renderSortedRenderers(i35, i19, i2, d3);
            } while (i19 < this.sortedWorldRenderers.length);
        } else {
            i34 = b17 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, i2, d3);
        }
        return i34;
    }

    private void func_962_a(int i1, int i2) {
        int i3 = i1;
        while (i3 < i2) {
            if (this.sortedWorldRenderers[i3].isWaitingOnOcclusionQuery) {
                this.field_1456_c.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB((int)this.sortedWorldRenderers[i3].field_1732_z, (int)34919, (IntBuffer)this.field_1456_c);
                if (this.field_1456_c.get(0) != 0) {
                    this.sortedWorldRenderers[i3].isWaitingOnOcclusionQuery = false;
                    this.field_1456_c.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB((int)this.sortedWorldRenderers[i3].field_1732_z, (int)34918, (IntBuffer)this.field_1456_c);
                    this.sortedWorldRenderers[i3].isVisible = this.field_1456_c.get(0) != 0;
                }
            }
            ++i3;
        }
    }

    private int renderSortedRenderers(int i1, int i2, int i3, double d4) {
        this.field_1415_R.clear();
        int i6 = 0;
        int i7 = i1;
        while (i7 < i2) {
            int i8;
            if (i3 == 0) {
                ++this.renderersLoaded;
                if (this.sortedWorldRenderers[i7].skipRenderPass[i3]) {
                    ++this.renderersSkippingRenderPass;
                } else if (!this.sortedWorldRenderers[i7].isInFrustum) {
                    ++this.renderersBeingClipped;
                } else if (this.occlusionEnabled && !this.sortedWorldRenderers[i7].isVisible) {
                    ++this.renderersBeingOccluded;
                } else {
                    ++this.renderersBeingRendered;
                }
            }
            if (!this.sortedWorldRenderers[i7].skipRenderPass[i3] && this.sortedWorldRenderers[i7].isInFrustum && this.sortedWorldRenderers[i7].isVisible && (i8 = this.sortedWorldRenderers[i7].getGLCallListForPass(i3)) >= 0) {
                this.field_1415_R.add(this.sortedWorldRenderers[i7]);
                ++i6;
            }
            ++i7;
        }
        EntityLiving entityLiving19 = this.mc.renderViewEntity;
        double d20 = entityLiving19.lastTickPosX + (entityLiving19.posX - entityLiving19.lastTickPosX) * d4;
        double d10 = entityLiving19.lastTickPosY + (entityLiving19.posY - entityLiving19.lastTickPosY) * d4;
        double d12 = entityLiving19.lastTickPosZ + (entityLiving19.posZ - entityLiving19.lastTickPosZ) * d4;
        int i14 = 0;
        int i15 = 0;
        while (i15 < this.field_1414_S.length) {
            this.field_1414_S[i15].func_859_b();
            ++i15;
        }
        i15 = 0;
        while (i15 < this.field_1415_R.size()) {
            WorldRenderer worldRenderer16 = (WorldRenderer)this.field_1415_R.get(i15);
            int i17 = -1;
            int i18 = 0;
            while (i18 < i14) {
                if (this.field_1414_S[i18].func_862_a(worldRenderer16.field_1755_i, worldRenderer16.field_1754_j, worldRenderer16.field_1753_k)) {
                    i17 = i18;
                }
                ++i18;
            }
            if (i17 < 0) {
                i17 = i14++;
                this.field_1414_S[i17].func_861_a(worldRenderer16.field_1755_i, worldRenderer16.field_1754_j, worldRenderer16.field_1753_k, d20, d10, d12);
            }
            this.field_1414_S[i17].func_858_a(worldRenderer16.getGLCallListForPass(i3));
            ++i15;
        }
        this.func_944_a(i3, d4);
        return i6;
    }

    public void func_944_a(int i1, double d2) {
        int i4 = 0;
        while (i4 < this.field_1414_S.length) {
            this.field_1414_S[i4].func_860_a();
            ++i4;
        }
    }

    public void updateClouds() {
        ++this.cloudOffsetX;
    }

    public void renderSky(float f1) {
        if (!this.mc.theWorld.worldProvider.field_4220_c) {
            float f11;
            float f8;
            float f7;
            GL11.glDisable((int)3553);
            Vec3D vec3D2 = this.worldObj.func_4079_a(this.mc.renderViewEntity, f1);
            float f3 = (float)vec3D2.xCoord;
            float f4 = (float)vec3D2.yCoord;
            float f5 = (float)vec3D2.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                float f6 = (f3 * 30.0f + f4 * 59.0f + f5 * 11.0f) / 100.0f;
                f7 = (f3 * 30.0f + f4 * 70.0f) / 100.0f;
                f8 = (f3 * 30.0f + f5 * 70.0f) / 100.0f;
                f3 = f6;
                f4 = f7;
                f5 = f8;
            }
            GL11.glColor3f((float)f3, (float)f4, (float)f5);
            Tessellator tessellator14 = Tessellator.instance;
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)2912);
            GL11.glColor3f((float)f3, (float)f4, (float)f5);
            GL11.glCallList((int)this.field_1433_z);
            GL11.glDisable((int)2912);
            GL11.glDisable((int)3008);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            float[] f15 = this.worldObj.worldProvider.calcSunriseSunsetColors(this.worldObj.getCelestialAngle(f1), f1);
            if (f15 != null) {
                GL11.glDisable((int)3553);
                GL11.glShadeModel((int)7425);
                GL11.glPushMatrix();
                GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                f8 = this.worldObj.getCelestialAngle(f1);
                GL11.glRotatef((float)(f8 > 0.5f ? 180.0f : 0.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                tessellator14.startDrawing(6);
                tessellator14.setColorRGBA_F(f15[0], f15[1], f15[2], f15[3]);
                tessellator14.addVertex(0.0, 100.0, 0.0);
                int b9 = 16;
                tessellator14.setColorRGBA_F(f15[0], f15[1], f15[2], 0.0f);
                int i10 = 0;
                while (i10 <= b9) {
                    f11 = (float)i10 * (float)Math.PI * 2.0f / (float)b9;
                    float f12 = MathHelper.sin(f11);
                    float f13 = MathHelper.cos(f11);
                    tessellator14.addVertex(f12 * 120.0f, f13 * 120.0f, -f13 * 40.0f * f15[3]);
                    ++i10;
                }
                tessellator14.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel((int)7424);
            }
            GL11.glEnable((int)3553);
            GL11.glBlendFunc((int)1, (int)1);
            GL11.glPushMatrix();
            f7 = 0.0f;
            f8 = 0.0f;
            float f16 = 0.0f;
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)f7, (float)f8, (float)f16);
            GL11.glRotatef((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(this.worldObj.getCelestialAngle(f1) * 360.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            float f17 = 30.0f;
            GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain/sun.png"));
            tessellator14.startDrawingQuads();
            tessellator14.addVertexWithUV(-f17, 100.0, -f17, 0.0, 0.0);
            tessellator14.addVertexWithUV(f17, 100.0, -f17, 1.0, 0.0);
            tessellator14.addVertexWithUV(f17, 100.0, f17, 1.0, 1.0);
            tessellator14.addVertexWithUV(-f17, 100.0, f17, 0.0, 1.0);
            tessellator14.draw();
            f17 = 20.0f;
            GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain/moon.png"));
            tessellator14.startDrawingQuads();
            tessellator14.addVertexWithUV(-f17, -100.0, f17, 1.0, 1.0);
            tessellator14.addVertexWithUV(f17, -100.0, f17, 0.0, 1.0);
            tessellator14.addVertexWithUV(f17, -100.0, -f17, 0.0, 0.0);
            tessellator14.addVertexWithUV(-f17, -100.0, -f17, 1.0, 0.0);
            tessellator14.draw();
            GL11.glDisable((int)3553);
            f11 = this.worldObj.getStarBrightness(f1);
            if (f11 > 0.0f) {
                GL11.glColor4f((float)f11, (float)f11, (float)f11, (float)f11);
                GL11.glCallList((int)this.starGLCallList);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2912);
            GL11.glPopMatrix();
            GL11.glColor3f((float)(f3 * 0.2f + 0.04f), (float)(f4 * 0.2f + 0.04f), (float)(f5 * 0.6f + 0.1f));
            GL11.glDisable((int)3553);
            GL11.glCallList((int)this.field_1432_A);
            GL11.glEnable((int)3553);
            GL11.glDepthMask((boolean)true);
        }
    }

    public void renderClouds(float f1) {
        if (!this.mc.theWorld.worldProvider.field_4220_c) {
            if (this.mc.gameSettings.fancyGraphics) {
                this.renderCloudsFancy(f1);
            } else {
                float f10;
                GL11.glDisable((int)2884);
                float f2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)f1);
                int b3 = 32;
                int i4 = 256 / b3;
                Tessellator tessellator5 = Tessellator.instance;
                GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/environment/clouds.png"));
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                Vec3D vec3D6 = this.worldObj.func_628_d(f1);
                float f7 = (float)vec3D6.xCoord;
                float f8 = (float)vec3D6.yCoord;
                float f9 = (float)vec3D6.zCoord;
                if (this.mc.gameSettings.anaglyph) {
                    f10 = (f7 * 30.0f + f8 * 59.0f + f9 * 11.0f) / 100.0f;
                    float f11 = (f7 * 30.0f + f8 * 70.0f) / 100.0f;
                    float f12 = (f7 * 30.0f + f9 * 70.0f) / 100.0f;
                    f7 = f10;
                    f8 = f11;
                    f9 = f12;
                }
                f10 = 4.8828125E-4f;
                double d22 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)f1 + (double)(((float)this.cloudOffsetX + f1) * 0.03f);
                double d13 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)f1;
                int i15 = MathHelper.floor_double(d22 / 2048.0);
                int i16 = MathHelper.floor_double(d13 / 2048.0);
                float f17 = 120.0f - f2 + 0.33f;
                float f18 = (float)((d22 -= (double)(i15 * 2048)) * (double)f10);
                float f19 = (float)((d13 -= (double)(i16 * 2048)) * (double)f10);
                tessellator5.startDrawingQuads();
                tessellator5.setColorRGBA_F(f7, f8, f9, 0.8f);
                int i20 = -b3 * i4;
                while (i20 < b3 * i4) {
                    int i21 = -b3 * i4;
                    while (i21 < b3 * i4) {
                        tessellator5.addVertexWithUV(i20 + 0, f17, i21 + b3, (float)(i20 + 0) * f10 + f18, (float)(i21 + b3) * f10 + f19);
                        tessellator5.addVertexWithUV(i20 + b3, f17, i21 + b3, (float)(i20 + b3) * f10 + f18, (float)(i21 + b3) * f10 + f19);
                        tessellator5.addVertexWithUV(i20 + b3, f17, i21 + 0, (float)(i20 + b3) * f10 + f18, (float)(i21 + 0) * f10 + f19);
                        tessellator5.addVertexWithUV(i20 + 0, f17, i21 + 0, (float)(i20 + 0) * f10 + f18, (float)(i21 + 0) * f10 + f19);
                        i21 += b3;
                    }
                    i20 += b3;
                }
                tessellator5.draw();
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)2884);
            }
        }
    }

    public void renderCloudsFancy(float f1) {
        float f19;
        float f18;
        float f17;
        GL11.glDisable((int)2884);
        float f2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)f1);
        Tessellator tessellator3 = Tessellator.instance;
        float f4 = 12.0f;
        float f5 = 4.0f;
        double d6 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)f1 + (double)(((float)this.cloudOffsetX + f1) * 0.03f)) / (double)f4;
        double d8 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)f1) / (double)f4 + (double)0.33f;
        float f10 = 108.0f - f2 + 0.33f;
        int i11 = MathHelper.floor_double(d6 / 2048.0);
        int i12 = MathHelper.floor_double(d8 / 2048.0);
        d6 -= (double)(i11 * 2048);
        d8 -= (double)(i12 * 2048);
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/environment/clouds.png"));
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        Vec3D vec3D13 = this.worldObj.func_628_d(f1);
        float f14 = (float)vec3D13.xCoord;
        float f15 = (float)vec3D13.yCoord;
        float f16 = (float)vec3D13.zCoord;
        if (this.mc.gameSettings.anaglyph) {
            f17 = (f14 * 30.0f + f15 * 59.0f + f16 * 11.0f) / 100.0f;
            f18 = (f14 * 30.0f + f15 * 70.0f) / 100.0f;
            f19 = (f14 * 30.0f + f16 * 70.0f) / 100.0f;
            f14 = f17;
            f15 = f18;
            f16 = f19;
        }
        f17 = (float)(d6 * 0.0);
        f18 = (float)(d8 * 0.0);
        f19 = 0.00390625f;
        f17 = (float)MathHelper.floor_double(d6) * f19;
        f18 = (float)MathHelper.floor_double(d8) * f19;
        float f20 = (float)(d6 - (double)MathHelper.floor_double(d6));
        float f21 = (float)(d8 - (double)MathHelper.floor_double(d8));
        int b22 = 8;
        int b23 = 3;
        float f24 = 9.765625E-4f;
        GL11.glScalef((float)f4, (float)1.0f, (float)f4);
        int i25 = 0;
        while (i25 < 2) {
            if (i25 == 0) {
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
            } else {
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            }
            int i26 = -b23 + 1;
            while (i26 <= b23) {
                int i27 = -b23 + 1;
                while (i27 <= b23) {
                    int i32;
                    tessellator3.startDrawingQuads();
                    float f28 = i26 * b22;
                    float f29 = i27 * b22;
                    float f30 = f28 - f20;
                    float f31 = f29 - f21;
                    if (f10 > -f5 - 1.0f) {
                        tessellator3.setColorRGBA_F(f14 * 0.7f, f15 * 0.7f, f16 * 0.7f, 0.8f);
                        tessellator3.setNormal(0.0f, -1.0f, 0.0f);
                        tessellator3.addVertexWithUV(f30 + 0.0f, f10 + 0.0f, f31 + (float)b22, (f28 + 0.0f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + (float)b22, f10 + 0.0f, f31 + (float)b22, (f28 + (float)b22) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + (float)b22, f10 + 0.0f, f31 + 0.0f, (f28 + (float)b22) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + 0.0f, f10 + 0.0f, f31 + 0.0f, (f28 + 0.0f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                    }
                    if (f10 <= f5 + 1.0f) {
                        tessellator3.setColorRGBA_F(f14, f15, f16, 0.8f);
                        tessellator3.setNormal(0.0f, 1.0f, 0.0f);
                        tessellator3.addVertexWithUV(f30 + 0.0f, f10 + f5 - f24, f31 + (float)b22, (f28 + 0.0f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + (float)b22, f10 + f5 - f24, f31 + (float)b22, (f28 + (float)b22) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + (float)b22, f10 + f5 - f24, f31 + 0.0f, (f28 + (float)b22) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                        tessellator3.addVertexWithUV(f30 + 0.0f, f10 + f5 - f24, f31 + 0.0f, (f28 + 0.0f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                    }
                    tessellator3.setColorRGBA_F(f14 * 0.9f, f15 * 0.9f, f16 * 0.9f, 0.8f);
                    if (i26 > -1) {
                        tessellator3.setNormal(-1.0f, 0.0f, 0.0f);
                        i32 = 0;
                        while (i32 < b22) {
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 0.0f, f10 + 0.0f, f31 + (float)b22, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 0.0f, f10 + f5, f31 + (float)b22, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 0.0f, f10 + f5, f31 + 0.0f, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 0.0f, f10 + 0.0f, f31 + 0.0f, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                            ++i32;
                        }
                    }
                    if (i26 <= 1) {
                        tessellator3.setNormal(1.0f, 0.0f, 0.0f);
                        i32 = 0;
                        while (i32 < b22) {
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 1.0f - f24, f10 + 0.0f, f31 + (float)b22, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 1.0f - f24, f10 + f5, f31 + (float)b22, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + (float)b22) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 1.0f - f24, f10 + f5, f31 + 0.0f, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)i32 + 1.0f - f24, f10 + 0.0f, f31 + 0.0f, (f28 + (float)i32 + 0.5f) * f19 + f17, (f29 + 0.0f) * f19 + f18);
                            ++i32;
                        }
                    }
                    tessellator3.setColorRGBA_F(f14 * 0.8f, f15 * 0.8f, f16 * 0.8f, 0.8f);
                    if (i27 > -1) {
                        tessellator3.setNormal(0.0f, 0.0f, -1.0f);
                        i32 = 0;
                        while (i32 < b22) {
                            tessellator3.addVertexWithUV(f30 + 0.0f, f10 + f5, f31 + (float)i32 + 0.0f, (f28 + 0.0f) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)b22, f10 + f5, f31 + (float)i32 + 0.0f, (f28 + (float)b22) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)b22, f10 + 0.0f, f31 + (float)i32 + 0.0f, (f28 + (float)b22) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + 0.0f, f10 + 0.0f, f31 + (float)i32 + 0.0f, (f28 + 0.0f) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            ++i32;
                        }
                    }
                    if (i27 <= 1) {
                        tessellator3.setNormal(0.0f, 0.0f, 1.0f);
                        i32 = 0;
                        while (i32 < b22) {
                            tessellator3.addVertexWithUV(f30 + 0.0f, f10 + f5, f31 + (float)i32 + 1.0f - f24, (f28 + 0.0f) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)b22, f10 + f5, f31 + (float)i32 + 1.0f - f24, (f28 + (float)b22) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + (float)b22, f10 + 0.0f, f31 + (float)i32 + 1.0f - f24, (f28 + (float)b22) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            tessellator3.addVertexWithUV(f30 + 0.0f, f10 + 0.0f, f31 + (float)i32 + 1.0f - f24, (f28 + 0.0f) * f19 + f17, (f29 + (float)i32 + 0.5f) * f19 + f18);
                            ++i32;
                        }
                    }
                    tessellator3.draw();
                    ++i27;
                }
                ++i26;
            }
            ++i25;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
    }

    public boolean updateRenderers(EntityLiving entityLiving1, boolean z2) {
        int i12;
        int i11;
        WorldRenderer worldRenderer10;
        block32: {
            boolean z3 = false;
            if (!z3) break block32;
            Collections.sort(this.worldRenderersToUpdate, new RenderSorter(entityLiving1));
            int i14 = this.worldRenderersToUpdate.size() - 1;
            int i15 = this.worldRenderersToUpdate.size();
            int i16 = 0;
            while (i16 < i15) {
                block35: {
                    WorldRenderer worldRenderer17;
                    block34: {
                        block33: {
                            worldRenderer17 = (WorldRenderer)this.worldRenderersToUpdate.get(i14 - i16);
                            if (z2) break block33;
                            if (worldRenderer17.distanceToEntitySquared(entityLiving1) > 1024.0f && (worldRenderer17.isInFrustum ? i16 >= 3 : i16 >= 1)) {
                                return false;
                            }
                            break block34;
                        }
                        if (!worldRenderer17.isInFrustum) break block35;
                    }
                    worldRenderer17.updateRenderer();
                    this.worldRenderersToUpdate.remove(worldRenderer17);
                    worldRenderer17.needsUpdate = false;
                }
                ++i16;
            }
            return this.worldRenderersToUpdate.size() == 0;
        }
        RenderSorter renderSorter4 = new RenderSorter(entityLiving1);
        WorldRenderer[] worldRenderer5 = new WorldRenderer[3];
        ArrayList<WorldRenderer> arrayList6 = null;
        int i7 = this.worldRenderersToUpdate.size();
        int i8 = 0;
        int i9 = 0;
        while (i9 < i7) {
            block31: {
                block37: {
                    block36: {
                        worldRenderer10 = (WorldRenderer)this.worldRenderersToUpdate.get(i9);
                        if (z2) break block36;
                        if (!(worldRenderer10.distanceToEntitySquared(entityLiving1) > 1024.0f)) break block37;
                        i11 = 0;
                        while (i11 < 3 && (worldRenderer5[i11] == null || renderSorter4.func_993_a(worldRenderer5[i11], worldRenderer10) <= 0)) {
                            ++i11;
                        }
                        if (--i11 > 0) {
                            i12 = i11;
                            while (true) {
                                if (--i12 == 0) {
                                    worldRenderer5[i11] = worldRenderer10;
                                    break block31;
                                }
                                worldRenderer5[i12 - 1] = worldRenderer5[i12];
                            }
                        }
                        break block31;
                    }
                    if (!worldRenderer10.isInFrustum) break block31;
                }
                if (arrayList6 == null) {
                    arrayList6 = new ArrayList<WorldRenderer>();
                }
                ++i8;
                arrayList6.add(worldRenderer10);
                this.worldRenderersToUpdate.set(i9, null);
            }
            ++i9;
        }
        if (arrayList6 != null) {
            if (arrayList6.size() > 1) {
                Collections.sort(arrayList6, renderSorter4);
            }
            i9 = arrayList6.size() - 1;
            while (i9 >= 0) {
                worldRenderer10 = (WorldRenderer)arrayList6.get(i9);
                worldRenderer10.updateRenderer();
                worldRenderer10.needsUpdate = false;
                --i9;
            }
        }
        i9 = 0;
        int i18 = 2;
        while (i18 >= 0) {
            WorldRenderer worldRenderer19 = worldRenderer5[i18];
            if (worldRenderer19 != null) {
                if (!worldRenderer19.isInFrustum && i18 != 2) {
                    worldRenderer5[i18] = null;
                    worldRenderer5[0] = null;
                    break;
                }
                worldRenderer5[i18].updateRenderer();
                worldRenderer5[i18].needsUpdate = false;
                ++i9;
            }
            --i18;
        }
        i18 = 0;
        i11 = 0;
        i12 = this.worldRenderersToUpdate.size();
        while (i18 != i12) {
            WorldRenderer worldRenderer13 = (WorldRenderer)this.worldRenderersToUpdate.get(i18);
            if (worldRenderer13 != null && worldRenderer13 != worldRenderer5[0] && worldRenderer13 != worldRenderer5[1] && worldRenderer13 != worldRenderer5[2]) {
                if (i11 != i18) {
                    this.worldRenderersToUpdate.set(i11, worldRenderer13);
                }
                ++i11;
            }
            ++i18;
        }
        while (--i18 >= i11) {
            this.worldRenderersToUpdate.remove(i18);
        }
        return i7 == i8 + i9;
    }

    public void func_959_a(EntityPlayer entityPlayer1, MovingObjectPosition movingObjectPosition2, int i3, ItemStack itemStack4, float f5) {
        Tessellator tessellator6 = Tessellator.instance;
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glBlendFunc((int)770, (int)1);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)((MathHelper.sin((float)System.currentTimeMillis() / 100.0f) * 0.2f + 0.4f) * 0.5f));
        if (i3 == 0) {
            if (this.field_1450_i > 0.0f) {
                GL11.glBlendFunc((int)774, (int)768);
                int i7 = this.renderEngine.getTexture("/terrain.png");
                GL11.glBindTexture((int)3553, (int)i7);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.5f);
                GL11.glPushMatrix();
                int i8 = this.worldObj.getBlockId(movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
                Block block9 = i8 > 0 ? Block.blocksList[i8] : null;
                GL11.glDisable((int)3008);
                GL11.glPolygonOffset((float)-3.0f, (float)-3.0f);
                GL11.glEnable((int)32823);
                tessellator6.startDrawingQuads();
                double d10 = entityPlayer1.lastTickPosX + (entityPlayer1.posX - entityPlayer1.lastTickPosX) * (double)f5;
                double d12 = entityPlayer1.lastTickPosY + (entityPlayer1.posY - entityPlayer1.lastTickPosY) * (double)f5;
                double d14 = entityPlayer1.lastTickPosZ + (entityPlayer1.posZ - entityPlayer1.lastTickPosZ) * (double)f5;
                tessellator6.setTranslationD(-d10, -d12, -d14);
                tessellator6.disableColor();
                if (block9 == null) {
                    block9 = Block.stone;
                }
                this.field_1438_u.renderBlockUsingTexture(block9, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ, 240 + (int)(this.field_1450_i * 10.0f));
                tessellator6.draw();
                tessellator6.setTranslationD(0.0, 0.0, 0.0);
                GL11.glPolygonOffset((float)0.0f, (float)0.0f);
                GL11.glDisable((int)32823);
                GL11.glEnable((int)3008);
                GL11.glDepthMask((boolean)true);
                GL11.glPopMatrix();
            }
        } else if (itemStack4 != null) {
            GL11.glBlendFunc((int)770, (int)771);
            float f16 = MathHelper.sin((float)System.currentTimeMillis() / 100.0f) * 0.2f + 0.8f;
            GL11.glColor4f((float)f16, (float)f16, (float)f16, (float)(MathHelper.sin((float)System.currentTimeMillis() / 200.0f) * 0.2f + 0.5f));
            int i8 = this.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)i8);
            int i17 = movingObjectPosition2.blockX;
            int i18 = movingObjectPosition2.blockY;
            int i11 = movingObjectPosition2.blockZ;
            if (movingObjectPosition2.sideHit == 0) {
                --i18;
            }
            if (movingObjectPosition2.sideHit == 1) {
                ++i18;
            }
            if (movingObjectPosition2.sideHit == 2) {
                --i11;
            }
            if (movingObjectPosition2.sideHit == 3) {
                ++i11;
            }
            if (movingObjectPosition2.sideHit == 4) {
                --i17;
            }
            if (movingObjectPosition2.sideHit == 5) {
                ++i17;
            }
        }
        GL11.glDisable((int)3042);
        GL11.glDisable((int)3008);
    }

    public void drawSelectionBox(EntityPlayer entityPlayer1, MovingObjectPosition movingObjectPosition2, int i3, ItemStack itemStack4, float f5) {
        if (i3 == 0 && movingObjectPosition2.typeOfHit == EnumMovingObjectType.TILE) {
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.4f);
            GL11.glLineWidth((float)2.0f);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            float f6 = 0.002f;
            int i7 = this.worldObj.getBlockId(movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
            if (i7 > 0) {
                Block.blocksList[i7].setBlockBoundsBasedOnState(this.worldObj, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
                double d8 = entityPlayer1.lastTickPosX + (entityPlayer1.posX - entityPlayer1.lastTickPosX) * (double)f5;
                double d10 = entityPlayer1.lastTickPosY + (entityPlayer1.posY - entityPlayer1.lastTickPosY) * (double)f5;
                double d12 = entityPlayer1.lastTickPosZ + (entityPlayer1.posZ - entityPlayer1.lastTickPosZ) * (double)f5;
                this.drawOutlinedBoundingBox(Block.blocksList[i7].getSelectedBoundingBoxFromPool(this.worldObj, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ).expand(f6, f6, f6).getOffsetBoundingBox(-d8, -d10, -d12));
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
        }
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB1) {
        Tessellator tessellator2 = Tessellator.instance;
        tessellator2.startDrawing(3);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
        tessellator2.draw();
        tessellator2.startDrawing(3);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
        tessellator2.draw();
        tessellator2.startDrawing(1);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
        tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
        tessellator2.draw();
    }

    public void func_949_a(int i1, int i2, int i3, int i4, int i5, int i6) {
        int i7 = MathHelper.bucketInt(i1, 16);
        int i8 = MathHelper.bucketInt(i2, 16);
        int i9 = MathHelper.bucketInt(i3, 16);
        int i10 = MathHelper.bucketInt(i4, 16);
        int i11 = MathHelper.bucketInt(i5, 16);
        int i12 = MathHelper.bucketInt(i6, 16);
        int i13 = i7;
        while (i13 <= i10) {
            int i14 = i13 % this.renderChunksWide;
            if (i14 < 0) {
                i14 += this.renderChunksWide;
            }
            int i15 = i8;
            while (i15 <= i11) {
                int i16 = i15 % this.renderChunksTall;
                if (i16 < 0) {
                    i16 += this.renderChunksTall;
                }
                int i17 = i9;
                while (i17 <= i12) {
                    int i18 = i17 % this.renderChunksDeep;
                    if (i18 < 0) {
                        i18 += this.renderChunksDeep;
                    }
                    int i19 = (i18 * this.renderChunksTall + i16) * this.renderChunksWide + i14;
                    WorldRenderer worldRenderer20 = this.worldRenderers[i19];
                    if (!worldRenderer20.needsUpdate) {
                        this.worldRenderersToUpdate.add(worldRenderer20);
                        worldRenderer20.markDirty();
                    }
                    ++i17;
                }
                ++i15;
            }
            ++i13;
        }
    }

    @Override
    public void markBlockAndNeighborsNeedsUpdate(int i1, int i2, int i3) {
        this.func_949_a(i1 - 1, i2 - 1, i3 - 1, i1 + 1, i2 + 1, i3 + 1);
    }

    @Override
    public void markBlockRangeNeedsUpdate(int i1, int i2, int i3, int i4, int i5, int i6) {
        this.func_949_a(i1 - 1, i2 - 1, i3 - 1, i4 + 1, i5 + 1, i6 + 1);
    }

    public void clipRenderersByFrustrum(ICamera iCamera1, float f2) {
        int i3 = 0;
        while (i3 < this.worldRenderers.length) {
            if (!(this.worldRenderers[i3].canRender() || this.worldRenderers[i3].isInFrustum && (i3 + this.frustrumCheckOffset & 0xF) != 0)) {
                this.worldRenderers[i3].updateInFrustrum(iCamera1);
            }
            ++i3;
        }
        ++this.frustrumCheckOffset;
    }

    @Override
    public void playRecord(String string1, int i2, int i3, int i4) {
        if (string1 != null) {
            this.mc.ingameGUI.setRecordPlayingMessage("C418 - " + string1);
        }
        this.mc.sndManager.playStreaming(string1, i2, i3, i4, 1.0f, 1.0f);
    }

    @Override
    public void playSound(String string1, double d2, double d4, double d6, float f8, float f9) {
        float f10 = 16.0f;
        if (f8 > 1.0f) {
            f10 *= f8;
        }
        if (this.mc.renderViewEntity.getDistanceSq(d2, d4, d6) < (double)(f10 * f10)) {
            this.mc.sndManager.playSound(string1, (float)d2, (float)d4, (float)d6, f8, f9);
        }
    }

    @Override
    public void spawnParticle(String string1, double d2, double d4, double d6, double d8, double d10, double d12) {
        double d20;
        double d18;
        double d16;
        double d14;
        if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null && (d14 = this.mc.renderViewEntity.posX - d2) * d14 + (d16 = this.mc.renderViewEntity.posY - d4) * d16 + (d18 = this.mc.renderViewEntity.posZ - d6) * d18 <= (d20 = 16.0) * d20) {
            if (string1.equals("bubble")) {
                this.mc.effectRenderer.addEffect(new EntityBubbleFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("smoke")) {
                this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("note")) {
                this.mc.effectRenderer.addEffect(new EntityNoteFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("portal")) {
                this.mc.effectRenderer.addEffect(new EntityPortalFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("explode")) {
                this.mc.effectRenderer.addEffect(new EntityExplodeFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("flame")) {
                this.mc.effectRenderer.addEffect(new EntityFlameFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("lava")) {
                this.mc.effectRenderer.addEffect(new EntityLavaFX(this.worldObj, d2, d4, d6));
            } else if (string1.equals("splash")) {
                this.mc.effectRenderer.addEffect(new EntitySplashFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            } else if (string1.equals("largesmoke")) {
                this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, d2, d4, d6, d8, d10, d12, 2.5f));
            } else if (string1.equals("reddust")) {
                this.mc.effectRenderer.addEffect(new EntityReddustFX(this.worldObj, d2, d4, d6, (float)d8, (float)d10, (float)d12));
            } else if (string1.equals("snowballpoof")) {
                this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, d2, d4, d6, Item.snowball));
            } else if (string1.equals("slime")) {
                this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, d2, d4, d6, Item.slimeBall));
            } else if (string1.equals("heart")) {
                this.mc.effectRenderer.addEffect(new EntityHeartFX(this.worldObj, d2, d4, d6, d8, d10, d12));
            }
        }
    }

    @Override
    public void obtainEntitySkin(Entity entity1) {
        entity1.updateCloak();
        if (entity1.skinUrl != null) {
            this.renderEngine.obtainImageData(entity1.skinUrl, new ImageBufferDownload());
        }
        if (entity1.cloakUrl != null) {
            this.renderEngine.obtainImageData(entity1.cloakUrl, new ImageBufferDownload());
        }
    }

    @Override
    public void releaseEntitySkin(Entity entity1) {
        if (entity1.skinUrl != null) {
            this.renderEngine.releaseImageData(entity1.skinUrl);
        }
        if (entity1.cloakUrl != null) {
            this.renderEngine.releaseImageData(entity1.cloakUrl);
        }
    }

    @Override
    public void updateAllRenderers() {
        int i1 = 0;
        while (i1 < this.worldRenderers.length) {
            if (this.worldRenderers[i1].field_1747_A && !this.worldRenderers[i1].needsUpdate) {
                this.worldRenderersToUpdate.add(this.worldRenderers[i1]);
                this.worldRenderers[i1].markDirty();
            }
            ++i1;
        }
    }

    @Override
    public void doNothingWithTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
    }
}

