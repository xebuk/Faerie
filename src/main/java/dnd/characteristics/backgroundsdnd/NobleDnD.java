package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class NobleDnD extends BackgroundDnD {

    public NobleDnD() {
        this.name = "Благородный";

        this.learnedSkills = Arrays.asList("История", "Убеждение");

        this.instrumentMastery1 = "Игровой набор";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
