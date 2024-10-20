/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ImageBuffer;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.ThreadDownloadImageData;
import org.lwjgl.opengl.GL11;

public class RenderEngine {
    public static boolean useMipmaps = false;
    private HashMap textureMap = new HashMap();
    private HashMap textureNameToImageMap = new HashMap();
    private IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
    private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(0x100000);
    private List textureList = new ArrayList();
    private Map urlToImageDataMap = new HashMap();
    private GameSettings options;
    private boolean clampTexture = false;
    private boolean blurTexture = false;
    private TexturePackList field_6527_k;
    private BufferedImage field_25189_l = new BufferedImage(64, 64, 2);

    public RenderEngine(TexturePackList texturePackList1, GameSettings gameSettings2) {
        this.field_6527_k = texturePackList1;
        this.options = gameSettings2;
        Graphics graphics3 = this.field_25189_l.getGraphics();
        graphics3.setColor(Color.WHITE);
        graphics3.fillRect(0, 0, 64, 64);
        graphics3.setColor(Color.BLACK);
        graphics3.drawString("missingtex", 1, 10);
        graphics3.dispose();
    }

    public int getTexture(String string1) {
        TexturePackBase texturePackBase2 = this.field_6527_k.selectedTexturePack;
        Integer integer3 = (Integer)this.textureMap.get(string1);
        if (integer3 != null) {
            return integer3;
        }
        try {
            this.singleIntBuffer.clear();
            GLAllocation.generateTextureNames(this.singleIntBuffer);
            int i6 = this.singleIntBuffer.get(0);
            if (string1.startsWith("##")) {
                this.setupTexture(this.unwrapImageByColumns(this.readTextureImage(texturePackBase2.func_6481_a(string1.substring(2)))), i6);
            } else if (string1.startsWith("%clamp%")) {
                this.clampTexture = true;
                this.setupTexture(this.readTextureImage(texturePackBase2.func_6481_a(string1.substring(7))), i6);
                this.clampTexture = false;
            } else if (string1.startsWith("%blur%")) {
                this.blurTexture = true;
                this.setupTexture(this.readTextureImage(texturePackBase2.func_6481_a(string1.substring(6))), i6);
                this.blurTexture = false;
            } else {
                InputStream inputStream4 = texturePackBase2.func_6481_a(string1);
                if (inputStream4 == null) {
                    this.setupTexture(this.field_25189_l, i6);
                } else {
                    this.setupTexture(this.readTextureImage(inputStream4), i6);
                }
            }
            this.textureMap.put(string1, i6);
            return i6;
        }
        catch (IOException iOException5) {
            throw new RuntimeException("!!");
        }
    }

    private BufferedImage unwrapImageByColumns(BufferedImage bufferedImage1) {
        int i2 = bufferedImage1.getWidth() / 16;
        BufferedImage bufferedImage3 = new BufferedImage(16, bufferedImage1.getHeight() * i2, 2);
        Graphics graphics4 = bufferedImage3.getGraphics();
        int i5 = 0;
        while (i5 < i2) {
            graphics4.drawImage(bufferedImage1, -i5 * 16, i5 * bufferedImage1.getHeight(), null);
            ++i5;
        }
        graphics4.dispose();
        return bufferedImage3;
    }

    public int allocateAndSetupTexture(BufferedImage bufferedImage1) {
        this.singleIntBuffer.clear();
        GLAllocation.generateTextureNames(this.singleIntBuffer);
        int i2 = this.singleIntBuffer.get(0);
        this.setupTexture(bufferedImage1, i2);
        this.textureNameToImageMap.put(i2, bufferedImage1);
        return i2;
    }

