/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Item;
import net.minecraft.src.TextureFX;

public class TextureWatchFX
extends TextureFX {
    private Minecraft field_4225_g;
    private int[] field_4224_h = new int[256];
    private int[] field_4223_i = new int[256];
    private double field_4222_j;
    private double field_4221_k;

    public TextureWatchFX(Minecraft minecraft1) {
        super(Item.pocketSundial.getIconIndex(null));
        this.field_4225_g = minecraft1;
        this.tileImage = 1;
        try {
            BufferedImage bufferedImage2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int i3 = this.iconIndex % 16 * 16;
            int i4 = this.iconIndex / 16 * 16;
            bufferedImage2.getRGB(i3, i4, 16, 16, this.field_4224_h, 0, 16);
            bufferedImage2 = ImageIO.read(Minecraft.class.getResource("/misc/dial.png"));
            bufferedImage2.getRGB(0, 0, 16, 16, this.field_4223_i, 0, 16);
        }
        catch (IOException iOException5) {
            iOException5.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        double d1 = 0.0;
        if (this.field_4225_g.theWorld != null && this.field_4225_g.thePlayer != null) {
            float f3 = this.field_4225_g.theWorld.getCelestialAngle(1.0f);
            d1 = -f3 * (float)Math.PI * 2.0f;
            if (this.field_4225_g.theWorld.worldProvider.field_4220_c) {
                d1 = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        double d22 = d1 - this.field_4222_j;
        while (d22 < -Math.PI) {
            d22 += Math.PI * 2;
        }
        while (d22 >= Math.PI) {
            d22 -= Math.PI * 2;
        }
        if (d22 < -1.0) {
            d22 = -1.0;
        }
        if (d22 > 1.0) {
            d22 = 1.0;
        }
        this.field_4221_k += d22 * 0.1;
        this.field_4221_k *= 0.8;
        this.field_4222_j += this.field_4221_k;
        double d5 = Math.sin(this.field_4222_j);
        double d7 = Math.cos(this.field_4222_j);
        int i9 = 0;
        while (i9 < 256) {
            int i10 = this.field_4224_h[i9] >> 24 & 0xFF;
            int i11 = this.field_4224_h[i9] >> 16 & 0xFF;
            int i12 = this.field_4224_h[i9] >> 8 & 0xFF;
            int i13 = this.field_4224_h[i9] >> 0 & 0xFF;
            if (i11 == i13 && i12 == 0 && i13 > 0) {
                double d14 = -((double)(i9 % 16) / 15.0 - 0.5);
                double d16 = (double)(i9 / 16) / 15.0 - 0.5;
                int i18 = i11;
                int i19 = (int)((d14 * d7 + d16 * d5 + 0.5) * 16.0);
                int i20 = (int)((d16 * d7 - d14 * d5 + 0.5) * 16.0);
                int i21 = (i19 & 0xF) + (i20 & 0xF) * 16;
                i10 = this.field_4223_i[i21] >> 24 & 0xFF;
                i11 = (this.field_4223_i[i21] >> 16 & 0xFF) * i11 / 255;
                i12 = (this.field_4223_i[i21] >> 8 & 0xFF) * i18 / 255;
                i13 = (this.field_4223_i[i21] >> 0 & 0xFF) * i18 / 255;
            }
            if (this.anaglyphEnabled) {
                int i23 = (i11 * 30 + i12 * 59 + i13 * 11) / 100;
                int i15 = (i11 * 30 + i12 * 70) / 100;
                int i24 = (i11 * 30 + i13 * 70) / 100;
                i11 = i23;
                i12 = i15;
                i13 = i24;
            }
            this.imageData[i9 * 4 + 0] = (byte)i11;
            this.imageData[i9 * 4 + 1] = (byte)i12;
            this.imageData[i9 * 4 + 2] = (byte)i13;
            this.imageData[i9 * 4 + 3] = (byte)i10;
            ++i9;
        }
    }
}

