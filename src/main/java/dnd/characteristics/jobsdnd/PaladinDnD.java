package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class PaladinDnD extends JobDnD {

    public PaladinDnD() {
        this.title = "Паладин";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 10;
        this.healthDice = "1d10";

        this.mainStat1 = "Мудрость";
        this.mainStat2 = "Харизма";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Атлетика", "Запугивание", "Медицина",
                "Проницательность", "Религия", "Убеждение");
    }
}
