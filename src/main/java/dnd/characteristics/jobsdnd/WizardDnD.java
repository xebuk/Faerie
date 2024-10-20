package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class WizardDnD extends JobDnD {

    public WizardDnD() {
        this.title = "Волшебник";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 6;
        this.healthDice = "1d6";

        this.mainStat1 = "Интеллект";
        this.mainStat2 = "Мудрость";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "История", "Магия", "Медицина",
                "Проницательность", "Религия");
    }
}
