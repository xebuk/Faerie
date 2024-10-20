package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class RangerDnD extends JobDnD {

    public RangerDnD() {
        this.title = "Следопыт";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 10;
        this.healthDice = "1d10";

        this.mainStat1 = "Сила";
        this.mainStat2 = "Ловкость";

        this.startingSkillAmount = 3;
        this.skillRoster = Arrays.asList("Анализ", "Атлетика",
                "Восприятие", "Выживание",
                "Природа", "Проницательность",
                "Скрытность", "Уход за животными");
    }
}
