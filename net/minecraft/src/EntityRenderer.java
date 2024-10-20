/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GLContext
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventRenderEntities;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.ClippingHelperImplementation;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityRainFX;
import net.minecraft.src.Frustrum;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MouseFilter;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class EntityRenderer {
    private Minecraft mc;
    private float farPlaneDistance = 0.0f;
    public ItemRenderer itemRenderer;
    private int field_1386_j;
    private Entity field_1385_k = null;
    private MouseFilter mouseFilterXAxis = new MouseFilter();
    private MouseFilter mouseFilterYAxis = new MouseFilter();
    private MouseFilter field_22233_n = new MouseFilter();
    private MouseFilter field_22232_o = new MouseFilter();
    private MouseFilter field_22231_p = new MouseFilter();
    private MouseFilter field_22229_q = new MouseFilter();
    private float field_22228_r = 4.0f;
    private float field_22227_s = 4.0f;
    private float field_22226_t = 0.0f;
    private float field_22225_u = 0.0f;
    private float field_22224_v = 0.0f;
    private float field_22223_w = 0.0f;
    private float field_22222_x = 0.0f;
    private float field_22221_y = 0.0f;
    private float field_22220_z = 0.0f;
    private float field_22230_A = 0.0f;
    private double cameraZoom = 1.0;
    private double cameraYaw = 0.0;
    private double cameraPitch = 0.0;
    private long prevFrameTime = System.currentTimeMillis();
    private Random random = new Random();
    volatile int field_1394_b = 0;
    volatile int field_1393_c = 0;
    FloatBuffer field_1392_d = GLAllocation.createDirectFloatBuffer(16);
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float field_1382_n;
    private float field_1381_o;

    public EntityRenderer(Minecraft minecraft1) {
        this.mc = minecraft1;
        this.itemRenderer = new ItemRenderer(minecraft1);
    }

    public void updateRenderer() {
        this.field_1382_n = this.field_1381_o;
        this.field_22227_s = this.field_22228_r;
        this.field_22225_u = this.field_22226_t;
        this.field_22223_w = this.field_22224_v;
        this.field_22221_y = this.field_22222_x;
        this.field_22230_A = this.field_22220_z;
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        float f1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
        float f2 = (float)(3 - this.mc.gameSettings.renderDistance) / 3.0f;
        float f3 = f1 * (1.0f - f2) + f2;
        this.field_1381_o += (f3 - this.field_1381_o) * 0.1f;
        ++this.field_1386_j;
        this.itemRenderer.updateEquippedItem();
        if (this.mc.isRaining) {
            this.addRainParticles();
        }
    }

    public void getMouseOver(float f1) {
        if (this.mc.renderViewEntity != null && this.mc.theWorld != null) {
            double d2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d2, f1);
            double d4 = d2;
            Vec3D vec3D6 = this.mc.renderViewEntity.getPosition(f1);
            if (this.mc.objectMouseOver != null) {
                d4 = this.mc.objectMouseOver.hitVec.distanceTo(vec3D6);
            }
            if (this.mc.playerController instanceof PlayerControllerTest) {
                d2 = 32.0;
                d4 = 32.0;
            } else {
                if (d4 > 3.0) {
                    d4 = 3.0;
                }
                d2 = d4;
            }
            Vec3D vec3D7 = this.mc.renderViewEntity.getLook(f1);
            Vec3D vec3D8 = vec3D6.addVector(vec3D7.xCoord * d2, vec3D7.yCoord * d2, vec3D7.zCoord * d2);
            this.field_1385_k = null;
            float f9 = 1.0f;
            List list10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec3D7.xCoord * d2, vec3D7.yCoord * d2, vec3D7.zCoord * d2).expand(f9, f9, f9));
            double d11 = 0.0;
            int i13 = 0;
            while (i13 < list10.size()) {
                Entity entity14 = (Entity)list10.get(i13);
                if (entity14.canBeCollidedWith()) {
                    double d18;
                    float f15 = entity14.getCollisionBorderSize();
                    AxisAlignedBB axisAlignedBB16 = entity14.boundingBox.expand(f15, f15, f15);
                    MovingObjectPosition movingObjectPosition17 = axisAlignedBB16.func_1169_a(vec3D6, vec3D8);
                    if (axisAlignedBB16.isVecInside(vec3D6)) {
                        if (0.0 < d11 || d11 == 0.0) {
                            this.field_1385_k = entity14;
                            d11 = 0.0;
                        }
                    } else if (movingObjectPosition17 != null && ((d18 = vec3D6.distanceTo(movingObjectPosition17.hitVec)) < d11 || d11 == 0.0)) {
                        this.field_1385_k = entity14;
                        d11 = d18;
                    }
                }
                ++i13;
            }
            if (this.field_1385_k != null && !(this.mc.playerController instanceof PlayerControllerTest)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.field_1385_k);
            }
        }
    }

    private float func_914_d(float f1) {
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        float f3 = 70.0f;
        if (entityLiving2.isInsideOfMaterial(Material.water)) {
            f3 = 60.0f;
        }
        if (entityLiving2.health <= 0) {
            float f4 = (float)entityLiving2.deathTime + f1;
            f3 /= (1.0f - 500.0f / (f4 + 500.0f)) * 2.0f + 1.0f;
        }
        return f3 + this.field_22221_y + (this.field_22222_x - this.field_22221_y) * f1;
    }

    private void hurtCameraEffect(float f1) {
        float f4;
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        float f3 = (float)entityLiving2.hurtTime - f1;
        if (entityLiving2.health <= 0) {
            f4 = (float)entityLiving2.deathTime + f1;
            GL11.glRotatef((float)(40.0f - 8000.0f / (f4 + 200.0f)), (float)0.0f, (float)0.0f, (float)1.0f);
        }
        if (f3 >= 0.0f) {
            f3 /= (float)entityLiving2.maxHurtTime;
            f3 = MathHelper.sin(f3 * f3 * f3 * f3 * (float)Math.PI);
            f4 = entityLiving2.attackedAtYaw;
            GL11.glRotatef((float)(-f4), (float)0.0f, (float)1.0f, (float)0.0f);
            GL11.glRotatef((float)(-f3 * 14.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)f4, (float)0.0f, (float)1.0f, (float)0.0f);
        }
    }

    private void setupViewBobbing(float f1) {
        if (this.mc.renderViewEntity instanceof EntityPlayer) {
            EntityPlayer entityPlayer2 = (EntityPlayer)this.mc.renderViewEntity;
            float f3 = entityPlayer2.distanceWalkedModified - entityPlayer2.prevDistanceWalkedModified;
            float f4 = -(entityPlayer2.distanceWalkedModified + f3 * f1);
            float f5 = entityPlayer2.field_775_e + (entityPlayer2.field_774_f - entityPlayer2.field_775_e) * f1;
            float f6 = entityPlayer2.cameraPitch + (entityPlayer2.field_9328_R - entityPlayer2.cameraPitch) * f1;
            GL11.glTranslatef((float)(MathHelper.sin(f4 * (float)Math.PI) * f5 * 0.5f), (float)(-Math.abs(MathHelper.cos(f4 * (float)Math.PI) * f5)), (float)0.0f);
            GL11.glRotatef((float)(MathHelper.sin(f4 * (float)Math.PI) * f5 * 3.0f), (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glRotatef((float)(Math.abs(MathHelper.cos(f4 * (float)Math.PI - 0.2f) * f5) * 5.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)f6, (float)1.0f, (float)0.0f, (float)0.0f);
        }
    }

    private void orientCamera(float f1) {
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        float f3 = entityLiving2.yOffset - 1.62f;
        double d4 = entityLiving2.prevPosX + (entityLiving2.posX - entityLiving2.prevPosX) * (double)f1;
        double d6 = entityLiving2.prevPosY + (entityLiving2.posY - entityLiving2.prevPosY) * (double)f1 - (double)f3;
        double d8 = entityLiving2.prevPosZ + (entityLiving2.posZ - entityLiving2.prevPosZ) * (double)f1;
        GL11.glRotatef((float)(this.field_22230_A + (this.field_22220_z - this.field_22230_A) * f1), (float)0.0f, (float)0.0f, (float)1.0f);
        if (entityLiving2.isPlayerSleeping()) {
            f3 = (float)((double)f3 + 1.0);
            GL11.glTranslatef((float)0.0f, (float)0.3f, (float)0.0f);
            if (!this.mc.gameSettings.field_22273_E) {
                int i10 = this.mc.theWorld.getBlockId(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
                if (i10 == Block.blockBed.blockID) {
                    int i11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
                    int i12 = i11 & 3;
                    GL11.glRotatef((float)(i12 * 90), (float)0.0f, (float)1.0f, (float)0.0f);
                }
                GL11.glRotatef((float)(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0f), (float)0.0f, (float)-1.0f, (float)0.0f);
                GL11.glRotatef((float)(entityLiving2.prevRotationPitch + (entityLiving2.rotationPitch - entityLiving2.prevRotationPitch) * f1), (float)-1.0f, (float)0.0f, (float)0.0f);
            }
        } else if (this.mc.gameSettings.thirdPersonView) {
            double d27 = this.field_22227_s + (this.field_22228_r - this.field_22227_s) * f1;
            if (this.mc.gameSettings.field_22273_E) {
                float f28 = this.field_22225_u + (this.field_22226_t - this.field_22225_u) * f1;
                float f13 = this.field_22223_w + (this.field_22224_v - this.field_22223_w) * f1;
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(-d27)));
                GL11.glRotatef((float)f13, (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)f28, (float)0.0f, (float)1.0f, (float)0.0f);
            } else {
                float f28 = entityLiving2.rotationYaw;
                float f13 = entityLiving2.rotationPitch;
                double d14 = (double)(-MathHelper.sin(f28 / 180.0f * (float)Math.PI) * MathHelper.cos(f13 / 180.0f * (float)Math.PI)) * d27;
                double d16 = (double)(MathHelper.cos(f28 / 180.0f * (float)Math.PI) * MathHelper.cos(f13 / 180.0f * (float)Math.PI)) * d27;
                double d18 = (double)(-MathHelper.sin(f13 / 180.0f * (float)Math.PI)) * d27;
                int i20 = 0;
                while (i20 < 8) {
                    double d25;
                    MovingObjectPosition movingObjectPosition24;
                    float f21 = (i20 & 1) * 2 - 1;
                    float f22 = (i20 >> 1 & 1) * 2 - 1;
                    float f23 = (i20 >> 2 & 1) * 2 - 1;
                    if ((movingObjectPosition24 = this.mc.theWorld.rayTraceBlocks(Vec3D.createVector(d4 + (double)(f21 *= 0.1f), d6 + (double)(f22 *= 0.1f), d8 + (double)(f23 *= 0.1f)), Vec3D.createVector(d4 - d14 + (double)f21 + (double)f23, d6 - d18 + (double)f22, d8 - d16 + (double)f23))) != null && (d25 = movingObjectPosition24.hitVec.distanceTo(Vec3D.createVector(d4, d6, d8))) < d27) {
                        d27 = d25;
                    }
                    ++i20;
                }
                GL11.glRotatef((float)(entityLiving2.rotationPitch - f13), (float)1.0f, (float)0.0f, (float)0.0f);
                GL11.glRotatef((float)(entityLiving2.rotationYaw - f28), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glTranslatef((float)0.0f, (float)0.0f, (float)((float)(-d27)));
                GL11.glRotatef((float)(f28 - entityLiving2.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
                GL11.glRotatef((float)(f13 - entityLiving2.rotationPitch), (float)1.0f, (float)0.0f, (float)0.0f);
            }
        } else {
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.1f);
        }
        if (!this.mc.gameSettings.field_22273_E) {
            GL11.glRotatef((float)(entityLiving2.prevRotationPitch + (entityLiving2.rotationPitch - entityLiving2.prevRotationPitch) * f1), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glRotatef((float)(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        }
        GL11.glTranslatef((float)0.0f, (float)f3, (float)0.0f);
    }

    public void func_21152_a(double d1, double d3, double d5) {
        this.cameraZoom = d1;
        this.cameraYaw = d3;
        this.cameraPitch = d5;
    }

    public void resetZoom() {
        this.cameraZoom = 1.0;
    }

    private void setupCameraTransform(float f1, int i2) {
        float f4;
        this.farPlaneDistance = 256 >> this.mc.gameSettings.renderDistance;
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        float f3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(-(i2 * 2 - 1)) * f3), (float)0.0f, (float)0.0f);
        }
        if (this.cameraZoom != 1.0) {
            GL11.glTranslatef((float)((float)this.cameraYaw), (float)((float)(-this.cameraPitch)), (float)0.0f);
            GL11.glScaled((double)this.cameraZoom, (double)this.cameraZoom, (double)1.0);
            GLU.gluPerspective((float)this.func_914_d(f1), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)this.farPlaneDistance);
        } else {
            GLU.gluPerspective((float)this.func_914_d(f1), (float)((float)this.mc.displayWidth / (float)this.mc.displayHeight), (float)0.05f, (float)this.farPlaneDistance);
        }
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(i2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
        }
        this.hurtCameraEffect(f1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(f1);
        }
        if ((f4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * f1) > 0.0f) {
            float f5 = 5.0f / (f4 * f4 + 5.0f) - f4 * 0.04f;
            f5 *= f5;
            GL11.glRotatef((float)(f4 * f4 * 1500.0f), (float)0.0f, (float)1.0f, (float)1.0f);
            GL11.glScalef((float)(1.0f / f5), (float)1.0f, (float)1.0f);
            GL11.glRotatef((float)(-f4 * f4 * 1500.0f), (float)0.0f, (float)1.0f, (float)1.0f);
        }
        this.orientCamera(f1);
    }

    private void func_4135_b(float f1, int i2) {
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((float)((float)(i2 * 2 - 1) * 0.1f), (float)0.0f, (float)0.0f);
        }
        GL11.glPushMatrix();
        this.hurtCameraEffect(f1);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(f1);
        }
        if (!(this.mc.gameSettings.thirdPersonView || this.mc.renderViewEntity.isPlayerSleeping() || this.mc.gameSettings.hideGUI)) {
            this.itemRenderer.renderItemInFirstPerson(f1);
        }
        GL11.glPopMatrix();
        if (!this.mc.gameSettings.thirdPersonView && !this.mc.renderViewEntity.isPlayerSleeping()) {
            this.itemRenderer.renderOverlays(f1);
            this.hurtCameraEffect(f1);
        }
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(f1);
        }
    }

    public void updateCameraAndRender(float f1) {
        if (!Display.isActive()) {
            if (System.currentTimeMillis() - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        } else {
            this.prevFrameTime = System.currentTimeMillis();
        }
        if (this.mc.inGameHasFocus) {
            this.mc.mouseHelper.mouseXYChange();
            float f2 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float f3 = f2 * f2 * f2 * 8.0f;
            float f4 = (float)this.mc.mouseHelper.deltaX * f3;
            float f5 = (float)this.mc.mouseHelper.deltaY * f3;
            int b6 = 1;
            if (this.mc.gameSettings.invertMouse) {
                b6 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                f4 = this.mouseFilterXAxis.func_22386_a(f4, 0.05f * f3);
                f5 = this.mouseFilterYAxis.func_22386_a(f5, 0.05f * f3);
            }
            this.mc.thePlayer.func_346_d(f4, f5 * (float)b6);
        }
        if (!this.mc.field_6307_v) {
            ScaledResolution scaledResolution7 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int i8 = scaledResolution7.getScaledWidth();
            int i9 = scaledResolution7.getScaledHeight();
            int i10 = Mouse.getX() * i8 / this.mc.displayWidth;
            int i11 = i9 - Mouse.getY() * i9 / this.mc.displayHeight - 1;
            if (this.mc.theWorld != null) {
                this.renderWorld(f1);
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    this.mc.ingameGUI.renderGameOverlay(f1, this.mc.currentScreen != null, i10, i11);
                }
            } else {
                GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
                GL11.glMatrixMode((int)5889);
                GL11.glLoadIdentity();
                GL11.glMatrixMode((int)5888);
                GL11.glLoadIdentity();
                this.func_905_b();
            }
            if (this.mc.currentScreen != null) {
                GL11.glClear((int)256);
                this.mc.currentScreen.drawScreen(i10, i11, f1);
                if (this.mc.currentScreen != null && this.mc.currentScreen.field_25091_h != null) {
                    this.mc.currentScreen.field_25091_h.func_25087_a(f1);
                }
            }
        }
    }

    public void renderWorld(float f1) {
        int i14;
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        this.getMouseOver(f1);
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        RenderGlobal renderGlobal3 = this.mc.renderGlobal;
        EffectRenderer effectRenderer4 = this.mc.effectRenderer;
        double d5 = entityLiving2.lastTickPosX + (entityLiving2.posX - entityLiving2.lastTickPosX) * (double)f1;
        double d7 = entityLiving2.lastTickPosY + (entityLiving2.posY - entityLiving2.lastTickPosY) * (double)f1;
        double d9 = entityLiving2.lastTickPosZ + (entityLiving2.posZ - entityLiving2.lastTickPosZ) * (double)f1;
        IChunkProvider iChunkProvider11 = this.mc.theWorld.getIChunkProvider();
        if (iChunkProvider11 instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate12 = (ChunkProviderLoadOrGenerate)iChunkProvider11;
            int i13 = MathHelper.floor_float((int)d5) >> 4;
            i14 = MathHelper.floor_float((int)d9) >> 4;
            chunkProviderLoadOrGenerate12.setCurrentChunkOver(i13, i14);
        }
        int i15 = 0;
        while (i15 < 2) {
            EntityPlayer entityPlayer17;
            if (this.mc.gameSettings.anaglyph) {
                if (i15 == 0) {
                    GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                } else {
                    GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                }
            }
            GL11.glViewport((int)0, (int)0, (int)this.mc.displayWidth, (int)this.mc.displayHeight);
            this.updateFogColor(f1);
            GL11.glClear((int)16640);
            GL11.glEnable((int)2884);
            this.setupCameraTransform(f1, i15);
            ClippingHelperImplementation.getInstance();
            if (this.mc.gameSettings.renderDistance < 2) {
                this.setupFog(-1);
                renderGlobal3.renderSky(f1);
            }
            GL11.glEnable((int)2912);
            this.setupFog(1);
            if (this.mc.gameSettings.ambientOcclusion) {
                GL11.glShadeModel((int)7425);
            }
            Frustrum frustrum16 = new Frustrum();
            frustrum16.setPosition(d5, d7, d9);
            this.mc.renderGlobal.clipRenderersByFrustrum(frustrum16, f1);
            this.mc.renderGlobal.updateRenderers(entityLiving2, false);
            this.setupFog(0);
            GL11.glEnable((int)2912);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            RenderHelper.disableStandardItemLighting();
            renderGlobal3.sortAndRender(entityLiving2, 0, f1);
            GL11.glShadeModel((int)7424);
            RenderHelper.enableStandardItemLighting();
            renderGlobal3.renderEntities(entityLiving2.getPosition(f1), frustrum16, f1);
            EventRenderEntities event = new EventRenderEntities(f1);
            Client.onEvent(event);
            effectRenderer4.func_1187_b(entityLiving2, f1);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0);
            effectRenderer4.renderParticles(entityLiving2, f1);
            if (this.mc.objectMouseOver != null && entityLiving2.isInsideOfMaterial(Material.water) && entityLiving2 instanceof EntityPlayer) {
                entityPlayer17 = (EntityPlayer)entityLiving2;
                GL11.glDisable((int)3008);
                renderGlobal3.func_959_a(entityPlayer17, this.mc.objectMouseOver, 0, entityPlayer17.inventory.getCurrentItem(), f1);
                renderGlobal3.drawSelectionBox(entityPlayer17, this.mc.objectMouseOver, 0, entityPlayer17.inventory.getCurrentItem(), f1);
                GL11.glEnable((int)3008);
            }
            GL11.glBlendFunc((int)770, (int)771);
            this.setupFog(0);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2884);
            GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/terrain.png"));
            if (this.mc.gameSettings.fancyGraphics) {
                GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
                i14 = renderGlobal3.sortAndRender(entityLiving2, 1, f1);
                GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
                if (this.mc.gameSettings.anaglyph) {
                    if (i15 == 0) {
                        GL11.glColorMask((boolean)false, (boolean)true, (boolean)true, (boolean)false);
                    } else {
                        GL11.glColorMask((boolean)true, (boolean)false, (boolean)false, (boolean)false);
                    }
                }
                if (i14 > 0) {
                    renderGlobal3.func_944_a(1, f1);
                }
            } else {
                renderGlobal3.sortAndRender(entityLiving2, 1, f1);
            }
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            if (this.cameraZoom == 1.0 && entityLiving2 instanceof EntityPlayer && this.mc.objectMouseOver != null && !entityLiving2.isInsideOfMaterial(Material.water)) {
                entityPlayer17 = (EntityPlayer)entityLiving2;
                GL11.glDisable((int)3008);
                renderGlobal3.func_959_a(entityPlayer17, this.mc.objectMouseOver, 0, entityPlayer17.inventory.getCurrentItem(), f1);
                renderGlobal3.drawSelectionBox(entityPlayer17, this.mc.objectMouseOver, 0, entityPlayer17.inventory.getCurrentItem(), f1);
                GL11.glEnable((int)3008);
            }
            GL11.glDisable((int)2912);
            if (this.field_1385_k != null) {
                // empty if block
            }
            this.setupFog(0);
            GL11.glEnable((int)2912);
            renderGlobal3.renderClouds(f1);
            GL11.glDisable((int)2912);
            this.setupFog(1);
            if (this.cameraZoom == 1.0) {
                GL11.glClear((int)256);
                this.func_4135_b(f1, i15);
            }
            if (!this.mc.gameSettings.anaglyph) {
                return;
            }
            ++i15;
        }
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)false);
    }

    private void addRainParticles() {
        if (this.mc.gameSettings.fancyGraphics) {
            EntityLiving entityLiving1 = this.mc.renderViewEntity;
            World world2 = this.mc.theWorld;
            int i3 = MathHelper.floor_double(entityLiving1.posX);
            int i4 = MathHelper.floor_double(entityLiving1.posY);
            int i5 = MathHelper.floor_double(entityLiving1.posZ);
            int b6 = 16;
            int i7 = 0;
            while (i7 < 150) {
                int i8 = i3 + this.random.nextInt(b6) - this.random.nextInt(b6);
                int i9 = i5 + this.random.nextInt(b6) - this.random.nextInt(b6);
                int i10 = world2.func_696_e(i8, i9);
                int i11 = world2.getBlockId(i8, i10 - 1, i9);
                if (i10 <= i4 + b6 && i10 >= i4 - b6) {
                    float f12 = this.random.nextFloat();
                    float f13 = this.random.nextFloat();
                    if (i11 > 0) {
                        this.mc.effectRenderer.addEffect(new EntityRainFX(world2, (float)i8 + f12, (double)((float)i10 + 0.1f) - Block.blocksList[i11].minY, (float)i9 + f13));
                    }
                }
                ++i7;
            }
        }
    }

    public void func_905_b() {
        ScaledResolution scaledResolution1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)scaledResolution1.field_25121_a, (double)scaledResolution1.field_25120_b, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    private void updateFogColor(float f1) {
        World world2 = this.mc.theWorld;
        EntityLiving entityLiving3 = this.mc.renderViewEntity;
        float f4 = 1.0f / (float)(4 - this.mc.gameSettings.renderDistance);
        f4 = 1.0f - (float)Math.pow(f4, 0.25);
        Vec3D vec3D5 = world2.func_4079_a(this.mc.renderViewEntity, f1);
        float f6 = (float)vec3D5.xCoord;
        float f7 = (float)vec3D5.yCoord;
        float f8 = (float)vec3D5.zCoord;
        Vec3D vec3D9 = world2.getFogColor(f1);
        this.fogColorRed = (float)vec3D9.xCoord;
        this.fogColorGreen = (float)vec3D9.yCoord;
        this.fogColorBlue = (float)vec3D9.zCoord;
        this.fogColorRed += (f6 - this.fogColorRed) * f4;
        this.fogColorGreen += (f7 - this.fogColorGreen) * f4;
        this.fogColorBlue += (f8 - this.fogColorBlue) * f4;
        if (entityLiving3.isInsideOfMaterial(Material.water)) {
            this.fogColorRed = 0.02f;
            this.fogColorGreen = 0.02f;
            this.fogColorBlue = 0.2f;
        } else if (entityLiving3.isInsideOfMaterial(Material.lava)) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        float f10 = this.field_1382_n + (this.field_1381_o - this.field_1382_n) * f1;
        this.fogColorRed *= f10;
        this.fogColorGreen *= f10;
        this.fogColorBlue *= f10;
        if (this.mc.gameSettings.anaglyph) {
            float f11 = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            float f12 = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            float f13 = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = f11;
            this.fogColorGreen = f12;
            this.fogColorBlue = f13;
        }
        GL11.glClearColor((float)this.fogColorRed, (float)this.fogColorGreen, (float)this.fogColorBlue, (float)0.0f);
    }

    private void setupFog(int i1) {
        EntityLiving entityLiving2 = this.mc.renderViewEntity;
        GL11.glFog((int)2918, (FloatBuffer)this.func_908_a(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
        GL11.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (entityLiving2.isInsideOfMaterial(Material.water)) {
            GL11.glFogi((int)2917, (int)2048);
            GL11.glFogf((int)2914, (float)0.1f);
            float f3 = 0.4f;
            float f4 = 0.4f;
            float f5 = 0.9f;
            if (this.mc.gameSettings.anaglyph) {
                float f6 = (f3 * 30.0f + f4 * 59.0f + f5 * 11.0f) / 100.0f;
                float f7 = (f3 * 30.0f + f4 * 70.0f) / 100.0f;
                float f = (f3 * 30.0f + f5 * 70.0f) / 100.0f;
            }
        } else if (entityLiving2.isInsideOfMaterial(Material.lava)) {
            GL11.glFogi((int)2917, (int)2048);
            GL11.glFogf((int)2914, (float)2.0f);
            float f3 = 0.4f;
            float f4 = 0.3f;
            float f5 = 0.3f;
            if (this.mc.gameSettings.anaglyph) {
                float f6 = (f3 * 30.0f + f4 * 59.0f + f5 * 11.0f) / 100.0f;
                float f7 = (f3 * 30.0f + f4 * 70.0f) / 100.0f;
                float f = (f3 * 30.0f + f5 * 70.0f) / 100.0f;
            }
        } else {
            GL11.glFogi((int)2917, (int)9729);
            GL11.glFogf((int)2915, (float)(this.farPlaneDistance * 0.25f));
            GL11.glFogf((int)2916, (float)this.farPlaneDistance);
            if (i1 < 0) {
                GL11.glFogf((int)2915, (float)0.0f);
                GL11.glFogf((int)2916, (float)(this.farPlaneDistance * 0.8f));
            }
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi((int)34138, (int)34139);
            }
            if (this.mc.theWorld.worldProvider.field_4220_c) {
                GL11.glFogf((int)2915, (float)0.0f);
            }
        }
        GL11.glEnable((int)2903);
        GL11.glColorMaterial((int)1028, (int)4608);
    }

    private FloatBuffer func_908_a(float f1, float f2, float f3, float f4) {
        this.field_1392_d.clear();
        this.field_1392_d.put(f1).put(f2).put(f3).put(f4);
        this.field_1392_d.flip();
        return this.field_1392_d;
    }
}

