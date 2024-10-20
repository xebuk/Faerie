package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;

import java.util.ArrayList;
import java.util.Arrays;

public class GuildArtisanDnD extends BackgroundDnD {

    public GuildArtisanDnD() {
        this.name = "Гильдейский ремесленник";

        this.learnedSkills = Arrays.asList("Проницательность", "Убеждение");

        this.instrumentMastery1 = "Не выбрано";
        this.instrumentMastery2 = "Нет";

        this.personality = new ArrayList<>();
        this.ideal = new ArrayList<>();
        this.bond = new ArrayList<>();
        this.flaw = new ArrayList<>();
    }
}
