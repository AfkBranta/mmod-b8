/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class FontRenderer {
    private int[] charWidth = new int[256];
    public int fontTextureName = 0;
    private int fontDisplayLists;
    private IntBuffer buffer = GLAllocation.createDirectIntBuffer(1024);

    public FontRenderer(GameSettings gameSettings1, String string2, RenderEngine renderEngine3) {
        int i16;
        int i15;
        int i12;
        int i11;
        int i10;
        int i9;
        BufferedImage bufferedImage4;
        try {
            bufferedImage4 = ImageIO.read(RenderEngine.class.getResourceAsStream(string2));
        }
        catch (IOException iOException18) {
            throw new RuntimeException(iOException18);
        }
        int i5 = bufferedImage4.getWidth();
        int i6 = bufferedImage4.getHeight();
        int[] i7 = new int[i5 * i6];
        bufferedImage4.getRGB(0, 0, i5, i6, i7, 0, i5);
        int i8 = 0;
        while (i8 < 256) {
            i9 = i8 % 16;
            i10 = i8 / 16;
            i11 = 7;
            while (i11 >= 0) {
                i12 = i9 * 8 + i11;
                boolean z13 = true;
                int i14 = 0;
                while (i14 < 8 && z13) {
                    i15 = (i10 * 8 + i14) * i5;
                    i16 = i7[i12 + i15] & 0xFF;
                    if (i16 > 0) {
                        z13 = false;
                    }
                    ++i14;
                }
                if (!z13) break;
                --i11;
            }
            if (i8 == 32) {
                i11 = 2;
            }
            this.charWidth[i8] = i11 + 2;
            ++i8;
        }
        this.fontTextureName = renderEngine3.allocateAndSetupTexture(bufferedImage4);
        this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
        Tessellator tessellator19 = Tessellator.instance;
        i9 = 0;
        while (i9 < 256) {
            GL11.glNewList((int)(this.fontDisplayLists + i9), (int)4864);
            tessellator19.startDrawingQuads();
            i10 = i9 % 16 * 8;
            i11 = i9 / 16 * 8;
            float f20 = 7.99f;
            float f21 = 0.0f;
            float f23 = 0.0f;
            tessellator19.addVertexWithUV(0.0, 0.0f + f20, 0.0, (float)i10 / 128.0f + f21, ((float)i11 + f20) / 128.0f + f23);
            tessellator19.addVertexWithUV(0.0f + f20, 0.0f + f20, 0.0, ((float)i10 + f20) / 128.0f + f21, ((float)i11 + f20) / 128.0f + f23);
            tessellator19.addVertexWithUV(0.0f + f20, 0.0, 0.0, ((float)i10 + f20) / 128.0f + f21, (float)i11 / 128.0f + f23);
            tessellator19.addVertexWithUV(0.0, 0.0, 0.0, (float)i10 / 128.0f + f21, (float)i11 / 128.0f + f23);
            tessellator19.draw();
            GL11.glTranslatef((float)this.charWidth[i9], (float)0.0f, (float)0.0f);
            GL11.glEndList();
            ++i9;
        }
        i9 = 0;
        while (i9 < 32) {
            boolean z24;
            i10 = (i9 >> 3 & 1) * 85;
            i11 = (i9 >> 2 & 1) * 170 + i10;
            i12 = (i9 >> 1 & 1) * 170 + i10;
            int i22 = (i9 >> 0 & 1) * 170 + i10;
            if (i9 == 6) {
                i11 += 85;
            }
            boolean bl = z24 = i9 >= 16;
            if (gameSettings1.anaglyph) {
                i15 = (i11 * 30 + i12 * 59 + i22 * 11) / 100;
                i16 = (i11 * 30 + i12 * 70) / 100;
                int i17 = (i11 * 30 + i22 * 70) / 100;
                i11 = i15;
                i12 = i16;
                i22 = i17;
            }
            if (z24) {
                i11 /= 4;
                i12 /= 4;
                i22 /= 4;
            }
            GL11.glNewList((int)(this.fontDisplayLists + 256 + i9), (int)4864);
            GL11.glColor3f((float)((float)i11 / 255.0f), (float)((float)i12 / 255.0f), (float)((float)i22 / 255.0f));
            GL11.glEndList();
            ++i9;
        }
    }

    public void drawStringWithShadow(String string1, float i2, float i3, int i4) {
        this.renderString(string1, i2 + 1.0f, i3 + 1.0f, i4, true);
        this.drawString(string1, i2, i3, i4);
    }

    public void drawString(String string1, float i2, float i3, int i4) {
        this.renderString(string1, i2, i3, i4, false);
    }

    /*
     * Exception decompiling
     */
    public void renderString(String string1, float i2, float i3, int i4, boolean z5) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: CONTINUE without a while class org.benf.cfr.reader.bytecode.analysis.parse.statement.AssignmentSimple
         *     at org.benf.cfr.reader.bytecode.analysis.parse.statement.GotoStatement.getTargetStartBlock(GotoStatement.java:102)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.statement.IfStatement.getStructuredStatement(IfStatement.java:110)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.getStructuredStatementPlaceHolder(Op03SimpleStatement.java:550)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:727)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public int getStringWidth(String string1) {
        if (string1 == null) {
            return 0;
        }
        int i2 = 0;
        int i3 = 0;
        while (i3 < string1.length()) {
            if (string1.charAt(i3) == '\u00a7') {
                ++i3;
            } else {
                int i4 = FontAllowedCharacters.allowedCharacters.indexOf(string1.charAt(i3));
                if (i4 >= 0) {
                    i2 += this.charWidth[i4 + 32];
                }
            }
            ++i3;
        }
        return i2;
    }
}

