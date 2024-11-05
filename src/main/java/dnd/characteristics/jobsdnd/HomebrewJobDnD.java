package dnd.characteristics.jobsdnd;

import dnd.characteristics.JobDnD;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HomebrewJobDnD extends JobDnD {

    public HomebrewJobDnD() {
        this.title = "Свой класс";
        this.advancedTitle = "Не выбран";
        this.usesMagic = false;

        this.startingHealth = 8;
        this.healthDice = "1d8";

        this.armorMastery = new HashSet<>();
        this.weaponMastery = new HashSet<>();
        this.instrumentsMastery = new HashSet<>();
        this.saveMastery = new HashSet<>();

        this.startingSkillAmount = 2;
        this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                "Восприятие", "Выживание", "Выступление",
                "Запугивание", "История", "Ловкость рук",
                "Магия", "Медицина", "Обман",
                "Природа", "Проницательность", "Религия",
                "Скрытность", "Убеждение", "Уход за животными");

        this.inventoryRefusalMoney = "5d4";
    }
}
