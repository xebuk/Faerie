package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class RogueDnD extends JobDnD {

    public RogueDnD() {
        this.title = "Плут";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Ловкость";
        this.mainStat2 = "Интеллект";

        this.startingSkillAmount = 4;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выступление",
                "Запугивание", "Ловкость рук", "Обман",
                "Проницательность", "Скрытность", "Убеждение");
    }
}
