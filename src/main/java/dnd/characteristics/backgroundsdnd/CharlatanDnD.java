package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class CharlatanDnD extends BackgroundDnD {

    public CharlatanDnD() {
        this.name = "Шарлатан";

        this.learnedSkills = Arrays.asList("Ловкость рук", "Обман");

        this.instrumentMastery1 = "Набор для грима";
        this.instrumentMastery2 = "Набор для фальцификации";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
