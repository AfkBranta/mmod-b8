/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import java.util.Random;
import net.minecraft.src.FontAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiDisableButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.MathHelper;
import net.minecraft.src.PlayerControllerSP;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld
extends GuiScreen {
    private GuiScreen field_22131_a;
    private GuiDisableButton textboxWorldName;
    private GuiDisableButton textboxSeed;
    private String folderName;
    private boolean createClicked;

    public GuiCreateWorld(GuiScreen guiScreen1) {
        this.field_22131_a = guiScreen1;
    }

    @Override
    public void updateScreen() {
        this.textboxWorldName.updateCursorCounter();
        this.textboxSeed.updateCursorCounter();
    }

    @Override
    public void initGui() {
        StringTranslate stringTranslate1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringTranslate1.translateKey("selectWorld.create")));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
        this.textboxWorldName = new GuiDisableButton(this.fontRenderer, this.width / 2 - 100, 60, 200, 20, stringTranslate1.translateKey("selectWorld.newWorld"));
        this.textboxWorldName.isFocused = true;
        this.textboxWorldName.setMaxStringLength(32);
        this.textboxSeed = new GuiDisableButton(this.fontRenderer, this.width / 2 - 100, 116, 200, 20, "");
        this.func_22129_j();
    }

    private void func_22129_j() {
        this.folderName = this.textboxWorldName.getText().trim();
        char[] c1 = FontAllowedCharacters.field_22286_b;
        int i2 = c1.length;
        int i3 = 0;
        while (i3 < i2) {
            char c4 = c1[i3];
            this.folderName = this.folderName.replace(c4, '_');
            ++i3;
        }
        if (MathHelper.stringNullOrLengthZero(this.folderName)) {
            this.folderName = "World";
        }
        this.folderName = GuiCreateWorld.func_25097_a(this.mc.getSaveLoader(), this.folderName);
    }

    public static String func_25097_a(ISaveFormat iSaveFormat0, String string1) {
        while (iSaveFormat0.func_22173_b(string1) != null) {
            string1 = String.valueOf(string1) + "-";
        }
        return string1;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.enabled) {
            if (guiButton1.id == 1) {
                this.mc.displayGuiScreen(this.field_22131_a);
            } else if (guiButton1.id == 0) {
                this.mc.displayGuiScreen(null);
                if (this.createClicked) {
                    return;
                }
                this.createClicked = true;
                long j2 = new Random().nextLong();
                String string4 = this.textboxSeed.getText();
                if (!MathHelper.stringNullOrLengthZero(string4)) {
                    try {
                        long j5 = Long.parseLong(string4);
                        if (j5 != 0L) {
                            j2 = j5;
                        }
                    }
                    catch (NumberFormatException numberFormatException7) {
                        j2 = string4.hashCode();
                    }
                }
                this.mc.playerController = new PlayerControllerSP(this.mc);
                this.mc.startWorld(this.folderName, this.textboxWorldName.getText(), j2);
                this.mc.displayGuiScreen(null);
            }
        }
    }

    @Override
    protected void keyTyped(char c1, int i2) {
        this.textboxWorldName.textboxKeyTyped(c1, i2);
        this.textboxSeed.textboxKeyTyped(c1, i2);
        if (c1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
        ((GuiButton)this.controlList.get((int)0)).enabled = this.textboxWorldName.getText().length() > 0;
        this.func_22129_j();
    }

    @Override
    protected void mouseClicked(int i1, int i2, int i3) {
        super.mouseClicked(i1, i2, i3);
        this.textboxWorldName.mouseClicked(i1, i2, i3);
        this.textboxSeed.mouseClicked(i1, i2, i3);
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        StringTranslate stringTranslate4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.create"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 0xA0A0A0);
        this.drawString(this.fontRenderer, String.valueOf(stringTranslate4.translateKey("selectWorld.resultFolder")) + " " + this.folderName, this.width / 2 - 100, 85, 0xA0A0A0);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.enterSeed"), this.width / 2 - 100, 104, 0xA0A0A0);
        this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.seedInfo"), this.width / 2 - 100, 140, 0xA0A0A0);
        this.textboxWorldName.drawTextBox();
        this.textboxSeed.drawTextBox();
        super.drawScreen(i1, i2, f3);
    }
}

