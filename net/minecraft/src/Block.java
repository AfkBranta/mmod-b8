/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Events.events.EventBlock;
import MEDMEX.Modules.Visual.Fullbright;
import MEDMEX.Modules.Visual.Xray;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BlockBed;
import net.minecraft.src.BlockBloodStone;
import net.minecraft.src.BlockBookshelf;
import net.minecraft.src.BlockButton;
import net.minecraft.src.BlockCactus;
import net.minecraft.src.BlockCake;
import net.minecraft.src.BlockChest;
import net.minecraft.src.BlockClay;
import net.minecraft.src.BlockCloth;
import net.minecraft.src.BlockCrops;
import net.minecraft.src.BlockDirt;
import net.minecraft.src.BlockDispenser;
import net.minecraft.src.BlockDoor;
import net.minecraft.src.BlockFence;
import net.minecraft.src.BlockFire;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.BlockFlowing;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.BlockGlass;
import net.minecraft.src.BlockGrass;
import net.minecraft.src.BlockGravel;
import net.minecraft.src.BlockIce;
import net.minecraft.src.BlockJukeBox;
import net.minecraft.src.BlockLadder;
import net.minecraft.src.BlockLeaves;
import net.minecraft.src.BlockLever;
import net.minecraft.src.BlockLightStone;
import net.minecraft.src.BlockLockedChest;
import net.minecraft.src.BlockLog;
import net.minecraft.src.BlockMinecartTrack;
import net.minecraft.src.BlockMobSpawner;
import net.minecraft.src.BlockMushroom;
import net.minecraft.src.BlockNote;
import net.minecraft.src.BlockObsidian;
import net.minecraft.src.BlockOre;
import net.minecraft.src.BlockOreBlock;
import net.minecraft.src.BlockPortal;
import net.minecraft.src.BlockPressurePlate;
import net.minecraft.src.BlockPumpkin;
import net.minecraft.src.BlockRedstoneOre;
import net.minecraft.src.BlockRedstoneRepeater;
import net.minecraft.src.BlockRedstoneTorch;
import net.minecraft.src.BlockRedstoneWire;
import net.minecraft.src.BlockReed;
import net.minecraft.src.BlockSand;
import net.minecraft.src.BlockSandStone;
import net.minecraft.src.BlockSapling;
import net.minecraft.src.BlockSign;
import net.minecraft.src.BlockSlowSand;
import net.minecraft.src.BlockSnow;
import net.minecraft.src.BlockSnowBlock;
import net.minecraft.src.BlockSoil;
import net.minecraft.src.BlockSponge;
import net.minecraft.src.BlockStairs;
import net.minecraft.src.BlockStationary;
import net.minecraft.src.BlockStep;
import net.minecraft.src.BlockStone;
import net.minecraft.src.BlockTNT;
import net.minecraft.src.BlockTorch;
import net.minecraft.src.BlockWorkbench;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMobType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemCloth;
import net.minecraft.src.ItemLog;
import net.minecraft.src.ItemSlab;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatList;
import net.minecraft.src.StepSound;
import net.minecraft.src.StepSoundSand;
import net.minecraft.src.StepSoundStone;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;

