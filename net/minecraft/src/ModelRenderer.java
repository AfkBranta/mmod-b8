/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.GLAllocation;
import net.minecraft.src.PositionTexureVertex;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturedQuad;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
    private PositionTexureVertex[] corners;
    private TexturedQuad[] faces;
    private int textureOffsetX;
    private int textureOffsetY;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled = false;
    private int displayList = 0;
    public boolean mirror = false;
    public boolean showModel = true;
    public boolean field_1402_i = false;

    public ModelRenderer(int i1, int i2) {
        this.textureOffsetX = i1;
        this.textureOffsetY = i2;
    }

    public void addBox(float f1, float f2, float f3, int i4, int i5, int i6) {
        this.addBox(f1, f2, f3, i4, i5, i6, 0.0f);
    }

    public void addBox(float f1, float f2, float f3, int i4, int i5, int i6, float f7) {
        this.corners = new PositionTexureVertex[8];
        this.faces = new TexturedQuad[6];
        float f8 = f1 + (float)i4;
        float f9 = f2 + (float)i5;
        float f10 = f3 + (float)i6;
        f1 -= f7;
        f2 -= f7;
        f3 -= f7;
        f8 += f7;
        f9 += f7;
        f10 += f7;
        if (this.mirror) {
            float f11 = f8;
            f8 = f1;
            f1 = f11;
        }
        PositionTexureVertex positionTexureVertex20 = new PositionTexureVertex(f1, f2, f3, 0.0f, 0.0f);
        PositionTexureVertex positionTexureVertex12 = new PositionTexureVertex(f8, f2, f3, 0.0f, 8.0f);
        PositionTexureVertex positionTexureVertex13 = new PositionTexureVertex(f8, f9, f3, 8.0f, 8.0f);
        PositionTexureVertex positionTexureVertex14 = new PositionTexureVertex(f1, f9, f3, 8.0f, 0.0f);
        PositionTexureVertex positionTexureVertex15 = new PositionTexureVertex(f1, f2, f10, 0.0f, 0.0f);
        PositionTexureVertex positionTexureVertex16 = new PositionTexureVertex(f8, f2, f10, 0.0f, 8.0f);
        PositionTexureVertex positionTexureVertex17 = new PositionTexureVertex(f8, f9, f10, 8.0f, 8.0f);
        PositionTexureVertex positionTexureVertex18 = new PositionTexureVertex(f1, f9, f10, 8.0f, 0.0f);
        this.corners[0] = positionTexureVertex20;
        this.corners[1] = positionTexureVertex12;
        this.corners[2] = positionTexureVertex13;
        this.corners[3] = positionTexureVertex14;
        this.corners[4] = positionTexureVertex15;
        this.corners[5] = positionTexureVertex16;
        this.corners[6] = positionTexureVertex17;
        this.corners[7] = positionTexureVertex18;
        this.faces[0] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex16, positionTexureVertex12, positionTexureVertex13, positionTexureVertex17}, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4 + i6, this.textureOffsetY + i6 + i5);
        this.faces[1] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex20, positionTexureVertex15, positionTexureVertex18, positionTexureVertex14}, this.textureOffsetX + 0, this.textureOffsetY + i6, this.textureOffsetX + i6, this.textureOffsetY + i6 + i5);
        this.faces[2] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex16, positionTexureVertex15, positionTexureVertex20, positionTexureVertex12}, this.textureOffsetX + i6, this.textureOffsetY + 0, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6);
        this.faces[3] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex13, positionTexureVertex14, positionTexureVertex18, positionTexureVertex17}, this.textureOffsetX + i6 + i4, this.textureOffsetY + 0, this.textureOffsetX + i6 + i4 + i4, this.textureOffsetY + i6);
        this.faces[4] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex12, positionTexureVertex20, positionTexureVertex14, positionTexureVertex13}, this.textureOffsetX + i6, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6 + i5);
        this.faces[5] = new TexturedQuad(new PositionTexureVertex[]{positionTexureVertex15, positionTexureVertex16, positionTexureVertex17, positionTexureVertex18}, this.textureOffsetX + i6 + i4 + i6, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4 + i6 + i4, this.textureOffsetY + i6 + i5);
        if (this.mirror) {
            int i19 = 0;
            while (i19 < this.faces.length) {
                this.faces[i19].flipFace();
                ++i19;
            }
        }
    }

    public void setPosition(float f1, float f2, float f3) {
        this.offsetX = f1;
        this.offsetY = f2;
        this.offsetZ = f3;
    }

    public void render(float f1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(f1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.offsetX == 0.0f && this.offsetY == 0.0f && this.offsetZ == 0.0f) {
                    GL11.glCallList((int)this.displayList);
                } else {
                    GL11.glTranslatef((float)(this.offsetX * f1), (float)(this.offsetY * f1), (float)(this.offsetZ * f1));
                    GL11.glCallList((int)this.displayList);
                    GL11.glTranslatef((float)(-this.offsetX * f1), (float)(-this.offsetY * f1), (float)(-this.offsetZ * f1));
                }
            } else {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.offsetX * f1), (float)(this.offsetY * f1), (float)(this.offsetZ * f1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
                GL11.glCallList((int)this.displayList);
                GL11.glPopMatrix();
            }
        }
    }

    public void func_25122_b(float f1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(f1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.offsetX * f1), (float)(this.offsetY * f1), (float)(this.offsetZ * f1));
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
            }
            GL11.glCallList((int)this.displayList);
            GL11.glPopMatrix();
        }
    }

    public void postRender(float f1) {
        if (!this.field_1402_i && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(f1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.offsetX != 0.0f || this.offsetY != 0.0f || this.offsetZ != 0.0f) {
                    GL11.glTranslatef((float)(this.offsetX * f1), (float)(this.offsetY * f1), (float)(this.offsetZ * f1));
                }
            } else {
                GL11.glTranslatef((float)(this.offsetX * f1), (float)(this.offsetY * f1), (float)(this.offsetZ * f1));
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleZ * 57.295776f), (float)0.0f, (float)0.0f, (float)1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleY * 57.295776f), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef((float)(this.rotateAngleX * 57.295776f), (float)1.0f, (float)0.0f, (float)0.0f);
                }
            }
        }
    }

    private void compileDisplayList(float f1) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList((int)this.displayList, (int)4864);
        Tessellator tessellator2 = Tessellator.instance;
        int i3 = 0;
        while (i3 < this.faces.length) {
            this.faces[i3].draw(tessellator2, f1);
            ++i3;
        }
        GL11.glEndList();
        this.compiled = true;
    }
}

