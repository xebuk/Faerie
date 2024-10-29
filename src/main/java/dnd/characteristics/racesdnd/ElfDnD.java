package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class ElfDnD extends RaceDnD {

    public ElfDnD() {
        this.name = "Эльф";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.dexterityBonus = 2;

        this.walkingSpeed = 30;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.ELVISH);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.ELVISH);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
