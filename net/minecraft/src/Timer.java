/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

public class Timer {
    public float ticksPerSecond;
    private double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float timerSpeed = 1.0f;
    public float elapsedPartialTicks = 0.0f;
    private long lastSyncSysClock;
    private long lastSyncHRClock;
    private double timeSyncAdjustment = 1.0;

    public Timer(float f1) {
        this.ticksPerSecond = f1;
        this.lastSyncSysClock = System.currentTimeMillis();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }

    public void updateTimer() {
        double d9;
        long j1 = System.currentTimeMillis();
        long j3 = j1 - this.lastSyncSysClock;
        long j5 = System.nanoTime() / 1000000L;
        if (j3 > 1000L) {
            long j7 = j5 - this.lastSyncHRClock;
            d9 = (double)j3 / (double)j7;
            this.timeSyncAdjustment += (d9 - this.timeSyncAdjustment) * (double)0.2f;
            this.lastSyncSysClock = j1;
            this.lastSyncHRClock = j5;
        }
        if (j3 < 0L) {
            this.lastSyncSysClock = j1;
            this.lastSyncHRClock = j5;
        }
        double d11 = (double)j5 / 1000.0;
        d9 = (d11 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = d11;
        if (d9 < 0.0) {
            d9 = 0.0;
        }
        if (d9 > 1.0) {
            d9 = 1.0;
        }
        this.elapsedPartialTicks = (float)((double)this.elapsedPartialTicks + d9 * (double)this.timerSpeed * (double)this.ticksPerSecond);
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= (float)this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}

