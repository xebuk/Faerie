package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class DwarfDnD extends RaceDnD {

    public DwarfDnD() {
        this.name = "Дварф";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.constitutionBonus = 2;

        this.statBonus1 = 1;
        this.statBonusTowards1 = "Не выбран";

        this.walkingSpeed = 25;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.DWARWISH);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