public class Block {
    public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0f, 1.0f);
    public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0f, 1.0f);
    public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0f, 1.0f);
    public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0f, 1.0f);
    public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0f, 1.0f);
    public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0f, 1.5f);
    public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0f, 1.0f);
    public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0f, 1.0f);
    public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0f, 1.0f);
    public static final Block[] blocksList = new Block[256];
    public static final boolean[] tickOnLoad = new boolean[256];
    public static final boolean[] opaqueCubeLookup = new boolean[256];
    public static final boolean[] isBlockContainer = new boolean[256];
    public static final int[] lightOpacity = new int[256];
    public static final boolean[] field_340_s = new boolean[256];
    public static final int[] lightValue = new int[256];
    public static final Block stone = new BlockStone(1, 1).setHardness(1.5f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("stone");
    public static final BlockGrass grass = (BlockGrass)new BlockGrass(2).setHardness(0.6f).setStepSound(soundGrassFootstep).setBlockName("grass");
    public static final Block dirt = new BlockDirt(3, 2).setHardness(0.5f).setStepSound(soundGravelFootstep).setBlockName("dirt");
    public static final Block cobblestone = new Block(4, 16, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("stonebrick");
    public static final Block planks = new Block(5, 4, Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(soundWoodFootstep).setBlockName("wood");
    public static final Block sapling = new BlockSapling(6, 15).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("sapling");
    public static final Block bedrock = new Block(7, 17, Material.rock).setHardness(-1.0f).setResistance(6000000.0f).setStepSound(soundStoneFootstep).setBlockName("bedrock");
    public static final Block waterMoving = new BlockFlowing(8, Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water");
    public static final Block waterStill = new BlockStationary(9, Material.water).setHardness(100.0f).setLightOpacity(3).setBlockName("water");
    public static final Block lavaMoving = new BlockFlowing(10, Material.lava).setHardness(0.0f).setLightValue(1.0f).setLightOpacity(255).setBlockName("lava");
    public static final Block lavaStill = new BlockStationary(11, Material.lava).setHardness(100.0f).setLightValue(1.0f).setLightOpacity(255).setBlockName("lava");
    public static final Block sand = new BlockSand(12, 18).setHardness(0.5f).setStepSound(soundSandFootstep).setBlockName("sand");
    public static final Block gravel = new BlockGravel(13, 19).setHardness(0.6f).setStepSound(soundGravelFootstep).setBlockName("gravel");
    public static final Block oreGold = new BlockOre(14, 32).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreGold");
    public static final Block oreIron = new BlockOre(15, 33).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreIron");
    public static final Block oreCoal = new BlockOre(16, 34).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreCoal");
    public static final Block wood = new BlockLog(17).setHardness(2.0f).setStepSound(soundWoodFootstep).setBlockName("log");
    public static final BlockLeaves leaves = (BlockLeaves)new BlockLeaves(18, 52).setHardness(0.2f).setLightOpacity(1).setStepSound(soundGrassFootstep).setBlockName("leaves");
    public static final Block sponge = new BlockSponge(19).setHardness(0.6f).setStepSound(soundGrassFootstep).setBlockName("sponge");
    public static final Block glass = new BlockGlass(20, 49, Material.glass, false).setHardness(0.3f).setStepSound(soundGlassFootstep).setBlockName("glass");
    public static final Block oreLapis = new BlockOre(21, 160).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreLapis");
    public static final Block blockLapis = new Block(22, 144, Material.rock).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("blockLapis");
    public static final Block dispenser = new BlockDispenser(23).setHardness(3.5f).setStepSound(soundStoneFootstep).setBlockName("dispenser");
    public static final Block sandStone = new BlockSandStone(24).setStepSound(soundStoneFootstep).setHardness(0.8f).setBlockName("sandStone");
    public static final Block musicBlock = new BlockNote(25).setHardness(0.8f).setBlockName("musicBlock");
    public static final Block blockBed = new BlockBed(26).setHardness(0.2f).setBlockName("bed");
    public static final Block field_9261_T = null;
    public static final Block field_9260_U = null;
    public static final Block field_9259_V = null;
    public static final Block field_9258_W = null;
    public static final Block field_9257_X = null;
    public static final Block field_9256_Y = null;
    public static final Block field_9255_Z = null;
    public static final Block field_9269_aa = null;
    public static final Block cloth = new BlockCloth().setHardness(0.8f).setStepSound(soundClothFootstep).setBlockName("cloth");
    public static final Block field_9268_ac = null;
    public static final BlockFlower plantYellow = (BlockFlower)new BlockFlower(37, 13).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("flower");
    public static final BlockFlower plantRed = (BlockFlower)new BlockFlower(38, 12).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("rose");
    public static final BlockFlower mushroomBrown = (BlockFlower)new BlockMushroom(39, 29).setHardness(0.0f).setStepSound(soundGrassFootstep).setLightValue(0.125f).setBlockName("mushroom");
    public static final BlockFlower mushroomRed = (BlockFlower)new BlockMushroom(40, 28).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("mushroom");
    public static final Block blockGold = new BlockOreBlock(41, 23).setHardness(3.0f).setResistance(10.0f).setStepSound(soundMetalFootstep).setBlockName("blockGold");
    public static final Block blockSteel = new BlockOreBlock(42, 22).setHardness(5.0f).setResistance(10.0f).setStepSound(soundMetalFootstep).setBlockName("blockIron");
    public static final Block stairDouble = new BlockStep(43, true).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
    public static final Block stairSingle = new BlockStep(44, false).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
    public static final Block brick = new Block(45, 7, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("brick");
    public static final Block tnt = new BlockTNT(46, 8).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("tnt");
    public static final Block bookShelf = new BlockBookshelf(47, 35).setHardness(1.5f).setStepSound(soundWoodFootstep).setBlockName("bookshelf");
    public static final Block cobblestoneMossy = new Block(48, 36, Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("stoneMoss");
    public static final Block obsidian = new BlockObsidian(49, 37).setHardness(10.0f).setResistance(2000.0f).setStepSound(soundStoneFootstep).setBlockName("obsidian");
    public static final Block torchWood = new BlockTorch(50, 80).setHardness(0.0f).setLightValue(0.9375f).setStepSound(soundWoodFootstep).setBlockName("torch");
    public static final BlockFire fire = (BlockFire)new BlockFire(51, 31).setHardness(0.0f).setLightValue(1.0f).setStepSound(soundWoodFootstep).setBlockName("fire");
    public static final Block mobSpawner = new BlockMobSpawner(52, 65).setHardness(5.0f).setStepSound(soundMetalFootstep).setBlockName("mobSpawner");
    public static final Block stairCompactPlanks = new BlockStairs(53, planks).setBlockName("stairsWood");
    public static final Block crate = new BlockChest(54).setHardness(2.5f).setStepSound(soundWoodFootstep).setBlockName("chest");
    public static final Block redstoneWire = new BlockRedstoneWire(55, 164).setHardness(0.0f).setStepSound(soundPowderFootstep).setBlockName("redstoneDust");
    public static final Block oreDiamond = new BlockOre(56, 50).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreDiamond");
    public static final Block blockDiamond = new BlockOreBlock(57, 24).setHardness(5.0f).setResistance(10.0f).setStepSound(soundMetalFootstep).setBlockName("blockDiamond");
    public static final Block workbench = new BlockWorkbench(58).setHardness(2.5f).setStepSound(soundWoodFootstep).setBlockName("workbench");
    public static final Block crops = new BlockCrops(59, 88).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("crops");
    public static final Block tilledField = new BlockSoil(60).setHardness(0.6f).setStepSound(soundGravelFootstep).setBlockName("farmland");
    public static final Block stoneOvenIdle = new BlockFurnace(61, false).setHardness(3.5f).setStepSound(soundStoneFootstep).setBlockName("furnace");
    public static final Block stoneOvenActive = new BlockFurnace(62, true).setHardness(3.5f).setStepSound(soundStoneFootstep).setLightValue(0.875f).setBlockName("furnace");
    public static final Block signPost = new BlockSign(63, TileEntitySign.class, true).setHardness(1.0f).setStepSound(soundWoodFootstep).setBlockName("sign");
    public static final Block doorWood = new BlockDoor(64, Material.wood).setHardness(3.0f).setStepSound(soundWoodFootstep).setBlockName("doorWood");
    public static final Block ladder = new BlockLadder(65, 83).setHardness(0.4f).setStepSound(soundWoodFootstep).setBlockName("ladder");
    public static final Block minecartTrack = new BlockMinecartTrack(66, 128).setHardness(0.7f).setStepSound(soundMetalFootstep).setBlockName("rail");
    public static final Block stairCompactCobblestone = new BlockStairs(67, cobblestone).setBlockName("stairsStone");
    public static final Block signWall = new BlockSign(68, TileEntitySign.class, false).setHardness(1.0f).setStepSound(soundWoodFootstep).setBlockName("sign");
    public static final Block lever = new BlockLever(69, 96).setHardness(0.5f).setStepSound(soundWoodFootstep).setBlockName("lever");
    public static final Block pressurePlateStone = new BlockPressurePlate(70, Block.stone.blockIndexInTexture, EnumMobType.mobs).setHardness(0.5f).setStepSound(soundStoneFootstep).setBlockName("pressurePlate");
    public static final Block doorSteel = new BlockDoor(71, Material.iron).setHardness(5.0f).setStepSound(soundMetalFootstep).setBlockName("doorIron");
    public static final Block pressurePlatePlanks = new BlockPressurePlate(72, Block.planks.blockIndexInTexture, EnumMobType.everything).setHardness(0.5f).setStepSound(soundWoodFootstep).setBlockName("pressurePlate");
    public static final Block oreRedstone = new BlockRedstoneOre(73, 51, false).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreRedstone");
    public static final Block oreRedstoneGlowing = new BlockRedstoneOre(74, 51, true).setLightValue(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(soundStoneFootstep).setBlockName("oreRedstone");
    public static final Block torchRedstoneIdle = new BlockRedstoneTorch(75, 115, false).setHardness(0.0f).setStepSound(soundWoodFootstep).setBlockName("notGate");
    public static final Block torchRedstoneActive = new BlockRedstoneTorch(76, 99, true).setHardness(0.0f).setLightValue(0.5f).setStepSound(soundWoodFootstep).setBlockName("notGate");
    public static final Block button = new BlockButton(77, Block.stone.blockIndexInTexture).setHardness(0.5f).setStepSound(soundStoneFootstep).setBlockName("button");
    public static final Block snow = new BlockSnow(78, 66).setHardness(0.1f).setStepSound(soundClothFootstep).setBlockName("snow");
    public static final Block ice = new BlockIce(79, 67).setHardness(0.5f).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("ice");
    public static final Block blockSnow = new BlockSnowBlock(80, 66).setHardness(0.2f).setStepSound(soundClothFootstep).setBlockName("snow");
    public static final Block cactus = new BlockCactus(81, 70).setHardness(0.4f).setStepSound(soundClothFootstep).setBlockName("cactus");
    public static final Block blockClay = new BlockClay(82, 72).setHardness(0.6f).setStepSound(soundGravelFootstep).setBlockName("clay");
    public static final Block reed = new BlockReed(83, 73).setHardness(0.0f).setStepSound(soundGrassFootstep).setBlockName("reeds");
    public static final Block jukebox = new BlockJukeBox(84, 74).setHardness(2.0f).setResistance(10.0f).setStepSound(soundStoneFootstep).setBlockName("jukebox");
    public static final Block fence = new BlockFence(85, 4).setHardness(2.0f).setResistance(5.0f).setStepSound(soundWoodFootstep).setBlockName("fence");
    public static final Block pumpkin = new BlockPumpkin(86, 102, false).setHardness(1.0f).setStepSound(soundWoodFootstep).setBlockName("pumpkin");
    public static final Block bloodStone = new BlockBloodStone(87, 103).setHardness(0.4f).setStepSound(soundStoneFootstep).setBlockName("hellrock");
    public static final Block slowSand = new BlockSlowSand(88, 104).setHardness(0.5f).setStepSound(soundSandFootstep).setBlockName("hellsand");
    public static final Block lightStone = new BlockLightStone(89, 105, Material.glass).setHardness(0.3f).setStepSound(soundGlassFootstep).setLightValue(1.0f).setBlockName("lightgem");
    public static final BlockPortal portal = (BlockPortal)new BlockPortal(90, 14).setHardness(-1.0f).setStepSound(soundGlassFootstep).setLightValue(0.75f).setBlockName("portal");
    public static final Block pumpkinLantern = new BlockPumpkin(91, 102, true).setHardness(1.0f).setStepSound(soundWoodFootstep).setLightValue(1.0f).setBlockName("litpumpkin");
    public static final Block cake = new BlockCake(92, 121).setHardness(0.5f).setStepSound(soundClothFootstep).setBlockName("cake");
    public static final Block redstoneRepeaterIdle = new BlockRedstoneRepeater(93, false).setHardness(0.0f).setStepSound(soundWoodFootstep).setBlockName("diode");
    public static final Block redstoneRepeaterActive = new BlockRedstoneRepeater(94, true).setHardness(0.0f).setLightValue(0.625f).setStepSound(soundWoodFootstep).setBlockName("diode");
    public static final Block lockedChest = new BlockLockedChest(95).setHardness(0.0f).setLightValue(1.0f).setStepSound(soundWoodFootstep).setBlockName("lockedchest").setTickOnLoad(true);
    public int blockIndexInTexture;
    public final int blockID;
    protected float blockHardness;
    protected float blockResistance;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    public StepSound stepSound = soundPowderFootstep;
    public float blockParticleGravity = 1.0f;
    public final Material blockMaterial;
    public float slipperiness = 0.6f;
    private String blockName;

    static {
        Item.itemsList[Block.cloth.blockID] = new ItemCloth(Block.cloth.blockID - 256).setItemName("cloth");
        Item.itemsList[Block.wood.blockID] = new ItemLog(Block.wood.blockID - 256).setItemName("log");
        Item.itemsList[Block.stairSingle.blockID] = new ItemSlab(Block.stairSingle.blockID - 256).setItemName("stoneSlab");
        int i0 = 0;
        while (i0 < 256) {
            if (blocksList[i0] != null && Item.itemsList[i0] == null) {
                Item.itemsList[i0] = new ItemBlock(i0 - 256);
            }
            ++i0;
        }
        Block.field_340_s[0] = true;
        StatList.func_25154_a();
    }

    protected Block(int i1, Material material2) {
        if (blocksList[i1] != null) {
            throw new IllegalArgumentException("Slot " + i1 + " is already occupied by " + blocksList[i1] + " when adding " + this);
        }
        this.blockMaterial = material2;
        Block.blocksList[i1] = this;
        this.blockID = i1;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        Block.opaqueCubeLookup[i1] = this.isOpaqueCube();
        Block.lightOpacity[i1] = this.isOpaqueCube() ? 255 : 0;
        Block.field_340_s[i1] = !material2.getCanBlockGrass();
        Block.isBlockContainer[i1] = false;
    }

    protected Block(int i1, int i2, Material material3) {
        this(i1, material3);
        this.blockIndexInTexture = i2;
    }

    protected Block setStepSound(StepSound stepSound1) {
        this.stepSound = stepSound1;
        return this;
    }

    protected Block setLightOpacity(int i1) {
        Block.lightOpacity[this.blockID] = i1;
        return this;
    }

    protected Block setLightValue(float f1) {
        Block.lightValue[this.blockID] = (int)(15.0f * f1);
        return this;
    }

    protected Block setResistance(float f1) {
        this.blockResistance = f1 * 3.0f;
        return this;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public int getRenderType() {
        return 0;
    }

    protected Block setHardness(float f1) {
        this.blockHardness = f1;
        if (this.blockResistance < f1 * 5.0f) {
            this.blockResistance = f1 * 5.0f;
        }
        return this;
    }

    protected Block setTickOnLoad(boolean z1) {
        Block.tickOnLoad[this.blockID] = z1;
        return this;
    }

    public void setBlockBounds(float f1, float f2, float f3, float f4, float f5, float f6) {
        this.minX = f1;
        this.minY = f2;
        this.minZ = f3;
        this.maxX = f4;
        this.maxY = f5;
        this.maxZ = f6;
    }

    public float getBlockBrightness(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        if (Fullbright.instance.isEnabled) {
            return 1.0f;
        }
        if (Xray.instance.isEnabled) {
            return 240.0f;
        }
        return iBlockAccess1.getLightBrightness(i2, i3, i4);
    }

    public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        if (Xray.instance.isEnabled && Xray.instance.xrayblocks.contains(this.blockID)) {
            this.setLightValue(15.0f);
            return true;
        }
        return i5 == 0 && this.minY > 0.0 ? true : (i5 == 1 && this.maxY < 1.0 ? true : (i5 == 2 && this.minZ > 0.0 ? true : (i5 == 3 && this.maxZ < 1.0 ? true : (i5 == 4 && this.minX > 0.0 ? true : (i5 == 5 && this.maxX < 1.0 ? true : !iBlockAccess1.isBlockOpaqueCube(i2, i3, i4))))));
    }

    public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        EventBlock event = new EventBlock(new MEDMEX.Utils.Vec3D(i2, i3, i4), this.blockID);
        Client.onEvent(event);
        return this.getBlockTextureFromSideAndMetadata(i5, iBlockAccess1.getBlockMetadata(i2, i3, i4));
    }

    public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
        return this.getBlockTextureFromSide(i1);
    }

    public int getBlockTextureFromSide(int i1) {
        return this.blockIndexInTexture;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return AxisAlignedBB.getBoundingBoxFromPool((double)i2 + this.minX, (double)i3 + this.minY, (double)i4 + this.minZ, (double)i2 + this.maxX, (double)i3 + this.maxY, (double)i4 + this.maxZ);
    }

    public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
        AxisAlignedBB axisAlignedBB7 = this.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
        if (axisAlignedBB7 != null && axisAlignedBB5.intersectsWith(axisAlignedBB7)) {
            arrayList6.add(axisAlignedBB7);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
        return AxisAlignedBB.getBoundingBoxFromPool((double)i2 + this.minX, (double)i3 + this.minY, (double)i4 + this.minZ, (double)i2 + this.maxX, (double)i3 + this.maxY, (double)i4 + this.maxZ);
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean canCollideCheck(int i1, boolean z2) {
        return this.isCollidable();
    }

    public boolean isCollidable() {
        return true;
    }

    public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
    }

    public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
    }

    public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
    }

    public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
    }

    public int tickRate() {
        return 10;
    }

    public void onBlockAdded(World world1, int i2, int i3, int i4) {
    }

    public void onBlockRemoval(World world1, int i2, int i3, int i4) {
    }

    public int quantityDropped(Random random1) {
        return 1;
    }

    public int idDropped(int i1, Random random2) {
        return this.blockID;
    }

    public float blockStrength(EntityPlayer entityPlayer1) {
        return this.blockHardness < 0.0f ? 0.0f : (!entityPlayer1.canHarvestBlock(this) ? 1.0f / this.blockHardness / 100.0f : entityPlayer1.getCurrentPlayerStrVsBlock(this) / this.blockHardness / 30.0f);
    }

    public void dropBlockAsItem(World world1, int i2, int i3, int i4, int i5) {
        this.dropBlockAsItemWithChance(world1, i2, i3, i4, i5, 1.0f);
    }

    public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
        if (!world1.multiplayerWorld) {
            int i7 = this.quantityDropped(world1.rand);
            int i8 = 0;
            while (i8 < i7) {
                int i9;
                if (world1.rand.nextFloat() <= f6 && (i9 = this.idDropped(i5, world1.rand)) > 0) {
                    float f10 = 0.7f;
                    double d11 = (double)(world1.rand.nextFloat() * f10) + (double)(1.0f - f10) * 0.5;
                    double d13 = (double)(world1.rand.nextFloat() * f10) + (double)(1.0f - f10) * 0.5;
                    double d15 = (double)(world1.rand.nextFloat() * f10) + (double)(1.0f - f10) * 0.5;
                    EntityItem entityItem17 = new EntityItem(world1, (double)i2 + d11, (double)i3 + d13, (double)i4 + d15, new ItemStack(i9, 1, this.damageDropped(i5)));
                    entityItem17.delayBeforeCanPickup = 10;
                    world1.entityJoinedWorld(entityItem17);
                }
                ++i8;
            }
        }
    }

    protected int damageDropped(int i1) {
        return 0;
    }

    public float getExplosionResistance(Entity entity1) {
        return this.blockResistance / 5.0f;
    }

    public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
        this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
        vec3D5 = vec3D5.addVector(-i2, -i3, -i4);
        vec3D6 = vec3D6.addVector(-i2, -i3, -i4);
        Vec3D vec3D7 = vec3D5.getIntermediateWithXValue(vec3D6, this.minX);
        Vec3D vec3D8 = vec3D5.getIntermediateWithXValue(vec3D6, this.maxX);
        Vec3D vec3D9 = vec3D5.getIntermediateWithYValue(vec3D6, this.minY);
        Vec3D vec3D10 = vec3D5.getIntermediateWithYValue(vec3D6, this.maxY);
        Vec3D vec3D11 = vec3D5.getIntermediateWithZValue(vec3D6, this.minZ);
        Vec3D vec3D12 = vec3D5.getIntermediateWithZValue(vec3D6, this.maxZ);
        if (!this.isVecInsideYZBounds(vec3D7)) {
            vec3D7 = null;
        }
        if (!this.isVecInsideYZBounds(vec3D8)) {
            vec3D8 = null;
        }
        if (!this.isVecInsideXZBounds(vec3D9)) {
            vec3D9 = null;
        }
        if (!this.isVecInsideXZBounds(vec3D10)) {
            vec3D10 = null;
        }
        if (!this.isVecInsideXYBounds(vec3D11)) {
            vec3D11 = null;
        }
        if (!this.isVecInsideXYBounds(vec3D12)) {
            vec3D12 = null;
        }
        Vec3D vec3D13 = null;
        if (vec3D7 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D7) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D7;
        }
        if (vec3D8 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D8) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D8;
        }
        if (vec3D9 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D9) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D9;
        }
        if (vec3D10 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D10) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D10;
        }
        if (vec3D11 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D11) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D11;
        }
        if (vec3D12 != null && (vec3D13 == null || vec3D5.distanceTo(vec3D12) < vec3D5.distanceTo(vec3D13))) {
            vec3D13 = vec3D12;
        }
        if (vec3D13 == null) {
            return null;
        }
        int b14 = -1;
        if (vec3D13 == vec3D7) {
            b14 = 4;
        }
        if (vec3D13 == vec3D8) {
            b14 = 5;
        }
        if (vec3D13 == vec3D9) {
            b14 = 0;
        }
        if (vec3D13 == vec3D10) {
            b14 = 1;
        }
        if (vec3D13 == vec3D11) {
            b14 = 2;
        }
        if (vec3D13 == vec3D12) {
            b14 = 3;
        }
        return new MovingObjectPosition(i2, i3, i4, b14, vec3D13.addVector(i2, i3, i4));
    }

    private boolean isVecInsideYZBounds(Vec3D vec3D1) {
        return vec3D1 == null ? false : vec3D1.yCoord >= this.minY && vec3D1.yCoord <= this.maxY && vec3D1.zCoord >= this.minZ && vec3D1.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXZBounds(Vec3D vec3D1) {
        return vec3D1 == null ? false : vec3D1.xCoord >= this.minX && vec3D1.xCoord <= this.maxX && vec3D1.zCoord >= this.minZ && vec3D1.zCoord <= this.maxZ;
    }

    private boolean isVecInsideXYBounds(Vec3D vec3D1) {
        return vec3D1 == null ? false : vec3D1.xCoord >= this.minX && vec3D1.xCoord <= this.maxX && vec3D1.yCoord >= this.minY && vec3D1.yCoord <= this.maxY;
    }

    public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
    }

    public int getRenderBlockPass() {
        return !Xray.instance.isEnabled || Xray.instance.xrayblocks.contains(this.blockID) ? 0 : 1;
    }

    public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
        int i5 = world1.getBlockId(i2, i3, i4);
        return i5 == 0 || Block.blocksList[i5].blockMaterial.getIsLiquid();
    }

    public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
        return false;
    }

    public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
    }

    public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
    }

    public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
    }

    public void velocityToAddToEntity(World world1, int i2, int i3, int i4, Entity entity5, Vec3D vec3D6) {
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
    }

    public int colorMultiplier(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
        return 0xFFFFFF;
    }

    public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
        return false;
    }

    public boolean canProvidePower() {
        return false;
    }

    public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
    }

    public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
        return false;
    }

    public void setBlockBoundsForItemRender() {
    }

    public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
        entityPlayer2.addStat(StatList.field_25159_y[this.blockID], 1);
        this.dropBlockAsItem(world1, i3, i4, i5, i6);
    }

    public boolean canBlockStay(World world1, int i2, int i3, int i4) {
        return true;
    }

    public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
    }

    public Block setBlockName(String string1) {
        this.blockName = "tile." + string1;
        return this;
    }

    public String func_25016_i() {
        return StatCollector.func_25200_a(String.valueOf(this.getBlockName()) + ".name");
    }

    public String getBlockName() {
        return this.blockName;
    }

    public void playBlock(World world1, int i2, int i3, int i4, int i5, int i6) {
    }
}

