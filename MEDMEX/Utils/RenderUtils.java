/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.Utils;

import MEDMEX.Utils.GuiUtils;
import MEDMEX.Utils.Vec3D;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PathPoint;
import net.minecraft.src.RenderItem;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Slot;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void boundingESPBoxFilled(AxisAlignedBB box, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        double x = box.minX - RenderManager.renderPosX;
        double y = box.minY - RenderManager.renderPosY;
        double z = box.minZ - RenderManager.renderPosZ;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)(0.00390625f * (float)r), (double)(0.00390625f * (float)g), (double)(0.00390625f * (float)b), (double)(0.00390625f * (float)a));
        GL11.glBegin((int)7);
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x - box.minX + box.maxX, y - box.minY + box.maxY, z - box.minZ + box.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void boundingESPBox(AxisAlignedBB box, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        double x = box.minX - RenderManager.renderPosX;
        double y = box.minY - RenderManager.renderPosY;
        double z = box.minZ - RenderManager.renderPosZ;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)0.0, (double)1.0, (double)0.0, (double)0.15f);
        AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x - box.minX + box.maxX, y - box.minY + box.maxY, z - box.minZ + box.maxZ);
        RenderUtils.drawOutlinedBoundingBox(bb, 0.00390625f * (float)r, 0.00390625f * (float)g, 0.00390625f * (float)b, 0.00390625f * (float)a);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.setColorRGBA_F(red, green, blue, alpha);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.setColorRGBA_F(red, green, blue, alpha);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.setColorRGBA_F(red, green, blue, alpha);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        tessellator.draw();
    }

    public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        double newx = x - RenderManager.renderPosX;
        double newy = y - RenderManager.renderPosY;
        double newz = z - RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)newx, (double)newy, (double)newz);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawTracerLine(AxisAlignedBB box, Color c, float lineWdith) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        double newx = box.minX + (box.maxX - box.minX) / 2.0 - RenderManager.renderPosX;
        double newy = box.minY + (box.maxY - box.minY) / 2.0 - RenderManager.renderPosY;
        double newz = box.minZ + (box.maxZ - box.minZ) / 2.0 - RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4d((double)(0.00390625f * (float)r), (double)(0.00390625f * (float)g), (double)(0.00390625f * (float)b), (double)(0.00390625f * (float)a));
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)newx, (double)newy, (double)newz);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawLineFromTo(Vec3D from, Vec3D to, float red, float green, float blue, float alpha, float lineWdith) {
        double fromx = from.getX() - RenderManager.renderPosX;
        double fromy = from.getY() - RenderManager.renderPosY;
        double fromz = from.getZ() - RenderManager.renderPosZ;
        double tox = to.getX() - RenderManager.renderPosX;
        double toy = to.getY() - RenderManager.renderPosY;
        double toz = to.getZ() - RenderManager.renderPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4d((double)(0.00390625f * red), (double)(0.00390625f * green), (double)(0.00390625f * blue), (double)(0.00390625f * alpha));
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)fromx, (double)fromy, (double)fromz);
        GL11.glVertex3d((double)tox, (double)toy, (double)toz);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawRotationLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glEnd();
        GL11.glBegin((int)2);
        int i = 0;
        while (i < 360) {
            GL11.glVertex3d((double)x, (double)y, (double)z);
            ++i;
        }
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawSlotInventory(Slot slot1) {
        int i5;
        Minecraft mc = Minecraft.theMinecraft;
        RenderItem itemRenderer = GuiContainer.itemRenderer;
        FontRenderer fontRenderer = mc.fontRenderer;
        int i2 = slot1.xDisplayPosition;
        int i3 = slot1.yDisplayPosition;
        ItemStack itemStack4 = slot1.getStack();
        if (itemStack4 == null && (i5 = slot1.getBackgroundIconIndex()) >= 0) {
            GL11.glDisable((int)2896);
            mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
            GuiUtils.drawTexturedModalRect(i2, i3, i5 % 16 * 16, i5 / 16 * 16, 16, 16);
            GL11.glEnable((int)2896);
            return;
        }
        itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemStack4, i2, i3);
        itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemStack4, i2, i3);
    }

    public static void renderLineList(ArrayList<Vec3D> list, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBegin((int)1);
        GL11.glColor4d((double)(0.00390625f * (float)r), (double)(0.00390625f * (float)g), (double)(0.00390625f * (float)b), (double)(0.00390625f * (float)a));
        int i = 0;
        while (i < list.size() - 1) {
            double x = list.get(i).getX() + (list.get(i).getX() > 0.0 ? 0.5 : -0.5) - RenderManager.field_1222_l;
            double y = list.get(i).getY() - RenderManager.field_1221_m;
            double z = list.get(i).getZ() + (list.get(i).getZ() > 0.0 ? 0.5 : -0.5) - RenderManager.field_1220_n;
            GL11.glVertex3d((double)x, (double)y, (double)z);
            x = list.get(i + 1).getX() + (list.get(i + 1).getX() > 0.0 ? 0.5 : -0.5) - RenderManager.field_1222_l;
            y = list.get(i + 1).getY() - RenderManager.field_1221_m;
            z = list.get(i + 1).getZ() + (list.get(i + 1).getZ() > 0.0 ? 0.5 : -0.5) - RenderManager.field_1220_n;
            GL11.glVertex3d((double)x, (double)y, (double)z);
            ++i;
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void drawBreakingBlock(AxisAlignedBB box, Color c) {
        Minecraft mc = Minecraft.theMinecraft;
        RenderUtils.boundingESPBox(box, c);
        double phase = Math.abs(Math.sin(0.08 * (double)mc.thePlayer.ticksExisted)) * (box.maxY - box.minY);
        AxisAlignedBB slice = new AxisAlignedBB(box.minX, box.minY + phase, box.minZ, box.maxX, box.minY + phase + 0.001, box.maxZ);
        RenderUtils.boundingESPBoxFilled(slice, c);
    }

    public static void renderLineListPath(PathPoint[] list, Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = c.getAlpha();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glBegin((int)1);
        GL11.glColor4d((double)(0.00390625f * (float)r), (double)(0.00390625f * (float)g), (double)(0.00390625f * (float)b), (double)(0.00390625f * (float)a));
        int i = 0;
        while (i < list.length - 1) {
            PathPoint p = list[i];
            double x = (double)list[i].xCoord + (list[i].xCoord > 0 ? 0.5 : -0.5) - RenderManager.renderPosX;
            double y = (double)list[i].yCoord + 0.5 - RenderManager.renderPosY;
            double z = (double)list[i].zCoord + (list[i].zCoord > 0 ? 0.5 : -0.5) - RenderManager.renderPosZ;
            GL11.glVertex3d((double)x, (double)y, (double)z);
            x = (double)list[i + 1].xCoord + (list[i + 1].xCoord > 0 ? 0.5 : -0.5) - RenderManager.renderPosX;
            y = (double)list[i + 1].yCoord + 0.5 - RenderManager.renderPosY;
            z = (double)list[i + 1].zCoord + (list[i + 1].zCoord > 0 ? 0.5 : -0.5) - RenderManager.renderPosZ;
            GL11.glVertex3d((double)x, (double)y, (double)z);
            ++i;
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void drawCircle(Vec3D pos, Color c, double rad) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)3);
        double x = pos.getX() - RenderManager.field_1222_l;
        double y = pos.getY() - RenderManager.field_1221_m;
        double z = pos.getZ() - RenderManager.field_1220_n;
        double pix2 = Math.PI * 2;
        int j = -20;
        while (j < 20) {
            int i = 0;
            while (i <= 90) {
                GL11.glColor4f((float)c.getRed(), (float)c.getGreen(), (float)c.getBlue(), (float)c.getAlpha());
                GL11.glVertex3d((double)(x + rad * Math.cos((double)i * (Math.PI * 2) / 45.0)), (double)(y + (double)j * 0.001), (double)(z + rad * Math.sin((double)i * (Math.PI * 2) / 45.0)));
                ++i;
            }
            ++j;
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }
}

