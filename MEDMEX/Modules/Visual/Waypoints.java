/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Settings.Setting;
import MEDMEX.Utils.PlayerUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Vec3D;
import MEDMEX.Utils.Waypoint;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class Waypoints
extends Module {
    public static Waypoints instance;
    public static CopyOnWriteArrayList<Waypoint> waypoints;

    static {
        waypoints = new CopyOnWriteArrayList();
    }

    public Waypoints() {
        super("Waypoints", "Waypoint saver", 0, Category.Visual);
        this.addSetting(new Setting("list", (Module)this, waypoints, "Waypoint"));
        instance = this;
    }

    @Override
    public void onEnable() {
        System.out.println(waypoints);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderEntities) {
            EventRenderEntities event = (EventRenderEntities)e;
            for (Waypoint w : waypoints) {
                this.renderWaypoint(w, event.getPartialTicks());
            }
        }
    }

    public void renderWaypoint(Waypoint w, float partialTicks) {
        Vec3D pos = w.getPos();
        if (w.getDimension() == 0 && this.mc.theWorld.getBlockId((int)this.mc.thePlayer.posX, 127, (int)this.mc.thePlayer.posZ) == 7) {
            pos = pos.scale(0.125, 1.0, 0.125);
        }
        if (w.getDimension() == -1 && this.mc.thePlayer.dimension == 0) {
            pos.scale(8.0, 8.0, 8.0);
        }
        Vec3D ogPos = pos;
        Vec3D player = PlayerUtils.getPos();
        RenderUtils.drawCircle(pos, Color.RED, 1.0);
        Minecraft mc = Minecraft.theMinecraft;
        if (Vec3D.getDistance(PlayerUtils.getPos(), pos) > 80.0) {
            Vec3D delta = new Vec3D(pos.getX() - player.getX(), pos.getY() - player.getY(), pos.getZ() - player.getZ());
            delta = delta.normalize();
            delta = delta.scale(80.0, 80.0, 80.0);
            pos = Vec3D.sum(delta, player);
        }
        float x = (float)pos.getX();
        float y = (float)pos.getY() + 2.0f;
        float z = (float)pos.getZ();
        y += 0.5f;
        if (RenderManager.instance.options == null) {
            return;
        }
        float p_189692_6_ = RenderManager.instance.playerViewY;
        float p_189692_7_ = RenderManager.instance.playerViewX;
        float p_189692_2_ = (float)((double)x - RenderManager.field_1222_l);
        float p_189692_3_ = (float)((double)y - RenderManager.field_1221_m);
        float p_189692_4_ = (float)((double)z - RenderManager.field_1220_n);
        FontRenderer p_189692_0_ = RenderManager.instance.getFontRenderer();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_189692_2_, (float)p_189692_3_, (float)p_189692_4_);
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-p_189692_6_), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(1.0f * p_189692_7_), (float)1.0f, (float)0.0f, (float)0.0f);
        float dist = (float)Vec3D.getDistance(PlayerUtils.getPos(), pos);
        if (dist < 20.0f) {
            GL11.glScalef((float)-0.05f, (float)-0.05f, (float)0.05f);
        } else {
            float mult = 14.0f / dist;
            GL11.glScalef((float)(-0.05f / mult), (float)(-0.05f / mult), (float)(-0.05f / mult));
        }
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        String dimension = w.getDimension() == 0 ? " \u00a72(O)" : " \u00a74(N)";
        String name = String.valueOf(w.getName()) + " [\u00a7d" + (int)Vec3D.getDistance(player, ogPos) + "\u00a7r]" + dimension;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int i = p_189692_0_.getStringWidth(name) / 2;
        GL11.glDisable((int)3553);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.1f, 0.1f, 0.1f, 0.5f);
        tessellator.addVertex(-i - 1, -1.0, 0.0);
        tessellator.addVertex(-i - 1, 8.0, 0.0);
        tessellator.addVertex(i + 1, 8.0, 0.0);
        tessellator.addVertex(i + 1, -1.0, 0.0);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.886f, 0.854f, 0.854f, 0.5f);
        tessellator.addVertex((double)(-i) - 1.5, -1.0, 0.0);
        tessellator.addVertex((double)(-i) - 1.5, 8.0, 0.0);
        tessellator.addVertex(-i - 1, 8.0, 0.0);
        tessellator.addVertex(-i - 1, -1.0, 0.0);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.886f, 0.854f, 0.854f, 0.5f);
        tessellator.addVertex(i + 1, -1.0, 0.0);
        tessellator.addVertex(i + 1, 8.0, 0.0);
        tessellator.addVertex((double)i + 1.5, 8.0, 0.0);
        tessellator.addVertex((double)i + 1.5, -1.0, 0.0);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.886f, 0.854f, 0.854f, 0.5f);
        tessellator.addVertex(-i - 1, -1.5, 0.0);
        tessellator.addVertex(-i - 1, -1.0, 0.0);
        tessellator.addVertex(i + 1, -1.0, 0.0);
        tessellator.addVertex(i + 1, -1.5, 0.0);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.886f, 0.854f, 0.854f, 0.5f);
        tessellator.addVertex(-i - 1, 8.0, 0.0);
        tessellator.addVertex(-i - 1, 8.5, 0.0);
        tessellator.addVertex(i + 1, 8.5, 0.0);
        tessellator.addVertex(i + 1, 8.0, 0.0);
        tessellator.draw();
        GL11.glEnable((int)3553);
        GL11.glDepthMask((boolean)true);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3042);
        p_189692_0_.drawString(name, -p_189692_0_.getStringWidth(name) / 2, 0.0f, 0xFFFFFF);
        GL11.glEnable((int)2929);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)0.05f);
        GL11.glPopMatrix();
    }

    @Override
    public void onDisable() {
    }
}

