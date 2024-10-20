/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class ClippingHelper {
    public float[][] frustum = new float[16][16];
    public float[] projectionMatrix = new float[16];
    public float[] modelviewMatrix = new float[16];
    public float[] clippingMatrix = new float[16];

    public boolean isBoxInFrustum(double d1, double d3, double d5, double d7, double d9, double d11) {
        int i13 = 0;
        while (i13 < 6) {
            if ((double)this.frustum[i13][0] * d1 + (double)this.frustum[i13][1] * d3 + (double)this.frustum[i13][2] * d5 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d7 + (double)this.frustum[i13][1] * d3 + (double)this.frustum[i13][2] * d5 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d1 + (double)this.frustum[i13][1] * d9 + (double)this.frustum[i13][2] * d5 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d7 + (double)this.frustum[i13][1] * d9 + (double)this.frustum[i13][2] * d5 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d1 + (double)this.frustum[i13][1] * d3 + (double)this.frustum[i13][2] * d11 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d7 + (double)this.frustum[i13][1] * d3 + (double)this.frustum[i13][2] * d11 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d1 + (double)this.frustum[i13][1] * d9 + (double)this.frustum[i13][2] * d11 + (double)this.frustum[i13][3] <= 0.0 && (double)this.frustum[i13][0] * d7 + (double)this.frustum[i13][1] * d9 + (double)this.frustum[i13][2] * d11 + (double)this.frustum[i13][3] <= 0.0) {
                return false;
            }
            ++i13;
        }
        return true;
    }
}

