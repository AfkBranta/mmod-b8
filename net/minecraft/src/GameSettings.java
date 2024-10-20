/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.StringTranslate;
import org.lwjgl.input.Keyboard;

public class GameSettings {
    private static final String[] RENDER_DISTANCES = new String[]{"options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
    private static final String[] DIFFICULTIES = new String[]{"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};
    private static final String[] field_25147_K = new String[]{"options.d.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    public float musicVolume = 1.0f;
    public float soundVolume = 1.0f;
    public float mouseSensitivity = 0.5f;
    public boolean invertMouse = false;
    public int renderDistance = 0;
    public boolean viewBobbing = true;
    public boolean anaglyph = false;
    public boolean limitFramerate = false;
    public boolean fancyGraphics = true;
    public boolean ambientOcclusion = true;
    public String skin = "Default";
    public KeyBinding keyBindForward = new KeyBinding("key.forward", 17);
    public KeyBinding keyBindLeft = new KeyBinding("key.left", 30);
    public KeyBinding keyBindBack = new KeyBinding("key.back", 31);
    public KeyBinding keyBindRight = new KeyBinding("key.right", 32);
    public KeyBinding keyBindJump = new KeyBinding("key.jump", 57);
    public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18);
    public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16);
    public KeyBinding keyBindChat = new KeyBinding("key.chat", 20);
    public KeyBinding keyBindToggleFog = new KeyBinding("key.fog", 33);
    public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42);
    public KeyBinding[] keyBindings = new KeyBinding[]{this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindToggleFog};
    protected Minecraft mc;
    private File optionsFile;
    public int difficulty = 2;
    public boolean hideGUI = false;
    public boolean thirdPersonView = false;
    public boolean showDebugInfo = false;
    public String lastServer = "";
    public boolean field_22275_C = false;
    public boolean smoothCamera = false;
    public boolean field_22273_E = false;
    public float field_22272_F = 1.0f;
    public float field_22271_G = 1.0f;
    public int field_25148_H = 0;

    public GameSettings(Minecraft minecraft1, File file2) {
        this.mc = minecraft1;
        this.optionsFile = new File(file2, "options.txt");
        this.loadOptions();
    }

    public GameSettings() {
    }

    public String getKeyBindingDescription(int i1) {
        StringTranslate stringTranslate2 = StringTranslate.getInstance();
        return stringTranslate2.translateKey(this.keyBindings[i1].keyDescription);
    }

    public String getOptionDisplayString(int i1) {
        return Keyboard.getKeyName((int)this.keyBindings[i1].keyCode);
    }

    public void setKeyBinding(int i1, int i2) {
        this.keyBindings[i1].keyCode = i2;
        this.saveOptions();
    }

