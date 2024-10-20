/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventGuiIngameRender;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ChatLine;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiChat;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiIngame
extends Gui {
    private static RenderItem itemRenderer = new RenderItem();
    private List chatMessageList = new ArrayList();
    private Random rand = new Random();
    private Minecraft mc;
    public String field_933_a = null;
    private int updateCounter = 0;
    private String recordPlaying = "";
    private int recordPlayingUpFor = 0;
    private boolean field_22065_l = false;
    public float field_6446_b;
    float prevVignetteBrightness = 1.0f;

    public GuiIngame(Minecraft minecraft1) {
        this.mc = minecraft1;
    }

    public void renderGameOverlay(float f1, boolean z2, int i3, int i4) {
        int i17;
        int i16;
        int i15;
        boolean z12;
        float f10;
        ScaledResolution scaledResolution5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int i6 = scaledResolution5.getScaledWidth();
        int i7 = scaledResolution5.getScaledHeight();
        FontRenderer fontRenderer8 = this.mc.fontRenderer;
        this.mc.entityRenderer.func_905_b();
        GL11.glEnable((int)3042);
        if (Minecraft.isFancyGraphicsEnabled()) {
            this.renderVignette(this.mc.thePlayer.getEntityBrightness(f1), i6, i7);
        }
        ItemStack itemStack9 = this.mc.thePlayer.inventory.armorItemInSlot(3);
        if (!this.mc.gameSettings.thirdPersonView && itemStack9 != null && itemStack9.itemID == Block.pumpkin.blockID) {
            this.renderPumpkinBlur(i6, i7);
        }
        if ((f10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * f1) > 0.0f) {
            this.renderPortalOverlay(f10, i6, i7);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/gui.png"));
        InventoryPlayer inventoryPlayer11 = this.mc.thePlayer.inventory;
        this.zLevel = -90.0f;
        this.drawTexturedModalRect(i6 / 2 - 91, i7 - 22, 0, 0, 182, 22);
        this.drawTexturedModalRect(i6 / 2 - 91 - 1 + inventoryPlayer11.currentItem * 20, i7 - 22 - 1, 0, 22, 24, 22);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/gui/icons.png"));
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)775, (int)769);
        this.drawTexturedModalRect(i6 / 2 - 7, i7 / 2 - 7, 0, 0, 16, 16);
        GL11.glDisable((int)3042);
        boolean bl = z12 = this.mc.thePlayer.field_9306_bj / 3 % 2 == 1;
        if (this.mc.thePlayer.field_9306_bj < 10) {
            z12 = false;
        }
        int i13 = this.mc.thePlayer.health;
        int i14 = this.mc.thePlayer.prevHealth;
        this.rand.setSeed(this.updateCounter * 312871);
        if (this.mc.playerController.shouldDrawHUD()) {
            int i18;
            i15 = this.mc.thePlayer.getPlayerArmorValue();
            i16 = 0;
            while (i16 < 10) {
                i17 = i7 - 32;
                if (i15 > 0) {
                    i18 = i6 / 2 + 91 - i16 * 8 - 9;
                    if (i16 * 2 + 1 < i15) {
                        this.drawTexturedModalRect(i18, i17, 34, 9, 9, 9);
                    }
                    if (i16 * 2 + 1 == i15) {
                        this.drawTexturedModalRect(i18, i17, 25, 9, 9, 9);
                    }
                    if (i16 * 2 + 1 > i15) {
                        this.drawTexturedModalRect(i18, i17, 16, 9, 9, 9);
                    }
                }
                int b28 = 0;
                if (z12) {
                    b28 = 1;
                }
                int i19 = i6 / 2 - 91 + i16 * 8;
                if (i13 <= 4) {
                    i17 += this.rand.nextInt(2);
                }
                this.drawTexturedModalRect(i19, i17, 16 + b28 * 9, 0, 9, 9);
                if (z12) {
                    if (i16 * 2 + 1 < i14) {
                        this.drawTexturedModalRect(i19, i17, 70, 0, 9, 9);
                    }
                    if (i16 * 2 + 1 == i14) {
                        this.drawTexturedModalRect(i19, i17, 79, 0, 9, 9);
                    }
                }
                if (i16 * 2 + 1 < i13) {
                    this.drawTexturedModalRect(i19, i17, 52, 0, 9, 9);
                }
                if (i16 * 2 + 1 == i13) {
                    this.drawTexturedModalRect(i19, i17, 61, 0, 9, 9);
                }
                ++i16;
            }
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
                i16 = (int)Math.ceil((double)(this.mc.thePlayer.air - 2) * 10.0 / 300.0);
                i17 = (int)Math.ceil((double)this.mc.thePlayer.air * 10.0 / 300.0) - i16;
                i18 = 0;
                while (i18 < i16 + i17) {
                    if (i18 < i16) {
                        this.drawTexturedModalRect(i6 / 2 - 91 + i18 * 8, i7 - 32 - 9, 16, 18, 9, 9);
                    } else {
                        this.drawTexturedModalRect(i6 / 2 - 91 + i18 * 8, i7 - 32 - 9, 25, 18, 9, 9);
                    }
                    ++i18;
                }
            }
        }
        GL11.glDisable((int)3042);
        GL11.glEnable((int)32826);
        GL11.glPushMatrix();
        GL11.glRotatef((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        i15 = 0;
        while (i15 < 9) {
            i16 = i6 / 2 - 90 + i15 * 20 + 2;
            i17 = i7 - 16 - 3;
            this.renderInventorySlot(i15, i16, i17, f1);
            ++i15;
        }
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable((int)32826);
        if (this.mc.thePlayer.func_22060_M() > 0) {
            GL11.glDisable((int)2929);
            GL11.glDisable((int)3008);
            i15 = this.mc.thePlayer.func_22060_M();
            float f27 = (float)i15 / 100.0f;
            if (f27 > 1.0f) {
                f27 = 1.0f - (float)(i15 - 100) / 10.0f;
            }
            i17 = (int)(220.0f * f27) << 24 | 0x101020;
            GuiIngame.drawRect(0.0f, 0.0f, i6, i7, i17);
            GL11.glEnable((int)3008);
            GL11.glEnable((int)2929);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            fontRenderer8.drawStringWithShadow("Minecraft Beta 1.4_01 (" + this.mc.debug + ")", 2.0f, 2.0f, 0xFFFFFF);
            fontRenderer8.drawStringWithShadow(this.mc.func_6241_m(), 2.0f, 12.0f, 0xFFFFFF);
            fontRenderer8.drawStringWithShadow(this.mc.func_6262_n(), 2.0f, 22.0f, 0xFFFFFF);
            fontRenderer8.drawStringWithShadow(this.mc.func_6245_o(), 2.0f, 32.0f, 0xFFFFFF);
            fontRenderer8.drawStringWithShadow(this.mc.func_21002_o(), 2.0f, 42.0f, 0xFFFFFF);
            long j24 = Runtime.getRuntime().maxMemory();
            long j29 = Runtime.getRuntime().totalMemory();
            long j30 = Runtime.getRuntime().freeMemory();
            long j21 = j29 - j30;
            String string23 = "Used memory: " + j21 * 100L / j24 + "% (" + j21 / 1024L / 1024L + "MB) of " + j24 / 1024L / 1024L + "MB";
            this.drawString(fontRenderer8, string23, i6 - fontRenderer8.getStringWidth(string23) - 2, 2, 0xE0E0E0);
            string23 = "Allocated memory: " + j29 * 100L / j24 + "% (" + j29 / 1024L / 1024L + "MB)";
            this.drawString(fontRenderer8, string23, i6 - fontRenderer8.getStringWidth(string23) - 2, 12, 0xE0E0E0);
            this.drawString(fontRenderer8, "x: " + this.mc.thePlayer.posX, 2, 64, 0xE0E0E0);
            this.drawString(fontRenderer8, "y: " + this.mc.thePlayer.posY, 2, 72, 0xE0E0E0);
            this.drawString(fontRenderer8, "z: " + this.mc.thePlayer.posZ, 2, 80, 0xE0E0E0);
        }
        if (this.recordPlayingUpFor > 0) {
            float f25 = (float)this.recordPlayingUpFor - f1;
            i16 = (int)(f25 * 256.0f / 20.0f);
            if (i16 > 255) {
                i16 = 255;
            }
            if (i16 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(i6 / 2), (float)(i7 - 48), (float)0.0f);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                i17 = 0xFFFFFF;
                if (this.field_22065_l) {
                    i17 = Color.HSBtoRGB(f25 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                fontRenderer8.drawString(this.recordPlaying, -fontRenderer8.getStringWidth(this.recordPlaying) / 2, -4.0f, i17 + (i16 << 24));
                GL11.glDisable((int)3042);
                GL11.glPopMatrix();
            }
        }
        int b26 = 10;
        boolean z31 = false;
        if (this.mc.currentScreen instanceof GuiChat) {
            b26 = 20;
            z31 = true;
        }
        EventGuiIngameRender event = new EventGuiIngameRender();
        Client.onEvent(event);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3008);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)(i7 - 48), (float)0.0f);
        i17 = 0;
        while (i17 < this.chatMessageList.size() && i17 < b26) {
            if (((ChatLine)this.chatMessageList.get((int)i17)).updateCounter < 200 || z31) {
                double d32 = (double)((ChatLine)this.chatMessageList.get((int)i17)).updateCounter / 200.0;
                d32 = 1.0 - d32;
                if ((d32 *= 10.0) < 0.0) {
                    d32 = 0.0;
                }
                if (d32 > 1.0) {
                    d32 = 1.0;
                }
                d32 *= d32;
                int i20 = (int)(255.0 * d32);
                if (z31) {
                    i20 = 255;
                }
                if (i20 > 0) {
                    int b33 = 2;
                    int i22 = -i17 * 9;
                    String string23 = ((ChatLine)this.chatMessageList.get((int)i17)).message;
                    GuiIngame.drawRect(b33, i22 - 1, b33 + 320, i22 + 8, i20 / 2 << 24);
                    GL11.glEnable((int)3042);
                    fontRenderer8.drawStringWithShadow(string23, b33, i22, 0xFFFFFF + (i20 << 24));
                }
            }
            ++i17;
        }
        GL11.glPopMatrix();
        GL11.glEnable((int)3008);
        GL11.glDisable((int)3042);
    }

    private void renderPumpkinBlur(int i1, int i2) {
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)3008);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
        Tessellator tessellator3 = Tessellator.instance;
        tessellator3.startDrawingQuads();
        tessellator3.addVertexWithUV(0.0, i2, -90.0, 0.0, 1.0);
        tessellator3.addVertexWithUV(i1, i2, -90.0, 1.0, 1.0);
        tessellator3.addVertexWithUV(i1, 0.0, -90.0, 1.0, 0.0);
        tessellator3.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator3.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderVignette(float f1, int i2, int i3) {
        if ((f1 = 1.0f - f1) < 0.0f) {
            f1 = 0.0f;
        }
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(f1 - this.prevVignetteBrightness) * 0.01);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)0, (int)769);
        GL11.glColor4f((float)this.prevVignetteBrightness, (float)this.prevVignetteBrightness, (float)this.prevVignetteBrightness, (float)1.0f);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
        Tessellator tessellator4 = Tessellator.instance;
        tessellator4.startDrawingQuads();
        tessellator4.addVertexWithUV(0.0, i3, -90.0, 0.0, 1.0);
        tessellator4.addVertexWithUV(i2, i3, -90.0, 1.0, 1.0);
        tessellator4.addVertexWithUV(i2, 0.0, -90.0, 1.0, 0.0);
        tessellator4.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator4.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBlendFunc((int)770, (int)771);
    }

    private void renderPortalOverlay(float f1, int i2, int i3) {
        f1 *= f1;
        f1 *= f1;
        f1 = f1 * 0.8f + 0.2f;
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)f1);
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
        float f4 = (float)(Block.portal.blockIndexInTexture % 16) / 16.0f;
        float f5 = (float)(Block.portal.blockIndexInTexture / 16) / 16.0f;
        float f6 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16.0f;
        float f7 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16.0f;
        Tessellator tessellator8 = Tessellator.instance;
        tessellator8.startDrawingQuads();
        tessellator8.addVertexWithUV(0.0, i3, -90.0, f4, f7);
        tessellator8.addVertexWithUV(i2, i3, -90.0, f6, f7);
        tessellator8.addVertexWithUV(i2, 0.0, -90.0, f6, f5);
        tessellator8.addVertexWithUV(0.0, 0.0, -90.0, f4, f5);
        tessellator8.draw();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private void renderInventorySlot(int i1, int i2, int i3, float f4) {
        ItemStack itemStack5 = this.mc.thePlayer.inventory.mainInventory[i1];
        if (itemStack5 != null) {
            float f6 = (float)itemStack5.animationsToGo - f4;
            if (f6 > 0.0f) {
                GL11.glPushMatrix();
                float f7 = 1.0f + f6 / 5.0f;
                GL11.glTranslatef((float)(i2 + 8), (float)(i3 + 12), (float)0.0f);
                GL11.glScalef((float)(1.0f / f7), (float)((f7 + 1.0f) / 2.0f), (float)1.0f);
                GL11.glTranslatef((float)(-(i2 + 8)), (float)(-(i3 + 12)), (float)0.0f);
            }
            itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, itemStack5, i2, i3);
            if (f6 > 0.0f) {
                GL11.glPopMatrix();
            }
            itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, itemStack5, i2, i3);
        }
    }

    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        ++this.updateCounter;
        int i1 = 0;
        while (i1 < this.chatMessageList.size()) {
            ++((ChatLine)this.chatMessageList.get((int)i1)).updateCounter;
            ++i1;
        }
    }

    public void addChatMessage(String string1) {
        while (this.mc.fontRenderer.getStringWidth(string1) > 320) {
            int i2 = 1;
            while (i2 < string1.length() && this.mc.fontRenderer.getStringWidth(string1.substring(0, i2 + 1)) <= 320) {
                ++i2;
            }
            this.addChatMessage(string1.substring(0, i2));
            string1 = string1.substring(i2);
        }
        this.chatMessageList.add(0, new ChatLine(string1));
        while (this.chatMessageList.size() > 50) {
            this.chatMessageList.remove(this.chatMessageList.size() - 1);
        }
    }

    public void setRecordPlayingMessage(String string1) {
        this.recordPlaying = "Now playing: " + string1;
        this.recordPlayingUpFor = 60;
        this.field_22065_l = true;
    }

    public void func_22064_c(String string1) {
        StringTranslate stringTranslate2 = StringTranslate.getInstance();
        String string3 = stringTranslate2.translateKey(string1);
        this.addChatMessage(string3);
    }
}

