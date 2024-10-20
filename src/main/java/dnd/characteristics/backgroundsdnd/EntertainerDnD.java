package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class EntertainerDnD extends BackgroundDnD {

    public EntertainerDnD() {
        this.name = "Артист";

        this.learnedSkills = Arrays.asList("Акробатика", "Выступление");

        this.instrumentMastery1 = "Набор для грима";
        this.instrumentMastery2 = "Не выбрано";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
