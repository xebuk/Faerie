package dnd.characteristics.racesdnd;

import dnd.values.LanguagesDnD;
import dnd.characteristics.RaceDnD;
import dnd.values.RacesSizeDnD;
import dnd.values.ScriptsDnD;

import java.util.ArrayList;
import java.util.Set;

public class DragonbornDnD extends RaceDnD {

    public DragonbornDnD() {
        this.name = "Драконорожденный";
        this.subspeciesName = "Не выбран";
        this.size = RacesSizeDnD.MEDIUM;

        this.strengthBonus = 2;
        this.charismaBonus = 1;

        this.walkingSpeed = 30;

        this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.DRACONIC);
        this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DRACONIC);

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
