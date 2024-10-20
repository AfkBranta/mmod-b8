/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.PathPoint;

public class Vec3D {
    public double x;
    public double y;
    public double z;

    public Vec3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3D(AxisAlignedBB bb) {
        this.x = bb.minX;
        this.y = bb.minY;
        this.z = bb.minZ;
    }

    public Vec3D offset(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3D add(double x, double y, double z) {
        return new Vec3D(this.x + x, this.y + y, this.z + z);
    }

    public Vec3D scale(double x, double y, double z) {
        return new Vec3D(this.x * x, this.y * y, this.z * z);
    }

    public static Vec3D pathPointToVec(PathPoint p) {
        return new Vec3D(p.xCoord, p.yCoord, p.zCoord);
    }

    public static double getDistance(Vec3D v1, Vec3D v2) {
        double dx = v1.getX() - v2.getX();
        double dy = v1.getY() - v2.getY();
        double dz = v1.getZ() - v2.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static double getVerticalDistance(Vec3D v1, Vec3D v2) {
        double dx = v1.getX() - v2.getX();
        double dz = v1.getZ() - v2.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    public Vec3D normalize() {
        double length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (length != 0.0) {
            double invLength = 1.0 / length;
            return new Vec3D(this.x * invLength, this.y * invLength, this.z * invLength);
        }
        return new Vec3D(0.0, 0.0, 0.0);
    }

    public net.minecraft.src.Vec3D toMCVec3() {
        return new net.minecraft.src.Vec3D(this.x, this.y, this.z);
    }

    public static Vec3D sum(Vec3D vec1, Vec3D vec2) {
        return new Vec3D(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z);
    }

    public AxisAlignedBB toBB() {
        return new AxisAlignedBB(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0);
    }

    public boolean equalsVec(Vec3D vec) {
        return this.x == vec.x && this.y == vec.y && this.z == vec.z;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

