/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.RenderEngine;
import org.lwjgl.opengl.GL11;

public class TextureFX {
    public byte[] imageData = new byte[1024];
    public int iconIndex;
    public boolean anaglyphEnabled = false;
    public int field_1130_d = 0;
    public int tileSize = 1;
    public int tileImage = 0;

    public TextureFX(int i1) {
        this.iconIndex = i1;
    }

    public void onTick() {
    }

    public void bindImage(RenderEngine renderEngine1) {
        if (this.tileImage == 0) {
            GL11.glBindTexture((int)3553, (int)renderEngine1.getTexture("/terrain.png"));
        } else if (this.tileImage == 1) {
            GL11.glBindTexture((int)3553, (int)renderEngine1.getTexture("/gui/items.png"));
        }
    }
}

