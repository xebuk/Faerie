package dnd.dmtools;

import common.DiceNew;
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
        pc.valuables = 15;
    }
}
