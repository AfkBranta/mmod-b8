/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityDiggingFX;
import net.minecraft.src.EntityFX;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class EffectRenderer {
    protected World worldObj;
    private List[] fxLayers = new List[4];
    private RenderEngine renderer;
    private Random rand = new Random();

    public EffectRenderer(World world1, RenderEngine renderEngine2) {
        if (world1 != null) {
            this.worldObj = world1;
        }
        this.renderer = renderEngine2;
        int i3 = 0;
        while (i3 < 4) {
            this.fxLayers[i3] = new ArrayList();
            ++i3;
        }
    }

    public void addEffect(EntityFX entityFX1) {
        int i2 = entityFX1.getFXLayer();
        this.fxLayers[i2].add(entityFX1);
    }

    public void updateEffects() {
        int i1 = 0;
        while (i1 < 4) {
            int i2 = 0;
            while (i2 < this.fxLayers[i1].size()) {
                EntityFX entityFX3 = (EntityFX)this.fxLayers[i1].get(i2);
                entityFX3.onUpdate();
                if (entityFX3.isDead) {
                    this.fxLayers[i1].remove(i2--);
                }
                ++i2;
            }
            ++i1;
        }
    }

    public void renderParticles(Entity entity1, float f2) {
        float f3 = MathHelper.cos(entity1.rotationYaw * (float)Math.PI / 180.0f);
        float f4 = MathHelper.sin(entity1.rotationYaw * (float)Math.PI / 180.0f);
        float f5 = -f4 * MathHelper.sin(entity1.rotationPitch * (float)Math.PI / 180.0f);
        float f6 = f3 * MathHelper.sin(entity1.rotationPitch * (float)Math.PI / 180.0f);
        float f7 = MathHelper.cos(entity1.rotationPitch * (float)Math.PI / 180.0f);
        EntityFX.interpPosX = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * (double)f2;
        EntityFX.interpPosY = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * (double)f2;
        EntityFX.interpPosZ = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * (double)f2;
        int i8 = 0;
        while (i8 < 3) {
            if (this.fxLayers[i8].size() != 0) {
                int i9 = 0;
                if (i8 == 0) {
                    i9 = this.renderer.getTexture("/particles.png");
                }
                if (i8 == 1) {
                    i9 = this.renderer.getTexture("/terrain.png");
                }
                if (i8 == 2) {
                    i9 = this.renderer.getTexture("/gui/items.png");
                }
                GL11.glBindTexture((int)3553, (int)i9);
                Tessellator tessellator10 = Tessellator.instance;
                tessellator10.startDrawingQuads();
                int i11 = 0;
                while (i11 < this.fxLayers[i8].size()) {
                    EntityFX entityFX12 = (EntityFX)this.fxLayers[i8].get(i11);
                    entityFX12.renderParticle(tessellator10, f2, f3, f7, f4, f5, f6);
                    ++i11;
                }
                tessellator10.draw();
            }
            ++i8;
        }
    }

    public void func_1187_b(Entity entity1, float f2) {
        int b3 = 3;
        if (this.fxLayers[b3].size() != 0) {
            Tessellator tessellator4 = Tessellator.instance;
            int i5 = 0;
            while (i5 < this.fxLayers[b3].size()) {
                EntityFX entityFX6 = (EntityFX)this.fxLayers[b3].get(i5);
                entityFX6.renderParticle(tessellator4, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                ++i5;
            }
        }
    }

    public void clearEffects(World world1) {
        this.worldObj = world1;
        int i2 = 0;
        while (i2 < 4) {
            this.fxLayers[i2].clear();
            ++i2;
        }
    }

    public void addBlockDestroyEffects(int i1, int i2, int i3) {
        int i4 = this.worldObj.getBlockId(i1, i2, i3);
        if (i4 != 0) {
            Block block5 = Block.blocksList[i4];
            int b6 = 4;
            int i7 = 0;
            while (i7 < b6) {
                int i8 = 0;
                while (i8 < b6) {
                    int i9 = 0;
                    while (i9 < b6) {
                        double d10 = (double)i1 + ((double)i7 + 0.5) / (double)b6;
                        double d12 = (double)i2 + ((double)i8 + 0.5) / (double)b6;
                        double d14 = (double)i3 + ((double)i9 + 0.5) / (double)b6;
                        this.addEffect(new EntityDiggingFX(this.worldObj, d10, d12, d14, d10 - (double)i1 - 0.5, d12 - (double)i2 - 0.5, d14 - (double)i3 - 0.5, block5).func_4041_a(i1, i2, i3));
                        ++i9;
                    }
                    ++i8;
                }
                ++i7;
            }
        }
    }

    public void addBlockHitEffects(int i1, int i2, int i3, int i4) {
        int i5 = this.worldObj.getBlockId(i1, i2, i3);
        if (i5 != 0) {
            Block block6 = Block.blocksList[i5];
            float f7 = 0.1f;
            double d8 = (double)i1 + this.rand.nextDouble() * (block6.maxX - block6.minX - (double)(f7 * 2.0f)) + (double)f7 + block6.minX;
            double d10 = (double)i2 + this.rand.nextDouble() * (block6.maxY - block6.minY - (double)(f7 * 2.0f)) + (double)f7 + block6.minY;
            double d12 = (double)i3 + this.rand.nextDouble() * (block6.maxZ - block6.minZ - (double)(f7 * 2.0f)) + (double)f7 + block6.minZ;
            if (i4 == 0) {
                d10 = (double)i2 + block6.minY - (double)f7;
            }
            if (i4 == 1) {
                d10 = (double)i2 + block6.maxY + (double)f7;
            }
            if (i4 == 2) {
                d12 = (double)i3 + block6.minZ - (double)f7;
            }
            if (i4 == 3) {
                d12 = (double)i3 + block6.maxZ + (double)f7;
            }
            if (i4 == 4) {
                d8 = (double)i1 + block6.minX - (double)f7;
            }
            if (i4 == 5) {
                d8 = (double)i1 + block6.maxX + (double)f7;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, d8, d10, d12, 0.0, 0.0, 0.0, block6).func_4041_a(i1, i2, i3).func_407_b(0.2f).func_405_d(0.6f));
        }
    }

    public String getStatistics() {
        return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
    }
}

