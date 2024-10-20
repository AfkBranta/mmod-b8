/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.MovementInput;

public class MovementInputFromOptions
extends MovementInput {
    private boolean[] movementKeyStates = new boolean[10];
    private GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettings1) {
        this.gameSettings = gameSettings1;
    }

    @Override
    public void checkKeyForMovementInput(int i1, boolean z2) {
        int b3 = -1;
        if (i1 == this.gameSettings.keyBindForward.keyCode) {
            b3 = 0;
        }
        if (i1 == this.gameSettings.keyBindBack.keyCode) {
            b3 = 1;
        }
        if (i1 == this.gameSettings.keyBindLeft.keyCode) {
            b3 = 2;
        }
        if (i1 == this.gameSettings.keyBindRight.keyCode) {
            b3 = 3;
        }
        if (i1 == this.gameSettings.keyBindJump.keyCode) {
            b3 = 4;
        }
        if (i1 == this.gameSettings.keyBindSneak.keyCode) {
            b3 = 5;
        }
        if (b3 >= 0) {
            this.movementKeyStates[b3] = z2;
        }
    }

    @Override
    public void resetKeyState() {
        int i1 = 0;
        while (i1 < 10) {
            this.movementKeyStates[i1] = false;
            ++i1;
        }
    }

    @Override
    public void updatePlayerMoveState(EntityPlayer entityPlayer1) {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.movementKeyStates[0]) {
            this.moveForward += 1.0f;
        }
        if (this.movementKeyStates[1]) {
            this.moveForward -= 1.0f;
        }
        if (this.movementKeyStates[2]) {
            this.moveStrafe += 1.0f;
        }
        if (this.movementKeyStates[3]) {
            this.moveStrafe -= 1.0f;
        }
        this.jump = this.movementKeyStates[4];
        this.sneak = this.movementKeyStates[5];
        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }
}

