package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class CrossbowBoltDnD extends ItemDnD {

    public CrossbowBoltDnD() {
        this.name = "Арбалетный болт";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 20;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1.5;
    }
}
