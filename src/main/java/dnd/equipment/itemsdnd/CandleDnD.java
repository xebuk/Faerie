package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class CandleDnD extends ItemDnD {

    public CandleDnD() {
        this.name = "Свеча";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.COPPER_COINS;

        this.weight = 0;
    }
}
