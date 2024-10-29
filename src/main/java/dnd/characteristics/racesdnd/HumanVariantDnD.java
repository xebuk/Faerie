package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class HumanVariantDnD extends RaceDnD {

    public HumanVariantDnD() {
        this.name = "Человек (Вариант)";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.statBonus1 = 1;
        this.statBonusTowards1 = "Не выбран";
        this.statBonus2 = 1;
        this.statBonusTowards2 = "Не выбран";

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
