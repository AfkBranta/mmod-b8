/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderItem
extends Render {
    private RenderBlocks renderBlocks = new RenderBlocks();
    private Random random = new Random();

    public RenderItem() {
        this.shadowSize = 0.15f;
        this.field_194_c = 0.75f;
    }

    public void doRenderItem(EntityItem entityItem1, double d2, double d4, double d6, float f8, float f9) {
        this.random.setSeed(187L);
        ItemStack itemStack10 = entityItem1.item;
        GL11.glPushMatrix();
        float f11 = MathHelper.sin(((float)entityItem1.age + f9) / 10.0f + entityItem1.field_804_d) * 0.1f + 0.1f;
        float f12 = (((float)entityItem1.age + f9) / 20.0f + entityItem1.field_804_d) * 57.295776f;
        int b13 = 1;
        if (entityItem1.item.stackSize > 1) {
            b13 = 2;
        }
        if (entityItem1.item.stackSize > 5) {
            b13 = 3;
        }
        if (entityItem1.item.stackSize > 20) {
            b13 = 4;
        }
        GL11.glTranslatef((float)((float)d2), (float)((float)d4 + f11), (float)((float)d6));
        GL11.glEnable((int)32826);
        if (itemStack10.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack10.itemID].getRenderType())) {
            GL11.glRotatef((float)f12, (float)0.0f, (float)1.0f, (float)0.0f);
            this.loadTexture("/terrain.png");
            float f27 = 0.25f;
            if (!Block.blocksList[itemStack10.itemID].renderAsNormalBlock() && itemStack10.itemID != Block.stairSingle.blockID) {
                f27 = 0.5f;
            }
            GL11.glScalef((float)f27, (float)f27, (float)f27);
            int i28 = 0;
            while (i28 < b13) {
                GL11.glPushMatrix();
                if (i28 > 0) {
                    float f16 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / f27;
                    float f17 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / f27;
                    float f18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / f27;
                    GL11.glTranslatef((float)f16, (float)f17, (float)f18);
                }
                this.renderBlocks.renderBlockOnInventory(Block.blocksList[itemStack10.itemID], itemStack10.getItemDamage());
                GL11.glPopMatrix();
                ++i28;
            }
        } else {
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            int i14 = itemStack10.getIconIndex();
            if (itemStack10.itemID < 256) {
                this.loadTexture("/terrain.png");
            } else {
                this.loadTexture("/gui/items.png");
            }
            Tessellator tessellator15 = Tessellator.instance;
            float f16 = (float)(i14 % 16 * 16 + 0) / 256.0f;
            float f17 = (float)(i14 % 16 * 16 + 16) / 256.0f;
            float f18 = (float)(i14 / 16 * 16 + 0) / 256.0f;
            float f19 = (float)(i14 / 16 * 16 + 16) / 256.0f;
            float f20 = 1.0f;
            float f21 = 0.5f;
            float f22 = 0.25f;
            int i23 = 0;
            while (i23 < b13) {
                GL11.glPushMatrix();
                if (i23 > 0) {
                    float f24 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f25 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    float f26 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef((float)f24, (float)f25, (float)f26);
                }
                GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                tessellator15.startDrawingQuads();
                tessellator15.setNormal(0.0f, 1.0f, 0.0f);
                tessellator15.addVertexWithUV(0.0f - f21, 0.0f - f22, 0.0, f16, f19);
                tessellator15.addVertexWithUV(f20 - f21, 0.0f - f22, 0.0, f17, f19);
                tessellator15.addVertexWithUV(f20 - f21, 1.0f - f22, 0.0, f17, f18);
                tessellator15.addVertexWithUV(0.0f - f21, 1.0f - f22, 0.0, f16, f18);
                tessellator15.draw();
                GL11.glPopMatrix();
                ++i23;
            }
        }
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    public void renderItemIntoGUI(FontRenderer fontRenderer1, RenderEngine renderEngine2, ItemStack itemStack3, int i4, int i5) {
        if (itemStack3 != null) {
            if (itemStack3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack3.itemID].getRenderType())) {
                int i6 = itemStack3.itemID;
                renderEngine2.bindTexture(renderEngine2.getTexture("/terrain.png"));
                Block block7 = Block.blocksList[i6];
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(i4 - 2), (float)(i5 + 3), (float)0.0f);
                GL11.glScalef((float)10.0f, (float)10.0f, (float)10.0f);
                GL11.glTranslatef((float)1.0f, (float)0.5f, (float)8.0f);
                GL11.glRotatef((float)210.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
                this.renderBlocks.renderBlockOnInventory(block7, itemStack3.getItemDamage());
                GL11.glPopMatrix();
            } else if (itemStack3.getIconIndex() >= 0) {
                GL11.glDisable((int)2896);
                if (itemStack3.itemID < 256) {
                    renderEngine2.bindTexture(renderEngine2.getTexture("/terrain.png"));
                } else {
                    renderEngine2.bindTexture(renderEngine2.getTexture("/gui/items.png"));
                }
                this.renderTexturedQuad(i4, i5, itemStack3.getIconIndex() % 16 * 16, itemStack3.getIconIndex() / 16 * 16, 16, 16);
                GL11.glEnable((int)2896);
            }
            GL11.glEnable((int)2884);
        }
    }

    public void renderItemOverlayIntoGUI(FontRenderer fontRenderer1, RenderEngine renderEngine2, ItemStack itemStack3, int i4, int i5) {
        if (itemStack3 != null) {
            if (itemStack3.stackSize > 1) {
                String string6 = "" + itemStack3.stackSize;
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                fontRenderer1.drawStringWithShadow(string6, i4 + 19 - 2 - fontRenderer1.getStringWidth(string6), i5 + 6 + 3, 0xFFFFFF);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
            }
            if (itemStack3.isItemDamaged()) {
                int i11 = (int)Math.round(13.0 - (double)itemStack3.getItemDamageForDisplay() * 13.0 / (double)itemStack3.getMaxDamage());
                int i7 = (int)Math.round(255.0 - (double)itemStack3.getItemDamageForDisplay() * 255.0 / (double)itemStack3.getMaxDamage());
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glDisable((int)3553);
                Tessellator tessellator8 = Tessellator.instance;
                int i9 = 255 - i7 << 16 | i7 << 8;
                int i10 = (255 - i7) / 4 << 16 | 0x3F00;
                this.renderQuad(tessellator8, i4 + 2, i5 + 13, 13, 2, 0);
                this.renderQuad(tessellator8, i4 + 2, i5 + 13, 12, 1, i10);
                this.renderQuad(tessellator8, i4 + 2, i5 + 13, i11, 1, i9);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glEnable((int)2929);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
        }
    }

    private void renderQuad(Tessellator tessellator1, int i2, int i3, int i4, int i5, int i6) {
        tessellator1.startDrawingQuads();
        tessellator1.setColorOpaque_I(i6);
        tessellator1.addVertex(i2 + 0, i3 + 0, 0.0);
        tessellator1.addVertex(i2 + 0, i3 + i5, 0.0);
        tessellator1.addVertex(i2 + i4, i3 + i5, 0.0);
        tessellator1.addVertex(i2 + i4, i3 + 0, 0.0);
        tessellator1.draw();
    }

    public void renderTexturedQuad(int i1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = 0.0f;
        float f8 = 0.00390625f;
        float f9 = 0.00390625f;
        Tessellator tessellator10 = Tessellator.instance;
        tessellator10.startDrawingQuads();
        tessellator10.addVertexWithUV(i1 + 0, i2 + i6, f7, (float)(i3 + 0) * f8, (float)(i4 + i6) * f9);
        tessellator10.addVertexWithUV(i1 + i5, i2 + i6, f7, (float)(i3 + i5) * f8, (float)(i4 + i6) * f9);
        tessellator10.addVertexWithUV(i1 + i5, i2 + 0, f7, (float)(i3 + i5) * f8, (float)(i4 + 0) * f9);
        tessellator10.addVertexWithUV(i1 + 0, i2 + 0, f7, (float)(i3 + 0) * f8, (float)(i4 + 0) * f9);
        tessellator10.draw();
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.doRenderItem((EntityItem)entity1, d2, d4, d6, f8, f9);
    }
}

