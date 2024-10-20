package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class TieflingDnD extends RaceDnD {

    public TieflingDnD() {
        this.name = "Тифлинг";
        this.subspeciesName = "Не выбран";

        this.intelligenceBonus = 1;
        this.charismaBonus = 2;

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
