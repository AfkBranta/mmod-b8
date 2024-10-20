/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public class TileEntitySign
extends TileEntity {
    public String[] signText = new String[]{"", "", "", ""};
    public int lineBeingEdited = -1;
    private boolean field_25062_c = true;

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        nBTTagCompound1.setString("Text1", this.signText[0]);
        nBTTagCompound1.setString("Text2", this.signText[1]);
        nBTTagCompound1.setString("Text3", this.signText[2]);
        nBTTagCompound1.setString("Text4", this.signText[3]);
    }

    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        this.field_25062_c = false;
        super.readFromNBT(nBTTagCompound1);
        int i2 = 0;
        while (i2 < 4) {
            this.signText[i2] = nBTTagCompound1.getString("Text" + (i2 + 1));
            if (this.signText[i2].length() > 15) {
                this.signText[i2] = this.signText[i2].substring(0, 15);
            }
            ++i2;
        }
    }
}

