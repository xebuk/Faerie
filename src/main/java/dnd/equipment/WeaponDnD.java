package dnd.equipment;

import dnd.values.WeaponTraitsDnD;

import java.util.HashSet;

public class WeaponDnD extends ItemDnD {
    public String name;
    public String summary;

    public int value;

    public int weight;

    public WeaponTraitsDnD type;

    public String attackDice;
    public String damageType;

    public HashSet<WeaponTraitsDnD> traits;
    public int minDistance;
    public int maxDistance;
}
