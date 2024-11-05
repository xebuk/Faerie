package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class StaffDnD extends ItemDnD {

    public StaffDnD() {
        this.name = "Посох";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 5;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 4;
    }
}
