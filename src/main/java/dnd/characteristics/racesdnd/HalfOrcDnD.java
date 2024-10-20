package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class HalfOrcDnD extends RaceDnD {

    public HalfOrcDnD() {
        this.name = "Полуорк";
        this.subspeciesName = "Не выбран";

        this.strengthBonus = 2;
        this.constitutionBonus = 1;

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
