package dnd.values.aspectvalues;

import java.io.Serializable;
import java.util.Map;

public enum AspectCategoriesDnD implements Serializable {
    NONE,
    ITEM,
    WEAPON,
    ARMOR,
    INSTRUMENTS,
    KIT,
    FEAT,
    ABILITY,
    SPELL;

    private static final Map<AspectCategoriesDnD, String> selectedEdit = Map.ofEntries(Map.entry(ITEM, "Предмет"),
            Map.entry(WEAPON, "Оружие"), Map.entry(ARMOR, "Броня"), Map.entry(INSTRUMENTS, "Инструменты"),
            Map.entry(KIT, "Комплект"), Map.entry(FEAT, "Черта"), Map.entry(ABILITY, "Способность"),
            Map.entry(SPELL, "Заклинание"));

    @Override
    public String toString() {
        return selectedEdit.get(this);
    }
}
