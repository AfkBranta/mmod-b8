/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package MEDMEX.AltManager;

import MEDMEX.AltManager.Alt;
import MEDMEX.AltManager.AltManager;
import MEDMEX.AltManager.GuiAltManager;
import MEDMEX.Client;
import MEDMEX.Utils.GuiTextField;
import java.io.IOException;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiAddAlt
extends GuiScreen {
    private final GuiAltManager manager;
    private String status = "\u00c2\u00a77Idle...";
    private GuiTextField username;

    public GuiAddAlt(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                AddAltThread login = new AddAltThread(this.username.getText());
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.drawCenteredString(this.fontRenderer, "Add Alt", this.width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRenderer, "Username", this.width / 2 - 96, 66, -7829368);
        }
        this.drawCenteredString(this.fontRenderer, this.status, this.width / 2, 30, -1);
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Login"));
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Back"));
        this.username = new GuiTextField(this, this.mc.fontRenderer, this.width / 2 - 100, 60, 200, 20, "");
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.username.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && this.username.isFocused()) {
            this.username.setFocused(!this.username.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton)this.controlList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.username.mouseClicked(par1, par2, par3);
    }

    static void access$0(GuiAddAlt guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread
    extends Thread {
        private final String username;

        public AddAltThread(String username) {
            this.username = username;
            GuiAddAlt.access$0(GuiAddAlt.this, "\u00c2\u00a77Idle...");
        }

        private final void checkAndAddAlt(String username) throws IOException {
            AltManager altManager = Client.altManager;
            AltManager.registry.add(new Alt(username));
            GuiAddAlt.access$0(GuiAddAlt.this, "Alt added. (" + username + ")");
        }

        @Override
        public void run() {
            AltManager altManager = Client.altManager;
            AltManager.registry.add(new Alt(this.username));
            GuiAddAlt.access$0(GuiAddAlt.this, "\u00c2\u00a7aAlt added. (" + this.username + ")");
        }
    }
}

