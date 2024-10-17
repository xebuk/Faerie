package dnd;

import dnd.characteristics.BackgroundDnD;
import dnd.characteristics.JobDnD;
import dnd.characteristics.RaceDnD;
import dnd.dmtools.*;
import dnd.characteristics.BookOfSpellsDnD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDnD implements Serializable {
    public String name;
    public ArrayList<JobDnD> jobs;
    public BackgroundDnD background;

    public String playerName;

    public RaceDnD race;
    public String alignment;
    public int experience;
    public ArrayList<Integer> level;
    public int totalLevel;

    public int inspiration;
    public int bonusMastery;

    public int armorClass;
    public int initiativeBonus;
    public int speed;

    public int healthPoints;
    public int maxHealthPoints;
    public int temporaryHealthPoints;
    public String healthDice;

    public int deathThrowsSuccess;
    public int deathThrowsFailure;

    public ArrayList<String> personality;
    public ArrayList<String> ideals;
    public ArrayList<String> bonds;
    public ArrayList<String> flaws;

    public int strength;
    public int strengthModifier;
    public int strengthSaveThrow;
    public int dexterity;
    public int dexterityModifier;
    public int dexteritySaveThrow;
    public int constitution;
    public int constitutionModifier;
    public int constitutionSaveThrow;
    public int intelligence;
    public int intelligenceModifier;
    public int intelligenceSaveThrow;
    public int wisdom;
    public int wisdomModifier;
    public int wisdomSaveThrow;
    public int charisma;
    public int charismaModifier;
    public int charismaSaveThrow;

    public int acrobatics;
    public int analysis;
    public int athletics;
    public int perception;
    public int survival;
    public int performance;
    public int intimidation;
    public int history;
    public int sleightOfHand;
    public int arcane;
    public int medicine;
    public int deception;
    public int nature;
    public int insight;
    public int religion;
    public int stealth;
    public int persuasion;
    public int animalHandling;

    public int passivePerception;

    public ArrayList<String> attacksAndSpells;
    public ArrayList<MagicItemDnD> equipment;
    public ArrayList<String> proficienciesAndLanguages;
    public ArrayList<String> traits;

    public String age;
    public String height;
    public String weight;
    public String eyes;
    public String skin;
    public String hair;

    public HashMap<String, String> allies;
    public ArrayList<String> features;
    public ArrayList<QuestDnDForPlayers> quests;
    public ArrayList<CommonItemDnD> itemCollection;

    public HashMap<String, String> notes;

    public BookOfSpellsDnD spellBook;

    public PlayerDnD() {}
}
