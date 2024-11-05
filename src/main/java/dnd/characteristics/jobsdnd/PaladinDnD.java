package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class PaladinDnD extends JobDnD {

    public PaladinDnD() {
        this.title = "Паладин";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 10;
        this.healthDice = "1d10";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.HEAVY, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.WISDOM, StatsTypeDnD.CHARISMA);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Атлетика", "Запугивание", "Медицина",
                "Проницательность", "Религия", "Убеждение");

        this.inventoryRefusalMoney = "5d4";
    }
}
