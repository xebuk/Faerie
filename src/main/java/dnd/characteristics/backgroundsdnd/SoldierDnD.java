package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class SoldierDnD extends BackgroundDnD {

    public SoldierDnD() {
        this.name = "Солдат";

        this.learnedSkills = Arrays.asList("Атлетика", "Запугивание");

        this.instrumentMastery1 = "Не выбрано";
        this.instrumentMastery2 = "Наземный транспорт";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
