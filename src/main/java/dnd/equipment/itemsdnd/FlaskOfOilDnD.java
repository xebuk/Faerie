package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class FlaskOfOilDnD extends ItemDnD {

    public FlaskOfOilDnD() {
        this.name = "Фляга масла";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.SILVER_COINS;

        this.weight = 1;
    }
}
