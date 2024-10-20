/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package MEDMEX.AltManager;

import MEDMEX.AltManager.Alt;
import MEDMEX.AltManager.AltLoginThread;
import MEDMEX.AltManager.AltManager;
import MEDMEX.AltManager.GuiAddAlt;
import MEDMEX.AltManager.GuiAltLogin;
import MEDMEX.Client;
import MEDMEX.Utils.WorldUtils;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAltManager
extends GuiScreen {
    protected GuiScreen parentScreen;
    private GuiButton login;
    private GuiButton remove;
    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt = null;
    private String status = "\u00c2\u00a77No alts selected";

    public GuiAltManager(GuiScreen parentScreenIn) {
        this.parentScreen = parentScreenIn;
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                if (this.loginThread == null) {
                    this.mc.displayGuiScreen(null);
                    break;
                }
                if (!this.loginThread.getStatus().equals("\u00c2\u00a7eAttempting to log in") && !this.loginThread.getStatus().equals("\u00c2\u00a7cDo not hit back!\u00c2\u00a7e Logging in...")) {
                    this.mc.displayGuiScreen(null);
                    break;
                }
                this.loginThread.setStatus("\u00c2\u00a7cFailed to login! Please try again!\u00c2\u00a7e Logging in...");
                break;
            }
            case 1: {
                String user = this.selectedAlt.getUsername();
                this.loginThread = new AltLoginThread(user);
                this.loginThread.start();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                AltManager altManager = Client.altManager;
                AltManager.registry.remove(this.selectedAlt);
                this.status = "\u00a7aRemoved.";
                this.selectedAlt = null;
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            }
            case 5: {
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.displayGuiScreen(new GuiConnecting(this.mc, WorldUtils.lastip, WorldUtils.lastport));
            }
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 26;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        this.drawDefaultBackground();
        Session cfr_ignored_0 = this.mc.session;
        this.drawString(this.fontRenderer, Session.username, 10, 10, -7829368);
        FontRenderer fontRenderer = this.fontRenderer;
        StringBuilder sb2 = new StringBuilder("Account Manager - ");
        this.drawCenteredString(fontRenderer, sb2.append(AltManager.registry.size()).append(" alts").toString(), this.width / 2, 10, -1);
        this.drawCenteredString(this.fontRenderer, this.loginThread == null ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);
        Gui.drawRect(50.0f, 33.0f, this.width - 50, this.height - 50, -16777216);
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, this.width, this.height - 50);
        GL11.glEnable((int)3089);
        int y2 = 38;
        AltManager altManager2 = Client.altManager;
        for (Alt alt2 : AltManager.registry) {
            if (!this.isAltInArea(y2)) continue;
            String name = alt2.getUsername();
            if (alt2 == this.selectedAlt) {
                if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown((int)0)) {
                    Gui.drawRect(52.0f, y2 - this.offset - 4, this.width - 52, y2 - this.offset + 20, -2142943931);
                } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
                    Gui.drawRect(52.0f, y2 - this.offset - 4, this.width - 52, y2 - this.offset + 20, -2142088622);
                } else {
                    Gui.drawRect(52.0f, y2 - this.offset - 4, this.width - 52, y2 - this.offset + 20, -2144259791);
                }
            } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown((int)0)) {
                Gui.drawRect(52.0f, y2 - this.offset - 4, this.width - 52, y2 - this.offset + 20, -16777216);
            } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
                Gui.drawRect(52.0f, y2 - this.offset - 4, this.width - 52, y2 - this.offset + 20, -16777216);
            }
            this.drawCenteredString(this.fontRenderer, name, this.width / 2, y2 - this.offset, -1);
            y2 += 26;
        }
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null) {
            this.login.enabled = false;
            this.remove.enabled = false;
        } else {
            this.login.enabled = true;
            this.remove.enabled = true;
        }
        if (Keyboard.isKeyDown((int)200)) {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        } else if (Keyboard.isKeyDown((int)208)) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
    }

    @Override
    public void initGui() {
        this.controlList.add(new GuiButton(0, this.width / 2 + 4 + 50, this.height - 24, 100, 20, "Cancel"));
        this.login = new GuiButton(1, this.width / 2 - 154, this.height - 48, 100, 20, "Login");
        this.controlList.add(this.login);
        this.remove = new GuiButton(2, this.width / 2 - 154, this.height - 24, 100, 20, "Remove");
        this.controlList.add(this.remove);
        this.controlList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 48, 100, 20, "Add"));
        this.controlList.add(new GuiButton(4, this.width / 2 - 50, this.height - 48, 100, 20, "Direct Login"));
        if (this.parentScreen instanceof GuiIngameMenu) {
            this.controlList.add(new GuiButton(5, this.width / 2 - 50, this.height - 24, 100, 20, "Reconnect"));
        }
        this.login.enabled = false;
        this.remove.enabled = false;
    }

    private boolean isAltInArea(int y2) {
        return y2 - this.offset <= this.height - 50;
    }

    private boolean isMouseOverAlt(int x2, int y2, int y1) {
        return x2 >= 52 && y2 >= y1 - 4 && x2 <= this.width - 52 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= this.width && y2 <= this.height - 50;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y2 = 38 - this.offset;
        AltManager altManager = Client.altManager;
        for (Alt alt2 : AltManager.registry) {
            if (this.isMouseOverAlt(par1, par2, y2)) {
                if (alt2 == this.selectedAlt) {
                    this.actionPerformed((GuiButton)this.controlList.get(1));
                    return;
                }
                this.selectedAlt = alt2;
            }
            y2 += 26;
        }
        super.mouseClicked(par1, par2, par3);
    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int factor = scale.scaleFactor;
        GL11.glScissor((int)((int)(x2 * (float)factor)), (int)((int)(((float)scale.getScaledHeight() - y22) * (float)factor)), (int)((int)((x22 - x2) * (float)factor)), (int)((int)((y22 - y2) * (float)factor)));
    }
}

