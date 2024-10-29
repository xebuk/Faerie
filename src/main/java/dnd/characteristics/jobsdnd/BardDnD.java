package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BardDnD extends JobDnD {

    public BardDnD() {
        this.title = "Бард";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.LONGSWORD,
                WeaponTraitsDnD.SHORTSWORD, WeaponTraitsDnD.RAPIER, WeaponTraitsDnD.HAND_CROSSBOW);
        this.instrumentsMastery = Set.of(); // три музыкальных инструмента на выбор
        this.saveMastery = Set.of(StatsTypeDnD.DEXTERITY, StatsTypeDnD.CHARISMA);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выживание", "Выступление",
                "Запугивание", "История", "Ловкость рук",
                "Магия", "Медицина", "Обман",
                "Природа", "Проницательность", "Религия",
                "Скрытность", "Убеждение", "Уход за животными");

        this.inventoryRefusalMoney = "5d4";
    }
}
