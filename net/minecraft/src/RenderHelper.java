/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.nio.FloatBuffer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.Vec3D;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    private static FloatBuffer field_1695_a = GLAllocation.createDirectFloatBuffer(16);

    public static void disableStandardItemLighting() {
        GL11.glDisable((int)2896);
        GL11.glDisable((int)16384);
        GL11.glDisable((int)16385);
        GL11.glDisable((int)2903);
    }

    public static void enableStandardItemLighting() {
        GL11.glEnable((int)2896);
        GL11.glEnable((int)16384);
        GL11.glEnable((int)16385);
        GL11.glEnable((int)2903);
        GL11.glColorMaterial((int)1032, (int)5634);
        float f0 = 0.4f;
        float f1 = 0.6f;
        float f2 = 0.0f;
        Vec3D vec3D3 = Vec3D.createVector(0.2f, 1.0, -0.7f).normalize();
        GL11.glLight((int)16384, (int)4611, (FloatBuffer)RenderHelper.func_1157_a(vec3D3.xCoord, vec3D3.yCoord, vec3D3.zCoord, 0.0));
        GL11.glLight((int)16384, (int)4609, (FloatBuffer)RenderHelper.func_1156_a(f1, f1, f1, 1.0f));
        GL11.glLight((int)16384, (int)4608, (FloatBuffer)RenderHelper.func_1156_a(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight((int)16384, (int)4610, (FloatBuffer)RenderHelper.func_1156_a(f2, f2, f2, 1.0f));
        vec3D3 = Vec3D.createVector(-0.2f, 1.0, 0.7f).normalize();
        GL11.glLight((int)16385, (int)4611, (FloatBuffer)RenderHelper.func_1157_a(vec3D3.xCoord, vec3D3.yCoord, vec3D3.zCoord, 0.0));
        GL11.glLight((int)16385, (int)4609, (FloatBuffer)RenderHelper.func_1156_a(f1, f1, f1, 1.0f));
        GL11.glLight((int)16385, (int)4608, (FloatBuffer)RenderHelper.func_1156_a(0.0f, 0.0f, 0.0f, 1.0f));
        GL11.glLight((int)16385, (int)4610, (FloatBuffer)RenderHelper.func_1156_a(f2, f2, f2, 1.0f));
        GL11.glShadeModel((int)7424);
        GL11.glLightModel((int)2899, (FloatBuffer)RenderHelper.func_1156_a(f0, f0, f0, 1.0f));
    }

    private static FloatBuffer func_1157_a(double d0, double d2, double d4, double d6) {
        return RenderHelper.func_1156_a((float)d0, (float)d2, (float)d4, (float)d6);
    }

    private static FloatBuffer func_1156_a(float f0, float f1, float f2, float f3) {
        field_1695_a.clear();
        field_1695_a.put(f0).put(f1).put(f2).put(f3);
        field_1695_a.flip();
        return field_1695_a;
    }
}

