package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomebrewBackgroundDnD extends BackgroundDnD {

    public HomebrewBackgroundDnD() {
        this.name = "Своя предыстория";
        this.summary = "Введите описание своей предыстории.";

        this.specialAbility = "Название способности";
        this.specialAbilitySummary = """
                Описание вашей способности.
                """;

        this.bonusSkills = 0;
        this.learnedSkills = List.of();

        this.instrumentMastery = Set.of();

        this.bonusLanguages = 0;
        this.languages = Set.of();
        this.scripts = Set.of();

        this.specialInfo = new ArrayList<>();

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
