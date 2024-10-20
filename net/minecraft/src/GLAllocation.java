/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation {
    private static List displayLists = new ArrayList();
    private static List textureNames = new ArrayList();

    public static synchronized int generateDisplayLists(int i0) {
        int i1 = GL11.glGenLists((int)i0);
        displayLists.add(i1);
        displayLists.add(i0);
        return i1;
    }

    public static synchronized void generateTextureNames(IntBuffer intBuffer0) {
        GL11.glGenTextures((IntBuffer)intBuffer0);
        int i1 = intBuffer0.position();
        while (i1 < intBuffer0.limit()) {
            textureNames.add(intBuffer0.get(i1));
            ++i1;
        }
    }

    public static synchronized void deleteTexturesAndDisplayLists() {
        int i0 = 0;
        while (i0 < displayLists.size()) {
            GL11.glDeleteLists((int)((Integer)displayLists.get(i0)), (int)((Integer)displayLists.get(i0 + 1)));
            i0 += 2;
        }
        IntBuffer intBuffer2 = GLAllocation.createDirectIntBuffer(textureNames.size());
        intBuffer2.flip();
        GL11.glDeleteTextures((IntBuffer)intBuffer2);
        int i1 = 0;
        while (i1 < textureNames.size()) {
            intBuffer2.put((Integer)textureNames.get(i1));
            ++i1;
        }
        intBuffer2.flip();
        GL11.glDeleteTextures((IntBuffer)intBuffer2);
        displayLists.clear();
        textureNames.clear();
    }

    public static synchronized ByteBuffer createDirectByteBuffer(int i0) {
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(i0).order(ByteOrder.nativeOrder());
        return byteBuffer1;
    }

    public static IntBuffer createDirectIntBuffer(int i0) {
        return GLAllocation.createDirectByteBuffer(i0 << 2).asIntBuffer();
    }

    public static FloatBuffer createDirectFloatBuffer(int i0) {
        return GLAllocation.createDirectByteBuffer(i0 << 2).asFloatBuffer();
    }
}

