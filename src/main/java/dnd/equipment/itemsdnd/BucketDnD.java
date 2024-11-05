package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BucketDnD extends ItemDnD {

    public BucketDnD() {
        this.name = "Ведро";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 5;
        this.currencyGrade = CurrencyDnD.COPPER_COINS;

        this.weight = 2;
    }
}
