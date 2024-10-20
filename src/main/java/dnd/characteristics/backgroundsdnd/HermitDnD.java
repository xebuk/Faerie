package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class HermitDnD extends BackgroundDnD {

    public HermitDnD() {
        this.name = "Отшельник";

        this.learnedSkills = Arrays.asList("Медицина", "Религия");

        this.instrumentMastery1 = "Набор травника";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
