package dnd.equipment;

import common.Constants;
import dnd.values.CurrencyDnD;
import dnd.values.WeaponTraitsDnD;

import java.util.HashSet;

public class MagicWeaponDnD extends WeaponDnD {
    public int hitBonus;
    public String effects;

    public MagicWeaponDnD() {
        this.name = "Своё магическое оружие";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.hitBonus = 1;
        this.effects = "Введите свои эффекты.";
    }
}
