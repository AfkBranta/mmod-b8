/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.MCHashTable;

class HashEntry {
    final int hashEntry;
    Object valueEntry;
    HashEntry nextEntry;
    final int slotHash;

    HashEntry(int i1, int i2, Object object3, HashEntry hashEntry4) {
        this.valueEntry = object3;
        this.nextEntry = hashEntry4;
        this.hashEntry = i2;
        this.slotHash = i1;
    }

    public final int getHash() {
        return this.hashEntry;
    }

    public final Object getValue() {
        return this.valueEntry;
    }

    public final boolean equals(Object object1) {
        Object object6;
        Object object5;
        Integer integer4;
        if (!(object1 instanceof HashEntry)) {
            return false;
        }
        HashEntry hashEntry2 = (HashEntry)object1;
        Integer integer3 = this.getHash();
        return (integer3 == (integer4 = Integer.valueOf(hashEntry2.getHash())) || integer3 != null && integer3.equals(integer4)) && ((object5 = this.getValue()) == (object6 = hashEntry2.getValue()) || object5 != null && object5.equals(object6));
    }

    public final int hashCode() {
        return MCHashTable.getHash(this.hashEntry);
    }

    public final String toString() {
        return String.valueOf(this.getHash()) + "=" + this.getValue();
    }
}

