package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class BardDnD extends JobDnD {

    public BardDnD() {
        this.title = "Бард";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Харизма";
        this.mainStat2 = "Ловкость";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выживание", "Выступление",
                "Запугивание", "История", "Ловкость рук",
                "Магия", "Медицина", "Обман",
                "Природа", "Проницательность", "Религия",
                "Скрытность", "Убеждение", "Уход за животными");
    }
}
