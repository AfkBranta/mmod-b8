/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderHell;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MobSpawnerBase;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldChunkManagerHell;
import net.minecraft.src.WorldProvider;

public class WorldProviderHell
extends WorldProvider {
    @Override
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(MobSpawnerBase.hell, 1.0, 0.0);
        this.field_4220_c = true;
        this.isHellWorld = true;
        this.field_6478_e = true;
        this.worldType = -1;
    }

    @Override
    public Vec3D func_4096_a(float f1, float f2) {
        return Vec3D.createVector(0.2f, 0.03f, 0.03f);
    }

    @Override
    protected void generateLightBrightnessTable() {
        float f1 = 0.1f;
        int i2 = 0;
        while (i2 <= 15) {
            float f3 = 1.0f - (float)i2 / 15.0f;
            this.lightBrightnessTable[i2] = (1.0f - f3) / (f3 * 3.0f + 1.0f) * (1.0f - f1) + f1;
            ++i2;
        }
    }

    @Override
    public IChunkProvider getChunkProvider() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getRandomSeed());
    }

    @Override
    public boolean canCoordinateBeSpawn(int i1, int i2) {
        int i3 = this.worldObj.getFirstUncoveredBlock(i1, i2);
        return i3 == Block.bedrock.blockID ? false : (i3 == 0 ? false : Block.opaqueCubeLookup[i3]);
    }

    @Override
    public float calculateCelestialAngle(long j1, float f3) {
        return 0.5f;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }
}

