package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class CaltropDnD extends ItemDnD {

    public CaltropDnD() {
        this.name = "Калтроп";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 20;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 2;
    }
}
