package dnd.values.masteryvalues;

import java.util.Map;

public enum AdvantageTypeDnD {
    ADVANTAGE, CLEAR_THROW, DISADVANTAGE;

    private static final Map<AdvantageTypeDnD, String> advantageTypeString = Map.of(ADVANTAGE, "Преимущество",
            CLEAR_THROW, "Чистый бросок", DISADVANTAGE, "Помеха");

    @Override
    public String toString() {
        return advantageTypeString.get(this);
    }
}
