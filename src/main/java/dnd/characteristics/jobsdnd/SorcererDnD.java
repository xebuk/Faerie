package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class SorcererDnD extends JobDnD {

    public SorcererDnD() {
        this.title = "Чародей";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 6;
        this.healthDice = "1d6";

        this.mainStat1 = "Телосложение";
        this.mainStat2 = "Харизма";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Запугивание", "Магия", "Обман",
                "Проницательность", "Религия", "Убеждение");
    }
}
