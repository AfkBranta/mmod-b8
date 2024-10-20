/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.Block;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityDispenser;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.TileEntityMobSpawner;
import net.minecraft.src.TileEntityNote;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;

public class TileEntity {
    private static Map nameToClassMap = new HashMap();
    private static Map classToNameMap = new HashMap();
    public World worldObj;
    public int xCoord;
    public int yCoord;
    public int zCoord;

    static {
        TileEntity.addMapping(TileEntityFurnace.class, "Furnace");
        TileEntity.addMapping(TileEntityChest.class, "Chest");
        TileEntity.addMapping(TileEntityDispenser.class, "Trap");
        TileEntity.addMapping(TileEntitySign.class, "Sign");
        TileEntity.addMapping(TileEntityMobSpawner.class, "MobSpawner");
        TileEntity.addMapping(TileEntityNote.class, "Music");
    }

    private static void addMapping(Class class0, String string1) {
        if (classToNameMap.containsKey(string1)) {
            throw new IllegalArgumentException("Duplicate id: " + string1);
        }
        nameToClassMap.put(string1, class0);
        classToNameMap.put(class0, string1);
    }

    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        this.xCoord = nBTTagCompound1.getInteger("x");
        this.yCoord = nBTTagCompound1.getInteger("y");
        this.zCoord = nBTTagCompound1.getInteger("z");
    }

    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        String string2 = (String)classToNameMap.get(this.getClass());
        if (string2 == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        nBTTagCompound1.setString("id", string2);
        nBTTagCompound1.setInteger("x", this.xCoord);
        nBTTagCompound1.setInteger("y", this.yCoord);
        nBTTagCompound1.setInteger("z", this.zCoord);
    }

    public void updateEntity() {
    }

    public static TileEntity createAndLoadEntity(NBTTagCompound nBTTagCompound0) {
        TileEntity tileEntity1 = null;
        try {
            Class class2 = (Class)nameToClassMap.get(nBTTagCompound0.getString("id"));
            if (class2 != null) {
                tileEntity1 = (TileEntity)class2.newInstance();
            }
        }
        catch (Exception exception3) {
            exception3.printStackTrace();
        }
        if (tileEntity1 != null) {
            tileEntity1.readFromNBT(nBTTagCompound0);
        } else {
            System.out.println("Skipping TileEntity with id " + nBTTagCompound0.getString("id"));
        }
        return tileEntity1;
    }

    public int getBlockMetadata() {
        return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
    }

    public void onInventoryChanged() {
        if (this.worldObj != null) {
            this.worldObj.func_698_b(this.xCoord, this.yCoord, this.zCoord, this);
        }
    }

    public double getDistanceFrom(double d1, double d3, double d5) {
        double d7 = (double)this.xCoord + 0.5 - d1;
        double d9 = (double)this.yCoord + 0.5 - d3;
        double d11 = (double)this.zCoord + 0.5 - d5;
        return d7 * d7 + d9 * d9 + d11 * d11;
    }

    public Block getBlockType() {
        return Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
    }
}

