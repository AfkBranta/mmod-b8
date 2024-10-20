/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.src.ChunkLoader;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.IChunkLoader;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldProviderHell;

public class SaveHandler
implements ISaveHandler {
    private static final Logger logger = Logger.getLogger("Minecraft");
    private final File saveDirectory;
    private final File playersDirectory;
    private final long now = System.currentTimeMillis();

    public SaveHandler(File file1, String string2, boolean z3) {
        this.saveDirectory = new File(file1, string2);
        this.saveDirectory.mkdirs();
        this.playersDirectory = new File(this.saveDirectory, "players");
        if (z3) {
            this.playersDirectory.mkdirs();
        }
        this.func_22154_d();
    }

    private void func_22154_d() {
        try {
            File file1 = new File(this.saveDirectory, "session.lock");
            try (DataOutputStream dataOutputStream2 = new DataOutputStream(new FileOutputStream(file1));){
                dataOutputStream2.writeLong(this.now);
            }
        }
        catch (IOException iOException7) {
            iOException7.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    protected File getSaveDirectory() {
        return this.saveDirectory;
    }

    @Override
    public void func_22150_b() {
        try {
            File file1 = new File(this.saveDirectory, "session.lock");
            try (DataInputStream dataInputStream2 = new DataInputStream(new FileInputStream(file1));){
                if (dataInputStream2.readLong() != this.now) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
        }
        catch (IOException iOException7) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider worldProvider1) {
        if (worldProvider1 instanceof WorldProviderHell) {
            File file2 = new File(this.saveDirectory, "DIM-1");
            file2.mkdirs();
            return new ChunkLoader(file2, true);
        }
        return new ChunkLoader(this.saveDirectory, true);
    }

    @Override
    public WorldInfo loadWorldInfo() {
        File file1 = new File(this.saveDirectory, "level.dat");
        if (file1.exists()) {
            try {
                NBTTagCompound nBTTagCompound2 = CompressedStreamTools.func_1138_a(new FileInputStream(file1));
                NBTTagCompound nBTTagCompound3 = nBTTagCompound2.getCompoundTag("Data");
                return new WorldInfo(nBTTagCompound3);
            }
            catch (Exception exception4) {
                exception4.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void saveWorldInfoAndPlayer(WorldInfo worldInfo1, List list2) {
        NBTTagCompound nBTTagCompound3 = worldInfo1.getNBTTagCompoundWithPlayer(list2);
        NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
        nBTTagCompound4.setTag("Data", nBTTagCompound3);
        try {
            File file5 = new File(this.saveDirectory, "level.dat_new");
            File file6 = new File(this.saveDirectory, "level.dat_old");
            File file7 = new File(this.saveDirectory, "level.dat");
            CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound4, new FileOutputStream(file5));
            if (file6.exists()) {
                file6.delete();
            }
            file7.renameTo(file6);
            if (file7.exists()) {
                file7.delete();
            }
            file5.renameTo(file7);
            if (file5.exists()) {
                file5.delete();
            }
        }
        catch (Exception exception8) {
            exception8.printStackTrace();
        }
    }

    @Override
    public void saveWorldInfo(WorldInfo worldInfo1) {
        NBTTagCompound nBTTagCompound2 = worldInfo1.getNBTTagCompound();
        NBTTagCompound nBTTagCompound3 = new NBTTagCompound();
        nBTTagCompound3.setTag("Data", nBTTagCompound2);
        try {
            File file4 = new File(this.saveDirectory, "level.dat_new");
            File file5 = new File(this.saveDirectory, "level.dat_old");
            File file6 = new File(this.saveDirectory, "level.dat");
            CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound3, new FileOutputStream(file4));
            if (file5.exists()) {
                file5.delete();
            }
            file6.renameTo(file5);
            if (file6.exists()) {
                file6.delete();
            }
            file4.renameTo(file6);
            if (file4.exists()) {
                file4.delete();
            }
        }
        catch (Exception exception7) {
            exception7.printStackTrace();
        }
    }
}

