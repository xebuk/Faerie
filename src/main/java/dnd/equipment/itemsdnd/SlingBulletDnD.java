package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class SlingBulletDnD extends ItemDnD {

    public SlingBulletDnD() {
        this.name = "Снаряд для пращи";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 20;

        this.value = 4;
        this.currencyGrade = CurrencyDnD.COPPER_COINS;

        this.weight = 1.5;
    }
}
