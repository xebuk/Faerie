package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class AcolyteDnD extends BackgroundDnD {

    public AcolyteDnD() {
        this.name = "Прислужник";

        this.learnedSkills = Arrays.asList("Проницательность", "Религия");

        this.instrumentMastery1 = "Нет";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
