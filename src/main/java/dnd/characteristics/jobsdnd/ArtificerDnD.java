package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class ArtificerDnD extends JobDnD {

    public ArtificerDnD() {
        this.title = "Изобретатель";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Телосложение";
        this.mainStat2 = "Интеллект";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Анализ", "Восприятие", "История", "Ловкость рук",
                "Магия", "Медицина", "Природа");
    }
}
