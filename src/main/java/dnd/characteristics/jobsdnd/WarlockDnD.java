package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class WarlockDnD extends JobDnD {

    public WarlockDnD() {
        this.title = "Колдун";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.WISDOM, StatsTypeDnD.CHARISMA);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "Запугивание", "История",
                "Магия", "Обман",
                "Природа", "Религия");

        this.inventoryRefusalMoney = "4d4";
    }
}
