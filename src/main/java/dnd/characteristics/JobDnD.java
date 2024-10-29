package dnd.characteristics;

import dnd.equipment.ItemDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.InstrumentsDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobDnD {
    public String title;
    public String advancedTitle;
    public boolean usesMagic;

    public int level = 1;
    public int startingHealth;
    public String healthDice;

    public Set<ArmorTypeDnD> armorMastery;
    public Set<WeaponTraitsDnD> weaponMastery;
    public Set<InstrumentsDnD> instrumentsMastery;
    public Set<StatsTypeDnD> saveMastery;

    public int startingSkillAmount;
    public List<String> skillRoster;

    public String inventoryRefusalMoney;

    public List<AbilityDnD> abilityRoster;
}
