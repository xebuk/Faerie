package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class VialOfAntitoxinDnD extends ItemDnD {

    public VialOfAntitoxinDnD() {
        this.name = "Пузырек противоядия";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 50;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 0;
    }
}