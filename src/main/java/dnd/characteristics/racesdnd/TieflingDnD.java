package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class TieflingDnD extends RaceDnD {

    public TieflingDnD() {
        this.name = "Тифлинг";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.intelligenceBonus = 1;
        this.charismaBonus = 2;

        this.walkingSpeed = 30;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.INFERNAL);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.INFERNAL);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
