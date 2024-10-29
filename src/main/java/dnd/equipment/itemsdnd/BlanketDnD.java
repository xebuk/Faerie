package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BlanketDnD extends ItemDnD {

    public BlanketDnD() {
        this.name = "Одеяло";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 5;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 3;
    }
}
