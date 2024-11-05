package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class FishingTackleDnD extends ItemDnD {

    public FishingTackleDnD() {
        this.name = "Комплект для рыбалки";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 4;
    }
}
