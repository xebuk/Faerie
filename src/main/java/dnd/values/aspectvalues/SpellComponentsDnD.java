package dnd.values.aspectvalues;

import java.io.Serializable;
import java.util.Map;

public enum SpellComponentsDnD implements Serializable {
    VERBAL, SOMATIC, MATERIAL;

    private static final Map<String, SpellComponentsDnD> spellComp = Map.of("Вербальный", VERBAL,
            "Соматический", SOMATIC, "Материальный", MATERIAL);

    public static Map<String, SpellComponentsDnD> getSpellComp() {
        return spellComp;
    }

    public static SpellComponentsDnD getComp(String component) {
        return spellComp.get(component);
    }
}
