package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class HomebrewJobDnD extends JobDnD {

    public HomebrewJobDnD() {
        this.title = "Свой класс";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Не выбран";
        this.mainStat2 = "Не выбран";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выживание", "Выступление",
                "Запугивание", "История", "Ловкость рук",
                "Магия", "Медицина", "Обман",
                "Природа", "Проницательность", "Религия",
                "Скрытность", "Убеждение", "Уход за животными");
    }
}
