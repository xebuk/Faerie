package dnd.values.masteryvalues;

import java.io.Serializable;
import java.util.Map;

public enum DamageTypeDnD implements Serializable {
    NONE,
    SLASHING,
    PIERCING,
    BLUDGEONING,

    POISON,
    ACID,
    FIRE,
    COLD,
    RADIANT,
    NECROTIC,
    LIGHTNING,
    THUNDER,
    FORCE,
    PSYCHIC;

    private static final Map<String, DamageTypeDnD> damageType = Map.ofEntries(Map.entry("Нет", NONE),
            Map.entry("Рубящий", SLASHING), Map.entry("Колющий", PIERCING), Map.entry("Дробящий", BLUDGEONING),
            Map.entry("Яд", POISON), Map.entry("Кислота", ACID), Map.entry("Огонь", FIRE),
            Map.entry("Холод", COLD), Map.entry("Святой", RADIANT), Map.entry("Некротика", NECROTIC),
            Map.entry("Молния", LIGHTNING), Map.entry("Громовой", THUNDER), Map.entry("Силовой", FORCE),
            Map.entry("Психический", PSYCHIC));

    private static final Map<DamageTypeDnD, String> damageTypeToString = Map.ofEntries(Map.entry(NONE, "Нет"),
            Map.entry(SLASHING, "Рубящий"), Map.entry(PIERCING, "Колющий"), Map.entry(BLUDGEONING, "Дробящий"),
            Map.entry(POISON, "Яд"), Map.entry(ACID, "Кислота"), Map.entry(FIRE, "Огонь"),
            Map.entry(COLD, "Холод"), Map.entry(RADIANT, "Святой"), Map.entry(NECROTIC, "Некротика"),
            Map.entry(LIGHTNING, "Молния"), Map.entry(THUNDER, "Громовой"), Map.entry(FORCE, "Силовой"),
            Map.entry(PSYCHIC, "Психический"));

    public static Map<String, DamageTypeDnD> getTypes() {
        return damageType;
    }

    public static DamageTypeDnD getType(String type) {
        return damageType.get(type);
    }

    @Override
    public String toString() {
        return damageTypeToString.get(this);
    }
}
