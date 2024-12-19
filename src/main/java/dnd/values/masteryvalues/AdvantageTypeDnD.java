package dnd.values.masteryvalues;

import java.util.Map;

public enum AdvantageTypeDnD {
    ADVANTAGE, CLEAR_THROW, DISADVANTAGE;

    private static final Map<AdvantageTypeDnD, String> advantageTypeString = Map.of(ADVANTAGE, "Преимущество",
            CLEAR_THROW, "Чистый бросок", DISADVANTAGE, "Помеха");

    private static final Map<String, AdvantageTypeDnD> rollParameters = Map.of("adv", ADVANTAGE,
            "clr", CLEAR_THROW, "dis", DISADVANTAGE);

    @Override
    public String toString() {
        return advantageTypeString.get(this);
    }

    public static Map<String, AdvantageTypeDnD> getRollParameters() {
        return rollParameters;
    }
}
