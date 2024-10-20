package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class FolkHeroDnD extends BackgroundDnD {

    public FolkHeroDnD() {
        this.name = "Народный герой";

        this.learnedSkills = Arrays.asList("Выживание", "Уход за животными");

        this.instrumentMastery1 = "Не выбран";
        this.instrumentMastery2 = "Наземный транспорт";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
