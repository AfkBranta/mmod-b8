/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ColorizerFoliage {
    private static final int[] foliageBuffer = new int[65536];

    static {
        try {
            BufferedImage bufferedImage0 = ImageIO.read(ColorizerFoliage.class.getResource("/misc/foliagecolor.png"));
            bufferedImage0.getRGB(0, 0, 256, 256, foliageBuffer, 0, 256);
        }
        catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    public static int getFoliageColor(double d0, double d2) {
        int i4 = (int)((1.0 - d0) * 255.0);
        int i5 = (int)((1.0 - (d2 *= d0)) * 255.0);
        return foliageBuffer[i5 << 8 | i4];
    }

    public static int getFoliageColorPine() {
        return 0x619961;
    }

    public static int getFoliageColorBirch() {
        return 8431445;
    }
}

