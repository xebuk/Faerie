package dnd.mainobjects;

import dnd.characteristics.*;
import dnd.dmtools.NoteDnD;
import dnd.equipment.*;
import dnd.equipment.InstrumentsDnD;
import dnd.values.characteristicsvalues.BackgroundsDnD;
import dnd.values.characteristicsvalues.JobsDnD;
import dnd.values.characteristicsvalues.RacesDnD;
import dnd.values.characteristicsvalues.RacesSizeDnD;
import dnd.values.equipmentids.ArmorsDnD;
import dnd.values.equipmentids.WeaponsDnD;
import dnd.values.masteryvalues.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerDnD implements Serializable {
    public String name;
    public String playerName;
    public Long campaignChatId;

    public RacesDnD race;
    public String raceName;
    public String raceSubspeciesName;
    public int walkingSpeed;
    public RacesSizeDnD size;

    public JobsDnD mainJob;
    public String mainJobTitle;
    public String mainPrestigeJobTitle;
    public int mainJobLevel = 1;

    public ArrayList<JobsDnD> secondaryJobs = new ArrayList<>();
    public ArrayList<String> secondaryJobsTitles = new ArrayList<>();
    public ArrayList<String> secondaryJobsPrestigeTitles = new ArrayList<>();
    public ArrayList<Integer> secondaryJobsLevels = new ArrayList<>();

    public BackgroundsDnD background;
    public String backgroundName;
    public String specialAbility;
    public String specialAbilitySummary;
    public String specialBackgroundQuality = "Нет";

    public HashMap<String, String> personality = new HashMap<>();
    public HashMap<String, String> ideals = new HashMap<>();
    public HashMap<String, String> bonds = new HashMap<>();
    public HashMap<String, String> flaws = new HashMap<>();

    public String alignment;

    public int experience = 0;
    public int totalLevel = 1;

    public int inspiration = 0;
    public int bonusMastery = 2;
    public MasteryTypeDnD bonusMasteryTowards;

    public int initiativeBonus;
    public int speed;

    public int healthPoints;
    public int maxHealthPoints;
    public int temporaryHealthPoints;
    public String healthDice;

    public int deathThrowsSuccess = 0;
    public int deathThrowsFailure = 0;

    //технические параметры для расчета статов персонажа, их никто не должен видеть
    public HashSet<String> allocatedStats = new HashSet<>();
    public ArrayList<Integer> luck = new ArrayList<>();
    //технические параметры для расчета статов персонажа, их никто не должен видеть

    //технические параметры для бросков, их никто не должен видеть
    public String diceComb = "";
    public MasteryTypeDnD statCheck;
    public AdvantageTypeDnD advantage = AdvantageTypeDnD.CLEAR_THROW;
    public MasteryTypeDnD specialCase = MasteryTypeDnD.NONE;
    public boolean inPublic = true;
    //технические параметры для бросков, их никто не должен видеть

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

    public int freeSkillPoints = 0;
    public HashSet<String> learnedSkills = new HashSet<>();
    public HashSet<MasteryTypeDnD> characterMasteries = new HashSet<>();

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

    public HashSet<MasteryTypeDnD> personalAdvantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> personalDisadvantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> externalAdvantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> externalDisadvantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> totalAdvantages = new HashSet<>();
    public HashSet<MasteryTypeDnD> totalDisadvantages = new HashSet<>();

    public ArrayList<AbilityDnD> abilities = new ArrayList<>();
    public ArrayList<FeatDnD> traits = new ArrayList<>();
    public ArrayList<FeatDnD> feats = new ArrayList<>();

    public WeaponDnD equippedWeapon;
    public int hitBonus = 0;
    public String attackDice = "1d4";
    public DamageTypeDnD damageType = DamageTypeDnD.BLUDGEONING;
    public int equippedWeaponIndex = 0;
    public String weaponEffects = "Нет";
    public HashSet<WeaponsDnD> weaponProficiency = new HashSet<>();

    public ArmorDnD equippedArmor;
    public int equippedArmorIndex = 0;
    public int armorClass = 10;
    public String armorEffects = "Нет";
    public HashSet<ArmorsDnD> armorProficiency = new HashSet<>();

    public ArrayList<ItemDnD> attunedAccessories = new ArrayList<>();

    public int bonusLanguages = 0;
    public HashSet<LanguagesDnD> knownLanguages = new HashSet<>();
    public HashSet<ScriptsDnD> knownScripts = new HashSet<>();

    public String age;
    public String height;
    public String weight;
    public String eyes;
    public String skin;
    public String hair;

    public double currentCarryingCapacity = 0;
    public double maxCarryingCapacity;

    public ArrayList<ItemDnD> itemCollectionOnHands = new ArrayList<>();
    public ArrayList<WeaponDnD> weaponCollectionOnHands = new ArrayList<>();
    public ArrayList<ArmorDnD> armorCollectionOnHands = new ArrayList<>();
    public ArrayList<InstrumentsDnD> instrumentsCollectionOnHands = new ArrayList<>();
    public ArrayList<KitDnD> kitCollectionOnHands = new ArrayList<>();

    public int gold = 0;
    public int silver = 0;
    public int copper = 0;

    public ArrayList<ItemDnD> itemCollection = new ArrayList<>();
    public ArrayList<WeaponDnD> weaponCollection = new ArrayList<>();
    public ArrayList<ArmorDnD> armorCollection = new ArrayList<>();
    public ArrayList<InstrumentsDnD> instrumentsCollection = new ArrayList<>();
    public ArrayList<KitDnD> kitCollection = new ArrayList<>();

    public ArrayList<NoteDnD> notes = new ArrayList<>();

    public BookOfSpellsDnD spellBook;

    public PlayerDnD() {}

    public String characterProfile() {
        StringBuilder profile = new StringBuilder();

        profile.append("Имя: ").append(this.name).append("\n");
        profile.append("Тег игрока: ").append(this.playerName).append("\n");
        profile.append("Раса: ").append(this.raceName).append("\n");
        profile.append("Подвид: ").append(this.raceSubspeciesName).append("\n");
        profile.append("Предыстория: ").append(this.backgroundName).append("\n");
        profile.append("Мировоззрение: ").append(this.alignment).append("\n\n");

        profile.append("Основной класс: ").append(this.mainJobTitle)
                .append(" - ").append(this.mainJobLevel).append(" уровень").append("\n");
        profile.append("Дополнительные классы: ");
        if (this.secondaryJobsTitles.isEmpty()) {
            profile.append("Нет.\n");
        }
        else {
            profile.append("\n");
            for (int i = 0; i < secondaryJobsTitles.size(); i++) {
                profile.append(secondaryJobsTitles.get(i)).append("\n");
                profile.append("Уровень: ").append(secondaryJobsLevels.get(i)).append("\n");
                profile.append("Подкласс: ").append(secondaryJobsPrestigeTitles.get(i)).append("\n");
            }
        }
        profile.append("\n");

        profile.append("Итоговый уровень: ").append(this.totalLevel).append("\n");
        profile.append("Опыт: ").append(this.experience).append("\n\n");

        profile.append("Характеристики: значение (модификатор) (бонус спас-броска)\n");
        profile.append("Сила: ").append(this.strength)
                .append("   (").append(this.strengthModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.STRENGTH)
                        ? this.strengthSaveThrow : "Не освоено")).append(")\n");
        profile.append("Ловкость: ").append(this.dexterity)
                .append("   (").append(this.dexterityModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.DEXTERITY)
                        ? this.dexteritySaveThrow : "Не освоено")).append(")\n");
        profile.append("Выносливость: ").append(this.constitution)
                .append("   (").append(this.constitutionModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.CONSTITUTION)
                        ? this.constitutionSaveThrow : "Не освоено")).append(")\n");
        profile.append("Мудрость: ").append(this.wisdom)
                .append("   (").append(this.wisdomModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.WISDOM)
                        ? this.wisdomSaveThrow : "Не освоено")).append(")\n");
        profile.append("Интеллект: ").append(this.intelligence)
                .append("   (").append(this.intelligenceModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.INTELLIGENCE)
                        ? this.intelligenceSaveThrow : "Не освоено")).append(")\n");
        profile.append("Харизма: ").append(this.charisma)
                .append("   (").append(this.charismaModifier)
                .append(")  -  (").append((characterMasteries.contains(MasteryTypeDnD.CHARISMA)
                        ? this.charismaSaveThrow : "Не освоено")).append(")\n\n");

        profile.append("Скорость ходьбы: ").append(this.walkingSpeed).append("\n\n");

        profile.append("Навыки: \n");
        profile.append("Акробатика: ").append((characterMasteries.contains(MasteryTypeDnD.ACROBATICS)
                ? this.acrobatics : "Не освоено")).append("\n");
        profile.append("Анализ: ").append((characterMasteries.contains(MasteryTypeDnD.ANALYSIS)
                ? this.analysis : "Не освоено")).append("\n");
        profile.append("Атлетика: ").append((characterMasteries.contains(MasteryTypeDnD.ATHLETICS)
                ? this.athletics : "Не освоено")).append("\n");
        profile.append("Восприятие: ").append((characterMasteries.contains(MasteryTypeDnD.PERCEPTION)
                ? this.perception : "Не освоено")).append("\n");
        profile.append("Выживание: ").append((characterMasteries.contains(MasteryTypeDnD.SURVIVAL)
                ? this.survival : "Не освоено")).append("\n");
        profile.append("Выступление: ").append((characterMasteries.contains(MasteryTypeDnD.PERFORMANCE)
                ? this.performance : "Не освоено")).append("\n");
        profile.append("Запугивание: ").append((characterMasteries.contains(MasteryTypeDnD.INTIMIDATION)
                ? this.intimidation : "Не освоено")).append("\n");
        profile.append("История: ").append((characterMasteries.contains(MasteryTypeDnD.HISTORY)
                ? this.history : "Не освоено")).append("\n");
        profile.append("Ловкость рук: ").append((characterMasteries.contains(MasteryTypeDnD.SLEIGHT_OF_HAND)
                ? this.sleightOfHand : "Не освоено")).append("\n");
        profile.append("Магия: ").append((characterMasteries.contains(MasteryTypeDnD.ARCANE)
                ? this.arcane : "Не освоено")).append("\n");
        profile.append("Медицина: ").append((characterMasteries.contains(MasteryTypeDnD.MEDICINE)
                ? this.medicine : "Не освоено")).append("\n");
        profile.append("Обман: ").append((characterMasteries.contains(MasteryTypeDnD.DECEPTION)
                ? this.deception : "Не освоено")).append("\n");
        profile.append("Природа: ").append((characterMasteries.contains(MasteryTypeDnD.NATURE)
                ? this.nature : "Не освоено")).append("\n");
        profile.append("Проницательность: ").append((characterMasteries.contains(MasteryTypeDnD.INSIGHT)
                ? this.insight : "Не освоено")).append("\n");
        profile.append("Религия: ").append((characterMasteries.contains(MasteryTypeDnD.RELIGION)
                ? this.religion : "Не освоено")).append("\n");
        profile.append("Скрытность: ").append((characterMasteries.contains(MasteryTypeDnD.STEALTH)
                ? this.stealth : "Не освоено")).append("\n");
        profile.append("Убеждение: ").append((characterMasteries.contains(MasteryTypeDnD.PERSUASION)
                ? this.persuasion : "Не освоено")).append("\n");
        profile.append("Уход за животными: ").append((characterMasteries.contains(MasteryTypeDnD.ANIMAL_HANDLING)
                ? this.animalHandling : "Не освоено")).append("\n\n");

        profile.append("Пассивное восприятие: ").append(this.passivePerception).append("\n\n");

        profile.append("Преимущества: ");
        if (this.personalAdvantages.isEmpty()) {
            profile.append("Нет.");
        }
        else {
            for (MasteryTypeDnD adv: this.personalAdvantages) {
                profile.append(adv.toString()).append(", ");
            }
            profile.setLength(profile.length() - 2);
        }
        profile.append("\n");

        profile.append("Помехи: ");
        if (this.personalDisadvantages.isEmpty()) {
            profile.append("Нет.");
        }
        else {
            for (MasteryTypeDnD disadv: this.personalDisadvantages) {
                profile.append(disadv.toString()).append(", ");
            }
            profile.setLength(profile.length() - 2);
        }
        profile.append("\n");




        return profile.toString();
    }

    public void initStartHealth() {
        this.healthPoints = JobsDnD.getJobAsClass(this.mainJob).startingHealth + constitutionModifier;
        this.maxHealthPoints = healthPoints;
        this.healthDice = JobsDnD.getJobAsClass(this.mainJob).healthDice;
        this.temporaryHealthPoints = 0;
    }

    public static int modifierSet(int stat) {
        return (-5 + (stat / 2));
    }

    public void initStrength(int strength) {
        this.strength = strength + RacesDnD.getRaceAsClass(this.race).strengthBonus;
        this.strengthModifier = modifierSet(this.strength);
        if (characterMasteries.contains(MasteryTypeDnD.STRENGTH)) {
            this.strengthSaveThrow = strengthModifier;
        }
        else {
            this.strengthSaveThrow = 0;
        }
        this.maxCarryingCapacity = this.strength * 15;
    }

    public void initDexterity(int dexterity) {
        this.dexterity = dexterity + RacesDnD.getRaceAsClass(this.race).dexterityBonus;
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
        this.constitution = constitution + RacesDnD.getRaceAsClass(this.race).constitutionBonus;
        this.constitutionModifier = modifierSet(this.constitution);
        if (characterMasteries.contains(MasteryTypeDnD.CONSTITUTION)) {
            this.constitutionSaveThrow = constitutionModifier;
        }
        else {
            this.constitutionSaveThrow = 0;
        }
    }

    public void initIntelligence(int intelligence) {
        this.intelligence = intelligence + RacesDnD.getRaceAsClass(this.race).intelligenceBonus;
        this.intelligenceModifier = modifierSet(this.intelligence);
        if (characterMasteries.contains(MasteryTypeDnD.INTELLIGENCE)) {
            this.intelligenceSaveThrow = intelligenceModifier;
        }
        else {
            this.intelligenceSaveThrow = 0;
        }
    }

    public void initWisdom(int wisdom) {
        this.wisdom = wisdom + RacesDnD.getRaceAsClass(this.race).wisdomBonus;
        this.wisdomModifier = modifierSet(this.wisdom);
        if (characterMasteries.contains(MasteryTypeDnD.WISDOM)) {
            this.wisdomSaveThrow = wisdomModifier;
        }
        else {
            this.wisdomSaveThrow = 0;
        }
    }

    public void initCharisma(int charisma) {
        this.charisma = charisma + RacesDnD.getRaceAsClass(this.race).charismaBonus;
        this.charismaModifier = modifierSet(this.charisma);
        if (characterMasteries.contains(MasteryTypeDnD.CHARISMA)) {
            this.charismaSaveThrow = charismaModifier;
        }
        else {
            this.charismaSaveThrow = 0;
        }
    }

    public void initBookOfSpellsDnD() {
        if (JobsDnD.getJobAsClass(this.mainJob).usesMagic) {
            this.spellBook = new BookOfSpellsDnD();
        }
    }

    public void setStrength(int strength) {
        this.strength = strength;
        this.strengthModifier = modifierSet(this.strength);
        if (characterMasteries.contains(MasteryTypeDnD.STRENGTH)) {
            this.strengthSaveThrow = strengthModifier;
        }
        else {
            this.strengthSaveThrow = 0;
        }
        this.maxCarryingCapacity = this.strength * 15;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
        this.dexterityModifier = modifierSet(this.dexterity);
        if (characterMasteries.contains(MasteryTypeDnD.DEXTERITY)) {
            this.dexteritySaveThrow = dexterityModifier;
        }
        else {
            this.dexteritySaveThrow = 0;
        }
        this.initiativeBonus = dexterityModifier;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
        this.constitutionModifier = modifierSet(this.constitution);
        if (characterMasteries.contains(MasteryTypeDnD.CONSTITUTION)) {
            this.constitutionSaveThrow = constitutionModifier;
        }
        else {
            this.constitutionSaveThrow = 0;
        }
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
        this.intelligenceModifier = modifierSet(this.intelligence);
        if (characterMasteries.contains(MasteryTypeDnD.INTELLIGENCE)) {
            this.intelligenceSaveThrow = intelligenceModifier;
        }
        else {
            this.intelligenceSaveThrow = 0;
        }
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
        this.wisdomModifier = modifierSet(this.wisdom);
        if (characterMasteries.contains(MasteryTypeDnD.WISDOM)) {
            this.wisdomSaveThrow = wisdomModifier;
        }
        else {
            this.wisdomSaveThrow = 0;
        }
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
        this.charismaModifier = modifierSet(this.charisma);
        if (characterMasteries.contains(MasteryTypeDnD.CHARISMA)) {
            this.charismaSaveThrow = charismaModifier;
        }
        else {
            this.charismaSaveThrow = 0;
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
