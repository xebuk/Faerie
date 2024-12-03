package dnd.values.masteryvalues;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public enum MasteryTypeDnD implements Serializable {
    NONE,
    LUCK,
    SAVE,
    DEATH_SAVE,
    PRECISION,
    DAMAGE,

    STRENGTH,
    DEXTERITY,
    CONSTITUTION,
    INTELLIGENCE,
    WISDOM,
    CHARISMA,

    ACROBATICS,
    ANALYSIS,
    ATHLETICS,
    PERCEPTION,
    SURVIVAL,
    PERFORMANCE,
    INTIMIDATION,
    HISTORY,
    SLEIGHT_OF_HAND,
    ARCANE,
    MEDICINE,
    DECEPTION,
    NATURE,
    INSIGHT,
    RELIGION,
    STEALTH,
    PERSUASION,
    ANIMAL_HANDLING;

    private static final Set<MasteryTypeDnD> stats = Set.of(STRENGTH, DEXTERITY, CONSTITUTION, INTELLIGENCE, WISDOM, CHARISMA);
    private static final Set<MasteryTypeDnD> skills = Set.of(ACROBATICS, ANALYSIS, ATHLETICS, PERCEPTION, SURVIVAL, PERFORMANCE,
    INTIMIDATION, HISTORY, SLEIGHT_OF_HAND, ARCANE, MEDICINE, DECEPTION, NATURE, INSIGHT, RELIGION, STEALTH, PERSUASION,
    ANIMAL_HANDLING);

    private static final Map<MasteryTypeDnD, String> masteryString = Map.ofEntries(Map.entry(STRENGTH, "Сила"),
            Map.entry(DEXTERITY, "Ловкость"), Map.entry(CONSTITUTION, "Выносливость"),
            Map.entry(INTELLIGENCE, "Интеллект"), Map.entry(WISDOM, "Мудрость"),  Map.entry(CHARISMA, "Харизма"),
            Map.entry(ACROBATICS, "Акробатика"), Map.entry(ANALYSIS, "Анализ"), Map.entry(ATHLETICS, "Атлетика"),
            Map.entry(PERCEPTION, "Восприятие"), Map.entry(SURVIVAL, "Выживание"), Map.entry(PERFORMANCE, "Выступление"),
            Map.entry(INTIMIDATION, "Запугивание"), Map.entry(HISTORY, "История"), Map.entry(SLEIGHT_OF_HAND, "Ловкость рук"),
            Map.entry(ARCANE, "Магия"), Map.entry(MEDICINE, "Медицина"), Map.entry(DECEPTION, "Обман"),
            Map.entry(NATURE, "Природа"), Map.entry(INSIGHT, "Проницательность"), Map.entry(RELIGION, "Религия"),
            Map.entry(STEALTH, "Скрытность"), Map.entry(PERSUASION, "Убеждение"), Map.entry(ANIMAL_HANDLING, "Уход за животными"),
            Map.entry(NONE, ""), Map.entry(LUCK, "Удача"), Map.entry(SAVE, "Спасение ("),
            Map.entry(DEATH_SAVE, "Спасение (Смерть)"), Map.entry(PRECISION, "Точность"),
            Map.entry(DAMAGE, "Нанесение урона"));

    private static final Map<String, MasteryTypeDnD> advantages = Map.ofEntries(Map.entry("Сила", STRENGTH),
            Map.entry("Ловкость", DEXTERITY), Map.entry("Выносливость", CONSTITUTION),
            Map.entry("Интеллект", INTELLIGENCE), Map.entry("Мудрость", WISDOM),  Map.entry("Харизма", CHARISMA),
            Map.entry("Акробатика", ACROBATICS), Map.entry("Анализ", ANALYSIS), Map.entry("Атлетика", ATHLETICS),
            Map.entry("Восприятие", PERCEPTION), Map.entry("Выживание", SURVIVAL), Map.entry("Выступление", PERFORMANCE),
            Map.entry("Запугивание", INTIMIDATION), Map.entry("История", HISTORY), Map.entry("Ловкость рук", SLEIGHT_OF_HAND),
            Map.entry("Магия", ARCANE), Map.entry("Медицина", MEDICINE), Map.entry("Обман", DECEPTION),
            Map.entry("Природа", NATURE), Map.entry("Проницательность", INSIGHT), Map.entry("Религия", RELIGION),
            Map.entry("Скрытность", STEALTH), Map.entry("Убеждение", PERSUASION), Map.entry("Уход за животными", ANIMAL_HANDLING));

    private static final Map<String, MasteryTypeDnD> rollParameters = Map.ofEntries(Map.entry("str", STRENGTH),
            Map.entry("dex", DEXTERITY), Map.entry("con", CONSTITUTION),
            Map.entry("int", INTELLIGENCE), Map.entry("wis", WISDOM),  Map.entry("cha", CHARISMA),
            Map.entry("acr", ACROBATICS), Map.entry("an", ANALYSIS), Map.entry("ath", ATHLETICS),
            Map.entry("perc", PERCEPTION), Map.entry("sur", SURVIVAL), Map.entry("perf", PERFORMANCE),
            Map.entry("inti", INTIMIDATION), Map.entry("hist", HISTORY), Map.entry("sl", SLEIGHT_OF_HAND),
            Map.entry("arc", ARCANE), Map.entry("med", MEDICINE), Map.entry("dec", DECEPTION),
            Map.entry("nat", NATURE), Map.entry("ins", INSIGHT), Map.entry("rel", RELIGION),
            Map.entry("stl", STEALTH), Map.entry("pers", PERSUASION), Map.entry("anh", ANIMAL_HANDLING));

    private static final Map<String, MasteryTypeDnD> editAdv = Map.ofEntries(Map.entry("-str", STRENGTH),
            Map.entry("-dex", DEXTERITY), Map.entry("-con", CONSTITUTION),
            Map.entry("-int", INTELLIGENCE), Map.entry("-wis", WISDOM),  Map.entry("-cha", CHARISMA),
            Map.entry("-acr", ACROBATICS), Map.entry("-an", ANALYSIS), Map.entry("-ath", ATHLETICS),
            Map.entry("-perc", PERCEPTION), Map.entry("-sur", SURVIVAL), Map.entry("-perf", PERFORMANCE),
            Map.entry("-inti", INTIMIDATION), Map.entry("-hist", HISTORY), Map.entry("-sl", SLEIGHT_OF_HAND),
            Map.entry("-arc", ARCANE), Map.entry("-med", MEDICINE), Map.entry("-dec", DECEPTION),
            Map.entry("-nat", NATURE), Map.entry("-ins", INSIGHT), Map.entry("-rel", RELIGION),
            Map.entry("-stl", STEALTH), Map.entry("-pers", PERSUASION), Map.entry("-anh", ANIMAL_HANDLING));

    private static final Map<String, MasteryTypeDnD> specialCases = Map.of("luck", LUCK, "svth", SAVE,
            "dsvth", DEATH_SAVE, "pres", PRECISION, "dam", DAMAGE);

    public static Set<MasteryTypeDnD> getSkills() {
        return skills;
    }

    public static Set<MasteryTypeDnD> getStats() {
        return stats;
    }

    public static Map<String, MasteryTypeDnD> getSpecialCases() {
        return specialCases;
    }

    public static Map<String, MasteryTypeDnD> getRollParameters() {
        return rollParameters;
    }

    public static Map<String, MasteryTypeDnD> getAdvantages() {
        return advantages;
    }

    public static Map<String, MasteryTypeDnD> getEditAdv() {
        return editAdv;
    }

    public static MasteryTypeDnD getAdvantage(String adv) {
        return advantages.get(adv);
    }

    public static MasteryTypeDnD getEditAdvantage(String token) {
        return editAdv.get(token);
    }

    public static MasteryTypeDnD getParameter(String parameter) {
        return rollParameters.get(parameter);
    }

    @Override
    public String toString() {
        return masteryString.get(this);
    }
}
