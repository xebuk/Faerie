package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BasketDnD extends ItemDnD {

    public BasketDnD() {
        this.name = "Корзина";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 4;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 2;
    }
}
