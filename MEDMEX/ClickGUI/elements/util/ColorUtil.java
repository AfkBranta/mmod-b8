/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.ClickGUI.elements.util;

import MEDMEX.Modules.Visual.ClickGUI;
import java.awt.Color;

public class ColorUtil {
    public static Color getClickGUIColor() {
        return new Color((int)ClickGUI.instance.getSet("GuiRed").getValDouble(), (int)ClickGUI.instance.getSet("GuiGreen").getValDouble(), (int)ClickGUI.instance.getSet("GuiBlue").getValDouble());
    }
}

