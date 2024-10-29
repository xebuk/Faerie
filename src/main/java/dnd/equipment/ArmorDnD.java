package dnd.equipment;

import dnd.values.ArmorTypeDnD;

public class ArmorDnD extends ItemDnD {
    public String name;
    public String summary;

    public int value;

    public int weight;

    public ArmorTypeDnD type;

    public int armorClass;
    public int dexterityModMax;

    public int strengthRequirement;

    public boolean hasStealthDisadvantage;
}
