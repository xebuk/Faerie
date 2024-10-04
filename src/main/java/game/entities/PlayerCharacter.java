package game.entities;

import game.characteristics.Job;

import java.io.Serializable;

public class PlayerCharacter implements Serializable {
    public String name;
    public Job job;
    public String race;

    public int health;
    public int armorClass;
    public String attackPower;

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

    public PlayerCharacter() {}

    public PlayerCharacter(String name, Job job, String race) {
        this.name = name;
        this.job = job;
        this.race = race;
    }

    public static int modifierSet(int stat) {
        return (-5 + (stat / 2));
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setHealth() {
        this.health = job.startHp + constitutionModifier;
    }

    public void setArmorClass() {
        this.armorClass = job.startArmorClass;
    }

    public void setAttackPower() {
        this.attackPower = job.startAttackRoll;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        this.strengthModifier = modifierSet(strength);
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
        this.dexterityModifier = modifierSet(dexterity);
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
        this.constitutionModifier = modifierSet(constitution);
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        this.intelligenceModifier = modifierSet(intelligence);
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
        this.wisdomModifier = modifierSet(wisdom);
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
        this.charismaModifier = modifierSet(charisma);
    }
}
