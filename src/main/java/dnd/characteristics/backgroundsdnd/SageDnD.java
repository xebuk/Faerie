package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class SageDnD extends BackgroundDnD {

    public SageDnD() {
        this.name = "Мудрец";

        this.learnedSkills = Arrays.asList("История", "Магия");

        this.instrumentMastery1 = "Нет";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
