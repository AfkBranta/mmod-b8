/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityFX;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class EntityPickupFX
extends EntityFX {
    private Entity field_675_a;
    private Entity field_679_o;
    private int field_678_p = 0;
    private int field_677_q = 0;
    private float field_676_r;

    public EntityPickupFX(World world1, Entity entity2, Entity entity3, float f4) {
        super(world1, entity2.posX, entity2.posY, entity2.posZ, entity2.motionX, entity2.motionY, entity2.motionZ);
        this.field_675_a = entity2;
        this.field_679_o = entity3;
        this.field_677_q = 3;
        this.field_676_r = f4;
    }

    @Override
    public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8 = ((float)this.field_678_p + f2) / (float)this.field_677_q;
        f8 *= f8;
        double d9 = this.field_675_a.posX;
        double d11 = this.field_675_a.posY;
        double d13 = this.field_675_a.posZ;
        double d15 = this.field_679_o.lastTickPosX + (this.field_679_o.posX - this.field_679_o.lastTickPosX) * (double)f2;
        double d17 = this.field_679_o.lastTickPosY + (this.field_679_o.posY - this.field_679_o.lastTickPosY) * (double)f2 + (double)this.field_676_r;
        double d19 = this.field_679_o.lastTickPosZ + (this.field_679_o.posZ - this.field_679_o.lastTickPosZ) * (double)f2;
        double d21 = d9 + (d15 - d9) * (double)f8;
        double d23 = d11 + (d17 - d11) * (double)f8;
        double d25 = d13 + (d19 - d13) * (double)f8;
        int i27 = MathHelper.floor_double(d21);
        int i28 = MathHelper.floor_double(d23 + (double)(this.yOffset / 2.0f));
        int i29 = MathHelper.floor_double(d25);
        float f30 = this.worldObj.getLightBrightness(i27, i28, i29);
        GL11.glColor4f((float)f30, (float)f30, (float)f30, (float)1.0f);
        RenderManager.instance.renderEntityWithPosYaw(this.field_675_a, (float)(d21 -= interpPosX), (float)(d23 -= interpPosY), (float)(d25 -= interpPosZ), this.field_675_a.rotationYaw, f2);
    }

    @Override
    public void onUpdate() {
        ++this.field_678_p;
        if (this.field_678_p == this.field_677_q) {
            this.setEntityDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}

