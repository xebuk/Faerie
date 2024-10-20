package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;

public class HomebrewBackgroundDnD extends BackgroundDnD {

    public HomebrewBackgroundDnD() {
        this.name = "Своя предыстория";
        this.summary = "Введите описание своей предыстории.";

        this.learnedSkills = new ArrayList<>();

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
