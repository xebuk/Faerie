package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class MonkDnD extends JobDnD {

    public MonkDnD() {
        this.title = "Монах";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Сила";
        this.mainStat2 = "Ловкость";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Атлетика",
                "История", "Проницательность", "Религия",
                "Скрытность");
    }
}
