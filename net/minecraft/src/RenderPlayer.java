/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventRenderNametag;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderPlayer
extends RenderLiving {
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String[] armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

    public RenderPlayer() {
        super(new ModelBiped(0.0f), 0.5f);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0f);
        this.modelArmor = new ModelBiped(0.5f);
    }

    protected boolean setArmorModel(EntityPlayer entityPlayer1, int i2, float f3) {
        Item item5;
        ItemStack itemStack4 = entityPlayer1.inventory.armorItemInSlot(3 - i2);
        if (itemStack4 != null && (item5 = itemStack4.getItem()) instanceof ItemArmor) {
            ItemArmor itemArmor6 = (ItemArmor)item5;
            this.loadTexture("/armor/" + armorFilenamePrefix[itemArmor6.renderIndex] + "_" + (i2 == 2 ? 2 : 1) + ".png");
            ModelBiped modelBiped7 = i2 == 2 ? this.modelArmor : this.modelArmorChestplate;
            modelBiped7.bipedHead.showModel = i2 == 0;
            modelBiped7.bipedHeadwear.showModel = i2 == 0;
            modelBiped7.bipedBody.showModel = i2 == 1 || i2 == 2;
            modelBiped7.bipedRightArm.showModel = i2 == 1;
            modelBiped7.bipedLeftArm.showModel = i2 == 1;
            modelBiped7.bipedRightLeg.showModel = i2 == 2 || i2 == 3;
            modelBiped7.bipedLeftLeg.showModel = i2 == 2 || i2 == 3;
            this.setRenderPassModel(modelBiped7);
            return true;
        }
        return false;
    }

    public void func_188_a(EntityPlayer entityPlayer1, double d2, double d4, double d6, float f8, float f9) {
        ItemStack itemStack10 = entityPlayer1.inventory.getCurrentItem();
        this.modelBipedMain.field_1278_i = itemStack10 != null;
        this.modelArmor.field_1278_i = this.modelBipedMain.field_1278_i;
        this.modelArmorChestplate.field_1278_i = this.modelBipedMain.field_1278_i;
        this.modelArmor.isSneak = this.modelBipedMain.isSneak = entityPlayer1.isSneaking();
        this.modelArmorChestplate.isSneak = this.modelBipedMain.isSneak;
        double d11 = d4 - (double)entityPlayer1.yOffset;
        if (entityPlayer1.isSneaking()) {
            d11 -= 0.125;
        }
        super.doRenderLiving(entityPlayer1, d2, d11, d6, f8, f9);
        this.modelBipedMain.isSneak = false;
        this.modelArmor.isSneak = false;
        this.modelArmorChestplate.isSneak = false;
        this.modelBipedMain.field_1278_i = false;
        this.modelArmor.field_1278_i = false;
        this.modelArmorChestplate.field_1278_i = false;
    }

    protected void renderName(EntityPlayer entityPlayer1, double d2, double d4, double d6) {
        if (Minecraft.isGuiEnabled() && entityPlayer1 != this.renderManager.livingPlayer) {
            float f8 = 1.6f;
            float f9 = 0.016666668f * f8;
            float f10 = entityPlayer1.getDistanceToEntity(this.renderManager.livingPlayer);
            float f11 = entityPlayer1.isSneaking() ? 32.0f : 64.0f;
            EventRenderNametag event = new EventRenderNametag();
            Client.onEvent(event);
            if (event.getCancelled()) {
                return;
            }
            if (f10 < f11) {
                String string12 = entityPlayer1.username;
                if (!entityPlayer1.isSneaking()) {
                    this.renderLivingLabel(entityPlayer1, string12, d2, d4, d6, 64);
                } else {
                    FontRenderer fontRenderer13 = this.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)((float)d2 + 0.0f), (float)((float)d4 + 2.3f), (float)((float)d6));
                    GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
                    GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glScalef((float)(-f9), (float)(-f9), (float)f9);
                    GL11.glDisable((int)2896);
                    GL11.glTranslatef((float)0.0f, (float)(0.25f / f9), (float)0.0f);
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    Tessellator tessellator14 = Tessellator.instance;
                    GL11.glDisable((int)3553);
                    tessellator14.startDrawingQuads();
                    int i15 = fontRenderer13.getStringWidth(string12) / 2;
                    tessellator14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
                    tessellator14.addVertex(-i15 - 1, -1.0, 0.0);
                    tessellator14.addVertex(-i15 - 1, 8.0, 0.0);
                    tessellator14.addVertex(i15 + 1, 8.0, 0.0);
                    tessellator14.addVertex(i15 + 1, -1.0, 0.0);
                    tessellator14.draw();
                    GL11.glEnable((int)3553);
                    GL11.glDepthMask((boolean)true);
                    fontRenderer13.drawString(string12, -fontRenderer13.getStringWidth(string12) / 2, 0.0f, 0x20FFFFFF);
                    GL11.glEnable((int)2896);
                    GL11.glDisable((int)3042);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    protected void renderSpecials(EntityPlayer entityPlayer1, float f2) {
        ItemStack itemStack21;
        float f5;
        ItemStack itemStack3 = entityPlayer1.inventory.armorItemInSlot(3);
        if (itemStack3 != null && itemStack3.getItem().shiftedIndex < 256) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625f);
            if (RenderBlocks.renderItemIn3d(Block.blocksList[itemStack3.itemID].getRenderType())) {
                float f4 = 0.625f;
                GL11.glTranslatef((float)0.0f, (float)-0.25f, (float)0.0f);
                GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)f4, (float)(-f4), (float)f4);
            }
            this.renderManager.itemRenderer.renderItem(itemStack3);
            GL11.glPopMatrix();
        }
        if (entityPlayer1.username.equals("deadmau5") && this.loadDownloadableImageTexture(entityPlayer1.skinUrl, null)) {
            int i19 = 0;
            while (i19 < 2) {
                f5 = entityPlayer1.prevRotationYaw + (entityPlayer1.rotationYaw - entityPlayer1.prevRotationYaw) * f2 - (entityPlayer1.prevRenderYawOffset + (entityPlayer1.renderYawOffset - entityPlayer1.prevRenderYawOffset) * f2);
                float f6 = entityPlayer1.prevRotationPitch + (entityPlayer1.rotationPitch - entityPlayer1.prevRotationPitch) * f2;
                GL11.glPushMatrix();
                GL11.glRotatef((float)f5, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)f6, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)(0.375f * (float)(i19 * 2 - 1)), (float)0.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)-0.375f, (float)0.0f);
                GL11.glRotatef((float)(-f6), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(-f5), (float)0.0f, (float)1.0f, (float)0.0f);
                float f7 = 1.3333334f;
                GL11.glScalef((float)f7, (float)f7, (float)f7);
                this.modelBipedMain.renderEars(0.0625f);
                GL11.glPopMatrix();
                ++i19;
            }
        }
        if (this.loadDownloadableImageTexture(entityPlayer1.playerCloakUrl, null)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)0.125f);
            double d20 = entityPlayer1.field_20066_r + (entityPlayer1.field_20063_u - entityPlayer1.field_20066_r) * (double)f2 - (entityPlayer1.prevPosX + (entityPlayer1.posX - entityPlayer1.prevPosX) * (double)f2);
            double d22 = entityPlayer1.field_20065_s + (entityPlayer1.field_20062_v - entityPlayer1.field_20065_s) * (double)f2 - (entityPlayer1.prevPosY + (entityPlayer1.posY - entityPlayer1.prevPosY) * (double)f2);
            double d8 = entityPlayer1.field_20064_t + (entityPlayer1.field_20061_w - entityPlayer1.field_20064_t) * (double)f2 - (entityPlayer1.prevPosZ + (entityPlayer1.posZ - entityPlayer1.prevPosZ) * (double)f2);
            float f10 = entityPlayer1.prevRenderYawOffset + (entityPlayer1.renderYawOffset - entityPlayer1.prevRenderYawOffset) * f2;
            double d11 = MathHelper.sin(f10 * (float)Math.PI / 180.0f);
            double d13 = -MathHelper.cos(f10 * (float)Math.PI / 180.0f);
            float f15 = (float)d22 * 10.0f;
            if (f15 < -6.0f) {
                f15 = -6.0f;
            }
            if (f15 > 32.0f) {
                f15 = 32.0f;
            }
            float f16 = (float)(d20 * d11 + d8 * d13) * 100.0f;
            float f17 = (float)(d20 * d13 - d8 * d11) * 100.0f;
            if (f16 < 0.0f) {
                f16 = 0.0f;
            }
            float f18 = entityPlayer1.field_775_e + (entityPlayer1.field_774_f - entityPlayer1.field_775_e) * f2;
            GL11.glRotatef((float)(6.0f + f16 / 2.0f + (f15 += MathHelper.sin((entityPlayer1.prevDistanceWalkedModified + (entityPlayer1.distanceWalkedModified - entityPlayer1.prevDistanceWalkedModified) * f2) * 6.0f) * 32.0f * f18)), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(f17 / 2.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(-f17 / 2.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            this.modelBipedMain.renderCloak(0.0625f);
            GL11.glPopMatrix();
        }
        if ((itemStack21 = entityPlayer1.inventory.getCurrentItem()) != null) {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625f);
            GL11.glTranslatef((float)-0.0625f, (float)0.4375f, (float)0.0625f);
            if (entityPlayer1.fishEntity != null) {
                itemStack21 = new ItemStack(Item.stick);
            }
            if (itemStack21.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack21.itemID].getRenderType())) {
                f5 = 0.5f;
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)-0.3125f);
                GL11.glRotatef((float)20.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glScalef((float)(f5 *= 0.75f), (float)(-f5), (float)f5);
            } else if (Item.itemsList[itemStack21.itemID].isFull3D()) {
                f5 = 0.625f;
                if (Item.itemsList[itemStack21.itemID].shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    GL11.glTranslatef((float)0.0f, (float)-0.125f, (float)0.0f);
                }
                GL11.glTranslatef((float)0.0f, (float)0.1875f, (float)0.0f);
                GL11.glScalef((float)f5, (float)(-f5), (float)f5);
                GL11.glRotatef((float)-100.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                f5 = 0.375f;
                GL11.glTranslatef((float)0.25f, (float)0.1875f, (float)-0.1875f);
                GL11.glScalef((float)f5, (float)f5, (float)f5);
                GL11.glRotatef((float)60.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glRotatef((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            }
            this.renderManager.itemRenderer.renderItem(itemStack21);
            GL11.glPopMatrix();
        }
    }

    protected void func_186_b(EntityPlayer entityPlayer1, float f2) {
        float f3 = 0.9375f;
        GL11.glScalef((float)f3, (float)f3, (float)f3);
    }

    public void drawFirstPersonHand() {
        this.modelBipedMain.onGround = 0.0f;
        this.modelBipedMain.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.modelBipedMain.bipedRightArm.render(0.0625f);
    }

    protected void func_22016_b(EntityPlayer entityPlayer1, double d2, double d4, double d6) {
        if (entityPlayer1.isEntityAlive() && entityPlayer1.isPlayerSleeping()) {
            super.func_22012_b(entityPlayer1, d2 + (double)entityPlayer1.field_22063_x, d4 + (double)entityPlayer1.field_22062_y, d6 + (double)entityPlayer1.field_22061_z);
        } else {
            super.func_22012_b(entityPlayer1, d2, d4, d6);
        }
    }

    protected void func_22017_a(EntityPlayer entityPlayer1, float f2, float f3, float f4) {
        if (entityPlayer1.isEntityAlive() && entityPlayer1.isPlayerSleeping()) {
            GL11.glRotatef((float)entityPlayer1.getBedOrientationInDegrees(), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.func_172_a(entityPlayer1), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)270.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        } else {
            super.func_21004_a(entityPlayer1, f2, f3, f4);
        }
    }

    @Override
    protected void passSpecialRender(EntityLiving entityLiving1, double d2, double d4, double d6) {
        this.renderName((EntityPlayer)entityLiving1, d2, d4, d6);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
        this.func_186_b((EntityPlayer)entityLiving1, f2);
    }

    @Override
    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return this.setArmorModel((EntityPlayer)entityLiving1, i2, f3);
    }

    @Override
    protected void renderEquippedItems(EntityLiving entityLiving1, float f2) {
        this.renderSpecials((EntityPlayer)entityLiving1, f2);
    }

    @Override
    protected void func_21004_a(EntityLiving entityLiving1, float f2, float f3, float f4) {
        this.func_22017_a((EntityPlayer)entityLiving1, f2, f3, f4);
    }

    @Override
    protected void func_22012_b(EntityLiving entityLiving1, double d2, double d4, double d6) {
        this.func_22016_b((EntityPlayer)entityLiving1, d2, d4, d6);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        this.func_188_a((EntityPlayer)entityLiving1, d2, d4, d6, f8, f9);
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.func_188_a((EntityPlayer)entity1, d2, d4, d6, f8, f9);
    }
}

