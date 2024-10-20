/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;

public class CompressedStreamTools {
    public static NBTTagCompound func_1138_a(InputStream inputStream0) throws IOException {
        NBTTagCompound nBTTagCompound2;
        try (DataInputStream dataInputStream1 = new DataInputStream(new GZIPInputStream(inputStream0));){
            nBTTagCompound2 = CompressedStreamTools.func_1141_a(dataInputStream1);
        }
        return nBTTagCompound2;
    }

    public static void writeGzippedCompoundToOutputStream(NBTTagCompound nBTTagCompound0, OutputStream outputStream1) throws IOException {
        try (DataOutputStream dataOutputStream2 = new DataOutputStream(new GZIPOutputStream(outputStream1));){
            CompressedStreamTools.func_1139_a(nBTTagCompound0, dataOutputStream2);
        }
    }

    public static NBTTagCompound func_1141_a(DataInput dataInput0) throws IOException {
        NBTBase nBTBase1 = NBTBase.readTag(dataInput0);
        if (nBTBase1 instanceof NBTTagCompound) {
            return (NBTTagCompound)nBTBase1;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void func_1139_a(NBTTagCompound nBTTagCompound0, DataOutput dataOutput1) throws IOException {
        NBTBase.writeTag(nBTTagCompound0, dataOutput1);
    }
}

