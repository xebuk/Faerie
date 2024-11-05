package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BarbarianDnD extends JobDnD {

    public BarbarianDnD() {
        title = "Варвар";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 12;
        this.healthDice = "1d12";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.STRENGTH, StatsTypeDnD.CONSTITUTION);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Атлетика", "Восприятие", "Выживание",
                "Запугивание", "Природа", "Уход за животными");

        this.inventoryRefusalMoney = "2d4";
    }
}
