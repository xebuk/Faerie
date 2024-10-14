package game.characteristics;

import game.items.Armor;
import game.items.Weapon;

import java.io.Serializable;

public class Job implements Serializable {
    public String jobName;
    public Weapon weaponType;
    public Armor armorType;

    public int startHealth;
    public int startArmorClass;
}
