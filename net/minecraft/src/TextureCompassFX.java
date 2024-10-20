/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.Item;
import net.minecraft.src.TextureFX;

public class TextureCompassFX
extends TextureFX {
    private Minecraft mc;
    private int[] field_4230_h = new int[256];
    private double field_4229_i;
    private double field_4228_j;

    public TextureCompassFX(Minecraft minecraft1) {
        super(Item.compass.getIconIndex(null));
        this.mc = minecraft1;
        this.tileImage = 1;
        try {
            BufferedImage bufferedImage2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
            int i3 = this.iconIndex % 16 * 16;
            int i4 = this.iconIndex / 16 * 16;
            bufferedImage2.getRGB(i3, i4, 16, 16, this.field_4230_h, 0, 16);
        }
        catch (IOException iOException5) {
            iOException5.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        int i19;
        int i18;
        int i17;
        int s16;
        int i15;
        int i14;
        int i13;
        int i12;
        int i11;
        int i10;
        int i1 = 0;
        while (i1 < 256) {
            int i2 = this.field_4230_h[i1] >> 24 & 0xFF;
            int i3 = this.field_4230_h[i1] >> 16 & 0xFF;
            int i4 = this.field_4230_h[i1] >> 8 & 0xFF;
            int i5 = this.field_4230_h[i1] >> 0 & 0xFF;
            if (this.anaglyphEnabled) {
                int i6 = (i3 * 30 + i4 * 59 + i5 * 11) / 100;
                int i7 = (i3 * 30 + i4 * 70) / 100;
                int i8 = (i3 * 30 + i5 * 70) / 100;
                i3 = i6;
                i4 = i7;
                i5 = i8;
            }
            this.imageData[i1 * 4 + 0] = (byte)i3;
            this.imageData[i1 * 4 + 1] = (byte)i4;
            this.imageData[i1 * 4 + 2] = (byte)i5;
            this.imageData[i1 * 4 + 3] = (byte)i2;
            ++i1;
        }
        double d20 = 0.0;
        if (this.mc.theWorld != null && this.mc.thePlayer != null) {
            ChunkCoordinates chunkCoordinates21 = this.mc.theWorld.getSpawnPoint();
            double d23 = (double)chunkCoordinates21.x - this.mc.thePlayer.posX;
            double d25 = (double)chunkCoordinates21.z - this.mc.thePlayer.posZ;
            d20 = (double)(this.mc.thePlayer.rotationYaw - 90.0f) * Math.PI / 180.0 - Math.atan2(d25, d23);
            if (this.mc.theWorld.worldProvider.field_4220_c) {
                d20 = Math.random() * 3.1415927410125732 * 2.0;
            }
        }
        double d22 = d20 - this.field_4229_i;
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
        this.field_4228_j += d22 * 0.1;
        this.field_4228_j *= 0.8;
        this.field_4229_i += this.field_4228_j;
        double d24 = Math.sin(this.field_4229_i);
        double d26 = Math.cos(this.field_4229_i);
        int i9 = -4;
        while (i9 <= 4) {
            i10 = (int)(8.5 + d26 * (double)i9 * 0.3);
            i11 = (int)(7.5 - d24 * (double)i9 * 0.3 * 0.5);
            i12 = i11 * 16 + i10;
            i13 = 100;
            i14 = 100;
            i15 = 100;
            s16 = 255;
            if (this.anaglyphEnabled) {
                i17 = (i13 * 30 + i14 * 59 + i15 * 11) / 100;
                i18 = (i13 * 30 + i14 * 70) / 100;
                i19 = (i13 * 30 + i15 * 70) / 100;
                i13 = i17;
                i14 = i18;
                i15 = i19;
            }
            this.imageData[i12 * 4 + 0] = (byte)i13;
            this.imageData[i12 * 4 + 1] = (byte)i14;
            this.imageData[i12 * 4 + 2] = (byte)i15;
            this.imageData[i12 * 4 + 3] = (byte)s16;
            ++i9;
        }
        i9 = -8;
        while (i9 <= 16) {
            i10 = (int)(8.5 + d24 * (double)i9 * 0.3);
            i11 = (int)(7.5 + d26 * (double)i9 * 0.3 * 0.5);
            i12 = i11 * 16 + i10;
            i13 = i9 >= 0 ? 255 : 100;
            i14 = i9 >= 0 ? 20 : 100;
            i15 = i9 >= 0 ? 20 : 100;
            s16 = 255;
            if (this.anaglyphEnabled) {
                i17 = (i13 * 30 + i14 * 59 + i15 * 11) / 100;
                i18 = (i13 * 30 + i14 * 70) / 100;
                i19 = (i13 * 30 + i15 * 70) / 100;
                i13 = i17;
                i14 = i18;
                i15 = i19;
            }
            this.imageData[i12 * 4 + 0] = (byte)i13;
            this.imageData[i12 * 4 + 1] = (byte)i14;
            this.imageData[i12 * 4 + 2] = (byte)i15;
            this.imageData[i12 * 4 + 3] = (byte)s16;
            ++i9;
        }
    }
}

