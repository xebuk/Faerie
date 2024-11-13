package dnd.mainobjects;

import dnd.characteristics.*;
import dnd.equipment.ItemDnD;
import dnd.equipment.MagicItemDnD;
import dnd.values.*;
import dnd.dmtools.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerDnD implements Serializable {
    public String name;
    public RaceDnD race;
    public JobDnD mainJob;
    public BackgroundDnD background;
    public String alignment;

    public String playerName;
    public Long campaignChatId;

    public ArrayList<JobDnD> secondaryJobs = new ArrayList<>();
    public int experience = 0;
    public int totalLevel = 1;

    public int inspiration = 0;
    public int bonusMastery = 2;
    public String bonusMasteryTowards;

    public int armorClass;
    public ArmorTypeDnD armorProficiency;
    public int initiativeBonus;
    public int speed;
    public RacesSizeDnD size;

    public int healthPoints;
    public int maxHealthPoints;
    public int temporaryHealthPoints;
    public String healthDice;

    public int deathThrowsSuccess = 0;
    public int deathThrowsFailure = 0;

    public String specialBackgroundQuality = "Нет";
    public HashMap<String, String> personality = new HashMap<>();
    public HashMap<String, String> ideals = new HashMap<>();
    public HashMap<String, String> bonds = new HashMap<>();
    public HashMap<String, String> flaws = new HashMap<>();

    public HashSet<String> allocatedStats = new HashSet<>();
    public ArrayList<Integer> luck = new ArrayList<>();

    public int bonusSkills = 0;
    public HashSet<String> learnedSkills = new HashSet<>();
    public HashSet<SkillsDnD> learnedSkillsMastery = new HashSet<>();
    public HashSet<String> saveThrowMastery = new HashSet<>();

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

    public HashSet<String> advantages = new HashSet<>();
    public HashSet<String> disadvantages = new HashSet<>();

    public ArrayList<AbilityDnD> attacksAndSpells = new ArrayList<>();
    public ArrayList<MagicItemDnD> equipment = new ArrayList<>();
    public HashSet<String> proficiencies = new HashSet<>();
    public ArrayList<FeatDnD> traits = new ArrayList<>();
    public ArrayList<FeatDnD> feats = new ArrayList<>();

    public int bonusLanguages = 0;
    public HashSet<LanguagesDnD> knownLanguages = new HashSet<>();
    public HashSet<ScriptsDnD> knownScripts = new HashSet<>();

    public String age;
    public String height;
    public String weight;
    public String eyes;
    public String skin;
    public String hair;

    public HashMap<String, NonPlayerDnD> allies = new HashMap<>();
    public ArrayList<QuestDnDForPlayers> quests = new ArrayList<>();

    public HashMap<ItemDnD, Integer> itemCollection = new HashMap<>();
    public int valuables = 0;

    public HashMap<String, String> notes = new HashMap<>();

    public BookOfSpellsDnD spellBook;

    public PlayerDnD() {}

    public void initStartHealth() {
        this.healthPoints = mainJob.startingHealth + constitutionModifier;
        this.maxHealthPoints = healthPoints;
        this.healthDice = mainJob.healthDice;
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

    public void initBookOfSpellsDnD() {
        if (this.mainJob.usesMagic) {
            this.spellBook = new BookOfSpellsDnD();
        }
    }

    public void setAcrobaticsMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.ACROBATICS)) {
            this.learnedSkillsMastery.remove(SkillsDnD.ACROBATICS);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.ACROBATICS);
        }
        this.acrobatics = this.dexterityModifier;
    }

    public void setAnalysisMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.ANALYSIS)) {
            this.learnedSkillsMastery.remove(SkillsDnD.ANALYSIS);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.ANALYSIS);
        }
        this.analysis = this.intelligenceModifier;
    }

    public void setAthleticsMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.ATHLETICS)) {
            this.learnedSkillsMastery.remove(SkillsDnD.ATHLETICS);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.ATHLETICS);
        }
        this.athletics = this.strengthModifier;
    }

    public void setPerceptionMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.PERCEPTION)) {
            this.learnedSkillsMastery.remove(SkillsDnD.PERCEPTION);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.PERCEPTION);
        }
        this.perception = wisdomModifier;
        this.passivePerception = 10 + perception;
    }

    public void setSurvivalMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.SURVIVAL)) {
            this.learnedSkillsMastery.remove(SkillsDnD.SURVIVAL);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.SURVIVAL);
        }
        this.survival = wisdomModifier;
    }

    public void setPerformanceMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.PERFORMANCE)) {
            this.learnedSkillsMastery.remove(SkillsDnD.PERFORMANCE);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.PERFORMANCE);
        }
        this.performance = charismaModifier;
    }

    public void setIntimidationMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.INTIMIDATION)) {
            this.learnedSkillsMastery.remove(SkillsDnD.INTIMIDATION);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.INTIMIDATION);
        }
        this.intimidation = charismaModifier;
    }

    public void setHistoryMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.HISTORY)) {
            this.learnedSkillsMastery.remove(SkillsDnD.HISTORY);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.HISTORY);
        }
        this.history = intelligenceModifier;
    }

    public void setSleightOfHandMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.SLEIGHT_OF_HAND)) {
            this.learnedSkillsMastery.remove(SkillsDnD.SLEIGHT_OF_HAND);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.SLEIGHT_OF_HAND);
        }
        this.sleightOfHand = dexterityModifier;
    }

    public void setArcaneMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.ARCANE)) {
            this.learnedSkillsMastery.remove(SkillsDnD.ARCANE);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.ARCANE);
        }
        this.arcane = intelligenceModifier;
    }

    public void setMedicineMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.MEDICINE)) {
            this.learnedSkillsMastery.remove(SkillsDnD.MEDICINE);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.MEDICINE);
        }
        this.medicine = wisdomModifier;
    }

    public void setDeceptionMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.DECEPTION)) {
            this.learnedSkillsMastery.remove(SkillsDnD.DECEPTION);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.DECEPTION);
        }
        this.deception = charismaModifier;
    }

    public void setNatureMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.NATURE)) {
            this.learnedSkillsMastery.remove(SkillsDnD.NATURE);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.NATURE);
        }
        this.nature = intelligenceModifier;
    }

    public void setInsightMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.INSIGHT)) {
            this.learnedSkillsMastery.remove(SkillsDnD.INSIGHT);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.INSIGHT);
        }
        this.insight = wisdomModifier;
    }

    public void setReligionMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.RELIGION)) {
            this.learnedSkillsMastery.remove(SkillsDnD.RELIGION);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.RELIGION);
        }
        this.religion = intelligenceModifier;
    }

    public void setStealthMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.STEALTH)) {
            this.learnedSkillsMastery.remove(SkillsDnD.STEALTH);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.STEALTH);
        }
        this.stealth = dexterityModifier;
    }

    public void setPersuasionMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.PERSUASION)) {
            this.learnedSkillsMastery.remove(SkillsDnD.PERSUASION);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.PERSUASION);
        }
        this.persuasion = charismaModifier;
    }

    public void setAnimalHandlingMastery() {
        if (this.learnedSkillsMastery.contains(SkillsDnD.ANIMAL_HANDLING)) {
            this.learnedSkillsMastery.remove(SkillsDnD.ANIMAL_HANDLING);
        }
        else {
            this.learnedSkillsMastery.add(SkillsDnD.ANIMAL_HANDLING);
        }
        this.animalHandling = wisdomModifier;
    }

    public void learnGiantsLanguage() {
        this.knownLanguages.add(LanguagesDnD.GIANTS);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnGnomishLanguage() {
        this.knownLanguages.add(LanguagesDnD.GNOMISH);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnGoblinLanguage() {
        this.knownLanguages.add(LanguagesDnD.GOBLIN);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnDwarvishLanguage() {
        this.knownLanguages.add(LanguagesDnD.DWARWISH);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnCommonLanguage() {
        this.knownLanguages.add(LanguagesDnD.COMMON);
        this.knownScripts.add(ScriptsDnD.COMMON);
    }

    public void learnOrcishLanguage() {
        this.knownLanguages.add(LanguagesDnD.ORCISH);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnHalflingLanguage() {
        this.knownLanguages.add(LanguagesDnD.HALFLING);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnElvishLanguage() {
        this.knownLanguages.add(LanguagesDnD.ELVISH);
        this.knownScripts.add(ScriptsDnD.ELVISH);
    }

    public void learnAbyssalLanguage() {
        this.knownLanguages.add(LanguagesDnD.ABYSSAL);
        this.knownScripts.add(ScriptsDnD.INFERNAL);
    }

    public void learnCelestialLanguage() {
        this.knownLanguages.add(LanguagesDnD.CELESTIAL);
        this.knownScripts.add(ScriptsDnD.CELESTIAL);
    }

    public void learnDraconicLanguage() {
        this.knownLanguages.add(LanguagesDnD.DRACONIC);
        this.knownScripts.add(ScriptsDnD.DRACONIC);
    }

    public void learnDeepSpeech() {
        this.knownLanguages.add(LanguagesDnD.DEEP_SPEECH);
    }

    public void learnInfernalLanguage() {
        this.knownLanguages.add(LanguagesDnD.INFERNAL);
        this.knownScripts.add(ScriptsDnD.INFERNAL);
    }

    public void learnPrimordialLanguage() {
        this.knownLanguages.add(LanguagesDnD.PRIMORDIAL);
        this.knownScripts.add(ScriptsDnD.DWARVISH);
    }

    public void learnSylvanLanguage() {
        this.knownLanguages.add(LanguagesDnD.SYLVAN);
        this.knownScripts.add(ScriptsDnD.ELVISH);
    }

    public void learnUndercommonLanguage() {
        this.knownLanguages.add(LanguagesDnD.UNDERCOMMON);
        this.knownScripts.add(ScriptsDnD.ELVISH);
    }
}
