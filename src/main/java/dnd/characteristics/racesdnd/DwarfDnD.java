package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class DwarfDnD extends RaceDnD {

    public DwarfDnD() {
        this.name = "Дварф";
        this.subspeciesName = "Не выбран";

        this.constitutionBonus = 2;

        this.statBonus1 = 1;
        this.statBonusTowards1 = "Не выбран";

        this.walkingSpeed = 25;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
