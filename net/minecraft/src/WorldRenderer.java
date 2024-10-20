/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.Entity;
import net.minecraft.src.ICamera;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {
    public World worldObj;
    private int glRenderList = -1;
    private static Tessellator tessellator = Tessellator.instance;
    public static int chunksUpdated = 0;
    public int posX;
    public int posY;
    public int posZ;
    public int sizeWidth;
    public int sizeHeight;
    public int sizeDepth;
    public int field_1755_i;
    public int field_1754_j;
    public int field_1753_k;
    public int field_1752_l;
    public int field_1751_m;
    public int field_1750_n;
    public boolean isInFrustum = false;
    public boolean[] skipRenderPass = new boolean[2];
    public int field_1746_q;
    public int field_1743_r;
    public int field_1741_s;
    public float field_1740_t;
    public boolean needsUpdate;
    public AxisAlignedBB field_1736_v;
    public int field_1735_w;
    public boolean isVisible = true;
    public boolean isWaitingOnOcclusionQuery;
    public int field_1732_z;
    public boolean field_1747_A;
    private boolean isInitialized = false;
    public List tileEntityRenderers = new ArrayList();
    private List tileEntities;

    public WorldRenderer(World world1, List list2, int i3, int i4, int i5, int i6, int i7) {
        this.worldObj = world1;
        this.tileEntities = list2;
        this.sizeHeight = this.sizeDepth = i6;
        this.sizeWidth = this.sizeDepth;
        this.field_1740_t = MathHelper.sqrt_float(this.sizeWidth * this.sizeWidth + this.sizeHeight * this.sizeHeight + this.sizeDepth * this.sizeDepth) / 2.0f;
        this.glRenderList = i7;
        this.posX = -999;
        this.setPosition(i3, i4, i5);
        this.needsUpdate = false;
    }

    public void setPosition(int i1, int i2, int i3) {
        if (i1 != this.posX || i2 != this.posY || i3 != this.posZ) {
            this.setDontDraw();
            this.posX = i1;
            this.posY = i2;
            this.posZ = i3;
            this.field_1746_q = i1 + this.sizeWidth / 2;
            this.field_1743_r = i2 + this.sizeHeight / 2;
            this.field_1741_s = i3 + this.sizeDepth / 2;
            this.field_1752_l = i1 & 0x3FF;
            this.field_1751_m = i2;
            this.field_1750_n = i3 & 0x3FF;
            this.field_1755_i = i1 - this.field_1752_l;
            this.field_1754_j = i2 - this.field_1751_m;
            this.field_1753_k = i3 - this.field_1750_n;
            float f4 = 6.0f;
            this.field_1736_v = AxisAlignedBB.getBoundingBox((float)i1 - f4, (float)i2 - f4, (float)i3 - f4, (float)(i1 + this.sizeWidth) + f4, (float)(i2 + this.sizeHeight) + f4, (float)(i3 + this.sizeDepth) + f4);
            GL11.glNewList((int)(this.glRenderList + 2), (int)4864);
            RenderItem.renderAABB(AxisAlignedBB.getBoundingBoxFromPool((float)this.field_1752_l - f4, (float)this.field_1751_m - f4, (float)this.field_1750_n - f4, (float)(this.field_1752_l + this.sizeWidth) + f4, (float)(this.field_1751_m + this.sizeHeight) + f4, (float)(this.field_1750_n + this.sizeDepth) + f4));
            GL11.glEndList();
            this.markDirty();
        }
    }

    private void setupGLTranslation() {
        GL11.glTranslatef((float)this.field_1752_l, (float)this.field_1751_m, (float)this.field_1750_n);
    }

    public void updateRenderer() {
        if (this.needsUpdate) {
            ++chunksUpdated;
            int i1 = this.posX;
            int i2 = this.posY;
            int i3 = this.posZ;
            int i4 = this.posX + this.sizeWidth;
            int i5 = this.posY + this.sizeHeight;
            int i6 = this.posZ + this.sizeDepth;
            int i7 = 0;
            while (i7 < 2) {
                this.skipRenderPass[i7] = true;
                ++i7;
            }
            Chunk.isLit = false;
            HashSet hashSet21 = new HashSet();
            hashSet21.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            int b8 = 1;
            ChunkCache chunkCache9 = new ChunkCache(this.worldObj, i1 - b8, i2 - b8, i3 - b8, i4 + b8, i5 + b8, i6 + b8);
            RenderBlocks renderBlocks10 = new RenderBlocks(chunkCache9);
            int i11 = 0;
            while (i11 < 2) {
                boolean z12 = false;
                boolean z13 = false;
                boolean z14 = false;
                int i15 = i2;
                while (i15 < i5) {
                    int i16 = i3;
                    while (i16 < i6) {
                        int i17 = i1;
                        while (i17 < i4) {
                            int i18 = chunkCache9.getBlockId(i17, i15, i16);
                            if (i18 > 0) {
                                Block block24;
                                int i20;
                                TileEntity tileEntity23;
                                if (!z14) {
                                    z14 = true;
                                    GL11.glNewList((int)(this.glRenderList + i11), (int)4864);
                                    GL11.glPushMatrix();
                                    this.setupGLTranslation();
                                    float f19 = 1.000001f;
                                    GL11.glTranslatef((float)((float)(-this.sizeDepth) / 2.0f), (float)((float)(-this.sizeHeight) / 2.0f), (float)((float)(-this.sizeDepth) / 2.0f));
                                    GL11.glScalef((float)f19, (float)f19, (float)f19);
                                    GL11.glTranslatef((float)((float)this.sizeDepth / 2.0f), (float)((float)this.sizeHeight / 2.0f), (float)((float)this.sizeDepth / 2.0f));
                                    tessellator.startDrawingQuads();
                                    tessellator.setTranslationD(-this.posX, -this.posY, -this.posZ);
                                }
                                if (i11 == 0 && Block.isBlockContainer[i18] && TileEntityRenderer.instance.hasSpecialRenderer(tileEntity23 = chunkCache9.getBlockTileEntity(i17, i15, i16))) {
                                    this.tileEntityRenderers.add(tileEntity23);
                                }
                                if ((i20 = (block24 = Block.blocksList[i18]).getRenderBlockPass()) != i11) {
                                    z12 = true;
                                } else if (i20 == i11) {
                                    z13 |= renderBlocks10.renderBlockByRenderType(block24, i17, i15, i16);
                                }
                            }
                            ++i17;
                        }
                        ++i16;
                    }
                    ++i15;
                }
                if (z14) {
                    tessellator.draw();
                    GL11.glPopMatrix();
                    GL11.glEndList();
                    tessellator.setTranslationD(0.0, 0.0, 0.0);
                } else {
                    z13 = false;
                }
                if (z13) {
                    this.skipRenderPass[i11] = false;
                }
                if (!z12) break;
                ++i11;
            }
            HashSet hashSet22 = new HashSet();
            hashSet22.addAll(this.tileEntityRenderers);
            hashSet22.removeAll(hashSet21);
            this.tileEntities.addAll(hashSet22);
            hashSet21.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(hashSet21);
            this.field_1747_A = Chunk.isLit;
            this.isInitialized = true;
        }
    }

    public float distanceToEntitySquared(Entity entity1) {
        float f2 = (float)(entity1.posX - (double)this.field_1746_q);
        float f3 = (float)(entity1.posY - (double)this.field_1743_r);
        float f4 = (float)(entity1.posZ - (double)this.field_1741_s);
        return f2 * f2 + f3 * f3 + f4 * f4;
    }

    public void setDontDraw() {
        int i1 = 0;
        while (i1 < 2) {
            this.skipRenderPass[i1] = true;
            ++i1;
        }
        this.isInFrustum = false;
        this.isInitialized = false;
    }

    public void func_1204_c() {
        this.setDontDraw();
        this.worldObj = null;
    }

    public int getGLCallListForPass(int i1) {
        return !this.isInFrustum ? -1 : (!this.skipRenderPass[i1] ? this.glRenderList + i1 : -1);
    }

    public void updateInFrustrum(ICamera iCamera1) {
        this.isInFrustum = iCamera1.isBoundingBoxInFrustum(this.field_1736_v);
    }

    public void callOcclusionQueryList() {
        GL11.glCallList((int)(this.glRenderList + 2));
    }

    public boolean canRender() {
        return !this.isInitialized ? false : this.skipRenderPass[0] && this.skipRenderPass[1];
    }

    public void markDirty() {
        this.needsUpdate = true;
    }
}

