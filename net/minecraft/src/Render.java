/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public abstract class Render {
    protected RenderManager renderManager;
    private ModelBase modelBase = new ModelBiped();
    private RenderBlocks renderBlocks = new RenderBlocks();
    protected float shadowSize = 0.0f;
    protected float field_194_c = 1.0f;

    public abstract void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9);

    protected void loadTexture(String string1) {
        RenderEngine renderEngine2 = this.renderManager.renderEngine;
        renderEngine2.bindTexture(renderEngine2.getTexture(string1));
    }

    protected boolean loadDownloadableImageTexture(String string1, String string2) {
        RenderEngine renderEngine3 = this.renderManager.renderEngine;
        int i4 = renderEngine3.getTextureForDownloadableImage(string1, string2);
        if (i4 >= 0) {
            renderEngine3.bindTexture(i4);
            return true;
        }
        return false;
    }

    private void renderEntityOnFire(Entity entity1, double d2, double d4, double d6, float f8) {
        GL11.glDisable((int)2896);
        int i9 = Block.fire.blockIndexInTexture;
        int i10 = (i9 & 0xF) << 4;
        int i11 = i9 & 0xF0;
        float f12 = (float)i10 / 256.0f;
        float f13 = ((float)i10 + 15.99f) / 256.0f;
        float f14 = (float)i11 / 256.0f;
        float f15 = ((float)i11 + 15.99f) / 256.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
        float f16 = entity1.width * 1.4f;
        GL11.glScalef((float)f16, (float)f16, (float)f16);
        this.loadTexture("/terrain.png");
        Tessellator tessellator17 = Tessellator.instance;
        float f18 = 1.0f;
        float f19 = 0.5f;
        float f20 = 0.0f;
        float f21 = entity1.height / entity1.width;
        GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)(-0.4f + (float)((int)f21) * 0.02f));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator17.startDrawingQuads();
        while (f21 > 0.0f) {
            tessellator17.addVertexWithUV(f18 - f19, 0.0f - f20, 0.0, f13, f15);
            tessellator17.addVertexWithUV(0.0f - f19, 0.0f - f20, 0.0, f12, f15);
            tessellator17.addVertexWithUV(0.0f - f19, 1.4f - f20, 0.0, f12, f14);
            tessellator17.addVertexWithUV(f18 - f19, 1.4f - f20, 0.0, f13, f14);
            f21 -= 1.0f;
            f20 -= 1.0f;
            f18 *= 0.9f;
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.04f);
        }
        tessellator17.draw();
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
    }

    private void renderShadow(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderEngine renderEngine10 = this.renderManager.renderEngine;
        renderEngine10.bindTexture(renderEngine10.getTexture("%clamp%/misc/shadow.png"));
        World world11 = this.getWorldFromRenderManager();
        GL11.glDepthMask((boolean)false);
        float f12 = this.shadowSize;
        double d13 = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * (double)f9;
        double d15 = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * (double)f9 + (double)entity1.getShadowSize();
        double d17 = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * (double)f9;
        int i19 = MathHelper.floor_double(d13 - (double)f12);
        int i20 = MathHelper.floor_double(d13 + (double)f12);
        int i21 = MathHelper.floor_double(d15 - (double)f12);
        int i22 = MathHelper.floor_double(d15);
        int i23 = MathHelper.floor_double(d17 - (double)f12);
        int i24 = MathHelper.floor_double(d17 + (double)f12);
        double d25 = d2 - d13;
        double d27 = d4 - d15;
        double d29 = d6 - d17;
        Tessellator tessellator31 = Tessellator.instance;
        tessellator31.startDrawingQuads();
        int i32 = i19;
        while (i32 <= i20) {
            int i33 = i21;
            while (i33 <= i22) {
                int i34 = i23;
                while (i34 <= i24) {
                    int i35 = world11.getBlockId(i32, i33 - 1, i34);
                    if (i35 > 0 && world11.getBlockLightValue(i32, i33, i34) > 3) {
                        this.renderShadowOnBlock(Block.blocksList[i35], d2, d4 + (double)entity1.getShadowSize(), d6, i32, i33, i34, f8, f12, d25, d27 + (double)entity1.getShadowSize(), d29);
                    }
                    ++i34;
                }
                ++i33;
            }
            ++i32;
        }
        tessellator31.draw();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }

    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    private void renderShadowOnBlock(Block block1, double d2, double d4, double d6, int i8, int i9, int i10, float f11, float f12, double d13, double d15, double d17) {
        double d20;
        Tessellator tessellator19 = Tessellator.instance;
        if (block1.renderAsNormalBlock() && (d20 = ((double)f11 - (d4 - ((double)i9 + d15)) / 2.0) * 0.5 * (double)this.getWorldFromRenderManager().getLightBrightness(i8, i9, i10)) >= 0.0) {
            if (d20 > 1.0) {
                d20 = 1.0;
            }
            tessellator19.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)d20);
            double d22 = (double)i8 + block1.minX + d13;
            double d24 = (double)i8 + block1.maxX + d13;
            double d26 = (double)i9 + block1.minY + d15 + 0.015625;
            double d28 = (double)i10 + block1.minZ + d17;
            double d30 = (double)i10 + block1.maxZ + d17;
            float f32 = (float)((d2 - d22) / 2.0 / (double)f12 + 0.5);
            float f33 = (float)((d2 - d24) / 2.0 / (double)f12 + 0.5);
            float f34 = (float)((d6 - d28) / 2.0 / (double)f12 + 0.5);
            float f35 = (float)((d6 - d30) / 2.0 / (double)f12 + 0.5);
            tessellator19.addVertexWithUV(d22, d26, d28, f32, f34);
            tessellator19.addVertexWithUV(d22, d26, d30, f32, f35);
            tessellator19.addVertexWithUV(d24, d26, d30, f33, f35);
            tessellator19.addVertexWithUV(d24, d26, d28, f33, f34);
        }
    }

    public static void renderOffsetAABB(AxisAlignedBB axisAlignedBB0, double d1, double d3, double d5) {
        GL11.glDisable((int)3553);
        Tessellator tessellator7 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator7.startDrawingQuads();
        tessellator7.setTranslationD(d1, d3, d5);
        tessellator7.setNormal(0.0f, 0.0f, -1.0f);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.setNormal(0.0f, 0.0f, 1.0f);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.setNormal(0.0f, -1.0f, 0.0f);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.setNormal(0.0f, 1.0f, 0.0f);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.setNormal(-1.0f, 0.0f, 0.0f);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.setNormal(1.0f, 0.0f, 0.0f);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator7.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator7.setTranslationD(0.0, 0.0, 0.0);
        tessellator7.draw();
        GL11.glEnable((int)3553);
    }

    public static void renderAABB(AxisAlignedBB axisAlignedBB0) {
        Tessellator tessellator1 = Tessellator.instance;
        tessellator1.startDrawingQuads();
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.minX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.minZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.maxY, axisAlignedBB0.maxZ);
        tessellator1.addVertex(axisAlignedBB0.maxX, axisAlignedBB0.minY, axisAlignedBB0.maxZ);
        tessellator1.draw();
    }

    public void setRenderManager(RenderManager renderManager1) {
        this.renderManager = renderManager1;
    }

    public void doRenderShadowAndFire(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        double d10;
        float f12;
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && (f12 = (float)((1.0 - (d10 = this.renderManager.func_851_a(entity1.posX, entity1.posY, entity1.posZ)) / 256.0) * (double)this.field_194_c)) > 0.0f) {
            this.renderShadow(entity1, d2, d4, d6, f12, f9);
        }
        if (entity1.isBurning()) {
            this.renderEntityOnFire(entity1, d2, d4, d6, f9);
        }
    }

    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
}

