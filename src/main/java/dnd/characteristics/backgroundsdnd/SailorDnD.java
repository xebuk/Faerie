package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class SailorDnD extends BackgroundDnD {

    public SailorDnD() {
        this.name = "Моряк";

        this.learnedSkills = Arrays.asList("Атлетика", "Восприятие");

        this.instrumentMastery1 = "Инструменты навигатора";
        this.instrumentMastery2 = "Водный транспорт";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
