/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.TexturePackBase;
import org.lwjgl.opengl.GL11;

public class TexturePackDefault
extends TexturePackBase {
    private int texturePackName = -1;
    private BufferedImage texturePackThumbnail;

    public TexturePackDefault() {
        this.texturePackFileName = "Default";
        this.firstDescriptionLine = "The default look of Minecraft";
        try {
            this.texturePackThumbnail = ImageIO.read(TexturePackDefault.class.getResource("/pack.png"));
        }
        catch (IOException iOException2) {
            iOException2.printStackTrace();
        }
    }

    @Override
    public void func_6484_b(Minecraft minecraft1) {
        if (this.texturePackThumbnail != null) {
            minecraft1.renderEngine.deleteTexture(this.texturePackName);
        }
    }

    @Override
    public void bindThumbnailTexture(Minecraft minecraft1) {
        if (this.texturePackThumbnail != null && this.texturePackName < 0) {
            this.texturePackName = minecraft1.renderEngine.allocateAndSetupTexture(this.texturePackThumbnail);
        }
        if (this.texturePackThumbnail != null) {
            minecraft1.renderEngine.bindTexture(this.texturePackName);
        } else {
            GL11.glBindTexture((int)3553, (int)minecraft1.renderEngine.getTexture("/gui/unknown_pack.png"));
        }
    }
}