    public void setOptionFloatValue(EnumOptions enumOptions1, float f2) {
        if (enumOptions1 == EnumOptions.MUSIC) {
            this.musicVolume = f2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (enumOptions1 == EnumOptions.SOUND) {
            this.soundVolume = f2;
            this.mc.sndManager.onSoundOptionsChanged();
        }
        if (enumOptions1 == EnumOptions.SENSITIVITY) {
            this.mouseSensitivity = f2;
        }
    }

    public void setOptionValue(EnumOptions enumOptions1, int i2) {
        if (enumOptions1 == EnumOptions.INVERT_MOUSE) {
            boolean bl = this.invertMouse = !this.invertMouse;
        }
        if (enumOptions1 == EnumOptions.RENDER_DISTANCE) {
            this.renderDistance = this.renderDistance + i2 & 3;
        }
        if (enumOptions1 == EnumOptions.GUI_SCALE) {
            this.field_25148_H = this.field_25148_H + i2 & 3;
        }
        if (enumOptions1 == EnumOptions.VIEW_BOBBING) {
            boolean bl = this.viewBobbing = !this.viewBobbing;
        }
        if (enumOptions1 == EnumOptions.ANAGLYPH) {
            this.anaglyph = !this.anaglyph;
            this.mc.renderEngine.refreshTextures();
        }
        if (enumOptions1 == EnumOptions.LIMIT_FRAMERATE) {
            boolean bl = this.limitFramerate = !this.limitFramerate;
        }
        if (enumOptions1 == EnumOptions.DIFFICULTY) {
            this.difficulty = this.difficulty + i2 & 3;
        }
        if (enumOptions1 == EnumOptions.GRAPHICS) {
            this.fancyGraphics = !this.fancyGraphics;
            this.mc.renderGlobal.loadRenderers();
        }
        if (enumOptions1 == EnumOptions.AMBIENT_OCCLUSION) {
            this.ambientOcclusion = !this.ambientOcclusion;
            this.mc.renderGlobal.loadRenderers();
        }
        this.saveOptions();
    }

    public float getOptionFloatValue(EnumOptions enumOptions1) {
        return enumOptions1 == EnumOptions.MUSIC ? this.musicVolume : (enumOptions1 == EnumOptions.SOUND ? this.soundVolume : (enumOptions1 == EnumOptions.SENSITIVITY ? this.mouseSensitivity : 0.0f));
    }

    public boolean getOptionOrdinalValue(EnumOptions enumOptions1) {
        switch (enumOptions1) {
            case INVERT_MOUSE: {
                return this.invertMouse;
            }
            case VIEW_BOBBING: {
                return this.viewBobbing;
            }
            case ANAGLYPH: {
                return this.anaglyph;
            }
            case LIMIT_FRAMERATE: {
                return this.limitFramerate;
            }
            case AMBIENT_OCCLUSION: {
                return this.ambientOcclusion;
            }
        }
        return false;
    }

    public String getKeyBinding(EnumOptions enumOptions1) {
        StringTranslate stringTranslate2 = StringTranslate.getInstance();
        String string3 = String.valueOf(stringTranslate2.translateKey(enumOptions1.getEnumString())) + ": ";
        if (enumOptions1.getEnumFloat()) {
            float f5 = this.getOptionFloatValue(enumOptions1);
            return enumOptions1 == EnumOptions.SENSITIVITY ? (f5 == 0.0f ? String.valueOf(string3) + stringTranslate2.translateKey("options.sensitivity.min") : (f5 == 1.0f ? String.valueOf(string3) + stringTranslate2.translateKey("options.sensitivity.max") : String.valueOf(string3) + (int)(f5 * 200.0f) + "%")) : (f5 == 0.0f ? String.valueOf(string3) + stringTranslate2.translateKey("options.off") : String.valueOf(string3) + (int)(f5 * 100.0f) + "%");
        }
        if (enumOptions1.getEnumBoolean()) {
            boolean z4 = this.getOptionOrdinalValue(enumOptions1);
            return z4 ? String.valueOf(string3) + stringTranslate2.translateKey("options.on") : String.valueOf(string3) + stringTranslate2.translateKey("options.off");
        }
        return enumOptions1 == EnumOptions.RENDER_DISTANCE ? String.valueOf(string3) + stringTranslate2.translateKey(RENDER_DISTANCES[this.renderDistance]) : (enumOptions1 == EnumOptions.DIFFICULTY ? String.valueOf(string3) + stringTranslate2.translateKey(DIFFICULTIES[this.difficulty]) : (enumOptions1 == EnumOptions.GUI_SCALE ? String.valueOf(string3) + stringTranslate2.translateKey(field_25147_K[this.field_25148_H]) : (enumOptions1 == EnumOptions.GRAPHICS ? (this.fancyGraphics ? String.valueOf(string3) + stringTranslate2.translateKey("options.graphics.fancy") : String.valueOf(string3) + stringTranslate2.translateKey("options.graphics.fast")) : string3)));
    }

    public void loadOptions() {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader(this.optionsFile));
            String string2 = "";
            while ((string2 = bufferedReader1.readLine()) != null) {
                String[] string3 = string2.split(":");
                if (string3[0].equals("music")) {
                    this.musicVolume = this.parseFloat(string3[1]);
                }
                if (string3[0].equals("sound")) {
                    this.soundVolume = this.parseFloat(string3[1]);
                }
                if (string3[0].equals("mouseSensitivity")) {
                    this.mouseSensitivity = this.parseFloat(string3[1]);
                }
                if (string3[0].equals("invertYMouse")) {
                    this.invertMouse = string3[1].equals("true");
                }
                if (string3[0].equals("viewDistance")) {
                    this.renderDistance = Integer.parseInt(string3[1]);
                }
                if (string3[0].equals("guiScale")) {
                    this.field_25148_H = Integer.parseInt(string3[1]);
                }
                if (string3[0].equals("bobView")) {
                    this.viewBobbing = string3[1].equals("true");
                }
                if (string3[0].equals("anaglyph3d")) {
                    this.anaglyph = string3[1].equals("true");
                }
                if (string3[0].equals("limitFramerate")) {
                    this.limitFramerate = string3[1].equals("true");
                }
                if (string3[0].equals("difficulty")) {
                    this.difficulty = Integer.parseInt(string3[1]);
                }
                if (string3[0].equals("fancyGraphics")) {
                    this.fancyGraphics = string3[1].equals("true");
                }
                if (string3[0].equals("ao")) {
                    this.ambientOcclusion = string3[1].equals("true");
                }
                if (string3[0].equals("skin")) {
                    this.skin = string3[1];
                }
                if (string3[0].equals("lastServer") && string3.length >= 2) {
                    this.lastServer = string3[1];
                }
                int i4 = 0;
                while (i4 < this.keyBindings.length) {
                    if (string3[0].equals("key_" + this.keyBindings[i4].keyDescription)) {
                        this.keyBindings[i4].keyCode = Integer.parseInt(string3[1]);
                    }
                    ++i4;
                }
            }
            bufferedReader1.close();
        }
        catch (Exception exception5) {
            System.out.println("Failed to load options");
            exception5.printStackTrace();
        }
    }

    private float parseFloat(String string1) {
        return string1.equals("true") ? 1.0f : (string1.equals("false") ? 0.0f : Float.parseFloat(string1));
    }

    public void saveOptions() {
        try {
            PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.optionsFile));
            printWriter1.println("music:" + this.musicVolume);
            printWriter1.println("sound:" + this.soundVolume);
            printWriter1.println("invertYMouse:" + this.invertMouse);
            printWriter1.println("mouseSensitivity:" + this.mouseSensitivity);
            printWriter1.println("viewDistance:" + this.renderDistance);
            printWriter1.println("guiScale:" + this.field_25148_H);
            printWriter1.println("bobView:" + this.viewBobbing);
            printWriter1.println("anaglyph3d:" + this.anaglyph);
            printWriter1.println("limitFramerate:" + this.limitFramerate);
            printWriter1.println("difficulty:" + this.difficulty);
            printWriter1.println("fancyGraphics:" + this.fancyGraphics);
            printWriter1.println("ao:" + this.ambientOcclusion);
            printWriter1.println("skin:" + this.skin);
            printWriter1.println("lastServer:" + this.lastServer);
            int i2 = 0;
            while (i2 < this.keyBindings.length) {
                printWriter1.println("key_" + this.keyBindings[i2].keyDescription + ":" + this.keyBindings[i2].keyCode);
                ++i2;
            }
            printWriter1.close();
        }
        catch (Exception exception3) {
            System.out.println("Failed to save options");
            exception3.printStackTrace();
        }
    }
}

