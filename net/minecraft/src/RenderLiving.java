/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.Render;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderLiving
extends Render {
    protected ModelBase mainModel;
    protected ModelBase renderPassModel;

    public RenderLiving(ModelBase modelBase1, float f2) {
        this.mainModel = modelBase1;
        this.shadowSize = f2;
    }

    public void setRenderPassModel(ModelBase modelBase1) {
        this.renderPassModel = modelBase1;
    }

    public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        GL11.glDisable((int)2884);
        this.mainModel.onGround = this.func_167_c(entityLiving1, f9);
        this.mainModel.isRiding = entityLiving1.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        try {
            float f10 = entityLiving1.prevRenderYawOffset + (entityLiving1.renderYawOffset - entityLiving1.prevRenderYawOffset) * f9;
            float f11 = entityLiving1.prevRotationYaw + (entityLiving1.rotationYaw - entityLiving1.prevRotationYaw) * f9;
            float f12 = entityLiving1.prevRotationPitch + (entityLiving1.rotationPitch - entityLiving1.prevRotationPitch) * f9;
            this.func_22012_b(entityLiving1, d2, d4, d6);
            float f13 = this.func_170_d(entityLiving1, f9);
            this.func_21004_a(entityLiving1, f13, f10, f9);
            float f14 = 0.0625f;
            GL11.glEnable((int)32826);
            GL11.glScalef((float)-1.0f, (float)-1.0f, (float)1.0f);
            this.preRenderCallback(entityLiving1, f9);
            GL11.glTranslatef((float)0.0f, (float)(-24.0f * f14 - 0.0078125f), (float)0.0f);
            float f15 = entityLiving1.field_705_Q + (entityLiving1.field_704_R - entityLiving1.field_705_Q) * f9;
            float f16 = entityLiving1.field_703_S - entityLiving1.field_704_R * (1.0f - f9);
            if (f15 > 1.0f) {
                f15 = 1.0f;
            }
            this.loadDownloadableImageTexture(entityLiving1.skinUrl, entityLiving1.getEntityTexture());
            GL11.glEnable((int)3008);
            this.mainModel.func_25103_a(entityLiving1, f16, f15, f9);
            this.mainModel.render(f16, f15, f13, f11 - f10, f12, f14);
            int i17 = 0;
            while (i17 < 4) {
                if (this.shouldRenderPass(entityLiving1, i17, f9)) {
                    this.renderPassModel.render(f16, f15, f13, f11 - f10, f12, f14);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)3008);
                }
                ++i17;
            }
            this.renderEquippedItems(entityLiving1, f9);
            float f25 = entityLiving1.getEntityBrightness(f9);
            int i18 = this.getColorMultiplier(entityLiving1, f25, f9);
            if ((i18 >> 24 & 0xFF) > 0 || entityLiving1.hurtTime > 0 || entityLiving1.deathTime > 0) {
                GL11.glDisable((int)3553);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glDepthFunc((int)514);
                if (entityLiving1.hurtTime > 0 || entityLiving1.deathTime > 0) {
                    GL11.glColor4f((float)f25, (float)0.0f, (float)0.0f, (float)0.4f);
                    this.mainModel.render(f16, f15, f13, f11 - f10, f12, f14);
                    int i19 = 0;
                    while (i19 < 4) {
                        if (this.shouldRenderPass(entityLiving1, i19, f9)) {
                            GL11.glColor4f((float)f25, (float)0.0f, (float)0.0f, (float)0.4f);
                            this.renderPassModel.render(f16, f15, f13, f11 - f10, f12, f14);
                        }
                        ++i19;
                    }
                }
                if ((i18 >> 24 & 0xFF) > 0) {
                    float f26 = (float)(i18 >> 16 & 0xFF) / 255.0f;
                    float f20 = (float)(i18 >> 8 & 0xFF) / 255.0f;
                    float f21 = (float)(i18 & 0xFF) / 255.0f;
                    float f22 = (float)(i18 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f((float)f26, (float)f20, (float)f21, (float)f22);
                    this.mainModel.render(f16, f15, f13, f11 - f10, f12, f14);
                    int i23 = 0;
                    while (i23 < 4) {
                        if (this.shouldRenderPass(entityLiving1, i23, f9)) {
                            GL11.glColor4f((float)f26, (float)f20, (float)f21, (float)f22);
                            this.renderPassModel.render(f16, f15, f13, f11 - f10, f12, f14);
                        }
                        ++i23;
                    }
                }
                GL11.glDepthFunc((int)515);
                GL11.glDisable((int)3042);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3553);
            }
            GL11.glDisable((int)32826);
        }
        catch (Exception exception24) {
            exception24.printStackTrace();
        }
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
        this.passSpecialRender(entityLiving1, d2, d4, d6);
    }

    protected void func_22012_b(EntityLiving entityLiving1, double d2, double d4, double d6) {
        GL11.glTranslatef((float)((float)d2), (float)((float)d4), (float)((float)d6));
    }

    protected void func_21004_a(EntityLiving entityLiving1, float f2, float f3, float f4) {
        GL11.glRotatef((float)(180.0f - f3), (float)0.0f, (float)1.0f, (float)0.0f);
        if (entityLiving1.deathTime > 0) {
            float f5 = ((float)entityLiving1.deathTime + f4 - 1.0f) / 20.0f * 1.6f;
            if ((f5 = MathHelper.sqrt_float(f5)) > 1.0f) {
                f5 = 1.0f;
            }
            GL11.glRotatef((float)(f5 * this.func_172_a(entityLiving1)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
    }

    protected float func_167_c(EntityLiving entityLiving1, float f2) {
        return entityLiving1.getSwingProgress(f2);
    }

    protected float func_170_d(EntityLiving entityLiving1, float f2) {
        return (float)entityLiving1.ticksExisted + f2;
    }

    protected void renderEquippedItems(EntityLiving entityLiving1, float f2) {
    }

    protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
        return false;
    }

    protected float func_172_a(EntityLiving entityLiving1) {
        return 90.0f;
    }

    protected int getColorMultiplier(EntityLiving entityLiving1, float f2, float f3) {
        return 0;
    }

    protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
    }

    protected void passSpecialRender(EntityLiving entityLiving1, double d2, double d4, double d6) {
        if (Minecraft.isDebugInfoEnabled()) {
            this.renderLivingLabel(entityLiving1, Integer.toString(entityLiving1.entityId), d2, d4, d6, 64);
        }
    }

    protected void renderLivingLabel(EntityLiving entityLiving1, String string2, double d3, double d5, double d7, int i9) {
        float f10 = entityLiving1.getDistanceToEntity(this.renderManager.livingPlayer);
        if (f10 <= (float)i9) {
            FontRenderer fontRenderer11 = this.getFontRendererFromRenderManager();
            float f12 = 1.6f;
            float f13 = 0.016666668f * f12;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)((float)d3 + 0.0f), (float)((float)d5 + 2.3f), (float)((float)d7));
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)this.renderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(-f13), (float)(-f13), (float)f13);
            GL11.glDisable((int)2896);
            GL11.glDepthMask((boolean)false);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            Tessellator tessellator14 = Tessellator.instance;
            int b15 = 0;
            if (string2.equals("deadmau5")) {
                b15 = -10;
            }
            GL11.glDisable((int)3553);
            tessellator14.startDrawingQuads();
            int i16 = fontRenderer11.getStringWidth(string2) / 2;
            tessellator14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
            tessellator14.addVertex(-i16 - 1, -1 + b15, 0.0);
            tessellator14.addVertex(-i16 - 1, 8 + b15, 0.0);
            tessellator14.addVertex(i16 + 1, 8 + b15, 0.0);
            tessellator14.addVertex(i16 + 1, -1 + b15, 0.0);
            tessellator14.draw();
            GL11.glEnable((int)3553);
            fontRenderer11.drawString(string2, -fontRenderer11.getStringWidth(string2) / 2, b15, 0x20FFFFFF);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            fontRenderer11.drawString(string2, -fontRenderer11.getStringWidth(string2) / 2, b15, -1);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        this.doRenderLiving((EntityLiving)entity1, d2, d4, d6, f8, f9);
    }
}

