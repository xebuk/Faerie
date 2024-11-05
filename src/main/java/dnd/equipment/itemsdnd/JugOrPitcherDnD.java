package dnd.equipment.itemsdnd;

import common.Constants;
import dnd.equipment.ItemDnD;
import dnd.values.CurrencyDnD;

public class JugOrPitcherDnD extends ItemDnD {

    public JugOrPitcherDnD() {
        this.name = "Кувшин или графин";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 2;
        this.currencyGrade = CurrencyDnD.COPPER_COINS;

        this.weight = 4;
    }
}
