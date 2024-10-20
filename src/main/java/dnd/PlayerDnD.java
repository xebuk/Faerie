package dnd;

import dnd.characteristics.*;
import dnd.dmtools.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerDnD implements Serializable {
    public String name;
    public ArrayList<JobDnD> jobs;
    public BackgroundDnD background;

    public String playerName;
    public Long campaignChatId;

    public RaceDnD race;
    public String alignment;
    public int experience = 0;
    public int totalLevel = 1;

    public int inspiration = 0;
    public int bonusMastery = 2;
    public String bonusMasteryTowards;

    public int armorClass;
    public int initiativeBonus;
    public int speed;

    public int healthPoints;
    public int maxHealthPoints;
    public int temporaryHealthPoints;
    public String healthDice;

    public int deathThrowsSuccess = 0;
    public int deathThrowsFailure = 0;

    public HashMap<String, String> personality;
    public HashMap<String, String> ideals;
    public HashMap<String, String> bonds;
    public HashMap<String, String> flaws;

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
    public boolean acrobaticsMastery = false;
    public int analysis;
    public boolean analysisMastery = false;
    public int athletics;
    public boolean athleticsMastery = false;
    public int perception;
    public boolean perceptionMastery = false;
    public int survival;
    public boolean survivalMastery = false;
    public int performance;
    public boolean performanceMastery = false;
    public int intimidation;
    public boolean intimidationMastery = false;
    public int history;
    public boolean historyMastery = false;
    public int sleightOfHand;
    public boolean sleightOfHandMastery = false;
    public int arcane;
    public boolean arcaneMastery = false;
    public int medicine;
    public boolean medicineMastery = false;
    public int deception;
    public boolean deceptionMastery = false;
    public int nature;
    public boolean natureMastery = false;
    public int insight;
    public boolean insightMastery = false;
    public int religion;
    public boolean religionMastery = false;
    public int stealth;
    public boolean stealthMastery = false;
    public int persuasion;
    public boolean persuasionMastery = false;
    public int animalHandling;
    public boolean animalHandlingMastery = false;

    public int passivePerception;

    public HashSet<String> learnedSkills = new HashSet<>();
    public HashSet<String> advantages = new HashSet<>();

    public ArrayList<AbilityDnD> attacksAndSpells;
    public ArrayList<MagicItemDnD> equipment;
    public ArrayList<String> proficienciesAndLanguages;
    public ArrayList<FeatDnD> traits;
    public ArrayList<FeatDnD> feats;

    public String age;
    public String height;
    public String weight;
    public String eyes;
    public String skin;
    public String hair;

    public HashMap<String, NonPlayerDnD> allies;
    public ArrayList<QuestDnDForPlayers> quests;
    public ArrayList<CommonItemDnD> itemCollection;

    public HashMap<String, String> notes;

    public BookOfSpellsDnD spellBook;

    public PlayerDnD() {}

    public void initStartHealth() {
        this.healthPoints = jobs.get(0).startingHealth + constitutionModifier;
        this.maxHealthPoints = healthPoints;
        this.healthDice = jobs.get(0).healthDice;
        this.temporaryHealthPoints = 0;
    }

    public static int modifierSet(int stat) {
        return (-5 + (stat / 2));
    }

    public void initStrength(int strength) {
        this.strength = strength + race.strengthBonus;
        this.strengthModifier = modifierSet(this.strength);
        this.strengthSaveThrow = strengthModifier;
    }

    public void initDexterity(int dexterity) {
        this.dexterity = dexterity + race.dexterityBonus;
        this.dexterityModifier = modifierSet(this.dexterity);
        this.dexteritySaveThrow = dexterityModifier;
        this.initiativeBonus = dexterityModifier;
    }

    public void initConstitution(int constitution) {
        this.constitution = constitution + race.constitutionBonus;
        this.constitutionModifier = modifierSet(this.constitution);
        this.constitutionSaveThrow = constitutionModifier;
    }

    public void initIntelligence(int intelligence) {
        this.intelligence = intelligence + race.intelligenceBonus;
        this.intelligenceModifier = modifierSet(this.intelligence);
        this.intelligenceSaveThrow = intelligenceModifier;
    }

    public void initWisdom(int wisdom) {
        this.wisdom = wisdom + race.wisdomBonus;
        this.wisdomModifier = modifierSet(this.wisdom);
        this.wisdomSaveThrow = wisdomModifier;
    }

    public void initCharisma(int charisma) {
        this.charisma = charisma + race.charismaBonus;
        this.charismaModifier = modifierSet(this.charisma);
        this.wisdomSaveThrow = charismaSaveThrow;
    }

    public void initSpeed() {
        this.speed = race.walkingSpeed;
    }

    public void initBookOfSpellsDnD() {
        if (this.jobs.get(0).usesMagic) {
            this.spellBook = new BookOfSpellsDnD();
        }
    }

    public void setAcrobaticsMastery() {
        this.acrobaticsMastery = !this.acrobaticsMastery;
        this.acrobatics = this.dexterityModifier;
    }

    public void setAnalysisMastery() {
        this.analysisMastery = !this.analysisMastery;
        this.analysis = this.intelligenceModifier;
    }

    public void setAthleticsMastery() {
        this.athleticsMastery = !this.athleticsMastery;
        this.athletics = this.strengthModifier;
    }

    public void setPerceptionMastery() {
        this.perceptionMastery = !this.perceptionMastery;
        this.perception = wisdomModifier;
        this.passivePerception = 10 + perception;
    }

    public void setSurvivalMastery() {
        this.survivalMastery = !this.survivalMastery;
        this.survival = wisdomModifier;
    }

    public void setPerformanceMastery() {
        this.performanceMastery = !this.performanceMastery;
        this.performance = charismaModifier;
    }

    public void setIntimidationMastery() {
        this.intimidationMastery = !this.intimidationMastery;
        this.intimidation = charismaModifier;
    }

    public void setHistoryMastery() {
        this.historyMastery = !this.historyMastery;
        this.history = intelligenceModifier;
    }

    public void setSleightOfHandMastery() {
        this.sleightOfHandMastery = !this.sleightOfHandMastery;
        this.sleightOfHand = dexterityModifier;
    }

    public void setArcaneMastery() {
        this.arcaneMastery = !this.arcaneMastery;
        this.arcane = intelligenceModifier;
    }

    public void setMedicineMastery() {
        this.medicineMastery = !this.medicineMastery;
        this.medicine = wisdomModifier;
    }

    public void setDeceptionMastery() {
        this.deceptionMastery = !this.deceptionMastery;
        this.deception = charismaModifier;
    }

    public void setNatureMastery() {
        this.natureMastery = !this.natureMastery;
        this.nature = intelligenceModifier;
    }

    public void setInsightMastery() {
        this.insightMastery = !this.insightMastery;
        this.insight = wisdomModifier;
    }

    public void setReligionMastery() {
        this.religionMastery = !this.religionMastery;
        this.religion = intelligenceModifier;
    }

    public void setStealthMastery() {
        this.stealthMastery = !this.stealthMastery;
        this.stealth = dexterityModifier;
    }

    public void setPersuasionMastery() {
        this.persuasionMastery = !this.persuasionMastery;
        this.persuasion = charismaModifier;
    }

    public void setAnimalHandlingMastery() {
        this.animalHandlingMastery = !this.animalHandlingMastery;
        this.animalHandling = wisdomModifier;
    }
}
