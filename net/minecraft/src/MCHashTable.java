/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.HashEntry;

public class MCHashTable {
    private transient HashEntry[] slots = new HashEntry[16];
    private transient int count;
    private int threshold = 12;
    private final float growFactor = 0.75f;
    private volatile transient int versionStamp;

    private static int computeHash(int i0) {
        i0 ^= i0 >>> 20 ^ i0 >>> 12;
        return i0 ^ i0 >>> 7 ^ i0 >>> 4;
    }

    private static int getSlotIndex(int i0, int i1) {
        return i0 & i1 - 1;
    }

    public Object lookup(int i1) {
        int i2 = MCHashTable.computeHash(i1);
        HashEntry hashEntry3 = this.slots[MCHashTable.getSlotIndex(i2, this.slots.length)];
        while (hashEntry3 != null) {
            if (hashEntry3.hashEntry == i1) {
                return hashEntry3.valueEntry;
            }
            hashEntry3 = hashEntry3.nextEntry;
        }
        return null;
    }

    public void addKey(int i1, Object object2) {
        int i3 = MCHashTable.computeHash(i1);
        int i4 = MCHashTable.getSlotIndex(i3, this.slots.length);
        HashEntry hashEntry5 = this.slots[i4];
        while (hashEntry5 != null) {
            if (hashEntry5.hashEntry == i1) {
                hashEntry5.valueEntry = object2;
            }
            hashEntry5 = hashEntry5.nextEntry;
        }
        ++this.versionStamp;
        this.insert(i3, i1, object2, i4);
    }

    private void grow(int i1) {
        HashEntry[] hashEntry2 = this.slots;
        int i3 = hashEntry2.length;
        if (i3 == 0x40000000) {
            this.threshold = Integer.MAX_VALUE;
        } else {
            HashEntry[] hashEntry4 = new HashEntry[i1];
            this.copyTo(hashEntry4);
            this.slots = hashEntry4;
            this.threshold = (int)((float)i1 * 0.75f);
        }
    }

    private void copyTo(HashEntry[] hashEntry1) {
        HashEntry[] hashEntry2 = this.slots;
        int i3 = hashEntry1.length;
        int i4 = 0;
        while (i4 < hashEntry2.length) {
            HashEntry hashEntry5 = hashEntry2[i4];
            if (hashEntry5 != null) {
                HashEntry hashEntry6;
                hashEntry2[i4] = null;
                do {
                    hashEntry6 = hashEntry5.nextEntry;
                    int i7 = MCHashTable.getSlotIndex(hashEntry5.slotHash, i3);
                    hashEntry5.nextEntry = hashEntry1[i7];
                    hashEntry1[i7] = hashEntry5;
                    hashEntry5 = hashEntry6;
                } while (hashEntry6 != null);
            }
            ++i4;
        }
    }

    public Object removeObject(int i1) {
        HashEntry hashEntry2 = this.removeEntry(i1);
        return hashEntry2 == null ? null : hashEntry2.valueEntry;
    }

    final HashEntry removeEntry(int i1) {
        HashEntry hashEntry4;
        int i2 = MCHashTable.computeHash(i1);
        int i3 = MCHashTable.getSlotIndex(i2, this.slots.length);
        HashEntry hashEntry5 = hashEntry4 = this.slots[i3];
        while (hashEntry5 != null) {
            HashEntry hashEntry6 = hashEntry5.nextEntry;
            if (hashEntry5.hashEntry == i1) {
                ++this.versionStamp;
                --this.count;
                if (hashEntry4 == hashEntry5) {
                    this.slots[i3] = hashEntry6;
                } else {
                    hashEntry4.nextEntry = hashEntry6;
                }
                return hashEntry5;
            }
            hashEntry4 = hashEntry5;
            hashEntry5 = hashEntry6;
        }
        return hashEntry5;
    }

    public void clearMap() {
        ++this.versionStamp;
        HashEntry[] hashEntry1 = this.slots;
        int i2 = 0;
        while (i2 < hashEntry1.length) {
            hashEntry1[i2] = null;
            ++i2;
        }
        this.count = 0;
    }

    private void insert(int i1, int i2, Object object3, int i4) {
        HashEntry hashEntry5 = this.slots[i4];
        this.slots[i4] = new HashEntry(i1, i2, object3, hashEntry5);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }

    static int getHash(int i0) {
        return MCHashTable.computeHash(i0);
    }
}

