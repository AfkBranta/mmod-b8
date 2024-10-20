/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.CraftingInventoryCB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityEgg;
import net.minecraft.src.EntityFallingSand;
import net.minecraft.src.EntityFish;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPainting;
import net.minecraft.src.EntityPickupFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntitySnowball;
import net.minecraft.src.EntityTNTPrimed;
import net.minecraft.src.Explosion;
import net.minecraft.src.GuiConnectFailed;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.InventoryBasic;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet100;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet103;
import net.minecraft.src.Packet104;
import net.minecraft.src.Packet105;
import net.minecraft.src.Packet106;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet130;
import net.minecraft.src.Packet17Sleep;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet21PickupSpawn;
import net.minecraft.src.Packet22Collect;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet24MobSpawn;
import net.minecraft.src.Packet25;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet28;
import net.minecraft.src.Packet29DestroyEntity;
import net.minecraft.src.Packet2Handshake;
import net.minecraft.src.Packet30Entity;
import net.minecraft.src.Packet34EntityTeleport;
import net.minecraft.src.Packet38;
import net.minecraft.src.Packet39;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet40;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.Packet50PreChunk;
import net.minecraft.src.Packet51MapChunk;
import net.minecraft.src.Packet52MultiBlockChange;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.Packet54;
import net.minecraft.src.Packet5PlayerInventory;
import net.minecraft.src.Packet60;
import net.minecraft.src.Packet6SpawnPosition;
import net.minecraft.src.Packet70;
import net.minecraft.src.Packet8;
import net.minecraft.src.Packet9;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.Session;
import net.minecraft.src.StatList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.WorldClient;

