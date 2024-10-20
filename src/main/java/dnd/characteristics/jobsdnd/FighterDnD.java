package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class FighterDnD extends JobDnD {

    public FighterDnD() {
        this.title = "Воин";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 10;
        this.healthDice = "1d10";

        this.mainStat1 = "Сила";
        this.mainStat2 = "Телосложение";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Атлетика",
                "Восприятие", "Выживание",
                "Запугивание", "История",
                "Проницательность", "Уход за животными");
    }
}
