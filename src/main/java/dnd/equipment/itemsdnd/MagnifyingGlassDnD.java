package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class MagnifyingGlassDnD extends ItemDnD {

    public MagnifyingGlassDnD() {
        this.name = "Увеличительное стекло";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 100;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 0;
    }
}
