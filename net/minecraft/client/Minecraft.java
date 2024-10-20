/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Controllers
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package net.minecraft.client;

import MEDMEX.Client;
import MEDMEX.Events.events.EventBreakBlock;
import MEDMEX.Events.events.EventKey;
import MEDMEX.Events.events.EventShutdown;
import MEDMEX.Events.events.EventStartup;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.File;
import java.nio.ByteBuffer;
import net.minecraft.client.MinecraftApplet;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOS2;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GameWindowListener;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.GuiUnused;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.MinecraftImpl;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatList;
import net.minecraft.src.Teleporter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFlamesFX;
import net.minecraft.src.TextureLavaFX;
import net.minecraft.src.TextureLavaFlowFX;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TexturePortalFX;
import net.minecraft.src.TextureWatchFX;
import net.minecraft.src.TextureWaterFX;
import net.minecraft.src.TexureWaterFlowFX;
import net.minecraft.src.ThreadDownloadResources;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.XBlah;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class Minecraft
implements Runnable {
    public static Minecraft theMinecraft;
    public PlayerController playerController;
    private boolean fullscreen = false;
    public int displayWidth;
    public int displayHeight;
    private OpenGlCapsChecker glCapabilities;
    public Timer timer = new Timer(20.0f);
    public World theWorld;
    public RenderGlobal renderGlobal;
    public EntityPlayerSP thePlayer;
    public EntityLiving renderViewEntity;
    public EffectRenderer effectRenderer;
    public Session session = null;
    public String minecraftUri;
    public Canvas mcCanvas;
    public boolean hideQuitButton = true;
    public volatile boolean isWorldLoaded = false;
    public RenderEngine renderEngine;
    public FontRenderer fontRenderer;
    public GuiScreen currentScreen = null;
    public LoadingScreenRenderer loadingScreen = new LoadingScreenRenderer(this);
    public EntityRenderer entityRenderer = new EntityRenderer(this);
    private ThreadDownloadResources downloadResourcesThread;
    private int ticksRan = 0;
    private int field_6282_S = 0;
    private int field_9236_T;
    private int field_9235_U;
    public GuiAchievement field_25002_t = new GuiAchievement(this);
    public GuiIngame ingameGUI;
    public boolean field_6307_v = false;
    public ModelBiped field_9242_w = new ModelBiped(0.0f);
    public MovingObjectPosition objectMouseOver = null;
    public GameSettings gameSettings;
    protected MinecraftApplet mcApplet;
    public SoundManager sndManager = new SoundManager();
    public MouseHelper mouseHelper;
    public TexturePackList texturePackList;
    private File mcDataDir;
    private ISaveFormat saveLoader;
    public static long[] frameTimes;
    public static long[] tickTimes;
    public static int numRecordedFrameTimes;
    public XBlah field_25001_G = new XBlah();
    private String serverName;
    private int serverPort;
    private TextureWaterFX textureWaterFX = new TextureWaterFX();
    private TextureLavaFX textureLavaFX = new TextureLavaFX();
    private static File minecraftDir;
    public volatile boolean running = true;
    public String debug = "";
    boolean isTakingScreenshot = false;
    long prevFrameTime = -1L;
    public boolean inGameHasFocus = false;
    private int field_6302_aa = 0;
    public boolean isRaining = false;
    long systemTime = System.currentTimeMillis();
    private int field_6300_ab = 0;

    static {
        frameTimes = new long[512];
        tickTimes = new long[512];
        numRecordedFrameTimes = 0;
        minecraftDir = null;
    }

    public Minecraft(Component component1, Canvas canvas2, MinecraftApplet minecraftApplet3, int i4, int i5, boolean z6) {
        this.field_9235_U = i5;
        this.fullscreen = z6;
        this.mcApplet = minecraftApplet3;
        new ThreadSleepForever(this, "Timer hack thread");
        this.mcCanvas = canvas2;
        this.displayWidth = i4;
        this.displayHeight = i5;
        this.fullscreen = z6;
        if (minecraftApplet3 == null || "true".equals(minecraftApplet3.getParameter("stand-alone"))) {
            this.hideQuitButton = false;
        }
        theMinecraft = this;
    }

    public abstract void displayUnexpectedThrowable(UnexpectedThrowable var1);

    public void setServer(String string1, int i2) {
        this.serverName = string1;
        this.serverPort = i2;
    }

    public void startGame() throws LWJGLException {
        if (this.mcCanvas != null) {
            Graphics graphics1 = this.mcCanvas.getGraphics();
            if (graphics1 != null) {
                graphics1.setColor(Color.BLACK);
                graphics1.fillRect(0, 0, this.displayWidth, this.displayHeight);
                graphics1.dispose();
            }
            Display.setParent((Canvas)this.mcCanvas);
        } else if (this.fullscreen) {
            Display.setFullscreen((boolean)true);
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
            if (this.displayWidth <= 0) {
                this.displayWidth = 1;
            }
            if (this.displayHeight <= 0) {
                this.displayHeight = 1;
            }
        } else {
            Display.setDisplayMode((DisplayMode)new DisplayMode(this.displayWidth, this.displayHeight));
        }
        Display.setTitle((String)"Minecraft Minecraft Beta 1.4_01");
        try {
            Display.create();
        }
        catch (LWJGLException lWJGLException6) {
            lWJGLException6.printStackTrace();
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            Display.create();
        }
        RenderManager.instance.itemRenderer = new ItemRenderer(this);
        this.mcDataDir = Minecraft.getMinecraftDir();
        this.saveLoader = new SaveConverterMcRegion(new File(this.mcDataDir, "saves"));
        this.gameSettings = new GameSettings(this, this.mcDataDir);
        this.texturePackList = new TexturePackList(this, this.mcDataDir);
        this.renderEngine = new RenderEngine(this.texturePackList, this.gameSettings);
        this.fontRenderer = new FontRenderer(this.gameSettings, "/font/default.png", this.renderEngine);
        this.loadScreen();
        Keyboard.create();
        Mouse.create();
        this.mouseHelper = new MouseHelper(this.mcCanvas);
        try {
            Controllers.create();
        }
        catch (Exception exception4) {
            exception4.printStackTrace();
        }
        this.checkGLError("Pre startup");
        GL11.glEnable((int)3553);
        GL11.glShadeModel((int)7425);
        GL11.glClearDepth((double)1.0);
        GL11.glEnable((int)2929);
        GL11.glDepthFunc((int)515);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        GL11.glCullFace((int)1029);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glMatrixMode((int)5888);
        this.checkGLError("Startup");
        this.glCapabilities = new OpenGlCapsChecker();
        this.sndManager.loadSoundSettings(this.gameSettings);
        this.renderEngine.registerTextureFX(this.textureLavaFX);
        this.renderEngine.registerTextureFX(this.textureWaterFX);
        this.renderEngine.registerTextureFX(new TexturePortalFX());
        this.renderEngine.registerTextureFX(new TextureCompassFX(this));
        this.renderEngine.registerTextureFX(new TextureWatchFX(this));
        this.renderEngine.registerTextureFX(new TexureWaterFlowFX());
        this.renderEngine.registerTextureFX(new TextureLavaFlowFX());
        this.renderEngine.registerTextureFX(new TextureFlamesFX(0));
        this.renderEngine.registerTextureFX(new TextureFlamesFX(1));
        this.renderGlobal = new RenderGlobal(this, this.renderEngine);
        GL11.glViewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
        try {
            this.downloadResourcesThread = new ThreadDownloadResources(this.mcDataDir, this);
            this.downloadResourcesThread.start();
        }
        catch (Exception exception4) {
            // empty catch block
        }
        this.checkGLError("Post startup");
        this.ingameGUI = new GuiIngame(this);
        if (this.serverName != null) {
            this.displayGuiScreen(new GuiConnecting(this, this.serverName, this.serverPort));
        } else {
            this.displayGuiScreen(new GuiMainMenu());
        }
        EventStartup event = new EventStartup();
        Client.onEvent(event);
    }

    private void loadScreen() throws LWJGLException {
        ScaledResolution scaledResolution1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
        GL11.glClear((int)16640);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)scaledResolution1.field_25121_a, (double)scaledResolution1.field_25120_b, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glViewport((int)0, (int)0, (int)this.displayWidth, (int)this.displayHeight);
        GL11.glClearColor((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        Tessellator tessellator2 = Tessellator.instance;
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2912);
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/title/mojang.png"));
        tessellator2.startDrawingQuads();
        tessellator2.setColorOpaque_I(0xFFFFFF);
        tessellator2.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        tessellator2.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        tessellator2.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        tessellator2.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator2.draw();
        int s3 = 256;
        int s4 = 256;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator2.setColorOpaque_I(0xFFFFFF);
        this.func_6274_a((this.displayWidth / 2 - s3) / 2, (this.displayHeight / 2 - s4) / 2, 0, 0, s3, s4);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        GL11.glEnable((int)3008);
        GL11.glAlphaFunc((int)516, (float)0.1f);
        Display.swapBuffers();
    }

    public void func_6274_a(int i1, int i2, int i3, int i4, int i5, int i6) {
        float f7 = 0.00390625f;
        float f8 = 0.00390625f;
        Tessellator tessellator9 = Tessellator.instance;
        tessellator9.startDrawingQuads();
        tessellator9.addVertexWithUV(i1 + 0, i2 + i6, 0.0, (float)(i3 + 0) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + i6, 0.0, (float)(i3 + i5) * f7, (float)(i4 + i6) * f8);
        tessellator9.addVertexWithUV(i1 + i5, i2 + 0, 0.0, (float)(i3 + i5) * f7, (float)(i4 + 0) * f8);
        tessellator9.addVertexWithUV(i1 + 0, i2 + 0, 0.0, (float)(i3 + 0) * f7, (float)(i4 + 0) * f8);
        tessellator9.draw();
    }

    public static File getMinecraftDir() {
        if (minecraftDir == null) {
            minecraftDir = Minecraft.getAppDir("minecraft");
        }
        return minecraftDir;
    }

    public static File getAppDir(String string0) {
        File file2;
        String string1 = System.getProperty("user.home", ".");
        switch (Minecraft.getOs()) {
            case linux: 
            case solaris: {
                file2 = new File(string1, String.valueOf('.') + string0 + '/');
                break;
            }
            case windows: {
                String string3 = System.getenv("APPDATA");
                if (string3 != null) {
                    file2 = new File(string3, "." + string0 + '/');
                    break;
                }
                file2 = new File(string1, String.valueOf('.') + string0 + '/');
                break;
            }
            case macos: {
                file2 = new File(string1, "Library/Application Support/" + string0);
                break;
            }
            default: {
                file2 = new File(string1, String.valueOf(string0) + '/');
            }
        }
        if (!file2.exists() && !file2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + file2);
        }
        return file2;
    }

    private static EnumOS2 getOs() {
        String string0 = System.getProperty("os.name").toLowerCase();
        return string0.contains("win") ? EnumOS2.windows : (string0.contains("mac") ? EnumOS2.macos : (string0.contains("solaris") ? EnumOS2.solaris : (string0.contains("sunos") ? EnumOS2.solaris : (string0.contains("linux") ? EnumOS2.linux : (string0.contains("unix") ? EnumOS2.linux : EnumOS2.unknown)))));
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void displayGuiScreen(GuiScreen guiScreen1) {
        if (!(this.currentScreen instanceof GuiUnused)) {
            if (this.currentScreen != null) {
                this.currentScreen.onGuiClosed();
            }
            if (guiScreen1 == null && this.theWorld == null) {
                guiScreen1 = new GuiMainMenu();
            } else if (guiScreen1 == null && this.thePlayer.health <= 0) {
                guiScreen1 = new GuiGameOver();
            }
            this.currentScreen = guiScreen1;
            if (guiScreen1 != null) {
                this.setIngameNotInFocus();
                ScaledResolution scaledResolution2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
                int i3 = scaledResolution2.getScaledWidth();
                int i4 = scaledResolution2.getScaledHeight();
                guiScreen1.setWorldAndResolution(this, i3, i4);
                this.field_6307_v = false;
            } else {
                this.setIngameFocus();
            }
        }
    }

    private void checkGLError(String string1) {
        int i2 = GL11.glGetError();
        if (i2 != 0) {
            String string3 = GLU.gluErrorString((int)i2);
            System.out.println("########## GL ERROR ##########");
            System.out.println("@ " + string1);
            System.out.println(String.valueOf(i2) + ": " + string3);
            System.exit(0);
        }
    }

    public void shutdownMinecraftApplet() {
        if (this.mcApplet != null) {
            this.mcApplet.clearApplet();
        }
        try {
            if (this.downloadResourcesThread != null) {
                this.downloadResourcesThread.closeMinecraft();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            System.out.println("Stopping!");
            try {
                this.changeWorld1(null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                GLAllocation.deleteTexturesAndDisplayLists();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            this.sndManager.closeMinecraft();
            Mouse.destroy();
            Keyboard.destroy();
        }
        finally {
            Display.destroy();
            System.exit(0);
        }
        System.gc();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.running = true;
        try {
            this.startGame();
        }
        catch (Exception exception15) {
            exception15.printStackTrace();
            this.displayUnexpectedThrowable(new UnexpectedThrowable("Failed to start game", exception15));
            return;
        }
        try {
            try {
                j1 = System.currentTimeMillis();
                i3 = 0;
                if (true) ** GOTO lbl86
                do {
                    AxisAlignedBB.clearBoundingBoxPool();
                    Vec3D.initialize();
                    if (this.mcCanvas == null && Display.isCloseRequested()) {
                        this.shutdown();
                    }
                    if (this.isWorldLoaded && this.theWorld != null) {
                        f4 = this.timer.renderPartialTicks;
                        this.timer.updateTimer();
                        this.timer.renderPartialTicks = f4;
                    } else {
                        this.timer.updateTimer();
                    }
                    j19 = System.nanoTime();
                    i6 = 0;
                    while (i6 < this.timer.elapsedTicks) {
                        ++this.ticksRan;
                        try {
                            this.runTick();
                        }
                        catch (MinecraftException minecraftException14) {
                            this.theWorld = null;
                            this.changeWorld1(null);
                            this.displayGuiScreen(new GuiConflictWarning());
                        }
                        ++i6;
                    }
                    j20 = System.nanoTime() - j19;
                    this.checkGLError("Pre render");
                    this.sndManager.func_338_a(this.thePlayer, this.timer.renderPartialTicks);
                    GL11.glEnable((int)3553);
                    if (this.theWorld != null && !this.theWorld.multiplayerWorld) {
                        this.theWorld.func_6465_g();
                    }
                    if (this.theWorld != null && this.theWorld.multiplayerWorld) {
                        this.theWorld.func_6465_g();
                    }
                    if (this.gameSettings.limitFramerate) {
                        Thread.sleep(5L);
                    }
                    if (!Keyboard.isKeyDown((int)65)) {
                        Display.update();
                    }
                    if (!this.field_6307_v) {
                        if (this.playerController != null) {
                            this.playerController.setPartialTime(this.timer.renderPartialTicks);
                        }
                        this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
                    }
                    if (!Display.isActive()) {
                        if (this.fullscreen) {
                            this.toggleFullscreen();
                        }
                        Thread.sleep(10L);
                    }
                    if (this.gameSettings.showDebugInfo) {
                        this.displayDebugInfo(j20);
                    } else {
                        this.prevFrameTime = System.nanoTime();
                    }
                    this.field_25002_t.func_25080_a();
                    Thread.yield();
                    if (Keyboard.isKeyDown((int)65)) {
                        Display.update();
                    }
                    this.screenshotListener();
                    if (!(this.mcCanvas == null || this.fullscreen || this.mcCanvas.getWidth() == this.displayWidth && this.mcCanvas.getHeight() == this.displayHeight)) {
                        this.displayWidth = this.mcCanvas.getWidth();
                        this.displayHeight = this.mcCanvas.getHeight();
                        if (this.displayWidth <= 0) {
                            this.displayWidth = 1;
                        }
                        if (this.displayHeight <= 0) {
                            this.displayHeight = 1;
                        }
                        this.resize(this.displayWidth, this.displayHeight);
                    }
                    this.checkGLError("Post render");
                    ++i3;
                    this.isWorldLoaded = this.isMultiplayerWorld() == false && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() != false;
                    while (System.currentTimeMillis() >= j1 + 1000L) {
                        this.debug = String.valueOf(i3) + " fps, " + WorldRenderer.chunksUpdated + " chunk updates";
                        WorldRenderer.chunksUpdated = 0;
                        j1 += 1000L;
                        i3 = 0;
                    }
lbl86:
                    // 2 sources

                    if (!this.running) return;
                } while (this.mcApplet == null || this.mcApplet.isActive());
                return;
            }
            catch (MinecraftError j1) {
                this.shutdownMinecraftApplet();
                return;
            }
            catch (Throwable throwable17) {
                this.theWorld = null;
                throwable17.printStackTrace();
                this.displayUnexpectedThrowable(new UnexpectedThrowable("Unexpected error", throwable17));
                this.shutdownMinecraftApplet();
                return;
            }
        }
        finally {
            this.shutdownMinecraftApplet();
        }
    }

    private void screenshotListener() {
        if (Keyboard.isKeyDown((int)60)) {
            if (!this.isTakingScreenshot) {
                this.isTakingScreenshot = true;
                if (Keyboard.isKeyDown((int)42)) {
                    this.ingameGUI.addChatMessage(this.func_21001_a(minecraftDir, this.displayWidth, this.displayHeight, 36450, 17700));
                } else {
                    this.ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot(minecraftDir, this.displayWidth, this.displayHeight));
                }
            }
        } else {
            this.isTakingScreenshot = false;
        }
    }

    private String func_21001_a(File file1, int i2, int i3, int i4, int i5) {
        try {
            ByteBuffer byteBuffer6 = BufferUtils.createByteBuffer((int)(i2 * i3 * 3));
            ScreenShotHelper screenShotHelper7 = new ScreenShotHelper(file1, i4, i5, i3);
            double d8 = (double)i4 / (double)i2;
            double d10 = (double)i5 / (double)i3;
            double d12 = d8 > d10 ? d8 : d10;
            int i14 = (i5 - 1) / i3 * i3;
            while (i14 >= 0) {
                int i15 = 0;
                while (i15 < i4) {
                    GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain.png"));
                    double d18 = (double)(i4 - i2) / 2.0 * 2.0 - (double)(i15 * 2);
                    double d20 = (double)(i5 - i3) / 2.0 * 2.0 - (double)(i14 * 2);
                    this.entityRenderer.func_21152_a(d12, d18 /= (double)i2, d20 /= (double)i3);
                    this.entityRenderer.renderWorld(1.0f);
                    this.entityRenderer.resetZoom();
                    Display.update();
                    try {
                        Thread.sleep(10L);
                    }
                    catch (InterruptedException interruptedException23) {
                        interruptedException23.printStackTrace();
                    }
                    Display.update();
                    byteBuffer6.clear();
                    GL11.glPixelStorei((int)3333, (int)1);
                    GL11.glPixelStorei((int)3317, (int)1);
                    GL11.glReadPixels((int)0, (int)0, (int)i2, (int)i3, (int)32992, (int)5121, (ByteBuffer)byteBuffer6);
                    screenShotHelper7.func_21189_a(byteBuffer6, i15, i14, i2, i3);
                    i15 += i2;
                }
                screenShotHelper7.func_21191_a();
                i14 -= i3;
            }
            return screenShotHelper7.func_21190_b();
        }
        catch (Exception exception24) {
            exception24.printStackTrace();
            return "Failed to save image: " + exception24;
        }
    }

    private void displayDebugInfo(long j1) {
        long j3 = 16666666L;
        if (this.prevFrameTime == -1L) {
            this.prevFrameTime = System.nanoTime();
        }
        long j5 = System.nanoTime();
        Minecraft.tickTimes[Minecraft.numRecordedFrameTimes & Minecraft.frameTimes.length - 1] = j1;
        Minecraft.frameTimes[Minecraft.numRecordedFrameTimes++ & Minecraft.frameTimes.length - 1] = j5 - this.prevFrameTime;
        this.prevFrameTime = j5;
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)this.displayWidth, (double)this.displayHeight, (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        Tessellator tessellator7 = Tessellator.instance;
        tessellator7.startDrawing(7);
        int i8 = (int)(j3 / 200000L);
        tessellator7.setColorOpaque_I(0x20000000);
        tessellator7.addVertex(0.0, this.displayHeight - i8, 0.0);
        tessellator7.addVertex(0.0, this.displayHeight, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight - i8, 0.0);
        tessellator7.setColorOpaque_I(0x20200000);
        tessellator7.addVertex(0.0, this.displayHeight - i8 * 2, 0.0);
        tessellator7.addVertex(0.0, this.displayHeight - i8, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight - i8, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight - i8 * 2, 0.0);
        tessellator7.draw();
        long j9 = 0L;
        int i11 = 0;
        while (i11 < frameTimes.length) {
            j9 += frameTimes[i11];
            ++i11;
        }
        i11 = (int)(j9 / 200000L / (long)frameTimes.length);
        tessellator7.startDrawing(7);
        tessellator7.setColorOpaque_I(0x20400000);
        tessellator7.addVertex(0.0, this.displayHeight - i11, 0.0);
        tessellator7.addVertex(0.0, this.displayHeight, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight, 0.0);
        tessellator7.addVertex(frameTimes.length, this.displayHeight - i11, 0.0);
        tessellator7.draw();
        tessellator7.startDrawing(1);
        int i12 = 0;
        while (i12 < frameTimes.length) {
            int i13 = (i12 - numRecordedFrameTimes & frameTimes.length - 1) * 255 / frameTimes.length;
            int i14 = i13 * i13 / 255;
            i14 = i14 * i14 / 255;
            int i15 = i14 * i14 / 255;
            i15 = i15 * i15 / 255;
            if (frameTimes[i12] > j3) {
                tessellator7.setColorOpaque_I(-16777216 + i14 * 65536);
            } else {
                tessellator7.setColorOpaque_I(-16777216 + i14 * 256);
            }
            long j16 = frameTimes[i12] / 200000L;
            long j18 = tickTimes[i12] / 200000L;
            tessellator7.addVertex((float)i12 + 0.5f, (float)((long)this.displayHeight - j16) + 0.5f, 0.0);
            tessellator7.addVertex((float)i12 + 0.5f, (float)this.displayHeight + 0.5f, 0.0);
            tessellator7.setColorOpaque_I(-16777216 + i14 * 65536 + i14 * 256 + i14 * 1);
            tessellator7.addVertex((float)i12 + 0.5f, (float)((long)this.displayHeight - j16) + 0.5f, 0.0);
            tessellator7.addVertex((float)i12 + 0.5f, (float)((long)this.displayHeight - (j16 - j18)) + 0.5f, 0.0);
            ++i12;
        }
        tessellator7.draw();
        GL11.glEnable((int)3553);
    }

    public void shutdown() {
        EventShutdown event = new EventShutdown();
        Client.onEvent(event);
        this.running = false;
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.field_6302_aa = this.ticksRan + 10000;
        }
    }

    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            if (this.thePlayer != null) {
                this.thePlayer.resetPlayerKeyState();
            }
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }

    public void displayInGameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
        }
    }

    private void func_6254_a(int i1, boolean z2) {
        if (!(this.playerController.field_1064_b || i1 == 0 && this.field_6282_S > 0)) {
            if (z2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && i1 == 0) {
                int i3 = this.objectMouseOver.blockX;
                int i4 = this.objectMouseOver.blockY;
                int i5 = this.objectMouseOver.blockZ;
                this.playerController.sendBlockRemoving(i3, i4, i5, this.objectMouseOver.sideHit);
                this.effectRenderer.addBlockHitEffects(i3, i4, i5, this.objectMouseOver.sideHit);
            } else {
                this.playerController.func_6468_a();
            }
        }
    }

    private void clickMouse(int i1) {
        if (i1 != 0 || this.field_6282_S <= 0) {
            ItemStack itemStack10;
            if (i1 == 0) {
                this.thePlayer.swingItem();
            }
            boolean z2 = true;
            if (this.objectMouseOver == null) {
                if (i1 == 0 && !(this.playerController instanceof PlayerControllerTest)) {
                    this.field_6282_S = 10;
                }
            } else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
                if (i1 == 0) {
                    this.playerController.func_6472_b(this.thePlayer, this.objectMouseOver.entityHit);
                }
                if (i1 == 1) {
                    this.playerController.func_6475_a(this.thePlayer, this.objectMouseOver.entityHit);
                }
            } else if (this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                int i3 = this.objectMouseOver.blockX;
                int i4 = this.objectMouseOver.blockY;
                int i5 = this.objectMouseOver.blockZ;
                int i6 = this.objectMouseOver.sideHit;
                Block block7 = Block.blocksList[this.theWorld.getBlockId(i3, i4, i5)];
                if (i1 == 0) {
                    EventBreakBlock event = new EventBreakBlock(i3, i4, i5, this.objectMouseOver.sideHit);
                    Client.onEvent(event);
                    this.theWorld.onBlockHit(i3, i4, i5, this.objectMouseOver.sideHit);
                    if (block7 != Block.bedrock || this.thePlayer.field_9371_f >= 100) {
                        this.playerController.clickBlock(i3, i4, i5, this.objectMouseOver.sideHit);
                    }
                } else {
                    int i9;
                    ItemStack itemStack8 = this.thePlayer.inventory.getCurrentItem();
                    int n = i9 = itemStack8 != null ? itemStack8.stackSize : 0;
                    if (this.playerController.sendPlaceBlock(this.thePlayer, this.theWorld, itemStack8, i3, i4, i5, i6)) {
                        z2 = false;
                        this.thePlayer.swingItem();
                    }
                    if (itemStack8 == null) {
                        return;
                    }
                    if (itemStack8.stackSize == 0) {
                        this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                    } else if (itemStack8.stackSize != i9) {
                        this.entityRenderer.itemRenderer.func_9449_b();
                    }
                }
            }
            if (z2 && i1 == 1 && (itemStack10 = this.thePlayer.inventory.getCurrentItem()) != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, itemStack10)) {
                this.entityRenderer.itemRenderer.func_9450_c();
            }
        }
    }

    public void toggleFullscreen() {
        try {
            this.fullscreen = !this.fullscreen;
            System.out.println("Toggle fullscreen!");
            if (this.fullscreen) {
                Display.setDisplayMode((DisplayMode)Display.getDesktopDisplayMode());
                this.displayWidth = Display.getDisplayMode().getWidth();
                this.displayHeight = Display.getDisplayMode().getHeight();
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
            } else {
                if (this.mcCanvas != null) {
                    this.displayWidth = this.mcCanvas.getWidth();
                    this.displayHeight = this.mcCanvas.getHeight();
                } else {
                    this.displayWidth = this.field_9236_T;
                    this.displayHeight = this.field_9235_U;
                }
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }
                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }
                Display.setDisplayMode((DisplayMode)new DisplayMode(this.field_9236_T, this.field_9235_U));
            }
            this.setIngameNotInFocus();
            Display.setFullscreen((boolean)this.fullscreen);
            Display.update();
            Thread.sleep(1000L);
            if (this.fullscreen) {
                this.setIngameFocus();
            }
            if (this.currentScreen != null) {
                this.setIngameNotInFocus();
                this.resize(this.displayWidth, this.displayHeight);
            }
            System.out.println("Size: " + this.displayWidth + ", " + this.displayHeight);
        }
        catch (Exception exception2) {
            exception2.printStackTrace();
        }
    }

    private void resize(int i1, int i2) {
        if (i1 <= 0) {
            i1 = 1;
        }
        if (i2 <= 0) {
            i2 = 1;
        }
        this.displayWidth = i1;
        this.displayHeight = i2;
        if (this.currentScreen != null) {
            ScaledResolution scaledResolution3 = new ScaledResolution(this.gameSettings, i1, i2);
            int i4 = scaledResolution3.getScaledWidth();
            int i5 = scaledResolution3.getScaledHeight();
            this.currentScreen.setWorldAndResolution(this, i4, i5);
        }
    }

    private void clickMiddleMouseButton() {
        if (this.objectMouseOver != null) {
            int i1 = this.theWorld.getBlockId(this.objectMouseOver.blockX, this.objectMouseOver.blockY, this.objectMouseOver.blockZ);
            if (i1 == Block.grass.blockID) {
                i1 = Block.dirt.blockID;
            }
            if (i1 == Block.stairDouble.blockID) {
                i1 = Block.stairSingle.blockID;
            }
            if (i1 == Block.bedrock.blockID) {
                i1 = Block.stone.blockID;
            }
            this.thePlayer.inventory.setCurrentItem(i1, this.playerController instanceof PlayerControllerTest);
        }
    }

    public void runTick() {
        int i3;
        IChunkProvider iChunkProvider1;
        this.ingameGUI.updateTick();
        this.entityRenderer.getMouseOver(1.0f);
        if (this.thePlayer != null && (iChunkProvider1 = this.theWorld.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate2 = (ChunkProviderLoadOrGenerate)iChunkProvider1;
            i3 = MathHelper.floor_float((int)this.thePlayer.posX) >> 4;
            int i4 = MathHelper.floor_float((int)this.thePlayer.posZ) >> 4;
            chunkProviderLoadOrGenerate2.setCurrentChunkOver(i3, i4);
        }
        if (!this.isWorldLoaded && this.theWorld != null) {
            this.playerController.updateController();
        }
        GL11.glBindTexture((int)3553, (int)this.renderEngine.getTexture("/terrain.png"));
        if (!this.isWorldLoaded) {
            this.renderEngine.func_1067_a();
        }
        if (this.currentScreen == null && this.thePlayer != null) {
            if (this.thePlayer.health <= 0) {
                this.displayGuiScreen(null);
            } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null && this.theWorld.multiplayerWorld) {
                this.displayGuiScreen(new GuiSleepMP());
            }
        } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
        if (this.currentScreen != null) {
            this.field_6302_aa = this.ticksRan + 10000;
        }
        if (this.currentScreen != null) {
            this.currentScreen.handleInput();
            if (this.currentScreen != null) {
                this.currentScreen.field_25091_h.func_25088_a();
                this.currentScreen.updateScreen();
            }
        }
        if (this.currentScreen == null || this.currentScreen.field_948_f) {
            block0: while (true) {
                if (!Mouse.next()) {
                    if (this.field_6282_S > 0) {
                        --this.field_6282_S;
                    }
                    while (true) {
                        if (!Keyboard.next()) {
                            if (this.currentScreen == null) {
                                if (Mouse.isButtonDown((int)0) && (float)(this.ticksRan - this.field_6302_aa) >= this.timer.ticksPerSecond / 4.0f && this.inGameHasFocus) {
                                    this.clickMouse(0);
                                    this.field_6302_aa = this.ticksRan;
                                }
                                if (Mouse.isButtonDown((int)1) && (float)(this.ticksRan - this.field_6302_aa) >= this.timer.ticksPerSecond / 4.0f && this.inGameHasFocus) {
                                    this.clickMouse(1);
                                    this.field_6302_aa = this.ticksRan;
                                }
                            }
                            this.func_6254_a(0, this.currentScreen == null && Mouse.isButtonDown((int)0) && this.inGameHasFocus);
                            break block0;
                        }
                        this.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
                        if (!Keyboard.getEventKeyState()) continue;
                        if (Keyboard.getEventKey() == 87) {
                            this.toggleFullscreen();
                            continue;
                        }
                        if (this.currentScreen != null) {
                            this.currentScreen.handleKeyboardInput();
                        } else {
                            if (Keyboard.getEventKey() != 0) {
                                EventKey event = new EventKey(Keyboard.getEventKey());
                                Client.onEvent(event);
                            }
                            if (Keyboard.getEventKey() == 1) {
                                this.displayInGameMenu();
                            }
                            if (Keyboard.getEventKey() == 31 && Keyboard.isKeyDown((int)61)) {
                                this.forceReload();
                            }
                            if (Keyboard.getEventKey() == 59) {
                                boolean bl = this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                            }
                            if (Keyboard.getEventKey() == 61) {
                                boolean bl = this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                            }
                            if (Keyboard.getEventKey() == 63) {
                                boolean bl = this.gameSettings.thirdPersonView = !this.gameSettings.thirdPersonView;
                            }
                            if (Keyboard.getEventKey() == 66) {
                                boolean bl = this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                            }
                            if (Keyboard.getEventKey() == this.gameSettings.keyBindInventory.keyCode) {
                                this.displayGuiScreen(new GuiInventory(this.thePlayer));
                            }
                            if (Keyboard.getEventKey() == this.gameSettings.keyBindDrop.keyCode) {
                                this.thePlayer.dropCurrentItem();
                            }
                            if (this.isMultiplayerWorld() && Keyboard.getEventKey() == this.gameSettings.keyBindChat.keyCode) {
                                this.displayGuiScreen(new GuiChat());
                            }
                        }
                        int i6 = 0;
                        while (i6 < 9) {
                            if (Keyboard.getEventKey() == 2 + i6) {
                                this.thePlayer.inventory.currentItem = i6;
                            }
                            ++i6;
                        }
                        if (Keyboard.getEventKey() != this.gameSettings.keyBindToggleFog.keyCode) continue;
                        this.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, !Keyboard.isKeyDown((int)42) && !Keyboard.isKeyDown((int)54) ? 1 : -1);
                    }
                }
                long j5 = System.currentTimeMillis() - this.systemTime;
                if (j5 > 200L) continue;
                i3 = Mouse.getEventDWheel();
                if (i3 != 0) {
                    this.thePlayer.inventory.changeCurrentItem(i3);
                    if (this.gameSettings.field_22275_C) {
                        if (i3 > 0) {
                            i3 = 1;
                        }
                        if (i3 < 0) {
                            i3 = -1;
                        }
                        this.gameSettings.field_22272_F += (float)i3 * 0.25f;
                    }
                }
                if (this.currentScreen == null) {
                    if (!this.inGameHasFocus && Mouse.getEventButtonState()) {
                        this.setIngameFocus();
                        continue;
                    }
                    if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                        this.clickMouse(0);
                        this.field_6302_aa = this.ticksRan;
                    }
                    if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
                        this.clickMouse(1);
                        this.field_6302_aa = this.ticksRan;
                    }
                    if (Mouse.getEventButton() != 2 || !Mouse.getEventButtonState()) continue;
                    this.clickMiddleMouseButton();
                    continue;
                }
                if (this.currentScreen == null) continue;
                this.currentScreen.handleMouseInput();
            }
        }
        if (this.theWorld != null) {
            if (this.thePlayer != null) {
                ++this.field_6300_ab;
                if (this.field_6300_ab == 30) {
                    this.field_6300_ab = 0;
                    this.theWorld.joinEntityInSurroundings(this.thePlayer);
                }
            }
            this.theWorld.difficultySetting = this.gameSettings.difficulty;
            if (this.theWorld.multiplayerWorld) {
                this.theWorld.difficultySetting = 3;
            }
            if (!this.isWorldLoaded) {
                this.entityRenderer.updateRenderer();
            }
            if (!this.isWorldLoaded) {
                this.renderGlobal.updateClouds();
            }
            if (!this.isWorldLoaded) {
                this.theWorld.func_633_c();
            }
            if (!this.isWorldLoaded || this.isMultiplayerWorld()) {
                this.theWorld.setAllowedMobSpawns(this.gameSettings.difficulty > 0, true);
                this.theWorld.tick();
            }
            if (!this.isWorldLoaded && this.theWorld != null) {
                this.theWorld.randomDisplayUpdates(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
            }
            if (!this.isWorldLoaded) {
                this.effectRenderer.updateEffects();
            }
        }
        this.systemTime = System.currentTimeMillis();
    }

    private void forceReload() {
        System.out.println("FORCING RELOAD!");
        this.sndManager = new SoundManager();
        this.sndManager.loadSoundSettings(this.gameSettings);
        this.downloadResourcesThread.reloadResources();
    }

    public boolean isMultiplayerWorld() {
        return this.theWorld != null && this.theWorld.multiplayerWorld;
    }

    public void startWorld(String string1, String string2, long j3) {
        this.changeWorld1(null);
        System.gc();
        if (this.saveLoader.isOldMapFormat(string1)) {
            this.convertMapFormat(string1, string2);
        } else {
            ISaveHandler iSaveHandler5 = this.saveLoader.getSaveLoader(string1, false);
            World world6 = null;
            world6 = new World(iSaveHandler5, string2, j3);
            if (world6.isNewWorld) {
                this.field_25001_G.func_25100_a(StatList.field_25183_f, 1);
                this.field_25001_G.func_25100_a(StatList.field_25184_e, 1);
                this.changeWorld2(world6, "Generating level");
            } else {
                this.field_25001_G.func_25100_a(StatList.field_25182_g, 1);
                this.field_25001_G.func_25100_a(StatList.field_25184_e, 1);
                this.changeWorld2(world6, "Loading level");
            }
        }
    }

    public void usePortal() {
        this.thePlayer.dimension = this.thePlayer.dimension == -1 ? 0 : -1;
        this.theWorld.setEntityDead(this.thePlayer);
        this.thePlayer.isDead = false;
        double d1 = this.thePlayer.posX;
        double d3 = this.thePlayer.posZ;
        double d5 = 8.0;
        if (this.thePlayer.dimension == -1) {
            this.thePlayer.setLocationAndAngles(d1 /= d5, this.thePlayer.posY, d3 /= d5, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
            this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
            World world7 = null;
            world7 = new World(this.theWorld, new WorldProviderHell());
            this.changeWorld(world7, "Entering the Nether", this.thePlayer);
        } else {
            this.thePlayer.setLocationAndAngles(d1 *= d5, this.thePlayer.posY, d3 *= d5, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
            this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
            World world7 = null;
            world7 = new World(this.theWorld, new WorldProvider());
            this.changeWorld(world7, "Leaving the Nether", this.thePlayer);
        }
        this.thePlayer.worldObj = this.theWorld;
        this.thePlayer.setLocationAndAngles(d1, this.thePlayer.posY, d3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
        this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
        new Teleporter().func_4107_a(this.theWorld, this.thePlayer);
    }

    public void changeWorld1(World world1) {
        this.changeWorld2(world1, "");
    }

    public void changeWorld2(World world1, String string2) {
        this.changeWorld(world1, string2, null);
    }

    public void changeWorld(World world1, String string2, EntityPlayer entityPlayer3) {
        this.renderViewEntity = null;
        this.loadingScreen.printText(string2);
        this.loadingScreen.displayLoadingString("");
        this.sndManager.playStreaming(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        if (this.theWorld != null) {
            this.theWorld.func_651_a(this.loadingScreen);
        }
        this.theWorld = world1;
        if (world1 != null) {
            IChunkProvider iChunkProvider4;
            this.playerController.func_717_a(world1);
            if (!this.isMultiplayerWorld()) {
                if (entityPlayer3 == null) {
                    this.thePlayer = (EntityPlayerSP)world1.func_4085_a(EntityPlayerSP.class);
                }
            } else if (this.thePlayer != null) {
                this.thePlayer.preparePlayerToSpawn();
                if (world1 != null) {
                    world1.entityJoinedWorld(this.thePlayer);
                }
            }
            if (!world1.multiplayerWorld) {
                this.func_6255_d(string2);
            }
            if (this.thePlayer == null) {
                this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(world1);
                this.thePlayer.preparePlayerToSpawn();
                this.playerController.flipPlayer(this.thePlayer);
            }
            this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
            if (this.renderGlobal != null) {
                this.renderGlobal.func_946_a(world1);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(world1);
            }
            this.playerController.func_6473_b(this.thePlayer);
            if (entityPlayer3 != null) {
                world1.func_6464_c();
            }
            if ((iChunkProvider4 = world1.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
                ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate5 = (ChunkProviderLoadOrGenerate)iChunkProvider4;
                int i6 = MathHelper.floor_float((int)this.thePlayer.posX) >> 4;
                int i7 = MathHelper.floor_float((int)this.thePlayer.posZ) >> 4;
                chunkProviderLoadOrGenerate5.setCurrentChunkOver(i6, i7);
            }
            world1.spawnPlayerWithLoadedChunks(this.thePlayer);
            if (world1.isNewWorld) {
                world1.func_651_a(this.loadingScreen);
            }
            this.renderViewEntity = this.thePlayer;
        } else {
            this.thePlayer = null;
        }
        System.gc();
        this.systemTime = 0L;
    }

    private void convertMapFormat(String string1, String string2) {
        this.loadingScreen.printText("Converting World to " + this.saveLoader.func_22178_a());
        this.loadingScreen.displayLoadingString("This may take a while :)");
        this.saveLoader.convertMapFormat(string1, this.loadingScreen);
        this.startWorld(string1, string2, 0L);
    }

    private void func_6255_d(String string1) {
        this.loadingScreen.printText(string1);
        this.loadingScreen.displayLoadingString("Building terrain");
        int s2 = 128;
        int i3 = 0;
        int i4 = s2 * 2 / 16 + 1;
        i4 *= i4;
        IChunkProvider iChunkProvider5 = this.theWorld.getIChunkProvider();
        ChunkCoordinates chunkCoordinates6 = this.theWorld.getSpawnPoint();
        if (this.thePlayer != null) {
            chunkCoordinates6.x = (int)this.thePlayer.posX;
            chunkCoordinates6.z = (int)this.thePlayer.posZ;
        }
        if (iChunkProvider5 instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate7 = (ChunkProviderLoadOrGenerate)iChunkProvider5;
            chunkProviderLoadOrGenerate7.setCurrentChunkOver(chunkCoordinates6.x >> 4, chunkCoordinates6.z >> 4);
        }
        int i10 = -s2;
        while (i10 <= s2) {
            int i8 = -s2;
            while (i8 <= s2) {
                this.loadingScreen.setLoadingProgress(i3++ * 100 / i4);
                this.theWorld.getBlockId(chunkCoordinates6.x + i10, 64, chunkCoordinates6.z + i8);
                while (this.theWorld.func_6465_g()) {
                }
                i8 += 16;
            }
            i10 += 16;
        }
        this.loadingScreen.displayLoadingString("Simulating world for a bit");
        boolean z9 = true;
        this.theWorld.func_656_j();
    }

    public void installResource(String string1, File file2) {
        int i3 = string1.indexOf("/");
        String string4 = string1.substring(0, i3);
        string1 = string1.substring(i3 + 1);
        if (string4.equalsIgnoreCase("sound")) {
            this.sndManager.addSound(string1, file2);
        } else if (string4.equalsIgnoreCase("newsound")) {
            this.sndManager.addSound(string1, file2);
        } else if (string4.equalsIgnoreCase("streaming")) {
            this.sndManager.addStreaming(string1, file2);
        } else if (string4.equalsIgnoreCase("music")) {
            this.sndManager.addMusic(string1, file2);
        } else if (string4.equalsIgnoreCase("newmusic")) {
            this.sndManager.addMusic(string1, file2);
        }
    }

    public OpenGlCapsChecker func_6251_l() {
        return this.glCapabilities;
    }

    public String func_6241_m() {
        return this.renderGlobal.func_953_b();
    }

    public String func_6262_n() {
        return this.renderGlobal.func_957_c();
    }

    public String func_21002_o() {
        return this.theWorld.func_21119_g();
    }

    public String func_6245_o() {
        return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.func_687_d();
    }

    public void respawn(boolean z1) {
        IChunkProvider iChunkProvider5;
        if (!this.theWorld.worldProvider.canRespawnHere()) {
            this.usePortal();
        }
        ChunkCoordinates chunkCoordinates2 = null;
        ChunkCoordinates chunkCoordinates3 = null;
        boolean z4 = true;
        if (this.thePlayer != null && !z1 && (chunkCoordinates2 = this.thePlayer.getPlayerSpawnCoordinate()) != null && (chunkCoordinates3 = EntityPlayer.func_25060_a(this.theWorld, chunkCoordinates2)) == null) {
            this.thePlayer.addChatMessage("tile.bed.notValid");
        }
        if (chunkCoordinates3 == null) {
            chunkCoordinates3 = this.theWorld.getSpawnPoint();
            z4 = false;
        }
        if ((iChunkProvider5 = this.theWorld.getIChunkProvider()) instanceof ChunkProviderLoadOrGenerate) {
            ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate6 = (ChunkProviderLoadOrGenerate)iChunkProvider5;
            chunkProviderLoadOrGenerate6.setCurrentChunkOver(chunkCoordinates3.x >> 4, chunkCoordinates3.z >> 4);
        }
        this.theWorld.setSpawnLocation();
        this.theWorld.updateEntityList();
        int i7 = 0;
        if (this.thePlayer != null) {
            i7 = this.thePlayer.entityId;
            this.theWorld.setEntityDead(this.thePlayer);
        }
        this.renderViewEntity = null;
        this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(this.theWorld);
        this.renderViewEntity = this.thePlayer;
        this.thePlayer.preparePlayerToSpawn();
        if (z4) {
            this.thePlayer.setPlayerSpawnCoordinate(chunkCoordinates2);
            this.thePlayer.setLocationAndAngles((float)chunkCoordinates3.x + 0.5f, (float)chunkCoordinates3.y + 0.1f, (float)chunkCoordinates3.z + 0.5f, 0.0f, 0.0f);
        }
        this.playerController.flipPlayer(this.thePlayer);
        this.theWorld.spawnPlayerWithLoadedChunks(this.thePlayer);
        this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.thePlayer.entityId = i7;
        this.thePlayer.func_6420_o();
        this.playerController.func_6473_b(this.thePlayer);
        this.func_6255_d("Respawning");
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public static void func_6269_a(String string0, String string1) {
        Minecraft.startMainThread(string0, string1, null);
    }

    public static void startMainThread(String string0, String string1, String string2) {
        boolean z3 = false;
        Frame frame5 = new Frame("Minecraft");
        Canvas canvas6 = new Canvas();
        frame5.setLayout(new BorderLayout());
        frame5.add((Component)canvas6, "Center");
        canvas6.setPreferredSize(new Dimension(854, 480));
        frame5.pack();
        frame5.setLocationRelativeTo(null);
        MinecraftImpl minecraftImpl7 = new MinecraftImpl(frame5, canvas6, null, 854, 480, z3, frame5);
        Thread thread8 = new Thread((Runnable)minecraftImpl7, "Minecraft main thread");
        thread8.setPriority(10);
        minecraftImpl7.minecraftUri = "www.minecraft.net";
        minecraftImpl7.session = string0 != null && string1 != null ? new Session(string0, string1) : new Session("Player" + System.currentTimeMillis() % 1000L, "");
        if (string2 != null) {
            String[] string9 = string2.split(":");
            minecraftImpl7.setServer(string9[0], Integer.parseInt(string9[1]));
        }
        frame5.setVisible(true);
        frame5.addWindowListener(new GameWindowListener(minecraftImpl7, thread8));
        thread8.start();
    }

    public NetClientHandler func_20001_q() {
        return this.thePlayer instanceof EntityClientPlayerMP ? ((EntityClientPlayerMP)this.thePlayer).sendQueue : null;
    }

    public static void main(String[] string0) {
        String string1 = null;
        String string2 = null;
        string1 = "Player" + System.currentTimeMillis() % 1000L;
        if (string0.length > 0) {
            string1 = string0[0];
        }
        string2 = "-";
        if (string0.length > 1) {
            string2 = string0[1];
        }
        Minecraft.func_6269_a(string1, string2);
    }

    public static boolean isGuiEnabled() {
        return theMinecraft == null || !Minecraft.theMinecraft.gameSettings.hideGUI;
    }

    public static boolean isFancyGraphicsEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.fancyGraphics;
    }

    public static boolean isAmbientOcclusionEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.ambientOcclusion;
    }

    public static boolean isDebugInfoEnabled() {
        return theMinecraft != null && Minecraft.theMinecraft.gameSettings.showDebugInfo;
    }

    public boolean lineIsCommand(String string1) {
        if (string1.startsWith("/")) {
            // empty if block
        }
        return false;
    }
}

