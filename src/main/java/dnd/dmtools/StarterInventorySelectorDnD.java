package dnd.dmtools;

import common.DiceNew;
import dnd.equipment.ItemDnD;
import dnd.mainobjects.PlayerDnD;

import java.util.Objects;

public class StarterInventorySelectorDnD {

    public static void inventoryRefusal(PlayerDnD pc) {
        pc.itemCollection.clear();
        pc.valuables = DiceNew.customDiceResult(pc.mainJob.inventoryRefusalMoney);
        if (!Objects.equals(pc.mainJob.title, "Монах")) {
            pc.valuables = pc.valuables * 10;
        }
    }

    public static void acolyteInventoryStart(PlayerDnD pc) {
        pc.itemCollection.put(new ItemDnD("Священный символ", 5), 1);
        pc.itemCollection.put(new ItemDnD("Палочка благовоний"), 5);
        pc.itemCollection.put(new ItemDnD("Ряса", 1, 4), 1);
        pc.itemCollection.put(new ItemDnD("Комплект обычной одежды"), 1);
        pc.valuables = 15;
    }
}
