package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class RangerDnD extends JobDnD {

    public RangerDnD() {
        this.title = "Следопыт";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 10;
        this.healthDice = "1d10";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.STRENGTH, StatsTypeDnD.DEXTERITY);

        this.startingSkillAmount = 3;
        this.skillRoster = Arrays.asList("Анализ", "Атлетика",
                "Восприятие", "Выживание",
                "Природа", "Проницательность",
                "Скрытность", "Уход за животными");

        this.inventoryRefusalMoney = "5d4";
    }
}
