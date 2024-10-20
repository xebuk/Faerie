package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class UrchinDnD extends BackgroundDnD {

    public UrchinDnD() {
        this.name = "Беспризорник";

        this.learnedSkills = Arrays.asList("Ловкость рук", "Скрытность");

        this.instrumentMastery1 = "Набор для грима";
        this.instrumentMastery2 = "Воровские инструменты";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
