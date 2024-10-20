package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class OutlanderDnD extends BackgroundDnD {

    public OutlanderDnD() {
        this.name = "Чузеземец";

        this.learnedSkills = Arrays.asList("Атлетика", "Выживание");

        this.instrumentMastery1 = "Не выбрано";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
