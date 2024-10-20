/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.PositionTexureVertex;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3D;

public class TexturedQuad {
    public PositionTexureVertex[] vertexPositions;
    public int nVertices = 0;
    private boolean invertNormal = false;

    public TexturedQuad(PositionTexureVertex[] positionTexureVertex1) {
        this.vertexPositions = positionTexureVertex1;
        this.nVertices = positionTexureVertex1.length;
    }

    public TexturedQuad(PositionTexureVertex[] positionTexureVertex1, int i2, int i3, int i4, int i5) {
        this(positionTexureVertex1);
        float f6 = 0.0015625f;
        float f7 = 0.003125f;
        positionTexureVertex1[0] = positionTexureVertex1[0].setTexturePosition((float)i4 / 64.0f - f6, (float)i3 / 32.0f + f7);
        positionTexureVertex1[1] = positionTexureVertex1[1].setTexturePosition((float)i2 / 64.0f + f6, (float)i3 / 32.0f + f7);
        positionTexureVertex1[2] = positionTexureVertex1[2].setTexturePosition((float)i2 / 64.0f + f6, (float)i5 / 32.0f - f7);
        positionTexureVertex1[3] = positionTexureVertex1[3].setTexturePosition((float)i4 / 64.0f - f6, (float)i5 / 32.0f - f7);
    }

    public void flipFace() {
        PositionTexureVertex[] positionTexureVertex1 = new PositionTexureVertex[this.vertexPositions.length];
        int i2 = 0;
        while (i2 < this.vertexPositions.length) {
            positionTexureVertex1[i2] = this.vertexPositions[this.vertexPositions.length - i2 - 1];
            ++i2;
        }
        this.vertexPositions = positionTexureVertex1;
    }

    public void draw(Tessellator tessellator1, float f2) {
        Vec3D vec3D3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
        Vec3D vec3D4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
        Vec3D vec3D5 = vec3D4.crossProduct(vec3D3).normalize();
        tessellator1.startDrawingQuads();
        if (this.invertNormal) {
            tessellator1.setNormal(-((float)vec3D5.xCoord), -((float)vec3D5.yCoord), -((float)vec3D5.zCoord));
        } else {
            tessellator1.setNormal((float)vec3D5.xCoord, (float)vec3D5.yCoord, (float)vec3D5.zCoord);
        }
        int i6 = 0;
        while (i6 < 4) {
            PositionTexureVertex positionTexureVertex7 = this.vertexPositions[i6];
            tessellator1.addVertexWithUV((float)positionTexureVertex7.vector3D.xCoord * f2, (float)positionTexureVertex7.vector3D.yCoord * f2, (float)positionTexureVertex7.vector3D.zCoord * f2, positionTexureVertex7.texturePositionX, positionTexureVertex7.texturePositionY);
            ++i6;
        }
        tessellator1.draw();
    }
}

