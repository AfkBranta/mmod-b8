/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.TexturePackBase;
import org.lwjgl.opengl.GL11;

public class TexturePackCustom
extends TexturePackBase {
    private ZipFile texturePackZipFile;
    private int texturePackName = -1;
    private BufferedImage texturePackThumbnail;
    private File texturePackFile;

    public TexturePackCustom(File file1) {
        this.texturePackFileName = file1.getName();
        this.texturePackFile = file1;
    }

    private String truncateString(String string1) {
        if (string1 != null && string1.length() > 34) {
            string1 = string1.substring(0, 34);
        }
        return string1;
    }

    @Override
    public void func_6485_a(Minecraft minecraft1) throws IOException {
        ZipFile zipFile2 = null;
        InputStream inputStream3 = null;
        try {
            try {
                zipFile2 = new ZipFile(this.texturePackFile);
                try {
                    inputStream3 = zipFile2.getInputStream(zipFile2.getEntry("pack.txt"));
                    BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(inputStream3));
                    this.firstDescriptionLine = this.truncateString(bufferedReader4.readLine());
                    this.secondDescriptionLine = this.truncateString(bufferedReader4.readLine());
                    bufferedReader4.close();
                    inputStream3.close();
                }
                catch (Exception bufferedReader4) {
                    // empty catch block
                }
                try {
                    inputStream3 = zipFile2.getInputStream(zipFile2.getEntry("pack.png"));
                    this.texturePackThumbnail = ImageIO.read(inputStream3);
                    inputStream3.close();
                }
                catch (Exception bufferedReader4) {
                    // empty catch block
                }
                zipFile2.close();
            }
            catch (Exception exception21) {
                exception21.printStackTrace();
                try {
                    inputStream3.close();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                try {
                    zipFile2.close();
                }
                catch (Exception exception) {}
            }
        }
        finally {
            try {
                inputStream3.close();
            }
            catch (Exception exception) {}
            try {
                zipFile2.close();
            }
            catch (Exception exception) {}
        }
    }

    @Override
    public void func_6484_b(Minecraft minecraft1) {
        if (this.texturePackThumbnail != null) {
            minecraft1.renderEngine.deleteTexture(this.texturePackName);
        }
        this.closeTexturePackFile();
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

    @Override
    public void func_6482_a() {
        try {
            this.texturePackZipFile = new ZipFile(this.texturePackFile);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void closeTexturePackFile() {
        try {
            this.texturePackZipFile.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.texturePackZipFile = null;
    }

    @Override
    public InputStream func_6481_a(String string1) {
        try {
            ZipEntry zipEntry2 = this.texturePackZipFile.getEntry(string1.substring(1));
            if (zipEntry2 != null) {
                return this.texturePackZipFile.getInputStream(zipEntry2);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return TexturePackBase.class.getResourceAsStream(string1);
    }
}

