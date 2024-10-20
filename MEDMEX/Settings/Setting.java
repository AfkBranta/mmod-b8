/*
 * Decompiled with CFR 0.152.
 */
package MEDMEX.Settings;

import MEDMEX.Modules.Module;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Setting {
    private String name;
    private Module parent;
    public String mode;
    private String sval;
    private ArrayList<String> options;
    private boolean bval;
    private double dval;
    private double min;
    private double max;
    private boolean onlyint = false;
    private Color color;
    private List list;
    private String classtype;

    public Setting(String name, Module parent, String sval, ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = "Enum";
    }

    public Setting(String name, Module parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = "Boolean";
    }

    public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        this.mode = "Slider";
    }

    public Setting(String name, Module parent, Color color) {
        this.name = name;
        this.parent = parent;
        this.color = color;
        this.mode = "Color";
    }

    public Setting(String name, Module parent, List list, String classtype) {
        this.name = name;
        this.parent = parent;
        this.list = list;
        this.mode = "List";
        this.classtype = classtype;
    }

    public String getClasstype() {
        return this.classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public List getList() {
        return this.list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getName() {
        return this.name;
    }

    public Module getParentMod() {
        return this.parent;
    }

    public String getValString() {
        return this.sval;
    }

    public void setValString(String in) {
        this.sval = in;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public boolean getValBoolean() {
        return this.bval;
    }

    public void setValBoolean(boolean in) {
        this.bval = in;
    }

    public double getValDouble() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return this.dval;
    }

    public void setValDouble(double in) {
        this.dval = in;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public boolean isCombo() {
        return this.mode.equalsIgnoreCase("Enum");
    }

    public boolean isCheck() {
        return this.mode.equalsIgnoreCase("Boolean");
    }

    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("Slider");
    }

    public boolean isColor() {
        return this.mode.equalsIgnoreCase("Color");
    }

    public boolean isList() {
        return this.mode.equalsIgnoreCase("List");
    }

    public boolean onlyInt() {
        return this.onlyint;
    }

    public String toString() {
        if (this.isCheck()) {
            return "<" + this.getName() + " : " + this.getValBoolean() + ">";
        }
        if (this.isCombo()) {
            return "<" + this.getName() + " : " + this.getValString() + ">";
        }
        if (this.isSlider()) {
            return "<" + this.getName() + " : " + this.getValDouble() + ">";
        }
        if (this.isList()) {
            return "<" + this.getName() + " : " + this.getList().toString() + ">";
        }
        return "";
    }
}

