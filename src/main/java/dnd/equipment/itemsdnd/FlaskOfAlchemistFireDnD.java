package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class FlaskOfAlchemistFireDnD extends ItemDnD {

    public FlaskOfAlchemistFireDnD() {
        this.name = "Склянка алхимического огня";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 50;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;
    }
}