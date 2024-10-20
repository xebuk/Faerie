package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class ClericDnD extends JobDnD {

    public ClericDnD() {
        this.title = "Жрец";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Мудрость";
        this.mainStat2 = "Харизма";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("История", "Медицина",
                "Проницательность", "Религия", "Убеждение");
    }
}
