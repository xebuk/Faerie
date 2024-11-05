package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.InstrumentsDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class DruidDnD extends JobDnD {

    public DruidDnD() {
        this.title = "Друид";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.MACE,
                WeaponTraitsDnD.DART, WeaponTraitsDnD.CLUB, WeaponTraitsDnD.DAGGER,
                WeaponTraitsDnD.SPEAR, WeaponTraitsDnD.JAVELIN, WeaponTraitsDnD.SLING,
                WeaponTraitsDnD.SICKLE, WeaponTraitsDnD.SCIMITAR);
        this.instrumentsMastery = Set.of(InstrumentsDnD.HERBALISM_KIT);
        this.saveMastery = Set.of(StatsTypeDnD.INTELLIGENCE, StatsTypeDnD.WISDOM);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Восприятие", "Выживание",
                "Магия", "Медицина", "Природа",
                "Проницательность", "Религия", "Уход за животными");

        this.inventoryRefusalMoney = "2d4";
    }
}
