/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.ServerManager;

import MEDMEX.ServerManager.Connect;
import MEDMEX.ServerManager.ServerLoginThread;
import MEDMEX.Utils.GuiTextField;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public final class GuiServerLogin
extends GuiScreen {
    private final GuiScreen previousScreen;
    private ServerLoginThread thread;
    private GuiTextField username;

    public GuiServerLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(this.previousScreen);
                break;
            }
            case 0: {
                Connect.connectToServer(this.username.getText(), 25565);
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.drawCenteredString(this.mc.fontRenderer, "Direct Connect", this.width / 2, 20, -1);
        this.drawCenteredString(this.mc.fontRenderer, this.thread == null ? "\u00a77Idle..." : this.thread.getStatus(), this.width / 2, 29, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRenderer, "Server IP", this.width / 2 - 96, 66, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        int var3 = this.height / 4 + 24;
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Connect\t"));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
        this.username = new GuiTextField(this, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20, "");
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        super.keyTyped(character, key);
        if (character == '\t' && !this.username.isFocused()) {
            this.username.setFocused(true);
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        super.mouseClicked(x2, y2, button);
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}

