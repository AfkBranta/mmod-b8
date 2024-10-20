/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;

public abstract class TileEntitySpecialRenderer {
    protected TileEntityRenderer tileEntityRenderer;

    public abstract void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8);

    protected void bindTextureByName(String string1) {
        RenderEngine renderEngine2 = this.tileEntityRenderer.renderEngine;
        renderEngine2.bindTexture(renderEngine2.getTexture(string1));
    }

    public void setTileEntityRenderer(TileEntityRenderer tileEntityRenderer1) {
        this.tileEntityRenderer = tileEntityRenderer1;
    }

    public FontRenderer getFontRenderer() {
        return this.tileEntityRenderer.getFontRenderer();
    }
}

