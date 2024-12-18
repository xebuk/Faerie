package game.entities;

import game.characteristics.Job;
import game.characteristics.Race;
import game.characteristics.races.Human;
import game.items.Artefact;

import java.io.Serializable;
import java.util.Stack;

public class PlayerCharacter implements Serializable {
    public String name;
    public Job job;
    public Race race;

    public int health;
    public int armorClass;
    public String attackDice;

    public int strength;
    public int strengthModifier;
    public int dexterity;
    public int dexterityModifier;
    public int constitution;
    public int constitutionModifier;
    public int intelligence;
    public int intelligenceModifier;
    public int wisdom;
    public int wisdomModifier;
    public int charisma;
    public int charismaModifier;

    public Stack<Artefact> inventory = new Stack<>();

    public PlayerCharacter() {
        this.name = "Терен";
        this.job = null;
        this.race = new Human();

        this.health = 1;
        this.armorClass = 1;
        this.attackDice = "1d4";
    }

    public static int modifierSet(int stat) {
        return (-5 + (stat / 2));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void initHealth() {
        this.health = job.startHealth + constitutionModifier;
    }

    public void setArmorClass() {
        this.armorClass = job.armorType.armorValue;
    }

    public void setAttackDice() {
        this.attackDice = job.weaponType.attackDice;
    }

    public void initStrength(int strength) {
        this.strength = strength;
        this.strengthModifier = modifierSet(strength);
    }

    public void initDexterity(int dexterity) {
        this.dexterity = dexterity;
        this.dexterityModifier = modifierSet(dexterity);
    }

    public void initConstitution(int constitution) {
        this.constitution = constitution;
        this.constitutionModifier = modifierSet(constitution);
    }

    public void initIntelligence(int intelligence) {
        this.intelligence = intelligence;
        this.intelligenceModifier = modifierSet(intelligence);
    }

    public void initWisdom(int wisdom) {
        this.wisdom = wisdom;
        this.wisdomModifier = modifierSet(wisdom);
    }

    public void initCharisma(int charisma) {
        this.charisma = charisma;
        this.charismaModifier = modifierSet(charisma);
    }

    public String statWindow() {
        StringBuilder windowText = new StringBuilder();

        windowText.append("Окно персонажа").append("\n").append("\n");

        windowText.append("Имя: ").append(name).append("\n");
        windowText.append("Раса: ").append(race.raceName).append("\n");
        windowText.append("Класс: ").append(job.jobName).append("\n").append("\n");

        windowText.append("Максимальное здоровье: ").append(health).append("\n");
        windowText.append("Класс брони: ").append(armorClass).append("\n");
        windowText.append("Кубики атаки: ").append(attackDice).append("\n").append("\n");

        windowText.append("Ваша статистика: ").append("\n");
        windowText.append("Сила - ").append(strength).append(" (").append(strengthModifier > 0 ? "+" + strengthModifier : strengthModifier).append(")").append("\n");
        windowText.append("Ловкость - ").append(dexterity).append(" (").append(dexterityModifier > 0 ? "+" + dexterityModifier : dexterityModifier).append(")").append("\n");
        windowText.append("Выносливость - ").append(constitution).append(" (").append(constitutionModifier > 0 ? "+" + constitutionModifier : constitutionModifier).append(")").append("\n");
        windowText.append("Интеллект - ").append(intelligence).append(" (").append(intelligenceModifier > 0 ? "+" + intelligenceModifier : intelligenceModifier).append(")").append("\n");
        windowText.append("Мудрость - ").append(wisdom).append(" (").append(wisdomModifier > 0 ? "+" + wisdomModifier : wisdomModifier).append(")").append("\n");
        windowText.append("Харизма - ").append(charisma).append(" (").append(charismaModifier > 0 ? "+" + charismaModifier : charismaModifier).append(")").append("\n").append("\n");

        return windowText.toString();
    }

    public void getItem(Artefact item) {
        initStrength(this.strength + item.strengthValue);
        initDexterity(this.dexterity + item.dexterityValue);
        initConstitution(this.constitution + item.constitutionValue);
        initIntelligence(this.intelligence + item.intelligenceValue);
        initWisdom(this.wisdom + item.wisdomValue);
        initCharisma(this.charisma + item.charismaValue);

        this.inventory.push(item);
    }
}
