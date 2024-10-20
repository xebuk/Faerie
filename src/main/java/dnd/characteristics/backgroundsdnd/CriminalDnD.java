package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class CriminalDnD extends BackgroundDnD {

    public CriminalDnD() {
        this.name = "Преступник";

        this.learnedSkills = Arrays.asList("Обман", "Скрытность");

        this.instrumentMastery1 = "Воровские инструменты";
        this.instrumentMastery2 = "Не выбрано";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
