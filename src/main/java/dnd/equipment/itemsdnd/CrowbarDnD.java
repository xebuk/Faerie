package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class CrowbarDnD extends ItemDnD {

    public CrowbarDnD() {
        this.name = "Ломик";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 2;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 5;
    }
}
