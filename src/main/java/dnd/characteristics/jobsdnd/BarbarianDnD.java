package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class BarbarianDnD extends JobDnD {

    public BarbarianDnD() {
        title = "Варвар";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 12;
        this.healthDice = "1d12";

        this.mainStat1 = "Сила";
        this.mainStat2 = "Телосложение";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Атлетика", "Восприятие", "Выживание",
                "Запугивание", "Природа", "Уход за животными");
    }
}
