package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class HumanVariantDnD extends RaceDnD {

    public HumanVariantDnD() {
        this.name = "Человек (Вариант)";
        this.subspeciesName = "Не выбран";

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
