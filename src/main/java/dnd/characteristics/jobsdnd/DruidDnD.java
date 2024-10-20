package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;

public class DruidDnD extends JobDnD {

    public DruidDnD() {
        this.title = "Друид";
        this.advancedTitle = "Не выбран";
        this.usesMagic = true;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.mainStat1 = "Интеллект";
        this.mainStat2 = "Мудрость";

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Восприятие", "Выживание",
                "Магия", "Медицина", "Природа",
                "Проницательность", "Религия", "Уход за животными");
    }
}
