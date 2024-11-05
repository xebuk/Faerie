package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;
import dnd.values.ArmorTypeDnD;
import dnd.values.InstrumentsDnD;
import dnd.values.StatsTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.Arrays;
import java.util.Set;

public class ArtificerDnD extends JobDnD {

    public ArtificerDnD() {
        this.title = "Изобретатель";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
        this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
        this.instrumentsMastery = Set.of(InstrumentsDnD.THIEF_TOOLS, InstrumentsDnD.TINKER_TOOLS);
        // одни инструменты ремесленника на выбор
        this.saveMastery = Set.of(StatsTypeDnD.CONSTITUTION, StatsTypeDnD.INTELLIGENCE);

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "Восприятие", "История", "Ловкость рук",
                "Магия", "Медицина", "Природа");

        this.inventoryRefusalMoney = "5d4";
    }
}
