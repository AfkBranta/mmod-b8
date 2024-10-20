/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.MCHashTable;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Path;
import net.minecraft.src.PathEntity;
import net.minecraft.src.PathPoint;

public class Pathfinder {
    private IBlockAccess worldMap;
    private Path path = new Path();
    private MCHashTable pointMap = new MCHashTable();
    private PathPoint[] pathOptions = new PathPoint[32];

    public Pathfinder(IBlockAccess iBlockAccess1) {
        this.worldMap = iBlockAccess1;
    }

    public PathEntity createEntityPathTo(Entity entity1, Entity entity2, float f3) {
        return this.createEntityPathTo(entity1, entity2.posX, entity2.boundingBox.minY, entity2.posZ, f3);
    }

    public PathEntity createEntityPathTo(Entity entity1, int i2, int i3, int i4, float f5) {
        return this.createEntityPathTo(entity1, (float)i2 + 0.5f, (float)i3 + 0.5f, (float)i4 + 0.5f, f5);
    }

    private PathEntity createEntityPathTo(Entity entity1, double d2, double d4, double d6, float f8) {
        this.path.clearPath();
        this.pointMap.clearMap();
        PathPoint pathPoint9 = this.openPoint(MathHelper.floor_double(entity1.boundingBox.minX), MathHelper.floor_double(entity1.boundingBox.minY), MathHelper.floor_double(entity1.boundingBox.minZ));
        PathPoint pathPoint10 = this.openPoint(MathHelper.floor_double(d2 - (double)(entity1.width / 2.0f)), MathHelper.floor_double(d4), MathHelper.floor_double(d6 - (double)(entity1.width / 2.0f)));
        PathPoint pathPoint11 = new PathPoint(MathHelper.floor_float(entity1.width + 1.0f), MathHelper.floor_float(entity1.height + 1.0f), MathHelper.floor_float(entity1.width + 1.0f));
        PathEntity pathEntity12 = this.addToPath(entity1, pathPoint9, pathPoint10, pathPoint11, f8);
        return pathEntity12;
    }

    private PathEntity addToPath(Entity entity1, PathPoint pathPoint2, PathPoint pathPoint3, PathPoint pathPoint4, float f5) {
        pathPoint2.totalPathDistance = 0.0f;
        pathPoint2.distanceToTarget = pathPoint2.distanceToNext = pathPoint2.distanceTo(pathPoint3);
        this.path.clearPath();
        this.path.addPoint(pathPoint2);
        PathPoint pathPoint6 = pathPoint2;
        while (!this.path.isPathEmpty()) {
            PathPoint pathPoint7 = this.path.dequeue();
            if (pathPoint7.equals(pathPoint3)) {
                return this.createEntityPath(pathPoint2, pathPoint3);
            }
            if (pathPoint7.distanceTo(pathPoint3) < pathPoint6.distanceTo(pathPoint3)) {
                pathPoint6 = pathPoint7;
            }
            pathPoint7.isFirst = true;
            int i8 = this.findPathOptions(entity1, pathPoint7, pathPoint4, pathPoint3, f5);
            int i9 = 0;
            while (i9 < i8) {
                PathPoint pathPoint10 = this.pathOptions[i9];
                float f11 = pathPoint7.totalPathDistance + pathPoint7.distanceTo(pathPoint10);
                if (!pathPoint10.isAssigned() || f11 < pathPoint10.totalPathDistance) {
                    pathPoint10.previous = pathPoint7;
                    pathPoint10.totalPathDistance = f11;
                    pathPoint10.distanceToNext = pathPoint10.distanceTo(pathPoint3);
                    if (pathPoint10.isAssigned()) {
                        this.path.changeDistance(pathPoint10, pathPoint10.totalPathDistance + pathPoint10.distanceToNext);
                    } else {
                        pathPoint10.distanceToTarget = pathPoint10.totalPathDistance + pathPoint10.distanceToNext;
                        this.path.addPoint(pathPoint10);
                    }
                }
                ++i9;
            }
        }
        if (pathPoint6 == pathPoint2) {
            return null;
        }
        return this.createEntityPath(pathPoint2, pathPoint6);
    }

