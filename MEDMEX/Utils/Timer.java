/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Utils;

public class Timer {
    public long timeleft = 0L;
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        this.timeleft = time - (System.currentTimeMillis() - this.lastMS);
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
}

