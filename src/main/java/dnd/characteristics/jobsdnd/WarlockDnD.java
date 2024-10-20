package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class WarlockDnD extends JobDnD {

    public WarlockDnD() {
        this.title = "Колдун";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Мудрость";
        this.mainStat2 = "Харизма";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "Запугивание", "История",
                "Магия", "Обман",
                "Природа", "Религия");
    }
}
