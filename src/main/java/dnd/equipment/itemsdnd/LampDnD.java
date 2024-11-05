package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class LampDnD extends ItemDnD {

    public LampDnD() {
        this.name = "Лампа";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 5;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 1;
    }
}
