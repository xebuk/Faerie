package dnd.characteristics.racesdnd;

import dnd.characteristics.RaceDnD;

import java.util.ArrayList;

public class DragonbornDnD extends RaceDnD {

    public DragonbornDnD() {
        this.name = "Драконорожденный";
        this.subspeciesName = "Не выбран";

        this.strengthBonus = 2;
        this.charismaBonus = 1;

        this.walkingSpeed = 30;

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
