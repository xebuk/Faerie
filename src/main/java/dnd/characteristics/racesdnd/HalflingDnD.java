package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class HalflingDnD extends RaceDnD {

    public HalflingDnD() {
        this.name = "Полурослик";
        this.subspeciesName = "Не выбран";

        this.dexterityBonus = 2;

        this.walkingSpeed = 25;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
