/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte[] pixelData;
    private static int[] imageData;
    private int field_21197_e;
    private DataOutputStream field_21196_f;
    private byte[] field_21195_g;
    private int field_21194_h;
    private int field_21193_i;
    private File field_21192_j;

    public static String saveScreenshot(File file0, int i1, int i2) {
        try {
            File file5;
            File file3 = new File(file0, "screenshots");
            file3.mkdir();
            if (buffer == null || buffer.capacity() < i1 * i2) {
                buffer = BufferUtils.createByteBuffer((int)(i1 * i2 * 3));
            }
            if (imageData == null || imageData.length < i1 * i2 * 3) {
                pixelData = new byte[i1 * i2 * 3];
                imageData = new int[i1 * i2];
            }
            GL11.glPixelStorei((int)3333, (int)1);
            GL11.glPixelStorei((int)3317, (int)1);
            buffer.clear();
            GL11.glReadPixels((int)0, (int)0, (int)i1, (int)i2, (int)6407, (int)5121, (ByteBuffer)buffer);
            buffer.clear();
            String string4 = dateFormat.format(new Date());
            int i6 = 1;
            while ((file5 = new File(file3, String.valueOf(string4) + (i6 == 1 ? "" : "_" + i6) + ".png")).exists()) {
                ++i6;
            }
            buffer.get(pixelData);
            int i7 = 0;
            while (i7 < i1) {
                int i8 = 0;
                while (i8 < i2) {
                    int i13;
                    int i9 = i7 + (i2 - i8 - 1) * i1;
                    int i10 = pixelData[i9 * 3 + 0] & 0xFF;
                    int i11 = pixelData[i9 * 3 + 1] & 0xFF;
                    int i12 = pixelData[i9 * 3 + 2] & 0xFF;
                    ScreenShotHelper.imageData[i7 + i8 * i1] = i13 = 0xFF000000 | i10 << 16 | i11 << 8 | i12;
                    ++i8;
                }
                ++i7;
            }
            BufferedImage bufferedImage15 = new BufferedImage(i1, i2, 1);
            bufferedImage15.setRGB(0, 0, i1, i2, imageData, 0, i1);
            ImageIO.write((RenderedImage)bufferedImage15, "png", file5);
            return "Saved screenshot as " + file5.getName();
        }
        catch (Exception exception14) {
            exception14.printStackTrace();
            return "Failed to save: " + exception14;
        }
    }

    public ScreenShotHelper(File file1, int i2, int i3, int i4) throws IOException {
        this.field_21194_h = i2;
        this.field_21193_i = i3;
        this.field_21197_e = i4;
        File file5 = new File(file1, "screenshots");
        file5.mkdir();
        String string6 = "huge_" + dateFormat.format(new Date());
        int i7 = 1;
        while ((this.field_21192_j = new File(file5, String.valueOf(string6) + (i7 == 1 ? "" : "_" + i7) + ".tga")).exists()) {
            ++i7;
        }
        byte[] b8 = new byte[18];
        b8[2] = 2;
        b8[12] = (byte)(i2 % 256);
        b8[13] = (byte)(i2 / 256);
        b8[14] = (byte)(i3 % 256);
        b8[15] = (byte)(i3 / 256);
        b8[16] = 24;
        this.field_21195_g = new byte[i2 * i4 * 3];
        this.field_21196_f = new DataOutputStream(new FileOutputStream(this.field_21192_j));
        this.field_21196_f.write(b8);
    }

    public void func_21189_a(ByteBuffer byteBuffer1, int i2, int i3, int i4, int i5) throws IOException {
        int i6 = i4;
        int i7 = i5;
        if (i4 > this.field_21194_h - i2) {
            i6 = this.field_21194_h - i2;
        }
        if (i5 > this.field_21193_i - i3) {
            i7 = this.field_21193_i - i3;
        }
        this.field_21197_e = i7;
        int i8 = 0;
        while (i8 < i7) {
            byteBuffer1.position((i5 - i7) * i4 * 3 + i8 * i4 * 3);
            int i9 = (i2 + i8 * this.field_21194_h) * 3;
            byteBuffer1.get(this.field_21195_g, i9, i6 * 3);
            ++i8;
        }
    }

    public void func_21191_a() throws IOException {
        this.field_21196_f.write(this.field_21195_g, 0, this.field_21194_h * 3 * this.field_21197_e);
    }

    public String func_21190_b() throws IOException {
        this.field_21196_f.close();
        return "Saved screenshot as " + this.field_21192_j.getName();
    }
}

