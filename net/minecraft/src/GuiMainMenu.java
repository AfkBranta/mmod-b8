/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.minecraft.src;

import MEDMEX.AltManager.GuiAltManager;
import MEDMEX.ServerManager.GuiServerManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.GuiTexturePacks;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiMainMenu
extends GuiScreen {
    private static final Random rand = new Random();
    private float updateCounter = 0.0f;
    private String splashText = "missingno";
    private GuiButton field_25096_l;

    public GuiMainMenu() {
        try {
            ArrayList<String> arrayList1 = new ArrayList<String>();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String string3 = "";
            while ((string3 = bufferedReader2.readLine()) != null) {
                if ((string3 = string3.trim()).length() <= 0) continue;
                arrayList1.add(string3);
            }
            this.splashText = (String)arrayList1.get(rand.nextInt(arrayList1.size()));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void updateScreen() {
        this.updateCounter += 1.0f;
    }

    @Override
    protected void keyTyped(char c1, int i2) {
    }

    @Override
    public void initGui() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        if (calendar1.get(2) + 1 == 11 && calendar1.get(5) == 9) {
            this.splashText = "Happy birthday, ez!";
        } else if (calendar1.get(2) + 1 == 6 && calendar1.get(5) == 1) {
            this.splashText = "Happy birthday, Notch!";
        } else if (calendar1.get(2) + 1 == 12 && calendar1.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar1.get(2) + 1 == 1 && calendar1.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        StringTranslate stringTranslate2 = StringTranslate.getInstance();
        int i4 = this.height / 4 + 48;
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, i4, stringTranslate2.translateKey("menu.singleplayer")));
        this.field_25096_l = new GuiButton(2, this.width / 2 - 100, i4 + 24, stringTranslate2.translateKey("menu.multiplayer"));
        this.controlList.add(this.field_25096_l);
        this.controlList.add(new GuiButton(3, this.width / 2 - 100, i4 + 48, stringTranslate2.translateKey("menu.mods")));
        if (this.mc.hideQuitButton) {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, i4 + 72, stringTranslate2.translateKey("menu.options")));
        } else {
            this.controlList.add(new GuiButton(0, this.width / 2 - 100, i4 + 72 + 12, 98, 20, stringTranslate2.translateKey("menu.options")));
            this.controlList.add(new GuiButton(4, this.width / 2 + 2, i4 + 72 + 12, 98, 20, stringTranslate2.translateKey("menu.quit")));
        }
        this.controlList.add(new GuiButton(7, this.width / 2 - 100, i4 + 94 + 12, 200, 20, "Account Manager"));
        if (this.mc.session == null) {
            this.field_25096_l.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (guiButton1.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (guiButton1.id == 2) {
            this.mc.displayGuiScreen(new GuiServerManager(this));
        }
        if (guiButton1.id == 3) {
            this.mc.displayGuiScreen(new GuiTexturePacks(this));
        }
        if (guiButton1.id == 4) {
            this.mc.shutdown();
        }
        if (guiButton1.id == 7) {
            this.mc.displayGuiScreen(new GuiAltManager(this));
        }
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        this.drawDefaultBackground();
        Tessellator tessellator4 = Tessellator.instance;
        int s5 = 274;
        int i6 = this.width / 2 - s5 / 2;
        int b7 = 30;
        GL11.glBindTexture((int)3553, (int)this.mc.renderEngine.getTexture("/title/mclogo.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(i6 + 0, b7 + 0, 0, 0, 155, 44);
        this.drawTexturedModalRect(i6 + 155, b7 + 0, 0, 45, 155, 44);
        tessellator4.setColorOpaque_I(0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), (float)70.0f, (float)0.0f);
        GL11.glRotatef((float)-20.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float f8 = 1.8f - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0f * (float)Math.PI * 2.0f) * 0.1f);
        f8 = f8 * 100.0f / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef((float)f8, (float)f8, (float)f8);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 0xFFFF00);
        GL11.glPopMatrix();
        this.drawString(this.fontRenderer, "Minecraft Beta 1.4_01", 2, 2, 0x505050);
        String string9 = "Copyright Mojang AB. Do not distribute.";
        this.drawString(this.fontRenderer, string9, this.width - this.fontRenderer.getStringWidth(string9) - 2, this.height - 10, 0xFFFFFF);
        super.drawScreen(i1, i2, f3);
    }
}

