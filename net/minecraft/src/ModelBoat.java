/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.ModelBase;
import net.minecraft.src.ModelRenderer;

public class ModelBoat
extends ModelBase {
    public ModelRenderer[] boatSides = new ModelRenderer[5];

    public ModelBoat() {
        this.boatSides[0] = new ModelRenderer(0, 8);
        this.boatSides[1] = new ModelRenderer(0, 0);
        this.boatSides[2] = new ModelRenderer(0, 0);
        this.boatSides[3] = new ModelRenderer(0, 0);
        this.boatSides[4] = new ModelRenderer(0, 0);
        int b1 = 24;
        int b2 = 6;
        int b3 = 20;
        int b4 = 4;
        this.boatSides[0].addBox(-b1 / 2, -b3 / 2 + 2, -3.0f, b1, b3 - 4, 4, 0.0f);
        this.boatSides[0].setPosition(0.0f, 0 + b4, 0.0f);
        this.boatSides[1].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.boatSides[1].setPosition(-b1 / 2 + 1, 0 + b4, 0.0f);
        this.boatSides[2].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.boatSides[2].setPosition(b1 / 2 - 1, 0 + b4, 0.0f);
        this.boatSides[3].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.boatSides[3].setPosition(0.0f, 0 + b4, -b3 / 2 + 1);
        this.boatSides[4].addBox(-b1 / 2 + 2, -b2 - 1, -1.0f, b1 - 4, b2, 2, 0.0f);
        this.boatSides[4].setPosition(0.0f, 0 + b4, b3 / 2 - 1);
        this.boatSides[0].rotateAngleX = 1.5707964f;
        this.boatSides[1].rotateAngleY = 4.712389f;
        this.boatSides[2].rotateAngleY = 1.5707964f;
        this.boatSides[3].rotateAngleY = (float)Math.PI;
    }

    @Override
    public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
        int i7 = 0;
        while (i7 < 5) {
            this.boatSides[i7].render(f6);
            ++i7;
        }
    }

    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
    }
}

