/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import net.minecraft.src.ImageBuffer;

public class ImageBufferDownload
implements ImageBuffer {
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    @Override
    public BufferedImage parseUserSkin(BufferedImage bufferedImage1) {
        int i7;
        int i6;
        if (bufferedImage1 == null) {
            return null;
        }
        this.imageWidth = 64;
        this.imageHeight = 32;
        BufferedImage bufferedImage2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
        Graphics graphics3 = bufferedImage2.getGraphics();
        graphics3.drawImage(bufferedImage1, 0, 0, null);
        graphics3.dispose();
        this.imageData = ((DataBufferInt)bufferedImage2.getRaster().getDataBuffer()).getData();
        this.func_884_b(0, 0, 32, 16);
        this.func_885_a(32, 0, 64, 32);
        this.func_884_b(0, 16, 64, 32);
        boolean z4 = false;
        int i5 = 32;
        while (i5 < 64) {
            i6 = 0;
            while (i6 < 16) {
                i7 = this.imageData[i5 + i6 * 64];
                if ((i7 >> 24 & 0xFF) < 128) {
                    z4 = true;
                }
                ++i6;
            }
            ++i5;
        }
        if (!z4) {
            i5 = 32;
            while (i5 < 64) {
                i6 = 0;
                while (i6 < 16) {
                    i7 = this.imageData[i5 + i6 * 64];
                    if ((i7 >> 24 & 0xFF) < 128) {
                        z4 = true;
                    }
                    ++i6;
                }
                ++i5;
            }
        }
        return bufferedImage2;
    }

    private void func_885_a(int i1, int i2, int i3, int i4) {
        if (!this.func_886_c(i1, i2, i3, i4)) {
            int i5 = i1;
            while (i5 < i3) {
                int i6 = i2;
                while (i6 < i4) {
                    int n = i5 + i6 * this.imageWidth;
                    this.imageData[n] = this.imageData[n] & 0xFFFFFF;
                    ++i6;
                }
                ++i5;
            }
        }
    }

    private void func_884_b(int i1, int i2, int i3, int i4) {
        int i5 = i1;
        while (i5 < i3) {
            int i6 = i2;
            while (i6 < i4) {
                int n = i5 + i6 * this.imageWidth;
                this.imageData[n] = this.imageData[n] | 0xFF000000;
                ++i6;
            }
            ++i5;
        }
    }

    private boolean func_886_c(int i1, int i2, int i3, int i4) {
        int i5 = i1;
        while (i5 < i3) {
            int i6 = i2;
            while (i6 < i4) {
                int i7 = this.imageData[i5 + i6 * this.imageWidth];
                if ((i7 >> 24 & 0xFF) < 128) {
                    return true;
                }
                ++i6;
            }
            ++i5;
        }
        return false;
    }
}

