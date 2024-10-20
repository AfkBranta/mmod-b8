/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import MEDMEX.Modules.Visual.Hand;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    private Minecraft mc;
    private ItemStack itemToRender = null;
    private float equippedProgress = 0.0f;
    private float prevEquippedProgress = 0.0f;
    private RenderBlocks renderBlocksInstance = new RenderBlocks();
    private int field_20099_f = -1;

    public ItemRenderer(Minecraft minecraft1) {
        this.mc = minecraft1;
    }

    public void renderItem(ItemStack itemStack1) {
        GL11.glPushMatrix();
        if (itemStack1.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack1.itemID].getRenderType())) {
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            this.renderBlocksInstance.renderBlockOnInventory(Block.blocksList[itemStack1.itemID], itemStack1.getItemDamage());
        } else {
            float f15;
            float f14;
            float f13;
            if (itemStack1.itemID < 256) {
                GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            } else {
                GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/items.png"));
            }
            Tessellator tessellator2 = Tessellator.instance;
            float f3 = ((float)(itemStack1.getIconIndex() % 16 * 16) + 0.0f) / 256.0f;
            float f4 = ((float)(itemStack1.getIconIndex() % 16 * 16) + 15.99f) / 256.0f;
            float f5 = ((float)(itemStack1.getIconIndex() / 16 * 16) + 0.0f) / 256.0f;
            float f6 = ((float)(itemStack1.getIconIndex() / 16 * 16) + 15.99f) / 256.0f;
            float f7 = 1.0f;
            float f8 = 0.0f;
            float f9 = 0.3f;
            GL11.glEnable((int)32826);
            GL11.glTranslatef((float)(-f8), (float)(-f9), (float)0.0f);
            float f10 = 1.5f;
            if (Hand.instance.isEnabled) {
                f10 = (float)Hand.instance.getSet("Scale").getValDouble();
            }
            GL11.glScalef((float)f10, (float)f10, (float)f10);
            GL11.glRotatef((float)50.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)335.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            if (Hand.instance.isEnabled) {
                GL11.glTranslatef((float)((float)Hand.instance.getSet("TranslateX").getValDouble()), (float)((float)Hand.instance.getSet("TranslateY").getValDouble()), (float)((float)Hand.instance.getSet("TranslateZ").getValDouble()));
            } else {
                GL11.glTranslatef((float)-0.9375f, (float)-0.0625f, (float)0.0f);
            }
            float f11 = 0.0625f;
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(0.0f, 0.0f, 1.0f);
            tessellator2.addVertexWithUV(0.0, 0.0, 0.0, f4, f6);
            tessellator2.addVertexWithUV(f7, 0.0, 0.0, f3, f6);
            tessellator2.addVertexWithUV(f7, 1.0, 0.0, f3, f5);
            tessellator2.addVertexWithUV(0.0, 1.0, 0.0, f4, f5);
            tessellator2.draw();
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(0.0f, 0.0f, -1.0f);
            tessellator2.addVertexWithUV(0.0, 1.0, 0.0f - f11, f4, f5);
            tessellator2.addVertexWithUV(f7, 1.0, 0.0f - f11, f3, f5);
            tessellator2.addVertexWithUV(f7, 0.0, 0.0f - f11, f3, f6);
            tessellator2.addVertexWithUV(0.0, 0.0, 0.0f - f11, f4, f6);
            tessellator2.draw();
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(-1.0f, 0.0f, 0.0f);
            int i12 = 0;
            while (i12 < 16) {
                f13 = (float)i12 / 16.0f;
                f14 = f4 + (f3 - f4) * f13 - 0.001953125f;
                f15 = f7 * f13;
                tessellator2.addVertexWithUV(f15, 0.0, 0.0f - f11, f14, f6);
                tessellator2.addVertexWithUV(f15, 0.0, 0.0, f14, f6);
                tessellator2.addVertexWithUV(f15, 1.0, 0.0, f14, f5);
                tessellator2.addVertexWithUV(f15, 1.0, 0.0f - f11, f14, f5);
                ++i12;
            }
            tessellator2.draw();
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(1.0f, 0.0f, 0.0f);
            i12 = 0;
            while (i12 < 16) {
                f13 = (float)i12 / 16.0f;
                f14 = f4 + (f3 - f4) * f13 - 0.001953125f;
                f15 = f7 * f13 + 0.0625f;
                tessellator2.addVertexWithUV(f15, 1.0, 0.0f - f11, f14, f5);
                tessellator2.addVertexWithUV(f15, 1.0, 0.0, f14, f5);
                tessellator2.addVertexWithUV(f15, 0.0, 0.0, f14, f6);
                tessellator2.addVertexWithUV(f15, 0.0, 0.0f - f11, f14, f6);
                ++i12;
            }
            tessellator2.draw();
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(0.0f, 1.0f, 0.0f);
            i12 = 0;
            while (i12 < 16) {
                f13 = (float)i12 / 16.0f;
                f14 = f6 + (f5 - f6) * f13 - 0.001953125f;
                f15 = f7 * f13 + 0.0625f;
                tessellator2.addVertexWithUV(0.0, f15, 0.0, f4, f14);
                tessellator2.addVertexWithUV(f7, f15, 0.0, f3, f14);
                tessellator2.addVertexWithUV(f7, f15, 0.0f - f11, f3, f14);
                tessellator2.addVertexWithUV(0.0, f15, 0.0f - f11, f4, f14);
                ++i12;
            }
            tessellator2.draw();
            tessellator2.startDrawingQuads();
            tessellator2.setNormal(0.0f, -1.0f, 0.0f);
            i12 = 0;
            while (i12 < 16) {
                f13 = (float)i12 / 16.0f;
                f14 = f6 + (f5 - f6) * f13 - 0.001953125f;
                f15 = f7 * f13;
                tessellator2.addVertexWithUV(f7, f15, 0.0, f3, f14);
                tessellator2.addVertexWithUV(0.0, f15, 0.0, f4, f14);
                tessellator2.addVertexWithUV(0.0, f15, 0.0f - f11, f4, f14);
                tessellator2.addVertexWithUV(f7, f15, 0.0f - f11, f3, f14);
                ++i12;
            }
            tessellator2.draw();
            GL11.glDisable((int)32826);
        }
        GL11.glPopMatrix();
    }

    public void renderItemInFirstPerson(float f1) {
        float f2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * f1;
        EntityPlayerSP entityPlayerSP3 = this.mc.thePlayer;
        GL11.glPushMatrix();
        GL11.glRotatef((float)(entityPlayerSP3.prevRotationPitch + (entityPlayerSP3.rotationPitch - entityPlayerSP3.prevRotationPitch) * f1), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)(entityPlayerSP3.prevRotationYaw + (entityPlayerSP3.rotationYaw - entityPlayerSP3.prevRotationYaw) * f1), (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        float f4 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(entityPlayerSP3.posX), MathHelper.floor_double(entityPlayerSP3.posY), MathHelper.floor_double(entityPlayerSP3.posZ));
        GL11.glColor4f((float)f4, (float)f4, (float)f4, (float)1.0f);
        ItemStack itemStack5 = this.itemToRender;
        if (entityPlayerSP3.fishEntity != null) {
            itemStack5 = new ItemStack(Item.stick);
        }
        if (itemStack5 != null) {
            GL11.glPushMatrix();
            float f6 = 0.8f;
            float f7 = entityPlayerSP3.getSwingProgress(f1);
            float f8 = MathHelper.sin(f7 * (float)Math.PI);
            float f9 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI);
            GL11.glTranslatef((float)(-f9 * 0.4f), (float)(MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI * 2.0f) * 0.2f), (float)(-f8 * 0.2f));
            GL11.glTranslatef((float)(0.7f * f6), (float)(-0.65f * f6 - (1.0f - f2) * 0.6f), (float)(-0.9f * f6));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            f7 = entityPlayerSP3.getSwingProgress(f1);
            f8 = MathHelper.sin(f7 * f7 * (float)Math.PI);
            f9 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI);
            GL11.glRotatef((float)(-f8 * 20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-f9 * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(-f9 * 80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            f7 = 0.4f;
            GL11.glScalef((float)f7, (float)f7, (float)f7);
            if (itemStack5.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            }
            this.renderItem(itemStack5);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            float f6 = 0.8f;
            float f7 = entityPlayerSP3.getSwingProgress(f1);
            float f8 = MathHelper.sin(f7 * (float)Math.PI);
            float f9 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI);
            GL11.glTranslatef((float)(-f9 * 0.3f), (float)(MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI * 2.0f) * 0.4f), (float)(-f8 * 0.4f));
            GL11.glTranslatef((float)(0.8f * f6), (float)(-0.75f * f6 - (1.0f - f2) * 0.6f), (float)(-0.9f * f6));
            GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glEnable((int)32826);
            f7 = entityPlayerSP3.getSwingProgress(f1);
            f8 = MathHelper.sin(f7 * f7 * (float)Math.PI);
            f9 = MathHelper.sin(MathHelper.sqrt_float(f7) * (float)Math.PI);
            GL11.glRotatef((float)(f9 * 70.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-f8 * 20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getEntityTexture()));
            GL11.glTranslatef((float)-1.0f, (float)3.6f, (float)3.5f);
            GL11.glRotatef((float)120.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)200.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glTranslatef((float)5.6f, (float)0.0f, (float)0.0f);
            Render render10 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer renderPlayer11 = (RenderPlayer)render10;
            f9 = 1.0f;
            GL11.glScalef((float)f9, (float)f9, (float)f9);
            renderPlayer11.drawFirstPersonHand();
            GL11.glPopMatrix();
        }
        GL11.glDisable((int)32826);
        RenderHelper.disableStandardItemLighting();
    }

    public void renderOverlays(float f1) {
        int i2;
        GL11.glDisable((int)3008);
        if (this.mc.thePlayer.isBurning()) {
            i2 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)i2);
            this.renderFireInFirstPerson(f1);
        }
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            i2 = MathHelper.floor_double(this.mc.thePlayer.posX);
            int i3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            int i4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            int i5 = this.mc.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture((int)3553, (int)i5);
            int i6 = this.mc.theWorld.getBlockId(i2, i3, i4);
            if (Block.blocksList[i6] != null) {
                this.renderInsideOfBlock(f1, Block.blocksList[i6].getBlockTextureFromSide(2));
            }
        }
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            i2 = this.mc.renderEngine.getTexture("/misc/water.png");
            GL11.glBindTexture((int)3553, (int)i2);
            this.renderWarpedTextureOverlay(f1);
        }
        GL11.glEnable((int)3008);
    }

    private void renderInsideOfBlock(float f1, int i2) {
        Tessellator tessellator3 = Tessellator.instance;
        this.mc.thePlayer.getEntityBrightness(f1);
        float f4 = 0.1f;
        GL11.glColor4f((float)f4, (float)f4, (float)f4, (float)0.5f);
        GL11.glPushMatrix();
        float f5 = -1.0f;
        float f6 = 1.0f;
        float f7 = -1.0f;
        float f8 = 1.0f;
        float f9 = -0.5f;
        float f10 = 0.0078125f;
        float f11 = (float)(i2 % 16) / 256.0f - f10;
        float f12 = ((float)(i2 % 16) + 15.99f) / 256.0f + f10;
        float f13 = (float)(i2 / 16) / 256.0f - f10;
        float f14 = ((float)(i2 / 16) + 15.99f) / 256.0f + f10;
        tessellator3.startDrawingQuads();
        tessellator3.addVertexWithUV(f5, f7, f9, f12, f14);
        tessellator3.addVertexWithUV(f6, f7, f9, f11, f14);
        tessellator3.addVertexWithUV(f6, f8, f9, f11, f13);
        tessellator3.addVertexWithUV(f5, f8, f9, f12, f13);
        tessellator3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderWarpedTextureOverlay(float f1) {
        Tessellator tessellator2 = Tessellator.instance;
        float f3 = this.mc.thePlayer.getEntityBrightness(f1);
        GL11.glColor4f((float)f3, (float)f3, (float)f3, (float)0.5f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glPushMatrix();
        float f4 = 4.0f;
        float f5 = -1.0f;
        float f6 = 1.0f;
        float f7 = -1.0f;
        float f8 = 1.0f;
        float f9 = -0.5f;
        float f10 = -this.mc.thePlayer.rotationYaw / 64.0f;
        float f11 = this.mc.thePlayer.rotationPitch / 64.0f;
        tessellator2.startDrawingQuads();
        tessellator2.addVertexWithUV(f5, f7, f9, f4 + f10, f4 + f11);
        tessellator2.addVertexWithUV(f6, f7, f9, 0.0f + f10, f4 + f11);
        tessellator2.addVertexWithUV(f6, f8, f9, 0.0f + f10, 0.0f + f11);
        tessellator2.addVertexWithUV(f5, f8, f9, f4 + f10, 0.0f + f11);
        tessellator2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    private void renderFireInFirstPerson(float f1) {
        Tessellator tessellator2 = Tessellator.instance;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.9f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        float f3 = 1.0f;
        int i4 = 0;
        while (i4 < 2) {
            GL11.glPushMatrix();
            int i5 = Block.fire.blockIndexInTexture + i4 * 16;
            int i6 = (i5 & 0xF) << 4;
            int i7 = i5 & 0xF0;
            float f8 = (float)i6 / 256.0f;
            float f9 = ((float)i6 + 15.99f) / 256.0f;
            float f10 = (float)i7 / 256.0f;
            float f11 = ((float)i7 + 15.99f) / 256.0f;
            float f12 = (0.0f - f3) / 2.0f;
            float f13 = f12 + f3;
            float f14 = 0.0f - f3 / 2.0f;
            float f15 = f14 + f3;
            float f16 = -0.5f;
            GL11.glTranslatef((float)((float)(-(i4 * 2 - 1)) * 0.24f), (float)-0.3f, (float)0.0f);
            GL11.glRotatef((float)((float)(i4 * 2 - 1) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            tessellator2.startDrawingQuads();
            tessellator2.addVertexWithUV(f12, f14, f16, f9, f11);
            tessellator2.addVertexWithUV(f13, f14, f16, f8, f11);
            tessellator2.addVertexWithUV(f13, f15, f16, f8, f10);
            tessellator2.addVertexWithUV(f12, f15, f16, f9, f10);
            tessellator2.draw();
            GL11.glPopMatrix();
            ++i4;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3042);
    }

    public void updateEquippedItem() {
        float f5;
        float f6;
        float f7;
        boolean z4;
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayerSP entityPlayerSP1 = this.mc.thePlayer;
        ItemStack itemStack2 = entityPlayerSP1.inventory.getCurrentItem();
        boolean bl = z4 = this.field_20099_f == entityPlayerSP1.inventory.currentItem && itemStack2 == this.itemToRender;
        if (this.itemToRender == null && itemStack2 == null) {
            z4 = true;
        }
        if (itemStack2 != null && this.itemToRender != null && itemStack2 != this.itemToRender && itemStack2.itemID == this.itemToRender.itemID) {
            this.itemToRender = itemStack2;
            z4 = true;
        }
        if ((f7 = (f6 = z4 ? 1.0f : 0.0f) - this.equippedProgress) < -(f5 = 0.4f)) {
            f7 = -f5;
        }
        if (f7 > f5) {
            f7 = f5;
        }
        this.equippedProgress += f7;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = itemStack2;
            this.field_20099_f = entityPlayerSP1.inventory.currentItem;
        }
    }

    public void func_9449_b() {
        this.equippedProgress = 0.0f;
    }

    public void func_9450_c() {
        this.equippedProgress = 0.0f;
    }
}

