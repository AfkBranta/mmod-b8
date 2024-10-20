/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Commands.commands;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Block;
import net.minecraft.src.StringTranslate;

public class BlockID
extends Command {
    public BlockID() {
        super("BlockID", "Get blockid from block name", "BlockID <blockName>", "id");
    }

    @Override
    public void onCommand(String[] args) {
        String blockName = args[0];
        Block[] blockArray = Block.blocksList;
        int n = Block.blocksList.length;
        int n2 = 0;
        while (n2 < n) {
            Block b = blockArray[n2];
            if (b != null) {
                String s = StringTranslate.getInstance().translateNamedKey(b.getBlockName().trim());
                if (blockName.equalsIgnoreCase(s = s.replace(" ", "_"))) {
                    Client.sendLocalChat("BlockID for " + s + ": " + b.blockID);
                    return;
                }
            }
            ++n2;
        }
    }
}

