package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class HalflingDnD extends RaceDnD {

    public HalflingDnD() {
        this.name = "Полурослик";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.SMALL;

        this.dexterityBonus = 2;

        this.walkingSpeed = 25;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.HALFLING);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
