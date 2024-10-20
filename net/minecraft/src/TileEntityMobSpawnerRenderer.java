/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer
extends TileEntitySpecialRenderer {
    private Map entityHashMap = new HashMap();

    public void renderTileEntityMobSpawner(TileEntityMobSpawner tileEntityMobSpawner1, double d2, double d4, double d6, float f8) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)d2 + 0.5f), (float)((float)d4), (float)((float)d6 + 0.5f));
        Entity entity9 = (Entity)this.entityHashMap.get(tileEntityMobSpawner1.getMobID());
        if (entity9 == null) {
            entity9 = EntityList.createEntityInWorld(tileEntityMobSpawner1.getMobID(), null);
            this.entityHashMap.put(tileEntityMobSpawner1.getMobID(), entity9);
        }
        if (entity9 != null) {
            entity9.setWorld(tileEntityMobSpawner1.worldObj);
            float f10 = 0.4375f;
            GL11.glTranslatef((float)0.0f, (float)0.4f, (float)0.0f);
            GL11.glRotatef((float)((float)(tileEntityMobSpawner1.yaw2 + (tileEntityMobSpawner1.yaw - tileEntityMobSpawner1.yaw2) * (double)f8) * 10.0f), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)-30.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glTranslatef((float)0.0f, (float)-0.4f, (float)0.0f);
            GL11.glScalef((float)f10, (float)f10, (float)f10);
            entity9.setLocationAndAngles(d2, d4, d6, 0.0f, 0.0f);
            RenderManager.instance.renderEntityWithPosYaw(entity9, 0.0, 0.0, 0.0, 0.0f, f8);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {
        this.renderTileEntityMobSpawner((TileEntityMobSpawner)tileEntity1, d2, d4, d6, f8);
    }
}

