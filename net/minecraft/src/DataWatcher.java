/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WatchableObject;

public class DataWatcher {
    private static final HashMap dataTypes = new HashMap();
    private final Map watchedObjects = new HashMap();
    private boolean objectChanged;

    static {
        dataTypes.put(Byte.class, 0);
        dataTypes.put(Short.class, 1);
        dataTypes.put(Integer.class, 2);
        dataTypes.put(Float.class, 3);
        dataTypes.put(String.class, 4);
        dataTypes.put(ItemStack.class, 5);
        dataTypes.put(ChunkCoordinates.class, 6);
    }

    public void addObject(int i1, Object object2) {
        Integer integer3 = (Integer)dataTypes.get(object2.getClass());
        if (integer3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + object2.getClass());
        }
        if (i1 > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + i1 + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(i1)) {
            throw new IllegalArgumentException("Duplicate id value for " + i1 + "!");
        }
        WatchableObject watchableObject4 = new WatchableObject(integer3, i1, object2);
        this.watchedObjects.put(i1, watchableObject4);
    }

    public byte getWatchableObjectByte(int i1) {
        return (Byte)((WatchableObject)this.watchedObjects.get(i1)).getObject();
    }

    public int func_25115_b(int i1) {
        return (Integer)((WatchableObject)this.watchedObjects.get(i1)).getObject();
    }

    public String func_25116_c(int i1) {
        return (String)((WatchableObject)this.watchedObjects.get(i1)).getObject();
    }

    public void updateObject(int i1, Object object2) {
        WatchableObject watchableObject3 = (WatchableObject)this.watchedObjects.get(i1);
        if (!object2.equals(watchableObject3.getObject())) {
            watchableObject3.setObject(object2);
            watchableObject3.setWatching(true);
            this.objectChanged = true;
        }
    }

    public static void writeObjectsInListToStream(List list0, DataOutputStream dataOutputStream1) throws IOException {
        if (list0 != null) {
            for (WatchableObject watchableObject3 : list0) {
                DataWatcher.writeWatchableObject(dataOutputStream1, watchableObject3);
            }
        }
        dataOutputStream1.writeByte(127);
    }

    public void writeWatchableObjects(DataOutputStream dataOutputStream1) throws IOException {
        for (WatchableObject watchableObject3 : this.watchedObjects.values()) {
            DataWatcher.writeWatchableObject(dataOutputStream1, watchableObject3);
        }
        dataOutputStream1.writeByte(127);
    }

    private static void writeWatchableObject(DataOutputStream dataOutputStream0, WatchableObject watchableObject1) throws IOException {
        int i2 = (watchableObject1.getObjectType() << 5 | watchableObject1.getDataValueId() & 0x1F) & 0xFF;
        dataOutputStream0.writeByte(i2);
        switch (watchableObject1.getObjectType()) {
            case 0: {
                dataOutputStream0.writeByte(((Byte)watchableObject1.getObject()).byteValue());
                break;
            }
            case 1: {
                dataOutputStream0.writeShort(((Short)watchableObject1.getObject()).shortValue());
                break;
            }
            case 2: {
                dataOutputStream0.writeInt((Integer)watchableObject1.getObject());
                break;
            }
            case 3: {
                dataOutputStream0.writeFloat(((Float)watchableObject1.getObject()).floatValue());
                break;
            }
            case 4: {
                dataOutputStream0.writeUTF((String)watchableObject1.getObject());
                break;
            }
            case 5: {
                ItemStack itemStack3 = (ItemStack)watchableObject1.getObject();
                dataOutputStream0.writeShort(itemStack3.getItem().shiftedIndex);
                dataOutputStream0.writeByte(itemStack3.stackSize);
                dataOutputStream0.writeShort(itemStack3.getItemDamage());
            }
            case 6: {
                ChunkCoordinates chunkCoordinates4 = (ChunkCoordinates)watchableObject1.getObject();
                dataOutputStream0.writeInt(chunkCoordinates4.x);
                dataOutputStream0.writeInt(chunkCoordinates4.y);
                dataOutputStream0.writeInt(chunkCoordinates4.z);
            }
        }
    }

    public static List readWatchableObjects(DataInputStream dataInputStream0) throws IOException {
        ArrayList<WatchableObject> arrayList1 = null;
        byte b2 = dataInputStream0.readByte();
        while (b2 != 127) {
            if (arrayList1 == null) {
                arrayList1 = new ArrayList<WatchableObject>();
            }
            int i3 = (b2 & 0xE0) >> 5;
            int i4 = b2 & 0x1F;
            WatchableObject watchableObject5 = null;
            switch (i3) {
                case 0: {
                    watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readByte());
                    break;
                }
                case 1: {
                    watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readShort());
                    break;
                }
                case 2: {
                    watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readInt());
                    break;
                }
                case 3: {
                    watchableObject5 = new WatchableObject(i3, i4, Float.valueOf(dataInputStream0.readFloat()));
                    break;
                }
                case 4: {
                    watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readUTF());
                    break;
                }
                case 5: {
                    short s6 = dataInputStream0.readShort();
                    byte b7 = dataInputStream0.readByte();
                    short s8 = dataInputStream0.readShort();
                    new WatchableObject(i3, i4, new ItemStack(s6, (int)b7, (int)s8));
                }
                case 6: {
                    int i9 = dataInputStream0.readInt();
                    int i10 = dataInputStream0.readInt();
                    int i11 = dataInputStream0.readInt();
                    watchableObject5 = new WatchableObject(i3, i4, new ChunkCoordinates(i9, i10, i11));
                }
            }
            arrayList1.add(watchableObject5);
            b2 = dataInputStream0.readByte();
        }
        return arrayList1;
    }

    public void updateWatchedObjectsFromList(List list1) {
        for (WatchableObject watchableObject3 : list1) {
            WatchableObject watchableObject4 = (WatchableObject)this.watchedObjects.get(watchableObject3.getDataValueId());
            if (watchableObject4 == null) continue;
            watchableObject4.setObject(watchableObject3.getObject());
        }
    }
}

