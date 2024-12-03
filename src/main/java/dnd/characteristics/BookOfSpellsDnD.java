package dnd.characteristics;

import java.io.Serializable;
import java.util.ArrayList;

public class BookOfSpellsDnD implements Serializable {
    public int spellsBaseStat;
    public int saveDc;
    public int attackBonus;

    public ArrayList<SpellDnD> activeSpells = new ArrayList<>();
    public ArrayList<SpellDnD> learnedSpells = new ArrayList<>();

    public int[] totalSpellSlots = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    public int[] spellSlotsSpent = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    public BookOfSpellsDnD() {}
}
