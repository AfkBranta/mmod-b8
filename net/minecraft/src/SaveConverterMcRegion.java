/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import net.minecraft.src.ChunkFilePattern;
import net.minecraft.src.ChunkFolderPattern;
import net.minecraft.src.FileMatcher;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RegionFile;
import net.minecraft.src.RegionFileCache;
import net.minecraft.src.SaveFormatComparator;
import net.minecraft.src.SaveFormatOld;
import net.minecraft.src.SaveOldDir;
import net.minecraft.src.WorldInfo;

public class SaveConverterMcRegion
extends SaveFormatOld {
    public SaveConverterMcRegion(File file1) {
        super(file1);
    }

    @Override
    public String func_22178_a() {
        return "Scaevolus' McRegion";
    }

    @Override
    public List func_22176_b() {
        File[] file2;
        ArrayList<SaveFormatComparator> arrayList1 = new ArrayList<SaveFormatComparator>();
        File[] file3 = file2 = this.field_22180_a.listFiles();
        int i4 = file2.length;
        int i5 = 0;
        while (i5 < i4) {
            String string7;
            WorldInfo worldInfo8;
            File file6 = file3[i5];
            if (file6.isDirectory() && (worldInfo8 = this.func_22173_b(string7 = file6.getName())) != null) {
                boolean z9 = worldInfo8.getSaveVersion() != 19132;
                String string10 = worldInfo8.getWorldName();
                if (string10 == null || MathHelper.stringNullOrLengthZero(string10)) {
                    string10 = string7;
                }
                arrayList1.add(new SaveFormatComparator(string7, string10, worldInfo8.getLastTimePlayed(), worldInfo8.getSizeOnDisk(), z9));
            }
            ++i5;
        }
        return arrayList1;
    }

    @Override
    public void func_22177_c() {
        RegionFileCache.func_22192_a();
    }

    @Override
    public ISaveHandler getSaveLoader(String string1, boolean z2) {
        return new SaveOldDir(this.field_22180_a, string1, z2);
    }

    @Override
    public boolean isOldMapFormat(String string1) {
        WorldInfo worldInfo2 = this.func_22173_b(string1);
        return worldInfo2 != null && worldInfo2.getSaveVersion() == 0;
    }

    @Override
    public boolean convertMapFormat(String string1, IProgressUpdate iProgressUpdate2) {
        iProgressUpdate2.setLoadingProgress(0);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        File file7 = new File(this.field_22180_a, string1);
        File file8 = new File(file7, "DIM-1");
        System.out.println("Scanning folders...");
        this.func_22183_a(file7, arrayList3, arrayList4);
        if (file8.exists()) {
            this.func_22183_a(file8, arrayList5, arrayList6);
        }
        int i9 = arrayList3.size() + arrayList5.size() + arrayList4.size() + arrayList6.size();
        System.out.println("Total conversion count is " + i9);
        this.func_22181_a(file7, arrayList3, 0, i9, iProgressUpdate2);
        this.func_22181_a(file8, arrayList5, arrayList3.size(), i9, iProgressUpdate2);
        WorldInfo worldInfo10 = this.func_22173_b(string1);
        worldInfo10.setSaveVersion(19132);
        ISaveHandler iSaveHandler11 = this.getSaveLoader(string1, false);
        iSaveHandler11.saveWorldInfo(worldInfo10);
        this.func_22182_a(arrayList4, arrayList3.size() + arrayList5.size(), i9, iProgressUpdate2);
        if (file8.exists()) {
            this.func_22182_a(arrayList6, arrayList3.size() + arrayList5.size() + arrayList4.size(), i9, iProgressUpdate2);
        }
        return true;
    }

    private void func_22183_a(File file1, ArrayList arrayList2, ArrayList arrayList3) {
        File[] file6;
        ChunkFolderPattern chunkFolderPattern4 = new ChunkFolderPattern(null);
        ChunkFilePattern chunkFilePattern5 = new ChunkFilePattern(null);
        File[] file7 = file6 = file1.listFiles(chunkFolderPattern4);
        int i8 = file6.length;
        int i9 = 0;
        while (i9 < i8) {
            File[] file11;
            File file10 = file7[i9];
            arrayList3.add(file10);
            File[] file12 = file11 = file10.listFiles(chunkFolderPattern4);
            int i13 = file11.length;
            int i14 = 0;
            while (i14 < i13) {
                File[] file16;
                File file15 = file12[i14];
                File[] file17 = file16 = file15.listFiles(chunkFilePattern5);
                int i18 = file16.length;
                int i19 = 0;
                while (i19 < i18) {
                    File file20 = file17[i19];
                    arrayList2.add(new FileMatcher(file20));
                    ++i19;
                }
                ++i14;
            }
            ++i9;
        }
    }

    private void func_22181_a(File file1, ArrayList arrayList2, int i3, int i4, IProgressUpdate iProgressUpdate5) {
        Collections.sort(arrayList2);
        byte[] b6 = new byte[4096];
        for (FileMatcher fileMatcher8 : arrayList2) {
            int i10;
            int i9 = fileMatcher8.func_22323_b();
            RegionFile regionFile11 = RegionFileCache.func_22193_a(file1, i9, i10 = fileMatcher8.func_22321_c());
            if (!regionFile11.func_22202_c(i9 & 0x1F, i10 & 0x1F)) {
                try {
                    int i17;
                    DataInputStream dataInputStream12 = new DataInputStream(new GZIPInputStream(new FileInputStream(fileMatcher8.func_22324_a())));
                    DataOutputStream dataOutputStream13 = regionFile11.func_22205_b(i9 & 0x1F, i10 & 0x1F);
                    boolean z14 = false;
                    while ((i17 = dataInputStream12.read(b6)) != -1) {
                        dataOutputStream13.write(b6, 0, i17);
                    }
                    dataOutputStream13.close();
                    dataInputStream12.close();
                }
                catch (IOException iOException15) {
                    iOException15.printStackTrace();
                }
            }
            int i16 = (int)Math.round(100.0 * (double)(++i3) / (double)i4);
            iProgressUpdate5.setLoadingProgress(i16);
        }
        RegionFileCache.func_22192_a();
    }

    private void func_22182_a(ArrayList arrayList1, int i2, int i3, IProgressUpdate iProgressUpdate4) {
        for (File file6 : arrayList1) {
            File[] file7 = file6.listFiles();
            SaveConverterMcRegion.func_22179_a(file7);
            file6.delete();
            int i8 = (int)Math.round(100.0 * (double)(++i2) / (double)i3);
            iProgressUpdate4.setLoadingProgress(i8);
        }
    }
}

