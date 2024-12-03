package dnd.values.equipmentids;

import java.util.Map;

public enum ArmorsDnD {
    CUSTOM,

    LIGHT,
    MEDIUM,
    HEAVY,
    SHIELD;

    private static final Map<String, ArmorsDnD> armorTypes = Map.of("Легкая", LIGHT,
            "Средняя", MEDIUM, "Тяжелая", HEAVY, "Щит", SHIELD);

    public static Map<String, ArmorsDnD> getArmorTypes() {
        return armorTypes;
    }

    public static ArmorsDnD getArmorType(String armorType) {
        return armorTypes.get(armorType);
    }
}
