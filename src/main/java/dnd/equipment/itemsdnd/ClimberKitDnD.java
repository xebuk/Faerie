package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class ClimberKitDnD extends ItemDnD {

    public ClimberKitDnD() {
        this.name = "Комплект для лазания";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 25;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 12;
    }
}
