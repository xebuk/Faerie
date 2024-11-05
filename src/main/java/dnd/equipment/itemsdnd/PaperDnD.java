package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class PaperDnD extends ItemDnD {

    public PaperDnD() {
        this.name = "Бумага";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 2;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 0;
    }
}
