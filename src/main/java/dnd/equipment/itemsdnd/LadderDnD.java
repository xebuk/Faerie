package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class LadderDnD extends ItemDnD {

    public LadderDnD() {
        this.name = "Лестница (10 футов)";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 25;
    }
}
