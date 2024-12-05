package dnd.values.equipmentids;

import java.util.Map;

public enum ArmorsDnD {
    CUSTOM,

    LIGHT,
    MEDIUM,
    HEAVY,
    SHIELD,

    BREASTPLATE,
    CHAIN_MAIL,
    CHAIN_SHIRT,
    HALF_PLATE,
    HIDE_ARMOR,
    LEATHER_ARMOR,
    PADDED_ARMOR,
    PLATE_ARMOR,
    RING_MAIL,
    SCALE_MAIL,
    SPIKED_ARMOR,
    SPLINT_ARMOR,
    STUDDED_LEATHER_ARMOR;

    private static final Map<String, ArmorsDnD> armorTypes = Map.of("Легкая", LIGHT,
            "Средняя", MEDIUM, "Тяжелая", HEAVY, "Щит", SHIELD);

    public static Map<String, ArmorsDnD> getArmorTypes() {
        return armorTypes;
    }

    public static ArmorsDnD getArmorType(String armorType) {
        return armorTypes.get(armorType);
    }
}
