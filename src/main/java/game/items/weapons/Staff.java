package game.items.weapons;

import game.items.Weapon;
import game.items.artefacts.CommonItem;

public class Staff extends Weapon {

    public Staff() {
        this.usesAmmunition = false;
        this.ammunitionType = new Air();

        this.attackDice = "1d6";
        this.attackPower = 4;

        this.specialBonuses = new CommonItem();
    }
}
