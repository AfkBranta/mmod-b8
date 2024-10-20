/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityRainFX;
import net.minecraft.src.World;

public class EntitySplashFX
extends EntityRainFX {
    public EntitySplashFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12) {
        super(world1, d2, d4, d6);
        this.particleGravity = 0.04f;
        ++this.particleTextureIndex;
        if (d10 == 0.0 && (d8 != 0.0 || d12 != 0.0)) {
            this.motionX = d8;
            this.motionY = d10 + 0.1;
            this.motionZ = d12;
        }
    }
}

