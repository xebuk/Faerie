package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class WizardDnD extends JobDnD {

    public WizardDnD() {
        this.title = "Волшебник";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 6;
        this.healthDice = "1d6";

        this.armorMastery = Set.of();
        this.weaponMastery = Set.of(WeaponTraitsDnD.DAGGER, WeaponTraitsDnD.SLING,
                WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.LIGHT_CROSSBOW);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.INTELLIGENCE, StatsTypeDnD.WISDOM);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "История", "Магия", "Медицина",
                "Проницательность", "Религия");

        this.inventoryRefusalMoney = "4d4";
    }
}
