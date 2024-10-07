package game.items.weapons;

import game.items.Weapon;
import game.items.artefacts.CommonItem;

public class Mace extends Weapon {

    public Mace() {
        this.usesAmmunition = false;
        this.ammunitionType = new Air();

        this.attackDice = "1d8";
        this.attackPower = 5;

        this.specialBonuses = new CommonItem();
    }
}
