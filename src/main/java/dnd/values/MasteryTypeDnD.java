package dnd.values;

import java.io.Serializable;
import java.util.Map;

public enum MasteryTypeDnD implements Serializable {
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

    private static final MasteryTypeDnD[] stats = {STRENGTH, DEXTERITY, CONSTITUTION, INTELLIGENCE, WISDOM, CHARISMA};
    private static final MasteryTypeDnD[] skills = {ACROBATICS, ANALYSIS, ATHLETICS, PERCEPTION, SURVIVAL, PERFORMANCE,
    INTIMIDATION, HISTORY, SLEIGHT_OF_HAND, ARCANE, MEDICINE, DECEPTION, NATURE, INSIGHT, RELIGION, STEALTH, PERSUASION,
    ANIMAL_HANDLING};

    private static final Map<MasteryTypeDnD, String> masteryString = Map.ofEntries(Map.entry(STRENGTH, "Сила"),
            Map.entry(DEXTERITY, "Ловкость"), Map.entry(CONSTITUTION, "Выносливость"),
            Map.entry(INTELLIGENCE, "Интеллект"), Map.entry(WISDOM, "Мудрость"),  Map.entry(CHARISMA, "Харизма"),
            Map.entry(ACROBATICS, "Акробатика"), Map.entry(ANALYSIS, "Анализ"), Map.entry(ATHLETICS, "Атлетика"),
            Map.entry(PERCEPTION, "Восприятие"), Map.entry(SURVIVAL, "Выживание"), Map.entry(PERFORMANCE, "Выступление"),
            Map.entry(INTIMIDATION, "Запугивание"), Map.entry(HISTORY, "История"), Map.entry(SLEIGHT_OF_HAND, "Ловкость рук"),
            Map.entry(ARCANE, "Магия"), Map.entry(MEDICINE, "Медицина"), Map.entry(DECEPTION, "Обман"),
            Map.entry(NATURE, "Природа"), Map.entry(INSIGHT, "Прорицательность"), Map.entry(RELIGION, "Религия"),
            Map.entry(STEALTH, "Скрытность"), Map.entry(PERSUASION, "Убеждение"), Map.entry(ANIMAL_HANDLING, "Уход за животными"));

    public static MasteryTypeDnD[] getSkills() {
        return skills;
    }

    public static MasteryTypeDnD[] getStats() {
        return stats;
    }
}
