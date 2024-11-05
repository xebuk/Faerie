package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class ParchmentDnD extends ItemDnD {

    public ParchmentDnD() {
        this.name = "Пергамент";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 0;
    }
}
