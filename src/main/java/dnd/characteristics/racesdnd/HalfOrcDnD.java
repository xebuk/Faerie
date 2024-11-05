package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class HalfOrcDnD extends RaceDnD {

    public HalfOrcDnD() {
        this.name = "Полуорк";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.strengthBonus = 2;
        this.constitutionBonus = 1;

        this.walkingSpeed = 30;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.ORCISH);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
