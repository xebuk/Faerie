package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.InstrumentsDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class RogueDnD extends JobDnD {

    public RogueDnD() {
        this.title = "Плут";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.HAND_CROSSBOW,
                WeaponTraitsDnD.LONGSWORD, WeaponTraitsDnD.RAPIER, WeaponTraitsDnD.SHORTSWORD);
        this.instrumentsMastery = Set.of(InstrumentsDnD.THIEF_TOOLS);
        this.saveMastery = Set.of(StatsTypeDnD.DEXTERITY, StatsTypeDnD.INTELLIGENCE);

        this.startingSkillAmount = 4;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выступление",
                "Запугивание", "Ловкость рук", "Обман",
                "Проницательность", "Скрытность", "Убеждение");

        this.inventoryRefusalMoney = "4d4";
    }
}
