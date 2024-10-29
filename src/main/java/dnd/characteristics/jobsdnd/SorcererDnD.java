package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class SorcererDnD extends JobDnD {

    public SorcererDnD() {
        this.title = "Чародей";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 6;
        this.healthDice = "1d6";

        this.armorMastery = Set.of();
        this.weaponMastery = Set.of(WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.DART,
                WeaponTraitsDnD.DAGGER, WeaponTraitsDnD.LIGHT_CROSSBOW, WeaponTraitsDnD.SLING);
        this.instrumentsMastery = Set.of();
        this.saveMastery = Set.of(StatsTypeDnD.CONSTITUTION, StatsTypeDnD.CHARISMA);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Запугивание", "Магия", "Обман",
                "Проницательность", "Религия", "Убеждение");

        this.inventoryRefusalMoney = "3d4";
    }
}
