package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class FlaskOrTankardDnD extends ItemDnD {

    public FlaskOrTankardDnD() {
        this.name = "Фляга или большая кружка";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 2;
        this.currencyGrade = CurrencyDnD.COPPER_COINS;

        this.weight = 1;
    }
}
