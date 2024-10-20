/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiVideoSettings
extends GuiScreen {
    private GuiScreen field_22110_h;
    protected String field_22107_a = "Video Settings";
    private GameSettings field_22109_i;
    private static EnumOptions[] field_22108_k = new EnumOptions[]{EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE, EnumOptions.LIMIT_FRAMERATE, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.AMBIENT_OCCLUSION};

    public GuiVideoSettings(GuiScreen guiScreen1, GameSettings gameSettings2) {
        this.field_22110_h = guiScreen1;
        this.field_22109_i = gameSettings2;
    }

    @Override
    public void initGui() {
        StringTranslate stringTranslate1 = StringTranslate.getInstance();
        this.field_22107_a = stringTranslate1.translateKey("options.videoTitle");
        int i2 = 0;
        EnumOptions[] enumOptions3 = field_22108_k;
        int i4 = enumOptions3.length;
        int i5 = 0;
        while (i5 < i4) {
            EnumOptions enumOptions6 = enumOptions3[i5];
            if (!enumOptions6.getEnumFloat()) {
                this.controlList.add(new GuiSmallButton(enumOptions6.returnEnumOrdinal(), this.width / 2 - 155 + i2 % 2 * 160, this.height / 6 + 24 * (i2 >> 1), enumOptions6, this.field_22109_i.getKeyBinding(enumOptions6)));
            } else {
                this.controlList.add(new GuiSlider(enumOptions6.returnEnumOrdinal(), this.width / 2 - 155 + i2 % 2 * 160, this.height / 6 + 24 * (i2 >> 1), enumOptions6, this.field_22109_i.getKeyBinding(enumOptions6), this.field_22109_i.getOptionFloatValue(enumOptions6)));
            }
            ++i2;
            ++i5;
        }
        this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, stringTranslate1.translateKey("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.enabled) {
            if (guiButton1.id < 100 && guiButton1 instanceof GuiSmallButton) {
                this.field_22109_i.setOptionValue(((GuiSmallButton)guiButton1).returnEnumOptions(), 1);
                guiButton1.displayString = this.field_22109_i.getKeyBinding(EnumOptions.func_20137_a(guiButton1.id));
            }
            if (guiButton1.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.field_22110_h);
            }
            ScaledResolution scaledResolution2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int i3 = scaledResolution2.getScaledWidth();
            int i4 = scaledResolution2.getScaledHeight();
            this.setWorldAndResolution(this.mc, i3, i4);
        }
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.field_22107_a, this.width / 2, 20, 0xFFFFFF);
        super.drawScreen(i1, i2, f3);
    }
}

