package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BookDnD extends ItemDnD {

    public BookDnD() {
        this.name = "Книга";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 25;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 5;
    }
}
