package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class ElfDnD extends RaceDnD {

    public ElfDnD() {
        this.name = "Эльф";
        this.subspeciesName = "Не выбран";

        this.dexterityBonus = 2;

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
