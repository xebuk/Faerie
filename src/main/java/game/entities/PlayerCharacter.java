package game.entities;

import game.characteristics.Jobs;
import game.characteristics.Races;

import java.io.Serializable;

public class PlayerCharacter implements Serializable {
    public String name;
    public String job;
    public String race;

    public int health;
    public int armorClass;
    public int attackPower;

    public int strength;
    public int dexterity;
    public int constitution;
    public int intelligence;
    public int wisdom;
    public int charisma;

    public PlayerCharacter() {}

    public PlayerCharacter(String name, String job, String race) {
        this.name = name;
        this.job = job;
        this.race = race;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
}
