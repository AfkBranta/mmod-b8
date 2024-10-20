/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import net.minecraft.src.Session;
import net.minecraft.src.World;

public class PlayerControllerTest
extends PlayerController {
    public PlayerControllerTest(Minecraft minecraft1) {
        super(minecraft1);
        this.field_1064_b = true;
    }

    @Override
    public void func_6473_b(EntityPlayer entityPlayer1) {
        int i2 = 0;
        while (i2 < 9) {
            if (entityPlayer1.inventory.mainInventory[i2] == null) {
                this.mc.thePlayer.inventory.mainInventory[i2] = new ItemStack((Block)Session.registeredBlocksList.get(i2));
            } else {
                this.mc.thePlayer.inventory.mainInventory[i2].stackSize = 1;
            }
            ++i2;
        }
    }

    @Override
    public boolean shouldDrawHUD() {
        return false;
    }

    @Override
    public void func_717_a(World world1) {
        super.func_717_a(world1);
    }

    @Override
    public void updateController() {
    }
}

