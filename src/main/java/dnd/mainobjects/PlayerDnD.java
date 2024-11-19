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
import java.util.Set;

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
    public MasteryTypeDnD bonusMasteryTowards;

    public int armorClass;
    public HashSet<ArmorTypeDnD> armorProficiency = new HashSet<>();
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
    public HashSet<MasteryTypeDnD> characterMasteries = new HashSet<>();

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

    public int passivePerception = 10;

    public HashSet<MasteryTypeDnD> advantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> disadvantages = new HashSet<>();

    public ArrayList<AbilityDnD> attacksAndSpells = new ArrayList<>();
    public ArrayList<MagicItemDnD> equipment = new ArrayList<>();
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

    public String characterProfile() {
        StringBuilder profile = new StringBuilder();

        profile.append("Имя: ").append(this.name).append("\n");
        profile.append("Тег игрока: ").append(this.playerName).append("\n");
        profile.append("Раса: ").append(this.race.name).append("\n");
        profile.append("Подвид: ").append(this.race.subspeciesName).append("\n");
        profile.append("Предыстория: ").append(this.background.name).append("\n");
        profile.append("Мировоззрение: ").append(this.alignment).append("\n\n");

        profile.append("Основной класс: ").append(this.mainJob.title)
                .append(" - ").append(this.mainJob.level).append(" уровень").append("\n");
        profile.append("Дополнительные классы: ");
        if (this.secondaryJobs.isEmpty()) {
            profile.append("Нет\n");
        }
        else {
            profile.append("\n");
            for (JobDnD job : this.secondaryJobs) {
                profile.append(job.title).append(" - ").append(job.level).append(" уровень").append("\n");
            }
        }
        profile.append("\n");

        profile.append("Итоговый уровень: ").append(this.totalLevel).append("\n");
        profile.append("Опыт: ").append(this.experience).append("\n\n");

        profile.append("Характеристики: значение (модификатор) (бонус спас-броска)\n");
        profile.append("Сила: ").append(this.strength)
                .append("   (").append(this.strengthModifier)
                .append(")  -  (").append(this.strengthSaveThrow).append(")\n");
        profile.append("Ловкость: ").append(this.dexterity)
                .append("   (").append(this.dexterityModifier)
                .append(")  -  (").append(this.dexteritySaveThrow).append(")\n");
        profile.append("Выносливость: ").append(this.constitution)
                .append("   (").append(this.constitutionModifier)
                .append(")  -  (").append(this.constitutionSaveThrow).append(")\n");
        profile.append("Мудрость: ").append(this.wisdom)
                .append("   (").append(this.wisdomModifier)
                .append(")  -  (").append(this.wisdomSaveThrow).append(")\n");
        profile.append("Интеллект: ").append(this.intelligence)
                .append("   (").append(this.intelligenceModifier)
                .append(")  -  (").append(this.intelligenceSaveThrow).append(")\n");
        profile.append("Харизма: ").append(this.charisma)
                .append("   (").append(this.charismaModifier)
                .append(")  -  (").append(this.charismaSaveThrow).append(")\n\n");

        profile.append("Навыки: \n");
        profile.append("Акробатика: ").append(this.acrobatics).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.ACROBATICS) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Анализ: ").append(this.analysis).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.ANALYSIS) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Атлетика: ").append(this.athletics).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.ATHLETICS) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Восприятие: ").append(this.perception).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.PERCEPTION) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Выживание: ").append(this.survival).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.SURVIVAL) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Выступление: ").append(this.performance).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.PERFORMANCE) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Запугивание: ").append(this.intimidation).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.INTIMIDATION) ? "Освоено\n" : "Не освоено\n"));
        profile.append("История: ").append(this.history).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.HISTORY) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Ловкость рук: ").append(this.sleightOfHand).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.SLEIGHT_OF_HAND) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Магия: ").append(this.arcane).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.ARCANE) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Медицина: ").append(this.medicine).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.MEDICINE) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Обман: ").append(this.deception).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.DECEPTION) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Природа: ").append(this.nature).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.NATURE) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Проницательность: ").append(this.insight).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.INSIGHT) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Религия: ").append(this.religion).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.RELIGION) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Скрытность: ").append(this.stealth).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.STEALTH) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Убеждение: ").append(this.persuasion).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.PERSUASION) ? "Освоено\n" : "Не освоено\n"));
        profile.append("Уход за животными: ").append(this.animalHandling).append(" - ")
                .append((characterMasteries.contains(MasteryTypeDnD.ANIMAL_HANDLING) ? "Освоено\n" : "Не освоено\n"));
        profile.append("\n");

        profile.append("Пассивное восприятие: ").append(this.passivePerception).append("\n\n");

        profile.append("Преимущества: ");
        if (this.advantages.isEmpty()) {
            profile.append("Нет \n");
        }
        else {
            for (MasteryTypeDnD adv: this.advantages) {
                profile.append(adv);
            }
            profile.append("\n");
        }

        profile.append("Помехи: ");
        if (this.disadvantages.isEmpty()) {
            profile.append("Нет \n");
        }
        else {
            for (MasteryTypeDnD disadv: this.disadvantages) {
                profile.append(disadv);
            }
            profile.append("\n");
        }

        return profile.toString();
    }

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
        if (characterMasteries.contains(MasteryTypeDnD.STRENGTH)) {
            this.strengthSaveThrow = strengthModifier;
        }
        else {
            this.strengthSaveThrow = 0;
        }
    }

    public void initDexterity(int dexterity) {
        this.dexterity = dexterity + race.dexterityBonus;
        this.dexterityModifier = modifierSet(this.dexterity);
        if (characterMasteries.contains(MasteryTypeDnD.DEXTERITY)) {
            this.dexteritySaveThrow = dexterityModifier;
        }
        else {
            this.dexteritySaveThrow = 0;
        }
        this.initiativeBonus = dexterityModifier;
    }

    public void initConstitution(int constitution) {
        this.constitution = constitution + race.constitutionBonus;
        this.constitutionModifier = modifierSet(this.constitution);
        if (characterMasteries.contains(MasteryTypeDnD.CONSTITUTION)) {
            this.constitutionSaveThrow = constitutionModifier;
        }
        else {
            this.constitutionSaveThrow = 0;
        }
    }

    public void initIntelligence(int intelligence) {
        this.intelligence = intelligence + race.intelligenceBonus;
        this.intelligenceModifier = modifierSet(this.intelligence);
        if (characterMasteries.contains(MasteryTypeDnD.INTELLIGENCE)) {
            this.intelligenceSaveThrow = intelligenceModifier;
        }
        else {
            this.intelligenceSaveThrow = 0;
        }
    }

    public void initWisdom(int wisdom) {
        this.wisdom = wisdom + race.wisdomBonus;
        this.wisdomModifier = modifierSet(this.wisdom);
        if (characterMasteries.contains(MasteryTypeDnD.WISDOM)) {
            this.wisdomSaveThrow = wisdomModifier;
        }
        else {
            this.wisdomSaveThrow = 0;
        }
    }

    public void initCharisma(int charisma) {
        this.charisma = charisma + race.charismaBonus;
        this.charismaModifier = modifierSet(this.charisma);
        if (characterMasteries.contains(MasteryTypeDnD.CHARISMA)) {
            this.charismaSaveThrow = charismaModifier;
        }
        else {
            this.charismaSaveThrow = 0;
        }
    }

    public void initBookOfSpellsDnD() {
        if (this.mainJob.usesMagic) {
            this.spellBook = new BookOfSpellsDnD();
        }
    }

    public void setAcrobaticsMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.ACROBATICS)) {
            this.characterMasteries.remove(MasteryTypeDnD.ACROBATICS);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.ACROBATICS);
        }
        this.acrobatics = this.dexterityModifier;
    }

    public void setAnalysisMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.ANALYSIS)) {
            this.characterMasteries.remove(MasteryTypeDnD.ANALYSIS);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.ANALYSIS);
        }
        this.analysis = this.intelligenceModifier;
    }

    public void setAthleticsMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.ATHLETICS)) {
            this.characterMasteries.remove(MasteryTypeDnD.ATHLETICS);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.ATHLETICS);
        }
        this.athletics = this.strengthModifier;
    }

    public void setPerceptionMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.PERCEPTION)) {
            this.characterMasteries.remove(MasteryTypeDnD.PERCEPTION);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.PERCEPTION);
        }
        this.perception = wisdomModifier;
        this.passivePerception = 10 + perception;
    }

    public void setSurvivalMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.SURVIVAL)) {
            this.characterMasteries.remove(MasteryTypeDnD.SURVIVAL);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.SURVIVAL);
        }
        this.survival = wisdomModifier;
    }

    public void setPerformanceMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.PERFORMANCE)) {
            this.characterMasteries.remove(MasteryTypeDnD.PERFORMANCE);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.PERFORMANCE);
        }
        this.performance = charismaModifier;
    }

    public void setIntimidationMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.INTIMIDATION)) {
            this.characterMasteries.remove(MasteryTypeDnD.INTIMIDATION);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.INTIMIDATION);
        }
        this.intimidation = charismaModifier;
    }

    public void setHistoryMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.HISTORY)) {
            this.characterMasteries.remove(MasteryTypeDnD.HISTORY);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.HISTORY);
        }
        this.history = intelligenceModifier;
    }

    public void setSleightOfHandMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.SLEIGHT_OF_HAND)) {
            this.characterMasteries.remove(MasteryTypeDnD.SLEIGHT_OF_HAND);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.SLEIGHT_OF_HAND);
        }
        this.sleightOfHand = dexterityModifier;
    }

    public void setArcaneMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.ARCANE)) {
            this.characterMasteries.remove(MasteryTypeDnD.ARCANE);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.ARCANE);
        }
        this.arcane = intelligenceModifier;
    }

    public void setMedicineMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.MEDICINE)) {
            this.characterMasteries.remove(MasteryTypeDnD.MEDICINE);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.MEDICINE);
        }
        this.medicine = wisdomModifier;
    }

    public void setDeceptionMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.DECEPTION)) {
            this.characterMasteries.remove(MasteryTypeDnD.DECEPTION);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.DECEPTION);
        }
        this.deception = charismaModifier;
    }

    public void setNatureMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.NATURE)) {
            this.characterMasteries.remove(MasteryTypeDnD.NATURE);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.NATURE);
        }
        this.nature = intelligenceModifier;
    }

    public void setInsightMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.INSIGHT)) {
            this.characterMasteries.remove(MasteryTypeDnD.INSIGHT);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.INSIGHT);
        }
        this.insight = wisdomModifier;
    }

    public void setReligionMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.RELIGION)) {
            this.characterMasteries.remove(MasteryTypeDnD.RELIGION);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.RELIGION);
        }
        this.religion = intelligenceModifier;
    }

    public void setStealthMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.STEALTH)) {
            this.characterMasteries.remove(MasteryTypeDnD.STEALTH);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.STEALTH);
        }
        this.stealth = dexterityModifier;
    }

    public void setPersuasionMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.PERSUASION)) {
            this.characterMasteries.remove(MasteryTypeDnD.PERSUASION);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.PERSUASION);
        }
        this.persuasion = charismaModifier;
    }

    public void setAnimalHandlingMastery() {
        if (this.characterMasteries.contains(MasteryTypeDnD.ANIMAL_HANDLING)) {
            this.characterMasteries.remove(MasteryTypeDnD.ANIMAL_HANDLING);
        }
        else {
            this.characterMasteries.add(MasteryTypeDnD.ANIMAL_HANDLING);
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
