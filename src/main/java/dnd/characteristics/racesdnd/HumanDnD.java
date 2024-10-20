package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class HumanDnD extends RaceDnD {

    public HumanDnD() {
        this.name = "Человек";
        this.subspeciesName = "Не выбран";

        this.strengthBonus = 1;
        this.dexterityBonus = 1;
        this.constitutionBonus = 1;
        this.intelligenceBonus = 1;
        this.wisdomBonus = 1;
        this.charismaBonus = 1;

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
