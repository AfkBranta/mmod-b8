/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderGenerate;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldChunkManager;
import net.minecraft.src.WorldProviderHell;

public class WorldProvider {
    public World worldObj;
    public WorldChunkManager worldChunkMgr;
    public boolean field_4220_c = false;
    public boolean isHellWorld = false;
    public boolean field_6478_e = false;
    public float[] lightBrightnessTable = new float[16];
    public int worldType = 0;
    private float[] colorsSunriseSunset = new float[4];

    public final void registerWorld(World world1) {
        this.worldObj = world1;
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }

    protected void generateLightBrightnessTable() {
        float f1 = 0.05f;
        int i2 = 0;
        while (i2 <= 15) {
            float f3 = 1.0f - (float)i2 / 15.0f;
            this.lightBrightnessTable[i2] = (1.0f - f3) / (f3 * 3.0f + 1.0f) * (1.0f - f1) + f1;
            ++i2;
        }
    }

    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManager(this.worldObj);
    }

    public IChunkProvider getChunkProvider() {
        return new ChunkProviderGenerate(this.worldObj, this.worldObj.getRandomSeed());
    }

    public boolean canCoordinateBeSpawn(int i1, int i2) {
        int i3 = this.worldObj.getFirstUncoveredBlock(i1, i2);
        return i3 == Block.sand.blockID;
    }

    public float calculateCelestialAngle(long j1, float f3) {
        int i4 = (int)(j1 % 24000L);
        float f5 = ((float)i4 + f3) / 24000.0f - 0.25f;
        if (f5 < 0.0f) {
            f5 += 1.0f;
        }
        if (f5 > 1.0f) {
            f5 -= 1.0f;
        }
        float f6 = f5;
        f5 = 1.0f - (float)((Math.cos((double)f5 * Math.PI) + 1.0) / 2.0);
        f5 = f6 + (f5 - f6) / 3.0f;
        return f5;
    }

    public float[] calcSunriseSunsetColors(float f1, float f2) {
        float f5;
        float f3 = 0.4f;
        float f4 = MathHelper.cos(f1 * (float)Math.PI * 2.0f) - 0.0f;
        if (f4 >= (f5 = -0.0f) - f3 && f4 <= f5 + f3) {
            float f6 = (f4 - f5) / f3 * 0.5f + 0.5f;
            float f7 = 1.0f - (1.0f - MathHelper.sin(f6 * (float)Math.PI)) * 0.99f;
            f7 *= f7;
            this.colorsSunriseSunset[0] = f6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[1] = f6 * f6 * 0.7f + 0.2f;
            this.colorsSunriseSunset[2] = f6 * f6 * 0.0f + 0.2f;
            this.colorsSunriseSunset[3] = f7;
            return this.colorsSunriseSunset;
        }
        return null;
    }

    public Vec3D func_4096_a(float f1, float f2) {
        float f3 = MathHelper.cos(f1 * (float)Math.PI * 2.0f) * 2.0f + 0.5f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        float f4 = 0.7529412f;
        float f5 = 0.84705883f;
        float f6 = 1.0f;
        return Vec3D.createVector(f4 *= f3 * 0.94f + 0.06f, f5 *= f3 * 0.94f + 0.06f, f6 *= f3 * 0.91f + 0.09f);
    }

    public boolean canRespawnHere() {
        return true;
    }

    public static WorldProvider func_4101_a(int i0) {
        return i0 == 0 ? new WorldProvider() : (i0 == -1 ? new WorldProviderHell() : null);
    }
}