    private int findPathOptions(Entity entity1, PathPoint pathPoint2, PathPoint pathPoint3, PathPoint pathPoint4, float f5) {
        int i6 = 0;
        int b7 = 0;
        if (this.getVerticalOffset(entity1, pathPoint2.xCoord, pathPoint2.yCoord + 1, pathPoint2.zCoord, pathPoint3) > 0) {
            b7 = 1;
        }
        PathPoint pathPoint8 = this.getSafePoint(entity1, pathPoint2.xCoord, pathPoint2.yCoord, pathPoint2.zCoord + 1, pathPoint3, b7);
        PathPoint pathPoint9 = this.getSafePoint(entity1, pathPoint2.xCoord - 1, pathPoint2.yCoord, pathPoint2.zCoord, pathPoint3, b7);
        PathPoint pathPoint10 = this.getSafePoint(entity1, pathPoint2.xCoord + 1, pathPoint2.yCoord, pathPoint2.zCoord, pathPoint3, b7);
        PathPoint pathPoint11 = this.getSafePoint(entity1, pathPoint2.xCoord, pathPoint2.yCoord, pathPoint2.zCoord - 1, pathPoint3, b7);
        if (pathPoint8 != null && !pathPoint8.isFirst && pathPoint8.distanceTo(pathPoint4) < f5) {
            this.pathOptions[i6++] = pathPoint8;
        }
        if (pathPoint9 != null && !pathPoint9.isFirst && pathPoint9.distanceTo(pathPoint4) < f5) {
            this.pathOptions[i6++] = pathPoint9;
        }
        if (pathPoint10 != null && !pathPoint10.isFirst && pathPoint10.distanceTo(pathPoint4) < f5) {
            this.pathOptions[i6++] = pathPoint10;
        }
        if (pathPoint11 != null && !pathPoint11.isFirst && pathPoint11.distanceTo(pathPoint4) < f5) {
            this.pathOptions[i6++] = pathPoint11;
        }
        return i6;
    }

    private PathPoint getSafePoint(Entity entity1, int i2, int i3, int i4, PathPoint pathPoint5, int i6) {
        PathPoint pathPoint7 = null;
        if (this.getVerticalOffset(entity1, i2, i3, i4, pathPoint5) > 0) {
            pathPoint7 = this.openPoint(i2, i3, i4);
        }
        if (pathPoint7 == null && i6 > 0 && this.getVerticalOffset(entity1, i2, i3 + i6, i4, pathPoint5) > 0) {
            pathPoint7 = this.openPoint(i2, i3 + i6, i4);
            i3 += i6;
        }
        if (pathPoint7 != null) {
            int i10;
            int i8 = 0;
            boolean z9 = false;
            while (i3 > 0 && (i10 = this.getVerticalOffset(entity1, i2, i3 - 1, i4, pathPoint5)) > 0) {
                if (i10 < 0) {
                    return null;
                }
                if (++i8 >= 4) {
                    return null;
                }
                --i3;
            }
            if (i3 > 0) {
                pathPoint7 = this.openPoint(i2, i3, i4);
            }
        }
        return pathPoint7;
    }

    private final PathPoint openPoint(int i1, int i2, int i3) {
        int i4 = PathPoint.func_22329_a(i1, i2, i3);
        PathPoint pathPoint5 = (PathPoint)this.pointMap.lookup(i4);
        if (pathPoint5 == null) {
            pathPoint5 = new PathPoint(i1, i2, i3);
            this.pointMap.addKey(i4, pathPoint5);
        }
        return pathPoint5;
    }

    private int getVerticalOffset(Entity entity1, int i2, int i3, int i4, PathPoint pathPoint5) {
        int i6 = i2;
        while (i6 < i2 + pathPoint5.xCoord) {
            int i7 = i3;
            while (i7 < i3 + pathPoint5.yCoord) {
                int i8 = i4;
                while (i8 < i4 + pathPoint5.zCoord) {
                    Material material9 = this.worldMap.getBlockMaterial(i6, i7, i8);
                    if (material9.getIsSolid()) {
                        return 0;
                    }
                    if (material9 == Material.water || material9 == Material.lava) {
                        return -1;
                    }
                    ++i8;
                }
                ++i7;
            }
            ++i6;
        }
        return 1;
    }

    private PathEntity createEntityPath(PathPoint pathPoint1, PathPoint pathPoint2) {
        int i3 = 1;
        PathPoint pathPoint4 = pathPoint2;
        while (pathPoint4.previous != null) {
            ++i3;
            pathPoint4 = pathPoint4.previous;
        }
        PathPoint[] pathPoint5 = new PathPoint[i3];
        pathPoint4 = pathPoint2;
        pathPoint5[--i3] = pathPoint2;
        while (pathPoint4.previous != null) {
            pathPoint4 = pathPoint4.previous;
            pathPoint5[--i3] = pathPoint4;
        }
        return new PathEntity(pathPoint5);
    }
}

