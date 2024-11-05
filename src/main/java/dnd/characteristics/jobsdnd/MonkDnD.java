package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class MonkDnD extends JobDnD {

    public MonkDnD() {
        this.title = "Монах";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of();
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.SHORTSWORD);
        this.instrumentsMastery = Set.of(); // либо инструмент ремесленника, либо музыкальный инструмент
        this.saveMastery = Set.of(StatsTypeDnD.STRENGTH, StatsTypeDnD.DEXTERITY);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Атлетика",
                "История", "Проницательность", "Религия",
                "Скрытность");

        this.inventoryRefusalMoney = "5d4"; // на 10 не умножать
    }
}
