/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelGhast
extends ModelBase {
    ModelRenderer body;
    ModelRenderer[] tentacles = new ModelRenderer[9];

    public ModelGhast() {
        int b1 = -16;
        this.body = new ModelRenderer(0, 0);
        this.body.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.body.offsetY += (float)(24 + b1);
        Random random2 = new Random(1660L);
        int i3 = 0;
        while (i3 < this.tentacles.length) {
            this.tentacles[i3] = new ModelRenderer(0, 0);
            float f4 = (((float)(i3 % 3) - (float)(i3 / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float f5 = ((float)(i3 / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int i6 = random2.nextInt(7) + 8;
            this.tentacles[i3].addBox(-1.0f, 0.0f, -1.0f, 2, i6, 2);
            this.tentacles[i3].offsetX = f4;
            this.tentacles[i3].offsetZ = f5;
            this.tentacles[i3].offsetY = 31 + b1;
            ++i3;
        }
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
        int i7 = 0;
        while (i7 < this.tentacles.length) {
            this.tentacles[i7].rotateAngleX = 0.2f * MathHelper.sin(f3 * 0.3f + (float)i7) + 0.4f;
            ++i7;
        }
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.setRotationAngles(f1, f2, f3, f4, f5, f6);
        this.body.render(f6);
        int i7 = 0;
        while (i7 < this.tentacles.length) {
            this.tentacles[i7].render(f6);
            ++i7;
        }
    }
}

