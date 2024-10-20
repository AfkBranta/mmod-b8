/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.Render;
import org.lwjgl.opengl.GL11;

public class RenderEntity
extends Render {
    @Override
    public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
        GL11.glPushMatrix();
        RenderEntity.renderOffsetAABB(entity1.boundingBox, d2 - entity1.lastTickPosX, d4 - entity1.lastTickPosY, d6 - entity1.lastTickPosZ);
        GL11.glPopMatrix();
    }
}

