/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import net.minecraft.src.ColorizerFoliage;

public class ColorizerGrass {
    private static final int[] grassBuffer = new int[65536];

    static {
        try {
            BufferedImage bufferedImage0 = ImageIO.read(ColorizerFoliage.class.getResource("/misc/grasscolor.png"));
            bufferedImage0.getRGB(0, 0, 256, 256, grassBuffer, 0, 256);
        }
        catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    public static int getGrassColor(double d0, double d2) {
        int i4 = (int)((1.0 - d0) * 255.0);
        int i5 = (int)((1.0 - (d2 *= d0)) * 255.0);
        return grassBuffer[i5 << 8 | i4];
    }
}

