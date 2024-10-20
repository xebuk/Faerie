package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class HalfElfDnD extends RaceDnD {

    public HalfElfDnD() {
        this.name = "Полуэльф";
        this.subspeciesName = "Не выбран";

        this.charismaBonus = 2;

        this.statBonus1 = 1;
        this.statBonusTowards1 = "Не выбран";
        this.statBonus2 = 1;
        this.statBonusTowards2 = "Не выбран";

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