public class NetClientHandler
extends NetHandler {
    private boolean disconnected = false;
    public static NetworkManager netManager;
    public String field_1209_a;
    private Minecraft mc;
    private WorldClient worldClient;
    private boolean field_1210_g = false;
    Random rand = new Random();

    public NetClientHandler(Minecraft minecraft1, String string2, int i3) throws UnknownHostException, IOException {
        this.mc = minecraft1;
        Socket socket4 = new Socket(InetAddress.getByName(string2), i3);
        netManager = new NetworkManager(socket4, "Client", this);
    }

    public void processReadPackets() {
        if (!this.disconnected) {
            netManager.processReadPackets();
        }
    }

    @Override
    public void handleLogin(Packet1Login packet1Login1) {
        this.mc.playerController = new PlayerControllerMP(this.mc, this);
        this.mc.field_25001_G.func_25100_a(StatList.field_25181_h, 1);
        this.worldClient = new WorldClient(this, packet1Login1.mapSeed, packet1Login1.dimension);
        this.worldClient.multiplayerWorld = true;
        this.mc.changeWorld1(this.worldClient);
        this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
        this.mc.thePlayer.entityId = packet1Login1.protocolVersion;
    }

    @Override
    public void handlePickupSpawn(Packet21PickupSpawn packet21PickupSpawn1) {
        double d2 = (double)packet21PickupSpawn1.xPosition / 32.0;
        double d4 = (double)packet21PickupSpawn1.yPosition / 32.0;
        double d6 = (double)packet21PickupSpawn1.zPosition / 32.0;
        EntityItem entityItem8 = new EntityItem(this.worldClient, d2, d4, d6, new ItemStack(packet21PickupSpawn1.itemID, packet21PickupSpawn1.count, packet21PickupSpawn1.itemDamage));
        entityItem8.motionX = (double)packet21PickupSpawn1.rotation / 128.0;
        entityItem8.motionY = (double)packet21PickupSpawn1.pitch / 128.0;
        entityItem8.motionZ = (double)packet21PickupSpawn1.roll / 128.0;
        entityItem8.serverPosX = packet21PickupSpawn1.xPosition;
        entityItem8.serverPosY = packet21PickupSpawn1.yPosition;
        entityItem8.serverPosZ = packet21PickupSpawn1.zPosition;
        this.worldClient.func_712_a(packet21PickupSpawn1.entityId, entityItem8);
    }

    @Override
    public void handleVehicleSpawn(Packet23VehicleSpawn packet23VehicleSpawn1) {
        double d2 = (double)packet23VehicleSpawn1.xPosition / 32.0;
        double d4 = (double)packet23VehicleSpawn1.yPosition / 32.0;
        double d6 = (double)packet23VehicleSpawn1.zPosition / 32.0;
        Entity object8 = null;
        if (packet23VehicleSpawn1.type == 10) {
            object8 = new EntityMinecart(this.worldClient, d2, d4, d6, 0);
        }
        if (packet23VehicleSpawn1.type == 11) {
            object8 = new EntityMinecart(this.worldClient, d2, d4, d6, 1);
        }
        if (packet23VehicleSpawn1.type == 12) {
            object8 = new EntityMinecart(this.worldClient, d2, d4, d6, 2);
        }
        if (packet23VehicleSpawn1.type == 90) {
            object8 = new EntityFish(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 60) {
            object8 = new EntityArrow(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 61) {
            object8 = new EntitySnowball(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 62) {
            object8 = new EntityEgg(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 1) {
            object8 = new EntityBoat(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 50) {
            object8 = new EntityTNTPrimed(this.worldClient, d2, d4, d6);
        }
        if (packet23VehicleSpawn1.type == 70) {
            object8 = new EntityFallingSand(this.worldClient, d2, d4, d6, Block.sand.blockID);
        }
        if (packet23VehicleSpawn1.type == 71) {
            object8 = new EntityFallingSand(this.worldClient, d2, d4, d6, Block.gravel.blockID);
        }
        if (object8 != null) {
            ((Entity)object8).serverPosX = packet23VehicleSpawn1.xPosition;
            ((Entity)object8).serverPosY = packet23VehicleSpawn1.yPosition;
            ((Entity)object8).serverPosZ = packet23VehicleSpawn1.zPosition;
            ((Entity)object8).rotationYaw = 0.0f;
            ((Entity)object8).rotationPitch = 0.0f;
            ((Entity)object8).entityId = packet23VehicleSpawn1.entityId;
            this.worldClient.func_712_a(packet23VehicleSpawn1.entityId, object8);
        }
    }

    @Override
    public void func_21146_a(Packet25 packet251) {
        EntityPainting entityPainting2 = new EntityPainting(this.worldClient, packet251.xPosition, packet251.yPosition, packet251.zPosition, packet251.direction, packet251.title);
        this.worldClient.func_712_a(packet251.entityId, entityPainting2);
    }

    @Override
    public void func_6498_a(Packet28 packet281) {
        Entity entity2 = this.getEntityByID(packet281.entityId);
        if (entity2 != null) {
            entity2.setVelocity((double)packet281.motionX / 8000.0, (double)packet281.motionY / 8000.0, (double)packet281.motionZ / 8000.0);
        }
    }

    @Override
    public void func_21148_a(Packet40 packet401) {
        Entity entity2 = this.getEntityByID(packet401.entityId);
        if (entity2 != null && packet401.func_21047_b() != null) {
            entity2.getDataWatcher().updateWatchedObjectsFromList(packet401.func_21047_b());
        }
    }

    @Override
    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20NamedEntitySpawn1) {
        double d2 = (double)packet20NamedEntitySpawn1.xPosition / 32.0;
        double d4 = (double)packet20NamedEntitySpawn1.yPosition / 32.0;
        double d6 = (double)packet20NamedEntitySpawn1.zPosition / 32.0;
        float f8 = (float)(packet20NamedEntitySpawn1.rotation * 360) / 256.0f;
        float f9 = (float)(packet20NamedEntitySpawn1.pitch * 360) / 256.0f;
        EntityOtherPlayerMP entityOtherPlayerMP10 = new EntityOtherPlayerMP(this.mc.theWorld, packet20NamedEntitySpawn1.name);
        entityOtherPlayerMP10.serverPosX = packet20NamedEntitySpawn1.xPosition;
        entityOtherPlayerMP10.serverPosY = packet20NamedEntitySpawn1.yPosition;
        entityOtherPlayerMP10.serverPosZ = packet20NamedEntitySpawn1.zPosition;
        int i11 = packet20NamedEntitySpawn1.currentItem;
        entityOtherPlayerMP10.inventory.mainInventory[entityOtherPlayerMP10.inventory.currentItem] = i11 == 0 ? null : new ItemStack(i11, 1, 0);
        entityOtherPlayerMP10.setPositionAndRotation(d2, d4, d6, f8, f9);
        this.worldClient.func_712_a(packet20NamedEntitySpawn1.entityId, entityOtherPlayerMP10);
    }

    @Override
    public void handleEntityTeleport(Packet34EntityTeleport packet34EntityTeleport1) {
        Entity entity2 = this.getEntityByID(packet34EntityTeleport1.entityId);
        if (entity2 != null) {
            entity2.serverPosX = packet34EntityTeleport1.xPosition;
            entity2.serverPosY = packet34EntityTeleport1.yPosition;
            entity2.serverPosZ = packet34EntityTeleport1.zPosition;
            double d3 = (double)entity2.serverPosX / 32.0;
            double d5 = (double)entity2.serverPosY / 32.0 + 0.015625;
            double d7 = (double)entity2.serverPosZ / 32.0;
            float f9 = (float)(packet34EntityTeleport1.yaw * 360) / 256.0f;
            float f10 = (float)(packet34EntityTeleport1.pitch * 360) / 256.0f;
            entity2.setPositionAndRotation2(d3, d5, d7, f9, f10, 3);
        }
    }

    @Override
    public void handleEntity(Packet30Entity packet30Entity1) {
        Entity entity2 = this.getEntityByID(packet30Entity1.entityId);
        if (entity2 != null) {
            entity2.serverPosX += packet30Entity1.xPosition;
            entity2.serverPosY += packet30Entity1.yPosition;
            entity2.serverPosZ += packet30Entity1.zPosition;
            double d3 = (double)entity2.serverPosX / 32.0;
            double d5 = (double)entity2.serverPosY / 32.0 + 0.015625;
            double d7 = (double)entity2.serverPosZ / 32.0;
            float f9 = packet30Entity1.rotating ? (float)(packet30Entity1.yaw * 360) / 256.0f : entity2.rotationYaw;
            float f10 = packet30Entity1.rotating ? (float)(packet30Entity1.pitch * 360) / 256.0f : entity2.rotationPitch;
            entity2.setPositionAndRotation2(d3, d5, d7, f9, f10, 3);
        }
    }

    @Override
    public void handleDestroyEntity(Packet29DestroyEntity packet29DestroyEntity1) {
        this.worldClient.removeEntityFromWorld(packet29DestroyEntity1.entityId);
    }

    @Override
    public void handleFlying(Packet10Flying packet10Flying1) {
        EntityPlayerSP entityPlayerSP2 = this.mc.thePlayer;
        double d3 = entityPlayerSP2.posX;
        double d5 = entityPlayerSP2.posY;
        double d7 = entityPlayerSP2.posZ;
        float f9 = entityPlayerSP2.rotationYaw;
        float f10 = entityPlayerSP2.rotationPitch;
        if (packet10Flying1.moving) {
            d3 = packet10Flying1.xPosition;
            d5 = packet10Flying1.yPosition;
            d7 = packet10Flying1.zPosition;
        }
        if (packet10Flying1.rotating) {
            f9 = packet10Flying1.yaw;
            f10 = packet10Flying1.pitch;
        }
        entityPlayerSP2.ySize = 0.0f;
        entityPlayerSP2.motionZ = 0.0;
        entityPlayerSP2.motionY = 0.0;
        entityPlayerSP2.motionX = 0.0;
        entityPlayerSP2.setPositionAndRotation(d3, d5, d7, f9, f10);
        packet10Flying1.xPosition = entityPlayerSP2.posX;
        packet10Flying1.yPosition = entityPlayerSP2.boundingBox.minY;
        packet10Flying1.zPosition = entityPlayerSP2.posZ;
        packet10Flying1.stance = entityPlayerSP2.posY;
        netManager.addToSendQueue(packet10Flying1);
        if (!this.field_1210_g) {
            this.mc.thePlayer.prevPosX = this.mc.thePlayer.posX;
            this.mc.thePlayer.prevPosY = this.mc.thePlayer.posY;
            this.mc.thePlayer.prevPosZ = this.mc.thePlayer.posZ;
            this.field_1210_g = true;
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void handlePreChunk(Packet50PreChunk packet50PreChunk1) {
        this.worldClient.func_713_a(packet50PreChunk1.xPosition, packet50PreChunk1.yPosition, packet50PreChunk1.mode);
    }

    @Override
    public void handleMultiBlockChange(Packet52MultiBlockChange packet52MultiBlockChange1) {
        Chunk chunk2 = this.worldClient.getChunkFromChunkCoords(packet52MultiBlockChange1.xPosition, packet52MultiBlockChange1.zPosition);
        int i3 = packet52MultiBlockChange1.xPosition * 16;
        int i4 = packet52MultiBlockChange1.zPosition * 16;
        int i5 = 0;
        while (i5 < packet52MultiBlockChange1.size) {
            short s6 = packet52MultiBlockChange1.coordinateArray[i5];
            int i7 = packet52MultiBlockChange1.typeArray[i5] & 0xFF;
            byte b8 = packet52MultiBlockChange1.metadataArray[i5];
            int i9 = s6 >> 12 & 0xF;
            int i10 = s6 >> 8 & 0xF;
            int i11 = s6 & 0xFF;
            chunk2.setBlockIDWithMetadata(i9, i11, i10, i7, b8);
            this.worldClient.func_711_c(i9 + i3, i11, i10 + i4, i9 + i3, i11, i10 + i4);
            this.worldClient.markBlocksDirty(i9 + i3, i11, i10 + i4, i9 + i3, i11, i10 + i4);
            ++i5;
        }
    }

    @Override
    public void handleMapChunk(Packet51MapChunk packet51MapChunk1) {
        this.worldClient.func_711_c(packet51MapChunk1.xPosition, packet51MapChunk1.yPosition, packet51MapChunk1.zPosition, packet51MapChunk1.xPosition + packet51MapChunk1.xSize - 1, packet51MapChunk1.yPosition + packet51MapChunk1.ySize - 1, packet51MapChunk1.zPosition + packet51MapChunk1.zSize - 1);
        this.worldClient.setChunkData(packet51MapChunk1.xPosition, packet51MapChunk1.yPosition, packet51MapChunk1.zPosition, packet51MapChunk1.xSize, packet51MapChunk1.ySize, packet51MapChunk1.zSize, packet51MapChunk1.chunk);
    }

    @Override
    public void handleBlockChange(Packet53BlockChange packet53BlockChange1) {
        this.worldClient.func_714_c(packet53BlockChange1.xPosition, packet53BlockChange1.yPosition, packet53BlockChange1.zPosition, packet53BlockChange1.type, packet53BlockChange1.metadata);
    }

    @Override
    public void handleKickDisconnect(Packet255KickDisconnect packet255KickDisconnect1) {
        netManager.networkShutdown("disconnect.kicked", new Object[0]);
        this.disconnected = true;
        this.mc.changeWorld1(null);
        this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.disconnected", "disconnect.genericReason", packet255KickDisconnect1.reason));
    }

    @Override
    public void handleErrorMessage(String string1, Object[] object2) {
        if (!this.disconnected) {
            this.disconnected = true;
            this.mc.changeWorld1(null);
            this.mc.displayGuiScreen(new GuiConnectFailed("disconnect.lost", string1, object2));
        }
    }

    public void addToSendQueue(Packet packet1) {
        if (!this.disconnected) {
            netManager.addToSendQueue(packet1);
        }
    }

    @Override
    public void handleCollect(Packet22Collect packet22Collect1) {
        Entity entity2 = this.getEntityByID(packet22Collect1.collectedEntityId);
        EntityLiving object3 = (EntityLiving)this.getEntityByID(packet22Collect1.collectorEntityId);
        if (object3 == null) {
            object3 = this.mc.thePlayer;
        }
        if (entity2 != null) {
            this.worldClient.playSoundAtEntity(entity2, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, entity2, object3, -0.5f));
            this.worldClient.removeEntityFromWorld(packet22Collect1.collectedEntityId);
        }
    }

    @Override
    public void handleChat(Packet3Chat packet3Chat1) {
        this.mc.ingameGUI.addChatMessage(packet3Chat1.message);
    }

    @Override
    public void handleArmAnimation(Packet18ArmAnimation packet18ArmAnimation1) {
        Entity entity2 = this.getEntityByID(packet18ArmAnimation1.entityId);
        if (entity2 != null) {
            if (packet18ArmAnimation1.animate == 1) {
                EntityPlayer entityPlayer3 = (EntityPlayer)entity2;
                entityPlayer3.swingItem();
            } else if (packet18ArmAnimation1.animate == 2) {
                entity2.performHurtAnimation();
            } else if (packet18ArmAnimation1.animate == 3) {
                EntityPlayer entityPlayer3 = (EntityPlayer)entity2;
                entityPlayer3.wakeUpPlayer(false, false, false);
            } else if (packet18ArmAnimation1.animate == 4) {
                EntityPlayer entityPlayer3 = (EntityPlayer)entity2;
                entityPlayer3.func_6420_o();
            }
        }
    }

    @Override
    public void func_22186_a(Packet17Sleep packet17Sleep1) {
        Entity entity2 = this.getEntityByID(packet17Sleep1.field_22045_a);
        if (entity2 != null && packet17Sleep1.field_22046_e == 0) {
            EntityPlayer entityPlayer3 = (EntityPlayer)entity2;
            entityPlayer3.sleepInBedAt(packet17Sleep1.field_22044_b, packet17Sleep1.field_22048_c, packet17Sleep1.field_22047_d);
        }
    }

    @Override
    public void handleHandshake(Packet2Handshake packet2Handshake1) {
        if (packet2Handshake1.username.equals("-")) {
            Session cfr_ignored_0 = this.mc.session;
            this.addToSendQueue(new Packet1Login(Session.username, "Password", 10));
        } else {
            try {
                Session cfr_ignored_1 = this.mc.session;
                URL uRL2 = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + Session.username + "&sessionId=" + this.mc.session.sessionId + "&serverId=" + packet2Handshake1.username);
                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(uRL2.openStream()));
                String string4 = bufferedReader3.readLine();
                bufferedReader3.close();
                if (string4.equalsIgnoreCase("ok")) {
                    Session cfr_ignored_2 = this.mc.session;
                    this.addToSendQueue(new Packet1Login(Session.username, "Password", 10));
                } else {
                    netManager.networkShutdown("disconnect.loginFailedInfo", string4);
                }
            }
            catch (Exception exception5) {
                exception5.printStackTrace();
                netManager.networkShutdown("disconnect.genericReason", "Internal client error: " + exception5.toString());
            }
        }
    }

    public void disconnect() {
        this.disconnected = true;
        netManager.networkShutdown("disconnect.closed", new Object[0]);
    }

    @Override
    public void handleMobSpawn(Packet24MobSpawn packet24MobSpawn1) {
        double d2 = (double)packet24MobSpawn1.xPosition / 32.0;
        double d4 = (double)packet24MobSpawn1.yPosition / 32.0;
        double d6 = (double)packet24MobSpawn1.zPosition / 32.0;
        float f8 = (float)(packet24MobSpawn1.yaw * 360) / 256.0f;
        float f9 = (float)(packet24MobSpawn1.pitch * 360) / 256.0f;
        EntityLiving entityLiving10 = (EntityLiving)EntityList.createEntity(packet24MobSpawn1.type, this.mc.theWorld);
        entityLiving10.serverPosX = packet24MobSpawn1.xPosition;
        entityLiving10.serverPosY = packet24MobSpawn1.yPosition;
        entityLiving10.serverPosZ = packet24MobSpawn1.zPosition;
        entityLiving10.entityId = packet24MobSpawn1.entityId;
        entityLiving10.setPositionAndRotation(d2, d4, d6, f8, f9);
        entityLiving10.field_9343_G = true;
        this.worldClient.func_712_a(packet24MobSpawn1.entityId, entityLiving10);
        List list11 = packet24MobSpawn1.getMetadata();
        if (list11 != null) {
            entityLiving10.getDataWatcher().updateWatchedObjectsFromList(list11);
        }
    }

    @Override
    public void handleUpdateTime(Packet4UpdateTime packet4UpdateTime1) {
        this.mc.theWorld.setWorldTime(packet4UpdateTime1.time);
    }

    @Override
    public void handleSpawnPosition(Packet6SpawnPosition packet6SpawnPosition1) {
        this.mc.thePlayer.setPlayerSpawnCoordinate(new ChunkCoordinates(packet6SpawnPosition1.xPosition, packet6SpawnPosition1.yPosition, packet6SpawnPosition1.zPosition));
    }

    @Override
    public void func_6497_a(Packet39 packet391) {
        Entity object2 = this.getEntityByID(packet391.entityId);
        Entity entity3 = this.getEntityByID(packet391.vehicleEntityId);
        if (packet391.entityId == this.mc.thePlayer.entityId) {
            object2 = this.mc.thePlayer;
        }
        if (object2 != null) {
            object2.mountEntity(entity3);
        }
    }

    @Override
    public void func_9447_a(Packet38 packet381) {
        Entity entity2 = this.getEntityByID(packet381.entityId);
        if (entity2 != null) {
            entity2.handleHealthUpdate(packet381.entityStatus);
        }
    }

    private Entity getEntityByID(int i1) {
        return i1 == this.mc.thePlayer.entityId ? this.mc.thePlayer : this.worldClient.func_709_b(i1);
    }

    @Override
    public void handleHealth(Packet8 packet81) {
        this.mc.thePlayer.setHealth(packet81.healthMP);
    }

    @Override
    public void func_9448_a(Packet9 packet91) {
        this.mc.respawn(true);
    }

    @Override
    public void func_12245_a(Packet60 packet601) {
        Explosion explosion2 = new Explosion(this.mc.theWorld, null, packet601.explosionX, packet601.explosionY, packet601.explosionZ, packet601.explosionSize);
        explosion2.destroyedBlockPositions = packet601.destroyedBlockPositions;
        explosion2.doExplosionB();
    }

    @Override
    public void func_20087_a(Packet100 packet1001) {
        if (packet1001.inventoryType == 0) {
            InventoryBasic inventoryBasic2 = new InventoryBasic(packet1001.windowTitle, packet1001.slotsCount);
            this.mc.thePlayer.displayGUIChest(inventoryBasic2);
            this.mc.thePlayer.craftingInventory.windowId = packet1001.windowId;
        } else if (packet1001.inventoryType == 2) {
            TileEntityFurnace tileEntityFurnace3 = new TileEntityFurnace();
            this.mc.thePlayer.displayGUIFurnace(tileEntityFurnace3);
            this.mc.thePlayer.craftingInventory.windowId = packet1001.windowId;
        } else if (packet1001.inventoryType == 3) {
            TileEntityDispenser tileEntityDispenser4 = new TileEntityDispenser();
            this.mc.thePlayer.displayGUIDispenser(tileEntityDispenser4);
            this.mc.thePlayer.craftingInventory.windowId = packet1001.windowId;
        } else if (packet1001.inventoryType == 1) {
            EntityPlayerSP entityPlayerSP5 = this.mc.thePlayer;
            this.mc.thePlayer.displayWorkbenchGUI(MathHelper.floor_double(entityPlayerSP5.posX), MathHelper.floor_double(entityPlayerSP5.posY), MathHelper.floor_double(entityPlayerSP5.posZ));
            this.mc.thePlayer.craftingInventory.windowId = packet1001.windowId;
        }
    }

    @Override
    public void func_20088_a(Packet103 packet1031) {
        if (packet1031.windowId == -1) {
            this.mc.thePlayer.inventory.setItemStack(packet1031.myItemStack);
        } else if (packet1031.windowId == 0) {
            this.mc.thePlayer.inventorySlots.putStackInSlot(packet1031.itemSlot, packet1031.myItemStack);
        } else if (packet1031.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            this.mc.thePlayer.craftingInventory.putStackInSlot(packet1031.itemSlot, packet1031.myItemStack);
        }
    }

    @Override
    public void func_20089_a(Packet106 packet1061) {
        CraftingInventoryCB craftingInventoryCB2 = null;
        if (packet1061.windowId == 0) {
            craftingInventoryCB2 = this.mc.thePlayer.inventorySlots;
        } else if (packet1061.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            craftingInventoryCB2 = this.mc.thePlayer.craftingInventory;
        }
        if (craftingInventoryCB2 != null) {
            if (packet1061.field_20030_c) {
                craftingInventoryCB2.func_20113_a(packet1061.field_20028_b);
            } else {
                craftingInventoryCB2.func_20110_b(packet1061.field_20028_b);
                this.addToSendQueue(new Packet106(packet1061.windowId, packet1061.field_20028_b, true));
            }
        }
    }

    @Override
    public void func_20094_a(Packet104 packet1041) {
        if (packet1041.windowId == 0) {
            this.mc.thePlayer.inventorySlots.putStacksInSlots(packet1041.itemStack);
        } else if (packet1041.windowId == this.mc.thePlayer.craftingInventory.windowId) {
            this.mc.thePlayer.craftingInventory.putStacksInSlots(packet1041.itemStack);
        }
    }

    @Override
    public void func_20093_a(Packet130 packet1301) {
        TileEntity tileEntity2;
        if (this.mc.theWorld.blockExists(packet1301.xPosition, packet1301.yPosition, packet1301.zPosition) && (tileEntity2 = this.mc.theWorld.getBlockTileEntity(packet1301.xPosition, packet1301.yPosition, packet1301.zPosition)) instanceof TileEntitySign) {
            TileEntitySign tileEntitySign3 = (TileEntitySign)tileEntity2;
            int i4 = 0;
            while (i4 < 4) {
                tileEntitySign3.signText[i4] = packet1301.signLines[i4];
                ++i4;
            }
            tileEntitySign3.onInventoryChanged();
        }
    }

    @Override
    public void func_20090_a(Packet105 packet1051) {
        this.registerPacket(packet1051);
        if (this.mc.thePlayer.craftingInventory != null && this.mc.thePlayer.craftingInventory.windowId == packet1051.windowId) {
            this.mc.thePlayer.craftingInventory.func_20112_a(packet1051.progressBar, packet1051.progressBarValue);
        }
    }

    @Override
    public void handlePlayerInventory(Packet5PlayerInventory packet5PlayerInventory1) {
        Entity entity2 = this.getEntityByID(packet5PlayerInventory1.entityID);
        if (entity2 != null) {
            entity2.outfitWithItem(packet5PlayerInventory1.slot, packet5PlayerInventory1.itemID, packet5PlayerInventory1.itemDamage);
        }
    }

    @Override
    public void func_20092_a(Packet101 packet1011) {
        this.mc.thePlayer.func_20059_m();
    }

    @Override
    public void func_21145_a(Packet54 packet541) {
        this.mc.theWorld.playNoteAt(packet541.xLocation, packet541.yLocation, packet541.zLocation, packet541.instrumentType, packet541.pitch);
    }

    @Override
    public void func_25118_a(Packet70 packet701) {
        int i2 = packet701.field_25019_b;
        if (i2 >= 0 && i2 < Packet70.field_25020_a.length) {
            this.mc.thePlayer.addChatMessage(Packet70.field_25020_a[i2]);
        }
    }
}

