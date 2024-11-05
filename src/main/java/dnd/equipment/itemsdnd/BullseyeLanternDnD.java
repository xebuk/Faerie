package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BullseyeLanternDnD extends ItemDnD {

    public BullseyeLanternDnD() {
        this.name = "Направленный фонарь";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 10;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 2;
    }
}