    public void setupTexture(BufferedImage bufferedImage1, int i2) {
        int i14;
        int i13;
        int i12;
        int i11;
        int i10;
        int i9;
        int i8;
        GL11.glBindTexture((int)3553, (int)i2);
        if (useMipmaps) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9987);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        } else {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
        }
        if (this.blurTexture) {
            GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
            GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        }
        if (this.clampTexture) {
            GL11.glTexParameteri((int)3553, (int)10242, (int)10496);
            GL11.glTexParameteri((int)3553, (int)10243, (int)10496);
        } else {
            GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
            GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        }
        int i3 = bufferedImage1.getWidth();
        int i4 = bufferedImage1.getHeight();
        int[] i5 = new int[i3 * i4];
        byte[] b6 = new byte[i3 * i4 * 4];
        bufferedImage1.getRGB(0, 0, i3, i4, i5, 0, i3);
        int i7 = 0;
        while (i7 < i5.length) {
            i8 = i5[i7] >> 24 & 0xFF;
            i9 = i5[i7] >> 16 & 0xFF;
            i10 = i5[i7] >> 8 & 0xFF;
            i11 = i5[i7] & 0xFF;
            if (this.options != null && this.options.anaglyph) {
                i12 = (i9 * 30 + i10 * 59 + i11 * 11) / 100;
                i13 = (i9 * 30 + i10 * 70) / 100;
                i14 = (i9 * 30 + i11 * 70) / 100;
                i9 = i12;
                i10 = i13;
                i11 = i14;
            }
            b6[i7 * 4 + 0] = (byte)i9;
            b6[i7 * 4 + 1] = (byte)i10;
            b6[i7 * 4 + 2] = (byte)i11;
            b6[i7 * 4 + 3] = (byte)i8;
            ++i7;
        }
        this.imageData.clear();
        this.imageData.put(b6);
        this.imageData.position(0).limit(b6.length);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)i3, (int)i4, (int)0, (int)6408, (int)5121, (ByteBuffer)this.imageData);
        if (useMipmaps) {
            i7 = 1;
            while (i7 <= 4) {
                i8 = i3 >> i7 - 1;
                i9 = i3 >> i7;
                i10 = i4 >> i7;
                i11 = 0;
                while (i11 < i9) {
                    i12 = 0;
                    while (i12 < i10) {
                        i13 = this.imageData.getInt((i11 * 2 + 0 + (i12 * 2 + 0) * i8) * 4);
                        i14 = this.imageData.getInt((i11 * 2 + 1 + (i12 * 2 + 0) * i8) * 4);
                        int i15 = this.imageData.getInt((i11 * 2 + 1 + (i12 * 2 + 1) * i8) * 4);
                        int i16 = this.imageData.getInt((i11 * 2 + 0 + (i12 * 2 + 1) * i8) * 4);
                        int i17 = this.weightedAverageColor(this.weightedAverageColor(i13, i14), this.weightedAverageColor(i15, i16));
                        this.imageData.putInt((i11 + i12 * i9) * 4, i17);
                        ++i12;
                    }
                    ++i11;
                }
                GL11.glTexImage2D((int)3553, (int)i7, (int)6408, (int)i9, (int)i10, (int)0, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                ++i7;
            }
        }
    }

    public void deleteTexture(int i1) {
        this.textureNameToImageMap.remove(i1);
        this.singleIntBuffer.clear();
        this.singleIntBuffer.put(i1);
        this.singleIntBuffer.flip();
        GL11.glDeleteTextures((IntBuffer)this.singleIntBuffer);
    }

    public int getTextureForDownloadableImage(String string1, String string2) {
        ThreadDownloadImageData threadDownloadImageData3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
        if (threadDownloadImageData3 != null && threadDownloadImageData3.image != null && !threadDownloadImageData3.textureSetupComplete) {
            if (threadDownloadImageData3.textureName < 0) {
                threadDownloadImageData3.textureName = this.allocateAndSetupTexture(threadDownloadImageData3.image);
            } else {
                this.setupTexture(threadDownloadImageData3.image, threadDownloadImageData3.textureName);
            }
            threadDownloadImageData3.textureSetupComplete = true;
        }
        return threadDownloadImageData3 != null && threadDownloadImageData3.textureName >= 0 ? threadDownloadImageData3.textureName : (string2 == null ? -1 : this.getTexture(string2));
    }

    public ThreadDownloadImageData obtainImageData(String string1, ImageBuffer imageBuffer2) {
        ThreadDownloadImageData threadDownloadImageData3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
        if (threadDownloadImageData3 == null) {
            this.urlToImageDataMap.put(string1, new ThreadDownloadImageData(string1, imageBuffer2));
        } else {
            ++threadDownloadImageData3.referenceCount;
        }
        return threadDownloadImageData3;
    }

    public void releaseImageData(String string1) {
        ThreadDownloadImageData threadDownloadImageData2 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
        if (threadDownloadImageData2 != null) {
            --threadDownloadImageData2.referenceCount;
            if (threadDownloadImageData2.referenceCount == 0) {
                if (threadDownloadImageData2.textureName >= 0) {
                    this.deleteTexture(threadDownloadImageData2.textureName);
                }
                this.urlToImageDataMap.remove(string1);
            }
        }
    }

    public void registerTextureFX(TextureFX textureFX1) {
        this.textureList.add(textureFX1);
        textureFX1.onTick();
    }

    public void func_1067_a() {
        int i12;
        int i11;
        int i10;
        int i9;
        int i8;
        int i7;
        int i6;
        int i5;
        int i4;
        int i3;
        TextureFX textureFX2;
        int i1 = 0;
        while (i1 < this.textureList.size()) {
            textureFX2 = (TextureFX)this.textureList.get(i1);
            textureFX2.anaglyphEnabled = this.options.anaglyph;
            textureFX2.onTick();
            this.imageData.clear();
            this.imageData.put(textureFX2.imageData);
            this.imageData.position(0).limit(textureFX2.imageData.length);
            textureFX2.bindImage(this);
            i3 = 0;
            while (i3 < textureFX2.tileSize) {
                i4 = 0;
                while (i4 < textureFX2.tileSize) {
                    GL11.glTexSubImage2D((int)3553, (int)0, (int)(textureFX2.iconIndex % 16 * 16 + i3 * 16), (int)(textureFX2.iconIndex / 16 * 16 + i4 * 16), (int)16, (int)16, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                    if (useMipmaps) {
                        i5 = 1;
                        while (i5 <= 4) {
                            i6 = 16 >> i5 - 1;
                            i7 = 16 >> i5;
                            i8 = 0;
                            while (i8 < i7) {
                                i9 = 0;
                                while (i9 < i7) {
                                    i10 = this.imageData.getInt((i8 * 2 + 0 + (i9 * 2 + 0) * i6) * 4);
                                    i11 = this.imageData.getInt((i8 * 2 + 1 + (i9 * 2 + 0) * i6) * 4);
                                    i12 = this.imageData.getInt((i8 * 2 + 1 + (i9 * 2 + 1) * i6) * 4);
                                    int i13 = this.imageData.getInt((i8 * 2 + 0 + (i9 * 2 + 1) * i6) * 4);
                                    int i14 = this.averageColor(this.averageColor(i10, i11), this.averageColor(i12, i13));
                                    this.imageData.putInt((i8 + i9 * i7) * 4, i14);
                                    ++i9;
                                }
                                ++i8;
                            }
                            GL11.glTexSubImage2D((int)3553, (int)i5, (int)(textureFX2.iconIndex % 16 * i7), (int)(textureFX2.iconIndex / 16 * i7), (int)i7, (int)i7, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                            ++i5;
                        }
                    }
                    ++i4;
                }
                ++i3;
            }
            ++i1;
        }
        i1 = 0;
        while (i1 < this.textureList.size()) {
            textureFX2 = (TextureFX)this.textureList.get(i1);
            if (textureFX2.field_1130_d > 0) {
                this.imageData.clear();
                this.imageData.put(textureFX2.imageData);
                this.imageData.position(0).limit(textureFX2.imageData.length);
                GL11.glBindTexture((int)3553, (int)textureFX2.field_1130_d);
                GL11.glTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)16, (int)16, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                if (useMipmaps) {
                    i3 = 1;
                    while (i3 <= 4) {
                        i4 = 16 >> i3 - 1;
                        i5 = 16 >> i3;
                        i6 = 0;
                        while (i6 < i5) {
                            i7 = 0;
                            while (i7 < i5) {
                                i8 = this.imageData.getInt((i6 * 2 + 0 + (i7 * 2 + 0) * i4) * 4);
                                i9 = this.imageData.getInt((i6 * 2 + 1 + (i7 * 2 + 0) * i4) * 4);
                                i10 = this.imageData.getInt((i6 * 2 + 1 + (i7 * 2 + 1) * i4) * 4);
                                i11 = this.imageData.getInt((i6 * 2 + 0 + (i7 * 2 + 1) * i4) * 4);
                                i12 = this.averageColor(this.averageColor(i8, i9), this.averageColor(i10, i11));
                                this.imageData.putInt((i6 + i7 * i5) * 4, i12);
                                ++i7;
                            }
                            ++i6;
                        }
                        GL11.glTexSubImage2D((int)3553, (int)i3, (int)0, (int)0, (int)i5, (int)i5, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                        ++i3;
                    }
                }
            }
            ++i1;
        }
    }

    private int averageColor(int i1, int i2) {
        int i3 = (i1 & 0xFF000000) >> 24 & 0xFF;
        int i4 = (i2 & 0xFF000000) >> 24 & 0xFF;
        return (i3 + i4 >> 1 << 24) + ((i1 & 0xFEFEFE) + (i2 & 0xFEFEFE) >> 1);
    }

    private int weightedAverageColor(int i1, int i2) {
        int i3 = (i1 & 0xFF000000) >> 24 & 0xFF;
        int i4 = (i2 & 0xFF000000) >> 24 & 0xFF;
        int s5 = 255;
        if (i3 + i4 == 0) {
            i3 = 1;
            i4 = 1;
            s5 = 0;
        }
        int i6 = (i1 >> 16 & 0xFF) * i3;
        int i7 = (i1 >> 8 & 0xFF) * i3;
        int i8 = (i1 & 0xFF) * i3;
        int i9 = (i2 >> 16 & 0xFF) * i4;
        int i10 = (i2 >> 8 & 0xFF) * i4;
        int i11 = (i2 & 0xFF) * i4;
        int i12 = (i6 + i9) / (i3 + i4);
        int i13 = (i7 + i10) / (i3 + i4);
        int i14 = (i8 + i11) / (i3 + i4);
        return s5 << 24 | i12 << 16 | i13 << 8 | i14;
    }

    public void refreshTextures() {
        BufferedImage bufferedImage4;
        TexturePackBase texturePackBase1 = this.field_6527_k.selectedTexturePack;
        Iterator<Object> iterator2 = this.textureNameToImageMap.keySet().iterator();
        while (iterator2.hasNext()) {
            int i3 = (Integer)iterator2.next();
            bufferedImage4 = (BufferedImage)this.textureNameToImageMap.get(i3);
            this.setupTexture(bufferedImage4, i3);
        }
        for (ThreadDownloadImageData threadDownloadImageData7 : this.urlToImageDataMap.values()) {
            threadDownloadImageData7.textureSetupComplete = false;
        }
        for (String string8 : this.textureMap.keySet()) {
            try {
                if (string8.startsWith("##")) {
                    bufferedImage4 = this.unwrapImageByColumns(this.readTextureImage(texturePackBase1.func_6481_a(string8.substring(2))));
                } else if (string8.startsWith("%clamp%")) {
                    this.clampTexture = true;
                    bufferedImage4 = this.readTextureImage(texturePackBase1.func_6481_a(string8.substring(7)));
                } else if (string8.startsWith("%blur%")) {
                    this.blurTexture = true;
                    bufferedImage4 = this.readTextureImage(texturePackBase1.func_6481_a(string8.substring(6)));
                } else {
                    bufferedImage4 = this.readTextureImage(texturePackBase1.func_6481_a(string8));
                }
                int i5 = (Integer)this.textureMap.get(string8);
                this.setupTexture(bufferedImage4, i5);
                this.blurTexture = false;
                this.clampTexture = false;
            }
            catch (IOException iOException6) {
                iOException6.printStackTrace();
            }
        }
    }

    private BufferedImage readTextureImage(InputStream inputStream1) throws IOException {
        BufferedImage bufferedImage2 = ImageIO.read(inputStream1);
        inputStream1.close();
        return bufferedImage2;
    }

    public void bindTexture(int i1) {
        if (i1 >= 0) {
            GL11.glBindTexture((int)3553, (int)i1);
        }
    }
}

