package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class BlowgunNeedleDnD extends ItemDnD {

    public BlowgunNeedleDnD() {
        this.name = "Игла для духовой трубки";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 50;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;
    }
}
