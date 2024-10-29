package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClericDnD extends JobDnD {

    public ClericDnD() {
        this.title = "Жрец";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.WISDOM, StatsTypeDnD.CHARISMA);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("История", "Медицина",
                "Проницательность", "Религия", "Убеждение");

        this.inventoryRefusalMoney = "5d4";
    }
}
