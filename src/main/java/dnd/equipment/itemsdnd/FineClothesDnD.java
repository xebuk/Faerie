package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class FineClothesDnD extends ItemDnD {

    public FineClothesDnD() {
        this.name = "Отличная одежда";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 15;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 6;
    }
}
