/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import net.minecraft.src.ImageBuffer;
import net.minecraft.src.ThreadDownloadImage;

public class ThreadDownloadImageData {
    public BufferedImage image;
    public int referenceCount = 1;
    public int textureName = -1;
    public boolean textureSetupComplete = false;

    public ThreadDownloadImageData(String string1, ImageBuffer imageBuffer2) {
        new ThreadDownloadImage(this, string1, imageBuffer2).start();
    }
}

