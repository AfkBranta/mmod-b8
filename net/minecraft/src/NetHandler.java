/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet100;
import net.minecraft.src.Packet101;
import net.minecraft.src.Packet102;
import net.minecraft.src.Packet103;
import net.minecraft.src.Packet104;
import net.minecraft.src.Packet105;
import net.minecraft.src.Packet106;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet130;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet17Sleep;
import net.minecraft.src.Packet18ArmAnimation;
import net.minecraft.src.Packet19;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet20NamedEntitySpawn;
import net.minecraft.src.Packet21PickupSpawn;
import net.minecraft.src.Packet22Collect;
import net.minecraft.src.Packet23VehicleSpawn;
import net.minecraft.src.Packet24MobSpawn;
import net.minecraft.src.Packet25;
import net.minecraft.src.Packet255KickDisconnect;
import net.minecraft.src.Packet27;
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
import net.minecraft.src.Packet7;
import net.minecraft.src.Packet70;
import net.minecraft.src.Packet8;
import net.minecraft.src.Packet9;

public class NetHandler {
    public void handleMapChunk(Packet51MapChunk packet51MapChunk1) {
    }

    public void registerPacket(Packet packet1) {
    }

    public void handleErrorMessage(String string1, Object[] object2) {
    }

    public void handleKickDisconnect(Packet255KickDisconnect packet255KickDisconnect1) {
        this.registerPacket(packet255KickDisconnect1);
    }

    public void handleLogin(Packet1Login packet1Login1) {
        this.registerPacket(packet1Login1);
    }

    public void handleFlying(Packet10Flying packet10Flying1) {
        this.registerPacket(packet10Flying1);
    }

    public void handleMultiBlockChange(Packet52MultiBlockChange packet52MultiBlockChange1) {
        this.registerPacket(packet52MultiBlockChange1);
    }

    public void handleBlockDig(Packet14BlockDig packet14BlockDig1) {
        this.registerPacket(packet14BlockDig1);
    }

    public void handleBlockChange(Packet53BlockChange packet53BlockChange1) {
        this.registerPacket(packet53BlockChange1);
    }

    public void handlePreChunk(Packet50PreChunk packet50PreChunk1) {
        this.registerPacket(packet50PreChunk1);
    }

    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet20NamedEntitySpawn1) {
        this.registerPacket(packet20NamedEntitySpawn1);
    }

    public void handleEntity(Packet30Entity packet30Entity1) {
        this.registerPacket(packet30Entity1);
    }

    public void handleEntityTeleport(Packet34EntityTeleport packet34EntityTeleport1) {
        this.registerPacket(packet34EntityTeleport1);
    }

    public void handlePlace(Packet15Place packet15Place1) {
        this.registerPacket(packet15Place1);
    }

    public void handleBlockItemSwitch(Packet16BlockItemSwitch packet16BlockItemSwitch1) {
        this.registerPacket(packet16BlockItemSwitch1);
    }

    public void handleDestroyEntity(Packet29DestroyEntity packet29DestroyEntity1) {
        this.registerPacket(packet29DestroyEntity1);
    }

    public void handlePickupSpawn(Packet21PickupSpawn packet21PickupSpawn1) {
        this.registerPacket(packet21PickupSpawn1);
    }

    public void handleCollect(Packet22Collect packet22Collect1) {
        this.registerPacket(packet22Collect1);
    }

    public void handleChat(Packet3Chat packet3Chat1) {
        this.registerPacket(packet3Chat1);
    }

    public void handleVehicleSpawn(Packet23VehicleSpawn packet23VehicleSpawn1) {
        this.registerPacket(packet23VehicleSpawn1);
    }

    public void handleArmAnimation(Packet18ArmAnimation packet18ArmAnimation1) {
        this.registerPacket(packet18ArmAnimation1);
    }

    public void func_21147_a(Packet19 packet191) {
        this.registerPacket(packet191);
    }

    public void handleHandshake(Packet2Handshake packet2Handshake1) {
        this.registerPacket(packet2Handshake1);
    }

    public void handleMobSpawn(Packet24MobSpawn packet24MobSpawn1) {
        this.registerPacket(packet24MobSpawn1);
    }

    public void handleUpdateTime(Packet4UpdateTime packet4UpdateTime1) {
        this.registerPacket(packet4UpdateTime1);
    }

    public void handleSpawnPosition(Packet6SpawnPosition packet6SpawnPosition1) {
        this.registerPacket(packet6SpawnPosition1);
    }

    public void func_6498_a(Packet28 packet281) {
        this.registerPacket(packet281);
    }

    public void func_21148_a(Packet40 packet401) {
        this.registerPacket(packet401);
    }

    public void func_6497_a(Packet39 packet391) {
        this.registerPacket(packet391);
    }

    public void func_6499_a(Packet7 packet71) {
        this.registerPacket(packet71);
    }

    public void func_9447_a(Packet38 packet381) {
        this.registerPacket(packet381);
    }

    public void handleHealth(Packet8 packet81) {
        this.registerPacket(packet81);
    }

    public void func_9448_a(Packet9 packet91) {
        this.registerPacket(packet91);
    }

    public void func_12245_a(Packet60 packet601) {
        this.registerPacket(packet601);
    }

    public void func_20087_a(Packet100 packet1001) {
        this.registerPacket(packet1001);
    }

    public void func_20092_a(Packet101 packet1011) {
        this.registerPacket(packet1011);
    }

    public void func_20091_a(Packet102 packet1021) {
        this.registerPacket(packet1021);
    }

    public void func_20088_a(Packet103 packet1031) {
        this.registerPacket(packet1031);
    }

    public void func_20094_a(Packet104 packet1041) {
        this.registerPacket(packet1041);
    }

    public void func_20093_a(Packet130 packet1301) {
        this.registerPacket(packet1301);
    }

    public void func_20090_a(Packet105 packet1051) {
        this.registerPacket(packet1051);
    }

    public void handlePlayerInventory(Packet5PlayerInventory packet5PlayerInventory1) {
        this.registerPacket(packet5PlayerInventory1);
    }

    public void func_20089_a(Packet106 packet1061) {
        this.registerPacket(packet1061);
    }

    public void func_21146_a(Packet25 packet251) {
        this.registerPacket(packet251);
    }

    public void func_21145_a(Packet54 packet541) {
        this.registerPacket(packet541);
    }

    public void func_22186_a(Packet17Sleep packet17Sleep1) {
    }

    public void func_22185_a(Packet27 packet271) {
    }

    public void func_25118_a(Packet70 packet701) {
    }
}

