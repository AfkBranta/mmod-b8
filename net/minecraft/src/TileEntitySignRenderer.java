/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.SignModel;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRenderer
extends TileEntitySpecialRenderer {
    private SignModel signModel = new SignModel();

    public void renderTileEntitySignAt(TileEntitySign tileEntitySign1, double d2, double d4, double d6, float f8) {
        float f12;
        Block block9 = tileEntitySign1.getBlockType();
        GL11.glPushMatrix();
        float f10 = 0.6666667f;
        if (block9 == Block.signPost) {
            GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d4 + 0.75f * f10), (float)((float)d6 + 0.5f));
            float f11 = (float)(tileEntitySign1.getBlockMetadata() * 360) / 16.0f;
            GL11.glRotatef((float)(-f11), (float)0.0f, (float)1.0f, (float)0.0f);
            this.signModel.field_1345_b.showModel = true;
        } else {
            int i16 = tileEntitySign1.getBlockMetadata();
            f12 = 0.0f;
            if (i16 == 2) {
                f12 = 180.0f;
            }
            if (i16 == 4) {
                f12 = 90.0f;
            }
            if (i16 == 5) {
                f12 = -90.0f;
            }
            GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d4 + 0.75f * f10), (float)((float)d6 + 0.5f));
            GL11.glRotatef((float)(-f12), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.3125f, (float)-0.4375f);
            this.signModel.field_1345_b.showModel = false;
        }
        this.bindTextureByName("/item/sign.png");
        GL11.glPushMatrix();
        GL11.glScalef((float)f10, (float)(-f10), (float)(-f10));
        this.signModel.func_887_a();
        GL11.glPopMatrix();
        FontRenderer fontRenderer17 = this.getFontRenderer();
        f12 = 0.016666668f * f10;
        GL11.glTranslatef((float)0.0f, (float)(0.5f * f10), (float)(0.07f * f10));
        GL11.glScalef((float)f12, (float)(-f12), (float)f12);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)(-1.0f * f12));
        GL11.glDepthMask((boolean)false);
        int b13 = 0;
        int i14 = 0;
        while (i14 < tileEntitySign1.signText.length) {
            String string15 = tileEntitySign1.signText[i14];
            if (i14 == tileEntitySign1.lineBeingEdited) {
                string15 = "> " + string15 + " <";
                fontRenderer17.drawString(string15, -fontRenderer17.getStringWidth(string15) / 2, i14 * 10 - tileEntitySign1.signText.length * 5, b13);
            } else {
                fontRenderer17.drawString(string15, -fontRenderer17.getStringWidth(string15) / 2, i14 * 10 - tileEntitySign1.signText.length * 5, b13);
            }
            ++i14;
        }
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {
        this.renderTileEntitySignAt((TileEntitySign)tileEntity1, d2, d4, d6, f8);
    }
}

