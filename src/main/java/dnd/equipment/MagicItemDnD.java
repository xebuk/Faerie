package dnd.equipment;

import common.Constants;
import dnd.values.CurrencyDnD;

import java.io.Serializable;

public class MagicItemDnD extends ItemDnD implements Serializable {
    public String effects;

    public MagicItemDnD() {
        this.name = "Свой магический предмет";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.effects = "Введите свои эффекты.";
    }
}
