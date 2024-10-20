/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.Modules.Visual;

import MEDMEX.Events.Event;
import MEDMEX.Events.events.EventRenderEntities;
import MEDMEX.Events.events.EventRenderNametag;
import MEDMEX.Modules.Category;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Player.Friends;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class Nametags
extends Module {
    public Nametags() {
        super("Nametags", "Big nametags on players", 0, Category.Visual);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventRenderNametag) {
            e.setCancelled(true);
        }
        if (e instanceof EventRenderEntities) {
            EventRenderEntities event = (EventRenderEntities)e;
            float partialTicks = event.getPartialTicks();
            List ents = this.mc.theWorld.loadedEntityList;
            for (Entity ent : ents) {
                if (!(ent instanceof EntityPlayer) || ent == this.mc.thePlayer) continue;
                float x = (float)(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)partialTicks);
                float y = (float)(ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)partialTicks);
                float z = (float)(ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)partialTicks);
                Nametags.renderNameTag((EntityPlayer)ent, 0.05f, partialTicks);
            }
        }
    }

    public static void renderNameTag(EntityPlayer e, float size, float partialTicks) {
        Minecraft mc = Minecraft.theMinecraft;
        double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)partialTicks;
        double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)partialTicks;
        double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)partialTicks;
        float x = (float)d0;
        boolean flag = e.isSneaking();
        float f2 = e.height + 0.5f - (flag ? 0.25f : 0.0f);
        float y = (float)d1 + f2;
        float z = (float)d2;
        y += size * 10.0f;
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
        float dist = mc.thePlayer.getDistanceToEntity(e);
        if (dist < 20.0f) {
            GL11.glScalef((float)(-size), (float)(-size), (float)size);
        } else {
            float mult = 14.0f / dist;
            GL11.glScalef((float)(-size / mult), (float)(-size / mult), (float)(-size / mult));
        }
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        String name = Nametags.getDisplayName(e);
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
        GL11.glScalef((float)-1.0f, (float)-1.0f, (float)size);
        GL11.glPopMatrix();
    }

    public static String getDisplayName(EntityPlayer e) {
        Minecraft mc = Minecraft.theMinecraft;
        String name = String.valueOf(e.username) + " ";
        int i = 0;
        while (i < 20) {
            name = i < e.health ? (e.health <= 6 ? String.valueOf(name) + "\u00a74:" : String.valueOf(name) + "\u00a72:") : String.valueOf(name) + "\u00a78:";
            i += 2;
        }
        if (Friends.instance.isEnabled && Friends.friends.contains(e.username.toLowerCase())) {
            name = "[\u00a7aF\u00a7r] " + name;
        }
        return name;
    }

    @Override
    public void onDisable() {
    }
}

