package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class SledgehammerDnD extends ItemDnD {

    public SledgehammerDnD() {
        this.name = "Кузнечный молот";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 2;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 10;
    }
}
