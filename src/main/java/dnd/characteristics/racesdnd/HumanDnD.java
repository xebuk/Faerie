package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class HumanDnD extends RaceDnD {

    public HumanDnD() {
        this.name = "Человек";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.strengthBonus = 1;
        this.dexterityBonus = 1;
        this.constitutionBonus = 1;
        this.intelligenceBonus = 1;
        this.wisdomBonus = 1;
        this.charismaBonus = 1;

        this.walkingSpeed = 30;

        this.bonusLanguages = 1;
        this.languages = Set.of(LanguagesDnD.COMMON);
        this.scripts = Set.of(ScriptsDnD.COMMON);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
