package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BallBearingDnD extends ItemDnD {

    public BallBearingDnD() {
        this.name = "Металлический шарик";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1000;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 2;
    }
}
