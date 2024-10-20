/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.ThreadConnectToServer;

public class GuiConnecting
extends GuiScreen {
    private NetClientHandler clientHandler;
    private boolean cancelled = false;

    public GuiConnecting(Minecraft minecraft1, String string2, int i3) {
        minecraft1.changeWorld1(null);
        new ThreadConnectToServer(this, minecraft1, string2, i3).start();
    }

    @Override
    public void updateScreen() {
        if (this.clientHandler != null) {
            this.clientHandler.processReadPackets();
        }
    }

    @Override
    protected void keyTyped(char c1, int i2) {
    }

    @Override
    public void initGui() {
        StringTranslate stringTranslate1 = StringTranslate.getInstance();
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton guiButton1) {
        if (guiButton1.id == 0) {
            this.cancelled = true;
            if (this.clientHandler != null) {
                this.clientHandler.disconnect();
            }
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        this.drawDefaultBackground();
        StringTranslate stringTranslate4 = StringTranslate.getInstance();
        if (this.clientHandler == null) {
            this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("connect.connecting"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
            this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 0xFFFFFF);
        } else {
            this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("connect.authorizing"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
            this.drawCenteredString(this.fontRenderer, this.clientHandler.field_1209_a, this.width / 2, this.height / 2 - 10, 0xFFFFFF);
        }
        super.drawScreen(i1, i2, f3);
    }

    static NetClientHandler setNetClientHandler(GuiConnecting guiConnecting0, NetClientHandler netClientHandler1) {
        guiConnecting0.clientHandler = netClientHandler1;
        return guiConnecting0.clientHandler;
    }

    static boolean isCancelled(GuiConnecting guiConnecting0) {
        return guiConnecting0.cancelled;
    }

    static NetClientHandler getNetClientHandler(GuiConnecting guiConnecting0) {
        return guiConnecting0.clientHandler;
    }
}

