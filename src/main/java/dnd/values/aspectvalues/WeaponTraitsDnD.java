package dnd.values.aspectvalues;

import java.io.Serializable;
import java.util.Map;

public enum WeaponTraitsDnD implements Serializable {
    AMMO,
    TWOHANDED,
    REACH,
    DISTANCE,
    LIGHT,
    THROWABLE,
    SPECIAL,
    RELOAD,
    HEAVY,
    UNIVERSAL,
    FENCING;

    private static final Map<String, WeaponTraitsDnD> traits = Map.ofEntries(Map.entry("Аммуниция", AMMO),
            Map.entry("Двуручное", TWOHANDED), Map.entry("Длинное", REACH), Map.entry("Дистанция", DISTANCE),
            Map.entry("Легкое", LIGHT), Map.entry("Бросаемое", THROWABLE), Map.entry("Специальное", SPECIAL),
            Map.entry("Перезарядка", RELOAD), Map.entry("Тяжелое", HEAVY), Map.entry("Универсальное", UNIVERSAL),
            Map.entry("Фехтовальное", FENCING));

    public static Map<String, WeaponTraitsDnD> getTraits() {
        return traits;
    }

    public static WeaponTraitsDnD getTrait(String trait) {
        return traits.get(trait);
    }
}
