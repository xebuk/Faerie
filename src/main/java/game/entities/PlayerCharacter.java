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

    public PlayerCharacter(String name, String job, String race) {
        this.name = name;
        this.job = job;
        this.race = race;
    }
}
