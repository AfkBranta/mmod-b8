/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.src.BlockMinecartTrack;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.World;

class MinecartTrackLogic {
    private World worldObj;
    private int trackX;
    private int trackY;
    private int trackZ;
    private int trackMetadata;
    private List connectedTracks;
    final BlockMinecartTrack minecartTrack;

    public MinecartTrackLogic(BlockMinecartTrack blockMinecartTrack1, World world2, int i3, int i4, int i5) {
        this.minecartTrack = blockMinecartTrack1;
        this.connectedTracks = new ArrayList();
        this.worldObj = world2;
        this.trackX = i3;
        this.trackY = i4;
        this.trackZ = i5;
        this.trackMetadata = world2.getBlockMetadata(i3, i4, i5);
        this.calculateConnectedTracks();
    }

    private void calculateConnectedTracks() {
        this.connectedTracks.clear();
        if (this.trackMetadata == 0) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 1) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
        } else if (this.trackMetadata == 2) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY + 1, this.trackZ));
        } else if (this.trackMetadata == 3) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY + 1, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
        } else if (this.trackMetadata == 4) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 5) {
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ + 1));
        } else if (this.trackMetadata == 6) {
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 7) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
        } else if (this.trackMetadata == 8) {
            this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
        } else if (this.trackMetadata == 9) {
            this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
            this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
        }
    }

    private void func_785_b() {
        int i1 = 0;
        while (i1 < this.connectedTracks.size()) {
            MinecartTrackLogic minecartTrackLogic2 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(i1));
            if (minecartTrackLogic2 != null && minecartTrackLogic2.isConnectedTo(this)) {
                this.connectedTracks.set(i1, new ChunkPosition(minecartTrackLogic2.trackX, minecartTrackLogic2.trackY, minecartTrackLogic2.trackZ));
            } else {
                this.connectedTracks.remove(i1--);
            }
            ++i1;
        }
    }

    private boolean isMinecartTrack(int i1, int i2, int i3) {
        return this.worldObj.getBlockId(i1, i2, i3) == this.minecartTrack.blockID ? true : (this.worldObj.getBlockId(i1, i2 + 1, i3) == this.minecartTrack.blockID ? true : this.worldObj.getBlockId(i1, i2 - 1, i3) == this.minecartTrack.blockID);
    }

    private MinecartTrackLogic getMinecartTrackLogic(ChunkPosition chunkPosition1) {
        return this.worldObj.getBlockId(chunkPosition1.x, chunkPosition1.y, chunkPosition1.z) == this.minecartTrack.blockID ? new MinecartTrackLogic(this.minecartTrack, this.worldObj, chunkPosition1.x, chunkPosition1.y, chunkPosition1.z) : (this.worldObj.getBlockId(chunkPosition1.x, chunkPosition1.y + 1, chunkPosition1.z) == this.minecartTrack.blockID ? new MinecartTrackLogic(this.minecartTrack, this.worldObj, chunkPosition1.x, chunkPosition1.y + 1, chunkPosition1.z) : (this.worldObj.getBlockId(chunkPosition1.x, chunkPosition1.y - 1, chunkPosition1.z) == this.minecartTrack.blockID ? new MinecartTrackLogic(this.minecartTrack, this.worldObj, chunkPosition1.x, chunkPosition1.y - 1, chunkPosition1.z) : null));
    }

    private boolean isConnectedTo(MinecartTrackLogic minecartTrackLogic1) {
        int i2 = 0;
        while (i2 < this.connectedTracks.size()) {
            ChunkPosition chunkPosition3 = (ChunkPosition)this.connectedTracks.get(i2);
            if (chunkPosition3.x == minecartTrackLogic1.trackX && chunkPosition3.z == minecartTrackLogic1.trackZ) {
                return true;
            }
            ++i2;
        }
        return false;
    }

    private boolean func_794_b(int i1, int i2, int i3) {
        int i4 = 0;
        while (i4 < this.connectedTracks.size()) {
            ChunkPosition chunkPosition5 = (ChunkPosition)this.connectedTracks.get(i4);
            if (chunkPosition5.x == i1 && chunkPosition5.z == i3) {
                return true;
            }
            ++i4;
        }
        return false;
    }

    private int getAdjacentTracks() {
        int i1 = 0;
        if (this.isMinecartTrack(this.trackX, this.trackY, this.trackZ - 1)) {
            ++i1;
        }
        if (this.isMinecartTrack(this.trackX, this.trackY, this.trackZ + 1)) {
            ++i1;
        }
        if (this.isMinecartTrack(this.trackX - 1, this.trackY, this.trackZ)) {
            ++i1;
        }
        if (this.isMinecartTrack(this.trackX + 1, this.trackY, this.trackZ)) {
            ++i1;
        }
        return i1;
    }

    private boolean handleKeyPress(MinecartTrackLogic minecartTrackLogic1) {
        if (this.isConnectedTo(minecartTrackLogic1)) {
            return true;
        }
        if (this.connectedTracks.size() == 2) {
            return false;
        }
        if (this.connectedTracks.size() == 0) {
            return true;
        }
        ChunkPosition chunkPosition2 = (ChunkPosition)this.connectedTracks.get(0);
        return minecartTrackLogic1.trackY == this.trackY && chunkPosition2.y == this.trackY ? true : true;
    }

    private void func_788_d(MinecartTrackLogic minecartTrackLogic1) {
        this.connectedTracks.add(new ChunkPosition(minecartTrackLogic1.trackX, minecartTrackLogic1.trackY, minecartTrackLogic1.trackZ));
        boolean z2 = this.func_794_b(this.trackX, this.trackY, this.trackZ - 1);
        boolean z3 = this.func_794_b(this.trackX, this.trackY, this.trackZ + 1);
        boolean z4 = this.func_794_b(this.trackX - 1, this.trackY, this.trackZ);
        boolean z5 = this.func_794_b(this.trackX + 1, this.trackY, this.trackZ);
        int b6 = -1;
        if (z2 || z3) {
            b6 = 0;
        }
        if (z4 || z5) {
            b6 = 1;
        }
        if (z3 && z5 && !z2 && !z4) {
            b6 = 6;
        }
        if (z3 && z4 && !z2 && !z5) {
            b6 = 7;
        }
        if (z2 && z4 && !z3 && !z5) {
            b6 = 8;
        }
        if (z2 && z5 && !z3 && !z4) {
            b6 = 9;
        }
        if (b6 == 0) {
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.minecartTrack.blockID) {
                b6 = 4;
            }
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.minecartTrack.blockID) {
                b6 = 5;
            }
        }
        if (b6 == 1) {
            if (this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                b6 = 2;
            }
            if (this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                b6 = 3;
            }
        }
        if (b6 < 0) {
            b6 = 0;
        }
        this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, b6);
    }

    private boolean func_786_c(int i1, int i2, int i3) {
        MinecartTrackLogic minecartTrackLogic4 = this.getMinecartTrackLogic(new ChunkPosition(i1, i2, i3));
        if (minecartTrackLogic4 == null) {
            return false;
        }
        minecartTrackLogic4.func_785_b();
        return minecartTrackLogic4.handleKeyPress(this);
    }

    public void func_792_a(boolean z1) {
        boolean z2 = this.func_786_c(this.trackX, this.trackY, this.trackZ - 1);
        boolean z3 = this.func_786_c(this.trackX, this.trackY, this.trackZ + 1);
        boolean z4 = this.func_786_c(this.trackX - 1, this.trackY, this.trackZ);
        boolean z5 = this.func_786_c(this.trackX + 1, this.trackY, this.trackZ);
        int b6 = -1;
        if ((z2 || z3) && !z4 && !z5) {
            b6 = 0;
        }
        if ((z4 || z5) && !z2 && !z3) {
            b6 = 1;
        }
        if (z3 && z5 && !z2 && !z4) {
            b6 = 6;
        }
        if (z3 && z4 && !z2 && !z5) {
            b6 = 7;
        }
        if (z2 && z4 && !z3 && !z5) {
            b6 = 8;
        }
        if (z2 && z5 && !z3 && !z4) {
            b6 = 9;
        }
        if (b6 == -1) {
            if (z2 || z3) {
                b6 = 0;
            }
            if (z4 || z5) {
                b6 = 1;
            }
            if (z1) {
                if (z3 && z5) {
                    b6 = 6;
                }
                if (z4 && z3) {
                    b6 = 7;
                }
                if (z5 && z2) {
                    b6 = 9;
                }
                if (z2 && z4) {
                    b6 = 8;
                }
            } else {
                if (z2 && z4) {
                    b6 = 8;
                }
                if (z5 && z2) {
                    b6 = 9;
                }
                if (z4 && z3) {
                    b6 = 7;
                }
                if (z3 && z5) {
                    b6 = 6;
                }
            }
        }
        if (b6 == 0) {
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ - 1) == this.minecartTrack.blockID) {
                b6 = 4;
            }
            if (this.worldObj.getBlockId(this.trackX, this.trackY + 1, this.trackZ + 1) == this.minecartTrack.blockID) {
                b6 = 5;
            }
        }
        if (b6 == 1) {
            if (this.worldObj.getBlockId(this.trackX + 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                b6 = 2;
            }
            if (this.worldObj.getBlockId(this.trackX - 1, this.trackY + 1, this.trackZ) == this.minecartTrack.blockID) {
                b6 = 3;
            }
        }
        if (b6 < 0) {
            b6 = 0;
        }
        this.trackMetadata = b6;
        this.calculateConnectedTracks();
        this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, b6);
        int i7 = 0;
        while (i7 < this.connectedTracks.size()) {
            MinecartTrackLogic minecartTrackLogic8 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(i7));
            if (minecartTrackLogic8 != null) {
                minecartTrackLogic8.func_785_b();
                if (minecartTrackLogic8.handleKeyPress(this)) {
                    minecartTrackLogic8.func_788_d(this);
                }
            }
            ++i7;
        }
    }

    static int getNAdjacentTracks(MinecartTrackLogic minecartTrackLogic0) {
        return minecartTrackLogic0.getAdjacentTracks();
    }
}

